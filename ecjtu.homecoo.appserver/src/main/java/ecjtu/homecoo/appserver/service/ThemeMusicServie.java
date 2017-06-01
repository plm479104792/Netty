package ecjtu.homecoo.appserver.service;

import ecjtu.homecoo.appserver.domain.ThemeMusic;

/**
 * @author xiaobai 
 * @Date:2016-05-26
 * */
public interface ThemeMusicServie {
	
	ThemeMusic getThemeMusicByGatewayNoAndThemeNo(String gatewayNo,String themeNo);
	
	
	ThemeMusic getThemeMusicByDeviceNoAndDeviceState(String deviceNo,String deviceState);
	
	ThemeMusic getLinkageMusicByDeviceNo(String deviceNo);
}
