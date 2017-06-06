package ecjtu.homecoo.appserver.processor.app;

import io.netty.channel.ChannelHandlerContext;


import ecjtu.homecoo.appserver.channel.ChannelManager;
import ecjtu.homecoo.appserver.processor.AbstractUpdatProcesser;
import ecjtu.homecoo.remoting.protocol.Message;
import ecjtu.homecoo.remoting.util.BasicProcess;

public class AppProcesser extends AbstractUpdatProcesser {

	public AppProcesser(ChannelManager connectionManager) {
		super(connectionManager);
		// TODO Auto-generated constructor stub
	}

	public Message processRequest(ChannelHandlerContext ctx, Message request)
			throws Exception {
		//这个是处理app请求的处理类,具体的业务逻辑就在这里写
		System.out.println("app单个设备控制报文在这里处理");
		String gateWayid = BasicProcess.toHexString(request.getMessageHead().getGateway_id());
//		byte[] dev_id=request.getMessageHead().getDev_id();
//		String device_id=BasicProcess.toHexString(dev_id);
//		System.out.println(request.getMessageHead().getData_type()+"	"+gateWayid);
		connectionManager.sendMessage(gateWayid, request);
//		System.out.println("====================================================="+device_id);
		return null;
	}

}
