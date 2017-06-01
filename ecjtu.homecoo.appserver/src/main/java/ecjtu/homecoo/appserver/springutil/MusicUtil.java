package ecjtu.homecoo.appserver.springutil;


import ecjtu.homecoo.appserver.domain.MusicOrder;
import ecjtu.homecoo.appserver.domain.ThemeMusic;
import ecjtu.homecoo.remoting.protocol.Message;
import ecjtu.homecoo.remoting.util.BasicProcess;

/**
 * @Description:音乐工具类
 * **/
public class MusicUtil {
	
	
	/**
	 * @Description:themeMusic 转 MusicOrder
	 * @param themeMusic
	 * @return musicOrder
	 * */
	public static MusicOrder ToMusicOrder(ThemeMusic themeMusic){
		MusicOrder musicOrder=new MusicOrder();
		musicOrder.setBz(themeMusic.getThemeNo());
//		musicOrder.setOrder("2");		//播放                                            
		musicOrder.setOrder("20");									//	20/21          播放/暂停
		if (themeMusic.getStyle().equals("1")) {
//			musicOrder.setOrder("1");
			musicOrder.setOrder("21");
		}
		musicOrder.setSongName(themeMusic.getSongName());
		musicOrder.setStyle(themeMusic.getStyle());
		musicOrder.setWgid(themeMusic.getGatewayNo());
		return musicOrder;
	}
	
	/**
	 * 撤防暂停音乐
	 * */
	public static MusicOrder AlertOffPauseMusic(Message message){
		byte[] gatewayNo=message.getMessageHead().getGateway_id();
		String gatewayId=BasicProcess.toHexString(gatewayNo);
		MusicOrder musicOrder=new MusicOrder();
		musicOrder.setBz("");
		musicOrder.setOrder("1");  //暂停音乐
		musicOrder.setSongName("aa");
		musicOrder.setStyle("1");
		musicOrder.setWgid(gatewayId);
		return musicOrder;
		
	}

}
