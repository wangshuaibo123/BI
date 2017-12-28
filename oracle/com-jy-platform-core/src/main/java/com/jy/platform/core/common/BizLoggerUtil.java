package com.jy.platform.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Description: 定义业务处理日志、通过分析此日志输出即可做到实时统计分析
 * @author chen
 * @version 1.0, 
 * @date 2014-9-02 下午02:44:19
 */
public class BizLoggerUtil {
	private final static Logger log = LoggerFactory.getLogger(BizLoggerUtil.class);

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
