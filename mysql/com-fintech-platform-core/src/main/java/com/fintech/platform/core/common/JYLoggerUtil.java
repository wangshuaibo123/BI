package com.fintech.platform.core.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Description: 定义处理日志的工具类
 * @author
 * @version 1.0, 
 * @date 2014-9-02 下午02:44:19
 */
public class JYLoggerUtil {
	private final static Logger log = LoggerFactory.getLogger(JYLoggerUtil.class);
	
	private static ThreadLocal<Map<String, Date>> threadMap = new ThreadLocal<Map<String, Date>>();
	
	
	public static <T> void info(Class<T> clazz,String message){
		Logger logger = LoggerFactory.getLogger(clazz);
		logger.info(message);
	}
	
	public static <T> void info(Class<T> clazz,String message,Throwable t){
		Logger logger = LoggerFactory.getLogger(clazz);
		logger.info(message, t);
	}
	
	public static <T> void debug(Class<T> clazz,String message){
		Logger logger = LoggerFactory.getLogger(clazz);
		logger.debug(message);
	}
	
	
	public static <T> void error(Class<T> clazz,String message){
		Logger logger = LoggerFactory.getLogger(clazz);
		logger.error(message);
	}
	
	public static <T> void error(Class<T> clazz,String message,Exception ex){
		Logger logger = LoggerFactory.getLogger(clazz);
		logger.error(message,ex);
	}
	
	/**
	 * 打印  某个方法执行的耗时
	 * 使用参考 WorkflowController addTemporaryJbpm4Info()
	 * JYLoggerUtil.logCurrentTime("addTemporaryJbpm4Info.do", true, this.getClass());
	 * ......执行方法
	 * JYLoggerUtil.logCurrentTime("addTemporaryJbpm4Info.do", false, this.getClass());
	 * @param <T>
	 * @param key 
	 * @param isStart 方法是否开始执行
	 * @param cls
	 */
	public static <T> void logCurrentTime(String key, boolean isStart, Class<T> cls) {
		
		//如果logger 日志级别不是level 级别则不输出该日志
		//System.out.println("logger.isDebugEnabled():"+logger.isInfoEnabled());
		//if(!logger.isDebugEnabled()) return ;
		
		if(!log.isInfoEnabled()) return ;
		
		Map<String, Date> timeMap = threadMap.get();//获取当前线程中保存的变量副本
		if (timeMap == null) {
			timeMap = new HashMap<String, Date>();
			threadMap.set(timeMap);//移除当前线程中变量的副本
		}
		
		String mapKey = cls.getName() + ":" + key;
		Date curDate = new Date();
		if (isStart) {
			timeMap.put(mapKey, curDate);
		} else {
			Date lastDate = timeMap.get(mapKey);
			if (lastDate != null) {
				timeMap.remove(mapKey);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				StringBuilder logInfo = new StringBuilder("=========");
				
				logInfo.append("，（位置：").append(cls.getName());
				logInfo.append("，内容：").append(key).append(")");
				logInfo.append("，间隔：").append(curDate.getTime() - lastDate.getTime());
				logInfo.append(",开始start：").append(sdf.format(lastDate));
				logInfo.append("，结束end：").append(sdf.format(curDate)).append("=========");
				
				log.info(logInfo.toString());
				threadMap.remove();//移除当前线程中变量的副本
			}
		}
		
		
	}
	/**
	 * 输出统一业务日志信息用于后续日志收集及统计
	 * @param obj
	 * @param cls
	 */
	public static <T> void showBizLogInfo(Object showOBJ, Class<T> cls) {
		StringBuilder logInfo = new StringBuilder("龍CONTENT:");
		
		logInfo.append(cls.getName());
		logInfo.append("龍CONTENT：").append(JSONObject.toJSONString(showOBJ));
		
		log.info(logInfo.toString());
	}
	/**
	 * 输出统一业务日志信息用于后续日志收集及统计
	 * @param obj
	 * @param cls
	 */
	public static <T> void showBizLogMsg(String msg, Class<T> cls) {
		StringBuilder logInfo = new StringBuilder("龍CONTENT:");
		
		logInfo.append(cls.getName());
		logInfo.append("龍CONTENT：").append(msg);
		
		log.info(logInfo.toString());
	}

}
