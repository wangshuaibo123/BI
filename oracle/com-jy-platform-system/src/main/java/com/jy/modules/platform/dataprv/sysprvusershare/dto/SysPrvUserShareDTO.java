package com.jy.modules.platform.dataprv.sysprvusershare.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:数据共享表
 *@author wangxz
 *@version 1.0,
 *@date 2014-10-18 16:07:49
 */
public class SysPrvUserShareDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**共享用户ID*/
	private java.lang.Long fromUserId;

	/**被共享用户ID*/
	private java.lang.Long toUserId;

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
	 *方法: 获得fromUserId
	 *@return: java.lang.Long  fromUserId
	 */
	public java.lang.Long getFromUserId(){
		return this.fromUserId;
	}

	/**
	 *方法: 设置fromUserId
	 *@param: java.lang.Long  fromUserId
	 */
	public void setFromUserId(java.lang.Long fromUserId){
		this.fromUserId = fromUserId;
	}

	/**
	 *方法: 获得toUserId
	 *@return: java.lang.Long  toUserId
	 */
	public java.lang.Long getToUserId(){
		return this.toUserId;
	}

	/**
	 *方法: 设置toUserId
	 *@param: java.lang.Long  toUserId
	 */
	public void setToUserId(java.lang.Long toUserId){
		this.toUserId = toUserId;
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