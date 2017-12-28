package com.jy.platform.rule.ruletree.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:规则树
 *@author zhangyu
 *@version 1.0,
 *@date 2017-04-20 16:30:44
 */
public class RuleTreeDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**父节点*/
	private java.lang.Long parentId;

	/**节点编码*/
	private java.lang.String nodeCode;

	/**节点名称*/
	private java.lang.String nodeName;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;

	/**乐观锁*/
	private java.lang.Long version;
	
	/** 父节点编码-显示用 */
	private String parentCode;
	/** 父节点名字-显示用 */
	private String parentName;

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
	 *方法: 获得nodeCode
	 *@return: java.lang.String  nodeCode
	 */
	public java.lang.String getNodeCode(){
		return this.nodeCode;
	}

	/**
	 *方法: 设置nodeCode
	 *@param: java.lang.String  nodeCode
	 */
	public void setNodeCode(java.lang.String nodeCode){
		this.nodeCode = nodeCode;
	}

	/**
	 *方法: 获得nodeName
	 *@return: java.lang.String  nodeName
	 */
	public java.lang.String getNodeName(){
		return this.nodeName;
	}

	/**
	 *方法: 设置nodeName
	 *@param: java.lang.String  nodeName
	 */
	public void setNodeName(java.lang.String nodeName){
		this.nodeName = nodeName;
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

	/** 父节点名字-显示用 */
	public String getParentName() {
		return parentName;
	}

	/** 父节点名字-显示用 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/** 父节点编码-显示用 */
	public String getParentCode() {
		return parentCode;
	}

	/** 父节点编码-显示用 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}