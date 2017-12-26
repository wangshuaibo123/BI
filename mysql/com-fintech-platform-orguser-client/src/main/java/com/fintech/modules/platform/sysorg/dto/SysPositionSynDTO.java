package com.fintech.modules.platform.sysorg.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:岗位待同步表
 *@author
 *@version 1.0,
 *@date 2015-01-17 13:42:26
 */
public class SysPositionSynDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**岗位名称*/
	private java.lang.String positionName;

	/**岗位编码*/
	private java.lang.String positionCode;

	/**排序*/
	private java.lang.String orderBy;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;

	/**乐观锁*/
	private java.lang.Long version;

	/**创建时间*/
	private java.sql.Timestamp createDate;

	/**岗位Id*/
	private java.lang.Long positionId;
	
	/**所属机构ID**/
	private java.lang.String orgCode;
	
	/**是否负责**/
	private java.lang.String isResponsible;
	
	/**岗位序列**/
	private java.lang.String positionSequence;
	
	/**岗位**/
	private java.lang.String post;
	
	/**生效日期**/
	private java.util.Date effectiveDate;
	
	/** 上级岗位**/
	private java.lang.String parentId;
	
	/** 上级岗位名称**/
	private java.lang.String parentName;
	
	/** 上级岗位编码**/
	private java.lang.String parentCode;
	
	public java.lang.String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(java.lang.String orgCode) {
		this.orgCode = orgCode;
	}

	public java.lang.String getPost() {
		return post;
	}

	public void setPost(java.lang.String post) {
		this.post = post;
	}

	public java.util.Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(java.util.Date effectiveDate) {
		this.effectiveDate = effectiveDate;
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
	 *方法: 获得positionName
	 *@return: java.lang.String  positionName
	 */
	public java.lang.String getPositionName(){
		return this.positionName;
	}

	/**
	 *方法: 设置positionName
	 *@param: java.lang.String  positionName
	 */
	public void setPositionName(java.lang.String positionName){
		this.positionName = positionName;
	}

	/**
	 *方法: 获得positionCode
	 *@return: java.lang.String  positionCode
	 */
	public java.lang.String getPositionCode(){
		return this.positionCode;
	}

	/**
	 *方法: 设置positionCode
	 *@param: java.lang.String  positionCode
	 */
	public void setPositionCode(java.lang.String positionCode){
		this.positionCode = positionCode;
	}

	/**
	 *方法: 获得orderBy
	 *@return: java.lang.String  orderBy
	 */
	public java.lang.String getOrderBy(){
		return this.orderBy;
	}

	/**
	 *方法: 设置orderBy
	 *@param: java.lang.String  orderBy
	 */
	public void setOrderBy(java.lang.String orderBy){
		this.orderBy = orderBy;
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
	 *方法: 获得version
	 *@return: java.lang.Long  version
	 */
	public java.lang.Long getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置version
	 *@param: java.lang.Long  version
	 */
	public void setVersion(java.lang.Long version){
		this.version = version;
	}

	/**
	 *方法: 获得createDate
	 *@return: java.sql.Timestamp  createDate
	 */
	public java.sql.Timestamp getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置createDate
	 *@param: java.sql.Timestamp  createDate
	 */
	public void setCreateDate(java.sql.Timestamp createDate){
		this.createDate = createDate;
	}

	/**
	 *方法: 获得positionId
	 *@return: java.lang.Long  positionId
	 */
	public java.lang.Long getPositionId(){
		return this.positionId;
	}

	/**
	 *方法: 设置positionId
	 *@param: java.lang.Long  positionId
	 */
	public void setPositionId(java.lang.Long positionId){
		this.positionId = positionId;
	}

	public java.lang.String getIsResponsible() {
		return isResponsible;
	}

	public void setIsResponsible(java.lang.String isResponsible) {
		this.isResponsible = isResponsible;
	}

	public java.lang.String getPositionSequence() {
		return positionSequence;
	}

	public void setPositionSequence(java.lang.String positionSequence) {
		this.positionSequence = positionSequence;
	}

	public java.lang.String getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.String parentId) {
		this.parentId = parentId;
	}

	public java.lang.String getParentName() {
		return parentName;
	}

	public void setParentName(java.lang.String parentName) {
		this.parentName = parentName;
	}

	public java.lang.String getParentCode() {
		return parentCode;
	}

	public void setParentCode(java.lang.String parentCode) {
		this.parentCode = parentCode;
	}

}