package com.fintech.modules.platform.sysasynjob.service;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.springframework.context.ApplicationContext;

import com.fintech.modules.platform.common.util.PlatformThreadTool;
import com.fintech.platform.api.bizasynjob.BizAsynJobAPI;
import com.fintech.platform.core.common.JYLoggerUtil;

/**
 * 
 * @description 异步 任务轮询  多线程 执行类 查询  多线程处理执行类
 * @author DELL
 * @date 2016年9月12日
 */
public class ThreadExecutor implements Runnable{
	private CountDownLatch cdlDept=null;
	private Map<String, String> param ;
	private ApplicationContext appCtx;
	public ThreadExecutor(Map<String,String> param,ApplicationContext appCtx){
		//this.cdlDept=countDownLatch;
		this.param = param;
		this.appCtx = appCtx;
	}
	public void run() {
		String jobKeyId ="";
		String beanId = param.get("beanId");
		String bizKeyId = param.get("bizKeyId");
		//获取 实例ID 并调用 相关方法 
		BizAsynJobAPI asynBiz =null;
		try {
			jobKeyId = param.get("jobKeyId");
			asynBiz = (BizAsynJobAPI) appCtx.getBean(beanId);
			boolean flag = asynBiz.executeAsynJob(jobKeyId, bizKeyId);
			if(flag){
				//执行方法 executeAsynJob 成功后 调用 executeNext方法
				asynBiz.executeNext(jobKeyId, bizKeyId);
			}else{
				//执行方法 executeAsynJob 返回false后 则回滚
				asynBiz.rollback(jobKeyId, bizKeyId);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//执行方法 executeAsynJob 返回false后 则回滚
			try {
				asynBiz.rollback(jobKeyId, bizKeyId);
			} catch (Exception e1) {
				JYLoggerUtil.error(this.getClass(), "====ThreadManagerQuery=====rollback=beanId:"+beanId+"=jobKeyId:"+jobKeyId, e1);
			}
			
			JYLoggerUtil.error(this.getClass(), "====ThreadManagerQuery=====error=beanId:"+beanId+"=jobKeyId:"+jobKeyId, e);
		}

		//将 threadMap 移除 该 jobKeyId
		PlatformThreadTool.threadMap.remove("jobKeyId:"+jobKeyId+"key");
		//计数器 减一
		//cdlDept.countDown();
	}
	
	
	
}
