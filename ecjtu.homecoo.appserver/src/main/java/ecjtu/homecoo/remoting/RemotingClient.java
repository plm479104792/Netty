package ecjtu.homecoo.remoting;

import java.util.List;
import java.util.concurrent.ExecutorService;

import ecjtu.homecoo.remoting.exception.RemotingConnectException;
import ecjtu.homecoo.remoting.exception.RemotingSendRequestException;
import ecjtu.homecoo.remoting.exception.RemotingTimeoutException;
import ecjtu.homecoo.remoting.exception.RemotingTooMuchRequestException;
import ecjtu.homecoo.remoting.netty.NettyRequestProcessor;
import ecjtu.homecoo.remoting.protocol.Message;


/**
 * 远程通信，Client接口
 * 
 * @author shijia.wxr<vintage.wang@gmail.com>
 * @since 2013-7-13
 */
public interface RemotingClient extends RemotingService {

    public void updateNameServerAddressList(final List<String> addrs);


    public List<String> getNameServerAddressList();


    public Message invokeSync(final String addr, final Message request,
            final long timeoutMillis) throws InterruptedException, RemotingConnectException,
            RemotingSendRequestException, RemotingTimeoutException;


    public void invokeAsync(final String addr, final Message request, final long timeoutMillis,
            final InvokeCallback invokeCallback) throws InterruptedException, RemotingConnectException,
            RemotingTooMuchRequestException, RemotingTimeoutException, RemotingSendRequestException;


    public void invokeOneway(final String addr, final Message request, final long timeoutMillis)
            throws InterruptedException, RemotingConnectException, RemotingTooMuchRequestException,
            RemotingTimeoutException, RemotingSendRequestException;


    public void registerProcessor(final short requestCode, final NettyRequestProcessor processor,
            final ExecutorService executor);


    public boolean isChannelWriteable(final String addr);
}
