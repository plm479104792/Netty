package ecjtu.homecoo.appserver.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ecjtu.homecoo.appserver.dao.PacketDao;
import ecjtu.homecoo.appserver.domain.Packet;
public class PacketDaoImpl implements PacketDao{
	
	private SqlSession sqlSession;

	public int updatePacket(Packet packet) {
		int row=sqlSession.update("PacketMapper.updatePacket", packet);
		return row;
	}

	public int deletePacket(Packet packet) {
		int row=sqlSession.delete("PacketMapper.deletePacket", packet);
		return row;
	}

	public int insertPacket(Packet packet) {
		int row=sqlSession.insert("PacketMapper.insertPacket", packet);
		return row;
	}

	public int selectPacket(Packet packet) {
		List<Packet> list=sqlSession.selectList("PacketMapper.selectPacket", packet);
		if (list.size()>0) {
			return list.size();
		}else {
			return 0;
		}
	}
	
	
	@Override
	public int deletePacketByGatewayNo(Packet packet) {
		int row=sqlSession.delete("PacketMapper.deletePacketByGatewayNo",packet);
		return row;
	}

	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	
}
