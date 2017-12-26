package com.fintech.modules.quartzJob.core.service;

import java.util.Calendar;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fintech.modules.quartzJob.core.dao.QuartzMapper;
import com.fintech.modules.quartzJob.core.service.impl.JYQuartzService;
import com.fintech.platform.tools.common.DateUtil;

/**
 * 
 * @description:使用@Aspect 注解的类， Spring 将会把它当作一个特殊的Bean（一个切面）
 * @author
 * @date:2014年10月9日上午9:13:44
 */
//@Aspect
public class JobTaskAspect {
	private final static Logger logSlf4j = LoggerFactory.getLogger("DEBUG");
	@Autowired
    @Qualifier("com.fintech.modules.quartzJob.core.dao.QuartzMapper")
    private QuartzMapper quartzMapper;
	@Autowired
	@Qualifier("com.fintech.modules.quartzJob.core.service.impl.JYQuartzService")
	private JYQuartzService myQuartzService;
	//必须为final String类型的,注解里要使用的变量只能是静态常量类型的
	public static final String EDP = "execution(* com.pt.demo.dao.quartzJob.bizJob.impl.*.execute(..))";//"execution(public * com.pt.demo.dao.service.bizJob..*(..))";
/*
	//@Before(EDP) //spring中Before通知
	public void logBefore(ProceedingJoinPoint joinPoint) {
		String taskInstanceId = "";
		Object stepJob = joinPoint.getTarget();
	
		//String jobName = stepJob.getName();
		Object[] args = joinPoint.getArgs();
		
		quartzMapper.updateTaskInstanceIsEnd(taskInstanceId);
		System.out.println("logBefore:现在时间是:" + new Date());
	}

	//@After(EDP) //spring中After通知
	public void logAfter(JoinPoint joinPoint) {
		System.out.println("logAfter:现在时间是:" + new Date());
	}*/

	//@Around(EDP) //spring中Around通知
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		String keyID = "";
		Object[] args = joinPoint.getArgs();
		//获取 quartz_task_group_instance 的主键ID keyID
		keyID = (String) args[1];
		
		IBaseJob stepJob = (IBaseJob) joinPoint.getTarget();
		String beanId = stepJob.getClass().getName();
		String taskInfo="";
		//insert 
		Long keyId = 0l;
		//异常信息
		String exceptionInfo = "";
		//每个步骤的任务执行状态 1 ：成功，0：失败
		String taskState = "1";
		String threadId = Thread.currentThread().getName();
		quartzMapper.updateTaskInstanceIsEnd(keyID);
		//start insert log
		keyId = myQuartzService.insertQuartzLog(threadId,keyID,taskInfo);
		logSlf4j.info("--------------insert step task 执行日志");
		
		Object obj = null;
		String start=DateUtil.format(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
		String end=start;
		try {
			obj = joinPoint.proceed(args);
			end=DateUtil.format(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
		} catch (Throwable e) {
			exceptionInfo = e.getMessage();
			//失败
			taskState = "0";
			throw e;
			//e.printStackTrace();
		}finally{
			// update log
			logSlf4j.info("----------update step task 执行日志");
			if(exceptionInfo!=null&&exceptionInfo.length()>4000){
				exceptionInfo=exceptionInfo.substring(0,2000);
			}
			myQuartzService.updateQuartzLog(keyId,keyID,taskState,start,end,exceptionInfo);
		}
		
		return obj;
	}

}
