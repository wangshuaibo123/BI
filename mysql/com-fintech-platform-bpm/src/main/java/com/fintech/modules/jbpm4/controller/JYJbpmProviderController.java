package com.fintech.modules.jbpm4.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;
import com.fintech.platform.jbpm4.service.IJbpmService;
import com.fintech.platform.jbpm4.service.JYTastService;
import com.fintech.platform.jbpm4.tool.JbpmTools;
import com.fintech.platform.restservice.web.base.BaseController;
/**
 * 
 * @Description: 定义对 外部系统 提供的 proiver 此controller 仅作查询
 * @author
 * @date 2014年9月5日 上午11:05:20
 * @version V1.0
 */
@Controller
@Scope("prototype")
@RequestMapping("/dojbpm/jyjbpmProvider")
public class JYJbpmProviderController extends BaseController{
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.JYTastServiceImpl")
	private JYTastService jyTaskService;
	
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.JbpmServiceImpl")
	private IJbpmService jbpmServcie;
	
    /**
     * 获取 流程  下一个走向的 
     * 待办任务 配置的 选人规则 信息
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getPartnerRuleInfo.do")
    @ResponseBody
   	public String getPartnerRuleInfo(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	//任务ID todo 当前任务是否已经执行，并且是当前执行人的待办任务
    	String taskId = requ.getParameter("taskId");
		String turnDir = this.getParameterString("turnDirection");//流程走向
    	//String proInstanceId = this.getParameterString("processInsId");//流程实例ID
    	//String bizTabId = this.getParameterString("bizTabId");//jbpm4_biz_tab 主键ID
		
		Map<String,Object> proInsMap = new HashMap<String,Object>();
    	String otherParamJavaCode = this.getParameterString("otherParamJavaCode");//其他流程 实例 变量信息
    	Map<String, Object> otherMap = JbpmTools.getInstance().makeupDynamicMap(otherParamJavaCode);//动态编译 
    	proInsMap.putAll(otherMap);//放入工作流 业务变量中 
    	
    	String resultMsg = null;
		resultMsg = jyTaskService.getPartnerRuleInfoAndPartnerId(taskId, turnDir, proInsMap);
        return resultMsg;//this.outWriteString(response, resultMsg);
   	}
    /**
     * 通过流程定义key,任务节点名称,流程版本号获取 该节点的选人规则信息
     * 返回 该节点的规则信息及参与者 信息
     * @param requ
     * @param resp
     * @throws Exception
     */
    @RequestMapping(value="/getPartnerInfoByProDef.do")
   	public void getPartnerInfoByProDef(HttpServletRequest requ, HttpServletResponse resp) throws Exception{
    	String proDefVersion =  this.getParameterString("proDefVersion");
		String proDefKey = this.getParameterString("proDefKey");
		String acitityName = this.getParameterString("acitityName");
    	Map<String,Object> varMap = new HashMap<String,Object>();//存放其他业务参数信息
    	String otherParamJavaCode = this.getParameterString("otherParamJavaCode");//其他流程 实例 变量信息
    	Map<String, Object> otherMap = JbpmTools.getInstance().makeupDynamicMap(otherParamJavaCode);//动态编译 
    	varMap.putAll(otherMap);
    	
    	String resultMsg = jyTaskService.getPartnerInfoByProDef(proDefKey, proDefVersion, acitityName, varMap);
        this.outWriteString(resp, resultMsg);
   	}
    /**
     * 获取 流程实例的状态
     * @param requ
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/getProInsState.do")
   	public void getProInsState(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	String bizTabId = requ.getParameter("bizTabId");
    	String resultMsg = jyTaskService.getProInsState(bizTabId);
        this.outWriteString(response, resultMsg);
   	}
    /**
     * 通过 userId，任务ID 判断userID
     * 是否可以　执行该待办任务
     * @param requ
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/getOperateTaskStateInfo.do")
   	public void getOperateTaskStateInfo(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	String taskId = requ.getParameter("taskId");
    	String curUserId = requ.getParameter("curUserId");
    	
    	String resultMsg = jyTaskService.getOperateTaskStateInfo(taskId,curUserId);
        this.outWriteString(response, resultMsg);
   	}
    
    /**
     * 根据流程定义key获取活动定义列表
     * 只返回类型为 task和custom的活动定义
     * @param proDefKey 流程定义KEY
     * @param response
     * @return 例：[{actName:'风险管理部'},{actName:'门店经理'}]
     * @throws Exception
     */
    @RequestMapping(value="/getActivityDefines.do")
    @SuppressWarnings("all")
    public void getActivityDefines(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	//流程定义key
    	String proDefKey = requ.getParameter("proDefKey");
    	String resultMsg = jbpmServcie.getActivityDefines(proDefKey);
        this.outWriteString(response, resultMsg);
   	}
    
    /**
     * 根据流程实例ID和活动名称获取活动信息
     * @param proInstId 流程实例ID
     * @param actName 活动名称
     * @return 例： {performerId:'1',performerName:'admin',completeTime:'2014-12-10 18:31:40'}
     * @throws IOException
     */
    @RequestMapping(value="/getActInstByProInstIdAndActName.do")
    @SuppressWarnings("all")
    public void getActInstByProInstIdAndActName(HttpServletRequest requ, HttpServletResponse response) throws IOException{
    	//流程实例ID
    	String proInstId = requ.getParameter("proInstId");
    	//活动名称
    	String actName = requ.getParameter("actName");
    	String resultMsg = jbpmServcie.getActInstByProInstIdAndActName(proInstId, actName);
    	this.outWriteString(response, resultMsg);
    }
    
