package com.fintech.modules.platform.sysorg.dto;

import java.util.Date;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:机构部门表
 *@author
 *@version 1.0,
 *@date 2014-10-15 10:26:06
 */
public class SysOrgDTO extends BaseDTO{
	
	public enum appFlagEnum {
		   platform("1","平台"),
	       loan("2","贷前贷后") ,
	       wm("3","理财") , 
	       core("4","核心");
	       private String nCode;
	       private String nCodeCN;
	       private appFlagEnum(String _nCode,String _nCodeCN) {
	           this.nCode = _nCode;
	           this.nCodeCN = _nCodeCN;
	       }
	       @Override
	       public String toString() {
	           return String.valueOf(this.nCode);
	       }
	       public String toStringCN() {
	           return String.valueOf(this.nCodeCN);
	       }
	}
	
	private static final long serialVersionUID = 1L;

	/**机构ID*/
	private java.lang.Long id;

	/**机构编码*/
	private java.lang.String orgCode;

	/**机构名称*/
	private java.lang.String orgName;

	/**机构类型：org组织，dept部门*/
	private java.lang.String orgType;
	private java.lang.String orgTypeCN;


	/**父机构ID*/
	private java.lang.String parentId;

	/**父机构ID串，以/分割*/
	private java.lang.String parentIds;

	/**排序*/
	private java.lang.String orderBy;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;
	private java.lang.String validateStateCN;


	/**是否是虚拟组织，1是，0否，默认0*/
	private java.lang.String isVirtual;
	private java.lang.String isVirtualCN;

	/**乐观锁*/
	private java.lang.Long version;
	
	/**系统标示 */
	private java.lang.String appFlag;
	
	/**系统标示中文 */
	private java.lang.String appFlagCN;
	
	/**是否叶子节点 */
	private java.lang.String isLeef;
	/**区域编码*/
	private java.lang.String areaCodes;
	/**区域地质*/
	private java.lang.String areaAdress;
	/**要修改父机构Id*/
	private java.lang.String parentOrgId;
	/**父机构Name*/
	private java.lang.String parentName;
	/**机构层级 **/
	private java.lang.String orgLevel;
	/** 生效日期**/
	private java.util.Date effectiveDate;
	/**创建日期**/
	private java.util.Date createTime;
	/**生效时间临时**/
	private java.lang.String ed;
	
	
	public java.lang.String getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(java.lang.String parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public java.lang.String getParentName() {
		return parentName;
	}

	public void setParentName(java.lang.String parentName) {
		this.parentName = parentName;
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
	
	/**方法: 获得orgTypeCN
	 * @return
	 */
	public java.lang.String getOrgTypeCN(){
		if("org".equals(getOrgType())){
			this.orgTypeCN = "组织";
		}else if ("dept".equals(getOrgType())) {
			this.orgTypeCN = "部门";
		}
		return this.orgTypeCN;
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
	
	/**方法: 获得validateStateCN
	 * @return
	 */
	public java.lang.String getValidateStateCN(){
		if("1".equals(getValidateState())){
			this.validateStateCN = "有效";
		}else if ("0".equals(getValidateState())) {
			this.validateStateCN = "无效";
		}
		return this.validateStateCN;
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
	
	/**方法: 获得IsVirtualCN
	 * @return
	 */
	public java.lang.String getIsVirtualCN(){
		if("1".equals(getIsVirtual())){
			this.isVirtualCN = "是";
		}else if ("0".equals(getIsVirtual())) {
			this.isVirtualCN = "否";
		}
		return this.isVirtualCN;
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

	public static long generatorVersion(){
		return new Date().getTime();
	}

	public java.lang.String getAppFlag() {
		return appFlag;
	}

	public void setAppFlag(java.lang.String appFlag) {
		this.appFlag = appFlag;
	}
	
	public java.lang.String getAppFlagCN(){
		this.appFlagCN = "";
		if(SysOrgDTO.appFlagEnum.platform.toString().equals(this.getAppFlag())){
			appFlagCN = SysOrgDTO.appFlagEnum.platform.toStringCN();
		}else if(SysOrgDTO.appFlagEnum.loan.toString().equals(this.getAppFlag())){
			appFlagCN = SysOrgDTO.appFlagEnum.loan.toStringCN();
		}else if(SysOrgDTO.appFlagEnum.wm.toString().equals(this.getAppFlag())){
			appFlagCN = SysOrgDTO.appFlagEnum.wm.toStringCN();
		}else if(SysOrgDTO.appFlagEnum.core.toString().equals(this.getAppFlag())){
			appFlagCN = SysOrgDTO.appFlagEnum.core.toStringCN();
		}
		return appFlagCN;
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

	public java.lang.String getEd() {
		return ed;
	}

	public void setEd(java.lang.String ed) {
		this.ed = ed;
	}
	
	
}