package ecjtu.homecoo.appserver.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ecjtu.homecoo.appserver.dao.DeviceStateDao;
import ecjtu.homecoo.appserver.domain.DeviceState;
public class DeviceStateDaoImpl implements DeviceStateDao{

	private SqlSession sqlSession;
	public int updateDeviceState(DeviceState deviceState) {
		int row=sqlSession.update("DeviceStateMapper.updateDeviceState", deviceState);
		return row;
	}
	
	
	public int selectDeviceState(DeviceState deviceState) {
		List<DeviceState> list = sqlSession.selectList("selectDeviceState",deviceState);
		if (list.size()>0) {
			return list.size();
		}else{
			return 0;
		}
	}
	
	
	public List<DeviceState> selectDeviceStateList(DeviceState deviceState) {
		List<DeviceState> list = sqlSession.selectList("selectDeviceState",deviceState);
		return list;
	}
	


	public int insertDeviceState(DeviceState deviceState) {
		int row=sqlSession.insert("insertDeviceState", deviceState);
		return row;
	}


	public int deleteDeviceState(DeviceState deviceState) {
		int row=sqlSession.delete("deletDeviceState", deviceState);
		return row;
	}


	public SqlSession getSqlSession() {
		return sqlSession;
	}


	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	
}
