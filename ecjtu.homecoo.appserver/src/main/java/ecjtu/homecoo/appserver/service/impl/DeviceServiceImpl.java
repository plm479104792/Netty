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
		Device device=new Device();
		device.setDeviceNo(GetMessageBody.GetDeviceNo(message));
		device.setGatewayNo(GetMessageBody.GetGatewayNo(message));
		
		//这里不更新设备状态是因为设备表device里面没有设置状态这个字段，保存在devicestaterecord表里
		// device表只用于手机端从服务器获取设备
		boolean b=deviceDao.existsDeviceByDeviceNo(device);
		int row=0;
		if (!b) {
			DeviceType deviceType=deviceTypeDao.selectByPrimaryKey(GetMessageBody.GetDeviceTypeIdShort(message));
			Device device1=GetMessageBody.GetDevice(message, deviceType);
			row=deviceDao.addDevice(device1);
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
