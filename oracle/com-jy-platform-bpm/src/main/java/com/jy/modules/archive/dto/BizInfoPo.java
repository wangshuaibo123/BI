package com.jy.modules.archive.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 数据对象
 * @author xyz
 *
 */
public class BizInfoPo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long   id;
	private String   bizTabId;
	private String   proInstanceId;
	private String   taskId;
	private String   activeName;
	private String   activeInfo;
	private String   optionRemark;
	private String   validateState;
	private String   ownerId;
	private String   orgId;
	private Date   createTime;
	private Date   modifyTime;
	private String   createBy;
	private String   modifyBy;
	private String   remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBizTabId() {
		return bizTabId;
	}
	public void setBizTabId(String bizTabId) {
		this.bizTabId = bizTabId;
	}
	public String getProInstanceId() {
		return proInstanceId;
	}
	public void setProInstanceId(String proInstanceId) {
		this.proInstanceId = proInstanceId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public String getActiveInfo() {
		return activeInfo;
	}
	public void setActiveInfo(String activeInfo) {
		this.activeInfo = activeInfo;
	}
	public String getOptionRemark() {
		return optionRemark;
	}
	public void setOptionRemark(String optionRemark) {
		this.optionRemark = optionRemark;
	}
	public String getValidateState() {
		return validateState;
	}
	public void setValidateState(String validateState) {
		this.validateState = validateState;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
