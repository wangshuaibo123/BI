package com.jy.modules.platform.jbpm.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:申请请假表
 *@author chen_gang
 *@version 1.0,
 *@date 2014-10-30 17:07:12
 */
public class LeaveDemoInfoDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**申请人姓名*/
	private java.lang.String appName;

	/**请假原因*/
	private java.lang.String appReason;

	/**申请请假天数*/
	private java.lang.String appDay;

	/**数据有效性（1：有效、0：无效）*/
	private java.lang.String validateState;

	/**是否批准*/
	private java.lang.String appState;

	/**创建时间
*/
	private java.sql.Timestamp createTime;
	
	private java.lang.String createTimeStr;

	/**修改时间
*/
	private java.sql.Timestamp modifyTime;

	/**创建人
*/
	private java.lang.String createBy;

	/**修改人
*/
	private java.lang.String modifyBy;

	/**备注*/
	private java.lang.String remark;

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
	 *方法: 获得appName
	 *@return: java.lang.String  appName
	 */
	public java.lang.String getAppName(){
		return this.appName;
	}

	/**
	 *方法: 设置appName
	 *@param: java.lang.String  appName
	 */
	public void setAppName(java.lang.String appName){
		this.appName = appName;
	}

	/**
	 *方法: 获得appReason
	 *@return: java.lang.String  appReason
	 */
	public java.lang.String getAppReason(){
		return this.appReason;
	}

	/**
	 *方法: 设置appReason
	 *@param: java.lang.String  appReason
	 */
	public void setAppReason(java.lang.String appReason){
		this.appReason = appReason;
	}

	/**
	 *方法: 获得appDay
	 *@return: java.lang.String  appDay
	 */
	public java.lang.String getAppDay(){
		return this.appDay;
	}

	/**
	 *方法: 设置appDay
	 *@param: java.lang.String  appDay
	 */
	public void setAppDay(java.lang.String appDay){
		this.appDay = appDay;
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
	 *方法: 获得appState
	 *@return: java.lang.String  appState
	 */
	public java.lang.String getAppState(){
		return this.appState;
	}

	/**
	 *方法: 设置appState
	 *@param: java.lang.String  appState
	 */
	public void setAppState(java.lang.String appState){
		this.appState = appState;
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
	 *方法: 获得modifyTime
	 *@return: java.sql.Timestamp  modifyTime
	 */
	public java.sql.Timestamp getModifyTime(){
		return this.modifyTime;
	}

	/**
	 *方法: 设置modifyTime
	 *@param: java.sql.Timestamp  modifyTime
	 */
	public void setModifyTime(java.sql.Timestamp modifyTime){
		this.modifyTime = modifyTime;
	}

	/**
	 *方法: 获得createBy
	 *@return: java.lang.String  createBy
	 */
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置createBy
	 *@param: java.lang.String  createBy
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}

	/**
	 *方法: 获得modifyBy
	 *@return: java.lang.String  modifyBy
	 */
	public java.lang.String getModifyBy(){
		return this.modifyBy;
	}

	/**
	 *方法: 设置modifyBy
	 *@param: java.lang.String  modifyBy
	 */
	public void setModifyBy(java.lang.String modifyBy){
		this.modifyBy = modifyBy;
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

    
    public java.lang.String getCreateTimeStr() {
        return createTimeStr;
    }

    
    public void setCreateTimeStr(java.lang.String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
	
	

}