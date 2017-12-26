package com.fintech.modules.logmonitor.sysapplevelsetup.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:错误级别设定表
 *@author lei
 *@version 1.0,
 *@date 2015-06-12 16:33:50
 */
public class SysAppLevelSetupDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private Long id;

	/**级别类型*/
	private Long logLevel;

	/**关键字*/
	private String keyWord;

	/**邮件提醒标识*/
	private Long emailFlag;
	
	/**短信提醒标识*/
	private Long smsFlag;
	
	/**展示详细信息标识*/
	private Long showDetailFlag;
	
	private String appFlag;
	
	public String getAppFlag() {
		return appFlag;
	}

	public void setAppFlag(String appFlag) {
		this.appFlag = appFlag;
	}

	public Long getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(Long smsFlag) {
		this.smsFlag = smsFlag;
	}

	public Long getShowDetailFlag() {
		return showDetailFlag;
	}

	public void setShowDetailFlag(Long showDetailFlag) {
		this.showDetailFlag = showDetailFlag;
	}

	private Long rate;
	
	private String rateUnit;

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
	 *方法: 获得logLevel
	 *@return: java.lang.Long  logLevel
	 */
	public Long getLogLevel(){
		return this.logLevel;
	}

	/**
	 *方法: 设置logLevel
	 *@param: java.lang.Long  logLevel
	 */
	public void setLogLevel(Long logLevel){
		this.logLevel = logLevel;
	}

	/**
	 *方法: 获得keyWord
	 *@return: java.lang.String  keyWord
	 */
	public String getKeyWord(){
		return this.keyWord;
	}

	/**
	 *方法: 设置keyWord
	 *@param: java.lang.String  keyWord
	 */
	public void setKeyWord(String keyWord){
		this.keyWord = keyWord;
	}

	/**
	 *方法: 获得emailFlag
	 *@return: java.lang.Long  emailFlag
	 */
	public Long getEmailFlag(){
		return this.emailFlag;
	}

	/**
	 *方法: 设置emailFlag
	 *@param: java.lang.Long  emailFlag
	 */
	public void setEmailFlag(Long emailFlag){
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