package ecjtu.homecoo.remoting;

import io.netty.channel.Channel;


/**
 * @author shijia.wxr<vintage.wang@gmail.com>
 * @since 2013-7-13
 */
public interface ChannelEventListener {
    void onChannelConnect(final String remoteAddr, final Channel channel);

    void onChannelClose(final String remoteAddr, final Channel channel);


    void onChannelException(final String remoteAddr, final Channel channel);


    void onChannelIdle(final String remoteAddr, final Channel channel);
   
}
