package ecjtu.homecoo.appserver.springutil;

import com.alibaba.fastjson.JSONObject;
import ecjtu.homecoo.appserver.domain.Jpush;
import ecjtu.homecoo.appserver.domain.MusicOrder;
import ecjtu.homecoo.appserver.domain.ThemeMusic;

/**
 * ThemeMusci TO Jpush
 * */
public class ThemeMusicToJpush {

	/**
	 * ThemeMusic To Jpush
	 * */
	public static Jpush ToJpush(ThemeMusic themeMusic){
		MusicOrder order=MusicUtil.ToMusicOrder(themeMusic);
		Jpush jpush=new Jpush();
		jpush.setGatewayNo(themeMusic.getGatewayNo());
		jpush.setMesssageType(3);
		jpush.setObject(JSONObject.toJSONString(order));
		jpush.setMusicOrder(order);
		return jpush;
	}
 
	/**
	 * 撤防暂停音乐
	 * */
	public static Jpush ToAlertOffPauseMusic(MusicOrder musicOrder){
		Jpush jpush=new Jpush();
		jpush.setGatewayNo(musicOrder.getWgid());
		jpush.setMesssageType(3);
		jpush.setObject(JSONObject.toJSON(musicOrder));
		jpush.setMusicOrder(musicOrder);
		jpush.setTime(0L);
		return jpush;
		
	}
	
}
