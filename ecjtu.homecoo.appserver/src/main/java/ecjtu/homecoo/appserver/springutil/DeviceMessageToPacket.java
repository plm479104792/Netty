package ecjtu.homecoo.appserver.springutil;


import ecjtu.homecoo.appserver.domain.Packet;
import ecjtu.homecoo.remoting.protocol.Message;
import ecjtu.homecoo.remoting.util.BasicProcess;

/**
 * 为了发送报文到手机端获取 特别写的一个工具类，刚好让手机解析netty 发送的报文与网关的一致。方便手机解析
 * 手机端的报文体 不需要状态戳(去掉时间戳 并把dets_length减去4个字节)
 * */
public class DeviceMessageToPacket {
	
	public static Packet  ToPacket(Message message){
		Packet packet=new Packet();			
		byte[] head=message.getMessageHead().getHeader();				//报文头部 head
		int stamp=message.getMessageHead().getStamp();					//报文时间戳
		byte[] Stamp= BasicProcess.intToByte(stamp);
		byte[] gatewayNo=message.getMessageHead().getGateway_id();		//网关
		byte[] dev_id=message.getMessageHead().getDev_id();				//设备id
		short dev_type=message.getMessageHead().getDev_type();			//设备类型
		byte[] device_type=BasicProcess.shortToByteArray2(dev_type);
		short data_type=message.getMessageHead().getData_type();		//报文类型data_type
		byte[] data_Type=BasicProcess.shortToByteArray2(data_type);
		short data_len=message.getMessageHead().getData_length();		//数据长度
		byte[] datalength=BasicProcess.shortToByteArray(data_len);
		byte[] body=message.getBody();									//报文体				
		String headString=BasicProcess.toHexString(head);
		String stampString=BasicProcess.toHexString(Stamp);					
		String gatewayId=BasicProcess.toHexString(gatewayNo);
		String devIdString=BasicProcess.toHexString(dev_id);
		String devtypeString=BasicProcess.toHexString(device_type);				
		String datatypeString=BasicProcess.toHexString(data_Type);				
		String datalenString=BasicProcess.toHexString(datalength);					
		String dataString=BasicProcess.toHexString(body);
		//整条报文的String类型
		String msg=headString+stampString+gatewayId+devIdString+devtypeString+datatypeString+datalenString+dataString;
		String themeno=BasicProcess.toHexString(body);	
		//获取情景唯一识别码
		packet.setThemeNo(null);
		packet.setGatewayNo(gatewayId);
		packet.setPacket(msg);
		return packet;
	}

}
