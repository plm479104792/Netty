
package ecjtu.homecoo.remoting.netty;

import ecjtu.homecoo.remoting.protocol.Message;
import io.netty.channel.ChannelHandlerContext;



/**
 * Common remoting command processor
 */
public interface NettyRequestProcessor {
    Message processRequest(ChannelHandlerContext ctx, Message request)
            throws Exception;
}
