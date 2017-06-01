package ecjtu.homecoo.appserver.service;

import ecjtu.homecoo.remoting.protocol.Message;

public interface PacketService {
	public int selectPacket(Message message);
	
	public int updatePacket(Message message);
	
	public int insertPacket(Message message);
	
	public int deletePacket(Message message);
	
	public int deletePacketByGatewayNo(Message message);

}
