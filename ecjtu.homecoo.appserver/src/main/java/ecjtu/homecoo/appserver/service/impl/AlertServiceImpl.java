package ecjtu.homecoo.appserver.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ecjtu.homecoo.appserver.dao.AlertDao;
import ecjtu.homecoo.appserver.domain.Alert;
import ecjtu.homecoo.appserver.service.AlertService;


public class AlertServiceImpl implements AlertService{
	
	private AlertDao alertDao;

	@Override
	public int insertAlert(Alert alert) {
		int row=alertDao.insertAlert(alert);
		return row;
	}

	@Override
	public int updateAlert(Alert alert) {
		int row=alertDao.updateAlert(alert);
		return row;
	}

	@Override
	public int deleteAlertBygatewayNo(String gatewayNo) {
		int row=alertDao.deleteAlertBygatewayNo(gatewayNo);
		return row;
	}

	@Override
	public int deleteAlertByDeviceNo(String deviceNo) {
		int row=alertDao.deleteAlertByDeviceNo(deviceNo);
		return row;
	}

	@Override
	public List<Alert> selectAlertsByGatewayNo(String gatewayNo) {
		List<Alert> alertList=alertDao.selectAlertsByGatewayNo(gatewayNo);
		return alertList;
	}

	public AlertDao getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(AlertDao alertDao) {
		this.alertDao = alertDao;
	}
	
}
