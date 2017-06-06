package ecjtu.homecoo.remoting.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecjtu.homecoo.appserver.springutil.DataType;
import ecjtu.homecoo.remoting.protocol.Message;
import ecjtu.homecoo.remoting.protocol.MessageHead;
import ecjtu.homecoo.remoting.util.BasicProcess;
/**
 * 报文解析
 * @author hsj
 *
 */
public class NettyDecoder extends ByteToMessageDecoder {
	private Logger logger = LoggerFactory.getLogger(NettyDecoder.class);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		//小字端解析数据
		while (true) { //这里使用while()主要是为了抛弃不合格的报文
			
			if (in.readableBytes() < 30) {
 				logger.error("报文读取异常，长度不够:当前长度为:"+in.readableBytes());
 				byte[] dataTestall = new byte[in.readableBytes()];
//				System.err.println("报文读取异常，长度不够:当前长度为:"+in.readableBytes()+" 异常报文: "+BasicProcess.toHexString(dataTestall));
				// return; //长度不够,直接抛弃掉这条报文
				break;
			} else {
				// 测试使用
				
				Message msg = new Message();
				byte[] dataTestall = new byte[in.readableBytes()];
				in.readBytes(dataTestall);
				logger.info("原版字节流 ：       "+ BasicProcess.toHexString(dataTestall));
//				System.out.println("原版字节流 ：       "+ BasicProcess.toHexString(dataTestall));
				in.resetReaderIndex(); // 复位一下
				in.markReaderIndex();
				byte[] header = new byte[4]; // head本身need采用的是字节数组的形式，这里为了方便测试直接用的String
				in.readBytes(header);
				String headerStr = new String(header);
				if(headerStr.equals("BBAA")){
					msg.setDataFrom(1);
					header = "AADD".getBytes();
					
				}
				//如果报头不对  丢弃
				if (!BasicProcess.toHexString(header).equals("41414444")) {
					break;
				}
				
				byte[] stamp = new byte[4];
				in.readBytes(stamp);
				int Stamp = BasicProcess.bytesToInt(stamp); // 一次读取4个字节，这里这么写和C端不匹配
				if (Stamp < 0) {
					logger.error("数据头错误Stamp,请客户端重新连接并发送");
					break;
				}
				byte[] Gateway_id = new byte[8];
				in.readBytes(Gateway_id);
				byte[] Dev_id = new byte[8];
				in.readBytes(Dev_id); 
				byte[] dev_type = new byte[2];
				in.readBytes(dev_type);
				short Dev_type = (short) BasicProcess.bytesToIntTest(dev_type);
				byte[] data_type = new byte[2];
				in.readBytes(data_type);
				// 这里byte数组获取的是两个字节的字节数组；
				short Data_type = (short) BasicProcess.bytetoint(data_type);
				if (Data_type < 1 || Data_type > 301) {
					logger.error("数据头错误Data_type，请重新连接并发送");
					break;
				}
				byte[] data_len = new byte[2]; // 数据长度
				in.readBytes(data_len);
				
				short Data_len= BasicProcess.byte2Short(data_len);		// 数据长度，由于这里只有两个字节
				if (in.readableBytes() < Data_len) {
					logger.error("数据体长度异常");
					break;
				}
				
				// 如果是加上状态戳（前4字节），解析需要先去掉这4个状态戳才是实体数据；
				byte[] body = new byte[in.readableBytes()]; // 这里不能为空
				// 从内存中读取数据(注意这里就是剩余的包了)。并且做转换
				in.readBytes(body); // 注意，这里是按照字节数来读取内容
				
				
				// java默认的是UTF-8编码的
				MessageHead msgHeader = new MessageHead();
				msgHeader.setHeader(header);
				msgHeader.setStamp(Stamp);
				msgHeader.setGateway_id(Gateway_id);
				msgHeader.setDev_id(Dev_id);
		//		System.out.println(new String(Gateway_id));
				
				msgHeader.setDev_type(Dev_type);
				msgHeader.setData_type(Data_type);
				msgHeader.setData_length(Data_len);
			
				msg.setMessageHead(msgHeader);
				msg.setBody(body);
				logger.info(String.valueOf(msg));
				in.discardReadBytes();
				out.add(msg);// 加载List
				
//				/**
//				 * update by xiaobai  2016-09-21
//				 * */
//				if (msg.getMessageHead().getData_type()==DataType.DATA_SET_SCENE_WG) {
//					System.out.println("收到网关上报情景设置报文 "+BasicProcess.toHexString(dataTestall));
//				}
//				if (msg.getMessageHead().getDev_type()==DeviceType.DEV_SENSOR_MAGLOCK) {
//					System.out.println("收到网关上报红外的报文  "+BasicProcess.toHexString(dataTestall));
//				}
				if (msg.getMessageHead().getData_type()==DataType.DATA_TUIW) {
					System.out.println("收到退网报文  "+BasicProcess.toHexString(dataTestall));
				}
				break;
			}
		}
		
	}

}
