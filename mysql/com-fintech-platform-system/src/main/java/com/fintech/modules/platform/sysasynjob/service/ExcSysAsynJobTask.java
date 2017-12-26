package com.fintech.modules.platform.sysasynjob.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fintech.modules.platform.common.util.PlatformThreadTool;
import com.fintech.modules.platform.sysasynjob.dto.SysAsynJobDTO;


/**
 * 异步 调用 接口
 * @author chengang
 *
 */
@Component("com.fintech.modules.platform.sysasynjob.service.ExcSysAsynJobTask")
public class ExcSysAsynJobTask implements Serializable,Job{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ExcSysAsynJobTask.class);
	//线程池初始化
	private static final int POOL_SIZE = 5;
	private static final int cpuNums = Runtime.getRuntime().availableProcessors();
	
	private static final ExecutorService exeJobPool = Executors.newFixedThreadPool(cpuNums * POOL_SIZE );
	
	private SysAsynJobService asynJobService;
    private static boolean isNext = true;
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(!isNext){
			logger.info("----------进入方法ExcSysAsynJobTask execute---------------");
			return ;
		}
		logger.info("----------ExcSysAsynJobTask execute start---------------");
		isNext = false;
		try {
			SchedulerContext cont = context.getScheduler().getContext();
			ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
			asynJobService = (SysAsynJobService) appCtx.getBean("com.fintech.modules.platform.sysasynjob.service.SysAsynJobService");
			
			Map<String,Object> asynParam = new HashMap<String,Object>();
			asynParam.put("jobState", "1");// 异步接口 待调用
			asynParam.put("runCun", "2");// 异常任务最多尝试3次
			
			
			List<SysAsynJobDTO> asynDtoList = asynJobService.searchSysAsynJob(asynParam);
			// 多线程 
			if(asynDtoList != null && asynDtoList.size() > 0){
				//得到线程池中线程队列
				//LinkedBlockingQueue<Runnable> queue = (LinkedBlockingQueue<Runnable>) asynExecutor.getQueue();
				int count = asynDtoList.size();
				//如果 线程池 队列过少 则等待下次
				//if(20000 -ThreadTool.threadMap.size() <= count) return;
				for(SysAsynJobDTO dto :asynDtoList){
					String beanId = dto.getBeanId();
					String bizKeyId = dto.getBizKeyId();
					String jobKeyId = dto.getId().toString();
					
					Map<String,String> tempParam = new HashMap<String,String>();
					tempParam.put("beanId", beanId);
					tempParam.put("bizKeyId", bizKeyId);
					tempParam.put("jobKeyId", jobKeyId);
					
					String key = "jobKeyId:"+jobKeyId+"key";
					if(PlatformThreadTool.threadMap.size() > 1000){
						//如果队列过大 则不在放入
						logger.error("====执行 ExcSysAsynJobTask 队列过大，等待下次异步处理====");
						break;
					}
					if(PlatformThreadTool.threadMap.containsKey(key)){
						//队列中 是否存在 了队列
						//存在则不再 放入队列中 直到 队列中 存放的执行完 
					}else{
						//将 jobKeyId 放入队列中 
						PlatformThreadTool.threadMap.put(key, jobKeyId);
						ThreadExecutor myThread = new ThreadExecutor(tempParam,appCtx);
						exeJobPool.execute(myThread);
					}
				}
			}
		}catch(Exception ex){
			logger.error(" 执行 ExcSysAsynJobTask 异常.........错误明细："+ex.getMessage());
		}finally{
			logger.info("----------ExcSysAsynJobTask execute end---------------");
			isNext= true;
		}
	}

}
