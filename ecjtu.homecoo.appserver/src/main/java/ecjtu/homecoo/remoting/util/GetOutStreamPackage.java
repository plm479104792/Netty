package ecjtu.homecoo.remoting.util;

import ecjtu.homecoo.remoting.protocol.Message;

public class GetOutStreamPackage {

	/**
	 * 用于查看netty下发给网关的报文
	 * */
	public static String GetPackage(Message message){
		
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
		String stampString=BasicProcess.toHexString(Stamp);					//？
		String gatewayId=BasicProcess.toHexString(gatewayNo);
		String devIdString=BasicProcess.toHexString(dev_id);
		String devtypeString=BasicProcess.toHexString(device_type);				//？
		String datatypeString=BasicProcess.toHexString(data_Type);				//? 少了前面两个00
		String datalenString=BasicProcess.toHexString(datalength);					//?
		String dataString=BasicProcess.toHexString(body);
		//整条报文的String类型
		String msg=headString+stampString+gatewayId+devIdString+devtypeString+datatypeString+datalenString+dataString;
		return msg;
		
	}
}
