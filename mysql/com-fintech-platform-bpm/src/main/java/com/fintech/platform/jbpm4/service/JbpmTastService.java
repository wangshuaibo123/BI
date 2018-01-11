package com.fintech.platform.jbpm4.service;

import java.util.List;
import java.util.Map;

import com.fintech.platform.core.common.BaseDTO;


/**
 * 
 * @Description: 定义jbpm4.4 任务执行的接口
 * @author
 * @version 1.0, 
 * @date 2013-8-29 下午01:36:01
 */
public interface JbpmTastService {
	public static final String TASK_EMERGENT_STATE_1="1";//一般
	public static final String TASK_EMERGENT_STATE_2="2";//紧急
	
	/**
	 * 批量 转移 待办 至toUserId 名下
	 * @param taskIds	待办任务ID 集合
	 * @param toUserId
	 * @param operateId
	 * @throws Exception
	 */
	public void batchUpdateAssignee(List<String> taskIds,String toUserId,String operateId) throws Exception ;
	
	/**
	 * 通过 taskId 从fromUserId 名下 转移taskId 给toUserid 
	 * @param taskId 任务ID 必传
	 * @param fromUserId taskID的现有归属人
	 * @param toUserId	接受taskID的人员
	 * @param opeateId	当前操作者
	 * @throws Exception
	 */
	public void updateAssignee(String taskId,String fromUserId,String toUserId,String opeateId) throws Exception ;
	/**
	 * 通过流程实例id 主流程实例ID 查询 上一节点处理人的相关信息
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getUpTaskDealPersonInfo(Map<String,Object> param);
	/**
	 * 通过流程实例id 获取 工作流与业务表关联表 的 业务信息 描述
	 * @param mainProInsId
	 * @return
	 */
	public Map<String,Object> obtainBizTabInfo(String mainProInsId);
	/**
	 * 获取 流程节点表单配置信息 及参与者选人规则
	 * @param proDefKey
	 * @param proDefVersion
	 * @param proActName
	 * @return
	 */
	public Map<String,Object> obtainJbpm4FormInfo(String proDefKey,String proDefVersion,String proActName);
	/**
	 * 获取 流程节点表单配置信息 及参与者选人规则
	 * @param formId 主键ID
	 * @return
	 */
	public Map<String, Object> obtainJbpm4FormInfoById(String formId);
	/**
	 * 通过 jbpm4_biz_tab 的ID 查询 其对应的 流程实例下第一个待办任务信息
	 * @param bizTabId
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> getFirstTaskByBizTabId(String bizTabId) throws Exception;
	/**
	 * 通过 流程实例 id 获取 当前 流程实例ID 未执行 的最大待办任务 信息
	 * @param proInsId
	 * @return
	 */
	public Map<String,String> getTaskInfoByProInsId(String proInsId);
	/**
	 * 结合业务中间表 发起流程
	 * @param proKey 流程定义key 必选
	 * @param variables 流程变量信息 必选
	 * @param bizTabName 业务主表信息（用于业务表 于 工作流 关联） 必选
	 * @param bizInfId 业务主表的主键id 必选
	 * @param bizType	业务类型  必选
	 * @param bizInfName 任务名称 可选
	 * @param startProUserid 流程发起人 id 必选
	 * @param orgId	流程发起者 的归属机构 必选
	 * @param remark 任务 业务或其他信息的描述  可选
	 * @return
	 * @throws Exception
	 */
	public String startProIns(String proKey,Map<String, Object> variables,
			String bizTabName,String bizInfId,String bizType,String bizInfName,
			String startProUserid,String orgId,
			String remark) throws Exception ;
	/**
	 * 流程监控 批量恢复、批量挂起 数据
	 * 
	 * @param baseDto
	 * @param ids 数据的主键
	 * @param state  要更新的状态（值）
	 * @throws Exception
	 */
	public void batchUpdateStateByIds(BaseDTO baseDto,String ids,String state) throws Exception;
	/**
	 * 个人任务 解锁、锁定 
	 * @param baseDto
	 * @param ids
	 * @param state
	 * @throws Exception
	 */
	
