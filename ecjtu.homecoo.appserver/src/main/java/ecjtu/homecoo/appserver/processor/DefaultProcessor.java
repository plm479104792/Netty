package ecjtu.homecoo.appserver.processor;


import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import ecjtu.homecoo.appserver.channel.ChannelManager;
import ecjtu.homecoo.appserver.client.ClientChannelInfo;
import ecjtu.homecoo.appserver.domain.Alert;
import ecjtu.homecoo.appserver.domain.DeviceState;
import ecjtu.homecoo.appserver.domain.Jpush;
import ecjtu.homecoo.appserver.domain.MusicOrder;
import ecjtu.homecoo.appserver.domain.Packet;
import ecjtu.homecoo.appserver.domain.ThemeMusic;
import ecjtu.homecoo.appserver.service.AlertService;
import ecjtu.homecoo.appserver.service.DeviceService;
import ecjtu.homecoo.appserver.service.DeviceStateService;
import ecjtu.homecoo.appserver.service.PacketService;
import ecjtu.homecoo.appserver.service.ThemeMusicServie;
import ecjtu.homecoo.appserver.springutil.Constant;
import ecjtu.homecoo.appserver.springutil.DataType;
import ecjtu.homecoo.appserver.springutil.DeviceMessageToPacket;
import ecjtu.homecoo.appserver.springutil.GetMessageBody;
import ecjtu.homecoo.appserver.springutil.JPushimpl;
import ecjtu.homecoo.appserver.springutil.MessageToAlert;
import ecjtu.homecoo.appserver.springutil.MessageToPacket;
import ecjtu.homecoo.appserver.springutil.MusicUtil;
import ecjtu.homecoo.appserver.springutil.NetTime;
import ecjtu.homecoo.appserver.springutil.SpringUtil;
import ecjtu.homecoo.appserver.springutil.ThemeMusicToJpush;
import ecjtu.homecoo.remoting.netty.NettyRequestProcessor;
import ecjtu.homecoo.remoting.protocol.Message;
import ecjtu.homecoo.remoting.util.BasicProcess;
public class DefaultProcessor extends AbstractUpdatProcesser implements NettyRequestProcessor{
	public DefaultProcessor() {
	}
	public DefaultProcessor(ChannelManager connectionManager) {
		super(connectionManager);
	}
	private AlertService alertService;
	private PacketService packetService;
	private ThemeMusicServie themeMusicServie;
	private DeviceStateService deviceStateService;
	private DeviceService deviceService;

	private static Logger logger = Logger.getLogger(DefaultProcessor.class);

