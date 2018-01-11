package com.fintech.platform.jbpm4.service.impl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.el.Expression;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysauth.SysRoleAPI;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.common.JYLoggerUtil;
import com.fintech.platform.jbpm4.dto.JYPartnerDTO;
import com.fintech.platform.jbpm4.dto.Jbpm4BizTabDTO;
import com.fintech.platform.jbpm4.dto.JbpmDTO;
import com.fintech.platform.jbpm4.jbpm4FormInfo.service.IJbpm4FormInfoService;
import com.fintech.platform.jbpm4.repository.JbpmMapper;
import com.fintech.platform.jbpm4.service.IJbpmService;
import com.fintech.platform.jbpm4.service.JYSelectPartnerService;
import com.fintech.platform.jbpm4.service.JbpmTastService;
import com.fintech.platform.jbpm4.tool.ConstantJBPM;
import com.fintech.platform.jbpm4.tool.MyJBPMTool;
import com.fintech.platform.jbpm4.tool.ParseXmlTool;
/**
 * 
 * @Description 定义 工作流 任务执行的 service 实现类
 * @author
 * @date 2014年9月24日 下午3:13:31
 * @version V1.0
 */
@Service("com.fintech.platform.jbpm4.service.impl.JbpmTaskServceImpl")
@SuppressWarnings("all")
public class JbpmTaskServceImpl implements JbpmTastService,Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger =  LoggerFactory.getLogger(JbpmTaskServceImpl.class);
	@Autowired
	private JbpmMapper mapper;
	@Autowired
	@Qualifier("MyJBPMTool")
	private MyJBPMTool myJBPMTool;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.Jbpm4BizTabService")
	private Jbpm4BizTabService bizService;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.jbpm4FormInfo.service.impl.Jbpm4FormInfoServiceImpl")
	private IJbpm4FormInfoService formService;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.JbpmServiceImpl")
	private IJbpmService jbpmService;
    @Autowired
    private OrgAPI orgApi;
	@Autowired
	private SysRoleAPI sysRoleAPI;
	
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JbpmTastService#batchUpdateAssignee(java.util.List, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public void batchUpdateAssignee(List<String> taskIds, String toUserId,
			String operateId) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		
		List<Map<String,Object>> taskList = new ArrayList<Map<String,Object>>();
		
		if(taskIds != null && taskIds.size() > 0){
			for(String taskId : taskIds){
				Map<String, Object> temp = new HashMap<String, Object>();
				// 任务Id
				temp.put("taskId", taskId);
				//接受转移任务的人员
				temp.put("toUserId", toUserId);
				//当前系统操作员
				temp.put("operateId", operateId);
				
				taskList.add(temp);
			}
		}
		
		param.put("taskList", taskList);
		mapper.batchUpdateAssignee(param);
		if(taskList == null || taskList.size() ==0){
			return ;
		}
		
		for(Map<String,Object> temp:taskList){
			mapper.batchUpdateAssignee(temp);
			mapper.updatejbpm4Task(temp);
			mapper.updatejbpm4HistTask(temp);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JbpmTastService#updateAssignee(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateAssignee(String taskId, String fromUserId,
			String toUserId, String operateId) throws Exception {
		List<String> taskIds = new ArrayList<String>();
		taskIds.add(taskId);
		//调用 批量的 进行处理
		this.batchUpdateAssignee(taskIds , toUserId, operateId);
	}
/*
 * (non-Javadoc)
 * @see com.fintech.platform.jbpm4.service.JbpmTastService#getUpTaskDealPersonInfo(java.util.Map)
 */
	public Map<String, Object> getUpTaskDealPersonInfo(Map<String, Object> param){
		List dataList = mapper.getUpTaskDealPersonInfo(param);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		if(dataList != null && dataList.size() >0){
			//暂时考虑上一节点只有一个人。
			//会签时 需要 再做特殊处理
			resultMap = (Map<String, Object>) dataList.get(0);
		}
		return resultMap;
	}
/*
 * (non-Javadoc)
 * @see com.fintech.platform.jbpm4.service.JbpmTastService#obtainBizTabInfo(java.lang.String)
 */
	public Map<String, Object> obtainBizTabInfo(String mainProInsId) {
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("mainProInsId", mainProInsId);
		Map<String, Object> resultMap = mapper.obtainBizTabInfo(param);
		
		return resultMap;
	}
