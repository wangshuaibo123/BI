package com.fintech.modules.platform.sysorg.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:虚拟树表
 *@author
 *@version 1.0,
 *@date 2015-01-16 17:14:31
 */
public class VmtreeInfoDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**序列或HR的OrgID*/
	private java.lang.Long orgId;

	/**机构名称*/
	private java.lang.String orgName;

	/**虚拟树代码*/
	private java.lang.String orgType;

	/**父节点ORG_ID*/
	private java.lang.Long parentId;

	/**数据来源（XN/HR）*/
	private java.lang.String sourceType;

	/**是否叶子节点（1：是，0：否）*/
	private java.lang.String endFlag;

	/**创建时间*/
	private java.sql.Timestamp createTime;

	/**创建人*/
	private java.lang.Long createBy;

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
	 *@return: java.lang.Long  parentId
	 */
	public java.lang.Long getParentId(){
		return this.parentId;
	}

	/**
	 *方法: 设置parentId
	 *@param: java.lang.Long  parentId
	 */
	public void setParentId(java.lang.Long parentId){
		this.parentId = parentId;
	}

	/**
	 *方法: 获得sourceType
	 *@return: java.lang.String  sourceType
	 */
	public java.lang.String getSourceType(){
		return this.sourceType;
	}

	/**
	 *方法: 设置sourceType
	 *@param: java.lang.String  sourceType
	 */
	public void setSourceType(java.lang.String sourceType){
		this.sourceType = sourceType;
	}

	/**
	 *方法: 获得endFlag
	 *@return: java.lang.String  endFlag
	 */
	public java.lang.String getEndFlag(){
		return this.endFlag;
	}

	/**
	 *方法: 设置endFlag
	 *@param: java.lang.String  endFlag
	 */
	public void setEndFlag(java.lang.String endFlag){
		this.endFlag = endFlag;
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

}