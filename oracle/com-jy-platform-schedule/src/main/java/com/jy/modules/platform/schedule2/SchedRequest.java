package com.jy.modules.platform.schedule2;

import java.io.Serializable;
/**
 * 
 * @author xyz
 * 请求参数对象
 */
public class SchedRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private String jobName; //作业名称，如 bean:com.jy.modules.quartzJob.qrtzextlog.MyDemoJob
	private String bizModule; // 业务系统名称，如 S000
	private String fireTime; // 触发时间 20161115175700
	private boolean writeLog = true; // 是否记录日志，默认记录
	private long stateInterval = 5; // 显示状态的间隔（单位秒）

	@Override
	public String toString() {
		return "SchedRequest [jobName=" + jobName + ", bizModule=" + bizModule + ", fireTime="
				+ fireTime + ", writeLog=" + writeLog + "]";
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getBizModule() {
		return bizModule;
	}

	public void setBizModule(String bizModule) {
		this.bizModule = bizModule;
	}

	public String getFireTime() {
		return fireTime;
	}

	public void setFireTime(String fireTime) {
		this.fireTime = fireTime;
	}

	public boolean isWriteLog() {
		return writeLog;
	}

	public void setWriteLog(boolean writeLog) {
		this.writeLog = writeLog;
	}

	public long getStateInterval() {
		return stateInterval;
	}

	public void setStateInterval(long stateInterval) {
		this.stateInterval = stateInterval;
	}
	
}