/*
 * (non-Javadoc)
 * @see com.fintech.platform.jbpm4.service.JbpmTastService#obtainJbpm4FormInfo(java.lang.String, java.lang.String, java.lang.String)
 */
	public Map<String, Object> obtainJbpm4FormInfo(String proDefKey,String proDefVersion, String proActName) {
		if(StringUtils.isEmpty(proDefKey) 
			|| StringUtils.isEmpty(proDefVersion) 
			|| StringUtils.isEmpty(proActName))
			return null;
		
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("proDefKey", proDefKey);
		param.put("proDefVersion", proDefVersion);
		param.put("proActName", proActName);
		Map<String, Object> resultMap = mapper.obtainJbpm4FormInfo(param);
		
		return resultMap;
	}
	public Map<String, Object> obtainJbpm4FormInfoById(String formId) {
		if(StringUtils.isEmpty(formId)) return null;
		
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("formId", formId);
		Map<String, Object> resultMap = mapper.obtainJbpm4FormInfoById(param);
		
		return resultMap;
	}
	
	@Transactional(rollbackFor= Exception.class)
	public String startProIns(String proKey, Map<String, Object> variables,
			String bizTabName, String bizInfId, String bizType,String bizInfName,
			 String startProUserid, String orgId, String remark)
			throws Exception {
		ProcessInstance proIns = myJBPMTool.startProcessInstanceByKey(proKey, variables);
		
		//向工作流与业务表结合 的中间表 jbpm4_biz_tab 插入一条数据
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String proInsId = proIns.getId();
		Jbpm4BizTabDTO dto = new Jbpm4BizTabDTO();
		dto.setProInstanceId(proInsId);
		dto.setBizInfName(bizInfName);
		dto.setBizTabName(bizTabName);
		dto.setBizInfId(bizInfId);
		dto.setBizType(bizType);
		dto.setRemark(remark);
		dto.setStartProUserid(startProUserid);
		dto.setOwnerId(startProUserid);
		dto.setOrgId(orgId);
		Long keyId = bizService.insertJbpm4BizTab(dto);
		
		return keyId.toString();
	}
	/**
	 * 更新 待办任务表 的字段
	 * 2014-10-31 14:16:05 chj
	 * @param baseDto
	 * @param ids
	 * @throws Exception
	 */
	public void updateDataLockByPrimaryKey(BaseDTO baseDto,String ids,String state)throws Exception{
		if(StringUtils.isEmpty(ids)) throw new Exception("操作失败！传人的参数主键为null");
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dto", baseDto);
		param.put("ids", ids);
		param.put("state", state);
		mapper.updateDataLockByPrimaryKey(param);
		
	}
	
	/**
	 * 2014-11-3 14:27:08 chj
	 * 流程监控 我的代办 批量挂起、批量恢复 更新字段
	 * @param baseDto
	 * @param ids
	 * @param state
	 * @throws Exception
	 */
	public void batchUpdateStateByIds(BaseDTO baseDto,String ids,String state) throws Exception{
		if(StringUtils.isEmpty(ids)) throw new Exception("操作失败！传入的参数主键为null");
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dto", baseDto);
		param.put("ids", ids);
		param.put("state", state);
		mapper.batchUpdateStateByIds(param);
	}
/*
 * (non-Javadoc)
 * @see com.fintech.platform.jbpm4.service.JbpmTastService#getFirstTaskByBizTabId(java.lang.String)
 */
	public Map<String, Object> getFirstTaskByBizTabId(String bizTabId) throws Exception {
		//Map<String, Object> param = new HashMap<String,Object>();
		//param.put("bizTabId", bizTabId);
		//Map<String,Object> jbpmBizTab = mapper.getFirstTaskByBizTabId(param);
		//(String) jbpmBizTab.get("PRO_INSTANCE_ID");
		Jbpm4BizTabDTO bizDto = bizService.queryJbpm4BizTabByPrimaryKey(bizTabId);
		String proInsId  = bizDto.getProInstanceId();
		TaskQuery query = myJBPMTool.getTaskService().createTaskQuery().processInstanceId(proInsId);
		String taskId = null;
		if(query != null){
			List<Task> list = query.list();
			if(list != null){
				Task firstTask = list.get(0);
				taskId = firstTask.getId();
			}
		}
		Map<String,Object> resultMap = null;
		if(StringUtils.isNotEmpty(taskId)){
			resultMap = new HashMap<String,Object>();
			resultMap.put("TASKID", taskId);
		}
		
		return resultMap;
	}

/*
 * 	(non-Javadoc)
 * @see com.fintech.platform.jbpm4.service.JbpmTastService#getTaskCunOfPseron(java.lang.String)
 */
	public int getTaskCunOfPseron(String userId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("userId", userId);
		Map<String,Object> resultMap = mapper.getTaskCunOfPseron(paraMap);
		String taskCunt = (String) resultMap.get("TASK_CUNT");
		return Integer.parseInt(taskCunt);
	}
	@Override
	public List<String> getTaskInfByUserId(String userId) {
		List<String> taskIds = new ArrayList<String>();
		JbpmDTO dto = new JbpmDTO();
		dto.setParamUserId(userId);
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("dto", dto);
		
		//查询出待办任务的基本信息列表
		List<Map> dataList = mapper.queryTasksByMySql(param);
		if(dataList != null && dataList.size() >0){
			for(Map tem: dataList){
				String taskId = String.valueOf((Long)tem.get("TASKID"));
				
				if(StringUtils.isNotEmpty(taskId))
				taskIds.add(taskId);
			}
		}
		
		return taskIds;
	}
