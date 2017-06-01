package ecjtu.homecoo.appserver.domain;

import java.util.Date;

public class Theme {
    private Integer themeId;

    private String themeNo;

    private String themeName;

    private String themeType;

    private String themeState;

    private String deviceNo;

    private String gatewayNo;

    private Date createTime;

    private Integer createBy;

    private Date updateTime;

    private Integer updateBy;
    

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getThemeNo() {
        return themeNo;
    }

    public void setThemeNo(String themeNo) {
        this.themeNo = themeNo;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
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

	public String getGatewayNo() {
		return gatewayNo;
	}

	public void setGatewayNo(String gatewayNo) {
		this.gatewayNo = gatewayNo;
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
		return "Theme [themeId=" + themeId + ", themeNo=" + themeNo
				+ ", themeName=" + themeName + ", themeType=" + themeType
				+ ", themeState=" + themeState + ", deviceNo=" + deviceNo
				+ ", gatewayNo=" + gatewayNo + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", updateTime=" + updateTime
				+ ", updateBy=" + updateBy + "]";
	}

    
}