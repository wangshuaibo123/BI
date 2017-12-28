package com.jy.modules.platform.quartztaskhis.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:定时任务执行轨迹表
 *@author lei
 *@version 1.0,
 *@date 2014-12-24 10:12:17
 */
public class QuartzTaskHisDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private java.lang.Long id;

	/**批次号码*/
	private java.lang.String batchNo;

	/**分组编码*/
	private java.lang.String groupId;

	/**执行任务的主线程ID*/
	private java.lang.String threadId;

	/**任务编号*/
	private java.lang.String taskId;

	/**任务类名（实体beanID）*/
	private java.lang.String beanId;

	/**任务执行返回结果（1：成功，0：是失败）*/
	private java.lang.String taskState;

	/**任务执行开始时间*/
	private String taskStartTime;

	/**任务执行结束时间*/
	private String taskEndTime;

	/**任务执行中信息描述*/
	private java.lang.String taskInfo;

	/**任务执行失败，错误描述*/
	private java.lang.String errorInfo;

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
	 *方法: 获得batchNo
	 *@return: java.lang.String  batchNo
	 */
	public java.lang.String getBatchNo(){
		return this.batchNo;
	}

	/**
	 *方法: 设置batchNo
	 *@param: java.lang.String  batchNo
	 */
	public void setBatchNo(java.lang.String batchNo){
		this.batchNo = batchNo;
	}

	/**
	 *方法: 获得groupId
	 *@return: java.lang.String  groupId
	 */
	public java.lang.String getGroupId(){
		return this.groupId;
	}

	/**
	 *方法: 设置groupId
	 *@param: java.lang.String  groupId
	 */
	public void setGroupId(java.lang.String groupId){
		this.groupId = groupId;
	}

	/**
	 *方法: 获得threadId
	 *@return: java.lang.String  threadId
	 */
	public java.lang.String getThreadId(){
		return this.threadId;
	}

	/**
	 *方法: 设置threadId
	 *@param: java.lang.String  threadId
	 */
	public void setThreadId(java.lang.String threadId){
		this.threadId = threadId;
	}

	/**
	 *方法: 获得taskId
	 *@return: java.lang.String  taskId
	 */
	public java.lang.String getTaskId(){
		return this.taskId;
	}

	/**
	 *方法: 设置taskId
	 *@param: java.lang.String  taskId
	 */
	public void setTaskId(java.lang.String taskId){
		this.taskId = taskId;
	}

	/**
	 *方法: 获得beanId
	 *@return: java.lang.String  beanId
	 */
	public java.lang.String getBeanId(){
		return this.beanId;
	}

	/**
	 *方法: 设置beanId
	 *@param: java.lang.String  beanId
	 */
	public void setBeanId(java.lang.String beanId){
		this.beanId = beanId;
	}

	/**
	 *方法: 获得taskState
	 *@return: java.lang.String  taskState
	 */
	public java.lang.String getTaskState(){
		return this.taskState;
	}

	/**
	 *方法: 设置taskState
	 *@param: java.lang.String  taskState
	 */
	public void setTaskState(java.lang.String taskState){
		this.taskState = taskState;
	}

	/**
	 *方法: 获得taskStartTime
	 *@return: String  taskStartTime
	 */
	public String getTaskStartTime(){
		return this.taskStartTime;
	}

	/**
	 *方法: 设置taskStartTime
	 *@param: String  taskStartTime
	 */
	public void setTaskStartTime(String taskStartTime){
		this.taskStartTime = taskStartTime;
	}

	/**
	 *方法: 获得taskEndTime
	 *@return: String  taskEndTime
	 */
	public String getTaskEndTime(){
		return this.taskEndTime;
	}

	/**
	 *方法: 设置taskEndTime
	 *@param: String  taskEndTime
	 */
	public void setTaskEndTime(String taskEndTime){
		this.taskEndTime = taskEndTime;
	}

	/**
	 *方法: 获得taskInfo
	 *@return: java.lang.String  taskInfo
	 */
	public java.lang.String getTaskInfo(){
		return this.taskInfo;
	}

	/**
	 *方法: 设置taskInfo
	 *@param: java.lang.String  taskInfo
	 */
	public void setTaskInfo(java.lang.String taskInfo){
		this.taskInfo = taskInfo;
	}

	/**
	 *方法: 获得errorInfo
	 *@return: java.lang.String  errorInfo
	 */
	public java.lang.String getErrorInfo(){
		return this.errorInfo;
	}

	/**
	 *方法: 设置errorInfo
	 *@param: java.lang.String  errorInfo
	 */
	public void setErrorInfo(java.lang.String errorInfo){
		this.errorInfo = errorInfo;
	}

}