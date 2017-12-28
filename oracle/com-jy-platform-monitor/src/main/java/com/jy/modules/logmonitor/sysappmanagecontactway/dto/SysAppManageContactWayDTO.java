package com.jy.modules.logmonitor.sysappmanagecontactway.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:系统管理者联系方式
 *@author lei
 *@version 1.0,
 *@date 2015-06-12 16:34:26
 */
public class SysAppManageContactWayDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**系统标识*/
	private java.lang.String appFlag;

	/**email地址*/
	private java.lang.String email;

	/**手机号*/
	private java.lang.String tel;

	/**管理者名称*/
	private java.lang.String manageName;
	
	/**
	 * 管理者可以接收到的错误级别以上
	 */
	private long logLevel;
	
	/**接收关键字*/
	private java.lang.String keyWord;

	public java.lang.String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(java.lang.String keyWord) {
		this.keyWord = keyWord;
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
	 *方法: 获得appFlag
	 *@return: java.lang.String  appFlag
	 */
	public java.lang.String getAppFlag(){
		return this.appFlag;
	}

	/**
	 *方法: 设置appFlag
	 *@param: java.lang.String  appFlag
	 */
	public void setAppFlag(java.lang.String appFlag){
		this.appFlag = appFlag;
	}

	/**
	 *方法: 获得email
	 *@return: java.lang.String  email
	 */
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置email
	 *@param: java.lang.String  email
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}

	/**
	 *方法: 获得tel
	 *@return: java.lang.String  tel
	 */
	public java.lang.String getTel(){
		return this.tel;
	}

	/**
	 *方法: 设置tel
	 *@param: java.lang.String  tel
	 */
	public void setTel(java.lang.String tel){
		this.tel = tel;
	}

	/**
	 *方法: 获得manageName
	 *@return: java.lang.String  manageName
	 */
	public java.lang.String getManageName(){
		return this.manageName;
	}

	/**
	 *方法: 设置manageName
	 *@param: java.lang.String  manageName
	 */
	public void setManageName(java.lang.String manageName){
		this.manageName = manageName;
	}

	public long getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(long logLevel) {
		this.logLevel = logLevel;
	}

}