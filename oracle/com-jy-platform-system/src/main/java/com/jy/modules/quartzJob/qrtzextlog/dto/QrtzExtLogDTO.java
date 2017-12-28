package com.jy.modules.quartzJob.qrtzextlog.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:QRTZ_EXT_LOG
 *@author zhangyu
 *@date 2016-11-15 16:08:47
 */
public class QrtzExtLogDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**ID，数据库主键*/
	private java.lang.Long id;

	/**作业名称*/
	private java.lang.String jobName;

	/**触发时间(定时器的触发时间，执行过以后，和“任务查询”功能中的“上次执行时间”相同)*/
	private java.sql.Timestamp fireTime;

	/**执行线程*/
	private java.lang.String threadId;

	/**开始时间*/
	private java.sql.Timestamp startTime;

	/**结束时间*/
	private java.sql.Timestamp endTime;

	/**状态[RUNNING|SUCCESS|ERROR]*/
	private java.lang.String state;

	/**结果[异常信息]*/
	private java.lang.String result;

	@Override
	public String toString() {
		return "QrtzExtLogDTO [id=" + id + ", jobName=" + jobName
				+ ", fireTime=" + fireTime + ", threadId=" + threadId
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", state=" + state + ", result=" + result + "]";
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