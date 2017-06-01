package ecjtu.homecoo.appserver.service.impl;


import ecjtu.homecoo.appserver.dao.ThemeMusicDao;
import ecjtu.homecoo.appserver.domain.ThemeMusic;
import ecjtu.homecoo.appserver.service.ThemeMusicServie;

public class ThemeMusicServiceImpl implements ThemeMusicServie{
	
	private ThemeMusicDao themeMusicDao;

	@Override
	public ThemeMusic getThemeMusicByGatewayNoAndThemeNo(String gatewayNo,
			String themeNo) {
		ThemeMusic themeMusic=themeMusicDao.getThemeMusicByGatewayNoAndThemeNo(gatewayNo, themeNo);
		return themeMusic;
	}

	@Override
	public ThemeMusic getThemeMusicByDeviceNoAndDeviceState(String deviceNo,
			String deviceState) {
		ThemeMusic themeMusic=themeMusicDao.getThemeMusicByDeviceNoAndDeviceState(deviceNo, deviceState);
		System.out.println("得到情景联动音乐:"+themeMusic.toString());
		return themeMusic;
	}
	
	@Override
	public ThemeMusic getLinkageMusicByDeviceNo(String deviceNo) {
		ThemeMusic themeMusic=themeMusicDao.getLinkageMusicDeviceNo(deviceNo);
		return themeMusic;
	}

	public ThemeMusicDao getThemeMusicDao() {
		return themeMusicDao;
	}

	public void setThemeMusicDao(ThemeMusicDao themeMusicDao) {
		this.themeMusicDao = themeMusicDao;
	}


}
