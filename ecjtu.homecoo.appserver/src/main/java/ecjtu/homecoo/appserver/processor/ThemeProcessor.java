package ecjtu.homecoo.appserver.processor;

import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import ecjtu.homecoo.appserver.channel.ChannelManager;
import ecjtu.homecoo.appserver.service.DeviceStateService;
import ecjtu.homecoo.appserver.springutil.DataType;
import ecjtu.homecoo.appserver.springutil.SpringUtil;
import ecjtu.homecoo.remoting.protocol.Message;
import ecjtu.homecoo.remoting.util.BasicProcess;

public class ThemeProcessor extends DefaultProcessor {

	public ThemeProcessor(ChannelManager connectionManager) {
		super(connectionManager);
		// TODO Auto-generated constructor stub
	}
	private static Logger logger = Logger.getLogger(ThemeProcessor.class);
	DeviceStateService deviceStateService = SpringUtil.getObject(DeviceStateService.class);
	@Override
	public Message processRequest(ChannelHandlerContext ctx, Message message)
			throws Exception {
		switch (message.getMessageHead().getData_type()) {
		case DataType.DATA_REQ_SYNC:
			// 收到网关的 21(同步请求报文)，查看数据库里面有没有该设备，有：更新设备状态；没有，直接插入设备 最后回复一个
			// 61(同步确认报文)
			int row = deviceStateService.selectDeviceState(message);
			short device=message.getMessageHead().getDev_type();
			if (row > 0) {
				deviceStateService.UpdateDeviceState(message);
			} else {
				deviceStateService.insertDeviceState(message);
			}
  //判断是不是报警类设备  是情景报警类设备:获取到设备状态，如果是64(报警状态)，发送消息推送给手机APP;如果不是报警状态，直接下发同步确认报文到网关 ; 不是报警类设备:直接下发一个同步确认报文给网关
			if (device>=110 && device<200) {
				byte[] body=message.getBody();
				String bodyString=BasicProcess.toHexString(body);
				String alertState=bodyString.substring(8);
				// 00 64  设备状态表示触发报警
				if (alertState.equals("0064")) {
					logger.info("发送报警信息到手机APP JPush");
				}
			}
			message.getMessageHead().setData_type(DataType.DATA_ACK_SYNC);
			logger.info("回复网关  同步确认报文(61)");
			ctx.writeAndFlush(message);
			break;
		default:
			ctx.fireChannelRead(message);
			break;
		}
		return null;
	}
}
