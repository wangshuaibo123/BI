package com.fintech.modules.platform.dataprv.sysprvtabledef.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:数据权限表定义
 *@author
 *@version 1.0,
 *@date 2014-10-18 16:07:40
 */
public class SysPrvTableDefDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**表名称*/
	private java.lang.String tableName;

	/**描述*/
	private java.lang.String tableDesc;

	/**有效性，1有效，0无效，默认1*/
	private java.lang.String validateState;

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
	 *方法: 获得tableName
	 *@return: java.lang.String  tableName
	 */
	public java.lang.String getTableName(){
		return this.tableName;
	}

	/**
	 *方法: 设置tableName
	 *@param: java.lang.String  tableName
	 */
	public void setTableName(java.lang.String tableName){
		this.tableName = tableName;
	}

	/**
	 *方法: 获得tableDesc
	 *@return: java.lang.String  tableDesc
	 */
	public java.lang.String getTableDesc(){
		return this.tableDesc;
	}

	/**
	 *方法: 设置tableDesc
	 *@param: java.lang.String  tableDesc
	 */
	public void setTableDesc(java.lang.String tableDesc){
		this.tableDesc = tableDesc;
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

}