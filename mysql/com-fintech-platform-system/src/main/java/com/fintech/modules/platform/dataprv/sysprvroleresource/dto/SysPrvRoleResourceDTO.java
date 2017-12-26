package com.fintech.modules.platform.dataprv.sysprvroleresource.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:数据角色资源表
 *@author
 *@version 1.0,
 *@date 2014-10-18 16:07:31
 */
public class SysPrvRoleResourceDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**角色Id*/
	private java.lang.Long roleId;

	/**资源ID*/
	private java.lang.Long resourceId;

	/**资源类型，机构org，用户user*/
	private java.lang.String resourceType;

	/**操作状态，插入I，删除D*/
	private java.lang.String actionState;

	/**同步状态，未同步0，已同步1*/
	private java.lang.String synState;

	/**有效状态，1有效，0无效，默认1*/
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
	 *方法: 获得resourceId
	 *@return: java.lang.Long  resourceId
	 */
	public java.lang.Long getResourceId(){
		return this.resourceId;
	}

	/**
	 *方法: 设置resourceId
	 *@param: java.lang.Long  resourceId
	 */
	public void setResourceId(java.lang.Long resourceId){
		this.resourceId = resourceId;
	}

	/**
	 *方法: 获得resourceType
	 *@return: java.lang.String  resourceType
	 */
	public java.lang.String getResourceType(){
		return this.resourceType;
	}

	/**
	 *方法: 设置resourceType
	 *@param: java.lang.String  resourceType
	 */
	public void setResourceType(java.lang.String resourceType){
		this.resourceType = resourceType;
	}

	/**
	 *方法: 获得actionState
	 *@return: java.lang.String  actionState
	 */
	public java.lang.String getActionState(){
		return this.actionState;
	}

	/**
	 *方法: 设置actionState
	 *@param: java.lang.String  actionState
	 */
	public void setActionState(java.lang.String actionState){
		this.actionState = actionState;
	}

	/**
	 *方法: 获得synState
	 *@return: java.lang.String  synState
	 */
	public java.lang.String getSynState(){
		return this.synState;
	}

	/**
	 *方法: 设置synState
	 *@param: java.lang.String  synState
	 */
	public void setSynState(java.lang.String synState){
		this.synState = synState;
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