package com.jy.platform.api.syslog;

public interface BizLogAPI {
	
	public enum LogType {
	       interfacelog, businesslog , performancelog ;
	}
	public enum OperateType {
	       login , insert , update, delete, view ;
	}
	
	/**
	 * 
	 * @param clientIp 操作IP
	 * @param userId 操作人ID
	 * @param logModule 日志所属业务模块
	 * @param logContent 日志内容，JSON格式，业务系统自己定义自己解析
	 * @param logType 日志类型:接口日志，业务日志，性能日志
	 * @param logOperate 日志操作类型：登录，插入，修改，删除，查看
	 * @return
	 */
	public boolean log(String clientIp, String userId, String logModule, String logContent, LogType logType,  OperateType logOperate);


}
