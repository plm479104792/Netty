package ecjtu.homecoo.remoting;

import ecjtu.homecoo.remoting.netty.ResponseFuture;

/**
 * 异步调用应答回调接口
 * 
 */
public interface InvokeCallback {
    public void operationComplete(final ResponseFuture responseFuture);
}
