package ecjtu.homecoo.remoting.core;

import ecjtu.homecoo.remoting.protocol.Message;
import io.netty.channel.ChannelHandlerContext;


public class Connection {
	public Integer getConnectId() {
		return connectId;
	}

	public void setConnectId(Integer connectId) {
		this.connectId = connectId;
	}

	public String getGateWayNo() {
		return gateWayNo;
	}

	public void setGateWayNo(String gateWayNo) {
		this.gateWayNo = gateWayNo;
	}

	private Integer connectId;
	private ChannelHandlerContext channelHandlerContext;
	private String gateWayNo;

	private volatile boolean isClosed = false;

	/**
	 * 是否是被踢下线
	 */
	private boolean isKick = false;
	
	public Connection(ChannelHandlerContext context
			){
		this.channelHandlerContext = context;
	}
	
	public void sendMessage(Message message){
		this.channelHandlerContext.writeAndFlush(message);
	}

	@Override
	public String toString() {
		return "Connection [connectId=" + connectId
				+ ", channelHandlerContext=" + channelHandlerContext
				+ ", gateWayNo=" + gateWayNo + "]";
	}
	
	
	
	
}
