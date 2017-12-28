package com.jy.modules.quartzJob.qrtzextlog;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.modules.quartzJob.qrtzextlog.dto.QrtzExtLogDTO;
import com.jy.modules.quartzJob.qrtzextlog.service.QrtzExtLogService;

/**
 * 日志代理类
 * @author zhangyu
 *
 */
public class JobLogDelegate implements Job {
	
	private static final Logger logger = LoggerFactory
			.getLogger(JobLogDelegate.class);

	/**
	 * 包装的Job实例
	 */
	private Job job;

	/**
	 * 日志服务 
	 */
	private QrtzExtLogService logService;
	
	public JobLogDelegate(Job job, QrtzExtLogService logService) {
		this.job = job;
		this.logService = logService;
	}
	
	/**
	 * 1、记录开始执行日志，状态为RUNNING（一个独立的事务）
	 * 2、调用真正的Job
	 * 3-1、成功-更新状态为SUCCESS（一个独立的事务）
	 * 3-2、异常-更新状态为ERROR，并记录异常信息（一个独立的事务）
	 * 
	 * 自己一个事务、不和Job的事务相关，自身出现异常不要影响Job的调用
	 */
	@Override
	public void execute(JobExecutionContext jobCtx) throws JobExecutionException {
		QrtzExtLogDTO logDto = new QrtzExtLogDTO();
		logDto.setJobName(jobCtx.getJobDetail().getKey().getName());
		logDto.setFireTime(new java.sql.Timestamp(jobCtx.getFireTime().getTime()));
		logDto.setThreadId(Thread.currentThread().getName());
		logDto.setStartTime(new java.sql.Timestamp(System.currentTimeMillis()));
		logDto.setState("RUNNING");

		long logID = -1;
		try {
			logger.info("记录定时任务执行日志，插入{}", logDto);
			logID = logService.insertQrtzExtLog(logDto);
			logDto.setId(logID);
		} catch (Exception e) {
			logger.error("记录定时任务执行日志，插入出错", e);
		}

		try {
			job.execute(jobCtx);

			logDto.setState("SUCCESS");
			logDto.setEndTime(new java.sql.Timestamp(System.currentTimeMillis()));
		} catch (Exception ex) {
			logDto.setState("ERROR");
			logDto.setResult(ex.getMessage());
			logDto.setEndTime(new java.sql.Timestamp(System.currentTimeMillis()));
			
			logger.error("定时任务执行异常", ex);
		}
		
		try {
			logger.info("记录定时任务执行日志，更新结果{}", logDto);
			if (logDto.getId() != -1) {
			    int count = logService.updateQrtzExtLogFinish(logDto);
			    if (count == 0) {
			    	throw new RuntimeException("更新定时任务日志状态出错，更新数据0条" + logDto);
			    }
			}
		} catch (Exception e) {
			logger.error("记录定时任务执行日志，更新结果出错", e);
		}

	}

}
