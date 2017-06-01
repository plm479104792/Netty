package ecjtu.homecoo.appserver.dao;

import java.util.List;

import ecjtu.homecoo.appserver.domain.DeviceState;

public interface DeviceStateDao {
	public int updateDeviceState(DeviceState deviceState);
	
	public int selectDeviceState(DeviceState deviceState);
	
	public List<DeviceState> selectDeviceStateList(DeviceState deviceState);
	
	public int insertDeviceState(DeviceState deviceState);
	
	public int deleteDeviceState(DeviceState deviceState);
}
