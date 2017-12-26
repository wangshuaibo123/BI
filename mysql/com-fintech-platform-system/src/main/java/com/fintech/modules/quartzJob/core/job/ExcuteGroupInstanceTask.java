package com.fintech.modules.quartzJob.core.job;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fintech.modules.platform.common.util.PlatformThreadTool;
import com.fintech.modules.quartzJob.core.service.QuartzJobThreadPool;
import com.fintech.modules.quartzJob.core.service.ThreadJobDetail;
import com.fintech.modules.quartzJob.core.service.impl.JYQuartzService;


/**
 * 
 * @description:定时 扫描 任务实例表 待处理的顺序 步骤信息
 * @author
 * @date:2014年9月29日下午5:22:08
 */
@Component("com.fintech.modules.quartzJob.core.job.ExcuteGroupInstanceTask")
public class ExcuteGroupInstanceTask implements Serializable,Job{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ExcuteGroupInstanceTask.class);
    //控制  不允许 多线程 执行 public void execute(JobExecutionContext context) throws JobExecutionException  方法
    private static boolean isNext = true;
    private JYQuartzService myQuartzService;
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(!isNext){
			logger.info("------------ExcuteGroupInstanceTask-------isNext:"+isNext);
			return ;
		}
		isNext = false;
		try {
			SchedulerContext cont = context.getScheduler().getContext();
			ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
			myQuartzService = (JYQuartzService) appCtx.getBean(JYQuartzService.class);
			//查询任务 未执行完的 批次信息
			List<Map<String,Object>> batchList = myQuartzService.findQuartzInstanceBatchInfo(null);
			
			//多线程  
			if(batchList != null && batchList.size() > 0){
				//初始化 线程池
				ThreadPoolExecutor executor = QuartzJobThreadPool.getThreadPool(5,10, 3000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(20000));
				
				//如果线程池队列 中 剩余空间 ＜ 待执行批次的 5倍数据 则此次 不再 向线程池中 追加 任务步骤
				if((20000 - PlatformThreadTool.THREAD_MAP.size()) < batchList.size() * 5) return ;
				
				for(Map<String,Object> temp : batchList){
					String tempGroupId = (String) temp.get("GROUP_ID");
					String tempGroupName = (String) temp.get("GROUP_NAME");
					String batchNo = (String) temp.get("BATCH_NO");
					
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("groupId", tempGroupId);
					param.put("groupName", tempGroupName);
					param.put("batchNo", batchNo);
					//按 批次号 查询 每一批次 的任务步骤 未执行的信息 按照执行步骤（DEAL_STEP） 降序 排序
					List<Map<String,Object>> listInstance = myQuartzService.queryQuartzTaskGroupInstancList(param);
					
					for(int i = 0;i < listInstance.size();i ++){
						Map<String,Object> map = listInstance.get(i);
						String checkRun = (String) map.get("CHECK_RUN");
						String beanID = (String) map.get("BEAN_ID");
						String keyID = String.valueOf((BigDecimal) map.get("ID"));
						String is_Next = (String)map.get("IS_NEXT");
						//当前 分组的同一批次号  的Deal_Step 最小的步骤 是否可以执行
						if("Y".equals(checkRun) && "Y".equals(is_Next)){
							
							if(PlatformThreadTool.THREAD_MAP.containsValue(keyID)){
								//队列中 是否存在 了队列
								//存在则不再 放入队列中 直到 队列中 存放的执行完 
							}else{
								//将 jobKeyId 放入 线程map 中 防止 后续 定时任务再次放入
								PlatformThreadTool.THREAD_MAP.put("jobKeyId:"+keyID,keyID);
								
								ThreadJobDetail jobStep = new ThreadJobDetail(keyID,beanID,context,appCtx);
								//放入 线程池 队列中
								executor.execute(jobStep);
							}
							//每个批次号 的待执行步骤只能有一个
							break;
						}
					}
					
				}
			}
			
		}catch(Exception ex){
			logger.error("---------------ExcuteGroupInstanceTask--error----------"+ex.getMessage());
		}finally{
			logger.info("------------ExcuteGroupInstanceTask-------end-------------");
			isNext= true;
		}
	}

}
