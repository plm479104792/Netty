package ecjtu.homecoo.remoting.protocol;

import java.util.Map;

/**
 * 数据类型指令集
 * 
 * */
public class DataType {
	
	public static final short DATA_STAT=1;  			//设备状态指令   		底层上发报文(上行)  
	public static final short DATA_CTRL=2;   			//设备执行指令类型   	手机下发报文(下行)
	public static final short DATA_TUIW = 12; 			//设备退网指令			
	public static final short DATA_CTRL_SCENE=13;    	//手机发送控制情景报文到网关  (netty不做处理   手机处理)
	public static final short DATA_REQ_SCENE=14;		//服务器主动向网关拉取情景设置
	public static final short DATA_SET_SCENE=15;		//情景设置	 请求情景设置
	public static final short DATA_SET_SCENE_WG=16;		//网关同步情景设置信息到netty
	public static final short DATA_OP_SCE=70;			//情景确认
	public static final short DATA_FINISH_SCENE=17;		//手机端情景更新之后要finish下再发给网关	(netty 不做处理，手机处理)
	public static final short DATA_DELETE_SCENE=18;		//删除情景
	public static final short DATA_REQ_SYNC=21;    		//同步请求		
	public static final short DATA_REQ_AUTH_GW=22;      //网关	    	
	public static final short DATA_REQ_AUTH_DEV=23;     //设备	     	
	public static final short DATA_REQ_USER=24;         //用户		
	public static final short DATA_REQ_STAMP=25;        //跟踪		    
	public static final short DATA_REQ_PULSE=26;        //心跳		    
	public static final short DATA_CHANGE_GW=30;        //设备更新    不需要回复网关    用于硬件情景音乐    update by 2014-09-24
	public static final short DATA_ACK_SYNC=61;         //同步确认		
	public static final short DATA_ACK_AUTH_GW=62;      //网关认证确认	    
	public static final short DATA_ACK_AUTH_DEV=63;     //设备			
	public static final short DATA_ACK_USER=64;         //用户		
	public static final short DATA_ACK_STAMP=65;        //跟踪			
	public static final short DATA_ACK_PULSE=66;        //心跳			
	public static final short DATA_SYS_RESET=101;       //复位
	public static final short DATA_WG_STAT =201;        //监听启动报文
	public static Map<String,Short> DATA_MAP;
	public static short THEME_FLAG=0;                   //情景标志  0:表示主动获取情景  1:表示收到情景  对应MAP值

}
