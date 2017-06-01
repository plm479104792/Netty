package ecjtu.homecoo.appserver.domain;

public class Alert {
    private Integer id;

    private String packet;

    private Long time;

    private String gatewayNo;

    private String deviceNo;

    private String bz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getGatewayNo() {
        return gatewayNo;
    }

    public void setGatewayNo(String gatewayNo) {
        this.gatewayNo = gatewayNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

	@Override
	public String toString() {
		return "Alert [id=" + id + ", packet=" + packet + ", time=" + time
				+ ", gatewayNo=" + gatewayNo + ", deviceNo=" + deviceNo
				+ ", bz=" + bz + "]";
	}
    
}