	public Message processRequest(ChannelHandlerContext ctx, Message message)
			throws Exception {
		AlertService alertService=SpringUtil.getApplicationContext().getBean(AlertService.class);
		DeviceStateService deviceStateService=SpringUtil.getApplicationContext().getBean(DeviceStateService.class);
		PacketService packetService=SpringUtil.getApplicationContext().getBean(PacketService.class);
		ThemeMusicServie themeMusicServie=SpringUtil.getApplicationContext().getBean(ThemeMusicServie.class);
		DeviceService deviceService=SpringUtil.getApplicationContext().getBean(DeviceService.class);
		
		JPushimpl jPushimpl=new JPushimpl();
		Jpush jpush=new Jpush();
		int record=0;
		// message 网关上发的报文 对象
		switch (message.getMessageHead().getData_type()) {

		case DataType.DATA_REQ_AUTH_GW:
			// 返回一个62(网关认证确认报文)
			message.getMessageHead().setData_type(DataType.DATA_ACK_AUTH_GW);
			message.setBody(message.getMessageHead().getGateway_id());
			logger.info(" 回复网关  请求确认报文(62) ");
			ctx.writeAndFlush(message);
			
			/**
			 * update by xiaobai 2019-09-21 查看网关是否能回复情景设置 
			 * 每次有新网关连接netty的时候，netty回复请求确认报文的时候，主动拉一次网关中的情景设置
			 * */
			String gatewayNo1=GetMessageBody.GetGatewayNo(message);
			Message msg=GetMessageBody.themeMsg(gatewayNo1);
//			DataType.DATA_MAP.put(gatewayNo1,(short)0);
			logger.info("服务器主动获取网关情景");
			ctx.writeAndFlush(msg);
			break;

		case DataType.DATA_REQ_PULSE:
			// 26 心跳请求报文 不做任何处理，返回一个心跳确认报文
			message = heartBeat(ctx, message);
			
//			logger.info("回复网关  心跳确认报文(66) ");
			message.getMessageHead().setData_type(DataType.DATA_ACK_PULSE);
			ctx.writeAndFlush(message);
//			System.err.println("目前与netty保存长连接的客户端数目:"+connectionManager.getAllChannel().size());
			logger.info("目前与netty保存长连接的客户端数目:"+connectionManager.getAllChannel().size());
//			/**
//			 * update by xiaobai 2019-09-21 查看网关是否能回复情景设置
//			 * */
//			String gatewayNo1=GetMessageBody.GetGatewayNo(message);
//			Message msg=GetMessageBody.themeMsg(gatewayNo1);
//			ctx.writeAndFlush(msg);
			
			break;
		case DataType.DATA_REQ_STAMP:
			// 65(跟踪确认报文) WG时间戳跟踪报文，case DATA_REQ_STAMP:
			// GW重启或者其他操作导致设备需求其在GW中的最新状态，通过Stamp时间戳来判别
			message.setBody(BasicProcess.toByteArray("00000000"));
			message.getMessageHead().setData_type(DataType.DATA_ACK_STAMP);
			logger.info("回复网关   跟踪确认报文(65)");
			ctx.writeAndFlush(message);
			break;
			
		case DataType.DATA_REQ_SYNC:
			// 收到网关的 21(同步请求报文)，查看数据库里面有没有该设备，有：更新设备状态；没有，直接插入设备 最后回复一个   61(同步确认报文)
			short device=0;
			try {
			record= deviceStateService.selectDeviceState(message);
			device=message.getMessageHead().getDev_type();
			deviceService.SaveOrupdateDeviceByDeviceNo(message);
			if (record > 0) {
				deviceStateService.UpdateDeviceState(message);
			} else {
				deviceStateService.insertDeviceState(message);
			}
			String bodyString = BasicProcess.toHexString(message.getBody()).substring(8);
			byte[] bodyString1=BasicProcess.StringtoByteArray(bodyString);
			message.setBody(bodyString1);
			//  发送到 手机的报文 需要先获取数据长度-4(状态戳)   
			short data_len=message.getMessageHead().getData_length();
			message.getMessageHead().setData_length((short)(data_len-4));
			message.getMessageHead().setData_type(DataType.DATA_STAT);
			Long time=NetTime.getNetTime();
			if (device>=110 && device<200) {
				// 00 64  设备状态表示触发报警
				if (bodyString.equals(Constant.AlertState)) {
					Alert alert=MessageToAlert.MessageToAlert(message, time);
					alertService.insertAlert(alert);
				}else if (bodyString.equals(Constant.AlertOff)) {
					//每次收到撤防的设备状态就JPush暂停音乐
//					AlertOffPauseMusic(message);
				}
			}else{
				jpush.setMesssageType(1);
			}
			message.getMessageHead().setData_length(data_len);
			message.getMessageHead().setData_type(DataType.DATA_ACK_SYNC);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
//			if ( !(device>=110 && device<200)) {
//			logger.info("回复网关  同步确认报文(61)");
			ctx.writeAndFlush(message);
//			}
			break;
			
		case DataType.DATA_CHANGE_GW:
			//网关更新设备状态  不需要回复网关  只用于硬件设备情景面板的情景音乐
			short device1=0;
			try {
			DeviceState state=deviceStateService.selectDeviceStateBYdeviceiD(message);
			record= deviceStateService.selectDeviceState(message);
			String deviceNo=BasicProcess.toHexString(message.getMessageHead().getDev_id());
			String gatewayNo=GetMessageBody.GetGatewayNo(message);
			device1=message.getMessageHead().getDev_type();
			deviceService.SaveOrupdateDeviceByDeviceNo(message);
			if (record > 0) {
				deviceStateService.UpdateDeviceState(message);
			} else {
				deviceStateService.insertDeviceState(message);
			}
			String bodyString = BasicProcess.toHexString(message.getBody()).substring(8);
			byte[] bodyString1=BasicProcess.StringtoByteArray(bodyString);
			message.setBody(bodyString1);
			//  发送到 手机的报文 需要先获取数据长度-4(状态戳)   
			short data_len=message.getMessageHead().getData_length();
			message.getMessageHead().setData_length((short)(data_len-4));
			message.getMessageHead().setData_type(DataType.DATA_STAT);
			Packet packet=DeviceMessageToPacket.ToPacket(message);
			jpush.setObject(JSONObject.toJSONString(packet));
			jpush.setGatewayNo(gatewayNo);
			Long time=NetTime.getNetTime();
			jpush.setPacket(packet);
			jpush.setTime(time);
			if (device1>=110 && device1<200) {
				//推送自定义消息
				jpush.setMesssageType(2);
				jPushimpl.sendPush(jpush);
				// 00 64  设备状态表示触发报警
				if (bodyString.equals(Constant.AlertState)) {
					Alert alert=MessageToAlert.MessageToAlert(message, time);
					alertService.insertAlert(alert);
					jPushimpl.SendNotification(device1, gatewayNo);
					SendLinkageMusic(deviceNo, state);
				}else if (bodyString.equals(Constant.AlertOff)) {
					//每次收到撤防的设备状态就JPush暂停音乐
//					AlertOffPauseMusic(message);
				}
			}else{
				jpush.setMesssageType(1);
				jPushimpl.sendPush(jpush);
			}
			if (device1==202) {				
				//判断硬件情景面板上的设备状态
				String deviceState=BasicProcess.wgDataToString(bodyString1);   //deviceState为报文穿过来的设置状态
				System.out.println(device1+"   网关上报设备状态     "+bodyString+"  数据库查询的设备状态  "+deviceState+" "+deviceState.substring(0, 4));
//				deviceState=deviceState.substring(0, 4);
				if ( state==null || !state.getDeviceState().equals(deviceState)) {   //state数据库里之前的设备  
					SendThemeMusicJpush(deviceNo, deviceState);
				}
			}
			message.getMessageHead().setData_length(data_len);
			message.getMessageHead().setData_type(DataType.DATA_ACK_SYNC);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case DataType.DATA_TUIW:
			// 收到网关的 12(设备退网报文) 去devicestate表中删除该设备 不用返回报文给网关
			try {
				deviceStateService.deleteDeviceState(message);
				deviceService.deleteDevcieByDeviceNo(message);
				logger.info("设备"+message.getMessageHead().getDev_type()+"退网成功！");
			} catch (Exception e) {
				e.printStackTrace();
			}
			//退网报文需要Jpush到手机
			String gatewayNo=GetMessageBody.GetGatewayNo(message);
			Packet packet=DeviceMessageToPacket.ToPacket(message);
			jpush.setGatewayNo(gatewayNo);
			jpush.setMesssageType(1);
			jpush.setObject(JSONObject.toJSONString(packet));
			jpush.setPacket(packet);
			jpush.setTime(0L);
			jPushimpl.sendPush(jpush);
			break;

		case DataType.DATA_REQ_AUTH_DEV:
			//收到网关的 23(设备认证请求报文) 网关中的设备状态应该是最新的，收到之后直接在数据库中查询，判断是否存在，有：更新设备，没有：添加设备  
			//  并返回   (63)设备认证确认报文
			record=deviceStateService.selectDeviceState(message);
			if (record>0) {
				deviceStateService.UpdateDeviceState(message);
			}else{
				deviceStateService.insertDeviceState(message);
			}
			message.getMessageHead().setData_type(DataType.DATA_ACK_AUTH_DEV);
			byte [] ACK_DEV = new byte[1];  //验证成功，返回1否则0;
			ACK_DEV[0]=01;
			message.setBody(ACK_DEV);
			logger.info("回复网关  设备认证确认报文(63) ");
			ctx.writeAndFlush(message);
			break;
			
		case DataType.DATA_DELETE_SCENE:
			//收到  (18)网关删除情景报文   (删除相应的情景之后不需要回复报文给网关)
			packetService.deletePacket(message);
//			message.getMessageHead().setData_type(DataType.DATA_OP_SCE);
			String gateWayno=BasicProcess.toHexString(message.getMessageHead().getGateway_id());
			connectionManager.sendMessage(gateWayno, message);
//			System.err.println("删除情景报文："+gateWayno);
			break;
			
		case DataType.DATA_SET_SCENE:
			//收到 (15)网关设置情景报文，(先判断数据库是否有该报文，有：更新;没有:添加该报文)
			int a=message.getDataFrom();
			try {
			record=packetService.selectPacket(message);
			if (record>0) {
				packetService.updatePacket(message);
			}else{
				packetService.insertPacket(message);
			}
			} catch (Exception e) {
			}
			if (a==1) {
				String gateWayno1=BasicProcess.toHexString(message.getMessageHead().getGateway_id());
				connectionManager.sendMessage(gateWayno1, message);
			}else{
				message.getMessageHead().setData_type(DataType.DATA_OP_SCE);
				ctx.writeAndFlush(message);
			}
			break;
			
		case DataType.DATA_SET_SCENE_WG:
			//底层上发报文   收到情景报文JPush推送到手机上
			try {
			record=packetService.selectPacket(message);
			if (record>0) {
//				System.err.println("添加情景");
				packetService.updatePacket(message);
			}else{   
//				System.err.println("更新情景");
				packetService.insertPacket(message);
			}
//			String gatewayString=GetMessageBody.GetGatewayNo(message);
//			short flag=DataType.DATA_MAP.get(gatewayString);
//			if (flag==0) {
//				packetService.deletePacketByGatewayNo(message);
//				packetService.insertPacket(message);
//				DataType.DATA_MAP.put(gatewayString, (short) 1);
//			}else if (flag==1) {
//				packetService.insertPacket(message);
//			}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
			
		case DataType.DATA_CTRL_SCENE:
			int b=message.getDataFrom();
			if (b==1) {
				String gateWayno2=BasicProcess.toHexString(message.getMessageHead().getGateway_id());
				String MessageBody=BasicProcess.toHexString(message.getBody());
				String themeNo="";
				if (MessageBody.length()>16) {
					themeNo=MessageBody.substring(0, 16);
					System.out.println("themeNo:"+themeNo);
				}
				connectionManager.sendMessage(gateWayno2, message);
				logger.info("下发 情景控制报文");
				/**
				 * @Description:这里收到手机APP下发的景控制报文，判断该情景是否有情景音乐,如果该情景设置了情景音乐。JPUSH推送情景音乐
				 * @Date:2016-05-29
				 * @Test:OK
				 * */
				ThemeMusic themeMusic=themeMusicServie.getThemeMusicByGatewayNoAndThemeNo(gateWayno2, themeNo);
				jpush=ThemeMusicToJpush.ToJpush(themeMusic);
				jPushimpl.sendPush(jpush);
				
			}else{
				//默认网关不会给我发送情景控制报文 
			}
			break;
			
		case DataType.DATA_OP_SCE:
			//情景确认报文      服务器收到之后  JPush推送到手机上
			Packet packet2=MessageToPacket.ToPacket(message);
			jpush.setMesssageType(4);
			jpush.setGatewayNo(packet2.getGatewayNo());
			jpush.setObject(JSONObject.toJSONString(packet2));
			jpush.setPacket(packet2);
			jPushimpl.sendPush(jpush);
			logger.info("JPUSH 情景确认报文");
			break;
			
		case DataType.DATA_FINISH_SCENE:
//			System.out.println("q情景完成报文下发");
			String gateWayno3=BasicProcess.toHexString(message.getMessageHead().getGateway_id());
			connectionManager.sendMessage(gateWayno3, message);
			break;
			
//		case DataType.DATA_REQ_SCENE:
//			String gateWayno4=BasicProcess.toHexString(message.getMessageHead().getGateway_id());
//			connectionManager.sendMessage(gateWayno4, message);
//			break;
			
		default:
			ctx.fireChannelRead(message);   //通知下一个handler处理该消息
			break;   
		}
		return null;
		
	}
	
	/**
	 * 查询情景音乐 并JPush到七寸屏上
	 * */
	public void SendThemeMusicJpush(String deviceNo,String deviceState){
		ThemeMusicServie themeMusicServie=SpringUtil.getApplicationContext().getBean(ThemeMusicServie.class);
		ThemeMusic themeMusic=themeMusicServie.getThemeMusicByDeviceNoAndDeviceState(deviceNo, deviceState);
		//这里需要做个判断  如果themeMusic  为空。下面的不需要进行了  如果不用try cacth的话。刚好屏蔽我这个问题
			if (themeMusic!=null) {
				Jpush jpush=new Jpush();
				JPushimpl jPushimpl=new JPushimpl();
				jpush=ThemeMusicToJpush.ToJpush(themeMusic);
//				System.out.println("JPush 推送情景音乐！"+themeMusic.toString());
				logger.info("JPush 推送情景音乐！"+themeMusic.toString());
				jPushimpl.sendPush(jpush);
		}
		
	}
	
	/**
	 * 联动音乐   报警类联动音乐
	 * */
	public void SendLinkageMusic(String deviceNo,DeviceState deviceState){
		ThemeMusicServie themeMusicServie=SpringUtil.getApplicationContext().getBean(ThemeMusicServie.class);
		ThemeMusic themeMusic=themeMusicServie.getLinkageMusicByDeviceNo(deviceNo);
		if (themeMusic!=null && !(deviceState.getDeviceState().equals(Constant.AlertState))) {
			Jpush jpush=new Jpush();
			JPushimpl jPushimpl=new JPushimpl();
			jpush=ThemeMusicToJpush.ToJpush(themeMusic);
			logger.info("JPush 推送情景音乐！"+themeMusic.toString());
			jPushimpl.sendPush(jpush);
		}
				
	}
	
	/**
	 * 撤防联动音乐  需要判断该传感器是否已经联动了音乐 联动了音乐撤防的同时暂停音乐
	 * */
	public void AlertOffPauseMusic(Message message){
		String deviceNo=BasicProcess.toHexString(message.getMessageHead().getDev_id());  //得到设备ID
		ThemeMusicServie themeMusicServie=SpringUtil.getApplicationContext().getBean(ThemeMusicServie.class);
		ThemeMusic themeMusic=themeMusicServie.getLinkageMusicByDeviceNo(deviceNo);
		if (themeMusic!=null) {
			MusicOrder musicOrder=MusicUtil.AlertOffPauseMusic(message);
			Jpush jpush=ThemeMusicToJpush.ToAlertOffPauseMusic(musicOrder);
			JPushimpl jPushimpl=new JPushimpl();
			jPushimpl.sendPush(jpush);
		}
		
	}
	
	
	public Message heartBeat(ChannelHandlerContext ctx, Message request) {
		// 26 心跳请求报文 不做任何处理，返回一个心跳确认报文的 (直接丢弃)
		request.getMessageHead().setData_type(DataType.DATA_ACK_PULSE);
		String gateWayNo =BasicProcess.toHexString(request.getMessageHead().getGateway_id());
		ClientChannelInfo clientChannelInfo = 
				new ClientChannelInfo(gateWayNo, 
						ctx.channel());
		if(request.getDataFrom() == 2){
			connectionManager.registorChannel(clientChannelInfo);
		}
		return request;
	}
	
	
	public AlertService getAlertService() {
		return alertService;
	}
	public void setAlertService(AlertService alertService) {
		this.alertService = alertService;
	}
	public PacketService getPacketService() {
		return packetService;
	}
	public void setPacketService(PacketService packetService) {
		this.packetService = packetService;
	}
	public ThemeMusicServie getThemeMusicServie() {
		return themeMusicServie;
	}
	public void setThemeMusicServie(ThemeMusicServie themeMusicServie) {
		this.themeMusicServie = themeMusicServie;
	}
	public DeviceStateService getDeviceStateService() {
		return deviceStateService;
	}
	public void setDeviceStateService(DeviceStateService deviceStateService) {
		this.deviceStateService = deviceStateService;
	}
	public DeviceService getDeviceService() {
		return deviceService;
	}
	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}
	

}
