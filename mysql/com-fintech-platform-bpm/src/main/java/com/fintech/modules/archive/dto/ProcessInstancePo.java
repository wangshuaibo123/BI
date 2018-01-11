package com.fintech.modules.archive.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * 数据对象
 * @author
 *
 */
public class ProcessInstancePo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long  dbid;
	private Long  dbVersion;
	private String  id;
	private String  procDefId;
	private String  key;
	private Date  startTime;
	private Date  endTime;
	private Long  duration;
	private String  state;
	private String  endActivity;
	private Long  nextId;
	private String  mainId;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEndActivity() {
		return endActivity;
	}
	public void setEndActivity(String endActivity) {
		this.endActivity = endActivity;
	}
	public Long getNextId() {
		return nextId;
	}
	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}
	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	
}
