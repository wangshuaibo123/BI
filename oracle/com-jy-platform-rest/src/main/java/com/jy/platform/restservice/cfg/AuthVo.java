package com.jy.platform.restservice.cfg;

import java.io.Serializable;

public class AuthVo implements Serializable {
	
	public AuthVo(String appId, String appPwd, String validTime) {
		this.appId = appId;
		this.appPwd = appPwd;
		this.validTime = validTime;
	}
	
	private static final long serialVersionUID = 1L;
	
	private String appId;
	
	private String appPwd;
	
	private String validTime;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppPwd() {
		return appPwd;
	}
	public void setAppPwd(String appPwd) {
		this.appPwd = appPwd;
	}
	public String getValidTime() {
		return validTime;
	}
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

}
