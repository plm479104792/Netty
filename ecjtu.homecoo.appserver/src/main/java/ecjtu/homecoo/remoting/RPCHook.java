package ecjtu.homecoo.remoting;

import ecjtu.homecoo.remoting.protocol.Message;

/**
 * 前后置处理拦截
 * @author hsj
 *
 */
public interface RPCHook {
    public void doBeforeRequest(final String remoteAddr, final Message request);


    public void doAfterResponse(final String remoteAddr, final Message request,
            final Message response);
}
