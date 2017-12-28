package com.jy.modules.logmonitor.sysapperrorinfo.dto;

import com.jy.platform.core.common.BaseDTO;

public class SysAppErrorLevelDetailDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 系统标识
	 */
	private String appFlag;
	
	/**
	 * 错误级别id
	 */
	private long id;
	
	/**
	 * 错误关键字
	 */
	private String keyWord;
	
	/**
	 * 错误级别
	 */
	private String logLevel;
	
	/**
	 * 是否发送邮件（0：不发送，1：发送）
	 */
	private String emailFlag;
	
	/**
	 * 是否发送短信（0：不发送，1：发送）
	 */
	private String smsFlag;
	
	/**
	 * 是否展示明细（0：不发送，1：发送）
	 */
	private String showDetailFlag;
	
	public String getSmsFlag() {
		return smsFlag;
	}

	public void setSmsFlag(String smsFlag) {
		this.smsFlag = smsFlag;
	}

	public String getShowDetailFlag() {
		return showDetailFlag;
	}

	public void setShowDetailFlag(String showDetailFlag) {
		this.showDetailFlag = showDetailFlag;
	}

	/**
	 * 错误个数
	 */
	private long count;
	
	/**
	 * 多个SYS_APP_ERROR_INFO表主键，以,隔开
	 */
	private String errorIds;

	public String getErrorIds() {
		return errorIds;
	}

	public void setErrorIds(String errorIds) {
		this.errorIds = errorIds;
	}

	public String getAppFlag() {
		return appFlag;
	}

	public void setAppFlag(String appFlag) {
		this.appFlag = appFlag;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}
	

}
