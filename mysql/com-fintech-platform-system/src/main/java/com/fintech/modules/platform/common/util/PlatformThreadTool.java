package com.fintech.modules.platform.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 
 * @description:定义 补充  线程池 工具类
 * @author
 * @date:2014年12月5日下午2:06:48
 */
public class PlatformThreadTool {
		//存放 有哪些 jobKeyId 已经放入线程池队列中 仅供platform 平台 系统  使用
		public static Map<String,String> THREAD_MAP = new ConcurrentHashMap<String,String>();//此 类是线程安全的
		
		public static Map<String,String> threadMap = new ConcurrentHashMap<String,String>();//此 类是线程安全的
		
}