/*
 * (non-Javadoc)
 * @see com.fintech.platform.jbpm4.service.JbpmTastService#getPartnerId(java.lang.String, java.util.Map)
 */
	@Transactional(readOnly=true)
	public String getPartnerId(String taskId, String turnDic,Map<String, Object> proMap){
		String partner = "-1";
		String proTaskId = String.valueOf(proMap.get("proTaskId"));//从流程变量中 获取当前的 任务的taskId 
		
		String formId = String.valueOf(proMap.get("FORM_ID"));//从业务变量中的Form_id
		//todo 查看参与者 配置信息 并进行反射执行
		Map<String, Object> formMap = null;
		try {
			if(StringUtils.isNotEmpty(formId)){
				formMap = this.obtainJbpm4FormInfoById(formId);
			}else{
				//如果有 判断决策节点 则调用该方法
				formMap = this.obtainPartnerRuleOfNextTast(proTaskId, turnDic, proMap);
			}
		} catch (Exception e) {
			formMap = null;
			logger.error(" 执行 getPartnerId 失败。",e);
		}
		if(formMap != null){
			//todo 获取该流程实例ID 下该节点的历史参与者信息 如果有值 则返回历史 参与者，
			//否则继续
			//判断是否是会签
			if(turnDic != null && turnDic.indexOf("会签")>0){
				//值为1是会签 
				proMap.put(ConstantJBPM.IS_COUNTERSIGN, "1");
			}
			partner = this.getPartnerIdByParaMap(formMap,proMap);
		}
		
		return partner;
	}
	/**
	 * 通过formMap 和proMap 获取或分配一个 具体的参与者ID
	 * @param formMap 在节点上的表单，选人规则配置信息
	 * @param proMap 流程实例 相关参数变量信息
	 * @return 具体的参与者ID
	 * @throws Exception 
	 */
	private String getPartnerIdByParaMap(Map<String, Object> formMap,Map<String, Object> proMap) {
		JYLoggerUtil.debug(this.getClass(), "getPartnerIdByParaMap.formMap:"+formMap);
		JYLoggerUtil.debug(this.getClass(), "getPartnerIdByParaMap.proMap:"+proMap);
		String partner = "-1";//默认为-1 
		
		String proTaskId = String.valueOf(proMap.get("proTaskId"));//从流程变量中 获取当前的 任务的taskId 
		String proProcessInsId = (String)proMap.get("proProcessInsId");//从流程变量中 获取当前的 流程实例ID
		String proAcitityName = String.valueOf(proMap.get("proAcitityName"));//从流程变量中 获取当前的 活动节点名称
		String proBizTabId = String.valueOf(proMap.get("proBizTabId"));//从流程变量中 获取当前的 jbpm4_biz_tab 的主键ID
		String proBizTabName = String.valueOf(proMap.get("proBizTabName"));//从流程变量中 获取当前的 业务表 名称
		String proBizInfId = String.valueOf(proMap.get("proBizInfId"));//从流程变量中 获取当前的 业务表对应的主键ID
		String formId = String.valueOf(proMap.get("FORM_ID"));//从业务变量中的Form_id
		
		formId= (String) formMap.get("FORM_ID");
		String partnerType = (String) formMap.get("PARTICIPANT_TYPE");
		String ruleInfo = (String) formMap.get("PARTICIPANT_RULE");
		String partType = (String) formMap.get("PART_TYPE");
		String otherParams	 = (String)formMap.get("OTHER_PARAMS");
		String nextActName = (String)formMap.get("PRO_ACTIVITY_NAME");//配置 选人规则的节点名称
		proMap.put("proNextActName", nextActName); //传递 配置规则的 节点名称 nextActName
		//如果流程实例ID 不为null  则获取该流程实例内的流程变量信息 放入 varMap ;
		Map<String,Object> varMap =  new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(proProcessInsId) ){
			Set<String> set = myJBPMTool.getExecutionService().getVariableNames(proProcessInsId);  
	        Map<String ,Object> dbMap = myJBPMTool.getExecutionService().getVariables(proProcessInsId, set);  
	        
			varMap.putAll(dbMap);
		}
		varMap.putAll(proMap);
		//todo 获取该流程实例ID 下该节点的历史参与者信息 需要配置 类JYObtainHisPartnerImpl
		//系统选择参与者 可以参考追加 实现 JYObtainHisPartnerImpl 的方法实现
		//具体参考 ObatinIntoPartnerImpl
		//自定义选择参与者 、按规则选择参与者、人工选择参与者、系统选择参与者
		try {
			ApplicationContext context = null;
			try{
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				ServletContext servletContext = webApplicationContext.getServletContext();
				context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			}catch(Exception e){
				context = SysConfigAPI.getApplicationContext();
			}
			if(ConstantJBPM.PARTNER_TYPE_1.equals(partnerType)){//系统选择参与者
			    JYSelectPartnerService selectPartner = (JYSelectPartnerService) context.getBean(ruleInfo);
			    //传递 配置规则的 节点名称 nextActName
			    JYPartnerDTO partnerDTO = selectPartner.selectPartnerBySysRule(proBizInfId, proBizTabName, proBizTabId, proProcessInsId, proAcitityName, varMap);
			    
			    partner = partnerDTO.getParUserId();
			}else if(ConstantJBPM.PARTNER_TYPE_2.equals(partnerType)){//按规则选择参与者
			    JYSelectPartnerService selectPartner = (JYSelectPartnerService) context.getBean(ruleInfo);
			    JYPartnerDTO partnerDTO = selectPartner.selectPartnerByDroolsRule(proBizInfId, proBizTabName, proBizTabId, proProcessInsId, proAcitityName, varMap);
			    
			    partner = partnerDTO.getParUserId();
			}else if(ConstantJBPM.PARTNER_TYPE_5.equals(partnerType)){//按规则自动 选择参与者
				JYSelectPartnerService selectPartner = null;
				JYPartnerDTO partnerDTO = null;
				//如果配置 按规则自动选择参与者 则优先调用 
				if(StringUtils.isNotEmpty(ruleInfo)){
					//获取该流程实例ID 下该节点的历史参与者信息 如果有值 则返回历史 参与者，
					//否则继续
					selectPartner = (JYSelectPartnerService) context.getBean(ruleInfo);
					//传递 配置规则的 节点名称
					partnerDTO = selectPartner.selectPartnerBySysRule(proBizInfId, proBizTabName, proBizTabId, proProcessInsId, proAcitityName, varMap);
				}
				//如果返回非 -1 或是null 或是 没有参与者 则调用其配置信息
				if(partnerDTO == null || "-1".equals(partnerDTO.getParUserId()) || "".equals(partnerDTO.getParUserId())){
					selectPartner = (JYSelectPartnerService) context.getBean(ConstantJBPM.PARTNER_TYPE_5_BEAN_ID);
					proMap.put("roleCode", otherParams);
					proMap.put("partType", partType);
					
					varMap.putAll(proMap);
					partnerDTO = selectPartner.selectPartnerBySysRule(proBizInfId, proBizTabName, proBizTabId, proProcessInsId, proAcitityName, varMap);
				}
			    
			    partner = partnerDTO.getParUserId();
			}
		} catch (BeansException e) {
			JYLoggerUtil.error(this.getClass(),"执行 getPartnerIdByParaMap 方法 BeansException异常",e);
		} catch (Exception e) {
			JYLoggerUtil.error(this.getClass(),"执行 getPartnerIdByParaMap 方法 Exception 异常",e);
		}
		
		return partner;
	}

	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JbpmTastService#obtainPartnerRuleOfNextTast(java.lang.String, java.lang.String, java.util.Map)
	 */
	public Map<String, Object> obtainPartnerRuleOfNextTast(String taskId,String turnDir, Map<String, Object> proMap) throws Exception {
		
		String proTaskId = String.valueOf(proMap.get("proTaskId"));//从流程变量中 获取当前的 任务的taskId 
		String proProcessInsId = String.valueOf(proMap.get("proProcessInsId"));//从流程变量中 获取当前的 流程实例ID
		String proAcitityName = String.valueOf(proMap.get("proAcitityName"));//从流程变量中 获取当前的 活动节点名称
		String proBizTabId = String.valueOf(proMap.get("proBizTabId"));//从流程变量中 获取当前的 jbpm4_biz_tab 的主键ID
		String proBizTabName = String.valueOf(proMap.get("proBizTabName"));//从流程变量中 获取当前的 业务表 名称
		String proBizInfId = String.valueOf(proMap.get("proBizInfId"));//从流程变量中 获取当前的 业务表对应的主键ID
		
		//通过流程实例获取流程定义 信息
		Map<String, Object> proDefMap = new HashMap<String,Object>();
		proDefMap.put("proInsId", proProcessInsId);
		
		Map<String,Object> defMap = mapper.queryProDefindInfo(proDefMap);
		String deploymentId = String.valueOf(defMap.get("DEPLOYMENT"));
		String resourceName = String.valueOf(defMap.get("RESOURCE_NAME"));//"uploadProcessXml/201402263403.jpdl.xml";//processDe.getName();
		String proDefId = String.valueOf(defMap.get("PRO_DEF_ID"));
		//获取流程定义
		ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(proDefId).uniqueResult();

		Map<String,Object> formAct = this.getTaskActivityName(deploymentId, resourceName, turnDir, proAcitityName, proMap, processDefinition);
		//获取 task 节点 名称
		String actName = (String) formAct.get("actName");
		//获取jbpm4_form_info 配置信息 （Partner Rule ,选人规则) 
		Map<String, Object> formMap = null;
		//获取子流程KEY
		String subProcesskey = (String)formAct.get("subProcesskey");
		//获取子流程版本
		String subProcessVersion = (String)formAct.get("subProcessVersion");
		//获取父流程KEY
		String parentProcesskey = (String)formAct.get("parentProcesskey");
		//获取父流程版本
		String parentProcessVersion = (String)formAct.get("parentProcessVersion");
		//如果是子流程节点
		if(null!=subProcesskey && !"".equals(subProcesskey)){
			formMap = this.obtainJbpm4FormInfo(subProcesskey,subProcessVersion,actName);
		}else if(null!=parentProcesskey && !"".equals(parentProcesskey)){//父活动下一个节点
			formMap = this.obtainJbpm4FormInfo(parentProcesskey,parentProcessVersion,actName);
		}else{
			formMap = jbpmService.obtainJbpm4FormByProInsId(proProcessInsId,actName);
		}
		return formMap;
	}
	/**
	 * 查询 任务 task 节点  信息
	 * @param deploymentId 流程 部署ID
	 * @param resourceName 流程资源名称
	 * @param actName	节点 名称
	 * @param proMap 流程实例 变量信息
	 * @param processDefinition 该流程定义信息
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> getTaskActivityName(String deploymentId,String resourceName,String turnDir,String actName,Map<String,Object> proMap,ProcessDefinition processDefinition) throws Exception{
		Map<String, Object> activityMap = new HashMap<String,Object>(); 
		ActivityImpl sourceActivity = ((ProcessDefinitionImpl) processDefinition).findActivity(actName);
		ActivityImpl destAct = null;
		//获取从该节点出去的 流转方向
		List<Transition> list = (List<Transition>) sourceActivity.getOutgoingTransitions();
		for(Transition temp:list){
			TransitionImpl tempImpl = (TransitionImpl)temp;
			String transName = tempImpl.getName();
			if(transName == null) transName="";
			if(turnDir.equals(transName)){//比对 路径方向 与 流程流转方向
				destAct = tempImpl.getDestination();
				break;
			}
		}
		String destActType = destAct.getType();
		String desctActName = destAct.getName();
		if("task".equals(destActType)){//目标节点是task （任务）节点
			activityMap.put("actName", desctActName);
			//todo 完善 考虑主子流程
			return activityMap;
		}else if("decision".equals(destActType)){
			//下一节点是 决策 则调用 决策信息 进行判断
			return getTaskActivityOfDecision(deploymentId, resourceName, desctActName, proMap, processDefinition);
		}else if("end".equals(destActType)){
			//下一节点是 end 节点时 ,判断是否有父流程，有父流程需要找到父流程的下一个节点
			getNextActivityOfParent(activityMap,proMap,processDefinition);
		}else if("sub-process".equals(destActType)){//子流程			
			return getActivityOfSubProcess(deploymentId,resourceName,desctActName,proMap,processDefinition);
		}
		
		return activityMap;
	}
	
	/**
	 * 查询子流程的第一个节点
	 * 如果子流程的第一个节点还是子流程，继续递归查询
	 * @param deploymentId
	 * @param resourceName
	 * @param desctActName
	 * @param proMap
	 * @param processDefinition
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> getActivityOfSubProcess(String deploymentId,String resourceName,String desctActName,Map<String,Object> proMap,ProcessDefinition processDefinition) throws Exception{
		Map<String, Object> activityMap = new HashMap<String,Object>(); 
		InputStream is = myJBPMTool.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
		
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        DocumentBuilder db=dbf.newDocumentBuilder();
        Document doc=db.parse(is);
		
		Element root = doc.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("sub-process");
		String subProKey = "";
		//如果有 子流程的话 会进行该循环
		for(int i = 0;i < nodeList.getLength(); i ++){
			Element myElemnet = (Element)nodeList.item(i);
			String subProName = XmlUtil.attribute(myElemnet , "name");// sub-process-key
			//找到当前子流程节点
			if(desctActName.equals(subProName)){
				subProKey = XmlUtil.attribute(myElemnet , "sub-process-key");// sub-process-key
				break;
			}
		}
		if(!"".equals(subProKey)){
			ProcessDefinition subProDef = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(subProKey).uniqueResult();
			ProcessDefinitionImpl subProDefImpl = (ProcessDefinitionImpl)subProDef;
			List<? extends Activity> actList =  subProDefImpl.getActivities();
			for(Activity act:actList){		
				//从开始节点找第一个节点
				if(act.getType().equals("start")){
					List<? extends Transition> transList = act.getOutgoingTransitions();
					Transition transition = transList.get(0);
					Activity firstActivity = transition.getDestination();
					//如果第一个节点是子流程，继续递归
					if(firstActivity.getType().equals("sub-process")){
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("proKey", subProKey);
						Map resultMap = mapper.queryJbpm4LobInfoByProkey(param );						
						resourceName  = (String) resultMap.get("NAME");
						deploymentId = (String) resultMap.get("DEPLOYMENT");
						return getActivityOfSubProcess(deploymentId,resourceName,firstActivity.getName(),proMap,subProDef);
						
					}else{
						activityMap.put("actName", firstActivity.getName());
						activityMap.put("subProcesskey", subProDef.getKey());
						activityMap.put("subProcessVersion", subProDef.getVersion());
					}
					break;
				}
			}
		}else{
			logger.error("子流程不存在，子流程节点："+desctActName);
		}
		return activityMap;
	}
	
	/**
	 * 获取父活动下一个节点信息
	 * @param actMap
	 * @param proMap
	 * @param processDefinition
	 * @throws Exception
	 */
	private void getNextActivityOfParent(Map<String,Object> actMap,Map<String,Object> proMap,ProcessDefinition processDefinition) throws Exception{
		String proProcessInsId = String.valueOf(proMap.get("proProcessInsId"));//从流程变量中 获取当前的 流程实例ID
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("proInstId", proProcessInsId);
		//查询父流程
		String mainId = mapper.getParentProcess(param);
		//有父流程
		if(null!=mainId && !"".equals(mainId)){
			//通过流程实例获取流程定义 信息
			Map<String, Object> proDefMap = new HashMap<String,Object>();
			proDefMap.put("proInsId", mainId);
			
			Map<String,Object> defMap = mapper.queryProDefindInfo(proDefMap);
			String deploymentId = String.valueOf(defMap.get("DEPLOYMENT"));
			String resourceName = String.valueOf(defMap.get("RESOURCE_NAME"));//"uploadProcessXml/201402263403.jpdl.xml";//processDe.getName();
			String proDefId = String.valueOf(defMap.get("PRO_DEF_ID"));
			InputStream is = myJBPMTool.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
			
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
	        DocumentBuilder db=dbf.newDocumentBuilder();
	        Document doc=db.parse(is);
			
			Element root = doc.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("sub-process");
			String parentActName = "";
			//如果有 子流程的话 会进行该循环
			for(int i = 0;i < nodeList.getLength(); i ++){
				Element myElemnet = (Element)nodeList.item(i);
				String subProKey =  XmlUtil.attribute(myElemnet , "sub-process-key");// sub-process-key
				//判断是否是该子流程的父活动
				if(processDefinition.getKey().equals(subProKey)){
					parentActName = XmlUtil.attribute(myElemnet , "name");// sub-process-key		
				}
			}
			//如果有父活动
			if(null!=parentActName && !"".equals(parentActName)){
				ProcessDefinitionImpl parentProcessDef = (ProcessDefinitionImpl)myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(proDefId).uniqueResult();
				ActivityImpl actImpl = parentProcessDef.getActivity(parentActName);
				List<? extends Transition> outTrans = actImpl.getOutgoingTransitions();
				//父活动的转出路径名通过 outcome 传递
				String outcome = (String)proMap.get("outcome");
				if(null==outcome){
					outcome = "";
				}
				for(Transition outTran : outTrans){
					String tranName = outTran.getName();
					if(null==tranName){
						tranName = "";
					}
					//如果转出路径一致 或者 只有一条转出路径
					if(outcome.equals(tranName) || outTrans.size()==1){
						Activity destAct = outTran.getDestination();
						String nextActName = destAct.getName();
						actMap.put("actName", nextActName);
						actMap.put("parentProcesskey", parentProcessDef.getKey());
						actMap.put("parentProcessVersion", parentProcessDef.getVersion());
						break;
					}
			    }
			}
		}	
	}
	
	
	/**
	 * 查询 决策节点 按 业务规则 执行后 下一个待办任务 task 节点 信息
	 * @param deploymentId 流程 部署ID
	 * @param resourceName 流程资源名称
	 * @param actName	节点 名称
	 * @param proMap 流程实例 变量信息
	 * @param processDefinition 该流程定义信息
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> getTaskActivityOfDecision(String deploymentId,String resourceName,String actName,Map<String,Object> proMap,ProcessDefinition processDefinition) throws Exception{
		Map<String, Object> activityMap = new HashMap<String,Object>(); 
		
		String proProcessInsId = String.valueOf(proMap.get("proProcessInsId"));//从流程变量中 获取当前的 流程实例ID
		//获取流程实例
		ProcessInstance proIns  =  myJBPMTool.getExecutionService().findProcessInstanceById(proProcessInsId);
		ExecutionImpl execution = (ExecutionImpl) proIns;
		execution.setVariables(proMap);
		//获取流程定义 xml文件信息
		InputStream is = myJBPMTool.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        DocumentBuilder db=dbf.newDocumentBuilder();
        Document doc= db.parse(is);
		Element root = doc.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("decision");
		
		List<Element> decisionElements = ParseXmlTool.elementsMethod(root, "decision");
		String turnDir = "";//决策节点 判断需要走的路径名称
		//获取 决策 信息 并进行模拟执行 判断
		for (int i=0; i< decisionElements.size(); i++) {
			Element decisionElement = decisionElements.get(i);
			String name = XmlUtil.attribute(decisionElement , "name");
			if(actName.equals(name)){
				String expr = XmlUtil.attribute(decisionElement , "expr");// <decision g='303,491,48,48' name='判断复议专员授权额度' expr='#{dddd}'>
				List<Element> handlerElements = ParseXmlTool.elementsMethod(decisionElement, "handler");//<handler class='com.fintech.modules.befloan.jbpm.decision.CheckAuditAmountDec'/> 
				//List<Element> conditionElements = ParseXmlTool.elementsMethod(decisionElement, "condition");//<condition expr='#{sss}'/> 
				//List<Element> transitionElements = ParseXmlTool.elementsMethod(decisionElement, "transition");//<transition name='在授权额度内' to='myEnd1'/>
				if(StringUtils.isNotEmpty(expr)){
					Expression expression = Expression.create(expr, null);
					Object result = expression.evaluate(execution);
					turnDir = (String) result;
				}
				if(handlerElements != null && handlerElements.size() >0){//取 handler
					Element handlerE = handlerElements.get(0);//class
					String className = handlerE.getAttribute("class");
					if(StringUtils.isNotEmpty(className)){
						ApplicationContext context = null;
						try{
							WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
							ServletContext servletContext = webApplicationContext.getServletContext();
							context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
						}catch(Exception e){
							context = SysConfigAPI.getApplicationContext();
						}
						//获取 执行 决策类型
					    DecisionHandler dh = (DecisionHandler) context.getBean(className);
					    turnDir = dh.decide(execution);
					}
				}
				if(StringUtils.isEmpty(expr) && (handlerElements == null || handlerElements.size() ==0)){
					//获取 decision 下所有 路径信息//取 conditionElements
					List<Element> transitionElements = ParseXmlTool.elementsMethod(decisionElement, "transition");
					for (int k=0; k< transitionElements.size(); k++) {
						boolean isNext = true;
						Element tranElement = transitionElements.get(k);
						String tranName = XmlUtil.attribute(tranElement , "name");
						//获取 transition 下所有 condition 信息  每个路径下只有一个 
						List<Element> tranCondElements = ParseXmlTool.elementsMethod(tranElement, "condition");
						String condExpr = XmlUtil.attribute(tranCondElements.get(0) , "expr");
						if(StringUtils.isNotEmpty(condExpr)){
							Expression expression = Expression.create(condExpr, null);
							Object result = expression.evaluate(execution);
							if (result instanceof Boolean)  isNext = ((Boolean) result).booleanValue();
						}
						
						if(isNext){
							turnDir = tranName;
							break;
						}
					}
				}
				
				//判断完成 决策 节点 后 进行 下一节点名称 的返回
				if(turnDir == null) turnDir = "";
				
				return getTaskActivityName(deploymentId,resourceName,turnDir,actName,proMap,processDefinition);
			}
		}
		
		
		return activityMap;
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JbpmTastService#getTaskIdByProInsId(java.lang.String)
	 */
	public Map<String, String> getTaskInfoByProInsId(String proInsId) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("proInsId", proInsId);
		//如果是会签 流程 的话 可能是多个 ，暂时 只考虑 一个
		List<Map<String,String>> resList = mapper.getTaskInfoByProInsId(param);
		Map<String,String> lastTaskInfo = resList.get(0);
		
		return lastTaskInfo;
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JbpmTastService#exceptionStoProIns(java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public void exceptionStoProIns(String proInsId, String exceptionRemark)
			throws Exception {
		//获取流程实例 信息
		ProcessInstance pi = myJBPMTool.getExecutionService().findProcessInstanceById(proInsId);
		
		myJBPMTool.getExecutionService().endProcessInstance(pi.getId(), exceptionRemark);
		
	}

	@Override
	public Map<String, Object> getTaskInfByTaskId(String taskId) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("taskId", taskId);
		Map<String,Object> taskInfo = mapper.getTaskInfByTaskId(param);
		
		return taskInfo;
	}

	@Override
	public String getHisUserOfActive(String bizInfId, String bizTabName,String actName) {
		String userId = "-1";//待分配
		Map<String,String> param = new HashMap<String,String>();
		param.put("bizInfId", bizInfId);
		param.put("bizTabName", bizTabName);
		param.put("actName", actName);
		List<Map<String,Object>> hisTaskList = mapper.getHisTaskInf(param);
		
		if(hisTaskList != null && hisTaskList.size() >0){
			Map<String,Object> taskInfo = hisTaskList.get(0);
			userId = (String) taskInfo.get("HIS_USER_ID");
			if(StringUtils.isEmpty(userId)){
				userId = "-1";//待分配
			}
		}
		if(StringUtils.isEmpty(userId)){
			userId = "-1";
		}
		
		return userId;
	}
	
    /**
     * 
     * @param bizInfId 业务主键ID
     * @param bizType 业务类型 
     * @param actName 待 查询的历史节点 名称
     * @return
     */
    public String getHisUserOfActiveByBizType(String bizInfId, String bizType,String actName){
        String userId = "-1";//待分配
        Map<String,String> param = new HashMap<String,String>();
        param.put("bizInfId", bizInfId);
        String[] arrBizType = bizType.split(",");
        StringBuffer bufBizType = new StringBuffer();
        bufBizType.append("(");
        for(int i=0;i<arrBizType.length;i++){
        	bufBizType.append("'").append(arrBizType[i]).append("'");
        	if(i!=(arrBizType.length-1)){
        		bufBizType.append(",");
        	}
        }
        bufBizType.append(")");
        param.put("bizType", bufBizType.toString());
        param.put("actName", actName);
        List<Map<String,Object>> hisTaskList = mapper.getHisTaskInfByBizType(param);
        
        if(hisTaskList != null && hisTaskList.size() >0){
            Map<String,Object> taskInfo = hisTaskList.get(0);
            userId = (String) taskInfo.get("HIS_USER_ID");
            if(StringUtils.isEmpty(userId)){
                userId = "-1";//待分配
            }
        }
        if(StringUtils.isEmpty(userId)){
            userId = "-1";
        }
        
        return userId;
    }
	
	/**
     * @param proInstId 流程实例ID
     * @param actName 待 查询的历史节点 名称
     * 参数为空时 返回-1
     * @return
     */
    public String getHisUserOfActiveByProInstId(String proInstId,String actName){
        String userId = "-1";//待分配
        if(StringUtils.isEmpty(proInstId)||StringUtils.isEmpty(actName)
        		||"null".equals(proInstId)||"null".equals(actName)){
        	return userId;
        }
        Map<String,String> param = new HashMap<String,String>();
        param.put("proInstId", proInstId);   
        param.put("actName", actName);
        List<Map<String,Object>> hisTaskList = mapper.getHisTaskInfByProInstId(param);
        if(hisTaskList != null && hisTaskList.size() >0){
            Map<String,Object> taskInfo = hisTaskList.get(0);
            userId = (String) taskInfo.get("HIS_USER_ID");
            if(StringUtils.isEmpty(userId)){
                userId = "-1";//待分配
            }
        }
        if(StringUtils.isEmpty(userId)){
            userId = "-1";
        }
        
        return userId;
    }

	@Override
	public Map<String, Object> getTaskCunAndLastTimeOfPerson(String userId) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("userId", userId);
		Map<String,Object> resultMap = mapper.getTaskCunOfPseron(paraMap);
		String taskCunt = (String) resultMap.get("TASK_CUNT");
		return resultMap;
	}
	
	/**
	 * 获取某个人 当天的任务(包括待办、已办、已结)个数 
	 * 及
	 * 最后一个任务的分配时间（任务创建时间）
	 * CREATE_TIME,TASK_CUNT
	 * @param userId
	 * @return
	 */
	public Map<String,Object> getTaskCunAndLastTimeOfPersonToday(String userId){
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("userId", userId);
		Map<String,Object> resultMap = mapper.getTaskCunOfPseronToday(paraMap);
		return resultMap;
	}

	@Transactional(rollbackFor=Exception.class)
	public void exceptionStoProIns(String bizInfId, String bizTabName,
			String bizType, String exceptionRemark) throws Exception {
		Map<String,Object> searchParams = new HashMap<String,Object>();
		Jbpm4BizTabDTO dto = new Jbpm4BizTabDTO();
		dto.setBizInfId(bizInfId);
		dto.setBizTabName(bizTabName);
		dto.setBizType(bizType);
		dto.setValidateState("1");
		searchParams.put("dto", dto);
		
		List<Jbpm4BizTabDTO> dataList = bizService.searchJbpm4BizTab(searchParams);
		
		if(dataList != null && dataList.size() == 1){
			Jbpm4BizTabDTO bizTabDTO = dataList.get(0);
			String proInsId = bizTabDTO.getProInstanceId();
			this.exceptionStoProIns(proInsId, exceptionRemark);
		}else{
			JYLoggerUtil.error(this.getClass(), "exceptionStoProIns 终止流程失败！！");
			throw new Exception("终止流程失败！！");
		}
		
	}

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
    public Map<String, Object> getCurTaskInfo(String bizInfId,String bizType){
        Map<String,Object> returnMap = new HashMap<String, Object>();
        Map<String,String> param = new HashMap<String,String>();
        param.put("bizInfId", bizInfId);
        param.put("bizType", bizType);
        List<Map<String,Object>> list = mapper.getCurTaskInfo(param);
        if(null!=list && list.size()>0){
            Map<String,Object> resultMap = list.get(0);
            returnMap.put("actName", resultMap.get("ACTNAME"));
            String performerId = (String)resultMap.get("PERFORMER");
            returnMap.put("performerId",performerId);
            String performerName = "";
            if(null!=performerId && !"".equals(performerId) && !"-1".equals(performerId)){
            	//判断执行人是不是用户ID，如果为用户ID则是数字，如果为角色组编码则不是数字
    			boolean isUserId = true;
    			try{
    				Long.parseLong(performerId);
    			}catch(NumberFormatException e){
    				isUserId = false;
    			}
    			//执行人是用户ID
    			if(isUserId){
    				UserInfo curUser = orgApi.getUserInfoDetail(performerId);
    				if(curUser != null ){
    					performerName = curUser.getUserName();
    				}
    			}else{
    				//查询角色组名称  针对推送到角色而非个人的情况
    				Map<String, Object> roleGroupParam = new HashMap<String, Object>();
    				roleGroupParam.put("roleGroupCode", performerId);
            		List<Map<String, Object>> roleGroupList = sysRoleAPI.queryRoleGroupList(0, 1, roleGroupParam);
    				if(null!=roleGroupList && roleGroupList.size()>0){
    					Map<String,Object> roleInfo = roleGroupList.get(0);
    					performerName = (String)roleInfo.get("roleGroupName");
    				}
    			}
            }
            returnMap.put("performerName",performerName);
        }
        return returnMap;
    }
    /*
     * (non-Javadoc)
     * @see com.fintech.platform.jbpm4.service.JbpmTastService#updateJbpm4BizTabTaskEmergentState(java.lang.String, java.lang.String)
     */
    @Transactional(rollbackFor=Exception.class)
	public void updateJbpm4BizTabTaskEmergentState(Map<String,Object> param,String emergentState) throws Exception {
		//
    	param.put("emergentState", emergentState);
		mapper.updateJbpm4BizTabTaskEmergentState(param);
	}

    /**
     * 
     * @param bizInfId 业务主键ID
     * @param bizType 业务类型 
     * @param actName 待 查询的历史节点 名称
     * @param isLike 是否按 节点名称 like查询
     * @return
     */
	public String getLastHisUserOfActiveByBizType(String bizInfId,
			String bizType, String actName, boolean actNameLike) {
		
		if(!actNameLike) return getHisUserOfActiveByBizType(bizInfId,bizType,actName);
		
		String userId = "-1";//待分配
        Map<String,String> param = new HashMap<String,String>();
        param.put("bizInfId", bizInfId);
        String[] arrBizType = bizType.split(",");
        StringBuffer bufBizType = new StringBuffer();
        bufBizType.append("(");
        for(int i=0;i<arrBizType.length;i++){
        	bufBizType.append("'").append(arrBizType[i]).append("'");
        	if(i!=(arrBizType.length-1)){
        		bufBizType.append(",");
        	}
        }
        bufBizType.append(")");
        param.put("bizType", bufBizType.toString());
        param.put("actName", actName);
    	List<Map<String,Object>> hisTaskList = mapper.getLastHisUserOfActiveByBizType(param);
        
        if(hisTaskList != null && hisTaskList.size() >0){
            Map<String,Object> taskInfo = hisTaskList.get(0);
            userId = (String) taskInfo.get("HIS_USER_ID");
            if(StringUtils.isEmpty(userId)){
                userId = "-1";//待分配
            }
        }
        if(StringUtils.isEmpty(userId)){
            userId = "-1";
        }
        
        return userId;
	}
	
	
}
