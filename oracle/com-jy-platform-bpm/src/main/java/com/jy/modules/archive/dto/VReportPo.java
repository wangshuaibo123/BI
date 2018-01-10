package com.jy.modules.archive.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 数据对象
 * @author xyz
 *
 */
public class VReportPo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String   bizType;
	private String   bizInfId;
	private String   proInstanceId;
	private String   startProUserId;
	private String   orgId;
	private Date   createTime;
	private Date   overTime;
	private Date   remindTime;
	private String   activityName;
	private Date   startTime;
	private Date   endTime;
	private Long   duration;
	private String   transition;
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getBizInfId() {
		return bizInfId;
	}
	public void setBizInfId(String bizInfId) {
		this.bizInfId = bizInfId;
	}
	public String getProInstanceId() {
		return proInstanceId;
	}
	public void setProInstanceId(String proInstanceId) {
		this.proInstanceId = proInstanceId;
	}
	public String getStartProUserId() {
		return startProUserId;
	}
	public void setStartProUserId(String startProUserId) {
		this.startProUserId = startProUserId;
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
	public Date getOverTime() {
		return overTime;
	}
	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}
	public Date getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getTransition() {
		return transition;
	}
	public void setTransition(String transition) {
		this.transition = transition;
	}
	
	
}
