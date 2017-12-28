package com.jy.platform.jbpm4.service;

import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;

import com.jy.platform.core.message.DataMsg;
import com.jy.platform.jbpm4.dto.JbpmDTO;
import com.jy.platform.jbpm4.dto.PartnerDTO;
import com.jy.platform.jbpm4.dto.TaskInfo;
import com.jy.platform.jbpm4.temporaryJbpm4Info.dto.TemporaryJbpm4InfoDTO;

/**
 * 增加使用平台分页的接口实现以便提升查询效率
 * @Description: 定义jbpm4.4 接口
 * @author chen
 * @version 1.0, 
 * @date 2013-8-29 下午01:36:01
 */
public interface IJbpmService {
	/**
	 * 查询所有 待办任务
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public List<Map> queryAllTask(Map<String,Object> param) throws Exception ;
	public List<Map> queryAllTaskByPaging(Map<String,Object> param) throws Exception ;
	/**
	 * 查询已经完成的待办任务
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public List queryCompletedTask(Map<String,Object> params) throws Exception;
	public List queryCompletedTaskByPaging(Map<String,Object> params) throws Exception;
	/**
	 * 查询已经办结的 
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String,Object>> queryEndTaskList(Map<String, Object> params) throws Exception;
	public List<Map<String,Object>> queryEndTaskListByPaging(Map<String, Object> params) throws Exception;
	/**
	 * 查询活动的流程 信息 list
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public List queryActiveProcessInfoList(Map<String, Object> params) throws Exception;
	/**
	 * 查询已经办结的流程实例 
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String,Object>> queryEndProInfoList(Map<String, Object> params) throws Exception; 
	public List<Map<String,Object>> queryEndProInfoListByPaging(Map<String, Object> params) throws Exception; 
	/**
	 * 通过xml字符串生成 xml文件 
	 * @param xmlContent
	 * @return 返回xml 文件路径
	 */
	public String productXmlByXmlContent(String xmlContent);
	/**
	 * 获取所有的流程定义信息
	 * @return
	 */
	public List<ProcessDefinition> getAllPdList(JbpmDTO dto,DataMsg pageData);
	/**
	 * 通过流程实例id 及当前活动的节点名称 获取该节点下所有流出 的流转方向
	 * 后续可以扩展到 流程图内所有未执行的 节点
	 * @param processInstanceId
	 * @param activeName
	 * @return
	 * @throws Exception 
	 */
	public String getAllTurenDirectory(String processInstanceId,String activeName) throws Exception;
	public String getAllTurenDirectory(String processInstanceId,String currentActiveName,String isJump,String isBack) throws Exception;
	/**
	 * 通过流程定义id 节点名称获取 该节点的向下流转方向
	 * @param proDefId
	 * @param activeName
	 * @return
	 */
	public String getTurenDirectory(String proDefId,String activeName);
	
	/**
	 * 通过流程实例id 及当前活动的节点名称 获取 流进 该节点下所有 的流转方向
	 * 后续可以扩展到 流程图内所有已经执行的 节点
	 * @param processInstanceId
	 * @param activeName
	 * @return
	 */
	public String getIncomingTransitions(String processInstanceId,String activeName);
	/**
	 * 获取流程实例的 所有活动的节点
	 * 没有子流程的流程即为主流程 
	 * @param mainProcessInstanceId
	 * @param subProcessInstanceId
	 * @return
	 */
	public String getAllActiveProcNodeName(String mainProcessInstanceId,String subProcessInstanceId);
	
	/**
	 * 通过流程定义 id 获取 该流程图中的task 节点信息
	 * @param processDefinitionId
	 * @return
	 */
	public String getProcNodeName(String processDefinitionId);
	
	
	/**
	 * 获取流程实例的 所有完成的节点
	 * 没有子流程的流程即为主流程 
	 * @param mainProcessInstanceId
	 * @param subProcessInstanceId
	 * @return
	 */
	public String getAllFinishedProcNodeName(String mainProcessInstanceId,String subProcessInstanceId);
	

	/**
	 * 获取流程实例的 所有 节点
	 * @param mainProcessInstanceId
	 * @param subProcessInstanceId
	 * @return
	 */
	public String getAllProcNodeName(String processInstanceId);
	/**
	 * 更新流程信息 并马上生效（已经发起的流程实例，重新启动应用后会使用最新的流程）
	 * @return
	 */
	public String updateJbpm4LobTableImmediately(Map<String,Object> paramMap) throws Exception ;
	/**
	 * 处理待办任务
	 * @param taskId
	 * @param turnDir
	 * @param paramMap
	 * @throws Exception
	 */
	public void completeTask(String taskId, String turnDir,Map<String,Object> paramMap) throws Exception ;
	/**
	 * 发布流程
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String deployProcess(TemporaryJbpm4InfoDTO dto,String lastId) throws Exception ;
	/**
	 * 取消发布流程
	 * @param deploymentId
	 * @throws Exception
	 */
	public void cancelPublishProcess(String deploymentId)throws Exception ;
	/**
	 * 发起新的流程实例
	 * @param proKey
	 * @param variables
	 * @return
	 * @throws Exception 
	 */
	public ProcessInstance startProcessInstanceByKey(String proKey,
			Map<String, Object> variables) throws Exception;
	/**
	 * 	//通过 动态绘制 来达到特殊 快速处理 暂时只支持同一个流程内。
	 * @param taskId
	 * @param destName
	 * @param processInstanceId
	 * @param variables
	 * @return
	 */
	public String singleBackWay(String taskId, String destName,
			String processInstanceId, Map<String, Object> variables);
	
