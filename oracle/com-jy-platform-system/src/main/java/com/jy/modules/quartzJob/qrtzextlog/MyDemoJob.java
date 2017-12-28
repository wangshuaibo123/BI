package com.jy.modules.quartzJob.qrtzextlog;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 测试用的job类
 * @author zhangyu
 *
 */
public class MyDemoJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("#####调用了我的demoJob......begin...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("#####调用了我的demoJob......end.");
	}

}
