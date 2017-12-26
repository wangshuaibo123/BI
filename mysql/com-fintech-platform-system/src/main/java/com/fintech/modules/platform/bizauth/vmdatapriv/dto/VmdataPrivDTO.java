package com.fintech.modules.platform.bizauth.vmdatapriv.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:映射表
 *@author
 *@version 1.0,
 *@date 2015-01-16 17:14:46
 */
public class VmdataPrivDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private java.lang.Long id;

	/**用户ID*/
	private java.lang.Long userId;

	/**数据归属人ID*/
	private java.lang.Long ownerId;

	/**数据归属机构ID*/
	private java.lang.Long orgId;

	/**业务表主键ID*/
	private java.lang.Long bizId;

	/**ORG_TYPE*/
	private java.lang.String orgType;

	/**创建时间*/
	private java.sql.Timestamp createTime;

	/**创建人*/
	private java.lang.Long createBy;
	
	/**
	 * 
	 * mapping表的主键
	 */
	private java.lang.Long vmRuleMappingId;
	
	
	

	public java.lang.Long getVmRuleMappingId() {
		return vmRuleMappingId;
	}

	public void setVmRuleMappingId(java.lang.Long vmRuleMappingId) {
		this.vmRuleMappingId = vmRuleMappingId;
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
	 *方法: 获得userId
	 *@return: java.lang.Long  userId
	 */
	public java.lang.Long getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置userId
	 *@param: java.lang.Long  userId
	 */
	public void setUserId(java.lang.Long userId){
		this.userId = userId;
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
	 *方法: 获得bizId
	 *@return: java.lang.Long  bizId
	 */
	public java.lang.Long getBizId(){
		return this.bizId;
	}

	/**
	 *方法: 设置bizId
	 *@param: java.lang.Long  bizId
	 */
	public void setBizId(java.lang.Long bizId){
		this.bizId = bizId;
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