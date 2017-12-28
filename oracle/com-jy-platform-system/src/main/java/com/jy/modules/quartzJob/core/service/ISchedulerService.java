package com.jy.modules.quartzJob.core.service;


public interface ISchedulerService {
	
	/**
	 * @author cxt
	 * 根据名称和组别暂停Tigger
	 * @param triggerName
	 * @param group
	 */
	void pauseTrigger(String triggerName,String group);
	
	/**
	 * @author cxt
	 * 恢复Trigger
	 * @param triggerName
	 * @param group
	 */
	void resumeTrigger(String triggerName,String group) ;
	
	/**
	 * @author cxt	
	 * 删除Trigger
	 * @param triggerName
	 * @param group
	 */
	boolean removeTrigdger(String triggerName,String group) throws Exception;
	/**
	 * @author cxt
	 * 添加新的job 实例
	 * @param jobName
	 * @param jobGroupName
	 * @param CronExp con 表达式
	 * @param clz
	 * @return
	 * @throws Exception
	 */
	public String addNewJob(String jobName,String jobGroupName,String CronExp,String clz) throws Exception;
	
	
	/**
	 * @author cxt
	 * 修改复杂的定时任务
	 * @param triName
	 * @param triGroupName
	 * @param jobName  xml文件自定义的jobName 和 triName 可能不同
	 * @param CronExp
	 * @throws Exception
	 */
	public boolean updateJob(String triName,String triGroupName,String jobName,String cronExp) throws Exception;
	/**
	 * 
	 * @param jobName job/trigger name
	 * @param jobGroupName job/tigger group name 可以为 null
	 * @param startTime 任务开始时间  格式：yyyy-MM-dd HH:mm:ss
	 * @param endTime   任务结束时间 可以为 null
	 * @param repeatCount 执行次数
	 * @param repeatInterval 执行间隔 
	 * @param clz  任务执行类  该类需要实现 job接口
	 * @throws Exception 
	 */
	public void addSimpleTrigger(String jobName,String jobGroupName,String startTime,String endTime,
			String repeatCount,String repeatInterval,String clz) throws Exception;
}
