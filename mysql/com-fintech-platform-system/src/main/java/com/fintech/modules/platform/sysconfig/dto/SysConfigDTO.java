package com.fintech.modules.platform.sysconfig.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:系统配置表
 *@author
 *@version 1.0,
 *@date 2014-10-15 10:27:46
 */
public class SysConfigDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**配置名称*/
	private java.lang.String configName;

	/**配置CODE*/
	private java.lang.String configCode;

	/**配置值*/
	private java.lang.String configValue;

	/**类型，0系统级（不可删除），1项目级*/
	private java.lang.String configType;

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
	 *方法: 获得configName
	 *@return: java.lang.String  configName
	 */
	public java.lang.String getConfigName(){
		return this.configName;
	}

	/**
	 *方法: 设置configName
	 *@param: java.lang.String  configName
	 */
	public void setConfigName(java.lang.String configName){
		this.configName = configName;
	}

	/**
	 *方法: 获得configCode
	 *@return: java.lang.String  configCode
	 */
	public java.lang.String getConfigCode(){
		return this.configCode;
	}

	/**
	 *方法: 设置configCode
	 *@param: java.lang.String  configCode
	 */
	public void setConfigCode(java.lang.String configCode){
		this.configCode = configCode;
	}

	/**
	 *方法: 获得configValue
	 *@return: java.lang.String  configValue
	 */
	public java.lang.String getConfigValue(){
		return this.configValue;
	}

	/**
	 *方法: 设置configValue
	 *@param: java.lang.String  configValue
	 */
	public void setConfigValue(java.lang.String configValue){
		this.configValue = configValue;
	}

	/**
	 *方法: 获得configType
	 *@return: java.lang.String  configType
	 */
	public java.lang.String getConfigType(){
		return this.configType;
	}

	/**
	 *方法: 设置configType
	 *@param: java.lang.String  configType
	 */
	public void setConfigType(java.lang.String configType){
		this.configType = configType;
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