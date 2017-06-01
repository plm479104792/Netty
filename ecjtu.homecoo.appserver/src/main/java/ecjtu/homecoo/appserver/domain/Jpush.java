package ecjtu.homecoo.appserver.domain;

import java.util.Date;

/**
 * 极光推送消息类
 * */
public class Jpush {
	private String gatewayNo;			//JPush推送别名  JPush根据gatewayNo推送到对应的网关下的所有手机
	private Object object;
	private int messsageType;			//1:设备状态更新  2:安防报警   3:音乐   4:情景类
	private MusicOrder musicOrder;		//音乐控制   用于IOS开发  JSON解析
	private Packet packet;				//专门用于IOS的开发  JSON解析
	
	private Long time;
	public String getGatewayNo() {
		return gatewayNo;
	}
	public void setGatewayNo(String gatewayNo) {
		this.gatewayNo = gatewayNo;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public int getMesssageType() {
		return messsageType;
	}
	public void setMesssageType(int messsageType) {
		this.messsageType = messsageType;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	public MusicOrder getMusicOrder() {
		return musicOrder;
	}
	public void setMusicOrder(MusicOrder musicOrder) {
		this.musicOrder = musicOrder;
	}
	public Packet getPacket() {
		return packet;
	}
	public void setPacket(Packet packet) {
		this.packet = packet;
	}
	@Override
	public String toString() {
		return "Jpush [gatewayNo=" + gatewayNo + ", object=" + object
				+ ", messsageType=" + messsageType + ", musicOrder="
				+ musicOrder + ", packet=" + packet + ", time=" + time + "]";
	}
	
}
