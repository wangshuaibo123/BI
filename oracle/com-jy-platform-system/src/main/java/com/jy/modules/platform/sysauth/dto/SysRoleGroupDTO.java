package com.jy.modules.platform.sysauth.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:角色组
 *@author yuchengyang-pc
 *@version 1.0,
 *@date 2014-11-28 15:38:04
 */
public class SysRoleGroupDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**角色组名称*/
	private java.lang.String roleGroupName;

	/**角色组编码*/
	private java.lang.String roleGroupCode;

	/**角色组类型：备用，默认1*/
	private java.lang.String roleGroupType;

	/**系统ID，备用*/
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
	 *方法: 获得roleGroupName
	 *@return: java.lang.String  roleGroupName
	 */
	public java.lang.String getRoleGroupName(){
		return this.roleGroupName;
	}

	/**
	 *方法: 设置roleGroupName
	 *@param: java.lang.String  roleGroupName
	 */
	public void setRoleGroupName(java.lang.String roleGroupName){
		this.roleGroupName = roleGroupName;
	}

	/**
	 *方法: 获得roleGroupCode
	 *@return: java.lang.String  roleGroupCode
	 */
	public java.lang.String getRoleGroupCode(){
		return this.roleGroupCode;
	}

	/**
	 *方法: 设置roleGroupCode
	 *@param: java.lang.String  roleGroupCode
	 */
	public void setRoleGroupCode(java.lang.String roleGroupCode){
		this.roleGroupCode = roleGroupCode;
	}

	/**
	 *方法: 获得roleGroupType
	 *@return: java.lang.String  roleGroupType
	 */
	public java.lang.String getRoleGroupType(){
		return this.roleGroupType;
	}

	/**
	 *方法: 设置roleGroupType
	 *@param: java.lang.String  roleGroupType
	 */
	public void setRoleGroupType(java.lang.String roleGroupType){
		this.roleGroupType = roleGroupType;
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