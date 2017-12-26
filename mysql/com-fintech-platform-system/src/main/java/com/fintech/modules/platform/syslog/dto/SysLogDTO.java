package com.fintech.modules.platform.syslog.dto;

import java.util.Calendar;

import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.tools.common.DateUtil;

/**
 *@Description:拦截日志
 *@author
 *@version 1.0,
 *@date 2014-12-15
 */
public class SysLogDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/*主键id*/
	private Long id;
	
	/*当前操作的用户*/
	private String userId;
	
	/*当前模块名称*/
	private String moduleName;
	
	/*当前调用的Controller 类名*/
	private String className;
	
	/*当前调用的方法名称*/
	private String methodName;
	
	/*当前调用的方法所有的参数*/
	private String paramInfo;
	
	private String uri;
	/**
	 * 1 表示uri拦截日志，0 表示系统日志
	 */
	private String type;
	
	/*当前日志产生的时间*/
	private String created;
	
	

	@Override
	public String toString() {
		
		return id+"\t"+type+"\t"+userId+"\t"
				+moduleName+"\t"+getClassName()+
				"\t"+methodName+"\t"+paramInfo+
				"\t"+uri+"\t"+
				DateUtil.format(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss:SSS");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}


	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParamInfo() {
		return paramInfo;
	}

	public void setParamInfo(String paramInfo) {
		this.paramInfo = paramInfo;
	}


	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
