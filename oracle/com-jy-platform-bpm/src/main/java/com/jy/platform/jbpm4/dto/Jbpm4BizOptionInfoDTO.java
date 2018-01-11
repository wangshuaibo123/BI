package com.jy.platform.jbpm4.dto;

import com.jy.platform.core.common.MyBatisDTO;

/**
 *@Description:业务流程节点意见表
 *@author chen_gang
 *@version 1.0,
 *@date 2014-10-29 14:37:17
 */
public class Jbpm4BizOptionInfoDTO extends MyBatisDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**关联jbpm4_biz_tab.id*/
	private java.lang.String fkJbpmBizTabId;

	/**主流程实例ID*/
	private java.lang.String proInstanceId;

	/**任务ID*/
	private java.lang.String taskId;

	/**环节(节点名称)*/
	private java.lang.String activeName;

	/**系统在该环节的描述*/
	private java.lang.String systemActiveInfo;

	/**意见信息（备注）*/
	private java.lang.String optionRemark;

	/**数据有效性（1：有效、0：无效）*/
	private java.lang.String validateState;

	/**业务归属人*/
	private java.lang.String ownerId;

	/**业务所属机构
*/
	private java.lang.String orgId;

	/**创建时间
*/
	private java.sql.Timestamp createTime;

	/**修改时间
*/
	private java.sql.Timestamp modifyTime;

	/**创建人
*/
	private java.lang.String createBy;

	/**修改人
*/
	private java.lang.String modifyBy;

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
	 *方法: 获得fkJbpmBizTabId
	 *@return: java.lang.String  fkJbpmBizTabId
	 */
	public java.lang.String getFkJbpmBizTabId(){
		return this.fkJbpmBizTabId;
	}

	/**
	 *方法: 设置fkJbpmBizTabId
	 *@param: java.lang.String  fkJbpmBizTabId
	 */
	public void setFkJbpmBizTabId(java.lang.String fkJbpmBizTabId){
		this.fkJbpmBizTabId = fkJbpmBizTabId;
	}

	/**
	 *方法: 获得proInstanceId
	 *@return: java.lang.String  proInstanceId
	 */
	public java.lang.String getProInstanceId(){
		return this.proInstanceId;
	}

	/**
	 *方法: 设置proInstanceId
	 *@param: java.lang.String  proInstanceId
	 */
	public void setProInstanceId(java.lang.String proInstanceId){
		this.proInstanceId = proInstanceId;
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
	 *方法: 获得activeName
	 *@return: java.lang.String  activeName
	 */
	public java.lang.String getActiveName(){
		return this.activeName;
	}

	/**
	 *方法: 设置activeName
	 *@param: java.lang.String  activeName
	 */
	public void setActiveName(java.lang.String activeName){
		this.activeName = activeName;
	}

	/**
	 *方法: 获得systemActiveInfo
	 *@return: java.lang.String  systemActiveInfo
	 */
	public java.lang.String getSystemActiveInfo(){
		return this.systemActiveInfo;
	}

	/**
	 *方法: 设置systemActiveInfo
	 *@param: java.lang.String  systemActiveInfo
	 */
	public void setSystemActiveInfo(java.lang.String systemActiveInfo){
		this.systemActiveInfo = systemActiveInfo;
	}

	/**
	 *方法: 获得optionRemark
	 *@return: java.lang.String  optionRemark
	 */
	public java.lang.String getOptionRemark(){
		return this.optionRemark;
	}

	/**
	 *方法: 设置optionRemark
	 *@param: java.lang.String  optionRemark
	 */
	public void setOptionRemark(java.lang.String optionRemark){
		this.optionRemark = optionRemark;
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
	 *方法: 获得ownerId
	 *@return: java.lang.String  ownerId
	 */
	public java.lang.String getOwnerId(){
		return this.ownerId;
	}

	/**
	 *方法: 设置ownerId
	 *@param: java.lang.String  ownerId
	 */
	public void setOwnerId(java.lang.String ownerId){
		this.ownerId = ownerId;
	}

	/**
	 *方法: 获得orgId
	 *@return: java.lang.String  orgId
	 */
	public java.lang.String getOrgId(){
		return this.orgId;
	}

	/**
	 *方法: 设置orgId
	 *@param: java.lang.String  orgId
	 */
	public void setOrgId(java.lang.String orgId){
		this.orgId = orgId;
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
	 *方法: 获得modifyTime
	 *@return: java.sql.Timestamp  modifyTime
	 */
	public java.sql.Timestamp getModifyTime(){
		return this.modifyTime;
	}

	/**
	 *方法: 设置modifyTime
	 *@param: java.sql.Timestamp  modifyTime
	 */
	public void setModifyTime(java.sql.Timestamp modifyTime){
		this.modifyTime = modifyTime;
	}

	/**
	 *方法: 获得createBy
	 *@return: java.lang.String  createBy
	 */
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置createBy
	 *@param: java.lang.String  createBy
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}

	/**
	 *方法: 获得modifyBy
	 *@return: java.lang.String  modifyBy
	 */
	public java.lang.String getModifyBy(){
		return this.modifyBy;
	}

	/**
	 *方法: 设置modifyBy
	 *@param: java.lang.String  modifyBy
	 */
	public void setModifyBy(java.lang.String modifyBy){
		this.modifyBy = modifyBy;
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