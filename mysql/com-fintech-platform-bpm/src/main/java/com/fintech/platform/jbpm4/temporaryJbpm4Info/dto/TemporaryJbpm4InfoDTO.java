package com.fintech.platform.jbpm4.temporaryJbpm4Info.dto;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 *@Description:工作流暂存表
 *@author
 *@version 1.0,
 *@date 2013-10-16 14:58:04
 */

public class TemporaryJbpm4InfoDTO implements Serializable{
	//是否发布
	private String publishState;
	
	private BigDecimal totalRecordNum;
	
	private Long num;
	
	private String procName;
	
	private String procCode;
	
	private String procVersion;
	
	private String bizFile;
	
	/**主键*/
	private java.lang.Long id;

	/**流程信息*/
	private java.lang.String xmlContent;

	/**数据有效性（1：有效、0：无效）*/
	private java.lang.String validateState;

	/**修改人*/
	private java.lang.String lastUpdBy;

	/**修改时间*/
	private String lastUpd;

	/**新增人*/
	private java.lang.String createdBy;

	/**新增时间*/
	private String created;

	/**备注*/
	private java.lang.String remark;

	/**扩展字段*/
	private java.lang.String ext1;

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
	 *方法: 获得xmlContent
	 *@return: java.lang.Object  xmlContent
	 */
	public java.lang.String getXmlContent(){
		return this.xmlContent;
	}

	/**
	 *方法: 设置xmlContent
	 *@param: java.lang.Object  xmlContent
	 */
	public void setXmlContent(java.lang.String xmlContent){
		this.xmlContent = xmlContent;
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
	 *方法: 获得lastUpdBy
	 *@return: java.lang.String  lastUpdBy
	 */
	public java.lang.String getLastUpdBy(){
		return this.lastUpdBy;
	}

	/**
	 *方法: 设置lastUpdBy
	 *@param: java.lang.String  lastUpdBy
	 */
	public void setLastUpdBy(java.lang.String lastUpdBy){
		this.lastUpdBy = lastUpdBy;
	}


	public String getLastUpd() {
		return lastUpd;
	}

	public void setLastUpd(String lastUpd) {
		this.lastUpd = lastUpd;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 *方法: 获得createdBy
	 *@return: java.lang.String  createdBy
	 */
	public java.lang.String getCreatedBy(){
		return this.createdBy;
	}

	/**
	 *方法: 设置createdBy
	 *@param: java.lang.String  createdBy
	 */
	public void setCreatedBy(java.lang.String createdBy){
		this.createdBy = createdBy;
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

	/**
	 *方法: 获得ext1
	 *@return: java.lang.String  ext1
	 */
	public java.lang.String getExt1(){
		return this.ext1;
	}

	/**
	 *方法: 设置ext1
	 *@param: java.lang.String  ext1
	 */
	public void setExt1(java.lang.String ext1){
		this.ext1 = ext1;
	}

	public BigDecimal getTotalRecordNum() {
		return totalRecordNum;
	}

	public void setTotalRecordNum(BigDecimal totalRecordNum) {
		this.totalRecordNum = totalRecordNum;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}

	public String getProcVersion() {
		return procVersion;
	}

	public void setProcVersion(String procVersion) {
		this.procVersion = procVersion;
	}

	public String getBizFile() {
		return bizFile;
	}

	public void setBizFile(String bizFile) {
		this.bizFile = bizFile;
	}

	public String getPublishState() {
		return publishState;
	}

	public void setPublishState(String publishState) {
		this.publishState = publishState;
	}

}