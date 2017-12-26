package com.fintech.modules.quartzJob.core.service.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fintech.modules.quartzJob.core.service.ISchedulerService;

@Service("com.fintech.modules.quartzJob.core.service.impl.SchedulerServiceImpl")
public class SchedulerServiceImpl implements ISchedulerService,Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    @Autowired
    @Qualifier("startQuertz")
    @Lazy
    private Scheduler scheduler;

    /**
     * @author 根据名称和组别暂停Tigger
     * @param triggerName
     * @param group
     */
    @Override
    public void pauseTrigger(String triggerName, String group) {
        try {
            scheduler.pauseTrigger(new TriggerKey(triggerName));
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author 恢复Trigger
     * @param triggerName
     * @param group
     */
    @Override
    public void resumeTrigger(String triggerName, String group) {
        try {
            // Trigger trigger = scheduler.getTrigger(triggerName);

            scheduler.resumeTrigger(new TriggerKey(triggerName));
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author 删除Trigger
     * @param triggerName
     * @param group
     */
    @Override
    public boolean removeTrigdger(String triggerName, String group) throws Exception {
        // 停止触发
        scheduler.pauseTrigger(new TriggerKey(triggerName));
        // 移除触发器
        return scheduler.unscheduleJob(new TriggerKey(triggerName));

        // scheduler.deleteJob(jobName, jobGroup);

    }

    private Date parseDate(String time) {
        try {
            return DateUtils.parseDate(time, new String[] { "yyyy-MM-dd HH:mm:ss" });
        }
        catch (ParseException e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * @author 添加新的job 实例
     * @param jobName
     * @param jobGroupName
     * @param CronExp con 表达式
     * @param clz
     * @return
     * @throws Exception
     */
    @Override
    public String addNewJob(String jobName, String jobGroupName, String cronExp, String clz) throws Exception {
        SchedulerContext cont = scheduler.getContext();
        ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
        jobName = jobName + "_" + UUID.randomUUID().toString();
        System.out.println(appCtx + "-----------" + clz);
        JobDetailImpl jobDetail = new JobDetailImpl();// 任务名，任务组，任务执行类
        jobDetail.setName(jobName);
        jobDetail.setGroup(Scheduler.DEFAULT_GROUP);
        jobDetail.setJobClass((Class<? extends Job>) this.getClass().forName(clz));
//        CronTrigger trigger = new CronTrigger(jobName, Scheduler.DEFAULT_GROUP);// 触发器名,触发器组
        CronTriggerImpl trigger=new CronTriggerImpl();
        trigger.setName(jobName);
        trigger.setGroup(Scheduler.DEFAULT_GROUP);

        trigger.setCronExpression(cronExp);// 触发器时间设定

        scheduler.scheduleJob(jobDetail, trigger);

        // 启动
        if (!scheduler.isShutdown())
            scheduler.start();

        return "";
    }

    /**
     * @author 修改复杂的定时任务
     * @param triName
     * @param triGroupName
     * @param jobName xml文件自定义的jobName 和 triName 可能不同
     * @param CronExp
     * @throws Exception
     */
    @Override
    public boolean updateJob(String triName, String triGroupName, String jobName, String cronExp) throws Exception {
        boolean isTrue = false;
        System.out.println("impl---------------" + triName + "," + triGroupName + "," + jobName + "," + cronExp);
        if (StringUtils.isEmpty(triGroupName)) {
            // 没有说明是在 默认分组 中
            triGroupName = Scheduler.DEFAULT_GROUP;
        }
        Trigger trigger = scheduler.getTrigger(new TriggerKey(triName));
        if (trigger != null) {
            JobDetail myJobDetail = scheduler.getJobDetail(new JobKey(jobName));
            Class jobClz = myJobDetail.getJobClass();
            // 暂定定时任务
            this.pauseTrigger(triName, triGroupName);

            // 移除定时任务
            this.removeTrigdger(triName, triGroupName);

            String tempTrName = triName;
            // 如果是含有下划线的 则说明是通过系统web 新增的。
            // 如：tr复杂的job_b460988c-c188-4e5a-ba07-84ef9540dfa9
            if (triName.indexOf("_") > 0) {
                tempTrName = triName.substring(0, triName.indexOf("_"));
                // 新增job信息
                this.addNewJob(tempTrName, triGroupName, cronExp, jobClz.getName());
            } else {
                // 如果不含有下滑线说明是系统 xml文件配置的定时任务
                /**/
                JobDataMap jobData = myJobDetail.getJobDataMap();

                SchedulerContext cont = scheduler.getContext();
                ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");

                Object targetObject = jobData.get("targetObject");
                if (targetObject == null) {

                } else {
                    jobData.put("targetObject", appCtx.getBean(targetObject.getClass().getName()));
                    String targetMethod = jobData.getString("targetMethod");
                    String staticMethod = jobData.getString("staticMethod");
                    Object[] arguments = (Object[]) jobData.get("arguments");

                }

                updateXmlquartzJob(tempTrName, triGroupName, cronExp, myJobDetail);
            }

            isTrue = true;
        }

        return isTrue;
    }

    /**
     * 更新 xml文件自身配置的 定时任务、
     * 
     * @author
     * @param jobName
     * @param jobGroupName
     * @param cronExp
     * @param jobDetail
     * @throws Exception
     */
    private void updateXmlquartzJob(String jobName, String jobGroupName, String cronExp, JobDetail jobDetail)
            throws Exception {

//        CronTrigger trigger = new CronTrigger(jobName, Scheduler.DEFAULT_GROUP);// 触发器名,触发器组
    	CronTriggerImpl trigger=new CronTriggerImpl();
    	trigger.setJobName(jobName);
    	trigger.setGroup(Scheduler.DEFAULT_GROUP);

        trigger.setCronExpression(cronExp);// 触发器时间设定

        scheduler.scheduleJob(jobDetail, trigger);

        // 启动
        if (!scheduler.isShutdown())
            scheduler.start();

    }
/*
 * (non-Javadoc)
 * @see com.pt.demo.dao.service.ISchedulerService#addSimpleTrigger(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
 */
    public void addSimpleTrigger(String jobName,String jobGroupName,String startTime,String endTime,
			String repeatCount,String repeatInterval,String clz) throws Exception{
		
		SchedulerContext cont = scheduler.getContext();
		ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
		//如：tr复杂的job_b460988c-c188-4e5a-ba07-84ef9540dfa9
		jobName = jobName +"_"+UUID.randomUUID().toString();//生成唯一的uuID 
		
		SimpleTriggerImpl simpleTrigger = new SimpleTriggerImpl();
		
		simpleTrigger.setJobName(jobName);		
		simpleTrigger.setJobGroup(Scheduler.DEFAULT_GROUP);		
		simpleTrigger.setRepeatInterval(1000L);
		
		
		simpleTrigger.setName(jobName);
		
		
		simpleTrigger.setGroup(Scheduler.DEFAULT_GROUP);
		
		simpleTrigger.setStartTime(this.parseDate(startTime));
		
		if(StringUtils.isNotEmpty(endTime)){
			simpleTrigger.setEndTime(this.parseDate(endTime));
		}
		
		if(StringUtils.isNotEmpty(repeatCount) && NumberUtils.toInt(repeatCount) > 0){
			simpleTrigger.setRepeatCount(NumberUtils.toInt(repeatCount));
		}
		
		if(StringUtils.isNotEmpty(repeatInterval) && NumberUtils.toLong(repeatInterval) > 0){
			simpleTrigger.setRepeatInterval(NumberUtils.toLong(repeatInterval)*1000);
		}

//		JobDetail jobDetail = new JobDetail(jobName, Scheduler.DEFAULT_GROUP, appCtx.getBean(clz).getClass());//任务名，任务组，任务执行类  
		JobDetailImpl jobDetail = new JobDetailImpl();// 任务名，任务组，任务执行类
        jobDetail.setName(jobName);
        jobDetail.setGroup(Scheduler.DEFAULT_GROUP);
        jobDetail.setJobClass((Class<? extends Job>) this.getClass().forName(clz));
		scheduler.addJob(jobDetail, true);
	
		scheduler.scheduleJob(simpleTrigger);
//		scheduler.rescheduleJob(simpleTrigger.getName(), simpleTrigger.getGroup(), simpleTrigger);
		scheduler.rescheduleJob(new TriggerKey(simpleTrigger.getName()), simpleTrigger);
		
		/*// job8  可以立即执行. 无trigger注册  
        job = newJob(SimpleJob.class).withIdentity("job8", "group1")  
                .storeDurably().build();  
        scheduler.addJob(job, true);  
        scheduler.triggerJob(jobKey("job8", "group1"));  */
			
	}
}
