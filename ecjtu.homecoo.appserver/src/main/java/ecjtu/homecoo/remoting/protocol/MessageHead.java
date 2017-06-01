package ecjtu.homecoo.remoting.protocol;


public class MessageHead {
	/**
	 * 消息头包装类
	 */
	private byte[] header = new byte[4]; // 4byte byte[] 报文开始头
	private int stamp; // 4byte int 时间戳
	private byte[] gateway_id = new byte[8]; // 8byte byte[] 网关ID
	private byte[] dev_id = new byte[8]; // 8byte byte[] 设备ID
	private short dev_type; // 2byte short 设备类型
	private short data_type; // 2byte short 数据类型
	private short data_length; // 2byte short 数据长度

	public int getStamp() {
		return stamp;
	}

	public void setStamp(int stamp) {
		this.stamp = stamp;
	}

	public byte[] getGateway_id() {
		return gateway_id;
	}

	public void setGateway_id(byte[] gatewayId) {
		gateway_id = gatewayId;
	}

	public void setDev_id(byte[] devId) {
		dev_id = devId;
	}

	public byte[] getDev_id() {
		return dev_id;
	}

	public short getDev_type() {
		return dev_type;
	}

	public void setDev_type(short devType) {
		dev_type = devType;
	}

	public short getData_type() {
		return data_type;
	}

	public void setData_type(short dataType) {
		data_type = dataType;
	}

	public byte[] getHeader() {
		return header;
	}

	public void setHeader(byte[] header) {
		this.header = header;
	}

	public short getData_length() {
		return data_length;
	}

	public void setData_length(short dataLength) {
		data_length = dataLength;
	}
	
}
