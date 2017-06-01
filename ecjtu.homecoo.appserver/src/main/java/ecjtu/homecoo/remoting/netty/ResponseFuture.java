package ecjtu.homecoo.remoting.netty;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import ecjtu.homecoo.remoting.InvokeCallback;
import ecjtu.homecoo.remoting.common.SemaphoreReleaseOnlyOnce;
import ecjtu.homecoo.remoting.protocol.Message;



/**
 * 异步请求应答封装
 * 
 * @author shijia.wxr<vintage.wang@gmail.com>
 * @since 2013-7-13
 */
public class ResponseFuture {
    private volatile Message responseCommand;
    private volatile boolean sendRequestOK = true;
    private volatile Throwable cause;
    private final int opaque;
    private final long timeoutMillis;
    private final InvokeCallback invokeCallback;
    private final long beginTimestamp = System.currentTimeMillis();
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    // 保证信号量至多至少只被释放一次
    private final SemaphoreReleaseOnlyOnce once;

    // 保证回调的callback方法至多至少只被执行一次
    private final AtomicBoolean executeCallbackOnlyOnce = new AtomicBoolean(false);


    public ResponseFuture(int opaque, long timeoutMillis, InvokeCallback invokeCallback,
            SemaphoreReleaseOnlyOnce once) {
        this.opaque = opaque;
        this.timeoutMillis = timeoutMillis;
        this.invokeCallback = invokeCallback;
        this.once = once;
    }


    public void executeInvokeCallback() {
        if (invokeCallback != null) {
            if (this.executeCallbackOnlyOnce.compareAndSet(false, true)) {
                invokeCallback.operationComplete(this);
            }
        }
    }


    public void release() {
        if (this.once != null) {
            this.once.release();
        }
    }


    public boolean isTimeout() {
        long diff = System.currentTimeMillis() - this.beginTimestamp;
        return diff > this.timeoutMillis;
    }


    public Message waitResponse(final long timeoutMillis) throws InterruptedException {
        this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return this.responseCommand;
    }


    public void putResponse(final Message responseCommand) {
        this.responseCommand = responseCommand;
        this.countDownLatch.countDown();
    }


    public long getBeginTimestamp() {
        return beginTimestamp;
    }


    public boolean isSendRequestOK() {
        return sendRequestOK;
    }


    public void setSendRequestOK(boolean sendRequestOK) {
        this.sendRequestOK = sendRequestOK;
    }


    public long getTimeoutMillis() {
        return timeoutMillis;
    }


    public InvokeCallback getInvokeCallback() {
        return invokeCallback;
    }


    public Throwable getCause() {
        return cause;
    }


    public void setCause(Throwable cause) {
        this.cause = cause;
    }


    public Message getResponseCommand() {
        return responseCommand;
    }


    public void setResponseCommand(Message responseCommand) {
        this.responseCommand = responseCommand;
    }


    public int getOpaque() {
        return opaque;
    }


    @Override
    public String toString() {
        return "ResponseFuture [responseCommand=" + responseCommand + ", sendRequestOK=" + sendRequestOK
                + ", cause=" + cause + ", opaque=" + opaque + ", timeoutMillis=" + timeoutMillis
                + ", invokeCallback=" + invokeCallback + ", beginTimestamp=" + beginTimestamp
                + ", countDownLatch=" + countDownLatch + "]";
    }
}
