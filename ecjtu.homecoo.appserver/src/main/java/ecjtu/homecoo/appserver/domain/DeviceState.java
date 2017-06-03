package ecjtu.homecoo.appserver.domain;

import java.util.Date;

public class DeviceState {
	private Integer recordId;
	private String deviceNo;
	private String deviceState;
	private String gatewayNo;
	private Date lastUpdateTime;
	private Integer updateBy;
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
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
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Integer getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	public String getGatewayNo() {
		return gatewayNo;
	}
	public void setGatewayNo(String gatewayNo) {
		this.gatewayNo = gatewayNo;
	}
	@Override
	public String toString() {
		return "DeviceState [recordId=" + recordId + ", deviceNo=" + deviceNo + ", deviceState=" + deviceState
				+ ", gatewayNo=" + gatewayNo + ", lastUpdateTime=" + lastUpdateTime + ", updateBy=" + updateBy + "]";
	}
	
}
