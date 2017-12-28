package com.jy.modules.platform.sysbizlog.dto;

import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.mybatis.MyBatisDomain;
/**
 *@Description:业务日志表
 *@author chen_gang
 *@version 1.0,
 *@date 2014-10-15 16:30:12
 */
@MyBatisDomain
public class SysBizLogDTO extends BaseDTO{
	
	public enum LogType {
	       interfacelog("interfacelog","接口日志") , businesslog ("businesslog","业务日志") , performancelog("performancelog","性能日志") ;
	       private String nCode;
	       private String nCodeCN;
	       private LogType(String _nCode,String _nCodeCN) {
	           this.nCode = _nCode;
	           this.nCodeCN = _nCodeCN;
	       }
	       @Override
	       public String toString() {
	           return this.nCode;
	       }
	       public String toStringCN() {
	           return this.nCodeCN;
	       }
	}
	
	public enum OperateType {
	       login("login","登陆") , 
	       insert("insert","插入") , 
	       update("update","更新"), 
	       delete("delete","删除"), 
	       view("view","查看") ;
	       private String nCode;
	       private String nCodeCN;
	       private OperateType(String _nCode,String _nCodeCN) {
	           this.nCode = _nCode;
	           this.nCodeCN = _nCodeCN;
	       }
	       @Override
	       public String toString() {
	           return this.nCode;
	       }
	       public String toStringCN() {
	           return this.nCodeCN;
	       }
	}

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**客户端IP*/
	private java.lang.String clientIp;

	/**操作人ID*/
	private java.lang.Long userId;

	/**操作人姓名*/
	private java.lang.String userName;

	/**日志内容*/
	private java.lang.String logContent;

	/**插入时间*/
	private java.util.Date logTime;

	/**日志类型*/
	private java.lang.String logType;
	//private java.lang.String logTypeCN;

	/**所属模块*/
	private java.lang.String logModule;

	/**操作类型*/
	private java.lang.String logOperate;
	//private java.lang.String logOperateCN;

	/**有效性，1有效，0无效，默认1*/
	private java.lang.String validateState;
	private java.lang.String validateStateCN;

	/**是否归档，1已归档，0未归档*/
	private java.lang.String isArchive;
	private java.lang.String isArchiveCN;

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
	 *方法: 获得clientIp
	 *@return: java.lang.String  clientIp
	 */
	public java.lang.String getClientIp(){
		return this.clientIp;
	}

	/**
	 *方法: 设置clientIp
	 *@param: java.lang.String  clientIp
	 */
	public void setClientIp(java.lang.String clientIp){
		this.clientIp = clientIp;
	}

	/**
	 *方法: 获得userId
	 *@return: java.lang.Long  userId
	 */
	public java.lang.Long getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置userId
	 *@param: java.lang.Long  userId
	 */
	public void setUserId(java.lang.Long userId){
		this.userId = userId;
	}

