package ecjtu.homecoo.appserver.springutil;



import ecjtu.homecoo.appserver.domain.Device;
import ecjtu.homecoo.appserver.domain.DeviceType;
import ecjtu.homecoo.remoting.protocol.Message;
import ecjtu.homecoo.remoting.protocol.MessageHead;
import ecjtu.homecoo.remoting.util.BasicProcess;

/**
 * 报文分拆工具类，得到报文中想到的数据
 * */
public class GetMessageBody {
	
	/**
	 * 得到报文中的设备ID  deviceNo
	 * */
	public static String GetDeviceNo(Message message){
		byte[] deviceno=message.getMessageHead().getDev_id();
		String deviceNo = BasicProcess.toHexString(deviceno);
		return deviceNo;
	}

	/**
	 * 得到报文的网关编号  gatewayNo
	 * */
	public static String GetGatewayNo(Message message){
		byte[] gatewayNo=message.getMessageHead().getGateway_id();
		String gatewayId=BasicProcess.toHexString(gatewayNo);
		return gatewayId;
	}
	
	/**
	 * 得到报文中的设备类型  String类型的设备类型   如: 3路开关  0300
	 * */
	public static String GetDeviceTypeId(Message message){
		short dev_type=message.getMessageHead().getDev_type();			//设备类型
		byte[] device_type=BasicProcess.shortToByteArray2(dev_type);
		String devtypeId=BasicProcess.toHexString(device_type);	
		return devtypeId;
	}
	
	/**
	 * 得到报文中的设备类型String类型的设备类型 如:3路开关 3
	 * */
	public static String GetDeviceTypeIdShort(Message message){
		short dev_type=message.getMessageHead().getDev_type();			//设备类型
		String deviceNo=String.valueOf(dev_type);
		return deviceNo;
	}
	
	/**
	 * 得到报文中设备的设备状态
	 * */
	public static String GetDeviceState(Message message){
		byte[] body=message.getBody();	
		String data=BasicProcess.toHexString(body);
		return data;
	}
	
	
	
	
	/**
	 * 得到device
	 * */
	public static Device GetDevice(Message message,DeviceType deviceType){
		Device device=new Device();
		device.setDeviceNo(GetMessageBody.GetDeviceNo(message));
		device.setDeviceName(deviceType.getDeviceTypeName());
		device.setDeviceTypeId(deviceType.getDeviceTypeId());
		device.setDeviceTypeName(deviceType.getDeviceTypeName());
		device.setDeviceCategoryId(deviceType.getDeviceCategoryId());
		device.setDeviceCategoryName(deviceType.getDeviceCategoryName());
		device.setSpaceTypeId("0");   //新设备上来默认空间不选择
		device.setSpaceNo("0");		//新设备上来 默认spaceno=0
		device.setGatewayNo(GetMessageBody.GetGatewayNo(message));
		return device;
		
	}
	
	/**
	 * 获取情景报文的
	 * */
	public static Message themeMsg(String gatewayNo){
		Message msg=new Message();
		MessageHead head=new MessageHead();
		String header="41414444";
		byte[] heade=BasicProcess.toByteArray(header);
		head.setHeader(heade);
		head.setStamp(00000000);
		head.setGateway_id(BasicProcess.toByteArray(gatewayNo));
		head.setDev_id(BasicProcess.toByteArray("3030303030303030"));
		head.setDev_type((short)50);
		head.setData_type(DataType.DATA_REQ_SCENE);
		head.setData_length((short)8);
		msg.setMessageHead(head);
		String data="3838383838383838";
		byte[] body=BasicProcess.toByteArray(data);
		msg.setBody(body);
		return msg;
	}
	
	
	
}
