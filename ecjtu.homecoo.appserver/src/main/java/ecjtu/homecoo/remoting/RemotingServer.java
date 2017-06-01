package ecjtu.homecoo.remoting;

import java.util.concurrent.ExecutorService;

import ecjtu.homecoo.remoting.common.Pair;
import ecjtu.homecoo.remoting.netty.NettyRequestProcessor;



/**
 * 远程通信，Server接口
 */
public interface RemotingServer extends RemotingService {

    /**
     * 注册请求处理器，ExecutorService必须要对应一个队列大小有限制的阻塞队列，防止OOM
     * 
     * @param requestCode
     * @param processor
     * @param executor
     */
    public void registerProcessor(final short requestCode, final NettyRequestProcessor processor,
            final ExecutorService executor);


    public void registerDefaultProcessor(final NettyRequestProcessor processor, final ExecutorService executor);


    /**
     * 服务器绑定的本地端口
     * 
     * @return PORT
     */
    public int localListenPort();


    public Pair<NettyRequestProcessor, ExecutorService> getProcessorPair(final int requestCode);

}
