package com.fintech.platform.jbpm4.jbpmPartner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.jbpm4.dto.JYPartnerDTO;
import com.fintech.platform.jbpm4.service.IJbpmService;
import com.fintech.platform.jbpm4.service.JYSelectPartnerService;
import com.fintech.platform.jbpm4.tool.ConstantJBPM;

/**
 * 前台页面 通过人工选择参与者 使用
 * 按规则自动选择参与者时使用 
 * @description: 定义 参与者选人接口的基本实现类
 * @author
 * @date:2015年1月11日下午12:36:50
 */
@Service("com.fintech.platform.jbpm4.jbpmPartner.JYSelectPartnerServiceImpl")
public class JYSelectPartnerServiceImpl implements JYSelectPartnerService ,Serializable{
	private static final long serialVersionUID = 1L;
	//获取 访问业务层的对象
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.jbpmPartner.PartnerSysRule")
	private PartnerSysRule sysRule;
	@Autowired
    private SessionAPI sessionAPI;
    
    /**
     * 配置 按规则自动选择参与者时 调用该方法
     * 实现：通过其配置 获取对应的的相关人员信息（排除离职人员，但无法排除请假人员）
     * 按平均任务个数的分配原则进行 分配，此时没有个人任务上限控制 
     */
	public JYPartnerDTO selectPartnerBySysRule(String bizInfId,
			String bizTabName, String bizTabId, String proInsId,
			String curActName, Map<String, Object> variables) {
		String parUserId = "-1";//待分配 通过流程 监控手工 任务分配
		JYPartnerDTO partner = new JYPartnerDTO();
		
		List<JYPartnerDTO> partnerList = new ArrayList<JYPartnerDTO>();
		//UserInfo userinfo = sessionAPI.getCurrentUserInfo();//获取当前登陆人相关信息

		//获取 配置的 角色 code 信息
		String roleCode = (String) variables.get("roleCode");
		String partType = (String) variables.get("partType");//配置 参与者类型 0 ：角色，1：机构，2：人员
		
		partnerList = this.selectPartnerByOperator(bizInfId, bizTabName, bizTabId, proInsId, curActName, variables);
    	String isCountersign = (String)variables.get(ConstantJBPM.IS_COUNTERSIGN);
    	//是否是会签节点 会签不再自动分配到某个人
    	if("1".equals(isCountersign)){
    		if(partnerList.size()>0){
    			StringBuffer parUsers = new StringBuffer();
    			for(int i=0;i<partnerList.size();i++){
    				JYPartnerDTO partnerDTO = partnerList.get(i);
    				String userId = partnerDTO.getParUserId();
    				if(i==0){
    					parUsers.append(userId);
    				}else{
    					parUsers.append(",").append(userId);
    				}
    			}
    			parUserId = parUsers.toString();
    		}
    	}else{
    		parUserId = sysRule.obtainPartnerByAVGRule(partnerList);
    	}
    	partner.setParUserId(parUserId);
		return partner;
		
	}
	public JYPartnerDTO selectPartnerByDroolsRule(String bizInfId,
			String bizTabName, String bizTabId, String proInsId,
			String curActName, Map<String, Object> variables) {
		
		return null;
	}
	/*
	 * (non-Javadoc)
	 * @see com.fintech.platform.jbpm4.service.JYSelectPartnerService#selectPartnerByOperator(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	public List<JYPartnerDTO> selectPartnerByOperator(String bizInfId,
			String bizTabName, String bizTabId, String proInsId,
			String curActName, Map<String, Object> variables) {
		List<JYPartnerDTO> partnerList = new ArrayList<JYPartnerDTO>();
		//UserInfo userinfo = sessionAPI.getCurrentUserInfo();//获取当前登陆人相关信息
		//获取 配置的 角色 code 信息
		String roleCode = (String) variables.get("roleCode");
		String partType = (String) variables.get("partType");//配置 参与者类型 0 ：角色，1：机构，2：人员
		//页面查询条件
		String parUserNo = (String) variables.get("parUserNo");
		String parRealName = (String) variables.get("parRealName");
		//调用 通过 角色编码 获取 该角色下 所有人员信息
    	if(ConstantJBPM.PARTNER_0.equals(partType) || partType == null || "".equals(partType)){
    		/*WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
    		ServletContext servletContext = webApplicationContext.getServletContext();
    		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    		SysRoleAPI sysRoleAPI =(SysRoleAPI) context.getBean("sysRoleAPI");*/
    		//获取 相关的接口
    		ApplicationContext context = null;
    		try{
    			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
    			ServletContext servletContext = webApplicationContext.getServletContext();
    			context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    		}catch(Exception e){
    			context = SysConfigAPI.getApplicationContext();
    		}
    		OrgAPI orgApi =(OrgAPI) context.getBean("orgAPI");
    		
