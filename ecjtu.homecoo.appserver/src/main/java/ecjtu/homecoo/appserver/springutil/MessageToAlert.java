package ecjtu.homecoo.appserver.springutil;


import ecjtu.homecoo.appserver.domain.Alert;
import ecjtu.homecoo.appserver.domain.Packet;
import ecjtu.homecoo.remoting.protocol.Message;
import ecjtu.homecoo.remoting.util.BasicProcess;

public class MessageToAlert {
	
	public static Alert MessageToAlert(Message message,Long time){
		Alert alert=new Alert();
		Packet packet=DeviceMessageToPacket.ToPacket(message);
		alert.setPacket(packet.getPacket());
		alert.setTime(time);
		alert.setGatewayNo(packet.getGatewayNo());
		byte[] deviceNo=message.getMessageHead().getDev_id();
		alert.setDeviceNo(BasicProcess.toHexString(deviceNo));
		return alert;
	}
}
