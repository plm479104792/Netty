package ecjtu.homecoo.appserver.domain;

import java.util.Date;

public class DeviceType {
    private String deviceTypeId;

    private String deviceTypeName;

    private Integer deviceCategoryId;

    private String deviceCategoryName;

    private Date createTime;

    private Integer createBy;

    private Date updateTime;

    private Integer updateBy;

    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public Integer getDeviceCategoryId() {
        return deviceCategoryId;
    }

    public void setDeviceCategoryId(Integer deviceCategoryId) {
        this.deviceCategoryId = deviceCategoryId;
    }

    public String getDeviceCategoryName() {
        return deviceCategoryName;
    }

    public void setDeviceCategoryName(String deviceCategoryName) {
        this.deviceCategoryName = deviceCategoryName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

	@Override
	public String toString() {
		return "DeviceType [deviceTypeId=" + deviceTypeId + ", deviceTypeName="
				+ deviceTypeName + ", deviceCategoryId=" + deviceCategoryId
				+ ", deviceCategoryName=" + deviceCategoryName
				+ ", createTime=" + createTime + ", createBy=" + createBy
				+ ", updateTime=" + updateTime + ", updateBy=" + updateBy + "]";
	}
    
}