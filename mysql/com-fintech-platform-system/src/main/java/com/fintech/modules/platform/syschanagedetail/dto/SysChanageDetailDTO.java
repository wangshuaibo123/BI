package com.fintech.modules.platform.syschanagedetail.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:变更信息明细表
 *@author DELL
 *@version 1.0,
 *@date 2016-09-09 18:08:38
 */
public class SysChanageDetailDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**ID*/
	private java.lang.Long id;

	/**信息变更表名称*/
	private java.lang.String bizTableName;

	/**表字段名称*/
	private java.lang.String bizTableColum;

	/**变更信息描述*/
	private java.lang.String changeItemName;

	/**原值*/
	private java.lang.String oldValue;

	/**新值*/
	private java.lang.String newValue;

	/**原值描述*/
	private java.lang.String oldShowvalue;

	/**新值描述*/
	private java.lang.String newShowvalue;

	/**表主键ID*/
	private java.lang.Long fkBizId;

	/**批次号/流程实例ID*/
	private java.lang.String batNo;

	/**变更是否生效（1：生效,0:未生效）*/
	private java.lang.String state;

	/**创建人*/
	private java.lang.Long createBy;

	/**业务所属人*/
	private java.lang.Long ownerId;

	/**业务所属机构*/
	private java.lang.Long orgId;

	/**创建时间*/
	private java.sql.Timestamp createTime;

	/**数据有效性*/
	private java.lang.String valid;

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
	 *方法: 获得bizTableName
	 *@return: java.lang.String  bizTableName
	 */
	public java.lang.String getBizTableName(){
		return this.bizTableName;
	}

	/**
	 *方法: 设置bizTableName
	 *@param: java.lang.String  bizTableName
	 */
	public void setBizTableName(java.lang.String bizTableName){
		this.bizTableName = bizTableName;
	}

	/**
	 *方法: 获得bizTableColum
	 *@return: java.lang.String  bizTableColum
	 */
	public java.lang.String getBizTableColum(){
		return this.bizTableColum;
	}

	/**
	 *方法: 设置bizTableColum
	 *@param: java.lang.String  bizTableColum
	 */
	public void setBizTableColum(java.lang.String bizTableColum){
		this.bizTableColum = bizTableColum;
	}

	/**
	 *方法: 获得changeItemName
	 *@return: java.lang.String  changeItemName
	 */
	public java.lang.String getChangeItemName(){
		return this.changeItemName;
	}

	/**
	 *方法: 设置changeItemName
	 *@param: java.lang.String  changeItemName
	 */
	public void setChangeItemName(java.lang.String changeItemName){
		this.changeItemName = changeItemName;
	}

	/**
	 *方法: 获得oldValue
	 *@return: java.lang.String  oldValue
	 */
	public java.lang.String getOldValue(){
		return this.oldValue;
	}

	/**
	 *方法: 设置oldValue
	 *@param: java.lang.String  oldValue
	 */
	public void setOldValue(java.lang.String oldValue){
		this.oldValue = oldValue;
	}

	/**
	 *方法: 获得newValue
	 *@return: java.lang.String  newValue
	 */
	public java.lang.String getNewValue(){
		return this.newValue;
	}

	/**
	 *方法: 设置newValue
	 *@param: java.lang.String  newValue
	 */
	public void setNewValue(java.lang.String newValue){
		this.newValue = newValue;
	}

	/**
	 *方法: 获得oldShowvalue
	 *@return: java.lang.String  oldShowvalue
	 */
	public java.lang.String getOldShowvalue(){
		return this.oldShowvalue;
	}

	/**
	 *方法: 设置oldShowvalue
	 *@param: java.lang.String  oldShowvalue
	 */
	public void setOldShowvalue(java.lang.String oldShowvalue){
		this.oldShowvalue = oldShowvalue;
	}

	/**
	 *方法: 获得newShowvalue
	 *@return: java.lang.String  newShowvalue
	 */
	public java.lang.String getNewShowvalue(){
		return this.newShowvalue;
	}

	/**
	 *方法: 设置newShowvalue
	 *@param: java.lang.String  newShowvalue
	 */
	public void setNewShowvalue(java.lang.String newShowvalue){
		this.newShowvalue = newShowvalue;
	}

	/**
	 *方法: 获得fkBizId
	 *@return: java.lang.Long  fkBizId
	 */
	public java.lang.Long getFkBizId(){
		return this.fkBizId;
	}

	/**
	 *方法: 设置fkBizId
	 *@param: java.lang.Long  fkBizId
	 */
	public void setFkBizId(java.lang.Long fkBizId){
		this.fkBizId = fkBizId;
	}

	/**
	 *方法: 获得batNo
	 *@return: java.lang.String  batNo
	 */
	public java.lang.String getBatNo(){
		return this.batNo;
	}

	/**
	 *方法: 设置batNo
	 *@param: java.lang.String  batNo
	 */
	public void setBatNo(java.lang.String batNo){
		this.batNo = batNo;
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
	 *方法: 获得createBy
	 *@return: java.lang.Long  createBy
	 */
	public java.lang.Long getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置createBy
	 *@param: java.lang.Long  createBy
	 */
	public void setCreateBy(java.lang.Long createBy){
		this.createBy = createBy;
	}

	/**
	 *方法: 获得ownerId
	 *@return: java.lang.Long  ownerId
	 */
	public java.lang.Long getOwnerId(){
		return this.ownerId;
	}

	/**
	 *方法: 设置ownerId
	 *@param: java.lang.Long  ownerId
	 */
	public void setOwnerId(java.lang.Long ownerId){
		this.ownerId = ownerId;
	}

	/**
	 *方法: 获得orgId
	 *@return: java.lang.Long  orgId
	 */
	public java.lang.Long getOrgId(){
		return this.orgId;
	}

	/**
	 *方法: 设置orgId
	 *@param: java.lang.Long  orgId
	 */
	public void setOrgId(java.lang.Long orgId){
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