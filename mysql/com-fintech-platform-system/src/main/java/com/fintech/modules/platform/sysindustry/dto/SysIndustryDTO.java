package com.fintech.modules.platform.sysindustry.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:sys_industry
 *@author
 *@version 1.0,
 *@date 2014-12-04 14:03:34
 */
public class SysIndustryDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**ID*/
	private java.lang.Long id;

	/**行业名称/职位名称*/
	private java.lang.String industryName;

	/**industry:行业;position:职位*/
	private java.lang.String industryType;

	/**职位所属的行业ID*/
	private java.lang.String parentCode;

	/**VALIDATE_STATE*/
	private java.lang.String validateState;

	private java.lang.String industryCode;
	
	public java.lang.String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(java.lang.String industryCode) {
		this.industryCode = industryCode;
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
	 *方法: 获得industryName
	 *@return: java.lang.String  industryName
	 */
	public java.lang.String getIndustryName(){
		return this.industryName;
	}

	/**
	 *方法: 设置industryName
	 *@param: java.lang.String  industryName
	 */
	public void setIndustryName(java.lang.String industryName){
		this.industryName = industryName;
	}

	/**
	 *方法: 获得industryType
	 *@return: java.lang.String  industryType
	 */
	public java.lang.String getIndustryType(){
		return this.industryType;
	}

	/**
	 *方法: 设置industryType
	 *@param: java.lang.String  industryType
	 */
	public void setIndustryType(java.lang.String industryType){
		this.industryType = industryType;
	}


	public java.lang.String getParentCode() {
		return parentCode;
	}

	public void setParentCode(java.lang.String parentCode) {
		this.parentCode = parentCode;
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