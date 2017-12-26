package com.fintech.modules.platform.sysflumeconfigzk.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:Flume配置表
 *@author Trust_FreeDom
 *@version 1.0,
 *@date 2016-09-09 11:08:34
 */
public class SysFlumeConfigZkDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private Long id;

	/**系统标示*/
	private String appFlag;

	/**IP*/
	private String appIp;

	/**角色*/
	private String appRole;

	/**状态*/
	private String status;

	/**配置信息*/
	private String config;

	/**创建人ID*/
	private String createBy;

	/**创建人姓名*/
	private String createByName;

	/**创建时间*/
	private java.sql.Timestamp createTime;

	/**更新人ID*/
	private String updateBy;

	/**更新人姓名*/
	private String updateByName;

	/**更新时间*/
	private java.sql.Timestamp updateTime;

	/**
	 *方法: 获得id
	 *@return: java.lang.Long  id
	 */
	public Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置id
	 *@param: java.lang.Long  id
	 */
	public void setId(Long id){
		this.id = id;
	}

	/**
	 *方法: 获得appFlag
	 *@return: java.lang.String  appFlag
	 */
	public String getAppFlag(){
		return this.appFlag;
	}

	/**
	 *方法: 设置appFlag
	 *@param: java.lang.String  appFlag
	 */
	public void setAppFlag(String appFlag){
		this.appFlag = appFlag;
	}

	/**
	 *方法: 获得appIp
	 *@return: java.lang.String  appIp
	 */
	public String getAppIp(){
		return this.appIp;
	}

	/**
	 *方法: 设置appIp
	 *@param: java.lang.String  appIp
	 */
	public void setAppIp(String appIp){
		this.appIp = appIp;
	}

	/**
	 *方法: 获得appRole
	 *@return: java.lang.String  appRole
	 */
	public String getAppRole(){
		return this.appRole;
	}

	/**
	 *方法: 设置appRole
	 *@param: java.lang.String  appRole
	 */
	public void setAppRole(String appRole){
		this.appRole = appRole;
	}

	/**
	 *方法: 获得status
	 *@return: java.lang.String  status
	 */
	public String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置status
	 *@param: java.lang.String  status
	 */
	public void setStatus(String status){
		this.status = status;
	}

	/**
	 *方法: 获得config
	 *@return: java.sql.Clob  config
	 */
	public String getConfig(){
		return this.config;
	}

	/**
	 *方法: 设置config
	 *@param: java.sql.Clob  config
	 */
	public void setConfig(String config){
		this.config = config;
	}

	/**
	 *方法: 获得createBy
	 *@return: java.lang.String  createBy
	 */
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置createBy
	 *@param: java.lang.String  createBy
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	/**
	 *方法: 获得createByName
	 *@return: java.lang.String  createByName
	 */
	public String getCreateByName(){
		return this.createByName;
	}

	/**
	 *方法: 设置createByName
	 *@param: java.lang.String  createByName
	 */
	public void setCreateByName(String createByName){
		this.createByName = createByName;
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
	 *方法: 获得updateBy
	 *@return: java.lang.String  updateBy
	 */
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置updateBy
	 *@param: java.lang.String  updateBy
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	/**
	 *方法: 获得updateByName
	 *@return: java.lang.String  updateByName
	 */
	public String getUpdateByName(){
		return this.updateByName;
	}

	/**
	 *方法: 设置updateByName
	 *@param: java.lang.String  updateByName
	 */
	public void setUpdateByName(String updateByName){
		this.updateByName = updateByName;
	}

	/**
	 *方法: 获得updateTime
	 *@return: java.sql.Timestamp  updateTime
	 */
	public java.sql.Timestamp getUpdateTime(){
		return this.updateTime;
	}

	/**
	 *方法: 设置updateTime
	 *@param: java.sql.Timestamp  updateTime
	 */
	public void setUpdateTime(java.sql.Timestamp updateTime){
		this.updateTime = updateTime;
	}

}