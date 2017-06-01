package ecjtu.homecoo.appserver.dao;

import ecjtu.homecoo.appserver.domain.Device;

public interface DeviceDao {
	
	int deleteDeviceByDeviceNo(String deviceNo);
	
	boolean existsDeviceByDeviceNo(String deviceNo);
	
	int updateDeviceByDeviceNo(Device device);
	
	int addDevice(Device device);

	Device getDeviceByDeviceNo(String deviceNo);
}