	 /**
     * 写 字符串 至前台页面
     * @param response
     * @param resultMsg
     * @throws IOException 
     */
    private void outWriteString( HttpServletResponse response,String resultMsg) throws IOException{
    	response.setContentType("text/Xml;charset=utf-8");
		Writer out = response.getWriter();
		out.write(resultMsg);
		out.flush();
		out.close();
    }
    
    /**
     * 获取 流程  下一个节点配置的角色
     * 待办任务 配置的 选人规则 信息
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getPartnerRole.do")
    @SuppressWarnings("all")
    public void getPartnerRole(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	//任务ID todo 当前任务是否已经执行，并且是当前执行人的待办任务
    	String taskId = requ.getParameter("taskId");
		String turnDir = this.getParameterString("turnDirection");//流程走向		
		Map<String,Object> proInsMap = new HashMap<String,Object>();
    	String otherParamJavaCode = this.getParameterString("otherParamJavaCode");//其他流程 实例 变量信息
    	Map<String, Object> otherMap = JbpmTools.getInstance().makeupDynamicMap(otherParamJavaCode);//动态编译 
    	proInsMap.putAll(otherMap);//放入工作流 业务变量中 
    	String resultMsg = jyTaskService.getPartnerRole(taskId, turnDir, proInsMap);
    	this.outWriteString(response, resultMsg);
    	
   	}
    
    /**
     * 通过流程定义key,任务节点名称,流程版本号获取 该节点配置的角色
     * 返回 该节点配置的角色组
     * @param requ
     * @param resp
     * @throws Exception
     */
    @RequestMapping(value="/getPartnerRoleByProDef.do")
    @SuppressWarnings("all")
   	public void getPartnerRoleByProDef(HttpServletRequest requ, HttpServletResponse resp) throws Exception{
    	//proDefVersion可以不传,如果为空则取最新版本
    	String proDefVersion =  this.getParameterString("proDefVersion");
		String proDefKey = this.getParameterString("proDefKey");
		String acitityName = this.getParameterString("acitityName");
    	Map<String,Object> varMap = new HashMap<String,Object>();//存放其他业务参数信息
    	String otherParamJavaCode = this.getParameterString("otherParamJavaCode");//其他流程 实例 变量信息
    	Map<String, Object> otherMap = JbpmTools.getInstance().makeupDynamicMap(otherParamJavaCode);//动态编译 
    	varMap.putAll(otherMap);
    	
    	String roldGroup = jyTaskService.getPartnerRoleByProDef(proDefKey, proDefVersion, acitityName, varMap);
    	if(null!=roldGroup && !"".equals(roldGroup) && !"null".equals(roldGroup)){
    		this.outWriteString(resp, roldGroup);
    	}
   	}
    
    /**
     * 通过流程实例ID,任务节点名称获取 该节点配置的角色
     * 返回 该节点配置的角色组
     * @param requ
     * @param resp
     * @throws Exception
     */
    @RequestMapping(value="/getPartnerRoleByProInst.do")
    @SuppressWarnings("all")
   	public void getPartnerRoleByProInst(HttpServletRequest requ, HttpServletResponse resp) throws Exception{
    	String proInstId =  this.getParameterString("proInstId");		
		String acitityName = this.getParameterString("acitityName");
    	Map<String,Object> varMap = new HashMap<String,Object>();//存放其他业务参数信息
    	String otherParamJavaCode = this.getParameterString("otherParamJavaCode");//其他流程 实例 变量信息
    	Map<String, Object> otherMap = JbpmTools.getInstance().makeupDynamicMap(otherParamJavaCode);//动态编译 
    	varMap.putAll(otherMap);
    	
    	String roldGroup = jyTaskService.getPartnerRoleByProInst(proInstId, acitityName, varMap);
    	if(null!=roldGroup && !"".equals(roldGroup) && !"null".equals(roldGroup)){
    		this.outWriteString(resp, roldGroup);
    	}
   	}
    
    /**
     * 根据formId获取流程配置信息
     * 待办任务 配置的 选人规则 信息
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getPartnerRuleInfoByFormId.do")
    @ResponseBody
   	public void getPartnerRuleInfoByFormId(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	//formId
    	String formId = requ.getParameter("formId");
		Map<String,Object> proInsMap = new HashMap<String,Object>();
    	String otherParamJavaCode = this.getParameterString("otherParamJavaCode");//其他流程 实例 变量信息
    	Map<String, Object> otherMap = JbpmTools.getInstance().makeupDynamicMap(otherParamJavaCode);//动态编译 
    	proInsMap.putAll(otherMap);//放入工作流 业务变量中 
    	
    	String resultMsg = null;
    	Jbpm4FormInfoDTO dto = new Jbpm4FormInfoDTO();
    	dto.setId(Long.parseLong(formId));
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("dto", dto);
    	resultMsg = jyTaskService.getPartnerRuleInfoByFormId(formId);
        this.outWriteString(response, resultMsg);
   	}

}
