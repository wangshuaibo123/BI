package com.fintech.modules.platform.sysasynjob.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:异步接口任务表
 *@author DELL
 *@version 1.0,
 *@date 2016-09-12 14:55:26
 */
public class SysAsynJobDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private java.lang.Long id;

	/**业务实体beanId*/
	private java.lang.String beanId;

	/**业务表主键ID*/
	private java.lang.String bizKeyId;

	/**任务状态（1：待调用，0：已完成）*/
	private java.lang.String jobState;

	/**任务调用开始时间*/
	private java.sql.Timestamp startTime;

	/**任务调用结束时间*/
	private java.sql.Timestamp endTime;

	/**任务异常描述*/
	private java.lang.String errorRemark;

	/**数据有效性(1：有效，0：无效)*/
	private java.lang.String valid;

	/**任务是否正在执行中 1：执行中，0:执行完成 或 未执行*/
	private java.lang.String jobRun;

	/**重复执行次数*/
	private java.lang.Long runCun;

	/**数据新增时间*/
	private java.sql.Timestamp createTime;

	/**备注*/
	private java.lang.String remark;

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
	 *方法: 获得bizKeyId
	 *@return: java.lang.String  bizKeyId
	 */
	public java.lang.String getBizKeyId(){
		return this.bizKeyId;
	}

	/**
	 *方法: 设置bizKeyId
	 *@param: java.lang.String  bizKeyId
	 */
	public void setBizKeyId(java.lang.String bizKeyId){
		this.bizKeyId = bizKeyId;
	}

	/**
	 *方法: 获得jobState
	 *@return: java.lang.String  jobState
	 */
	public java.lang.String getJobState(){
		return this.jobState;
	}

	/**
	 *方法: 设置jobState
	 *@param: java.lang.String  jobState
	 */
	public void setJobState(java.lang.String jobState){
		this.jobState = jobState;
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
	 *方法: 获得errorRemark
	 *@return: java.lang.String  errorRemark
	 */
	public java.lang.String getErrorRemark(){
		return this.errorRemark;
	}

	/**
	 *方法: 设置errorRemark
	 *@param: java.lang.String  errorRemark
	 */
	public void setErrorRemark(java.lang.String errorRemark){
		this.errorRemark = errorRemark;
	}

	/**
	 *方法: 获得valid
	 *@return: java.lang.String  valid
	 */
	public java.lang.String getValid(){
		return this.valid;
	}

	/**
	 *方法: 设置valid
	 *@param: java.lang.String  valid
	 */
	public void setValid(java.lang.String valid){
		this.valid = valid;
	}

	/**
	 *方法: 获得jobRun
	 *@return: java.lang.String  jobRun
	 */
	public java.lang.String getJobRun(){
		return this.jobRun;
	}

	/**
	 *方法: 设置jobRun
	 *@param: java.lang.String  jobRun
	 */
	public void setJobRun(java.lang.String jobRun){
		this.jobRun = jobRun;
	}

	/**
	 *方法: 获得runCun
	 *@return: java.lang.Long  runCun
	 */
	public java.lang.Long getRunCun(){
		return this.runCun;
	}

	/**
	 *方法: 设置runCun
	 *@param: java.lang.Long  runCun
	 */
	public void setRunCun(java.lang.Long runCun){
		this.runCun = runCun;
	}

	/**
	 *方法: 获得createTime
	 *@return: java.sql.Timestamp  createTime
	 */
	public java.sql.Timestamp getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置createTime
	 *@param: java.sql.Timestamp  createTime
	 */
	public void setCreateTime(java.sql.Timestamp createTime){
		this.createTime = createTime;
	}

	/**
	 *方法: 获得remark
	 *@return: java.lang.String  remark
	 */
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置remark
	 *@param: java.lang.String  remark
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}

}