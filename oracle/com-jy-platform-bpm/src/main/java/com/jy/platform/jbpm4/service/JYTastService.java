package com.jy.platform.jbpm4.service;

import java.util.List;
import java.util.Map;

import com.jy.platform.jbpm4.dto.Jbpm4BizOptionInfoDTO;

/**
 * 
 * @description:定义 工作流 对外部系统公布的 接口放
 * @author chen_gng
 * @date:2014年10月29日上午10:37:46
 */
public interface JYTastService {
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
	 * 结合业务中间表 发起流程
	 * @param proKey 流程定义key 必选
	 * @param variables 流程变量信息 必选
	 * @param bizTabName 业务主表信息（用于业务表 于 工作流 关联） 必选
	 * @param bizInfId 业务主表的主键id 必选
	 * @param bizInfNo 业务主表的业务编号 可选
	 * @param bizType	业务类型  必选
	 * @param bizInfName 任务名称 可选
	 * @param startProUserid 流程发起人 id 必选
	 * @param orgId	流程发起者 的归属机构 必选
	 * @param remark 任务 业务或其他信息的描述  可选
	 * @return
	 * @throws Exception
	 */
	public String startProIns(String proKey,Map<String, Object> variables,
			String bizTabName,String bizInfId,String bizInfNo,String bizType,String bizInfName,
			String startProUserid,String orgId,
			String remark) throws Exception ;
	/**
	 * 通过 jbpm4_biz_tab id 自动执行 第一个待办任务
	 * @param bizTabId 主键ID
	 * @param autoTurnDic 自动执行的流程流转 方向
	 * @param variables 流程业务变量信息
	 * @throws Exception
	 */
	public void autoCompleteFirstTask(String bizTabId,String autoTurnDic,Map<String, Object> variables) throws Exception;
	/**
	 * 处理待办任务
	 * @param taskId 任务ID
	 * @param turnDir 任务流程流程方法
	 * @param variables 流程、业务变量信息
	 * @throws Exception
	 */
	public void completeTask(String taskId, String turnDir,Map<String, Object> variables) throws Exception ;
	/**
	 * 获取 下一节点 待办任务(task) 的参与者规则配置 信息
	 * @param taskId 待办任务ID
	 * @param turnDir 流程走向
	 * @param proInsMap 流程实例 变量参数信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getPartnerRuleInfo(String taskId,String turnDir, Map<String, Object> proInsMap) throws Exception ;
	/**
	 * 对外部系统 公布的选人规则 及选人结果 接口
	 * 返回 json 格式
	 * @param taskId
	 * @param turnDir
	 * @param proInsMap
	 * @return
	 * @throws Exception
	 */
	public String getPartnerRuleInfoAndPartnerId(String taskId,String turnDir, Map<String, Object> proInsMap) throws Exception ;
	
	/**
	 *
	 * 获取下一个活动节点配置的角色信息
	 * 如果为规则自动选择参与者并且参与者类型为角色，则获取配置的角色组编码
	 * 其他情况跟getPartnerRuleInfoAndPartnerId接口逻辑一致
	 *
	 * @param taskId
	 * @param turnDir
	 * @param proInsMap
	 * @return
	 * @throws Exception
	 */
	public String getPartnerRole(String taskId, String turnDir,Map<String, Object> proInsMap) throws Exception;
	
	/**
	 * 
	 * @param proDefKey 流程 编码（定义 key）
	 * @param proDefVersion 流程版本号
	 * @param acitityName 任务节点名称
	 * @param proMap 其他参数信息 供选人接口使用
	 * @return
	 * @throws Exception
	 */
	public String getPartnerInfoByProDef(String proDefKey,String proDefVersion, String acitityName,Map<String,Object> proMap)throws Exception ;
	
	/**
	 * 根据业务主键、业务表名、业务类型、节点名称 获取该节点执行人
		* @title: getPartnerIdByProDef
		* @author
		* @description:
		* @date 2015年3月4日 下午2:47:37
		* @param bizInfId
		* @param bizTabName
		* @param bizType
		* @param acitityName
		* @param variables
		* @return
		* @throws Exception
		* @throws
	 */
	public String getPartnerIdByBizInfo(String bizInfId,String bizTabName,String bizType,String acitityName,Map<String, Object> variables) throws Exception ;
	
	/**
	 * 新增 节点意见描述信息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long insertBizOptionInf(Jbpm4BizOptionInfoDTO dto) throws Exception ;
	
	/**
	 * 修改 节点意见描述信息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public void updateBizOptionInf(Jbpm4BizOptionInfoDTO dto) throws Exception ;
	/**
	 * 删除 节点意见描述信息
	 * @param dto
	 * @throws Exception
	 */
	public void deleteBizOptionInfByKey(Jbpm4BizOptionInfoDTO dto,String ids) throws Exception ;
	
	/**
	 * 删除 节点意见描述信息
	 * @param dto
	 * @throws Exception
	 */
	public void deleteOldSomeBizOptionInf(Map<String,String> param) throws Exception ;
	/**
	 * 获取 流程实例的状态
	 * @param proInsMap
	 * @return
	 */
	public String getProInsState(String bizTabId);
	/**
	 * 通过 userId，任务ID 判断userID
	 * 是否可以　执行该待办任务
	 * @param varMap
	 * @return
	 */
	public String getOperateTaskStateInfo(String taskId, String curUserId);
	/**
	 * 
	 * @param bizInfId 业务主键ID
	 * @param bizTabName 业务表名称
	 * @param actName 待 查询的历史节点 名称
	 * @return
	 */
	public String getHisUserOfActive(String bizInfId, String bizTabName,String actName);
	
