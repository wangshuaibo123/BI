package com.jy.platform.schedule.demo;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.platform.schedule.quartz.QuartzJobWrapper;

@Controller
@RequestMapping("/api/demojob1")
public class DemoJob1 extends QuartzJobWrapper implements Job {

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        System.out.println(new java.sql.Time(System.currentTimeMillis()).toString() + " ##DemoJob1被调用了，JobExecutionContext=" + ctx);
        SchedulerContext sc;
        try {
            sc = ctx.getScheduler().getContext();
        }
        catch (SchedulerException e) {
            e.printStackTrace();
            return;
        }
        ApplicationContext ac = (ApplicationContext) sc.get("applicationContextKey");
        System.out.println(ac.getBean("startQuertz").getClass().getName());
    }

    /**
     * 供测试调用，模拟control-m调用的场景
     */
    @RequestMapping(value="/test", method={RequestMethod.GET}, produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void test() {
        try {
            this.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