	/**
	 * 查询参与者
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public List queryPartnerListInfo(PartnerDTO dto) throws Exception;
	
	/**
	 * 获取主流程名下所有子流程 信息 返回json 数据
	 * @param mainProId
	 * @param isInstance 是否是主流程实例 true:是，false:否（mainProId:主流程定义id）
	 * @return
	 * @throws Exception
	 */
	public String getSubProListInfo(String mainProId,boolean isInstance)   throws Exception ;
	
	/**
	 * 通过 流程编码 获取最新版本的 流程节点信息（task end）
	 * @param proKey
	 * @return
	 * @throws Exception
	 */
	public String getNodesOfProDefi(String proKey) throws Exception ;
	/**
	 * 跨流程处理 
	 * @param taskId 待办任务id
	 * @param curSubProKey 当前子流程实例的 流程编码
	 * @param curDestName 当前子流程实例的 最后一个节点 默认为end
	 * @param destSubProKey 目标子流程的流程编码
	 * @param destSubProName 目标子流程 在主流程中的节点名称
	 * @param destName	目标子流程的 节点
	 * @param processInstanceId 当前的流程实例id
	 * @param mainProIns 当前主流程实例 id
	 * @param variables 其他流程 业务参数信息 
	 * @throws Exception
	 */
	public String toOtherProTask(String taskId, String curSubProKey,
			String curDestName, String destSubProKey,String destSubProName, String destName,
			String processInstanceId, String mainProIns,
			Map<String, Object> variables) throws Exception ;

	/**
	 * 获取流程图的图片信息
	 * @param paraMap
	 * @return
	 */
	public byte[] fetchProPng(Map paraMap);
	/**
	 * 通过流程实例ID (既可以是主流程实例ID，也可以使子流程实例ID)
	 * @param executionId
	 * @param actName 当前活动的节点(task)名称 
	 * @return
	 */
	public Map<String,Object> obtainJbpm4FormByProInsId(String executionId,String actName);
	/**
	 * 查看流程执行 轨迹
	 * bizTabName
	 * bizInfId
	 * processInstantceId 优先使用
	 * @param searchParams
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String,Object>> viewWorkflowHisLog(Map<String, Object> searchParams) throws Exception;
	
	/**
	 * 根据流程定义key 获取活动定义列表
	 * 只返回 类型为 task和custom的活动定义
	 * @param proDefKey 流程定义key
	 * @return 例：[{actName:'风险管理部'},{actName:'门店经理'}]
	 */
	public String getActivityDefines(String proDefKey);
	
	/**
	 * 根据流程实例ID和活动名称获取活动信息
	 * @param proInstId
	 * @param actName
	 * @return 例：{performerId:'1',performerName:'admin',completeTime:'2014-12-10 18:31:40'}
	 */
	public String getActInstByProInstIdAndActName(String proInstId,String actName);
	
	/**
     * 根据流程实例ID查询活动历史列表
     * @param proInstId 流程实例ID
     * @return
     */
    public List<Map<String, Object>> queryHisActInstsByProInstId(String proInstId);
    
	/**
	 * 判断userId 是否请假中
	 * @param userId
	 * @return
	 */
	public boolean  checkLeaveByUserId(String userId);
	/**
	 * 分页查询 历史任务数据 by xyz
	 * @param searchParams
	 * @return
	 */
	public List queryEndHistTaskListByPaging(Map<String, Object> searchParams)throws Exception;
	/**
	 * 查询历史数据 轨迹数据 by xyz
	 * @param searchParams
	 * @return
	 */
	public List<Map<String, Object>> viewWorkflowHisLogForHistData(	Map<String, Object> searchParams) throws Exception;
	/**
	 * 获取图片 by  xyz
	 */
	public byte[] fetchProPngByHist(Map paraMap);
	/**
	 * 查询节点信息 by xyz
	 * @param mainProcessInstanceId
	 * @param subProcessInstanceId
	 * @return
	 */
	public String getAllActiveProcNodeNameByHist(String mainProcessInstanceId,String subProcessInstanceId);
	/**
	 * 查询任务信息
	 * @param processInsId
	 * @param isAll
	 * @return
	 */
	public List<TaskInfo> queryNextTaskInfoList(String processInsId, String isAll);
	/**
	 * 查询任务数据
	 * @throws Exception 
	 */
	public List<TaskInfo> queryNextTaskInfoList(String processInsId, String isAll,String myTurn,String acitityName) throws Exception;
	/**
	 * 获取任务选择框 状态
	 * @param processInsId
	 * @param myTurn
	 * @param acitityName
	 * @return
	 * @throws Exception 
	 */
	public boolean isShowTaskInfo(String processInsId, String myTurn, String acitityName) throws Exception; 
}