   /**
     * 
     * @param bizInfId 业务主键ID
     * @param bizType 业务类型 
     * @param actName 待 查询的历史节点 名称
     * @return
     */
    public String getHisUserOfActiveByBizType(String bizInfId, String bizType,String actName);
    /**
     * 
     * @param bizInfId 业务主键ID
     * @param bizType 业务类型 
     * @param actName 待 查询的历史节点 名称
     * @param isLike 是否按 节点名称 like查询
     * @return
     */
    public String getLastHisUserOfActiveByBizType(String bizInfId, String bizType,String actName,boolean actNameLike);
	
	/**
     * @param proInstId 流程实例ID
     * @param actName 待 查询的历史节点 名称
     * @return
     */
    public String getHisUserOfActiveByProInstId(String proInstId,String actName);
	
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
	 * 通过任务ID ，参数key 获取流程实例中存放的参数value
	 * @param taskId 必须
	 * @param var 必须
	 * @return
	 */
	public Object getProInsVariablesByTaskId(String taskId,String var);
	/**
	 * 通过任务ID ，参数key 获取流程实例中存放的参数value
	 * @param taskId 必须 待办任务ID
	 * @param var 必须
	 * @return
	 */
	public Object getProInsVariablesByTaskId(String taskId);
	/**
	 * 获取流程实例中存放的参数value
	 * @param proInsId 流程实例ID
	 * @return
	 */
	public Object getProInsVariablesByProInsId(String proInsId);
	/**
	 * 获取流程实例中存放的参数value
	 * @param proInsId 流程实例ID
	 * @param var 必须
	 * @return
	 */
	public Object getProInsVariablesByProInsId(String proInsId,String var);
	/**
	 * 创建 流程实例的历史变量信息
	 * @param executionId
	 * @param name
	 * @param value
	 */
	public void createHisVariable(String executionId, String name, Object value);
	/**
	 * 创建 流程实例的历史变量信息
	 * @param executionId
	 * @param name
	 * @param value
	 */
	public void createHisVariables(String executionId, Map<String, ?> variables);
	/**
	 * 获取 流程实例的历史变量信息
	 * @param processInstnceId
	 * @param name
	 * @return
	 */
	public Object getHisVariable(String processInstnceId, String name);
	/**
	 * 获取 流程实例的历史变量信息
	 * @param processInstnceId
	 * @param name
	 * @return
	 */
	public Map<String, Object> getHisVariables(String processInstnceId);
	
	/**
	 * 自动完成任务接口，后台扫描任务调用该接口完成任务
	 * 此接口暂时不支持会签流程
	 * @param bizInfId 业务主键
	 * @param bizTabName 业务表名
	 * @param bizType 业务类型
	 * @param turnDir
	 * @param variables 流程变量
	 */
	public void autoCompleteTask(String bizInfId,String bizTabName,String bizType,String turnDir,Map<String, Object> variables)  throws Exception;
	
	/**
	 * @throws Exception 
     * 查询任务接口
     * @title: queryAllTask
     * @author
     * @description:
     * @date 2015年1月20日 下午5:36:34
     * @param Map
     *       key:processState 任务状态（ PROCESS_TASK：待办任务   COMPLETED_TASK：已办任务）必传项
     *           userId 用户
     *           acitityName 活动名称
     *           processInsId 流程实例
     *           bizType 业务类型
     *           myExtSQL 自定义工作流关联sql
     *           eqActName 为：Y时，流程节点名称 按等于查询 
     *           customSQL:按业务自定义排序
     *           busInfoName:任务名称
     *           startTime：到达开始时间
     *           endTime：到达结束时间
     *           sortName：排序字段
     *           sortType：排序方式
     *        
     * @return
     * @throws
     */
	public List queryAllTask(Map<String, Object> param) throws Exception;
	
    /**
     * 根据业务类型和业务主键查询历史活动列表
     * @param bizInfId 业务主键 必填
     * @param bizType  业务类型  必填
     * @return
     * @throws
     */
	public List<Map<String, Object>> queryHisActInsts(String bizInfId,String bizType) throws Exception;
	
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
	 * 更新待办任务的 业务任务状态信息
	 * @param id
	 * @param bizTaskType
	 * @throws Exception
	 */
	public void updateBizTaskTypeById(String id,String bizTaskType) throws Exception;
	
	/**
	 * 获取节点配置的角色
	 * @param proDefKey 流程定义KEY
	 * @param proDefVersion 流程定义版本
	 * @param acitityName 节点名称
	 * @param proMap
	 * @return 角色ID
	 * @throws Exception
	 */
	public String getPartnerRoleByProDef(String proDefKey,String proDefVersion, String acitityName,Map<String,Object> proMap) throws Exception ;
	
	/**
	 * 获取节点配置的角色
	 * @param proInstId 流程实例ID	
	 * @param acitityName 节点名称
	 * @param proMap 流程变量
	 * @return 角色ID
	 * @throws Exception
	 */
	public String getPartnerRoleByProInst(String proInstId,String acitityName,Map<String,Object> proMap) throws Exception ;
	
	/**
	 * 根据formId获取参与者配置信息
	 * @param formId
	 * @return
	 * @throws Exception
	 */
	public String getPartnerRuleInfoByFormId(String formId) throws Exception;
	
	/**
	 * 完成跳转任务，by xyz
	 * @param taskId
	 * @param turnDir
	 * @param targetTaskName
	 * @param variables
	 * @throws Exception
	 */
	public void completeJumpTask(String taskId, String turnDir,String targetTaskName,Map<String, Object> variables) throws Exception ;
	/**
	 * 获取历史任务名称
	 * @param processInstanceId
	 * @return  xyz
	 */
	public List<String> getHistTaskName(String processInstanceId);
}
