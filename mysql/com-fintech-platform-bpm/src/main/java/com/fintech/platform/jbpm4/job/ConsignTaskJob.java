package com.fintech.platform.jbpm4.job;

import java.io.Serializable;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fintech.platform.jbpm4.dto.Consign;
import com.fintech.platform.jbpm4.service.ConsignService;
import com.fintech.platform.jbpm4.service.JbpmTastService;
/**
 * 定时将 设置 任务委托的  人明细的 待办任务进行 任务转移 操作
 * @description:定时任务 处理 任务委托 
 * @author
 * @date:2014年12月16日下午3:14:15
 */
@Component("com.fintech.platform.jbpm4.job.ConsignTaskJob")
public class ConsignTaskJob implements Serializable,Job{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ConsignTaskJob.class);

    private ConsignService consignService;
    private JbpmTastService jbpmService;
    
    private static boolean isNext = true;
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(!isNext){
			logger.info("----------进入方法ExcuteLoanAsynJob execute---------------");
			return ;
		}
		isNext = false;
		try {
			SchedulerContext cont = context.getScheduler().getContext();
			ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
			consignService = (ConsignService) appCtx.getBean("com.fintech.platform.jbpm4.service.impl.ConsignServiceImpl");
			jbpmService = (JbpmTastService) appCtx.getBean("com.fintech.platform.jbpm4.service.impl.JbpmTaskServceImpl");
			
			List<Consign> consignList = consignService.getConsignTask(null);
			for(Consign task : consignList){
				String formUserId = task.getFromUserId();
				//jbpm提供返回需处理的taskid接口
				List<String> taskList = jbpmService.getTaskInfByUserId(formUserId);
				//插入委托轨迹表
				if(taskList!=null && taskList.size() >0){
					for(String taskId : taskList){
						jbpmService.updateAssignee(taskId, task.getFromUserId(), task.getToUserId(), "1");
					}
				}
				//检测失效的工作委托 表，数据有效性改为0
				consignService.updateConsignValidateState(null);
			}
		}catch(Exception ex){
			logger.error("==========com.fintech.platform.jbpm4.job.ConsignTaskJob 执行失败===========",ex);
		}finally{
			isNext= true;
		}
	
	}

}
