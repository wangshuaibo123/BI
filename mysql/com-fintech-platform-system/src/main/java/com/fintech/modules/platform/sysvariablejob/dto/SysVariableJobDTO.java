package com.fintech.modules.platform.sysvariablejob.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:异步任务参数表
 *@author DELL
 *@version 1.0,
 *@date 2016-09-19 13:51:00
 */
public class SysVariableJobDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private java.lang.Long id;

	/**参数1*/
	private java.lang.String var1;

	/**序列化参数2*/
	private java.lang.Object var2;

	/**数据有效性(1：有效，0：无效)*/
	private java.lang.String valid;

	/**数据新增时间*/
	private java.sql.Timestamp createTime;

	/**备注*/
	private java.lang.String remark;
	/**
	 * 返回序列化对象二进制
	 */
	private byte[] var2Bytes;

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
	 *方法: 获得var1
	 *@return: java.lang.String  var1
	 */
	public java.lang.String getVar1(){
		return this.var1;
	}

	/**
	 *方法: 设置var1
	 *@param: java.lang.String  var1
	 */
	public void setVar1(java.lang.String var1){
		this.var1 = var1;
	}

	/**
	 *方法: 获得var2
	 *@return: java.lang.Object  var2
	 */
	public java.lang.Object getVar2(){
		return this.var2;
	}

	/**
	 *方法: 设置var2
	 *@param: java.lang.Object  var2
	 */
	public void setVar2(java.lang.Object var2){
		this.var2 = var2;
	}

	/**
	 *方法: 获得valid
	 *@return: java.lang.String  valid
	 */
	public java.lang.String getValid(){
		return this.valid;
	}

	/**
	 *方法: 设置valid
	 *@param: java.lang.String  valid
	 */
	public void setValid(java.lang.String valid){
		this.valid = valid;
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
	 *方法: 获得remark
	 *@return: java.lang.String  remark
	 */
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置remark
	 *@param: java.lang.String  remark
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}

	public byte[] getVar2Bytes() {
		return var2Bytes;
	}

	public void setVar2Bytes(byte[] var2Bytes) {
		this.var2Bytes = var2Bytes;
	}
}