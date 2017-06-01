package ecjtu.homecoo.appserver.springutil;

/**
 * 设备类型
 * @author libin
 *
 */
public class DeviceType {

	public static final short DEV_SWITCH_1=1;   //开关type
	public static final short DEV_SWITCH_2=2;
	public static final short DEV_SWITCH_3=3;
	public static final short DEV_SWITCH_4=4;
	public static final short DEV_DIMSWITCH_1=5;
	public static final short DEV_CURTAIN_1=6;
	public static final short DEV_CURTAIN_2=7;
	public static final short DEV_SOCK_1=8;
	public static final short DEV_SOCK_4=9;
	
	public static final short DEV_WG_STATE = 51; //新加入一个WG风扇状态设备类型
	
	public static final short DEV_SENSOR_SMOKE=118;  		//烟感(报警类)       //原来是101传感器type     
	public static final short DEV_SENSOR_CO=114;  	//修改2015.8.26  报警器类改为大于等于110  
	public static final short DEV_SENSOR_WATER=103;
	public static final short DEV_SENSOR_TEMP=104;			//温湿度		
	public static final short DEV_SENSOR_HUMI=115;   		//燃气(报警类)
	public static final short DEV_SENSOR_TEHU=106;
	public static final short DEV_SENSOR_INFRA=113;			//红外入侵(报警类)
	public static final short DEV_SENSOR_LUMI=108;
	public static final short DEV_SENSOR_PM25=109;
	public static final short DEV_SENSOR_MAGLOCK=110;		//门磁(报警类)
	public static final short DEV_SENSOR_FLAME=111;
	public static final short DEV_SENSOR_RAIN=112;
	
	public static final short DEV_MECH_VALVE=201;  //其他type
	public static final short DEV_THEME_4=202;			//普通的情景开关
	public static final short DEV_INFRACTRL=203;
	public static final short DEV_ALARM=304;
}





