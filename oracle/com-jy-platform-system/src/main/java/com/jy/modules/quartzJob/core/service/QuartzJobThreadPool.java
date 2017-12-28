package com.jy.modules.quartzJob.core.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 
 * @Description: 线程池管理
 * @author chen
 * @version 1.0, 
 * @date 2014-7-17 下午12:14:41
 */
@SuppressWarnings("all")
public class QuartzJobThreadPool {
	
	private static ThreadPoolExecutor quartzJobExectutor = null;

	/**
	 * 功能:得到线程池实例
	 * @param corePoolSize 线程池维护线程的最少数量
	 * @param maximumPoolSize 线程池维护线程的最大数量
	 * @param keepAliveTime 线程池维护线程所允许的空闲时间
	 * @param unit 线程池维护线程所允许的空闲时间的单位
	 * @param workQueue 线程池所使用的缓冲队列
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ThreadPoolExecutor getThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue workQueue) {
		synchronized (QuartzJobThreadPool.class) {
			if (quartzJobExectutor == null) {
				
				quartzJobExectutor = createExecutor(
					corePoolSize,
					maximumPoolSize,
					keepAliveTime,
					unit,
					workQueue);
			}
		}
		
		return quartzJobExectutor;
	}

	/**
	 * 功能:创建ThreadPoolExecutor实例;
	 * @param corePoolSize：核心线程数量
	 * @param maximumPoolSize：最大线程数量
	 * @param keepAliveTime：线程空闲保持时间
	 * @param unit：时间单位
	 * @param workQueue：工作队列
	 * @param handler：旧任务抛弃策略
	 * @return
	 * ThreadPoolExecutor
	 */
	@SuppressWarnings("unchecked")
	private static ThreadPoolExecutor createExecutor(
		int corePoolSize,
		int maximumPoolSize,
		long keepAliveTime,
		TimeUnit unit,
		BlockingQueue workQueue) {
		quartzJobExectutor =
			new ThreadPoolExecutor(
				corePoolSize,
				maximumPoolSize,
				keepAliveTime,
				unit,
				workQueue);
		return quartzJobExectutor;
	}

}
