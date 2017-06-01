package ecjtu.homecoo.remoting.util;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 工具类
 * 
 * */	
public class BasicProcess {
	
	/**
	 * 产生8位随机数
	 * @param pwd_len
	 * @return
	 */
	public static String genRandomNum(int pwd_len){
	    //35是因为数组是从0开始的，26个字母+10个数字
	    final int maxNum = 36;
	    int i; //生成的随机数
	    int count = 0; //生成的密码的长度
	    char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	    StringBuffer pwd = new StringBuffer("");
	    Random r = new Random();
	    while(count < pwd_len){
	     //生成随机数，取绝对值，防止生成负数，
	   
	     i = Math.abs(r.nextInt(maxNum)); //生成的数最大为36-1
	   
	     if (i >= 0 && i < str.length) {
	      pwd.append(str[i]);
	      count ++;
	     }
	    }
	    return pwd.toString();
	 }
	
	
	/**
	 * 16进制byte[]转String类型
	 */
	public static String toHexString(byte[] byteArray) {
		if (byteArray == null || byteArray.length < 1)
			throw new IllegalArgumentException(
					"this byteArray must not be null or empty");
		final StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			if ((byteArray[i] & 0xff) < 0x10)// 0~F前面不零
				hexString.append("0");
			hexString.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return hexString.toString().toLowerCase();
	}

	
	/**
	 * String 转换成byte[]形式，注意这个不改变实际内容 “00 01 30 02” 转  00 01 30 02
	 */
	public static byte[] toByteArray(String hexString) {
		if (StringUtils.isBlank(hexString))
			throw new IllegalArgumentException(
					"this hexString must not be empty");
		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {
			// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}

	/**
	 * String 转换成byte[]形式，注意这个不改变实际内容 “00 01 30 02” 转 00 01 30 02
	 */
	public static byte[] StringtoByteArray(String hexString) {

		hexString = hexString.toLowerCase();
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {
			// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}
	
	/**
	 * byte[]原版打印 测试使用,这里只是byte[] 的String形式的输出； 01 00 02 30  转 “01 00 02 30”
	 */
	public static String printHexString(byte[] b) {
		StringBuffer returnValue = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			// System.out.print(hex.toUpperCase() + " ");
			returnValue.append(hex.toUpperCase() + " ");
		}
		return "[" + returnValue.toString() + "]";
	}
	
	
	
	/**
	 * byte[]转16进制int型，这里是为了转short 
	 * 这里写了两个不同的编码方式，供两种不同效果
	 * 
	 * */
	public static int byte2int(byte[] res) {  //前大后小，为datalength使用
		// res = InversionByte(res);
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
		int targets = (res[1] & 0xff) | ((res[0] << 8) & 0xff00); // | 表示安位或
		// int targets = ((res[0] & 0xff) |(res[1] << 0xff00) );
		return targets;
	}
	
	public static short byte2ShortLittleEnddian(byte[] res){
		short s = 0;
		if(res == null || res.length == 0)
		{
			return s;
		}
		for(int i = 0 ; i < res.length;i++){
			s = (short)(s | res[i] << (i*8)); 
		}
		return s;
	}
	public static short byte2ShortBigEnddian(byte[] res){
		short s = 0;
		if(res == null || res.length == 0)
		{
			return s;
		}
		for(int i = 0 ; i < res.length;i++){
			s = (short)(s | res[i] << ((res.length-i-1)*8)); 
		}
		return s;
	}
	
	/**
	 * 将byte[2]转换成short，长度表示为00 01
	 * 
	 * @param b
	 * @return
	 */
	public static short byte2Short(byte[] b) {
		return (short) (((b[0] & 0xff) << 8) | (b[1] & 0xff));
	}
	
	
	/** 
	 * byte[]转16进制int型，这里是为了转short 
	 * 这里写了两个不同的编码方式，供两种不同效果
	 * 
	 * */
	public static int bytetoint(byte[] res) {//正常转变，前小后大,为datatype，devType使用
		// res = InversionByte(res);
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
//		int targets = (res[1] & 0xff) | ((res[0] << 8) & 0xff00); // | 表示安位或
		 int targets = ((res[0] & 0xff) |(res[1] << 0xff00) );
		return targets;
	}
	
	
	/** 
	 * 
	 * short转byte[] 
	 * 
	 * */
	public static byte[] shortToByteArray(short s) {
		byte[] targets = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}
	
	//高位在前（dataLen定制）
	public static byte[] shortToByteArray2(short s) {
		byte[] targets = new byte[2];
		int offset1 = (targets.length - 1 - 1) * 8;	
		int offset = (targets.length - 1 - 0) * 8;
		targets[0] = (byte) ((s >>> offset1) & 0xff);
		targets[1] = (byte) ((s >>> offset) & 0xff);
		return targets;
	}
	
	
	/** 
	 * 基于位移的32位int转化成byte[] 
	 * @param int  number 
	 * @return byte[] 
	 */    
	public static byte[] intToByte(int number) {  
	    byte[] abyte = new byte[4];  
	    // "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。  
	    abyte[0] = (byte) (0xff & number);  
	    // ">>"右移位，若为正数则高位补0，若为负数则高位补1  
	    abyte[1] = (byte) ((0xff00 & number) >> 8);  
	    abyte[2] = (byte) ((0xff0000 & number) >> 16);  
	    abyte[3] = (byte) ((0xff000000 & number) >> 24);  
	    return abyte;  
	}  
	  
	/** 
	 *基于位移的 byte[]转化成32位int 
	 * @param byte[] bytes 
	 * @return int  number 
	 */   
	public static int bytesToInt(byte[] bytes) {  
	    int number = bytes[0] & 0xFF;  
	    // "|="按位或赋值。  
	    number |= ((bytes[1] << 8) & 0xFF00);  
	    number |= ((bytes[2] << 16) & 0xFF0000);  
	    number |= ((bytes[3] << 24) & 0xFF000000);  
	    return number;  
	} 
	
	
	public static int bytesToIntTest(byte[] bytes) {   //2015.8.24 
	    int number = bytes[0] & 0xFF;  
	    // "|="按位或赋值。  
	    number |= ((bytes[1] << 8) & 0xFF00);  
	     
	    return number;  
	} 
	
	/**
	 * 根据设备种类进行data转码
	 * 
	 * @param b
	 * @return
	 */
	public static String wgDataToString(byte[] b) {
		String strhex = "";
			for (int i = 0; i < b.length; i++) {
				String hex = Integer.toHexString(b[i] & 0xFF);
				if (hex.equals("64")) {
					hex = "1";
				} else if (hex.equals("00")) {
					hex = "0";
				} else if (hex.equals("32")) { // 情景学习
					hex = "2";
				}
				strhex += hex; // 除了0和64其它的原字节返回
			}
		return strhex;
	}
}
