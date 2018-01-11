package com.fintech.platform.jbpm4.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:工作流角色映射表
 *@author
 *@version 1.0,
 *@date 2015-06-05 10:32:28
 */
/**
 * @author
 *
 */
public class Jbpm4RoleMappingDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private java.lang.Long id;

	/**角色ID*/
	private java.lang.String roleCode;
	
	private java.lang.String roleCodes;
	
	private java.lang.String roleCodeName;

	/**映射角色ID*/
	private java.lang.String mappingRoleCode;
	
	private java.lang.String mappingRoleCodeName;

	/**数据有效性（1：有效、0：无效）*/
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
	 *方法: 获得mappingRoleCode
	 *@return: java.lang.String  mappingRoleCode
	 */
	public java.lang.String getMappingRoleCode(){
		return this.mappingRoleCode;
	}

	/**
	 *方法: 设置mappingRoleCode
	 *@param: java.lang.String  mappingRoleCode
	 */
	public void setMappingRoleCode(java.lang.String mappingRoleCode){
		this.mappingRoleCode = mappingRoleCode;
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

	public java.lang.String getRoleCodeName() {
		return roleCodeName;
	}

	public void setRoleCodeName(java.lang.String roleCodeName) {
		this.roleCodeName = roleCodeName;
	}

	public java.lang.String getMappingRoleCodeName() {
		return mappingRoleCodeName;
	}

	public void setMappingRoleCodeName(java.lang.String mappingRoleCodeName) {
		this.mappingRoleCodeName = mappingRoleCodeName;
	}

	public java.lang.String getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(java.lang.String roleCodes) {
		this.roleCodes = roleCodes;
	}
	
	

}