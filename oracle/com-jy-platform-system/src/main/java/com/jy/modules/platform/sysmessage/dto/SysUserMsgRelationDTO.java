package com.jy.modules.platform.sysmessage.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:SYS_USER_MSG_RELATION
 *@author lin
 *@version 1.0,
 *@date 2014-11-14 11:08:58
 */
public class SysUserMsgRelationDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long relId;

	/**信息状态：0：有效1：用户删除；default0*/
	private java.lang.String relStatus;

	/**归属者ID 收件人*/
	private java.lang.String userId;

	/**用户读取状态：0:未读；1：已读 default:0*/
	private java.lang.String readFlag;

	/**消息ID*/
	private java.lang.Long msgId;
	
	/**收件人姓名显示**/
	private String userNameShow;
	

	/**
	 *方法: 获得relId
	 *@return: java.lang.Long  relId
	 */
	public java.lang.Long getRelId(){
		return this.relId;
	}

	/**
	 *方法: 设置relId
	 *@param: java.lang.Long  relId
	 */
	public void setRelId(java.lang.Long relId){
		this.relId = relId;
	}

	/**
	 *方法: 获得relStatus
	 *@return: java.lang.String  relStatus
	 */
	public java.lang.String getRelStatus(){
		return this.relStatus;
	}

	/**
	 *方法: 设置relStatus
	 *@param: java.lang.String  relStatus
	 */
	public void setRelStatus(java.lang.String relStatus){
		this.relStatus = relStatus;
	}

	/**
	 *方法: 获得userId
	 *@return: java.lang.String  userId
	 */
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置userId
	 *@param: java.lang.String  userId
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}

	/**
	 *方法: 获得readFlag
	 *@return: java.lang.String  readFlag
	 */
	public java.lang.String getReadFlag(){
		return this.readFlag;
	}

	/**
	 *方法: 设置readFlag
	 *@param: java.lang.String  readFlag
	 */
	public void setReadFlag(java.lang.String readFlag){
		this.readFlag = readFlag;
	}

	/**
	 *方法: 获得msgId
	 *@return: java.lang.Long  msgId
	 */
	public java.lang.Long getMsgId(){
		return this.msgId;
	}

	/**
	 *方法: 设置msgId
	 *@param: java.lang.Long  msgId
	 */
	public void setMsgId(java.lang.Long msgId){
		this.msgId = msgId;
	}
	/**收件人姓名显示**/
	public String getUserNameShow() {
		return userNameShow;
	}
	/**收件人姓名显示**/
	public void setUserNameShow(String userNameShow) {
		this.userNameShow = userNameShow;
	}

}