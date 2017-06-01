package ecjtu.homecoo.appserver.domain;

import java.util.Date;

public class Device {
    private Integer deviceId;

    private String deviceNo;

    private String deviceTypeId;

    private String deviceTypeName;

    private Integer deviceCategoryId;

    private String deviceCategoryName;
    
    private String deviceName;

    private String gatewayNo;
    
    private String spaceNo;

    private String spaceTypeId;
    
    private String phoneNum;

    private Date createTime;

    private Integer createBy;

    private Date updateTime;

    private Integer updateBy;
    
//    private String deviceState;
    
    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

   

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


    public String getGatewayNo() {
        return gatewayNo;
    }

    public void setGatewayNo(String gatewayNo) {
        this.gatewayNo = gatewayNo;
    }

    public String getSpaceTypeId() {
        return spaceTypeId;
    }

    public void setSpaceTypeId(String spaceTypeId) {
        this.spaceTypeId = spaceTypeId;
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

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getSpaceNo() {
		return spaceNo;
	}

	public void setSpaceNo(String spaceNo) {
		this.spaceNo = spaceNo;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId + ", deviceNo=" + deviceNo
				+ ", deviceTypeId=" + deviceTypeId + ", deviceTypeName="
				+ deviceTypeName + ", deviceCategoryId=" + deviceCategoryId
				+ ", deviceCategoryName=" + deviceCategoryName
				+ ", deviceName=" + deviceName + ", gatewayNo=" + gatewayNo
				+ ", spaceNo=" + spaceNo + ", spaceTypeId=" + spaceTypeId
				+ ", phoneNum=" + phoneNum + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", updateTime=" + updateTime
				+ ", updateBy=" + updateBy + ", deviceState=" 
				+ "]";
	}

}