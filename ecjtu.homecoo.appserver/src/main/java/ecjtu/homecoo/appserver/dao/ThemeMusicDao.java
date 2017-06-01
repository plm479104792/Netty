package ecjtu.homecoo.appserver.dao;

import ecjtu.homecoo.appserver.domain.ThemeMusic;

public interface ThemeMusicDao {
	
	ThemeMusic getThemeMusicByGatewayNoAndThemeNo(String gatewayNo,String themeNo);
	
	ThemeMusic getThemeMusicByDeviceNoAndDeviceState(String deviceNo,String deviceState);
	
	ThemeMusic getLinkageMusicDeviceNo(String deviceNo);
	
}