    		Map<String, Object> searchParams = new HashMap<String, Object>();
    		//searchParams.put("roleType", "2");//只查询 工作流角色
    		//searchParams.put("roleCode", roleCode);
    		
    		searchParams.put("groupCodes","'" +roleCode+"'"); 
    		
    		//页面查询条件
    		searchParams.put("userNo", parUserNo);
    		searchParams.put("userName", parRealName);
        	
    		//调用 通过角色查询人员的 接口
    		List<Map<String, Object>> list = orgApi.queryUserListPage(0, 1, searchParams);//sysRoleAPI.queryUserList(0, 1, searchParams);
    		//将list map 转换成 JYPartnerDTO
        	if(list != null &&  list.size() >0){
        		for(Map<String,Object> tempMap :list){
        			String id = String.valueOf(tempMap.get("id"));
        			String userNo = String.valueOf(tempMap.get("userNo"));
        			String userName = String.valueOf(tempMap.get("userName"));
        			JYPartnerDTO partnerDTO = new JYPartnerDTO();
        			partnerDTO.setParUserId(id);
        			partnerDTO.setParUserNo(userNo);
        			partnerDTO.setParRealName(userName);
        			partnerList.add(partnerDTO);
        		}
        	}
    	}else if(ConstantJBPM.PARTNER_1.equals(partType)){//调用 通过组织查询出人的 接口
    		//获取 相关的接口
    		ApplicationContext context = null;
    		try{
    			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
    			ServletContext servletContext = webApplicationContext.getServletContext();
    			context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    		}catch(Exception e){
    			context = SysConfigAPI.getApplicationContext();
    		}
    		OrgAPI orgApi =(OrgAPI) context.getBean("orgAPI");
    		
    		Map<String,Object> searchParams = new HashMap<String,Object>();
    		searchParams.put("orgId", roleCode);//所属 机构id
    		//页面查询条件
    		searchParams.put("userNo", parUserNo);
    		searchParams.put("userName", parRealName);
    		// 调用接口的相关实现类
    		List<Map<String,Object>> userList = orgApi.queryUserListPage(0, 1, searchParams);
    		//将list map 转换成 JYPartnerDTO
        	if(userList != null &&  userList.size() >0){
        		for(Map<String,Object> tempMap :userList ){
        			String id = String.valueOf(tempMap.get("id"));
        			String userNo = String.valueOf(tempMap.get("userNo"));
        			String userName = String.valueOf(tempMap.get("userName"));
        			JYPartnerDTO partnerDTO = new JYPartnerDTO();
        			partnerDTO.setParUserId(id);
        			partnerDTO.setParUserNo(userNo);
        			partnerDTO.setParRealName(userName);
        			partnerList.add(partnerDTO);
        		}
        	}
        	
    	}else if(ConstantJBPM.PARTNER_2.equals(partType)){// 通过人员 编码 或是 ID 查询出 人员 详情 接口
    		//获取 相关的接口
    		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
    		ServletContext servletContext = webApplicationContext.getServletContext();
    		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    		OrgAPI orgApi =(OrgAPI) context.getBean("orgAPI");
    		String[] userIds = roleCode.split(",");
    		for(String userId :userIds){
    			UserInfo user = orgApi.getUserInfoDetail(userId);
    			if(user == null ) continue;//如果没有查询到 人员信息 则继续下一个
    			if(StringUtils.isNotEmpty(parUserNo) && !user.getUserNo().contains(parUserNo)) continue;
    			if(StringUtils.isNotEmpty(parRealName) && !user.getUserName().contains(parRealName))continue;
    			JYPartnerDTO partnerDTO = new JYPartnerDTO();
    			partnerDTO.setParUserId(userId);
    			partnerDTO.setParUserNo(user.getUserNo());
    			partnerDTO.setParRealName(user.getUserName());
    			partnerList.add(partnerDTO);
    		}
    		
    	}
    	//员工请假处理
    	partnerList = dealLeaveUser(partnerList);
		return partnerList;
	}
	/**
	 * 人工手动 选择 参与者
	 * @param bizInfId 业务表主键ID
	 * @param bizTabName 业务表名称
	 * @param bizTabId jbpm4_biz_tab 表主键ID
	 * @param proInsId 主流程实例ID
	 * @param curActName 当前活动的流程节点名称
	 * @param variables 流程实例业务变量 信息
	 * @return
	 * @throws Exception 
	 */
	public List<JYPartnerDTO> mutiSelectPartnerByOperator(String bizInfId,
			String bizTabName, String bizTabId, String processInsId,
			String acitityName, Map<String, Object> proInsMap) throws Exception {
		
		List<JYPartnerDTO> partnerList = new ArrayList<JYPartnerDTO>();
		String participantRule = (String) proInsMap.get("participantRule");
    	//如果有补充规则 则调用 其配置的补充 规则java 类
		if(StringUtils.isNotEmpty(participantRule)){
			ApplicationContext context = null;
			try{
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				ServletContext servletContext = webApplicationContext.getServletContext();
				context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			}catch(Exception e){
				context = SysConfigAPI.getApplicationContext();
			}
			//调用 自定义的 选人规则 信息
			JYSelectPartnerService jySelectBiz = (JYSelectPartnerService) context.getBean(participantRule);
			partnerList = jySelectBiz.selectPartnerByOperator(bizInfId, bizTabName, bizTabId, processInsId, acitityName, proInsMap);
		}else{	
			//使用系统 默认 手工选人 规则
			partnerList = this.selectPartnerByOperator(bizInfId, bizTabName, bizTabId, processInsId, acitityName, proInsMap);
		}
		return partnerList;
	}
	
	/**
	 * 请假处理
	 * @param paraterList
	 */
	private List<JYPartnerDTO> dealLeaveUser(List<JYPartnerDTO> paraterList){
		List<JYPartnerDTO> paraters = new ArrayList<JYPartnerDTO>();
		ApplicationContext context = null;
		try{
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
			ServletContext servletContext = webApplicationContext.getServletContext();
			context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		}catch(Exception e){
			context = SysConfigAPI.getApplicationContext();
		}
		SysConfigAPI sysConfigAPI = (SysConfigAPI) context.getBean("sysConfigAPI");
		//获取工作流请假处理开关
		String flowLeave = sysConfigAPI.getValue("flow_leave_switch");
		//是否处理员工请假
		if("yes".equalsIgnoreCase(flowLeave)){
			if(paraters!=null){
				IJbpmService jbpmService =  (IJbpmService)context.getBean("com.fintech.platform.jbpm4.service.impl.JbpmServiceImpl");
				for(JYPartnerDTO partnerDTO:paraterList){
					String userId = partnerDTO.getParUserId();
					//判断是否请假
					if(!jbpmService.checkLeaveByUserId(userId)){
						paraters.add(partnerDTO);
					}
				}
			}
		}else{
			paraters = paraterList;			
		}
		
		return paraters;
	}

}
