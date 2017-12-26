package com.fintech.platform.core.message;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 定义服务方返回参数基础对象
 * @author Administrator
 *
 */
public class ResponseBaseDTO implements Serializable{
	private static final long serialVersionUID = 2696919189793467091L;
	//全局id
	private String globalID;
	//消息返回时间 
    private String retTime;
    ////消息头返回的编码0000代表成功（业务编码）
    private String retCode;
    //非成功状态返回的信息描述
    private String retMsg;
    //类别编码
    private String categoryCode;
	////接口名称(服务名.方法名)
    private String interfaceName;
    
    
    public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
    public String getGlobalID() {
		return globalID;
	}
	public void setGlobalID(String globalID) {
		this.globalID = globalID;
	}
	public String getRetTime() {
		return retTime;
	}
	public void setRetTime(String retTime) {
		this.retTime = retTime;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
    
    public ResponseBaseDTO(){
		this.retTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		this.retCode="0000";//成功状态
    }
    public ResponseBaseDTO(HeaderBean req) {
    	this.retTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    	this.retCode="0000";//成功状态
    	this.interfaceName = req.getInterfaceName();
    	this.globalID = req.getGlobalID();
    	this.categoryCode = req.getCategoryCode();
	}
    
}
