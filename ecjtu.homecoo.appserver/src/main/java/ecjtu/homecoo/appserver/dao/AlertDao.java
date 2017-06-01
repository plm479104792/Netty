package ecjtu.homecoo.appserver.dao;

import java.util.List;

import ecjtu.homecoo.appserver.domain.Alert;

public interface AlertDao {

	int insertAlert(Alert alert);	
	
	int updateAlert(Alert alert);
	
	int deleteAlertBygatewayNo(String gatewayNo);

	int deleteAlertByDeviceNo(String deviceNo);
	
	List<Alert> selectAlertsByGatewayNo(String gatewayNo);
	
	
}
