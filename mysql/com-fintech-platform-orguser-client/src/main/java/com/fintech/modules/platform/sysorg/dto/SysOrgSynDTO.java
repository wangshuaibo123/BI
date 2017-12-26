package com.fintech.modules.platform.sysorg.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:机构部门表
 *@author
 *@version 1.0,
 *@date 2015-01-20 10:24:15
 */
public class SysOrgSynDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**机构ID*/
	private java.lang.Long id;

	/**机构编码*/
	private java.lang.String orgCode;

	/**机构名称*/
	private java.lang.String orgName;

	/**机构类型：org组织，dept部门*/
	private java.lang.String orgType;

	/**父机构ID*/
	private java.lang.String parentId;

	/**父机构ID串，以/分割*/
	private java.lang.String parentIds;

	/**排序*/
	private java.lang.String orderBy;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;

	/**是否是虚拟组织，1是，0否，默认0*/
	private java.lang.String isVirtual;

	/**乐观锁*/
	private java.lang.Long version;

	/**系统标示 1 平台 2贷前 3贷后 4 理财*/
	private java.lang.String appFlag;

	/**创建时间*/
	private java.sql.Timestamp createDate;

	/**机构id*/
	private java.lang.Long orgId;
	
	/**是否叶子节点 */
	private java.lang.String isLeef;
	/**区域编码*/
	private java.lang.String areaCodes;
	/**区域地质*/
	private java.lang.String areaAdress;
	
	/**机构层级 **/
	private java.lang.String orgLevel;
	/** 生效日期**/
	private java.util.Date effectiveDate;
	
	/**创建日期**/
	private java.util.Date createTime;

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
	 *方法: 获得orgCode
	 *@return: java.lang.String  orgCode
	 */
	public java.lang.String getOrgCode(){
		return this.orgCode;
	}

	/**
	 *方法: 设置orgCode
	 *@param: java.lang.String  orgCode
	 */
	public void setOrgCode(java.lang.String orgCode){
		this.orgCode = orgCode;
	}

	/**
	 *方法: 获得orgName
	 *@return: java.lang.String  orgName
	 */
	public java.lang.String getOrgName(){
		return this.orgName;
	}

	/**
	 *方法: 设置orgName
	 *@param: java.lang.String  orgName
	 */
	public void setOrgName(java.lang.String orgName){
		this.orgName = orgName;
	}

	/**
	 *方法: 获得orgType
	 *@return: java.lang.String  orgType
	 */
	public java.lang.String getOrgType(){
		return this.orgType;
	}

	/**
	 *方法: 设置orgType
	 *@param: java.lang.String  orgType
	 */
	public void setOrgType(java.lang.String orgType){
		this.orgType = orgType;
	}

	/**
	 *方法: 获得parentId
	 *@return: java.lang.String  parentId
	 */
	public java.lang.String getParentId(){
		return this.parentId;
	}

	/**
	 *方法: 设置parentId
	 *@param: java.lang.String  parentId
	 */
	public void setParentId(java.lang.String parentId){
		this.parentId = parentId;
	}

	/**
	 *方法: 获得parentIds
	 *@return: java.lang.String  parentIds
	 */
	public java.lang.String getParentIds(){
		return this.parentIds;
	}

	/**
	 *方法: 设置parentIds
	 *@param: java.lang.String  parentIds
	 */
	public void setParentIds(java.lang.String parentIds){
		this.parentIds = parentIds;
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
	 *方法: 获得isVirtual
	 *@return: java.lang.String  isVirtual
	 */
	public java.lang.String getIsVirtual(){
		return this.isVirtual;
	}

	/**
	 *方法: 设置isVirtual
	 *@param: java.lang.String  isVirtual
	 */
	public void setIsVirtual(java.lang.String isVirtual){
		this.isVirtual = isVirtual;
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
	 *方法: 获得appFlag
	 *@return: java.lang.String  appFlag
	 */
	public java.lang.String getAppFlag(){
		return this.appFlag;
	}

	/**
	 *方法: 设置appFlag
	 *@param: java.lang.String  appFlag
	 */
	public void setAppFlag(java.lang.String appFlag){
		this.appFlag = appFlag;
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

	
	public java.lang.String getIsLeef() {
		return isLeef;
	}

	public void setIsLeef(java.lang.String isLeef) {
		this.isLeef = isLeef;
	}

	public java.lang.String getAreaCodes() {
		return areaCodes;
	}

	public void setAreaCodes(java.lang.String areaCodes) {
		this.areaCodes = areaCodes;
	}

	public java.lang.String getAreaAdress() {
		return areaAdress;
	}

	public void setAreaAdress(java.lang.String areaAdress) {
		this.areaAdress = areaAdress;
	}

	public java.lang.String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(java.lang.String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public java.util.Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(java.util.Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
}