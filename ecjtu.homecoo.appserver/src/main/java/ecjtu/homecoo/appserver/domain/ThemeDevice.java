package ecjtu.homecoo.appserver.domain;

public class ThemeDevice {

	private String themeNo;				//手机号加情景产生的，唯一
	private String themeDeviceNo;		//情景设备识别码 (硬件情景设备)
	private String themeType;
	private String themeState;
	private String deviceNo;
	private String deviceState;
	private String gatewayNo;
	public String getThemeNo() {
		return themeNo;
	}
	public void setThemeNo(String themeNo) {
		this.themeNo = themeNo;
	}
	public String getThemeDeviceNo() {
		return themeDeviceNo;
	}
	public void setThemeDeviceNo(String themeDeviceNo) {
		this.themeDeviceNo = themeDeviceNo;
	}
	public String getThemeType() {
		return themeType;
	}
	public void setThemeType(String themeType) {
		this.themeType = themeType;
	}
	public String getThemeState() {
		return themeState;
	}
	public void setThemeState(String themeState) {
		this.themeState = themeState;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getDeviceState() {
		return deviceState;
	}
	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
	}
	public String getGatewayNo() {
		return gatewayNo;
	}
	public void setGatewayNo(String gatewayNo) {
		this.gatewayNo = gatewayNo;
	}
	
	
	@Override
	public String toString() {
		return "ThemeDevice [themeNo=" + themeNo + ", themeDeviceNo="
				+ themeDeviceNo + ", themeType=" + themeType + ", themeState="
				+ themeState + ", deviceNo=" + deviceNo + ", deviceState="
				+ deviceState + ", gatewayNo=" + gatewayNo + "]";
	}
}