	/**
	 *方法: 获得userName
	 *@return: java.lang.String  userName
	 */
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置userName
	 *@param: java.lang.String  userName
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}

	/**
	 *方法: 获得logContent
	 *@return: java.lang.String  logContent
	 */
	public java.lang.String getLogContent(){
		return this.logContent;
	}

	/**
	 *方法: 设置logContent
	 *@param: java.lang.String  logContent
	 */
	public void setLogContent(java.lang.String logContent){
		this.logContent = logContent;
	}

	/**
	 *方法: 获得logTime
	 *@return: java.util.Date  logTime
	 */
	public java.util.Date getLogTime(){
		return this.logTime;
	}

	/**
	 *方法: 设置logTime
	 *@param: java.util.Date  logTime
	 */
	public void setLogTime(java.util.Date logTime){
		this.logTime = logTime;
	}

	/**
	 *方法: 获得logType
	 *@return: java.lang.String  logType
	 */
	public java.lang.String getLogType(){
		return this.logType;
	}
	
	public java.lang.String getLogTypeCN(){
		String logTypeCN = "";
		if(SysBizLogDTO.LogType.businesslog.toString().equals(this.getLogType())){
			logTypeCN = SysBizLogDTO.LogType.businesslog.toStringCN();
		}else if (SysBizLogDTO.LogType.interfacelog.toString().equals(this.getLogType())) {
			logTypeCN = SysBizLogDTO.LogType.interfacelog.toStringCN();
		}else if (SysBizLogDTO.LogType.performancelog.toString().equals(this.getLogType())) {
			logTypeCN = SysBizLogDTO.LogType.performancelog.toStringCN();
		}
		return logTypeCN;
	}

	/**
	 *方法: 设置logType
	 *@param: java.lang.String  logType
	 */
	public void setLogType(java.lang.String logType){
		this.logType = logType;
	}

	/**
	 *方法: 获得logModule
	 *@return: java.lang.String  logModule
	 */
	public java.lang.String getLogModule(){
		return this.logModule;
	}

	/**
	 *方法: 设置logModule
	 *@param: java.lang.String  logModule
	 */
	public void setLogModule(java.lang.String logModule){
		this.logModule = logModule;
	}

	/**
	 *方法: 获得logOperate
	 *@return: java.lang.String  logOperate
	 */
	public java.lang.String getLogOperate(){
		return this.logOperate;
	}
	
	public java.lang.String getLogOperateCN() {
		String logOperateCN = "";
		if(SysBizLogDTO.OperateType.login.toString().equals(this.getLogOperate())){
			logOperateCN = SysBizLogDTO.OperateType.login.toStringCN();
		}else if (SysBizLogDTO.OperateType.insert.toString().equals(this.getLogOperate())) {
			logOperateCN = SysBizLogDTO.OperateType.insert.toStringCN();
		}else if (SysBizLogDTO.OperateType.update.toString().equals(this.getLogOperate())) {
			logOperateCN = SysBizLogDTO.OperateType.update.toStringCN();
		}else if (SysBizLogDTO.OperateType.view.toString().equals(this.getLogOperate())) {
			logOperateCN = SysBizLogDTO.OperateType.view.toStringCN();
		}else if (SysBizLogDTO.OperateType.delete.toString().equals(this.getLogOperate())) {
			logOperateCN = SysBizLogDTO.OperateType.delete.toStringCN();
		}
		return logOperateCN;
	}

	/**
	 *方法: 设置logOperate
	 *@param: java.lang.String  logOperate
	 */
	public void setLogOperate(java.lang.String logOperate){
		this.logOperate = logOperate;
	}

	/**
	 *方法: 获得validateState
	 *@return: java.lang.String  validateState
	 */
	public java.lang.String getValidateState(){
		return this.validateState;
	}
	
	public java.lang.String getValidateStateCN() {
		if("1".equals(getValidateState())){
			this.validateStateCN = "有效";
		}else if ("0".equals(getValidateState())) {
			this.validateStateCN = "无效";
		}
		return this.validateStateCN;
	}

	/**
	 *方法: 设置validateState
	 *@param: java.lang.String  validateState
	 */
	public void setValidateState(java.lang.String validateState){
		this.validateState = validateState;
	}

	/**
	 *方法: 获得isArchive
	 *@return: java.lang.String  isArchive
	 */
	public java.lang.String getIsArchive(){
		return this.isArchive;
	}
	public java.lang.String getIsArchiveCN() {
		if("1".equals(getIsArchive())){
			this.isArchiveCN = "已归档";
		}else if ("0".equals(getIsArchive())) {
			this.isArchiveCN = "未归档";
		}
		return this.isArchiveCN;
	}
	
	/**
	 *方法: 设置isArchive
	 *@param: java.lang.String  isArchive
	 */
	public void setIsArchive(java.lang.String isArchive){
		this.isArchive = isArchive;
	}




	

}