package com.jy.modules.platform.bizauth.vmauthregisterinfo.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:权限注册表
 *@author chen_gang
 *@version 1.0,
 *@date 2015-01-16 17:14:11
 */
public class VmauthRegisterInfoDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**虚拟树代码*/
	private java.lang.String vmtreeCode;

	/**虚拟树名称*/
	private java.lang.String vmtreeName;

	/**映射表名*/
	private java.lang.String mapTabName;

	/**数据权限表名*/
	private java.lang.String dataTabName;

	/**映射表创建SQL*/
	private java.lang.String mapInitSql;

	/**数据权限表创建SQL*/
	private java.lang.String dataInitSql;

	/**创建时间*/
	private java.sql.Timestamp createTime;

	/**创建人*/
	private java.lang.Long createBy;

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
	 *方法: 获得vmtreeCode
	 *@return: java.lang.String  vmtreeCode
	 */
	public java.lang.String getVmtreeCode(){
		return this.vmtreeCode;
	}

	/**
	 *方法: 设置vmtreeCode
	 *@param: java.lang.String  vmtreeCode
	 */
	public void setVmtreeCode(java.lang.String vmtreeCode){
		this.vmtreeCode = vmtreeCode;
	}

	/**
	 *方法: 获得vmtreeName
	 *@return: java.lang.String  vmtreeName
	 */
	public java.lang.String getVmtreeName(){
		return this.vmtreeName;
	}

	/**
	 *方法: 设置vmtreeName
	 *@param: java.lang.String  vmtreeName
	 */
	public void setVmtreeName(java.lang.String vmtreeName){
		this.vmtreeName = vmtreeName;
	}

	/**
	 *方法: 获得mapTabName
	 *@return: java.lang.String  mapTabName
	 */
	public java.lang.String getMapTabName(){
		return this.mapTabName;
	}

	/**
	 *方法: 设置mapTabName
	 *@param: java.lang.String  mapTabName
	 */
	public void setMapTabName(java.lang.String mapTabName){
		this.mapTabName = mapTabName;
	}

	/**
	 *方法: 获得dataTabName
	 *@return: java.lang.String  dataTabName
	 */
	public java.lang.String getDataTabName(){
		return this.dataTabName;
	}

	/**
	 *方法: 设置dataTabName
	 *@param: java.lang.String  dataTabName
	 */
	public void setDataTabName(java.lang.String dataTabName){
		this.dataTabName = dataTabName;
	}

	/**
	 *方法: 获得mapInitSql
	 *@return: java.lang.String  mapInitSql
	 */
	public java.lang.String getMapInitSql(){
		return this.mapInitSql;
	}

	/**
	 *方法: 设置mapInitSql
	 *@param: java.lang.String  mapInitSql
	 */
	public void setMapInitSql(java.lang.String mapInitSql){
		this.mapInitSql = mapInitSql;
	}

	/**
	 *方法: 获得dataInitSql
	 *@return: java.lang.String  dataInitSql
	 */
	public java.lang.String getDataInitSql(){
		return this.dataInitSql;
	}

	/**
	 *方法: 设置dataInitSql
	 *@param: java.lang.String  dataInitSql
	 */
	public void setDataInitSql(java.lang.String dataInitSql){
		this.dataInitSql = dataInitSql;
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