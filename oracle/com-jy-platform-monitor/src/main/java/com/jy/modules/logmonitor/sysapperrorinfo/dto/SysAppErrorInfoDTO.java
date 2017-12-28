package com.jy.modules.logmonitor.sysapperrorinfo.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:业务系统节点错误日志
 *@author lei
 *@version 1.0,
 *@date 2015-04-03 10:07:07
 */
public class SysAppErrorInfoDTO extends BaseDTO{
	/******按照月中每天统计********/
	public static final int TYPE_COUNT_DAY = 1;
	/******按照天中每时统计********/
	public static final int TYPE_COUNT_HOUR = 0;
	

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**节点名称（IP）*/
	private java.lang.String nodeName;

	/**业务系统标识*/
	private java.lang.String appFlag;

	/**日志生成时间*/
	private java.lang.String createTime;

	/**内容*/
	private java.lang.String concent;
	
	private String fileName;
	/**级别*/
	private Integer logLevel;
	/**统计数量**/
	private Long count;
	/**时间显示字段,月中每天或是天中每小时,eg 1-31天,0-23小时**/
	private String ctime;
	/**
	 * 日志级别对应id
	 */
	private Long levelSetupId;
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
	 *方法: 获得createTime
	 *@return: java.lang.String  createTime
	 */
	public java.lang.String getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置createTime
	 *@param: java.lang.String  createTime
	 */
	public void setCreateTime(java.lang.String createTime){
		this.createTime = createTime;
	}

	/**
	 *方法: 获得concent
	 *@return: java.lang.String  concent
	 */
	public java.lang.String getConcent(){
		return this.concent;
	}

	/**
	 *方法: 设置concent
	 *@param: java.lang.String  concent
	 */
	public void setConcent(java.lang.String concent){
		this.concent = concent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(Integer logLevel) {
		this.logLevel = logLevel;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	
	public Long getLevelSetupId() {
		return levelSetupId;
	}

	public void setLevelSetupId(Long levelSetupId) {
		this.levelSetupId = levelSetupId;
	}

}