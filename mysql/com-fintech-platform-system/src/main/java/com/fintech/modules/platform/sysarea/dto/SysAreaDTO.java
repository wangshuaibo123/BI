package com.fintech.modules.platform.sysarea.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:行政区域
 *@author
 *@version 1.0,
 *@date 2014-10-23 09:53:30
 */
public class SysAreaDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**区域编码*/
	private java.lang.String areaCode;

	/**区域名称*/
	private java.lang.String areaName;

	/**父节点ID*/
	private java.lang.Long parentId;

	/**有效性状态，1有效，0无效，默认1*/
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
	 *方法: 获得areaCode
	 *@return: java.lang.String  areaCode
	 */
	public java.lang.String getAreaCode(){
		return this.areaCode;
	}

	/**
	 *方法: 设置areaCode
	 *@param: java.lang.String  areaCode
	 */
	public void setAreaCode(java.lang.String areaCode){
		this.areaCode = areaCode;
	}

	/**
	 *方法: 获得areaName
	 *@return: java.lang.String  areaName
	 */
	public java.lang.String getAreaName(){
		return this.areaName;
	}

	/**
	 *方法: 设置areaName
	 *@param: java.lang.String  areaName
	 */
	public void setAreaName(java.lang.String areaName){
		this.areaName = areaName;
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