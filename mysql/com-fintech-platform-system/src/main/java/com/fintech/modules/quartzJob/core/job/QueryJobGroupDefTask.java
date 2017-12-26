package com.fintech.modules.quartzJob.core.job;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fintech.modules.quartzJob.core.service.impl.JYQuartzService;

/**
 * 
 * @description:扫描日中 任务定义表 已经发布的 任务组 步骤顺序执行的 信息
 * @author
 * @date:2014年9月29日下午5:22:08
 */
@Component("com.fintech.modules.quartzJob.core.job.QueryJobGroupDefTask")
public class QueryJobGroupDefTask implements Serializable, Job {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(QueryJobGroupDefTask.class);

	private JYQuartzService myQuartzService;

	private static boolean isNext = true;

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
//		if (new Date().getHours() >= 21) {
			if (!isNext)
				return;
			isNext = false;
			try {
				SchedulerContext cont = context.getScheduler().getContext();
				ApplicationContext appCtx = (ApplicationContext) cont
						.get("applicationContextKey");
				myQuartzService = (JYQuartzService) appCtx
						.getBean(JYQuartzService.class);

				List<Map<String, Object>> groupDefList = myQuartzService
						.findQuartzTaskGroupDef(null);

				Calendar ca=Calendar.getInstance(Locale.CHINA);
				System.out.println(ca.toString());
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				String runTime=sdf.format(ca.getTime());
				if (groupDefList != null && groupDefList.size() > 0) {
					for (Map temp : groupDefList) {
						String tempGroupId = (String) temp.get("GROUP_ID");
						String tempGroupName = (String) temp.get("GROUP_NAME");

						Map<String, Object> param = new HashMap<String, Object>();

						param.put("groupId", tempGroupId);
						param.put("groupName", tempGroupName);
						param.put("runTime", runTime);
						myQuartzService.initGroupInstanceInfo(param);
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}finally{
				logger.info("------------QueryJobGroupDefTask-------end-------------");
				isNext = true;
			}
		}
//	}

}
