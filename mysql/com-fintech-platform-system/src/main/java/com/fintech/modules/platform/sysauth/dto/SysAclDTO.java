package com.fintech.modules.platform.sysauth.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:操作权限控制表
 *@author
 *@version 1.0,
 *@date 2014-10-15 10:23:43
 */
public class SysAclDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**角色ID*/
	private java.lang.Long roleId;

	/**资源ID*/
	private java.lang.Long resoureId;

	/**1可访问，0拒绝访问*/
	private java.lang.Long accessibility;

	/**应用ID，备用*/
	private java.lang.Long appId;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;

	/**乐观锁*/
	private java.lang.Long version;

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
	 *方法: 获得roleId
	 *@return: java.lang.Long  roleId
	 */
	public java.lang.Long getRoleId(){
		return this.roleId;
	}

	/**
	 *方法: 设置roleId
	 *@param: java.lang.Long  roleId
	 */
	public void setRoleId(java.lang.Long roleId){
		this.roleId = roleId;
	}

	/**
	 *方法: 获得resoureId
	 *@return: java.lang.Long  resoureId
	 */
	public java.lang.Long getResoureId(){
		return this.resoureId;
	}

	/**
	 *方法: 设置resoureId
	 *@param: java.lang.Long  resoureId
	 */
	public void setResoureId(java.lang.Long resoureId){
		this.resoureId = resoureId;
	}

	/**
	 *方法: 获得accessibility
	 *@return: java.lang.Long  accessibility
	 */
	public java.lang.Long getAccessibility(){
		return this.accessibility;
	}

	/**
	 *方法: 设置accessibility
	 *@param: java.lang.Long  accessibility
	 */
	public void setAccessibility(java.lang.Long accessibility){
		this.accessibility = accessibility;
	}

	/**
	 *方法: 获得appId
	 *@return: java.lang.Long  appId
	 */
	public java.lang.Long getAppId(){
		return this.appId;
	}

	/**
	 *方法: 设置appId
	 *@param: java.lang.Long  appId
	 */
	public void setAppId(java.lang.Long appId){
		this.appId = appId;
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

	/**
	 *方法: 获得version
	 *@return: java.lang.Long  version
	 */
	public java.lang.Long getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置version
	 *@param: java.lang.Long  version
	 */
	public void setVersion(java.lang.Long version){
		this.version = version;
	}

}