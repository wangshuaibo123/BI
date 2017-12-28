package com.jy.modules.archive.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 数据对象
 * @author xyz
 *
 */
public class ActInstancePo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long    id;
	private String    classStr;
	private Long    dbVersion;
	private Long    processInstanId;
	private String    type;
	private String    execution;
	private String    activityName;
	private Date    start;
	private Date    end;
	private Long    duration;
	private String    transition;
	private Long    nextId;
	private Long    taskId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClassStr() {
		return classStr;
	}
	public void setClassStr(String classStr) {
		this.classStr = classStr;
	}
	public Long getDbVersion() {
		return dbVersion;
	}
	public void setDbVersion(Long dbVersion) {
		this.dbVersion = dbVersion;
	}
	public Long getProcessInstanId() {
		return processInstanId;
	}
	public void setProcessInstanId(Long processInstanId) {
		this.processInstanId = processInstanId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExecution() {
		return execution;
	}
	public void setExecution(String execution) {
		this.execution = execution;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
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
	public Long getNextId() {
		return nextId;
	}
	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
}