	public void updateDataLockByPrimaryKey(BaseDTO baseDto,String ids,String state)throws Exception;
	/**
	 * 获取某个人 的待办任务个数
	 * @param userId
	 * @return
	 */
	public int getTaskCunOfPseron(String userId);
	/**
	 * 获取某个人 的待办任务个数 
	 * 及
	 * 最后一个待办任务的分配时间（任务创建时间）
	 * CREATE_TIME,TASK_CUNT
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getTaskCunAndLastTimeOfPerson(String userId);
	/**
	 * 获取某个人 当天的任务(包括待办、已办、已结)个数 
	 * 及
	 * 最后一个任务的分配时间（任务创建时间）
	 * CREATE_TIME,TASK_CUNT
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getTaskCunAndLastTimeOfPersonToday(String userId);
	/**
	 * 获取 某个人名下 的所有待办任务 信息
	 * @param userId
	 * @return
	 */
	public List<String> getTaskInfByUserId(String userId);
	
	/**
	 * 通过 待办任务ID 及流程实例业务变量 参数信息获取 相关 业务配置信息
	 * @param taskId
	 * @param turnDir 流程流转方向
	 * @param proMap 流程及业务变量 信息
	 * @return
	 */
	public String getPartnerId(String taskId,String turnDir,Map<String,Object> proMap);
	/**
	 * 获取 下一个任务节点 的选人规则配置信息
	 * @param taskId
	 * @param turnDir 流程流转方向
	 * @param proMap
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> obtainPartnerRuleOfNextTast(String taskId,String turnDir,Map<String,Object> proMap)throws Exception;
	/**
	 * 异常终止 流程实例
	 * @param proInsId 流程实例ID
	 * @param exceptionRemark 备注
	 * @return
	 * @throws Exception
	 */
	public void exceptionStoProIns(String proInsId,String exceptionRemark)throws Exception;
	/**
	 * 异常终止 流程实例
	 * @param bizInfId 业务表主键ID 必传
	 * @param bizTabName 业务表名称 必传
	 * @param bizType 业务类型 必传
	 * @param exceptionRemark 备注 必传
	 * @return
	 * @throws Exception
	 */
	public void exceptionStoProIns(String bizInfId,String bizTabName,String bizType,String exceptionRemark)throws Exception;
	/**
	 * 通过 任务ID 获取任务信息
	 * @param taskId
	 * @return
	 */
	public Map<String, Object> getTaskInfByTaskId(String taskId);
	/**
	 * 获取 流程节点的历史 处理人（最后一个人）
	 * @param bizInfId 业务主键ID
	 * @param bizTabName 业务表名称
	 * @param actName 待 查询的历史节点 名称
	 * @return
	 */
	public String getHisUserOfActive(String bizInfId, String bizTabName,String actName) ;
	
	   /**
     * 获取 流程节点的历史 处理人（最后一个人）
     * @param bizInfId 业务主键ID
     * @param bizType 业务类型
     * @param actName 待 查询的历史节点 名称
     * @return
     */
    public String getHisUserOfActiveByBizType(String bizInfId, String bizType,String actName) ;
	
	/**
     * @param proInstId 流程实例ID
     * @param actName 待 查询的历史节点 名称
     * @return
     */
    public String getHisUserOfActiveByProInstId(String proInstId,String actName);
    
    /**
     * 根据 业务主键ID bizInfId和业务类型 bizType查询当前任务信息
     *  暂时不考虑会签
     * @title: getCurTaskInfo
     * @author
     * @description:
     * @date 2015年2月11日 上午9:18:17
     * @param bizInfId 业务主键ID
     * @param bizType 业务类型
     * @return actName:活动名称  performerId:执行人ID performerName:执行人名称
     * @throws
     */
    public Map<String, Object> getCurTaskInfo(String bizInfId,String bizType);
    /**
     * 通过任务ID更新待办任务的 紧急程度
     * @param taskId
     * @param emergentState
     * @throws Exception
     */
    public void updateJbpm4BizTabTaskEmergentState(Map<String,Object> param,String emergentState)throws Exception;
    /**
     * 
     * @param bizInfId 业务主键ID
     * @param bizType 业务类型 
     * @param actName 待 查询的历史节点 名称
     * @param isLike 是否按 节点名称 like查询
     * @return
     */
	public String getLastHisUserOfActiveByBizType(String bizInfId,
			String bizType, String actName, boolean actNameLike);
	
}

