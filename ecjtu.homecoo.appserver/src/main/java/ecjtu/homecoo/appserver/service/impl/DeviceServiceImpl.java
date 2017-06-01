package ecjtu.homecoo.appserver.service.impl;


import ecjtu.homecoo.appserver.dao.DeviceDao;
import ecjtu.homecoo.appserver.dao.DeviceTypeDao;
import ecjtu.homecoo.appserver.domain.Device;
import ecjtu.homecoo.appserver.domain.DeviceType;
import ecjtu.homecoo.appserver.service.DeviceService;
import ecjtu.homecoo.appserver.springutil.GetMessageBody;
import ecjtu.homecoo.remoting.protocol.Message;

public class DeviceServiceImpl implements DeviceService{

	private DeviceDao deviceDao;
	private DeviceTypeDao deviceTypeDao;
	
	@Override
	public int deleteDevcieByDeviceNo(Message message) {
		String deviceNo=GetMessageBody.GetDeviceNo(message);
		int row=deviceDao.deleteDeviceByDeviceNo(deviceNo);
		return row;
	}

	@Override
	public int SaveOrupdateDeviceByDeviceNo(Message message) {
		String deviceNo=GetMessageBody.GetDeviceNo(message);
		boolean b=deviceDao.existsDeviceByDeviceNo(deviceNo);
		int row=0;
		if (b) {
			Device device=deviceDao.getDeviceByDeviceNo(deviceNo);
			device.setGatewayNo(GetMessageBody.GetGatewayNo(message));
			row=deviceDao.updateDeviceByDeviceNo(device);
		}else{
			DeviceType deviceType=deviceTypeDao.selectByPrimaryKey(GetMessageBody.GetDeviceTypeIdShort(message));
			Device device=GetMessageBody.GetDevice(message, deviceType);
			row=deviceDao.addDevice(device);
		}
		
		return row;
	}

	public DeviceDao getDeviceDao() {
		return deviceDao;
	}

	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	public DeviceTypeDao getDeviceTypeDao() {
		return deviceTypeDao;
	}

	public void setDeviceTypeDao(DeviceTypeDao deviceTypeDao) {
		this.deviceTypeDao = deviceTypeDao;
	}
	
}
