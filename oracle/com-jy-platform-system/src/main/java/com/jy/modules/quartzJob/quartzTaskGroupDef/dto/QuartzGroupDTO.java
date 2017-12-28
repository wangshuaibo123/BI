package com.jy.modules.quartzJob.quartzTaskGroupDef.dto;

import java.util.List;

import com.jy.platform.core.common.BaseDTO;

public class QuartzGroupDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private String groupId;

	private String groupName;

	private String groupState;

	private String autoExec;

	private String dealChance;
	
	private String isStop;
	
	private String runTime;
	
	private String taskStartTime;
	
	private String taskEndTime;
	
	private String taskInsState;
	
	private String created;

	public String getIsStop() {
		return isStop;
	}

	public void setIsStop(String isStop) {
		this.isStop = isStop;
	}

	private List<QuartzTaskGroupDefDTO> taskList;
	
	public String getAutoExec() {
		return autoExec;
	}

	public void setAutoExec(String autoExec) {
		this.autoExec = autoExec;
	}

	public String getDealChance() {
		return dealChance;
	}

	public void setDealChance(String dealChance) {
		this.dealChance = dealChance;
	}


	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupState() {
		return groupState;
	}

	public void setGroupState(String groupState) {
		this.groupState = groupState;
	}

	public List<QuartzTaskGroupDefDTO> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<QuartzTaskGroupDefDTO> taskList) {
		this.taskList = taskList;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(String taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public String getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(String taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public String getTaskInsState() {
		return taskInsState;
	}

	public void setTaskInsState(String taskInsState) {
		this.taskInsState = taskInsState;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
