package com.jy.platform.tuomin.monitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.jy.platform.tuomin.Configuration;
import com.jy.platform.tuomin.Configuration.ThreadConf;

public class MonitorService {
	
	private static final String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	private static ConcurrentHashMap<String, MonitorBean> map = new ConcurrentHashMap<>();
	
	/**
	 * 设置状态。初始化是把所有线程的配置都放到map中
	 */
	public static synchronized void initThreadStatus() {
		if (!map.isEmpty()) {
			return;
		}
		int tCount = Configuration.getThreadCount();
		for (int i = 0; i < tCount; i++) {
		    for (int j = 0; j < 1024; j++) {
		    	ThreadConf tc = Configuration.getThreadConf("t" + i + "." + j);
		    	if (tc == null) {
		    		//break;
		    		continue;
		    	}
		    	MonitorBean mb = new MonitorBean();
		    	mb.setThreadNo(tc.getKey());
		    	mb.setSysName(tc.getSysname());
		    	mb.setTableName(tc.getTableName());
		    	mb.setBeginTime("");
		    	mb.setEndTime("");
		    	mb.setStatus("");
		    	map.put(mb.getThreadNo(), mb);
		    }
		}
	}
	
	/**
	 * 设置开始时间
	 * @param key 线程编号
	 */
	public static void setBeginTime(String key) {
		String beginTime = new SimpleDateFormat(TIME_FORMAT).format(new Date());
		map.get(key).setBeginTime(beginTime);
	}
	
	/**
	 * 设置结束时间
	 * @param key 线程编号
	 */
	public static void setEndTime(String key) {
		String endTime = new SimpleDateFormat(TIME_FORMAT).format(new Date());
		MonitorBean bean = map.get(key);
		bean.setEndTime(endTime);
		// 如果状态不是异常，则清空
		if (!bean.getStatus().startsWith("Ex:")) {
			bean.setStatus("");
		}
	}
	
	/**
	 * 更新线程状态
	 * @param key 线程编号
	 * @param status 状态描述
	 */
	public static void setStatus(String key, String status) {
		MonitorBean bean = map.get(key);
		bean.setStatus(status);
	}
	
	/**
	 * 更新线程状态(异常)
	 * @param key 线程编号
	 * @param status 异常描述
	 */
	public static void setError(String key, String msg) {
		MonitorBean bean = map.get(key);
		bean.setStatus("Ex:" + msg);
	}

	/**
	 * 获取所有线程的状态
	 * @return 状态bean列表
	 */
	public static List<MonitorBean> getThreadStatus() {
		if (map.isEmpty()) {
			initThreadStatus();
		}
		MonitorBean[] beans = new MonitorBean[map.size()];
		beans = map.values().toArray(beans);
		Arrays.sort(beans);
		
		List<MonitorBean> list = new ArrayList<MonitorBean>();
		for (MonitorBean bean: beans) {
			list.add(bean);
		}
		return list;
	}

}
