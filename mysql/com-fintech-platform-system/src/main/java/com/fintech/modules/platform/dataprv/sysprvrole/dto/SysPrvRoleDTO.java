package com.fintech.modules.platform.dataprv.sysprvrole.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:数据权限角色定义
 *@author
 *@version 1.0,
 *@date 2014-10-18 16:07:13
 */
public class SysPrvRoleDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**角色CODE*/
	private java.lang.String roleCode;

	/**角色名称*/
	private java.lang.String roleName;

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

}