package ecjtu.homecoo.appserver.springutil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NetTime {

	public static long getNetTime(){
		long time=System.currentTimeMillis();
//		  try {
//				URL url=new URL("http://www.bjtime.cn");//取得资源对象
//				  URLConnection uc=url.openConnection();//生成连接对象
//				  uc.connect(); //发出连接
//				  time=uc.getDate(); //取得网站日期时间
//				  System.out.println("===网络时间"+time);
//				  Date date=new Date(time); //转换为标准时间对象
//				  SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
//				  System.out.println("===极光推送的时间"+formatter.format(date));
//				  
//			} catch (MalformedURLException e) {
//				System.out.println("MalformedURLException");
//				e.printStackTrace();
//			} catch (IOException e) {
//				System.err.println("IOException");
				time=System.currentTimeMillis();
//				System.out.println("当前系统时间 ："+time);
//			}
		return time;
	}
}
