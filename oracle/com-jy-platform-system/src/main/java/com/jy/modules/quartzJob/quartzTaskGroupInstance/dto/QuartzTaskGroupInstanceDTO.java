package com.jy.modules.quartzJob.quartzTaskGroupInstance.dto;

import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.mybatis.MyBatisDomain;
/**
 *@Description:分组任务实例表
 *@author chen_gang
 *@version 1.0,
 *@date 2014-10-14 21:37:38
 */
@MyBatisDomain
public class QuartzTaskGroupInstanceDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private java.lang.Long id;

	/**分组处理编号*/
	private java.lang.String groupId;

	/**分组处理名称*/
	private java.lang.String groupName;

	/**批次号码*/
	private java.lang.String batchNo;

	/**任务编号*/
	private java.lang.String taskId;

	/**任务描述*/
	private java.lang.String taskName;

	/**任务类名（实体beanID）*/
	private java.lang.String beanId;

	/**执行步骤(顺序序号)*/
	private java.lang.Long dealStep;

	/**前提步骤（任务编号）*/
	private java.lang.String preStep;

	/**前置步骤返回状态（1：成功，0：是失败）*/
	private java.lang.String preStepState;

	/**是否自动执行（1：自动，0：手动）*/
	private java.lang.String autoExec;

	/**执行时机（day:每天，year：每年12月31日，norun:不执行）*/
	private java.lang.String dealChance;

	/**任务实例执行状态（1：成功：0：失败）*/
	private java.lang.String taskInsState;

	/**失败后，断点执行（1：是，0：否）*/
	private java.lang.String bugContinue;

	/**任务是否执行完成（0：完成，1：未完成）*/
	private java.lang.String isEnd;

	/**数据有效性（1：有效、0：无效）*/
	private java.lang.String validateState;

	/**新增时间*/
	private java.util.Date created;

	/**修改时间*/
	private java.util.Date lastUpd;

	/**备注*/
	private java.lang.String remark;
	
	/**任务开始时间*/
	private String taskStartTime;
	
	/**任务执行结束*/
	private String taskEndTime;

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
	 *方法: 获得groupName
	 *@return: java.lang.String  groupName
	 */
	public java.lang.String getGroupName(){
		return this.groupName;
	}

	/**
	 *方法: 设置groupName
	 *@param: java.lang.String  groupName
	 */
	public void setGroupName(java.lang.String groupName){
		this.groupName = groupName;
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
	 *方法: 获得taskName
	 *@return: java.lang.String  taskName
	 */
	public java.lang.String getTaskName(){
		return this.taskName;
	}

	/**
	 *方法: 设置taskName
	 *@param: java.lang.String  taskName
	 */
	public void setTaskName(java.lang.String taskName){
		this.taskName = taskName;
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
	 *方法: 获得dealStep
	 *@return: java.lang.Long  dealStep
	 */
	public java.lang.Long getDealStep(){
		return this.dealStep;
	}

	/**
	 *方法: 设置dealStep
	 *@param: java.lang.Long  dealStep
	 */
	public void setDealStep(java.lang.Long dealStep){
		this.dealStep = dealStep;
	}

	/**
	 *方法: 获得preStep
	 *@return: java.lang.String  preStep
	 */
	public java.lang.String getPreStep(){
		return this.preStep;
	}

	/**
	 *方法: 设置preStep
	 *@param: java.lang.String  preStep
	 */
	public void setPreStep(java.lang.String preStep){
		this.preStep = preStep;
	}

	/**
	 *方法: 获得preStepState
	 *@return: java.lang.String  preStepState
	 */
	public java.lang.String getPreStepState(){
		return this.preStepState;
	}

	/**
	 *方法: 设置preStepState
	 *@param: java.lang.String  preStepState
	 */
	public void setPreStepState(java.lang.String preStepState){
		this.preStepState = preStepState;
	}

	/**
	 *方法: 获得autoExec
	 *@return: java.lang.String  autoExec
	 */
	public java.lang.String getAutoExec(){
		return this.autoExec;
	}

	/**
	 *方法: 设置autoExec
	 *@param: java.lang.String  autoExec
	 */
	public void setAutoExec(java.lang.String autoExec){
		this.autoExec = autoExec;
	}

	/**
	 *方法: 获得dealChance
	 *@return: java.lang.String  dealChance
	 */
	public java.lang.String getDealChance(){
		return this.dealChance;
	}

	/**
	 *方法: 设置dealChance
	 *@param: java.lang.String  dealChance
	 */
	public void setDealChance(java.lang.String dealChance){
		this.dealChance = dealChance;
	}

	/**
	 *方法: 获得taskInsState
	 *@return: java.lang.String  taskInsState
	 */
	public java.lang.String getTaskInsState(){
		return this.taskInsState;
	}

	/**
	 *方法: 设置taskInsState
	 *@param: java.lang.String  taskInsState
	 */
	public void setTaskInsState(java.lang.String taskInsState){
		this.taskInsState = taskInsState;
	}

	/**
	 *方法: 获得bugContinue
	 *@return: java.lang.String  bugContinue
	 */
	public java.lang.String getBugContinue(){
		return this.bugContinue;
	}

	/**
	 *方法: 设置bugContinue
	 *@param: java.lang.String  bugContinue
	 */
	public void setBugContinue(java.lang.String bugContinue){
		this.bugContinue = bugContinue;
	}

	/**
	 *方法: 获得isEnd
	 *@return: java.lang.String  isEnd
	 */
	public java.lang.String getIsEnd(){
		return this.isEnd;
	}

	/**
	 *方法: 设置isEnd
	 *@param: java.lang.String  isEnd
	 */
	public void setIsEnd(java.lang.String isEnd){
		this.isEnd = isEnd;
	}

	/**
	 *方法: 获得validateState
	 *@return: java.lang.String  validateState
	 */
	public java.lang.String getValidateState(){
		return this.validateState;
	}

	/**
	 *方法: 设置validateState
	 *@param: java.lang.String  validateState
	 */
	public void setValidateState(java.lang.String validateState){
		this.validateState = validateState;
	}

	/**
	 *方法: 获得created
	 *@return: java.util.Date  created
	 */
	public java.util.Date getCreated(){
		return this.created;
	}

	/**
	 *方法: 设置created
	 *@param: java.util.Date  created
	 */
	public void setCreated(java.util.Date created){
		this.created = created;
	}

	/**
	 *方法: 获得lastUpd
	 *@return: java.util.Date  lastUpd
	 */
	public java.util.Date getLastUpd(){
		return this.lastUpd;
	}

	/**
	 *方法: 设置lastUpd
	 *@param: java.util.Date  lastUpd
	 */
	public void setLastUpd(java.util.Date lastUpd){
		this.lastUpd = lastUpd;
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

}