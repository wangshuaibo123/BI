package com.jy.modules.platform.systemplate.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:模板
 *@author yuchengyang-pc
 *@version 1.0,
 *@date 2014-10-27 14:30:25
 */
public class SysTemplateDTO extends BaseDTO{
	
	public enum TemplateType {
	       sms("1","短信") ,
	       mail("2","邮件") , 
	       other("9","其他") ;
	       private String nCode;
	       private String nCodeCN;
	       private TemplateType(String _nCode,String _nCodeCN) {
	           this.nCode = _nCode;
	           this.nCodeCN = _nCodeCN;
	       }
	       @Override
	       public String toString() {
	           return String.valueOf(this.nCode);
	       }
	       public String toStringCN() {
	           return String.valueOf(this.nCodeCN);
	       }
	}

	private static final long serialVersionUID = 1L;

	/**机构ID*/
	private java.lang.Long id;

	/**系统ID*/
	private java.lang.String appId;

	/**模板编码*/
	private java.lang.String templateNo;

	/**模板名称*/
	private java.lang.String templateName;

	/**模板类型 1  短信 2邮件 9 其他*/
	private java.lang.String templateType;
	private java.lang.String templateTypeCN;

	/**模板内容*/
	private java.lang.Object templateContent;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;

	/**乐观锁*/
	private java.lang.Long version;

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
	 *方法: 获得appId
	 *@return: java.lang.String  appId
	 */
	public java.lang.String getAppId(){
		return this.appId;
	}

	/**
	 *方法: 设置appId
	 *@param: java.lang.String  appId
	 */
	public void setAppId(java.lang.String appId){
		this.appId = appId;
	}

	/**
	 *方法: 获得templateNo
	 *@return: java.lang.String  templateNo
	 */
	public java.lang.String getTemplateNo(){
		return this.templateNo;
	}

	/**
	 *方法: 设置templateNo
	 *@param: java.lang.String  templateNo
	 */
	public void setTemplateNo(java.lang.String templateNo){
		this.templateNo = templateNo;
	}

	/**
	 *方法: 获得templateName
	 *@return: java.lang.String  templateName
	 */
	public java.lang.String getTemplateName(){
		return this.templateName;
	}

	/**
	 *方法: 设置templateName
	 *@param: java.lang.String  templateName
	 */
	public void setTemplateName(java.lang.String templateName){
		this.templateName = templateName;
	}

	/**
	 *方法: 获得templateType
	 *@return: java.lang.String  templateType
	 */
	public java.lang.String getTemplateType(){
		return this.templateType;
	}
	public java.lang.String getTemplateTypeCN(){
		this.templateTypeCN = "";
		if(SysTemplateDTO.TemplateType.mail.toString().equals(this.getTemplateType())){
			templateTypeCN = SysTemplateDTO.TemplateType.mail.toStringCN();
		}else if(SysTemplateDTO.TemplateType.sms.toString().equals(this.getTemplateType())){
			templateTypeCN = SysTemplateDTO.TemplateType.sms.toStringCN();
		}else if(SysTemplateDTO.TemplateType.other.toString().equals(this.getTemplateType())){
			templateTypeCN = SysTemplateDTO.TemplateType.other.toStringCN();
		}
		return templateTypeCN;
	}

	/**
	 *方法: 设置templateType
	 *@param: java.lang.String  templateType
	 */
	public void setTemplateType(java.lang.String templateType){
		this.templateType = templateType;
	}

	/**
	 *方法: 获得templateContent
	 *@return: java.lang.Object  templateContent
	 */
	public java.lang.Object getTemplateContent(){
		return this.templateContent;
	}

	/**
	 *方法: 设置templateContent
	 *@param: java.lang.Object  templateContent
	 */
	public void setTemplateContent(java.lang.Object templateContent){
		this.templateContent = templateContent;
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
	 *方法: 获得version
	 *@return: java.lang.Long  version
	 */
	public java.lang.Long getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置version
	 *@param: java.lang.Long  version
	 */
	public void setVersion(java.lang.Long version){
		this.version = version;
	}

}