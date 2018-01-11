package com.jy.modules.archive.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 数据对象
 * @author xyz
 *
 */
public class TaskPo implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long  dbid;
	private Long  dbVersion;
	private String  execution;
	private String  outcome;
	private String  assignee;
	private Long  priority;
	private String  state;
	private Date  createTime;
	private Date  endTime;
	private Long  duration;
	private Long  nextId;
	private Long  superTask;
	public Long getDbid() {
		return dbid;
	}
	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}
	public Long getDbVersion() {
		return dbVersion;
	}
	public void setDbVersion(Long dbVersion) {
		this.dbVersion = dbVersion;
	}
	public String getExecution() {
		return execution;
	}
	public void setExecution(String execution) {
		this.execution = execution;
	}
	public String getOutcome() {
		return outcome;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public Long getNextId() {
		return nextId;
	}
	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}
	public Long getSuperTask() {
		return superTask;
	}
	public void setSuperTask(Long superTask) {
		this.superTask = superTask;
	}
	
	
}
