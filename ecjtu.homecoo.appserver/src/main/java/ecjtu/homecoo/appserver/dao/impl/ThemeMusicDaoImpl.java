package ecjtu.homecoo.appserver.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import ecjtu.homecoo.appserver.dao.ThemeMusicDao;
import ecjtu.homecoo.appserver.domain.ThemeMusic;

public class ThemeMusicDaoImpl implements ThemeMusicDao{

	private SqlSession sqlSession;

	@Override
	public ThemeMusic getThemeMusicByGatewayNoAndThemeNo(String gatewayNo,
			String themeNo) {
		ThemeMusic themeMusic=new ThemeMusic();
		themeMusic.setGatewayNo(gatewayNo);
		themeMusic.setThemeNo(themeNo);
		ThemeMusic record=sqlSession.selectOne("ThemeMusicMapper.getThemeMusicByGatewayNoAndThemeNo", themeMusic);
		return record;
	}

	@Override
	public ThemeMusic getThemeMusicByDeviceNoAndDeviceState(String deviceNo,
			String deviceState) {
		ThemeMusic themeMusic=new ThemeMusic();
		ThemeMusic themeMusic1=new ThemeMusic();
		ThemeMusic record=new ThemeMusic();
		List<ThemeMusic> list=new ArrayList<>();
		themeMusic.setDeviceNo(deviceNo);
		themeMusic.setDeviceState(deviceState);
		
		try {
			themeMusic1=sqlSession.selectOne("ThemeMusicMapper.getThemeMusicByDeviceNoAndDeviceState", themeMusic);
			record=themeMusic1;
		} catch (Exception e) {
			list= sqlSession.selectList("ThemeMusicMapper.getThemeMusicByDeviceNoAndDeviceState", themeMusic);
			if (list.size()>0) {
				record=list.get(0);
			}
		}
		return record;
	}

	@Override
	public ThemeMusic getLinkageMusicDeviceNo(String deviceNo) {
		ThemeMusic themeMusic=new ThemeMusic();
		themeMusic.setDeviceNo(deviceNo);
		System.out.println("daoimpl"+themeMusic.toString());
		ThemeMusic record=sqlSession.selectOne("ThemeMusicMapper.getLinkageMusicDeviceNo",themeMusic);
		return record;
	}

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	
	
}
