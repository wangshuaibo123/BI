package com.fintech.modules.platform.testtable1.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:测试表
 *@author
 *@version 1.0,
 *@date 2014-10-25 15:06:44
 */
public class TestTable1DTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**姓名*/
	private java.lang.String name;

	/**地址*/
	private java.lang.String address;

	/**状态*/
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
	 *方法: 获得name
	 *@return: java.lang.String  name
	 */
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置name
	 *@param: java.lang.String  name
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}

	/**
	 *方法: 获得address
	 *@return: java.lang.String  address
	 */
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置address
	 *@param: java.lang.String  address
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
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