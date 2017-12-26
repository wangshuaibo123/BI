package com.fintech.modules.platform.dataprv.sysprvroleauth.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:数据权限角色授权表
 *@author
 *@version 1.0,
 *@date 2014-10-18 16:07:22
 */
public class SysPrvRoleAuthDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**用户ID*/
	private java.lang.Long userId;

	/**角色ID*/
	private java.lang.Long roleId;

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