package com.fintech.modules.platform.sysauth.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:角色用户表
 *@author
 *@version 1.0,
 *@date 2014-10-15 10:25:12
 */
public class SysRoleUserDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**角色ID*/
	private java.lang.Long roleId;

	/**用户ID或者机构ID*/
	private java.lang.Long targetId;
	
	private java.lang.String targetName; 

	/**系统ID，备用*/
	private java.lang.Long appId;

	/**user用户类型,org机构类型*/
	private java.lang.String targetType;

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
	 *方法: 获得targetId
	 *@return: java.lang.Long  targetId
	 */
	public java.lang.Long getTargetId(){
		return this.targetId;
	}

	/**
	 *方法: 设置targetId
	 *@param: java.lang.Long  targetId
	 */
	public void setTargetId(java.lang.Long targetId){
		this.targetId = targetId;
	}

	public java.lang.String getTargetName() {
		return targetName;
	}

	public void setTargetName(java.lang.String targetName) {
		this.targetName = targetName;
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
	 *方法: 获得targetType
	 *@return: java.lang.String  targetType
	 */
	public java.lang.String getTargetType(){
		return this.targetType;
	}

	/**
	 *方法: 设置targetType
	 *@param: java.lang.String  targetType
	 */
	public void setTargetType(java.lang.String targetType){
		this.targetType = targetType;
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