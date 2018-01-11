package com.fintech.platform.jbpm4.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysauth.SysRoleAPI;
import com.fintech.platform.core.common.JYLoggerUtil;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.jbpm4.dto.Jbpm4BizOptionInfoDTO;
import com.fintech.platform.jbpm4.dto.Jbpm4BizTabDTO;
import com.fintech.platform.jbpm4.dto.JbpmDTO;
import com.fintech.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;
import com.fintech.platform.jbpm4.jbpm4FormInfo.service.IJbpm4FormInfoService;
import com.fintech.platform.jbpm4.service.IJbpmService;
import com.fintech.platform.jbpm4.service.JYTastService;
import com.fintech.platform.jbpm4.service.Jbpm4BizOptionInfoService;
import com.fintech.platform.jbpm4.service.JbpmTastService;
import com.fintech.platform.jbpm4.tool.ConstantJBPM;
import com.fintech.platform.jbpm4.tool.MyJBPMTool;

/**
 * 
 * @description: 对外部 系统公布的 接口实现类
 * @author
 * @date:2014年10月29日上午11:30:39
 */
@Service("com.fintech.platform.jbpm4.service.impl.JYTastServiceImpl")
public class JYTastServiceImpl implements JYTastService,Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("MyJBPMTool")
	private MyJBPMTool myJBPMTool;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.Jbpm4BizTabService")
	private Jbpm4BizTabService bizService;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.JbpmTaskServceImpl")
	private JbpmTastService jbpmTaskService;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.Jbpm4BizOptionInfoService")
	private Jbpm4BizOptionInfoService bizOption;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.jbpm4FormInfo.service.impl.Jbpm4FormInfoServiceImpl")
	private IJbpm4FormInfoService	formService;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.JbpmServiceImpl")
	private IJbpmService jbpmServcie;
	
	@Autowired
	private SysRoleAPI sysRoleAPI;
	
    @Autowired
    private SessionAPI sessionAPI;
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#startProIns(java.lang.String, java.util.Map, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor= Exception.class)
	public String startProIns(String proKey, Map<String, Object> variables,
			String bizTabName, String bizInfId, String bizType,String bizInfName,
			 String startProUserid, String orgId, String remark)
			throws Exception {
		ProcessInstance proIns = myJBPMTool.startProcessInstanceByKey(proKey, variables);
		
		//向工作流与业务表结合 的中间表 jbpm4_biz_tab 插入一条数据
		//Map<String,Object> paramMap = new HashMap<String,Object>();
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
		if(variables != null ){
			String tempType = (String) variables.get("bizTaskType");
			
			if(tempType != null && !"".equals(tempType))
				dto.setBizTaskType(tempType);
		}
		
		Long keyId = bizService.insertJbpm4BizTab(dto);
		
		return keyId.toString();
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#startProIns(java.lang.String, java.util.Map, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor= Exception.class)
	public String startProIns(String proKey, Map<String, Object> variables,
			String bizTabName, String bizInfId,String bizInfNo, String bizType,String bizInfName,
			 String startProUserid, String orgId, String remark)
			throws Exception {
		ProcessInstance proIns = myJBPMTool.startProcessInstanceByKey(proKey, variables);

		String proInsId = proIns.getId();
		Jbpm4BizTabDTO dto = new Jbpm4BizTabDTO();
		dto.setProInstanceId(proInsId);
		dto.setBizInfName(bizInfName);
		dto.setBizTabName(bizTabName);
		dto.setBizInfId(bizInfId);
		dto.setBizInfNo(bizInfNo);
		dto.setBizType(bizType);
		dto.setRemark(remark);
		dto.setStartProUserid(startProUserid);
		dto.setOwnerId(startProUserid);
		dto.setOrgId(orgId);
		Long keyId = bizService.insertJbpm4BizTab(dto);
		
		return keyId.toString();
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#autoCompleteFirstTask(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor= Exception.class)
	public void autoCompleteFirstTask(String bizTabId,String autoTurnDic,Map<String, Object> variables) throws Exception {
		Map<String,Object> resultMap = jbpmTaskService.getFirstTaskByBizTabId(bizTabId);
		if(resultMap == null) throw new Exception("自动执行待办任务查询失败！！！");
		String taskId = (String) resultMap.get("TASKID");
		String autoNextPartner = (String) variables.get("autoNextPartner");
		if(StringUtils.isNotEmpty(autoNextPartner)){
			variables.put("owner", autoNextPartner);
		}
		//系统自动执行第一个待办任务
		this.completeTask(taskId, autoTurnDic, variables);
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#completeTask(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor= Exception.class)
	public void completeTask(String taskId, String turnDir,Map<String, Object> variables) throws Exception {
		//判断前台页面是否 指定了 参与者  此方法暂时不支持 会签 完成待办任务
		String owner = (String) variables.get("owner");
		//该业务逻辑移植到 前台页面控制
		/*if(StringUtils.isEmpty(owner) || "-1".equals(owner) || "0".equals(owner)){
			//如果没有则说明是 需要系统 自动指定的 
			owner = jbpmTaskService.getPartnerId(taskId, turnDir,variables);//"1";
			variables.put("owner", owner);
		}*/
		if(StringUtils.isNotEmpty(owner)&& "null".equalsIgnoreCase(owner)){
			variables.put("owner", "-1");//增强数据合法性校验
		}
		if(StringUtils.isNotEmpty(turnDir)){
			//判断是否是会签
			if(turnDir.indexOf("会签")>0){
				int ownerCount = 1;
				if(StringUtils.isNotEmpty(owner)){
					ownerCount = owner.split(",").length;
				}
				variables.put("owners", owner);
				variables.put("ownerCount", ownerCount);
			}
			myJBPMTool.dealWithTask3(taskId, turnDir, variables);
		}else{
			myJBPMTool.dealWithTask2(taskId, variables);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#insertBizOptionInf(com.fintech.platform.jbpm4.dto.Jbpm4BizOptionInfoDTO)
	 */
	public Long insertBizOptionInf(Jbpm4BizOptionInfoDTO dto) throws Exception {
		Long id = bizOption.insertJbpm4BizOptionInfo(dto);
		return id;
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#updateBizOptionInf(com.fintech.platform.jbpm4.dto.Jbpm4BizOptionInfoDTO)
	 */
	public void updateBizOptionInf(Jbpm4BizOptionInfoDTO dto) throws Exception {
		bizOption.updateJbpm4BizOptionInfo(dto);
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#deleteBizOptionInfByKey(com.fintech.platform.jbpm4.dto.Jbpm4BizOptionInfoDTO, java.lang.String)
	 */
	public void deleteBizOptionInfByKey(Jbpm4BizOptionInfoDTO dto,String ids)
			throws Exception {
		bizOption.deleteJbpm4BizOptionInfoByPrimaryKey(dto, ids);
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#getPartnerRuleInfo(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Transactional(readOnly=true)
	public Map<String,String> getPartnerRuleInfo(String taskId, String turnDir,
			Map<String, Object> proInsMap) throws Exception {
		Map<String,String> ruleMap = new HashMap<String,String>();
		
		Map<String,Object> formRuleMap =jbpmTaskService.obtainPartnerRuleOfNextTast(taskId, turnDir, proInsMap);
		String partnerType = ConstantJBPM.PARTNER_TYPE_3;//自定义选择参与者 、按规则选择参与者、人工选择参与者、系统选择参与者
		if(formRuleMap != null){
			partnerType = (String) formRuleMap.get("PARTICIPANT_TYPE");
			String ruleInfo = (String) formRuleMap.get("PARTICIPANT_RULE");
			String otherParams	 = (String)formRuleMap.get("OTHER_PARAMS");
			String partType = (String) formRuleMap.get("PART_TYPE");
			String formId = (String)formRuleMap.get("FORM_ID");
			String actName = (String)formRuleMap.get("PRO_ACTIVITY_NAME");
			
			ruleMap.put("PARTICIPANT_TYPE", partnerType);
			ruleMap.put("PARTICIPANT_RULE", ruleInfo);
			ruleMap.put("OTHER_PARAMS", otherParams);
			ruleMap.put("PART_TYPE", partType);
			ruleMap.put("FORM_ID", formId);
			ruleMap.put("ACT_NAME", actName);
		}
		
		return ruleMap;
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#getPartnerRuleInfoAndPartnerId(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Transactional(readOnly=true)
	public String getPartnerRuleInfoAndPartnerId(String taskId, String turnDir,Map<String, Object> proInsMap) throws Exception {
		Map<String, String> ruleMap = this.getPartnerRuleInfo(taskId, turnDir, proInsMap);

		String reStr = this.makeupRuleJSON(taskId, turnDir, proInsMap, ruleMap);
		
		return reStr;
	}
	
	/**
	 * 组装 返回的节点配置的选人 规则JSON 数据
	 * @param taskId
	 * @param turnDir
	 * @param proInsMap
	 * @param ruleMap
	 * @return
	 * @throws Exception 
	 */
	private String makeupRuleJSON(String taskId, String turnDir, Map<String, Object> proInsMap,Map<String, String> ruleMap) throws Exception{
		String nextPartnerId = "";
		
		String partnerType = ruleMap.get("PARTICIPANT_TYPE");
		String formId = ruleMap.get("FORM_ID");
    	boolean dialogBoolean = false;//是否需要弹出页面 供人工选择参与者
    	if(ConstantJBPM.PARTNER_TYPE_3.equals(partnerType) || ConstantJBPM.PARTNER_TYPE_4.equals(partnerType)){
    		dialogBoolean = true;
    	}else{
    		if(StringUtils.isNotEmpty(formId)){
    			//获取 下一个参与者 信息
        		proInsMap.put("FORM_ID", formId);
        		nextPartnerId = jbpmTaskService.getPartnerId(taskId, turnDir, proInsMap);
    		}else{
    			nextPartnerId = "-1";//没有配置 则放-1 此时 可能是结束节点
    		}
    		
    	}
    	String ruleInfo = ruleMap.get("PARTICIPANT_RULE");
    	String otherParams = ruleMap.get("OTHER_PARAMS");
    	String partType = ruleMap.get("PART_TYPE");
		
    	if(StringUtils.isEmpty(ruleInfo)) ruleInfo="";
    	if(StringUtils.isEmpty(otherParams)) otherParams="";
    	if(StringUtils.isEmpty(partType)) partType="";
    	if(StringUtils.isEmpty(formId)) formId="";
    	if(StringUtils.isEmpty(nextPartnerId)) nextPartnerId="";
    	
    	
		StringBuilder resultMsg = new StringBuilder("[{");
    	resultMsg.append("participantType:'").append(partnerType).append("'");
    	resultMsg.append(",participantRule:'").append(ruleInfo).append("'");
    	resultMsg.append(",otherParams:'").append(otherParams).append("'");
    	resultMsg.append(",nextPartnerId:'").append(nextPartnerId).append("'");
    	resultMsg.append(",partType:'").append(partType).append("'");
    	resultMsg.append(",formId:'").append(formId).append("'");
    	if(StringUtils.isNotEmpty(formId)){//如果formId 有数据
    		Map<String, Object> formMap = jbpmTaskService.obtainJbpm4FormInfoById(formId);
    		resultMsg.append(",proNextActName:'").append(formMap.get("PRO_ACTIVITY_NAME").toString()).append("'");
    	}else{
    		resultMsg.append(",proNextActName:''");
    	}
    	resultMsg.append(",dialogBoolean:'").append(dialogBoolean).append("'");
    	resultMsg.append("}]");
    	
    	return resultMsg.toString();
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#getPartnerInfoByProDef(java.lang.String, java.lang.String, java.lang.String,Map<String,Object>)
	 */
	public String getPartnerInfoByProDef(String proDefKey,String proDefVersion, String acitityName,Map<String,Object> proMap) throws Exception {
		//版本号可以不传，不传则取最新版本
		if(null==proDefVersion || "".equals(proDefVersion)){
			List<ProcessDefinition> proDefList = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(proDefKey).orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).list();
			if(proDefList!=null && proDefList.size()>0){
				ProcessDefinition proDef = proDefList.get(0);
				proDefVersion = proDef.getVersion()+"";
			}
		}
		String formId = formService.queryJbpm4FormInfoIdBysql(proDefKey, proDefVersion, acitityName);
		
		Jbpm4FormInfoDTO dto = new Jbpm4FormInfoDTO();
    	dto.setId(Long.parseLong(formId));
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("dto", dto);
    	Jbpm4FormInfoDTO ruleDto = formService.queryOneJbpm4FormInfo(paramMap);
    	
    	Map<String, String> ruleMap = new HashMap<String,String>();
    	ruleMap.put("FORM_ID", formId);
    	ruleMap.put("PARTICIPANT_TYPE", ruleDto.getParticipatorType());
    	ruleMap.put("PARTICIPANT_RULE", ruleDto.getRuleInfo());
    	ruleMap.put("OTHER_PARAMS", ruleDto.getOtherParams());
    	ruleMap.put("PART_TYPE", ruleDto.getPartType());
    	
    	String str = this.makeupRuleJSON(null, null, proMap, ruleMap);
		
		return str;
	}
	
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
    public String getPartnerIdByBizInfo(String bizInfId,String bizTabName,String bizType,String acitityName,Map<String, Object> variables) throws Exception{
        Map<String,Object> searchParams = new HashMap<String,Object>();
        Jbpm4BizTabDTO bizTabDTO = new Jbpm4BizTabDTO();
        bizTabDTO.setBizInfId(bizInfId);
        bizTabDTO.setBizTabName(bizTabName);
        bizTabDTO.setBizType(bizType);
        bizTabDTO.setValidateState("1");
        searchParams.put("dto", bizTabDTO);
        String proInsId = "";
        String proBizTabId = "";
        //查询流程实例ID
        List<Jbpm4BizTabDTO> dataList = bizService.searchJbpm4BizTab(searchParams);
        if(dataList != null && dataList.size() >0){
            Jbpm4BizTabDTO bizTabDTO1 = dataList.get(0);
            proInsId = bizTabDTO1.getProInstanceId();
            proBizTabId = bizTabDTO1.getId().toString();
        }
        if(StringUtils.isEmpty(proInsId)){
            throw new Exception("流程不存在！");
        }
        ProcessInstance processInstance = myJBPMTool.getExecutionService().findProcessInstanceById(proInsId);
        ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).uniqueResult();
        String nextPartnerId = "";
        String formId = formService.queryJbpm4FormInfoIdBysql(processDefinition.getKey(), processDefinition.getVersion()+"", acitityName);
        if(StringUtils.isEmpty(formId)){
            return "-1";
        }
        Jbpm4FormInfoDTO dto = new Jbpm4FormInfoDTO();
        dto.setId(Long.parseLong(formId));
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dto", dto);
        Jbpm4FormInfoDTO ruleDto = formService.queryOneJbpm4FormInfo(paramMap);
        
        String partnerType = ruleDto.getParticipatorType();
        if(ConstantJBPM.PARTNER_TYPE_3.equals(partnerType) || ConstantJBPM.PARTNER_TYPE_4.equals(partnerType)){
            nextPartnerId = "-1";
        }else{
            Map<String,Object> proInsMap = new HashMap<String,Object>();
            //获取 下一个参与者 信息
            proInsMap.put("FORM_ID", formId);
            if(null!=variables){
                proInsMap.putAll(variables);
            }
            proInsMap.put("proInsId", proInsId);
            proInsMap.put("proBizTabId", proBizTabId);
            JYLoggerUtil.debug(this.getClass(), "getPartnerIdByBizInfo.proInsMap:"+proInsMap);
            nextPartnerId = jbpmTaskService.getPartnerId(null, null, proInsMap);
        }
        return nextPartnerId;
    }

	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#getProInsState(java.util.Map)
	 */
	public String getProInsState(String bizTabId) {
		StringBuilder sb = new StringBuilder("[{");
		Jbpm4BizTabDTO bizTab = null;
		try {
			bizTab = bizService.queryJbpm4BizTabByPrimaryKey(bizTabId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String state = bizTab.getProInstanceState();
		String msg  = "";
		if(state == null || "".equals(state)){
			state = "0";// 挂起状态
			msg = "该任务的流程实例处于挂起状态，暂时不能处理该任务！";
		}
		sb.append("proInsState:'").append(state).append("'");
		sb.append(",msg:'").append(msg).append("'");
		
		sb.append("}]");
		return sb.toString();
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#getOperateTaskStateInfo(java.util.Map)
	 */
	public String getOperateTaskStateInfo(String taskId, String curUserId){
		StringBuilder sb = new StringBuilder("[{");
		String nextFlag = "1";//默认 当前登录 人可以执行该待办任务
		String msg = "";
		Map<String,Object> taskInfo =jbpmTaskService.getTaskInfByTaskId(taskId);
		if(taskInfo == null){
			nextFlag ="0";//不能执行 该待办任务
			msg ="该待办任务不存在或已经执行！";
			
		}else{
			//待办任务存在 
			String taskUserId = (String) taskInfo.get("CUR_USER_ID");
			if(!curUserId.equals(taskUserId)){
				//执行人是否为归属的角色组
				boolean isOwnerRoleGroup = false;
				List<Map<String, Object>> roleGroupList = sysRoleAPI.getRoleGroupByUserId(curUserId);
	        	for(int i=0;i<roleGroupList.size();i++){
	        		Map<String, Object> roleGroup = roleGroupList.get(i);
	        		String roleGroupCode = (String)roleGroup.get("roleGroupCode");
	        		if(roleGroupCode!=null && roleGroupCode.equals(taskUserId)){
	        			isOwnerRoleGroup = true;
	        			break;
	        		}
	        	}
				if(!isOwnerRoleGroup){
					nextFlag ="0";//不能执行 该待办任务
					msg ="该待办任务已经被转移或签收，您无权操作！";
				}
			}
		}
		
		sb.append("nextFlag:'").append(nextFlag).append("'");
		sb.append(",msg:'").append(msg).append("'");
		sb.append("}]");
		return sb.toString();
	}
	@Override
	public String getHisUserOfActive(String bizInfId, String bizTabName,
			String actName) {
		String userId = jbpmTaskService.getHisUserOfActive(bizInfId, bizTabName, actName);
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
        String userId = jbpmTaskService.getHisUserOfActiveByBizType(bizInfId, bizType, actName);
        return userId;
    }
	
	/**
     * @param proInstId 流程实例ID
     * @param actName 待 查询的历史节点 名称
     * @return
     */
    public String getHisUserOfActiveByProInstId(String proInstId,String actName){
        String userId = jbpmTaskService.getHisUserOfActiveByProInstId(proInstId,actName);
        return userId;
    }
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#exceptionStoProIns(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public void exceptionStoProIns(String bizInfId, String bizTabName,
			String bizType, String exceptionRemark) throws Exception {
		jbpmTaskService.exceptionStoProIns(bizInfId, bizTabName, bizType, exceptionRemark);
	}
	@Override
	public Object getProInsVariablesByTaskId(String taskId, String var) {
		String proInsId = myJBPMTool.getTaskService().getTask(taskId).getExecutionId();
		Object obj = this.getProInsVariablesByProInsId(proInsId,var);
		return obj;
	}
	@Override
	public Object getProInsVariablesByTaskId(String taskId) {
		String proInsId = myJBPMTool.getTaskService().getTask(taskId).getExecutionId();
		Object map = this.getProInsVariablesByProInsId(proInsId);
		return map;
	}
	@Override
	public Object getProInsVariablesByProInsId(String proInsId) {
		Set<String> set = myJBPMTool.getExecutionService().getVariableNames(proInsId);  
        Map<String ,Object> map = myJBPMTool.getExecutionService().getVariables(proInsId, set);  
        
		return map;
	}
	@Override
	//@Transactional(propagation=Propagation.REQUIRES_NEW)//另开启一个事务
	public Object getProInsVariablesByProInsId(String proInsId, String var) {
		Object obj = myJBPMTool.getExecutionService().getVariable(proInsId, var);
		return obj;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void createHisVariable(String executionId, String name, Object value) {
		myJBPMTool.getExecutionService().createVariable(executionId, name, value, true);
		
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void createHisVariables(String executionId, Map<String, ?> variables) {
		myJBPMTool.getExecutionService().createVariables(executionId, variables, true);
	}
	@Override
	public Object getHisVariable(String processInstnceId, String name) {
		Object obj =myJBPMTool.getHistoryService().getVariable(processInstnceId, name);
		return obj;
	}
	@Override
	public Map<String, Object> getHisVariables(String processInstnceId) {
		Set<String> set = myJBPMTool.getHistoryService().getVariableNames(processInstnceId);
		Map<String, Object> obj = (Map<String, Object>) myJBPMTool.getHistoryService().getVariables(processInstnceId, set);
		return obj;
	}
	
	/**
	 * 自动完成任务接口，后台扫描任务调用该接口完成任务
	 * @param bizInfId 业务主键
	 * @param bizTabName 业务表名
	 * @param bizType 业务类型
	 * @param turnDir 转向的分支路径
	 * @param variables 流程变量
	 */
	@Transactional(rollbackFor=Exception.class)
	public void autoCompleteTask(String bizInfId,String bizTabName,String bizType,String turnDir,Map<String, Object> variables)  throws Exception{
		Map<String,Object> searchParams = new HashMap<String,Object>();
		Jbpm4BizTabDTO dto = new Jbpm4BizTabDTO();
		dto.setBizInfId(bizInfId);
		dto.setBizTabName(bizTabName);
		dto.setBizType(bizType);
		dto.setValidateState("1");
		searchParams.put("dto", dto);
		//查询流程实例ID
		List<Jbpm4BizTabDTO> dataList = bizService.searchJbpm4BizTab(searchParams);
		if(dataList != null && dataList.size() >0){
			Jbpm4BizTabDTO bizTabDTO = dataList.get(0);
			String proInsId = bizTabDTO.getProInstanceId();
			//查询要处理的任务信息
			Map<String,String> taskInfo = jbpmTaskService.getTaskInfoByProInsId(proInsId); 
	    	String lastTaskId = taskInfo.get("TASK_ID");//当前流程实例ID下的　待办任务ID
	    	//完成任务
	    	completeTask(lastTaskId, turnDir, variables);
		}else{
			throw new Exception("待办任务不存在！");
		}
	}
	
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
        *        
        * @return
        * @throws
     */
    public List queryAllTask(Map<String, Object> param) throws Exception{
    	 Map<String,Object> queryMap = new HashMap<String,Object>();
        JbpmDTO dto = new JbpmDTO();
        if(param.get("acitityName")!=null && !"".equals(param.get("acitityName"))){
            dto.setAcitityName((String)param.get("acitityName"));
        }
        if(param.get("processInsId")!=null && !"".equals(param.get("processInsId"))){
            dto.setProcessInsId((String)param.get("processInsId"));
        }
        if(param.get("bizType")!=null && !"".equals(param.get("bizType"))){
            dto.setBizType((String)param.get("bizType"));
        }
        
        //此标志传1则后台自动拼装当前登录用户和归属的角色组
        if("1".equals(param.get("isQueryUserAndRoles"))){
        	UserInfo userInfo = sessionAPI.getCurrentUserInfo();
        	String userId = userInfo.getUserId()+"";
        	List<Map<String, Object>> roleGroupList = sysRoleAPI.getRoleGroupByUserId(userId);
        	StringBuffer userIdsBuf = new StringBuffer();
            userIdsBuf.append("(");
        	for(int i=0;i<roleGroupList.size();i++){
        		Map<String, Object> roleGroup = roleGroupList.get(i);
        		String roleGroupCode = (String)roleGroup.get("roleGroupCode");
        		userIdsBuf.append("'").append(roleGroupCode).append("',");
        	}
        	userIdsBuf.append("'").append(userId).append("')");
        	dto.setParamUserIds(userIdsBuf.toString());
        }else{
        	if(param.get("userId")!=null && !"".equals(param.get("userId"))){
                dto.setParamUserId((String)param.get("userId"));
            }
        	if(param.get("userIds")!=null && !"".equals(param.get("userIds"))){
            	String userIds = (String)param.get("userIds");
            	if(userIds.indexOf("(")>-1){
            		dto.setParamUserIds(userIds);
            	}else{
            		String[] userIdArr = userIds.split(",");
            		StringBuffer userIdSbf = new StringBuffer();
            		userIdSbf.append("(");
            		for(int i=0;i<userIdArr.length;i++){
            			userIdSbf.append("'").append(userIdArr[i]).append("'");
            			if(userIdArr.length>1 && i!=(userIdArr.length-1)){
            				userIdSbf.append(",");
            			}
            		}
            		userIdSbf.append(")");
            		dto.setParamUserIds(userIdSbf.toString());
            	}
                
            }
        }
        
        if(param.get("customSQL")!=null && !"".equals(param.get("customSQL"))){
            dto.setCustomSQL(param.get("customSQL").toString());
        }
        
        if(param.get("eqActName")!=null && !"".equals(param.get("eqActName"))){
        	if("Y".equalsIgnoreCase(param.get("eqActName").toString())){
        		 queryMap.put("eqActName", "1");
        	}
        }
        if(param.get("orgId")!=null && !"".equals(param.get("orgId"))){
            dto.setOrgId((String)param.get("orgId"));
        }
        
        if(param.get("busInfoName")!=null && !"".equals(param.get("busInfoName"))){
            dto.setBusInfoName((String)param.get("busInfoName"));
        }
        
        if(param.get("startTime")!=null && !"".equals(param.get("startTime"))){
            dto.setStartTime((String)param.get("startTime"));
        }
        
        if(param.get("endTime")!=null && !"".equals(param.get("endTime"))){
            dto.setEndTime((String)param.get("endTime"));
        }
        
        if(param.get("sortName")!=null && !"".equals(param.get("sortName"))){
            dto.setSortName((String)param.get("sortName"));
        }
        
        if(param.get("sortType")!=null && !"".equals(param.get("sortType"))){
            dto.setSortType((String)param.get("sortType"));
        }
        
        //自定义 业务表 关联工作流的sql语句
        if(param.get("myExtSQL")!=null && !"".equals(param.get("myExtSQL"))){
        	queryMap.put("myExtSQL", param.get("myExtSQL"));
        }
        if(param.get("myPage")!=null ){//分页信息
        	
        	queryMap.put("page", param.get("myPage"));
        }
        if(param.get("page")!=null ){//分页信息
        	PageParameter pageInfo = (PageParameter)param.get("page");
        	DataMsg dataMsg = new DataMsg();
        	dataMsg.setStartIndex((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize());
    		dataMsg.setEndIndex(pageInfo.getCurrentPage()  * pageInfo.getPageSize());
    		dataMsg.setPageSize(pageInfo.getPageSize());
        	queryMap.put("page", dataMsg);
        }
        
        queryMap.put("dto", dto);
        //已办任务查询
        if("COMPLETED_TASK".equals(param.get("processState"))){
            return jbpmServcie.queryCompletedTask(queryMap);
        }else if("END_TASK".equals(param.get("processState"))){
        	return jbpmServcie.queryEndTaskList(queryMap);
        }else{//待办任务查询
            return jbpmServcie.queryAllTask(queryMap);
        }
        
    }
    
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
        *        
        * @return
        * @throws
     */
    public List queryAllTaskByPaging(Map<String, Object> param) throws Exception{
        JbpmDTO dto = new JbpmDTO();
        if(param.get("acitityName")!=null && !"".equals(param.get("acitityName"))){
            dto.setAcitityName((String)param.get("acitityName"));
        }
        if(param.get("processInsId")!=null && !"".equals(param.get("processInsId"))){
            dto.setProcessInsId((String)param.get("processInsId"));
        }
        if(param.get("bizType")!=null && !"".equals(param.get("bizType"))){
            dto.setBizType((String)param.get("bizType"));
        }
        
        //此标志传1则后台自动拼装当前登录用户和归属的角色组
        if("1".equals(param.get("isQueryUserAndRoles"))){
        	UserInfo userInfo = sessionAPI.getCurrentUserInfo();
        	String userId = userInfo.getUserId()+"";
        	List<Map<String, Object>> roleGroupList = sysRoleAPI.getRoleGroupByUserId(userId);
        	StringBuffer userIdsBuf = new StringBuffer();
            userIdsBuf.append("(");
        	for(int i=0;i<roleGroupList.size();i++){
        		Map<String, Object> roleGroup = roleGroupList.get(i);
        		String roleGroupCode = (String)roleGroup.get("roleGroupCode");
        		userIdsBuf.append("'").append(roleGroupCode).append("',");
        	}
        	userIdsBuf.append("'").append(userId).append("')");
        	dto.setParamUserIds(userIdsBuf.toString());
        }else{
        	if(param.get("userId")!=null && !"".equals(param.get("userId"))){
                dto.setParamUserId((String)param.get("userId"));
            }
        	if(param.get("userIds")!=null && !"".equals(param.get("userIds"))){
            	String userIds = (String)param.get("userIds");
            	if(userIds.indexOf("(")>-1){
            		dto.setParamUserIds(userIds);
            	}else{
            		String[] userIdArr = userIds.split(",");
            		StringBuffer userIdSbf = new StringBuffer();
            		userIdSbf.append("(");
            		for(int i=0;i<userIdArr.length;i++){
            			userIdSbf.append("'").append(userIdArr[i]).append("'");
            			if(userIdArr.length>1 && i!=(userIdArr.length-1)){
            				userIdSbf.append(",");
            			}
            		}
            		userIdSbf.append(")");
            		dto.setParamUserIds(userIdSbf.toString());
            	}
                
            }
        }
        
        if(param.get("customSQL")!=null && !"".equals(param.get("customSQL"))){
            dto.setCustomSQL(param.get("customSQL").toString());
        }
        
        if(param.get("eqActName")!=null && !"".equals(param.get("eqActName"))){
        	if("Y".equalsIgnoreCase(param.get("eqActName").toString())){
        		 param.put("eqActName", "1");
        	}
        }
        if(param.get("orgId")!=null && !"".equals(param.get("orgId"))){
            dto.setOrgId((String)param.get("orgId"));
        }
        
        if(param.get("busInfoName")!=null && !"".equals(param.get("busInfoName"))){
            dto.setBusInfoName((String)param.get("busInfoName"));
        }
        
        if(param.get("startTime")!=null && !"".equals(param.get("startTime"))){
            dto.setStartTime((String)param.get("startTime"));
        }
        
        if(param.get("endTime")!=null && !"".equals(param.get("endTime"))){
            dto.setEndTime((String)param.get("endTime"));
        }
        
        if(param.get("sortName")!=null && !"".equals(param.get("sortName"))){
            dto.setSortName((String)param.get("sortName"));
        }
        
        if(param.get("sortType")!=null && !"".equals(param.get("sortType"))){
            dto.setSortType((String)param.get("sortType"));
        }
        
        //自定义 业务表 关联工作流的sql语句
        if(param.get("myExtSQL")!=null && !"".equals(param.get("myExtSQL"))){
        	param.put("myExtSQL", param.get("myExtSQL"));
        }
        /*if(param.get("myPage")!=null ){//分页信息
        	
        	param.put("page", param.get("myPage"));
        }*/
        /*if(param.get("page")!=null ){//分页信息
        	PageParameter pageInfo = (PageParameter)param.get("page");
        	DataMsg dataMsg = new DataMsg();
        	dataMsg.setStartIndex((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize());
    		dataMsg.setEndIndex(pageInfo.getCurrentPage()  * pageInfo.getPageSize());
    		dataMsg.setPageSize(pageInfo.getPageSize());
        	param.put("page", dataMsg);
        }*/
        
        param.put("dto", dto);
        //已办任务查询
        if("COMPLETED_TASK".equals(param.get("processState"))){
            return jbpmServcie.queryCompletedTaskByPaging(param);
        }else if("END_TASK".equals(param.get("processState"))){
        	return jbpmServcie.queryEndTaskListByPaging(param);
        }else{//待办任务查询
            return jbpmServcie.queryAllTaskByPaging(param);
        }
        
    }
    /**
     * 根据业务类型和业务主键查询历史活动列表
     * @param bizInfId 业务主键 必填
     * @param bizType  业务类型  必填
     * @return
     * @throws
     */
    public List<Map<String, Object>> queryHisActInsts(String bizInfId,String bizType) throws Exception{
        List<Map<String, Object>> actInstList = new ArrayList<Map<String,Object>>();
        Map<String,Object> searchParams = new HashMap<String,Object>();
        Jbpm4BizTabDTO dto = new Jbpm4BizTabDTO();
        dto.setBizInfId(bizInfId);
        dto.setBizType(bizType);
        searchParams.put("dto", dto);
        List<Jbpm4BizTabDTO> bizList = bizService.searchJbpm4BizTab(searchParams);
        if(null!=bizList && bizList.size()>0){
            Jbpm4BizTabDTO bizTabDTO = bizList.get(0);
            String proInstId = bizTabDTO.getProInstanceId();
            actInstList = jbpmServcie.queryHisActInstsByProInstId(proInstId);
            
        }
        return actInstList;
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
        return jbpmTaskService.getCurTaskInfo(bizInfId, bizType);
    }
    
	public void deleteOldSomeBizOptionInf(Map<String, String> param)throws Exception {
		bizOption.deleteOldSomeBizOptionInf(param);
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYTastService#updateBizTaskTypeById(java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public void updateBizTaskTypeById(String id,String bizTaskType) throws Exception {

		bizService.updateBizTaskTypeById(id, bizTaskType);
	}
	
	/**
	 * 获取节点配置的角色
	 * @param proDefKey
	 * @param proDefVersion
	 * @param acitityName
	 * @param proMap
	 * @return 角色ID
	 * @throws Exception
	 */
	public String getPartnerRoleByProDef(String proDefKey,String proDefVersion, String acitityName,Map<String,Object> proMap) throws Exception{
		//版本号可以不传，不传则取最新版本
		if(null==proDefVersion || "".equals(proDefVersion)){
			List<ProcessDefinition> proDefList = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(proDefKey).orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION).list();
			if(proDefList!=null && proDefList.size()>0){
				ProcessDefinition proDef = proDefList.get(0);
				proDefVersion = proDef.getVersion()+"";
			}
		}
		String formId = formService.queryJbpm4FormInfoIdBysql(proDefKey, proDefVersion, acitityName);
		Jbpm4FormInfoDTO dto = new Jbpm4FormInfoDTO();
    	dto.setId(Long.parseLong(formId));
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("dto", dto);
    	Jbpm4FormInfoDTO ruleDto = formService.queryOneJbpm4FormInfo(paramMap);
    	String partnerType = ruleDto.getParticipatorType();
    	String partType = ruleDto.getPartType();
    	//按规则自动选择参与者 并且选择的参与者为角色时情况推送到角色组而非个人
    	if(ConstantJBPM.PARTNER_TYPE_5.equals(partnerType) && ConstantJBPM.PARTNER_0.equals(partType)){
    		String roleGroup = ruleDto.getOtherParams();
    		if(null==roleGroup || "".equals(roleGroup) || "null".equalsIgnoreCase(roleGroup)){
    			roleGroup = "-1";
    		}
    		boolean dialogBoolean = false;
        	String nextPartnerId = roleGroup;
        	String ruleInfo = ruleDto.getRuleInfo();
        	String otherParams = ruleDto.getOtherParams();
        	if(StringUtils.isEmpty(ruleInfo)) ruleInfo="";
        	if(StringUtils.isEmpty(otherParams)) otherParams="";
        	if(StringUtils.isEmpty(partType)) partType="";
        	if(StringUtils.isEmpty(formId)) formId="";
        	if(StringUtils.isEmpty(nextPartnerId)) nextPartnerId="";
        	StringBuilder resultMsg = new StringBuilder("[{");
        	//是否自动分配到角色组
        	resultMsg.append("isAutoAllocatRoleGroup:'").append(true).append("'");
        	resultMsg.append(",participantType:'").append(partnerType).append("'");
        	resultMsg.append(",participantRule:'").append(ruleInfo).append("'");
        	resultMsg.append(",otherParams:'").append(otherParams).append("'");
        	resultMsg.append(",nextPartnerId:'").append(nextPartnerId).append("'");
        	resultMsg.append(",partType:'").append(partType).append("'");
        	resultMsg.append(",formId:'").append(formId).append("'");     
        	resultMsg.append(",proNextActName:'").append(ruleDto.getProActivityName()).append("'");
        	resultMsg.append(",dialogBoolean:'").append(dialogBoolean).append("'");
        	resultMsg.append("}]");
        	return resultMsg.toString();
    	}else{
    		Map<String, String> ruleMap = new HashMap<String,String>();
        	ruleMap.put("FORM_ID", formId);
        	ruleMap.put("PARTICIPANT_TYPE", ruleDto.getParticipatorType());
        	ruleMap.put("PARTICIPANT_RULE", ruleDto.getRuleInfo());
        	ruleMap.put("OTHER_PARAMS", ruleDto.getOtherParams());
        	ruleMap.put("PART_TYPE", ruleDto.getPartType());
        	
        	String str = this.makeupRuleJSON(null, null, proMap, ruleMap);
        	return str;
    	}
	}
	
	/**
	 * 获取节点配置的角色
	 * @param proInstId 流程实例ID	
	 * @param acitityName 节点名称
	 * @param proMap 流程变量
	 * @return 角色ID
	 * @throws Exception
	 */
	public String getPartnerRoleByProInst(String proInstId,String acitityName,Map<String,Object> proMap) throws Exception{
		ProcessInstance processInstance = myJBPMTool.getExecutionService().findProcessInstanceById(proInstId);
		ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId()).uniqueResult();
		String formId = formService.queryJbpm4FormInfoIdBysql(processDefinition.getKey(), processDefinition.getVersion()+"", acitityName);
		Jbpm4FormInfoDTO dto = new Jbpm4FormInfoDTO();
    	dto.setId(Long.parseLong(formId));
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("dto", dto);
    	Jbpm4FormInfoDTO ruleDto = formService.queryOneJbpm4FormInfo(paramMap);
    	
    	String partnerType = ruleDto.getParticipatorType();
    	String partType = ruleDto.getPartType();
    	//按规则自动选择参与者 并且选择的参与者为角色时情况推送到角色组而非个人
    	if(ConstantJBPM.PARTNER_TYPE_5.equals(partnerType) && ConstantJBPM.PARTNER_0.equals(partType)){
    		String roleId = ruleDto.getOtherParams();
    		if(null==roleId || "".equals(roleId) || "null".equalsIgnoreCase(roleId)){
    			roleId = "-1";
    		}
    		return roleId;
    	}else{
    		Map<String, String> ruleMap = new HashMap<String,String>();
        	ruleMap.put("FORM_ID", formId);
        	ruleMap.put("PARTICIPANT_TYPE", ruleDto.getParticipatorType());
        	ruleMap.put("PARTICIPANT_RULE", ruleDto.getRuleInfo());
        	ruleMap.put("OTHER_PARAMS", ruleDto.getOtherParams());
        	ruleMap.put("PART_TYPE", ruleDto.getPartType());
        	
        	String str = this.makeupRuleJSON(null, null, proMap, ruleMap);
        	return str;
    	}
    	
    	
	}
	
	/**
	 * 获取下一个活动节点配置的角色信息
	 * 如果为规则自动选择参与者并且参与者类型为角色，则获取配置的角色组编码
	 * 其他情况跟getPartnerRuleInfoAndPartnerId接口逻辑一致
	 */
	@Transactional(readOnly=true)
	public String getPartnerRole(String taskId, String turnDir,Map<String, Object> proInsMap) throws Exception {
		Map<String, String> ruleMap = this.getPartnerRuleInfo(taskId, turnDir, proInsMap);

		String partnerType = ruleMap.get("PARTICIPANT_TYPE");
    	String partType = ruleMap.get("PART_TYPE");
    	//按规则自动选择参与者 并且选择的参与者为角色时情况推送到角色组而非个人
    	if(ConstantJBPM.PARTNER_TYPE_5.equals(partnerType) && ConstantJBPM.PARTNER_0.equals(partType)){
    		String actName = ruleMap.get("ACT_NAME");
        	String proProcessInsId = String.valueOf(proInsMap.get("proProcessInsId"));
        	String roleGroup = ruleMap.get("OTHER_PARAMS");
        	//先查询历史执行人
        	String userId = this.getHisUserOfActiveByProInstId(proProcessInsId, actName);
        	if(null!=userId && !"".equals(userId) && !"-1".equals(userId)){
        		roleGroup = userId;
        	}
        	if(null==roleGroup || "".equals(roleGroup) || "null".equalsIgnoreCase(roleGroup)){
        		roleGroup  = "-1";
        	}
        	boolean dialogBoolean = false;
        	String nextPartnerId = roleGroup;
        	String ruleInfo = ruleMap.get("PARTICIPANT_RULE");
        	String otherParams = ruleMap.get("OTHER_PARAMS");
        	String formId = ruleMap.get("FORM_ID");
        	String proNextActName = ruleMap.get("ACT_NAME");
        	if(StringUtils.isEmpty(ruleInfo)) ruleInfo="";
        	if(StringUtils.isEmpty(otherParams)) otherParams="";
        	if(StringUtils.isEmpty(partType)) partType="";
        	if(StringUtils.isEmpty(formId)) formId="";
        	if(StringUtils.isEmpty(nextPartnerId)) nextPartnerId="";
        	StringBuilder resultMsg = new StringBuilder("[{");
        	//是否自动分配到角色组
        	resultMsg.append("isAutoAllocatRoleGroup:'").append(true).append("'");
        	resultMsg.append(",participantType:'").append(partnerType).append("'");
        	resultMsg.append(",participantRule:'").append(ruleInfo).append("'");
        	resultMsg.append(",otherParams:'").append(otherParams).append("'");
        	resultMsg.append(",nextPartnerId:'").append(nextPartnerId).append("'");
        	resultMsg.append(",partType:'").append(partType).append("'");
        	resultMsg.append(",formId:'").append(formId).append("'");        
        	resultMsg.append(",proNextActName:'").append(proNextActName).append("'");
        	resultMsg.append(",dialogBoolean:'").append(dialogBoolean).append("'");
        	resultMsg.append("}]");
        	return resultMsg.toString();
    	}else{
    		return this.makeupRuleJSON(taskId, turnDir, proInsMap, ruleMap);
    	}
	}
	
	/**
	 * 根据formId获取参与者配置信息
	 * @param formId
	 * @return
	 * @throws Exception
	 */
	public String getPartnerRuleInfoByFormId(String formId) throws Exception{
		Jbpm4FormInfoDTO dto = new Jbpm4FormInfoDTO();
    	dto.setId(Long.parseLong(formId));
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("dto", dto);
    	Jbpm4FormInfoDTO ruleDto = formService.queryOneJbpm4FormInfo(paramMap);
    	
    	StringBuilder resultMsg = new StringBuilder("[{");
    	resultMsg.append("participantType:'").append(ruleDto.getParticipatorType()).append("'");
    	resultMsg.append(",participantRule:'").append(ruleDto.getRuleInfo()).append("'");
    	resultMsg.append(",otherParams:'").append(ruleDto.getOtherParams()).append("'");
    	resultMsg.append(",partType:'").append(ruleDto.getPartType()).append("'");
    	resultMsg.append(",formId:'").append(formId).append("'");
    	resultMsg.append(",proNextActName:'").append(ruleDto.getProActivityName()).append("'");
    	boolean dialogBoolean = false;
    	if(ConstantJBPM.PARTNER_TYPE_3.equals(ruleDto.getParticipatorType()) || ConstantJBPM.PARTNER_TYPE_4.equals(ruleDto.getParticipatorType())){
    		dialogBoolean = true;
    	}
    	resultMsg.append(",dialogBoolean:'").append(dialogBoolean).append("'");
    	boolean isAutoAllocatRoleGroup = false;
    	//按规则自动选择参与者 并且选择的参与者为角色时情况推送到角色组而非个人
    	if(ConstantJBPM.PARTNER_TYPE_5.equals(ruleDto.getParticipatorType()) 
    			&& ConstantJBPM.PARTNER_0.equals(ruleDto.getPartType())){
    		isAutoAllocatRoleGroup = true;
    	}
    	resultMsg.append(",isAutoAllocatRoleGroup:'").append(isAutoAllocatRoleGroup).append("'");
    	resultMsg.append("}]");
    	return resultMsg.toString();
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
		String userId = jbpmTaskService.getLastHisUserOfActiveByBizType(bizInfId, bizType, actName,actNameLike);
        return userId;
	}

}
