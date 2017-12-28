package com.jy.platform.schedule.quartz;

import java.util.Date;

import org.quartz.Calendar;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.Trigger;

import com.jy.modules.platform.schedule2.ApplicationContextHelper;

/**
 * 只提供getScheduler方法，其他方法都不可用
 * @author 张羽
 *
 */
public class QuartzJobExecutionContextWrapper implements JobExecutionContext {

    @Override
    public Scheduler getScheduler() {
        // quartz的Scheduler在spring中定义的bean名称，如果没有配置，默认为startQuertz
        String schedulerBeanName = ApplicationContextHelper.getConfig("schedulerBeanName");
        if (schedulerBeanName == null) {
            schedulerBeanName = "startQuertz";
        }
        
        // 获取quartz的Scheduler
        try {
            Object o = ApplicationContextHelper.getBean(schedulerBeanName);
            return (Scheduler) o;
        }
        catch (Exception e) {
            throw new RuntimeException("Control-M调度模式下获取调度器异常：" + e.getMessage());
        }
    }

    /**
     * 在Control-M调度模式下，不支持quartz原生方法
     */
    private void throwUnsupportedException() {
        throw new RuntimeException("Control-M调度模式下，不支持quartz原生方法。");
    }

    @Override
    public Object get(Object key) {
        throwUnsupportedException();
        return null;
    }

    @Override
    public Calendar getCalendar() {
        throwUnsupportedException();
        return null;
    }

    @Override
    public String getFireInstanceId() {
        throwUnsupportedException();
        return null;
    }

    @Override
    public Date getFireTime() {
        throwUnsupportedException();
        return null;
    }

    @Override
    public JobDetail getJobDetail() {
        throwUnsupportedException();
        return null;
    }

    @Override
    public Job getJobInstance() {
        throwUnsupportedException();
        return null;
    }

    @Override
    public long getJobRunTime() {
        throwUnsupportedException();
        return 0;
    }

    @Override
    public JobDataMap getMergedJobDataMap() {
        throwUnsupportedException();
        return null;
    }

    @Override
    public Date getNextFireTime() {
        throwUnsupportedException();
        return null;
    }

    @Override
    public Date getPreviousFireTime() {
        throwUnsupportedException();
        return null;
    }

    @Override
    public int getRefireCount() {
        throwUnsupportedException();
        return 0;
    }

    @Override
    public Object getResult() {
        throwUnsupportedException();
        return null;
    }

    @Override
    public Date getScheduledFireTime() {
        throwUnsupportedException();
        return null;
    }

    @Override
    public Trigger getTrigger() {
        throwUnsupportedException();
        return null;
    }

    @Override
    public boolean isRecovering() {
        throwUnsupportedException();
        return false;
    }

    @Override
    public void put(Object arg0, Object arg1) {
        throwUnsupportedException();
    }

    @Override
    public void setResult(Object arg0) {
        throwUnsupportedException();
    }

}
