package com.jy.platform.rule.ruledefinition.dto;

import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.rule.RuleDef;
/**
 *@Description:RULE_DEFINITION
 *@author zhangyu
 *@version 1.0,
 *@date 2017-03-28 15:17:21
 */
public class RuleDefinitionDTO extends BaseDTO implements RuleDef {

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;
	
	/** 父ID */
	private java.lang.Long parentId;

	/**规则名称*/
	private java.lang.String ruleName;

	/**规则定义*/
	private String ruleResource;

	/**定义类型*/
	private java.lang.String resourceType;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;

	/**乐观锁*/
	private Integer version;

	/**规则集编码-展示用*/
	private java.lang.String ruleCode;

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
	 *方法: 获得ruleCode
	 *@return: java.lang.String  ruleCode
	 */
	public java.lang.String getRuleCode(){
		return this.ruleCode;
	}

	/**
	 *方法: 设置ruleCode
	 *@param: java.lang.String  ruleCode
	 */
	public void setRuleCode(java.lang.String ruleCode){
		this.ruleCode = ruleCode;
	}

	/**
	 *方法: 获得ruleName
	 *@return: java.lang.String  ruleName
	 */
	public java.lang.String getRuleName(){
		return this.ruleName;
	}

	/**
	 *方法: 设置ruleName
	 *@param: java.lang.String  ruleName
	 */
	public void setRuleName(java.lang.String ruleName){
		this.ruleName = ruleName;
	}

	/**
	 *方法: 获得ruleResource
	 *@return: String  ruleResource
	 */
	public String getRuleResource(){
		return this.ruleResource;
	}

	/**
	 *方法: 设置ruleResource
	 *@param: String  ruleResource
	 */
	public void setRuleResource(String ruleResource){
		this.ruleResource = ruleResource;
	}

	/**
	 *方法: 获得resourceType
	 *@return: java.lang.String  resourceType
	 */
	public java.lang.String getResourceType(){
		return this.resourceType;
	}

	/**
	 *方法: 设置resourceType
	 *@param: java.lang.String  resourceType
	 */
	public void setResourceType(java.lang.String resourceType){
		this.resourceType = resourceType;
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
	 *@return: Integer  version
	 */
	public Integer getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置version
	 *@param: Integer  version
	 */
	public void setVersion(Integer version){
		this.version = version;
	}

	/** 父ID */
	public java.lang.Long getParentId() {
		return parentId;
	}

	/** 父ID */
	public void setParentId(java.lang.Long parentId) {
		this.parentId = parentId;
	}

	/** 规则ID */
	@Override
	public String getRuleId() {
		return String.valueOf(id);
	}

	/** 规则版本号 */
	@Override
	public String getRuleVersion() {
		return String.valueOf(version);
	}

}