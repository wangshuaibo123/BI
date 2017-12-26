package com.fintech.modules.platform.leave.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:员工请假表
 *@author
 *@version 1.0,
 *@date 2014-12-03 17:59:31
 */
public class LeaveInfoDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private java.lang.Long id;

	/**请假人ID*/
	private java.lang.String leaveUserId;

	/**员工职级*/
	private java.lang.String userLevel;

	/**员工姓名*/
	private java.lang.String userName;

	/**业务所属机构
*/
	private java.lang.String orgName;

	/**事由*/
	private java.lang.String reason;

	/**电子邮箱*/
	private java.lang.String email;

	/**状态（1：正常   2：请假中）*/
	private java.lang.String status;

	/**请假开始时间*/
	private java.util.Date startTime;

	/**请假结束时间*/
	private java.util.Date endTime;

	/**请假类型（1：代理请假，2：本人请假）*/
	private java.lang.String leaveType;

	/**数据有效性（1：有效、0：无效）*/
	private java.lang.String validateState;

	/**业务归属人*/
	private java.lang.Long ownerId;

	/**创建时间
*/
	private java.sql.Timestamp createTime;

	/**修改时间
*/
	private java.sql.Timestamp modifyTime;

	/**创建人
*/
	private java.lang.Long createBy;

	/**备注*/
	private java.lang.String remark;

	/**修改人
*/
	private java.lang.Long modifyBy;

	/**业务所属机构ID
*/
	private java.lang.Long orgId;

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
	 *方法: 获得leaveUserId
	 *@return: java.lang.String  leaveUserId
	 */
	public java.lang.String getLeaveUserId(){
		return this.leaveUserId;
	}

	/**
	 *方法: 设置leaveUserId
	 *@param: java.lang.String  leaveUserId
	 */
	public void setLeaveUserId(java.lang.String leaveUserId){
		this.leaveUserId = leaveUserId;
	}

	/**
	 *方法: 获得userLevel
	 *@return: java.lang.String  userLevel
	 */
	public java.lang.String getUserLevel(){
		return this.userLevel;
	}

	/**
	 *方法: 设置userLevel
	 *@param: java.lang.String  userLevel
	 */
	public void setUserLevel(java.lang.String userLevel){
		this.userLevel = userLevel;
	}

	/**
	 *方法: 获得userName
	 *@return: java.lang.String  userName
	 */
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置userName
	 *@param: java.lang.String  userName
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}

	/**
	 *方法: 获得orgName
	 *@return: java.lang.String  orgName
	 */
	public java.lang.String getOrgName(){
		return this.orgName;
	}

	/**
	 *方法: 设置orgName
	 *@param: java.lang.String  orgName
	 */
	public void setOrgName(java.lang.String orgName){
		this.orgName = orgName;
	}

	/**
	 *方法: 获得reason
	 *@return: java.lang.String  reason
	 */
	public java.lang.String getReason(){
		return this.reason;
	}

	/**
	 *方法: 设置reason
	 *@param: java.lang.String  reason
	 */
	public void setReason(java.lang.String reason){
		this.reason = reason;
	}

	/**
	 *方法: 获得email
	 *@return: java.lang.String  email
	 */
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置email
	 *@param: java.lang.String  email
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}

	/**
	 *方法: 获得status
	 *@return: java.lang.String  status
	 */
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置status
	 *@param: java.lang.String  status
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}

	/**
	 *方法: 获得startTime
	 *@return: java.util.Date  startTime
	 */
	public java.util.Date getStartTime(){
		return this.startTime;
	}

	/**
	 *方法: 设置startTime
	 *@param: java.util.Date  startTime
	 */
	public void setStartTime(java.util.Date startTime){
		this.startTime = startTime;
	}

	/**
	 *方法: 获得endTime
	 *@return: java.util.Date  endTime
	 */
	public java.util.Date getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置endTime
	 *@param: java.util.Date  endTime
	 */
	public void setEndTime(java.util.Date endTime){
		this.endTime = endTime;
	}

	/**
	 *方法: 获得leaveType
	 *@return: java.lang.String  leaveType
	 */
	public java.lang.String getLeaveType(){
		return this.leaveType;
	}

	/**
	 *方法: 设置leaveType
	 *@param: java.lang.String  leaveType
	 */
	public void setLeaveType(java.lang.String leaveType){
		this.leaveType = leaveType;
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
	 *方法: 获得ownerId
	 *@return: java.lang.Long  ownerId
	 */
	public java.lang.Long getOwnerId(){
		return this.ownerId;
	}

	/**
	 *方法: 设置ownerId
	 *@param: java.lang.Long  ownerId
	 */
	public void setOwnerId(java.lang.Long ownerId){
		this.ownerId = ownerId;
	}

	/**
	 *方法: 获得createTime
	 *@return: java.sql.Timestamp  createTime
	 */
	public java.sql.Timestamp getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置createTime
	 *@param: java.sql.Timestamp  createTime
	 */
	public void setCreateTime(java.sql.Timestamp createTime){
		this.createTime = createTime;
	}

	/**
	 *方法: 获得modifyTime
	 *@return: java.sql.Timestamp  modifyTime
	 */
	public java.sql.Timestamp getModifyTime(){
		return this.modifyTime;
	}

	/**
	 *方法: 设置modifyTime
	 *@param: java.sql.Timestamp  modifyTime
	 */
	public void setModifyTime(java.sql.Timestamp modifyTime){
		this.modifyTime = modifyTime;
	}

	/**
	 *方法: 获得createBy
	 *@return: java.lang.Long  createBy
	 */
	public java.lang.Long getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置createBy
	 *@param: java.lang.Long  createBy
	 */
	public void setCreateBy(java.lang.Long createBy){
		this.createBy = createBy;
	}

	/**
	 *方法: 获得remark
	 *@return: java.lang.String  remark
	 */
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置remark
	 *@param: java.lang.String  remark
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}

	/**
	 *方法: 获得modifyBy
	 *@return: java.lang.Long  modifyBy
	 */
	public java.lang.Long getModifyBy(){
		return this.modifyBy;
	}

	/**
	 *方法: 设置modifyBy
	 *@param: java.lang.Long  modifyBy
	 */
	public void setModifyBy(java.lang.Long modifyBy){
		this.modifyBy = modifyBy;
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

}