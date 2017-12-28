package com.jy.modules.platform.sysdict.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:数据字典明细表
 *@author chen_gang
 *@version 1.0,
 *@date 2014-10-15 10:28:43
 */
public class SysDictDetailDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**主键字典主键*/
	private java.lang.Long dictId;

	/**数据名称*/
	private java.lang.String dictDetailName;

	/**数据值*/
	private java.lang.String dictDetailValue;

	/**排序*/
	private java.lang.String orderBy;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;

	/**乐观锁*/
	private java.lang.Long version;

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
	 *方法: 获得dictId
	 *@return: java.lang.Long  dictId
	 */
	public java.lang.Long getDictId(){
		return this.dictId;
	}

	/**
	 *方法: 设置dictId
	 *@param: java.lang.Long  dictId
	 */
	public void setDictId(java.lang.Long dictId){
		this.dictId = dictId;
	}

	/**
	 *方法: 获得dictDetailName
	 *@return: java.lang.String  dictDetailName
	 */
	public java.lang.String getDictDetailName(){
		return this.dictDetailName;
	}

	/**
	 *方法: 设置dictDetailName
	 *@param: java.lang.String  dictDetailName
	 */
	public void setDictDetailName(java.lang.String dictDetailName){
		this.dictDetailName = dictDetailName;
	}

	/**
	 *方法: 获得dictDetailValue
	 *@return: java.lang.String  dictDetailValue
	 */
	public java.lang.String getDictDetailValue(){
		return this.dictDetailValue;
	}

	/**
	 *方法: 设置dictDetailValue
	 *@param: java.lang.String  dictDetailValue
	 */
	public void setDictDetailValue(java.lang.String dictDetailValue){
		this.dictDetailValue = dictDetailValue;
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

}