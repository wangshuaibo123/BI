package com.fintech.modules.platform.sysauth.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:角色表
 *@author
 *@version 1.0,
 *@date 2014-10-15 10:24:59
 */
public class SysRoleDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**角色名称*/
	private java.lang.String roleName;

	/**角色编码*/
	private java.lang.String roleCode;

	/**角色类型：0系统角色（不能删除），1项目自定义角色*/
	private java.lang.String roleType;

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
	 *方法: 获得roleName
	 *@return: java.lang.String  roleName
	 */
	public java.lang.String getRoleName(){
		return this.roleName;
	}

	/**
	 *方法: 设置roleName
	 *@param: java.lang.String  roleName
	 */
	public void setRoleName(java.lang.String roleName){
		this.roleName = roleName;
	}

	/**
	 *方法: 获得roleCode
	 *@return: java.lang.String  roleCode
	 */
	public java.lang.String getRoleCode(){
		return this.roleCode;
	}

	/**
	 *方法: 设置roleCode
	 *@param: java.lang.String  roleCode
	 */
	public void setRoleCode(java.lang.String roleCode){
		this.roleCode = roleCode;
	}

	/**
	 *方法: 获得roleType
	 *@return: java.lang.String  roleType
	 */
	public java.lang.String getRoleType(){
		return this.roleType;
	}

	/**
	 *方法: 设置roleType
	 *@param: java.lang.String  roleType
	 */
	public void setRoleType(java.lang.String roleType){
		this.roleType = roleType;
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