package com.fintech.modules.quartzJob.quartzTaskGroupDef.dto;

import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.mybatis.MyBatisDomain;
/**
 *@Description:任务分组定义表
 *@author
 *@version 1.0,
 *@date 2014-10-14 21:37:26
 */
@MyBatisDomain
public class QuartzTaskGroupDefDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private java.lang.Long id;

	/**分组处理编号*/
	private java.lang.String groupId;

	/**分组处理名称*/
	private java.lang.String groupName;

	/**分组任务是否发布（1：发布，0：待发布）*/
	private java.lang.String groupState;

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

	/**数据有效性（1：有效、0：无效）*/
	private java.lang.String validateState;

	/**新增人*/
	private java.lang.String createdBy;

	/**新增时间*/
	private java.util.Date created;

	/**修改人*/
	private java.lang.String lastUpdBy;

	/**修改时间*/
	private java.util.Date lastUpd;

	/**备注*/
	private java.lang.String remark;
	
	private java.lang.String isEnd;
	
	private java.lang.String runTime;


	public java.lang.String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(java.lang.String isEnd) {
		this.isEnd = isEnd;
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
	 *方法: 获得groupState
	 *@return: java.lang.String  groupState
	 */
	public java.lang.String getGroupState(){
		return this.groupState;
	}

	/**
	 *方法: 设置groupState
	 *@param: java.lang.String  groupState
	 */
	public void setGroupState(java.lang.String groupState){
		this.groupState = groupState;
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
	 *方法: 获得createdBy
	 *@return: java.lang.String  createdBy
	 */
	public java.lang.String getCreatedBy(){
		return this.createdBy;
	}

	/**
	 *方法: 设置createdBy
	 *@param: java.lang.String  createdBy
	 */
	public void setCreatedBy(java.lang.String createdBy){
		this.createdBy = createdBy;
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
	 *方法: 获得lastUpdBy
	 *@return: java.lang.String  lastUpdBy
	 */
	public java.lang.String getLastUpdBy(){
		return this.lastUpdBy;
	}

	/**
	 *方法: 设置lastUpdBy
	 *@param: java.lang.String  lastUpdBy
	 */
	public void setLastUpdBy(java.lang.String lastUpdBy){
		this.lastUpdBy = lastUpdBy;
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

	public java.lang.String getRunTime() {
		return runTime;
	}

	public void setRunTime(java.lang.String runTime) {
		this.runTime = runTime;
	}

}