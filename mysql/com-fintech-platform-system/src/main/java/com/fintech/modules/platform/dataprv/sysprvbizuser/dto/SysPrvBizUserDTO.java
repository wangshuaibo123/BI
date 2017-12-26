package com.fintech.modules.platform.dataprv.sysprvbizuser.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:业务数据用户权限表
 *@author
 *@version 1.0,
 *@date 2014-10-18 16:05:10
 */
public class SysPrvBizUserDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**用户ID*/
	private java.lang.Long userId;

	/**机构ID*/
	private java.lang.Long orgId;

	/**业务ID*/
	private java.lang.Long bizId;

	/**业务表名*/
	private java.lang.String tableName;

	/**操作状态，插入I，删除D*/
	private java.lang.String actionState;

	/**同步状态，未同步0，已同步1*/
	private java.lang.String synState;

	/**有效状态，1有效，0无效，默认1*/
	private java.lang.String validateState;
	
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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
	 *方法: 获得orgId
	 *@return: java.lang.Long  orgId
	 */
	public java.lang.Long getOrgId(){
		return this.orgId;
	}

	/**
	 *方法: 设置orgId
	 *@param: java.lang.Long  orgId
	 */
	public void setOrgId(java.lang.Long orgId){
		this.orgId = orgId;
	}

	/**
	 *方法: 获得bizId
	 *@return: java.lang.Long  bizId
	 */
	public java.lang.Long getBizId(){
		return this.bizId;
	}

	/**
	 *方法: 设置bizId
	 *@param: java.lang.Long  bizId
	 */
	public void setBizId(java.lang.Long bizId){
		this.bizId = bizId;
	}

	/**
	 *方法: 获得tableName
	 *@return: java.lang.String  tableName
	 */
	public java.lang.String getTableName(){
		return this.tableName;
	}

	/**
	 *方法: 设置tableName
	 *@param: java.lang.String  tableName
	 */
	public void setTableName(java.lang.String tableName){
		this.tableName = tableName;
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