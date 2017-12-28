package com.jy.modules.platform.sysauth.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:角色组角色中间表
 *@author yuchengyang-pc
 *@version 1.0,
 *@date 2014-11-28 17:38:38
 */
public class SysRoleGroupRoleDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**角色ID*/
	private java.lang.Long roleId;

	/**角色组ID*/
	private java.lang.Long roleGroupId;

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
	 *方法: 获得roleGroupId
	 *@return: java.lang.Long  roleGroupId
	 */
	public java.lang.Long getRoleGroupId(){
		return this.roleGroupId;
	}

	/**
	 *方法: 设置roleGroupId
	 *@param: java.lang.Long  roleGroupId
	 */
	public void setRoleGroupId(java.lang.Long roleGroupId){
		this.roleGroupId = roleGroupId;
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