package ecjtu.homecoo.appserver.service;

import java.util.List;

import ecjtu.homecoo.appserver.domain.Alert;

/**
 * @author xiaobai
 * */
public interface AlertService {
	
	int insertAlert(Alert alert);
	
	int updateAlert(Alert alert);

	int deleteAlertBygatewayNo(String gatewayNo);

	int deleteAlertByDeviceNo(String deviceNo);
	
	List<Alert> selectAlertsByGatewayNo(String gatewayNo);
	

}
