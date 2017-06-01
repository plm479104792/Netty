package ecjtu.homecoo.appserver.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ecjtu.homecoo.appserver.dao.AlertDao;
import ecjtu.homecoo.appserver.domain.Alert;

public class AlertDaoImpl implements AlertDao{

	private SqlSession sqlSession;
	
	public int insertAlert(Alert alert) {
		int row=sqlSession.insert("AlertMapper.insertAlert", alert);
		return row;
	}

	@Override
	public int updateAlert(Alert alert) {
		int row=sqlSession.update("AlertMapper.updateAlert", alert);
		return row;
	}

	@Override
	public int deleteAlertBygatewayNo(String gatewayNo) {
		int row=sqlSession.delete("AlertMapper.deleteAlertBygatewayNo", gatewayNo);
		return row;
	}

	@Override
	public int deleteAlertByDeviceNo(String deviceNo) {
		int row=sqlSession.delete("AlertMapper.deleteAlertByDeviceNo", deviceNo);
		return row;
	}

	@Override
	public List<Alert> selectAlertsByGatewayNo(String gatewayNo) {
		List<Alert> list=sqlSession.selectList("AlertMapper.selectAlertsByGatewayNo", gatewayNo);
		return list;
	}

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	
}
