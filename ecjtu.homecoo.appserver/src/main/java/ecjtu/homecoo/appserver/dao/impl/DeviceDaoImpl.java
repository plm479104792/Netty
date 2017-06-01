package ecjtu.homecoo.appserver.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;


import ecjtu.homecoo.appserver.dao.DeviceDao;
import ecjtu.homecoo.appserver.domain.Device;

public class DeviceDaoImpl implements DeviceDao{
	
	private SqlSession sqlSession;

	@Override
	public int deleteDeviceByDeviceNo(String deviceNo) {
		int row=sqlSession.delete("DeviceMapper.deleteByDeviceNo",deviceNo);
		return row;
	}

	@Override
	public boolean existsDeviceByDeviceNo(String deviceNo) {
		boolean b=false;
//		Device device=sqlSession.selectOne("DeviceMapper.existsDeviceByDeviceNo", deviceNo);   2016-06-31 update
		List<Device> list=sqlSession.selectList("DeviceMapper.existsDeviceByDeviceNo", deviceNo);
		if (list.size()>0) {
			b=true;
		}
		return b;
	}
	
	@Override
	public int updateDeviceByDeviceNo(Device device) {
		int row=sqlSession.update("DeviceMapper.updateDeviceByDeviceNo", device);
		return row;
	}
	
	@Override
	public int addDevice(Device device) {
		int row=sqlSession.insert("DeviceMapper.insertDevice", device);
		return row;
	}
	
	@Override
	public Device getDeviceByDeviceNo(String deviceNo) {
//		Device device=sqlSession.selectOne("DeviceMapper.existsDeviceByDeviceNo", deviceNo);   2016-06-31 update
		List<Device> list=sqlSession.selectList("DeviceMapper.existsDeviceByDeviceNo", deviceNo);
		return list.get(0);
	}
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}


}
