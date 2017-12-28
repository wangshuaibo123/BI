package com.jy.modules.logmonitor.sysapplevelsetup.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:错误级别设定表
 *@author lei
 *@version 1.0,
 *@date 2015-06-12 16:33:50
 */
public class SysAppLevelSetupDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**级别类型*/
	private java.lang.Long logLevel;

	/**关键字*/
	private java.lang.String keyWord;

	/**邮件提醒标识*/
	private java.lang.Long emailFlag;
	
	/**短信提醒标识*/
	private java.lang.Long smsFlag;
	
	/**展示详细信息标识*/
	private java.lang.Long showDetailFlag;
	
	private String appFlag;
	
	public String getAppFlag() {
		return appFlag;
	}

	public void setAppFlag(String appFlag) {
		this.appFlag = appFlag;
	}

	public java.lang.Long getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(java.lang.Long smsFlag) {
		this.smsFlag = smsFlag;
	}

	public java.lang.Long getShowDetailFlag() {
		return showDetailFlag;
	}

	public void setShowDetailFlag(java.lang.Long showDetailFlag) {
		this.showDetailFlag = showDetailFlag;
	}

	private Long rate;
	
	private String rateUnit;

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
	 *方法: 获得logLevel
	 *@return: java.lang.Long  logLevel
	 */
	public java.lang.Long getLogLevel(){
		return this.logLevel;
	}

	/**
	 *方法: 设置logLevel
	 *@param: java.lang.Long  logLevel
	 */
	public void setLogLevel(java.lang.Long logLevel){
		this.logLevel = logLevel;
	}

	/**
	 *方法: 获得keyWord
	 *@return: java.lang.String  keyWord
	 */
	public java.lang.String getKeyWord(){
		return this.keyWord;
	}

	/**
	 *方法: 设置keyWord
	 *@param: java.lang.String  keyWord
	 */
	public void setKeyWord(java.lang.String keyWord){
		this.keyWord = keyWord;
	}

	/**
	 *方法: 获得emailFlag
	 *@return: java.lang.Long  emailFlag
	 */
	public java.lang.Long getEmailFlag(){
		return this.emailFlag;
	}

	/**
	 *方法: 设置emailFlag
	 *@param: java.lang.Long  emailFlag
	 */
	public void setEmailFlag(java.lang.Long emailFlag){
		this.emailFlag = emailFlag;
	}

	public Long getRate() {
		return rate;
	}

	public void setRate(Long rate) {
		this.rate = rate;
	}

	public String getRateUnit() {
		return rateUnit;
	}

	public void setRateUnit(String rateUnit) {
		this.rateUnit = rateUnit;
	}

}