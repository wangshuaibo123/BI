package com.jy.modules.quartzJob.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.modules.quartzJob.core.dao.QuartzMapper;
import com.jy.modules.quartzJob.core.service.ISchedulerService;

@Service("com.jy.modules.quartzJob.core.service.impl.JYQuartzService")
public class JYQuartzService {


    @Autowired
    @Qualifier("com.jy.modules.quartzJob.core.dao.QuartzMapper")
    private QuartzMapper quartzMapper;
    @Autowired
    @Qualifier("com.jy.modules.quartzJob.core.service.impl.SchedulerServiceImpl")
    private ISchedulerService scheduler;
    /**
     * @title: getQuartzByPage
     * @author 分页查询用户
     * @description:
     * @date 2014年9月3日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
    public List<Map<String,Object>> getQuartzByPage(Map<String, Object> param) {
    	
        //Object systemIdObj = param.get("systemId");
        List<Map<String,Object>> result = quartzMapper.getQuartzByPage(param);
        if(result != null && result.size() > 0){
			long val = 0;
			for(int i = 0;i < result.size(); i ++){
				Map<String,Object> map = (Map<String,Object>) result.get(i);
				
				val = MapUtils.getLongValue(map, "NEXT_FIRE_TIME");
				if (val > 0) {
					map.put("NEXT_FIRE_TIME", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
				}

				val = MapUtils.getLongValue(map, "PREV_FIRE_TIME");
				if (val > 0) {
					map.put("PREV_FIRE_TIME", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
				}

				val = MapUtils.getLongValue(map, "START_TIME");
				if (val > 0) {
					map.put("START_TIME", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
				}
				
				val = MapUtils.getLongValue(map, "END_TIME");
				if (val > 0) {
					map.put("END_TIME", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
				}
			}
		}
        
        return result;


    }

    /**
     * @title: findAllQuartzCount
     * @author cxt
     * @description:
     * @date 2014年9月3日 下午2:00:24
     * @param param
     * @return
     * @throws
     */
    public int findAllQuartzCount(Map<String, Object> param) {


        return quartzMapper.findAllQuartzCount(param);
        
    }
    
    /**
     * @title: findQuartzCronByName
     * @author cxt
     * @description:
     * @date 2014年9月3日 下午2:00:24
     * @param param
     * @return
     * @throws
     */
    public String findQuartzCronByName(Map<String, Object> param) {

    	
        return quartzMapper.findQuartzCronByName(param);
        
    }
    
    /**
     * @title: findQuartzJobByName
     * @author cxt
     * @description:
     * @date 2014年9月3日 下午2:00:24
     * @param param
     * @return
     * @throws
     */
    public Map findQuartzJobByName(Map<String, Object> param) {

    	
        return quartzMapper.findQuartzJobByName(param);
        
    }
/**
 * 新增 任务开始日志信息
 * @param uuid
 * @param groupId
 * @param threadId
 * @param taskId
 * @param beanId
 * @param taskInfo
 * @return
 */
	public Long insertQuartzLog(String threadId,String keyID,String taskInfo) {
		
		Long id = 0L;
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("threadId", threadId);
			param.put("keyID", keyID);
			param.put("taskInfo", taskInfo);
			
			
			quartzMapper.insertStepLog(param);
			id = (Long) param.get("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return id;
	}
/**
 * 回写 任务日志 信息	
 * @param keyId
 * @param taskState 任务执行状态 1：成功，0：失败
 * @param start 任务执行开始时间
 * @param end 任务执行结束时间
 * @param exceptionInfo 
 */
	public void updateQuartzLog(Long keyId,String keyID,String taskState,String start, String end,String exceptionInfo) {
		
		try {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("keyId", keyId);
			param.put("exceptionInfo",exceptionInfo);
			param.put("taskState", taskState);
			param.put("start", start);
			param.put("end", end);
			param.put("keyID", keyID);
			quartzMapper.updateStepLog(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * 查询 有多少组 待执行的任务 定义信息
 * @param param
 * @return
 */
	public List<Map<String,Object>> findQuartzTaskGroupDef(Map<String,Object> param) {
		
		List<Map<String,Object>> resultList = quartzMapper.findQuartzTaskGroupDef(null);
		
		return resultList;
	}

	public void initGroupInstanceInfo(Map<String,Object> param) {
		
		quartzMapper.initGroupInstanceInfo(param);
		
	}
	public void initGroupInstanceInfoOne(Map<String,Object> param) {
		
		quartzMapper.initGroupInstanceInfoOne(param);
		
	}
/**
 * 按 批次号 查询 每一批次 的任务步骤 未执行的信息
 * @param param
 * @return
 */
	public List<Map<String,Object>> queryQuartzTaskGroupInstancList(Map<String, Object> param) {
		List<Map<String,Object>> result = quartzMapper.queryQuartzTaskGroupInstancList(param);
		
		return result;
	}
	/**
	 * 查询 任务实例表中 未执行完的 批次信息
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findQuartzInstanceBatchInfo(Map<String, Object> param) {
		List<Map<String,Object>> result = quartzMapper.findQuartzInstanceBatchInfo(param);
		
		return result;
	}
	/**
	 * @param taskInstanceId quartz_task_group_instance 主键ID
	 * @param jobName job/trigger name
	 * @param jobGroupName job/tigger group name 可以为 null
	 * @param startTime 任务开始时间  格式：yyyy-MM-dd HH:mm:ss
	 * @param endTime   任务结束时间 可以为 null
	 * @param repeatCount 执行次数
	 * @param repeatInterval 执行间隔 
	 * @param clz  任务执行类  该类需要实现 job接口
	 * @throws Exception 
	 */
	@Transactional
	public void addSimpleTrigger(String taskInstanceId,String jobName,String jobGroupName,String startTime,String endTime,
			String repeatCount,String repeatInterval,String clz) throws Exception{
		
		quartzMapper.updateTaskInstanceIsEnd(taskInstanceId);
		scheduler.addSimpleTrigger(jobName, jobGroupName, startTime, endTime, repeatCount, repeatInterval, clz);
	}
}
