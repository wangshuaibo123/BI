package com.jy.platform.api.org;

import java.util.Date;
/**<pre>
 * 类名中文描述:机构信息接口
 *
 * 基本操作功能:
 *
 * Module ID  : com-jy-platform-api 
 *
 * Create Date：2014年10月30日 下午5:12:02
 * 
 * CopyRight  :  Copyright(C) 2014-xxxx  捷越联合 <br/>
 * 
 * @since 0.1
 * @version: 0.1
 * @author <a href="mailto:chengyangyu@jieyuechina.com">cyy</a>
 * 	OrgInfo properties：
	private java.lang.Long id;机构Id
	private java.lang.String orgCode;机构编码
	private java.lang.String orgName;机构名称
	private java.lang.String orgType;机构类型：org组织，dept部门
	private java.lang.String orgTypeCN;
	private java.lang.String parentId;父机构ID
	private java.lang.String parentIds;父机构ID串，以/分割
	private java.lang.String orderBy;排序
	private java.lang.String validateState;有效性状态，1有效，0无效，默认1
	private java.lang.String validateStateCN;
	private java.lang.String isVirtual;*是否是虚拟组织，1是，0否，默认0
	private java.lang.String isVirtualCN;
	private java.lang.Long version;乐观锁
 *
 * Change History Log
 * --------------------------------------------------------------------------------------------------------------
 * Date	      | Version | Author	   | Description			              
 * --------------------------------------------------------------------------------------------------------------
 * 2014年10月30日 | 0.1     | cyy| CREATE THE JAVA FILE: OrgInfo.java.
 * --------------------------------------------------------------------------------------------------------------
 *
 * --------------------------------------------------------------------------------------------------------------
 *
 * </pre>
 */
public class OrgInfo implements java.io.Serializable{

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
	//增加直属上级的名称
	private String parentName;



	public java.lang.String getParentName(){
		return this.parentName;
	}

	public void setParentName(String parentName){
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
		if("dept".equals(getOrgType())){
			this.orgTypeCN = "组织";
		}else if ("org".equals(getOrgType())) {
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

}