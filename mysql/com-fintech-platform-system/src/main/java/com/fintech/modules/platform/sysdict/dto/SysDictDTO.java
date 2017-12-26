package com.fintech.modules.platform.sysdict.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:数据字典
 *@author
 *@version 1.0,
 *@date 2014-10-15 10:28:27
 */
public class SysDictDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**数据字典code*/
	private java.lang.String dictCode;

	/**数据字典名称*/
	private java.lang.String dictName;

	/**数据字典类型，0系统级（不可删除），1项目级*/
	private java.lang.String dictType;

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
	 *方法: 获得dictCode
	 *@return: java.lang.String  dictCode
	 */
	public java.lang.String getDictCode(){
		return this.dictCode;
	}

	/**
	 *方法: 设置dictCode
	 *@param: java.lang.String  dictCode
	 */
	public void setDictCode(java.lang.String dictCode){
		this.dictCode = dictCode;
	}

	/**
	 *方法: 获得dictName
	 *@return: java.lang.String  dictName
	 */
	public java.lang.String getDictName(){
		return this.dictName;
	}

	/**
	 *方法: 设置dictName
	 *@param: java.lang.String  dictName
	 */
	public void setDictName(java.lang.String dictName){
		this.dictName = dictName;
	}

	/**
	 *方法: 获得dictType
	 *@return: java.lang.String  dictType
	 */
	public java.lang.String getDictType(){
		return this.dictType;
	}

	/**
	 *方法: 设置dictType
	 *@param: java.lang.String  dictType
	 */
	public void setDictType(java.lang.String dictType){
		this.dictType = dictType;
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