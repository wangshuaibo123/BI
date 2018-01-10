package com.fintech.modules.archive.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * @author
 *
 */
public class JBPM4BizTabPo  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private java.lang.String bizInfNo;
	

	/**主键ID*/
	private java.lang.Long id;

	/**业务表名称*/
	private java.lang.String bizTabName;

	/**业务类型*/
	private java.lang.String bizType;

	/**业务表主键ID*/
	private java.lang.String bizInfId;
	
	/**业务任务名称*/
	private java.lang.String bizInfName;

	/**业务任务状态*/
	private java.lang.String bizTaskType;

	/**主流程实例ID*/
	private java.lang.String proInstanceId;

	/**流程发起者*/
	private java.lang.String startProUserid;

	/**流程实例状态（-1：异常终止，0：暂停，1：正常）*/
	private java.lang.String proInstanceState;

	/**当前流程实例下的任务状态（0：解锁，1：加锁）*/
	private java.lang.String taskState;

	/**数据有效性（1：有效、0：无效）*/
	private java.lang.String validateState;

	/**业务归属人*/
	private java.lang.String ownerId;

	/**业务所属机构
*/
	private java.lang.String orgId;

	/**创建时间
*/
	private Date createTime;

	/**修改时间
*/
	private java.util.Date modifyTime;

	/**创建人
*/
	private java.lang.String createBy;

	/**修改人
*/
	private java.lang.String modifyBy;

	/**备注*/
	private java.lang.String remark;

	/**是否隐藏待办*/
	private java.lang.String isHidden;
	
	/**超时时间*/
	private java.util.Date overTime;
	
	/**提前提醒时间*/
	private java.util.Date remindTime;
	
	
	
	public java.lang.Long getId() {
		return id;
	}
	public void setId(java.lang.Long id) {
		this.id = id;
	}
	public java.lang.String getBizTabName() {
		return bizTabName;
	}
	public void setBizTabName(java.lang.String bizTabName) {
		this.bizTabName = bizTabName;
	}
	public java.lang.String getBizType() {
		return bizType;
	}
	public void setBizType(java.lang.String bizType) {
		this.bizType = bizType;
	}
	public java.lang.String getBizInfId() {
		return bizInfId;
	}
	public void setBizInfId(java.lang.String bizInfId) {
		this.bizInfId = bizInfId;
	}
	public java.lang.String getBizInfName() {
		return bizInfName;
	}
	public void setBizInfName(java.lang.String bizInfName) {
		this.bizInfName = bizInfName;
	}
	public java.lang.String getBizTaskType() {
		return bizTaskType;
	}
	public void setBizTaskType(java.lang.String bizTaskType) {
		this.bizTaskType = bizTaskType;
	}
	public java.lang.String getProInstanceId() {
		return proInstanceId;
	}
	public void setProInstanceId(java.lang.String proInstanceId) {
		this.proInstanceId = proInstanceId;
	}
	public java.lang.String getStartProUserid() {
		return startProUserid;
	}
	public void setStartProUserid(java.lang.String startProUserid) {
		this.startProUserid = startProUserid;
	}
	public java.lang.String getProInstanceState() {
		return proInstanceState;
	}
	public void setProInstanceState(java.lang.String proInstanceState) {
		this.proInstanceState = proInstanceState;
	}
	public java.lang.String getTaskState() {
		return taskState;
	}
	public void setTaskState(java.lang.String taskState) {
		this.taskState = taskState;
	}
	public java.lang.String getValidateState() {
		return validateState;
	}
	public void setValidateState(java.lang.String validateState) {
		this.validateState = validateState;
	}
	public java.lang.String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(java.lang.String ownerId) {
		this.ownerId = ownerId;
	}
	public java.lang.String getOrgId() {
		return orgId;
	}
	public void setOrgId(java.lang.String orgId) {
		this.orgId = orgId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public java.lang.String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}
	public java.lang.String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(java.lang.String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	public java.lang.String getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(java.lang.String isHidden) {
		this.isHidden = isHidden;
	}
	public java.util.Date getOverTime() {
		return overTime;
	}
	public void setOverTime(java.util.Date overTime) {
		this.overTime = overTime;
	}
	public java.util.Date getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(java.util.Date remindTime) {
		this.remindTime = remindTime;
	}
	public java.lang.String getBizInfNo() {
		return bizInfNo;
	}
	public void setBizInfNo(java.lang.String bizInfNo) {
		this.bizInfNo = bizInfNo;
	}
	
}
