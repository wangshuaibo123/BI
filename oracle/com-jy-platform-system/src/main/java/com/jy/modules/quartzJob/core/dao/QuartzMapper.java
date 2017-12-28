package com.jy.modules.quartzJob.core.dao;

import java.util.List;
import java.util.Map;

import com.jy.platform.core.mybatis.MyBatisRepository;
@MyBatisRepository("com.jy.modules.quartzJob.core.dao.QuartzMapper")
public interface QuartzMapper {

    public int findAllQuartzCount(Map<String, Object> param);

    /**
     * @title: getQuartzByPage
     * @author cxt
     * @description:
     * @date 2014年9月3日 下午1:35:45
     * @param param
     * @return
     * @throws
     */
    public List<Map<String,Object>> getQuartzByPage(Map<String, Object> param);
    
    /**
     * @title: findQuartzCronByName
     * @author cxt
     * @description:
     * @date 2014年9月3日 下午1:35:45
     * @param param
     * @return
     * @throws
     */
    public String findQuartzCronByName(Map<String, Object> param);
    
    /**
     * @title: findQuartzJobByName
     * @author cxt
     * @description:
     * @date 2014年9月3日 下午1:35:45
     * @param param
     * @return
     * @throws
     */
    public Map findQuartzJobByName(Map<String, Object> param);
    /**
	 * 回写 任务日志 信息	
	 * @param keyId
	 * @param taskState 任务执行状态 1：成功，0：失败
	 * @param exceptionInfo 
	 */
	public void updateStepLog(Map<String, Object> param);
	
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
	public Long insertStepLog(Map<String, Object> param);
	/**
	 * 查询 有多少组 待执行的任务 定义信息
	 * @return
	 */
	public List<Map<String,Object>> findQuartzTaskGroupDef(Map<String, Object> param);
/**
 * 按组 实例化 分组任务定义表 信息 至实例表
 * @param param
 */
	public void initGroupInstanceInfo(Map<String, Object> param);
	/**
	 * 按组 实例化 分组任务定义表 信息 至实例表
	 * @param param
	 */
	public void initGroupInstanceInfoOne(Map<String, Object> param);
/**
 * 按 批次号 查询 每一批次 的任务步骤 未执行的信息
 * @param param
 * @return
 */
	public List<Map<String, Object>> queryQuartzTaskGroupInstancList(
			Map<String, Object> param);
/**
 * 查询 任务实例表中 未执行完的 批次信息
 * @param param
 * @return
 */
	public List<Map<String, Object>> findQuartzInstanceBatchInfo(Map<String, Object> param);
/**
 * 通过主键ID 更新task_group_instance 的 is_end 字段信息
 * @param taskInstanceId
 */
public void updateTaskInstanceIsEnd(String taskInstanceId);
	
}
