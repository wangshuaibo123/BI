package com.fintech.modules.quartzJob.core.service;

import java.util.concurrent.CountDownLatch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.fintech.modules.platform.common.util.PlatformThreadTool;

/**
 * 
 * @Description: 查询  多线程处理执行类
 * @author
 * @version 1.0, 
 * @date 2014-8-14 上午10:00:12
 */
public class ThreadJobDetail implements Runnable{
	private static final Logger logger = LoggerFactory.getLogger(ThreadJobDetail.class);
	private CountDownLatch cdlDept=null;
	private String keyID;
	private String beanID;
	private JobExecutionContext context;
	private ApplicationContext appCtx;
	public ThreadJobDetail(String keyID,String beanID,JobExecutionContext context,ApplicationContext appCtx){
		//this.cdlDept=countDownLatch;
		this.keyID = keyID;
		this.beanID = beanID;
		this.context = context;
		this.appCtx = appCtx;
	}
	public void run() {
		String jobKeyId = keyID;
		IBaseJob stepJob = null;
		
			//获取 实例ID 并调用 相关方法 
		try {
			stepJob=(IBaseJob) appCtx.getBean(beanID);
			try {
				stepJob.execute(context,keyID);
			} catch (Throwable e) {
				if(stepJob!=null){
					try {
						stepJob.rollback(context, keyID);
					} catch (JobExecutionException e1) {
						// TODO Auto-generated catch block
						logger.error("执行回滚操作失败:"+e1.getMessage());
					}
				}
			}
		} catch (BeansException e) {//获取bean失败
			logger.error("获取bean失败:"+e.getMessage());
		}

		//将 threadMap 移除 该 jobKeyId
		PlatformThreadTool.THREAD_MAP.remove("jobKeyId:"+jobKeyId);
		//计数器 减一
		//cdlDept.countDown();
	}
	
	
	
}
