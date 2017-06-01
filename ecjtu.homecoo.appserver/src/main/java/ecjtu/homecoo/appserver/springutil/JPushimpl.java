package ecjtu.homecoo.appserver.springutil;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.annotations.JsonAdapter;
import com.mysql.jdbc.log.Log;

import ecjtu.homecoo.appserver.domain.Jpush;
import ecjtu.homecoo.appserver.processor.DefaultProcessor;

public class JPushimpl {
	private static final String appKey = "f01169052651936b1a139e18"; // 极光推送应用的KEY
	private static final String masterSecret = "8604eec283004285f913daf0"; // 极光推送应用KEY对应的Secret

	/**
	 * 发送Jpush自定义消息
	 * */
	
	private static Logger logger = Logger.getLogger(JPushimpl.class);
	public void sendPush(Jpush jpush) {
		String alias = jpush.getGatewayNo();
		String jpushString = JSONObject.toJSONString(jpush);
		
		// 默认离线消息存活一天
		JPushClient jPushClient = new JPushClient(masterSecret, appKey);
//		System.out.println("jpus" + jpush.toString());
		logger.info("===============");
		logger.info("JPush推送的消息:"+jpushString);
		logger.info("===============");
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(
						Audience.newBuilder()
								.addAudienceTarget(AudienceTarget.alias(alias))
								// .addAudienceTarget(AudienceTarget.tag("",""))
								// .addAudienceTarget(AudienceTarget.registrationId("",""))
								.build())
				.setMessage(
						Message.newBuilder().setMsgContent(jpushString)
								.addExtra("", "").build())
				.setOptions(Options.newBuilder().setTimeToLive(43200).build()) // 设置离线消息也是
																				// 存活半天
				.build();
		try {
			PushResult result = jPushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			System.err.println("这里有异常吗？");
		} catch (APIRequestException e) {
			System.err.println("JPush自定义消息     推送别名的网关未连接到netty");
		}

	}

	// 推送各种报警通知
	public void sendNotificationAllSmoke(Jpush jpush)
			throws APIConnectionException, APIRequestException {
		// 设置离线通知 存活时间为半天
		JPushClient jPushClientAndroid = new JPushClient(masterSecret, appKey,
				true, 43200);
		try {
			@SuppressWarnings("unused")
			// 以后可能用得上
			PushResult result = jPushClientAndroid
					.sendAndroidNotificationWithAlias("请及时打开APP查看报警信息！",
							"烟感报警", null, jpush.getGatewayNo());
		} catch (Exception e) {
			System.err.println("JPush通知    推送别名的网关未连接到netty");
		}
	}

	public void sendNotificationAllHumi(Jpush jpush)
			throws APIConnectionException, APIRequestException {
		JPushClient jPushClientAndroid = new JPushClient(masterSecret, appKey,
				true, 43200);
		try {
			@SuppressWarnings("unused")
			// 以后可能用得上
			PushResult result = jPushClientAndroid
					.sendAndroidNotificationWithAlias("请及时打开APP查看报警信息！",
							"燃气报警", null, jpush.getGatewayNo());
		} catch (Exception e) {
			System.err.println("JPush通知     推送别名的网关未连接到netty");
		}
	}

	public void sendNotificationAllInfra(Jpush jpush)
			throws APIConnectionException, APIRequestException {
		JPushClient jPushClientAndroid = new JPushClient(masterSecret, appKey,
				true, 43200);
		try {
			@SuppressWarnings("unused")
			// 以后可能用得上
			PushResult result = jPushClientAndroid
					.sendAndroidNotificationWithAlias("请及时打开APP查看报警信息！",
							"红外报警", null, jpush.getGatewayNo());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("JPush通知    推送别名的网关未连接到netty  Notification");
		}
	}

	public void sendNotificationAllMaglock(Jpush jpush)
			throws APIConnectionException, APIRequestException {
		JPushClient jPushClientAndroid = new JPushClient(masterSecret, appKey,
				true, 43200);
		try {
			@SuppressWarnings("unused")
			// 以后可能用得上
			PushResult result = jPushClientAndroid
					.sendAndroidNotificationWithAlias("请及时打开APP查看报警信息！",
							"门磁报警", null, jpush.getGatewayNo());
		} catch (Exception e) {
			System.err.println("JPush通知    推送别名的网关未连接到netty");
		}
	}

	
	/**
	 * 发送IOS通知
	 * */
	public void sendIosNotification(short device,String gatewayNo) {
		String alert=getIosNotification(device);
		JPushClient jPushClientIos = new JPushClient(masterSecret, appKey,
				false, 43200);
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(Platform.ios())
				// .setPlatform(Platform.android_ios())
				.setAudience(
						Audience.newBuilder()
								.addAudienceTarget(AudienceTarget.alias(gatewayNo))
								.build())
				.setNotification(
						Notification
								.newBuilder()
								.addPlatformNotification(
										IosNotification
												.newBuilder()
												.setAlert(alert)
												// 报警类 红外报警 门磁报警
												.setBadge(0)
												// 设置应用右上角的角标
												// .setCategory("wo X")
												// //iOS8以上的才支持这个参数
												.setContentAvailable(false)
												.setSound("default")
												// .addExtra("a", "a")
												.build()).build())
				.setOptions(Options.newBuilder().setApnsProduction(false) // Apns// true：生产模式   	// false:开发者模式
						.setTimeToLive(43200).build()).build();
		try {
			jPushClientIos.sendPush(payload);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据devicetype 得到是什么报警
	 * */
	public String getIosNotification(short device) {
		String alert="";
		switch (device) {
		case 110:
			// 门磁报警
			alert="门磁报警";
			break;
		case 113:
			// 红外
			alert="红外报警";
			break;
		case 118:
			// 烟感
			alert="烟感报警";
			break;
		case 115:
			// 燃气
			alert="燃气报警";
			break;
		default:
			break;
		}
		return alert;
	}

	/**
	 * 发送android 通知
	 * */
	public void SendAndroidNotification(short device,String gatewayNo){
		String alert=getIosNotification(device);
		JPushClient jPushClientAndroid = new JPushClient(masterSecret, appKey,true, 43200);
		PushPayload payload=PushPayload.newBuilder()
				.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.alias(gatewayNo)).build())
				.setPlatform(Platform.android())
				.setNotification(Notification.android(alert, "请及时打开APP查看报警信息！", null))
				.setOptions(Options.newBuilder().setApnsProduction(true).setTimeToLive(43200).build())
				.build();
		try {
			jPushClientAndroid.sendPush(payload);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}						
		
	}
	
	
	/**
	 * 发送android ios 通知
	 * */
	public void SendNotification(short device,String gatewayNo){
		SendAndroidNotification(device, gatewayNo);
		sendIosNotification(device, gatewayNo);
	}
	
	
}
