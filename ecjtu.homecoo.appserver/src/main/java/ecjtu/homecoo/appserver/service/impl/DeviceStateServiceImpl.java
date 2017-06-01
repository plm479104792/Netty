package ecjtu.homecoo.appserver.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ecjtu.homecoo.appserver.dao.DeviceStateDao;
import ecjtu.homecoo.appserver.domain.DeviceState;
import ecjtu.homecoo.appserver.service.DeviceStateService;
import ecjtu.homecoo.remoting.protocol.Message;
import ecjtu.homecoo.remoting.protocol.MessageHead;
import ecjtu.homecoo.remoting.util.BasicProcess;
public class DeviceStateServiceImpl implements DeviceStateService{

	private DeviceStateDao deviceStateDao;
	public int UpdateDeviceState(Message message) {
		MessageHead head=message.getMessageHead();
		DeviceState record=new DeviceState();
		byte[] body=message.getBody();
		String bodyString = BasicProcess.toHexString(body);
		String data=bodyString.substring(8);
		record.setDeviceState(data);
		record.setDeviceNo(BasicProcess.toHexString(head.getDev_id()));
		record.setLastUpdateTime(new Date());
		int row=deviceStateDao.updateDeviceState(record);
		return row;
	}
	
	public int selectDeviceState(Message message) {
		DeviceState deviceState=new DeviceState();
		byte[] device_id=message.getMessageHead().getDev_id();
		String deviceNo = BasicProcess.toHexString(device_id);
		deviceState.setDeviceNo(deviceNo);
		int row= deviceStateDao.selectDeviceState(deviceState);
		return row;
	}
	
	public DeviceState selectDeviceStateBYdeviceiD(Message message) {
		DeviceState deviceState=new DeviceState();
		byte[] device_id=message.getMessageHead().getDev_id();
		String deviceNo = BasicProcess.toHexString(device_id);
		deviceState.setDeviceNo(deviceNo);
		List<DeviceState> row= deviceStateDao.selectDeviceStateList(deviceState);
		if (row.size()>0) {
			return row.get(0);
		}else{
			return null;
		}
	}

	public int insertDeviceState(Message message) {

		MessageHead head=message.getMessageHead();
		DeviceState record=new DeviceState();
		byte[] body=message.getBody();
		String bodyString = BasicProcess.toHexString(body);
		String data=bodyString.substring(8);
		record.setDeviceState(data);
		record.setDeviceNo(BasicProcess.toHexString(head.getDev_id()));
		record.setLastUpdateTime(new Date());
		int row=deviceStateDao.insertDeviceState(record);
		return row;
	}

	public int deleteDeviceState(Message message) {
		DeviceState deviceState=new DeviceState();
		byte[] deviceNo=message.getMessageHead().getDev_id();
		String deviceno = BasicProcess.toHexString(deviceNo);
		deviceState.setDeviceNo(deviceno);
		int row=deviceStateDao.deleteDeviceState(deviceState);
		return row;
	}

	public DeviceStateDao getDeviceStateDao() {
		return deviceStateDao;
	}

	public void setDeviceStateDao(DeviceStateDao deviceStateDao) {
		this.deviceStateDao = deviceStateDao;
	}
	

}
