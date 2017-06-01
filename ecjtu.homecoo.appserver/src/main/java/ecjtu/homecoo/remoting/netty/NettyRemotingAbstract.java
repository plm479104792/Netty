package ecjtu.homecoo.remoting.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecjtu.homecoo.remoting.ChannelEventListener;
import ecjtu.homecoo.remoting.InvokeCallback;
import ecjtu.homecoo.remoting.RPCHook;
import ecjtu.homecoo.remoting.common.Pair;
import ecjtu.homecoo.remoting.common.RemotingHelper;
import ecjtu.homecoo.remoting.common.SemaphoreReleaseOnlyOnce;
import ecjtu.homecoo.remoting.common.ServiceThread;
import ecjtu.homecoo.remoting.exception.RemotingSendRequestException;
import ecjtu.homecoo.remoting.exception.RemotingTimeoutException;
import ecjtu.homecoo.remoting.exception.RemotingTooMuchRequestException;
import ecjtu.homecoo.remoting.protocol.Message;


/**
 * Server与Client公用抽象类
 * 
 * @author shijia.wxr<vintage.wang@gmail.com>
 * @since 2013-7-13
 */
public abstract class NettyRemotingAbstract {
    private static final Logger plog = LoggerFactory.getLogger(RemotingHelper.RemotingLogName);

    // 信号量，Oneway情况会使用，防止本地Netty缓存请求过多
    protected final Semaphore semaphoreOneway;

    // 信号量，异步调用情况会使用，防止本地Netty缓存请求过多
    protected final Semaphore semaphoreAsync;
    
    protected ConcurrentHashMap<Integer, ResponseFuture> responseTable = new ConcurrentHashMap<>();

    // 默认请求代码处理器
    protected Pair<NettyRequestProcessor, ExecutorService> defaultRequestProcessor;

    // 注册的各个RPC处理器
    protected final HashMap<Short/* request code */, Pair<NettyRequestProcessor, ExecutorService>> processorTable =
            new HashMap<Short, Pair<NettyRequestProcessor, ExecutorService>>(64);

    protected final NettyEventExecuter nettyEventExecuter = new NettyEventExecuter();


    public abstract ChannelEventListener getChannelEventListener();

 
    public abstract RPCHook getRPCHook();


    public void putNettyEvent(final NettyEvent event) {
        this.nettyEventExecuter.putNettyEvent(event);
    }

    class NettyEventExecuter extends ServiceThread {
        private final LinkedBlockingQueue<NettyEvent> eventQueue = new LinkedBlockingQueue<NettyEvent>();
        private final int MaxSize = 10000;


        public void putNettyEvent(final NettyEvent event) {
            if (this.eventQueue.size() <= MaxSize) {
                this.eventQueue.add(event);
            }
            else {
                plog.warn("event queue size[{}] enough, so drop this event {}", this.eventQueue.size(),
                    event.toString());
            }
        }


        @Override
        public void run() {
            plog.info(this.getServiceName() + " service started");

            final ChannelEventListener listener = NettyRemotingAbstract.this.getChannelEventListener();

            while (!this.isStoped()) {
                try {
                    NettyEvent event = this.eventQueue.poll(3000, TimeUnit.MILLISECONDS);
                    if (event != null && listener != null) {
                        switch (event.getType()) {
                        case IDLE:
                            listener.onChannelIdle(event.getRemoteAddr(), event.getChannel());
                            break;
                        case CLOSE:
                            listener.onChannelClose(event.getRemoteAddr(), event.getChannel());
                            break;
                        case CONNECT:
                            listener.onChannelConnect(event.getRemoteAddr(), event.getChannel());
                            break;
                        case EXCEPTION:
                            listener.onChannelException(event.getRemoteAddr(), event.getChannel());
                            break;
                        default:
                            break;

                        }
                    }
                }
                catch (Exception e) {
                    plog.warn(this.getServiceName() + " service has exception. ", e);
                }
            }

            plog.info(this.getServiceName() + " service end");
        }


        @Override
        public String getServiceName() {
            return NettyEventExecuter.class.getSimpleName();
        }
    }


    public NettyRemotingAbstract(final int permitsOneway, final int permitsAsync) {
        this.semaphoreOneway = new Semaphore(permitsOneway, true);
        this.semaphoreAsync = new Semaphore(permitsAsync, true);
    }


