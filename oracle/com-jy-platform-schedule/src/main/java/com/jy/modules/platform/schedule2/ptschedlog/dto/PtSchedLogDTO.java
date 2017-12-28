package com.jy.modules.platform.schedule2.ptschedlog.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:pt_sched_log
 *@author JY-IT-D001
 *@version 1.0,
 *@date 2017-02-07 16:22:25
 */
public class PtSchedLogDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;
	public static final String STATE_SUCCESS="SUCCESS";
	public static final String STATE_ERROR="ERROR";
	public static final String STATE_RUNNING="RUNNING";
	/**id*/
	private java.lang.Long id;

	/**作业名称*/
	private java.lang.String jobName;

	/**触发时间*/
	private java.sql.Timestamp fireTime;
	
	/**业务系统名称*/
	private java.lang.String bizModule;

	/**开始时间*/
	private java.sql.Timestamp startTime;

	/**结束时间*/
	private java.sql.Timestamp endTime;

	/**状态*/
	private java.lang.String state;

	/**线程ID*/
	private java.lang.String threadId;

	/**结果*/
	private java.lang.String result;
	
	
	private String baseExt2;
	
	public String getBaseExt2() {
		return baseExt2;
	}

	public void setBaseExt2(String baseExt2) {
		this.baseExt2 = baseExt2;
	}

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
	 *方法: 获得jobName
	 *@return: java.lang.String  jobName
	 */
	public java.lang.String getJobName(){
		return this.jobName;
	}

	/**
	 *方法: 设置jobName
	 *@param: java.lang.String  jobName
	 */
	public void setJobName(java.lang.String jobName){
		this.jobName = jobName;
	}

	/**
	 *方法: 获得fireTime
	 *@return: java.sql.Timestamp  fireTime
	 */
	public java.sql.Timestamp getFireTime(){
		return this.fireTime;
	}

	/**
	 *方法: 设置fireTime
	 *@param: java.sql.Timestamp  fireTime
	 */
	public void setFireTime(java.sql.Timestamp fireTime){
		this.fireTime = fireTime;
	}

	/**
	 *方法: 获得bizModule
	 *@return: java.lang.String  bizModule
	 */
	public java.lang.String getBizModule(){
		return this.bizModule;
	}

	/**
	 *方法: 设置bizModule
	 *@param: java.lang.String  bizModule
	 */
	public void setBizModule(java.lang.String bizModule){
		this.bizModule = bizModule;
	}

	/**
	 *方法: 获得startTime
	 *@return: java.sql.Timestamp  startTime
	 */
	public java.sql.Timestamp getStartTime(){
		return this.startTime;
	}

	/**
	 *方法: 设置startTime
	 *@param: java.sql.Timestamp  startTime
	 */
	public void setStartTime(java.sql.Timestamp startTime){
		this.startTime = startTime;
	}

	/**
	 *方法: 获得endTime
	 *@return: java.sql.Timestamp  endTime
	 */
	public java.sql.Timestamp getEndTime(){
		return this.endTime;
	}

	/**
	 *方法: 设置endTime
	 *@param: java.sql.Timestamp  endTime
	 */
	public void setEndTime(java.sql.Timestamp endTime){
		this.endTime = endTime;
	}

	/**
	 *方法: 获得state
	 *@return: java.lang.String  state
	 */
	public java.lang.String getState(){
		return this.state;
	}

	/**
	 *方法: 设置state
	 *@param: java.lang.String  state
	 */
	public void setState(java.lang.String state){
		this.state = state;
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
	 *方法: 获得result
	 *@return: java.lang.String  result
	 */
	public java.lang.String getResult(){
		return this.result;
	}

	/**
	 *方法: 设置result
	 *@param: java.lang.String  result
	 */
	public void setResult(java.lang.String result){
		this.result = result;
	}

}