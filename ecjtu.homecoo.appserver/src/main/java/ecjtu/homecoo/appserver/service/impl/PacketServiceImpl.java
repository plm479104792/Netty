package ecjtu.homecoo.appserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ecjtu.homecoo.appserver.dao.PacketDao;
import ecjtu.homecoo.appserver.domain.Packet;
import ecjtu.homecoo.appserver.service.PacketService;
import ecjtu.homecoo.appserver.springutil.GetMessageBody;
import ecjtu.homecoo.appserver.springutil.MessageToPacket;
import ecjtu.homecoo.remoting.protocol.Message;
public class PacketServiceImpl implements PacketService{
	
	private PacketDao packetDao;

	public int selectPacket(Message message) {
		Packet packet=MessageToPacket.ToPacket(message);
		int row=packetDao.selectPacket(packet);
		return row;
	}

	public int updatePacket(Message message) {
		Packet packet=MessageToPacket.ToPacket(message);
		//获取整条报文放在packet上     可以封装成一个类    有重复报文
		int row=packetDao.updatePacket(packet); 		
		return row;
	}

	public int insertPacket(Message message) {
		Packet packet=MessageToPacket.ToPacket(message);
		int row=packetDao.insertPacket(packet);
		return row;
	}

	public int deletePacket(Message message) {
		Packet packet=MessageToPacket.ToPacket(message);
		int row=packetDao.deletePacket(packet);
		return row;
	}

	
	@Override
	public int deletePacketByGatewayNo(Message message) {
		Packet packet=new Packet();
		String gatewayNo=GetMessageBody.GetGatewayNo(message);
		packet.setGatewayNo(gatewayNo);
		int row=packetDao.deletePacketByGatewayNo(packet);
		return row;
	}
	

	public PacketDao getPacketDao() {
		return packetDao;
	}

	public void setPacketDao(PacketDao packetDao) {
		this.packetDao = packetDao;
	}

	
}