    public void processRequestCommand(final ChannelHandlerContext ctx, final Message cmd) {
        final Pair<NettyRequestProcessor, ExecutorService> matched = this.processorTable.get(cmd.getMessageHead().getData_type());
        final Pair<NettyRequestProcessor, ExecutorService> pair =
                null == matched ? this.defaultRequestProcessor : matched;
         //建立channel 和 gateWayNo 的关系
        
 
        if (pair != null) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        RPCHook rpcHook = NettyRemotingAbstract.this.getRPCHook();
                        if (rpcHook != null) {
                            rpcHook
                                .doBeforeRequest(RemotingHelper.parseChannelRemoteAddr(ctx.channel()), cmd);
                        }

                        final Message response = pair.getObject1().processRequest(ctx, cmd);
                        if (rpcHook != null) {
                            rpcHook.doAfterResponse(RemotingHelper.parseChannelRemoteAddr(ctx.channel()),
                                cmd, response);
                        }
                        if(response != null){
                        	 try {
                                 ctx.writeAndFlush(response);
                             }
                             catch (Throwable e) {
                                 plog.error("process request over, but response failed", e);
                                 plog.error(cmd.toString());
                                 plog.error(response.toString());
                             }
                        }else{
                        	// 收到请求，但是没有返回应答，可能是processRequest中进行了应答，忽略这种情况
                        }
     
                    }
                    catch (Throwable e) {
                        plog.error("process request exception", e);
                        plog.error(cmd.toString());

               
                    }
                    
                }
            };

            try {
                // 这里需要做流控，要求线程池对应的队列必须是有大小限制的
                pair.getObject2().submit(run);
            }
            catch (RejectedExecutionException e) {
                // 每个线程10s打印一次
                if ((System.currentTimeMillis() % 10000) == 0) {
                    plog.warn(RemotingHelper.parseChannelRemoteAddr(ctx.channel()) //
                            + ", too many requests and system thread pool busy, RejectedExecutionException " //
                            + pair.getObject2().toString() //
                            + " request code: " + cmd.getMessageHead().getData_type());
                }

    
            }
        }
       
    }

    public void processMessageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
        final Message cmd = msg;
      
        processRequestCommand(ctx, cmd);
           
        
    }

    public Message invokeSyncImpl(final Channel channel, final Message request,
            final long timeoutMillis) throws InterruptedException, RemotingSendRequestException,
            RemotingTimeoutException {
        try {
            final ResponseFuture responseFuture =
                    new ResponseFuture(request.getOpaque(), timeoutMillis, null, null);
            this.responseTable.put(request.getOpaque(), responseFuture);
            channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture f) throws Exception {
                    if (f.isSuccess()) {
                        responseFuture.setSendRequestOK(true);
                        return;
                    }
                    else {
                        responseFuture.setSendRequestOK(false);
                    }

                    responseTable.remove(request.getOpaque());
                    responseFuture.setCause(f.cause());
                    responseFuture.putResponse(null);
                    plog.warn("send a request command to channel <" + channel.remoteAddress() + "> failed.");
                    plog.warn(request.toString());
                }
            });

            Message responseCommand = responseFuture.waitResponse(timeoutMillis);
            if (null == responseCommand) {
                if (responseFuture.isSendRequestOK()) {
                    throw new RemotingTimeoutException(RemotingHelper.parseChannelRemoteAddr(channel),
                        timeoutMillis, responseFuture.getCause());
                }
                else {
                    throw new RemotingSendRequestException(RemotingHelper.parseChannelRemoteAddr(channel),
                        responseFuture.getCause());
                }
            }

            return responseCommand;
        }
        finally {
            this.responseTable.remove(request.getOpaque());
        }
    }
    
    public void invokeAsyncImpl(final Channel channel, final Message request,
            final long timeoutMillis, final InvokeCallback invokeCallback) throws InterruptedException,
            RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException {
        boolean acquired = this.semaphoreAsync.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
        if (acquired) {
            final SemaphoreReleaseOnlyOnce once = new SemaphoreReleaseOnlyOnce(this.semaphoreAsync);

            final ResponseFuture responseFuture =
                    new ResponseFuture(request.getOpaque(), timeoutMillis, invokeCallback, once);
            this.responseTable.put(request.getOpaque(), responseFuture);
            try {
                channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture f) throws Exception {
                        if (f.isSuccess()) {
                            responseFuture.setSendRequestOK(true);
                            return;
                        }
                        else {
                            responseFuture.setSendRequestOK(false);
                        }

                        responseFuture.putResponse(null);
                        responseTable.remove(request.getOpaque());
                        try {
                            responseFuture.executeInvokeCallback();
                        }
                        catch (Throwable e) {
                            plog.warn("excute callback in writeAndFlush addListener, and callback throw", e);
                        }
                        finally {
                            responseFuture.release();
                        }

                        plog.warn("send a request command to channel <{}> failed.",
                            RemotingHelper.parseChannelRemoteAddr(channel));
                        plog.warn(request.toString());
                    }
                });
            }
            catch (Exception e) {
                responseFuture.release();
                plog.warn(
                    "send a request command to channel <" + RemotingHelper.parseChannelRemoteAddr(channel)
                            + "> Exception", e);
                throw new RemotingSendRequestException(RemotingHelper.parseChannelRemoteAddr(channel), e);
            }
        }
        else {
            if (timeoutMillis <= 0) {
                throw new RemotingTooMuchRequestException("invokeAsyncImpl invoke too fast");
            }
            else {
                String info =
                        String
                            .format(
                                "invokeAsyncImpl tryAcquire semaphore timeout, %dms, waiting thread nums: %d semaphoreAsyncValue: %d", //
                                timeoutMillis,//
                                this.semaphoreAsync.getQueueLength(),//
                                this.semaphoreAsync.availablePermits()//
                            );
                plog.warn(info);
                plog.warn(request.toString());
                throw new RemotingTimeoutException(info);
            }
        }
    }
    public void invokeOnewayImpl(final Channel channel, final Message request,
            final long timeoutMillis) throws InterruptedException, RemotingTooMuchRequestException,
            RemotingTimeoutException, RemotingSendRequestException {
 
        boolean acquired = this.semaphoreOneway.tryAcquire(timeoutMillis, TimeUnit.MILLISECONDS);
        if (acquired) {
            final SemaphoreReleaseOnlyOnce once = new SemaphoreReleaseOnlyOnce(this.semaphoreOneway);
            try {
                channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture f) throws Exception {
                        once.release();
                        if (!f.isSuccess()) {
                            plog.warn("send a request command to channel <" + channel.remoteAddress()
                                    + "> failed.");
                            plog.warn(request.toString());
                        }
                    }
                });
            }
            catch (Exception e) {
                once.release();
                plog.warn("write send a request command to channel <" + channel.remoteAddress() + "> failed.");
                throw new RemotingSendRequestException(RemotingHelper.parseChannelRemoteAddr(channel), e);
            }
        }
        else {
            if (timeoutMillis <= 0) {
                throw new RemotingTooMuchRequestException("invokeOnewayImpl invoke too fast");
            }
            else {
                String info =
                        String
                            .format(
                                "invokeOnewayImpl tryAcquire semaphore timeout, %dms, waiting thread nums: %d semaphoreAsyncValue: %d", //
                                timeoutMillis,//
                                this.semaphoreAsync.getQueueLength(),//
                                this.semaphoreAsync.availablePermits()//
                            );
                plog.warn(info);
                plog.warn(request.toString());
                throw new RemotingTimeoutException(info);
            }
        }
    }
    public void scanResponseTable() {
        Iterator<Entry<Integer, ResponseFuture>> it = this.responseTable.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, ResponseFuture> next = it.next();
            ResponseFuture rep = next.getValue();

            if ((rep.getBeginTimestamp() + rep.getTimeoutMillis() + 1000) <= System.currentTimeMillis()) {
                it.remove();
                try {
                    rep.executeInvokeCallback();
                }
                catch (Throwable e) {
                    plog.warn("scanResponseTable, operationComplete Exception", e);
                }
                finally {
                    rep.release();
                }

                plog.warn("remove timeout request, " + rep);
            }
        }
    }
    
    
    abstract public ExecutorService getCallbackExecutor();


}
