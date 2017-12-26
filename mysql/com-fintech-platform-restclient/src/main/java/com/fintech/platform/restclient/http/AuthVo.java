package com.fintech.platform.restclient.http;

import java.io.Serializable;

public class AuthVo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String appId;
	
	private String appPwd;
	
	private String validTime;
	
	private String serviceUrl;
	
	public AuthVo(){
		
	}
	public AuthVo(String appId, String appPwd, String validTime) {
		this.appId = appId;
		this.appPwd = appPwd;
		this.validTime = validTime;
	}
	public AuthVo(String appId, String appPwd, String validTime,String serviceUrl) {
		this.appId = appId;
		this.appPwd = appPwd;
		this.validTime = validTime;
		this.serviceUrl = serviceUrl;
	}
	
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
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
