package ecjtu.homecoo.appserver.client;

import io.netty.channel.Channel;

public class ClientChannelInfo {
	
	public String gateWayno;
	private Channel channel;
	private long lastHeartTime;
	
	
	public ClientChannelInfo(String gameWayno, Channel channel){
		this.gateWayno = gameWayno;
		this.channel = channel;
		this.lastHeartTime = System.currentTimeMillis();
	}
	public String getGateWayno() {
		return gateWayno;
	}
	public void setGateWayno(String gateWayno) {
		this.gateWayno = gateWayno;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public long getLastHeartTime() {
		return lastHeartTime;
	}
	public void setLastHeartTime(long lastHeartTime) {
		this.lastHeartTime = lastHeartTime;
	}
}
