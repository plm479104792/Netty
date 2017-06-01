package ecjtu.homecoo.appserver.dao;

import ecjtu.homecoo.appserver.domain.Packet;

public interface PacketDao {
	
	public int updatePacket(Packet packet);
	
	public int deletePacket(Packet packet);
	
	public int insertPacket(Packet packet);
	
	public int selectPacket(Packet packet);
	
	public int deletePacketByGatewayNo(Packet packet);
	
}
