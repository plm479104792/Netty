package ecjtu.homecoo.appserver.service;



import ecjtu.homecoo.appserver.domain.DeviceState;
import ecjtu.homecoo.remoting.protocol.Message;

/**
 * @author Administrator
 *
 */
public interface DeviceStateService {
	 public int UpdateDeviceState(Message message);
	 
	 public int selectDeviceState(Message message);
	 
	 public DeviceState selectDeviceStateBYdeviceiD(Message message);

	 public int insertDeviceState(Message message);
	 
	 public int deleteDeviceState(Message message);

}
