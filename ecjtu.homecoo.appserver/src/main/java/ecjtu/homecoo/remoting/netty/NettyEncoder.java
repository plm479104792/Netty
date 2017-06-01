package ecjtu.homecoo.remoting.netty;

import java.nio.ByteBuffer;

import ecjtu.homecoo.remoting.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class NettyEncoder extends MessageToByteEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out)
			throws Exception {
		ByteBuffer byteBuffer = msg.encodeHeader();
		out.writeBytes(byteBuffer.array());
		byte[] body = msg.getBody();
		if(body != null){
			out.writeBytes(msg.getBody());
		}
		
	}

}
