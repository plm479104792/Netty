package ecjtu.homecoo.remoting.protocol;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

import ecjtu.homecoo.remoting.util.BasicProcess;


public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private MessageHead messageHead;
	/**
	 * 1来app ,2-来自网关
	 */
	private transient int dataFrom = 2;
	public int getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(int dataFrom) {
		this.dataFrom = dataFrom;
	}
	private static AtomicInteger RequestId = new AtomicInteger(0);
	/**
	 * 包体
	 */
	private byte[] body;
	
	private int opaque = RequestId.getAndIncrement();
	public int getOpaque() {
		return opaque;
	}

	public void setOpaque(int opaque) {
		this.opaque = opaque;
	}

	public Message(){
		
	}
	

	public ByteBuffer encodeHeader(){
	ByteBuffer byteBuffer = ByteBuffer.allocate(30);
		
		byte[] header = messageHead.getHeader();
		int stamp = messageHead.getStamp();
		byte[] gateway_id = messageHead.getGateway_id();
		byte[] dev_id = messageHead.getDev_id();
		short dev_type = (short)messageHead.getDev_type();
		byte[] Dev_type = BasicProcess.shortToByteArray2(dev_type);
		short data_type = (short)messageHead.getData_type();
		byte[] Data_type = BasicProcess.shortToByteArray2(data_type);
		byteBuffer.put(header);
		byte[] Stamp = new byte[4];
		Stamp = BasicProcess.intToByte(stamp);
		byteBuffer.put(Stamp);
		byteBuffer.put(gateway_id);
		byteBuffer.put(dev_id);
		byteBuffer.put(Dev_type);
		byteBuffer.put(Data_type);
		short length = (short)(body != null ? body.length:0);
		byteBuffer.putShort(length);
		return byteBuffer;
	}
	
	public Message(MessageHead head,  byte[] body){
		this.messageHead = head;
		this.body = body;
	}
	public MessageHead getMessageHead() {
		return messageHead;
	}
	public void setMessageHead(MessageHead messageHead) {
		this.messageHead = messageHead;
	}
	public byte[] getBody() {
		return body;
	}
	public void setBody(byte[] body) {
		this.body = body;
	}

}
