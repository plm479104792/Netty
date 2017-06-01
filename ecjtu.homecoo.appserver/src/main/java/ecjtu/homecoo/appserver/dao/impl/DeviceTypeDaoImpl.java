package ecjtu.homecoo.appserver.dao.impl;

import org.apache.ibatis.session.SqlSession;

import ecjtu.homecoo.appserver.dao.DeviceTypeDao;
import ecjtu.homecoo.appserver.domain.DeviceType;

public class DeviceTypeDaoImpl implements DeviceTypeDao{

	private SqlSession sqlSession;
	
	@Override
	public DeviceType selectByPrimaryKey(String deviceTypeId) {
		DeviceType deviceType=sqlSession.selectOne("DeviceTypeMapper.selectBydeviceTypeId",deviceTypeId);
		return deviceType;
	}

	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	
	
}
