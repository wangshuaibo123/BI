package com.jy.modules.quartzJob.qrtzextlog;

import org.quartz.Job;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.jy.modules.quartzJob.qrtzextlog.service.QrtzExtLogService;

/**
 * 自定义JobFactory
 * @author zhangyu
 *
 */
public class CustomJobFactory extends SpringBeanJobFactory {
	
	private static final Logger logger = LoggerFactory
			.getLogger(CustomJobFactory.class);

	/* 用来记录日志的Service */
	private QrtzExtLogService logService;

	/**
	 * 创建Job实例的代理，把logService传递给它
	 */
	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle)
			throws Exception {
		String triggerName = bundle.getTrigger().getKey().getName();
		boolean writeLog = logService.getQrtzTriggersWriteLogByTriggerName(triggerName);
		if (!writeLog) {
			return super.createJobInstance(bundle);
		} else {
			logger.info("CustomJobFactory为{}创建日志代理", bundle.getJobDetail().getJobClass().getName());
			Object jobInstance = super.createJobInstance(bundle);
			JobLogDelegate delegate = new JobLogDelegate((Job)jobInstance, logService);
	        return delegate;
		}
	}

	public void setLogService(QrtzExtLogService logService) {
		this.logService = logService;
	}

}
