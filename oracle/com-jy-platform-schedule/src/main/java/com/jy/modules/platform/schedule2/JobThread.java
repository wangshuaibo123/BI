package com.jy.modules.platform.schedule2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.platform.schedule.Job;

/**
 * 作业线程
 * @author zhangyu
 *
 */
public class JobThread extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(JobThread.class);
	/**
	 * 作业线程是否结束了
	 */
	private boolean finished = false;

	/**
	 * job的执行结果
	 */
	private String jobResult;

	private SchedRequest schedRequest;

	private Job job;


	public JobThread(SchedRequest schedRequest) {
		this.schedRequest = schedRequest;
	}


	/**
	 * 1、根据jobName获取job实例 2、调用job实例，设置jobResult
	 */
	public void run() {
		try {
			// 如果是bean，从容器中获取实例；否则认为是java类，通过反射创建实例
			String jobName = schedRequest.getJobName();
			if (jobName.startsWith("bean:")) {
				this.job = (Job) ApplicationContextHelper.getBean(jobName.substring(5).trim());
			} else {
				Class<?> clazz = Class.forName(jobName.trim());
				this.job = (Job) clazz.newInstance();
			}

			this.job.execute();

			jobResult = SchedService.SUCCESS;
		} catch (Exception e) {
			jobResult = e.getClass().getSimpleName() + ":" + e.getMessage();
			logger.error("作业线程执行异常：", e);
		}
		finished = true;
	}

	/**
	 * 作业线程是否结束了
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * job的执行结果
	 */
	public String getJobResult() {
		return jobResult;
	}

	/**
	 * 获取作业执行的状态
	 */
	public String getJobState() {
		if (job != null) {
			return job.getState();
		}
		return null;
	}
}
