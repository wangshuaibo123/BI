package com.fintech.modules.logmonitor.sysapperrorinfo.job;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fintech.modules.logmonitor.sysapperrorinfo.service.SysAppErrorInfoService;
import com.fintech.modules.logmonitor.sysapplevelsetup.dto.SysAppLevelSetupDTO;
import com.fintech.modules.logmonitor.sysapplevelsetup.service.SysAppLevelSetupService;

/**
 * 错误日志收集到后，负责匹配错误日志级别
 */
@Component("com.fintech.modules.logmonitor.sysapperrorinfo.job.ExcuteAppErrorInfoMatchTask")
public class ExcuteAppErrorInfoMatchTask implements Serializable, Job {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ExcuteAppErrorInfoMatchTask.class);
    //控制  不允许 多线程 执行 public void execute(JobExecutionContext context) throws JobExecutionException  方法
    private static boolean isNext = true;
    
    private SysAppErrorInfoService errorInfoService;
    
    private SysAppLevelSetupService setupService;
    
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(!isNext){
			logger.info("------------ExcuteAppErrorInfoTask-------isNext:"+isNext);
			return ;
		}
		isNext = false;
		SchedulerContext cont;
		try {
			cont = context.getScheduler().getContext();
			ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
			errorInfoService=(SysAppErrorInfoService)appCtx.getBean(SysAppErrorInfoService.class);
			setupService=(SysAppLevelSetupService) appCtx.getBean(SysAppLevelSetupService.class);
			
			logger.info("更新错误日志的级别设置开始======================================");
			//查询所有错误级别
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("dto", new SysAppLevelSetupDTO());
			List<SysAppLevelSetupDTO> list = setupService.searchSysAppLevelSetup(paramMap);
			//循环更新日志级别
			for(SysAppLevelSetupDTO dto : list){
				errorInfoService.updateSysAppErrorInfoLevel(dto.getAppFlag(), dto.getKeyWord(), dto.getLogLevel().toString(), dto.getId());
			}
			//统一将此时所有匹配字段为N的更新为Y，上面循环日志级别时不更新，为了使得后面的匹配可以覆盖前面的
			errorInfoService.updateSysAppErrorMatched();
			logger.info("更新错误日志的级别设置结束=====================================");
		}
		catch(Exception ex){
			logger.error("---------------ExcuteAppErrorInfoMatchTask--error----------"+ex.getMessage());
		}
		finally{
			logger.info("------------ExcuteAppErrorInfoMatchTask-------end-------------");
			isNext= true;
		}
	}

}
