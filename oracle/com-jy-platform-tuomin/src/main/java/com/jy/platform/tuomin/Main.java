package com.jy.platform.tuomin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.platform.tuomin.monitor.MonitorService;

/**
 * 脱敏处理的主程序
 * @author zhangyu
 *
 */
public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);
	
	/** 是否已经启动 */
	private static boolean started;

	/** 是否已经启动 */
	public synchronized static boolean isStarted() {
		return started;
	}

	/** 是否已经启动 */
	public synchronized static void setStarted(boolean started) {
		Main.started = started;
	}

	/**
	 * 启动处理
	 */
	private static void star(String dbType) {
		if (started) {
			logger.warn("程序已经启动过了！");
			return;
		} else {
			setStarted(true);
		}
		
		// TODO
		MonitorService.initThreadStatus();
		
		// 获取配置-几个线程并行处理
		int tCount = Configuration.getThreadCount();
		logger.info("将要启动{}个线程并行处理", tCount);

		for (int i = 0; i < tCount; i++) {
			logger.info("启动处理线程t{}", i);
			if("ORACLE".equalsIgnoreCase(dbType)){
			    new Thread(new ProcessThread("t" + i)).start();
			}else if("MYSQL".equalsIgnoreCase(dbType)){
			    new Thread(new ProcessThreadMysql("t" + i)).start();
			}else{//默认使用oracle
                new Thread(new ProcessThread("t" + i)).start();
            }
		}

		logger.info("脱敏程序线程都已启动");
	}

	public static void main(String[] args) {
		// 启动处理程序
		star(args[0]);
	}

}
