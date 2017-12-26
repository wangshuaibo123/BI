package com.fintech.modules.platform.bizauth.vmrulemapping.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:映射表
 *@author
 *@version 1.0,
 *@date 2015-01-16 17:14:38
 */
public class VmruleMappingDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private java.lang.Long id;

	/**类型（1：人对人，2：人对机构，3：机构对机构）*/
	private java.lang.String mapType;

	/**关联KEY信息*/
	private java.lang.String mapKey;

	/**关联VALUE信息*/
	private java.lang.String mapValue;

	/**虚拟树代码*/
	private java.lang.String orgType;

	/**创建时间*/
	private java.sql.Timestamp createTime;

	/**创建人*/
	private java.lang.Long createBy;
	
	private java.lang.String keyName;
	
	private java.lang.String valueName;
	
	

	public java.lang.String getKeyName() {
		return keyName;
	}

	public void setKeyName(java.lang.String keyName) {
		this.keyName = keyName;
	}

	public java.lang.String getValueName() {
		return valueName;
	}

	public void setValueName(java.lang.String valueName) {
		this.valueName = valueName;
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
	 *方法: 获得mapType
	 *@return: java.lang.String  mapType
	 */
	public java.lang.String getMapType(){
		return this.mapType;
	}

	/**
	 *方法: 设置mapType
	 *@param: java.lang.String  mapType
	 */
	public void setMapType(java.lang.String mapType){
		this.mapType = mapType;
	}

	/**
	 *方法: 获得mapKey
	 *@return: java.lang.String  mapKey
	 */
	public java.lang.String getMapKey(){
		return this.mapKey;
	}

	/**
	 *方法: 设置mapKey
	 *@param: java.lang.String  mapKey
	 */
	public void setMapKey(java.lang.String mapKey){
		this.mapKey = mapKey;
	}

	/**
	 *方法: 获得mapValue
	 *@return: java.lang.String  mapValue
	 */
	public java.lang.String getMapValue(){
		return this.mapValue;
	}

	/**
	 *方法: 设置mapValue
	 *@param: java.lang.String  mapValue
	 */
	public void setMapValue(java.lang.String mapValue){
		this.mapValue = mapValue;
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