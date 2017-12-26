package com.fintech.modules.platform.sysasynjob.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 异步 调用 第三方 接口
 * @description:使用@Aspect 注解的类， Spring 将会把它当作一个特殊的Bean（一个切面）
 * @author chen_gang
 * @date:2014年10月9日上午9:13:44
 */
//@Component("com.fintech.modules.platform.sysasynjob.service.SysAsynJobAspect")
//@Aspect
public class SysAsynJobAspect {
	//必须为final String类型的,注解里要使用的变量只能是静态常量类型的
	public static final String EDP = "execution(* com.fintech.modules.befloan.callthirdbiz.*.executeAsynJob(..))";//"execution(public * com.pt.demo.dao.service.bizJob..*(..))";

	private final static Logger logSlf4j = LoggerFactory.getLogger("DEBUG");
	@Autowired
    @Qualifier("com.fintech.modules.platform.sysasynjob.service.SysAsynJobService")
    private SysAsynJobService asynJobService;
	
	//@Around(EDP) //spring中Around通知
	public Object logAround(ProceedingJoinPoint joinPoint) throws Exception {
		logSlf4j.debug("SysAsynJobAspect.logAround===============================start======");
		Object[] args = joinPoint.getArgs();
		String jobKeyId = (String) args[0];
		//更新 异步接口调用开始时间
		asynJobService.updateJobStartTime(jobKeyId);
		String jobState ="1";//1:待调用, 0:已完成
		
		String errorRemark = "";//异常信息
		Object obj = null;
		try {
			obj = joinPoint.proceed(args);

			if( obj != null && (Boolean)obj == true){
				jobState = "0";//已完成
			}
		}catch (Exception e) {
			logSlf4j.error("=====SysAsynJobAspect.logAround==Exception=error==",e);
			//jobState ="1";//抛异常 则继续 待轮循
			errorRemark = e.getMessage();
			
		} catch (Throwable e) {
			logSlf4j.error("=====SysAsynJobAspect.logAround==Throwable==error===",e);
			//jobState ="1";//抛异常 则继续 待轮循
			errorRemark = e.getMessage();
			
		}finally{
			if(errorRemark != null && errorRemark.length() >2000){
				errorRemark = errorRemark.substring(0, 2000);
			}
			
			//更新 异步接口调用 后的  状态、结束时间、异常信息 等
			if("0".equals(jobState)){//已完成
				asynJobService.updateSysAsynJob(jobKeyId, "0", errorRemark);
			}else{
				//回填错误日志信息
				asynJobService.updateJobErrorMsg(jobKeyId, errorRemark);
			}
			
			logSlf4j.debug("SysAsynJobAspect.logAround===============================end======");
		}
		
		return obj;
	}

}
