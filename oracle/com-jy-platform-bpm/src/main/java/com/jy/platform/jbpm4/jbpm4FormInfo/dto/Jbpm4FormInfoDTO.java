package com.jy.platform.jbpm4.jbpm4FormInfo.dto;

import java.io.Serializable;


/**
 *@Description:工作流表单配置表
 *@author chen
 *@version 1.0,
 *@date 2014-03-05 14:39:08
 */

public class Jbpm4FormInfoDTO implements Serializable{
    private static final long serialVersionUID = -1600366697291280306L;
    //定义当前 执行任务的人员id 
	private String taskUserId;
	//流程流转方法
	private String turnDirection;

	/**ID*/
	private java.lang.Long id;

	/**流程定义key*/
	private java.lang.String proDefKey;

	/**节点名称*/
	private java.lang.String proActivityName;

	/**节点所挂表单*/
	private java.lang.String proActivityForm;

	/**参与者类型*/
	private java.lang.String participatorType;

	/**人工选择参与者时 参与者 类型*/
	private java.lang.String partType;

	/**当前节点其他参数信息*/
	private java.lang.String otherParams;
	//存放 otherParams 的中文描述信息
	private String otherParamsDis;

	/**规则定义*/
	private java.lang.String ruleInfo;

	/**CREATE_TIME*/
	private java.util.Date createTime;

	/**备注*/
	private java.lang.String remark;

	/**EXT1*/
	private java.lang.String ext1;

	/**EXT2*/
	private java.lang.String ext2;

	/**EXT3*/
	private java.lang.String ext3;

	/**数据有效性（1：有效、0：无效）*/
	private java.lang.String validateState;

	/**流程的版本信息*/
	private java.lang.String proLevel;

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
	 *方法: 获得proDefKey
	 *@return: java.lang.String  proDefKey
	 */
	public java.lang.String getProDefKey(){
		return this.proDefKey;
	}

	/**
	 *方法: 设置proDefKey
	 *@param: java.lang.String  proDefKey
	 */
	public void setProDefKey(java.lang.String proDefKey){
		this.proDefKey = proDefKey;
	}

	/**
	 *方法: 获得proActivityName
	 *@return: java.lang.String  proActivityName
	 */
	public java.lang.String getProActivityName(){
		return this.proActivityName;
	}

	/**
	 *方法: 设置proActivityName
	 *@param: java.lang.String  proActivityName
	 */
	public void setProActivityName(java.lang.String proActivityName){
		this.proActivityName = proActivityName;
	}

	/**
	 *方法: 获得proActivityForm
	 *@return: java.lang.String  proActivityForm
	 */
	public java.lang.String getProActivityForm(){
		return this.proActivityForm;
	}

	/**
	 *方法: 设置proActivityForm
	 *@param: java.lang.String  proActivityForm
	 */
	public void setProActivityForm(java.lang.String proActivityForm){
		this.proActivityForm = proActivityForm;
	}

	/**
	 *方法: 获得participatorType
	 *@return: java.lang.String  participatorType
	 */
	public java.lang.String getParticipatorType(){
		return this.participatorType;
	}

	/**
	 *方法: 设置participatorType
	 *@param: java.lang.String  participatorType
	 */
	public void setParticipatorType(java.lang.String participatorType){
		this.participatorType = participatorType;
	}


	/**
	 *方法: 获得otherParams
	 *@return: java.lang.String  otherParams
	 */
	public java.lang.String getOtherParams(){
		return this.otherParams;
	}

	/**
	 *方法: 设置otherParams
	 *@param: java.lang.String  otherParams
	 */
	public void setOtherParams(java.lang.String otherParams){
		this.otherParams = otherParams;
	}

	/**
	 *方法: 获得ruleInfo
	 *@return: java.lang.String  ruleInfo
	 */
	public java.lang.String getRuleInfo(){
		return this.ruleInfo;
	}

	/**
	 *方法: 设置ruleInfo
	 *@param: java.lang.String  ruleInfo
	 */
	public void setRuleInfo(java.lang.String ruleInfo){
		this.ruleInfo = ruleInfo;
	}

	/**
	 *方法: 获得createTime
	 *@return: java.util.Date  createTime
	 */
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置createTime
	 *@param: java.util.Date  createTime
	 */
	public void setCreateTime(java.util.Date createTime){
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

	/**
	 *方法: 获得ext1
	 *@return: java.lang.String  ext1
	 */
	public java.lang.String getExt1(){
		return this.ext1;
	}

	/**
	 *方法: 设置ext1
	 *@param: java.lang.String  ext1
	 */
	public void setExt1(java.lang.String ext1){
		this.ext1 = ext1;
	}

	/**
	 *方法: 获得ext2
	 *@return: java.lang.String  ext2
	 */
	public java.lang.String getExt2(){
		return this.ext2;
	}

	/**
	 *方法: 设置ext2
	 *@param: java.lang.String  ext2
	 */
	public void setExt2(java.lang.String ext2){
		this.ext2 = ext2;
	}

	/**
	 *方法: 获得ext3
	 *@return: java.lang.String  ext3
	 */
	public java.lang.String getExt3(){
		return this.ext3;
	}

	/**
	 *方法: 设置ext3
	 *@param: java.lang.String  ext3
	 */
	public void setExt3(java.lang.String ext3){
		this.ext3 = ext3;
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
	 *方法: 获得proLevel
	 *@return: java.lang.String  proLevel
	 */
	public java.lang.String getProLevel(){
		return this.proLevel;
	}

	/**
	 *方法: 设置proLevel
	 *@param: java.lang.String  proLevel
	 */
	public void setProLevel(java.lang.String proLevel){
		this.proLevel = proLevel;
	}

	public String getTaskUserId() {
		return taskUserId;
	}

	public void setTaskUserId(String taskUserId) {
		this.taskUserId = taskUserId;
	}

	public String getTurnDirection() {
		return turnDirection;
	}

	public void setTurnDirection(String turnDirection) {
		this.turnDirection = turnDirection;
	}
	public java.lang.String getPartType() {
		return partType;
	}

	public void setPartType(java.lang.String partType) {
		this.partType = partType;
	}
	public String getOtherParamsDis() {
		return otherParamsDis;
	}

	public void setOtherParamsDis(String otherParamsDis) {
		this.otherParamsDis = otherParamsDis;
	}
}