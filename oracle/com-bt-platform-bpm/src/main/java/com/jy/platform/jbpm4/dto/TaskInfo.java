package com.jy.platform.jbpm4.dto;

import java.io.Serializable;
/**
 * 任务信息
 * @author JY-IT-D001
 *
 */
public class TaskInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String taskNo;
	private String taskName;
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public TaskInfo(String taskNo, String taskName) {
		super();
		this.taskNo = taskNo;
		this.taskName = taskName;
	}
	
}
