package ecjtu.homecoo.appserver.domain;

public class ThemeMusic {
	private Integer id;
	private String songName;			
	private String themeNo;	
	private String gatewayNo;
	private String themeName;
	private String deviceNo;			//硬件情景面板
	private String deviceState;			//情景面板上的设备状态
	private String style;		//风格	默认列表循环 (2)  	1:单曲循环      2:列表循环    3:随机循环
	private String space;		//备用(空间)
	private String bz;			//备用
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	
	public String getThemeNo() {
		return themeNo;
	}
	public void setThemeNo(String themeNo) {
		this.themeNo = themeNo;
	}
	public String getGatewayNo() {
		return gatewayNo;
	}
	public void setGatewayNo(String gatewayNo) {
		this.gatewayNo = gatewayNo;
	}
	public String getThemeName() {
		return themeName;
	}
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
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
	@Override
	public String toString() {
		return "ThemeMusic [id=" + id + ", songName=" + songName + ", themeNo="
				+ themeNo + ", gatewayNo=" + gatewayNo + ", themeName="
				+ themeName + ", deviceNo=" + deviceNo + ", deviceState="
				+ deviceState + ", style=" + style + ", space=" + space
				+ ", bz=" + bz + "]";
	}
	
	
}
