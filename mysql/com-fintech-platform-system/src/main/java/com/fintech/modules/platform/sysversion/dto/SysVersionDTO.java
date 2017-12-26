package com.fintech.modules.platform.sysversion.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:系统版本号表
 *@author
 *@version 1.0,
 *@date 2015-03-17 10:32:51
 */
public class SysVersionDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**版本号*/
	private java.lang.String versionNum;

	/**版本名称*/
	private java.lang.String versionName;

	/**版本内容*/
	private java.lang.String versionContent;

	/**上线时间*/
	private java.lang.String versionTime;

	/**系统标志位*/
	private java.lang.String systemState;

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
	 *方法: 获得versionNum
	 *@return: java.lang.Long  versionNum
	 */
	public java.lang.String getVersionNum(){
		return this.versionNum;
	}

	/**
	 *方法: 设置versionNum
	 *@param: java.lang.Long  versionNum
	 */
	public void setVersionNum(java.lang.String versionNum){
		this.versionNum = versionNum;
	}

	/**
	 *方法: 获得versionName
	 *@return: java.lang.Long  versionName
	 */
	public java.lang.String getVersionName(){
		return this.versionName;
	}

	/**
	 *方法: 设置versionName
	 *@param: java.lang.Long  versionName
	 */
	public void setVersionName(java.lang.String versionName){
		this.versionName = versionName;
	}

	/**
	 *方法: 获得versionContent
	 *@return: java.lang.String  versionContent
	 */
	public java.lang.String getVersionContent(){
		return this.versionContent;
	}

	/**
	 *方法: 设置versionContent
	 *@param: java.lang.String  versionContent
	 */
	public void setVersionContent(java.lang.String versionContent){
		this.versionContent = versionContent;
	}

	/**
	 *方法: 获得versionTime
	 *@return: java.sql.Timestamp  versionTime
	 */
	public java.lang.String getVersionTime(){
		return this.versionTime;
	}

	/**
	 *方法: 设置versionTime
	 *@param: java.sql.Timestamp  versionTime
	 */
	public void setVersionTime(java.lang.String versionTime){
		this.versionTime = versionTime;
	}

	/**
	 *方法: 获得systemState
	 *@return: java.lang.String  systemState
	 */
	public java.lang.String getSystemState(){
		return this.systemState;
	}

	/**
	 *方法: 设置systemState
	 *@param: java.lang.String  systemState
	 */
	public void setSystemState(java.lang.String systemState){
		this.systemState = systemState;
	}

}