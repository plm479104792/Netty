package ecjtu.homecoo.appserver.service;

import ecjtu.homecoo.remoting.protocol.Message;

public interface DeviceService {

		int deleteDevcieByDeviceNo(Message message);
		
		int SaveOrupdateDeviceByDeviceNo(Message message);
		
}
