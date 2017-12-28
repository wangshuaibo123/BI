package com.jy.modules.logmonitor.sysapplogpriv.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:日志访问权限表
 *@author sunli
 *@version 1.0,
 *@date 2016-05-30 11:30:54
 */
public class SysAppLogPrivDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**系统标示*/
	private java.lang.String appId;
	
	private java.lang.String appName;
	
	private java.lang.String appIds;

	/**用户ID*/
	private java.lang.Long userId;
	
	private String userName;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;

	/**
	 *方法: 获得id
	 *@return: java.lang.Long  id
	 */
	public java.lang.Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置id
	 *@param: java.lang.Long  id
	 */
	public void setId(java.lang.Long id){
		this.id = id;
	}

	/**
	 *方法: 获得appId
	 *@return: java.lang.String  appId
	 */
	public java.lang.String getAppId(){
		return this.appId;
	}

	/**
	 *方法: 设置appId
	 *@param: java.lang.String  appId
	 */
	public void setAppId(java.lang.String appId){
		this.appId = appId;
	}

	/**
	 *方法: 获得userId
	 *@return: java.lang.Long  userId
	 */
	public java.lang.Long getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置userId
	 *@param: java.lang.Long  userId
	 */
	public void setUserId(java.lang.Long userId){
		this.userId = userId;
	}

	/**
	 *方法: 获得validateState
	 *@return: java.lang.String  validateState
	 */
	public java.lang.String getValidateState(){
		return this.validateState;
	}

	/**
	 *方法: 设置validateState
	 *@param: java.lang.String  validateState
	 */
	public void setValidateState(java.lang.String validateState){
		this.validateState = validateState;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public java.lang.String getAppIds() {
		return appIds;
	}
	public void setAppIds(java.lang.String appIds) {
		this.appIds = appIds;
	}
	public java.lang.String getAppName() {
		return appName;
	}
	public void setAppName(java.lang.String appName) {
		this.appName = appName;
	}
}