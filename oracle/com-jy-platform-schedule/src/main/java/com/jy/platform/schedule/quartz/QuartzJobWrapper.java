package com.jy.platform.schedule.quartz;

import org.quartz.JobExecutionContext;

import com.jy.platform.schedule.Job;

/**
 * 调度框架为了兼容quartz的job（平台的定时任务）而包装的类。定时任务都是实现的org.quartz.Job，
 * execute方法有JobExecutionContext参数，项目中开发人员只用它来获取spring容器中的bean。为了兼容，
 * 特意包装，尽量不改动原有代码。
 * @author 张羽
 *
 */
public abstract class QuartzJobWrapper implements Job, org.quartz.Job {
    
    /**
     * 创建一个JobExecutionContext实例，作为参数，调用原来的execute(ctx)方法
     */
    @Override
    public void execute() throws Exception {
        JobExecutionContext ctx = new QuartzJobExecutionContextWrapper();
        execute(ctx);
    }

    @Override
    public void rollback() throws Exception {

    }

    @Override
    public void pause() throws Exception {

    }

    @Override
    public String getState() {
        return null;
    }

}
