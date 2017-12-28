package com.jy.modules.jbpm4.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.api.sysauth.SysRoleAPI;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.jbpm4.dto.JYPartnerDTO;
import com.jy.platform.jbpm4.jbpmPartner.JYSelectPartnerServiceImpl;
import com.jy.platform.jbpm4.tool.ConstantJBPM;
import com.jy.platform.jbpm4.tool.JbpmTools;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: JbpmJYPartnerController
 * @description: 定义  人工 选择参与者信息 控制层
 * @author:  douyongliang
 */
@Controller
@Scope("prototype")
@RequestMapping("/dojbpm/jbpmJYPartner")
public class JYJbpmPartnerController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(JYJbpmPartnerController.class);
    @Autowired
    @Qualifier(ConstantJBPM.PARTNER_TYPE_5_BEAN_ID)
    private JYSelectPartnerServiceImpl service;
    @Autowired
    private SessionAPI sessionAPI;
    @Autowired
	private SysRoleAPI sysRoleAPI;
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}") 
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException{
        ModelAndView model = new ModelAndView();
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("forward:/lbTReconsideration/prepareExecute/toQueryPage");
        }
        return model;
    }
    
    /**
     * 选择任务 参与者
     * @param request
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/selectJbpmJYPartner")
    @ResponseBody
    public DataMsg selectJbpmJYPartner(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
        try {
        	//获取当前登录的用户信息
        	UserInfo userinfo = sessionAPI.getCurrentUserInfo();
        	String myTurn = this.getParameterString("myTurn");//工作 按钮名称 路径名称
        	String otherParams = this.getParameterString("otherParams");//节点配置的 角色 code 信息
        	String partType = this.getParameterString("partType");//配置 参与者类型 0 ：角色，1：机构，2：人员
        	String formId = this.getParameterString("formId");//配置 表 主键ID
        	
        	String parUserNo = this.getParameterString("parUserNo");//页面查询条件
        	String parRealName = this.getParameterString("parRealName");//页面查询条件
        	
        	String participantType = this.getParameterString("participantType");//选人规则类型 
        	String participantRule = this.getParameterString("participantRule");//获取 扩展的 选人java 类 
        	String taskId = this.getParameterString("taskId");//工作 流 待办任务ID
        	String turnDirection = this.getParameterString("turnDirection");//工作流 流程走向
        	String acitityName = this.getParameterString("acitityName");//工作流  节点名称
        	String bizTabName = this.getParameterString("bizTabName");//工作 按钮名称 路径名称
        	String proNextActName = this.getParameterString("proNextActName");//工作 按钮名称 路径名称
        	
        	String bizInfId = request.getParameter("bizInfId");
        	String processInsId = request.getParameter("processInsId");
        	String bizTabId = request.getParameter("bizTabId");
        	String proInsParam = request.getParameter("proInsParam");//流程相关 参数信息 
        	
        	Map<String,Object> proInsMap = new HashMap<String,Object>();
        	Map<String, Object> otherMap = JbpmTools.getInstance().makeupDynamicMap(proInsParam);//动态编译 
        	proInsMap.putAll(otherMap);//放入工作流 业务变量中 
        	
        	proInsMap.put("partType", partType);
        	proInsMap.put("formId", formId);
        	proInsMap.put("roleCode", otherParams);
        	proInsMap.put("participantRule", participantRule);
        	proInsMap.put("myTurn", myTurn);
        	proInsMap.put("turnDirection", turnDirection);
        	proInsMap.put("participantType", participantType);
        	proInsMap.put("taskId", taskId);
        	proInsMap.put("proNextActName", proNextActName);
        	
        	//页面查询条件
        	proInsMap.put("parUserNo", parUserNo);
        	proInsMap.put("parRealName", parRealName);
        	
        	List<JYPartnerDTO> partnerList = service.mutiSelectPartnerByOperator(bizInfId, bizTabName, bizTabId, processInsId, acitityName, proInsMap);
        	
        	if(partnerList == null || partnerList.size() ==0){
        		partnerList = new ArrayList<JYPartnerDTO>();
        		JYPartnerDTO partner = new JYPartnerDTO();
        		partner.setParUserId("-1");
        		partner.setParUserNo("-1");
        		partner.setParRealName("-1");
        		partnerList.add(partner);
        	}
        	//按条件进行过滤
        	if(StringUtils.isNotEmpty(parUserNo)){
        		for(int i = partnerList.size() -1;i > -1 ;i --){
        			JYPartnerDTO tem = partnerList.get(i);
        			if(!tem.getParUserNo().contains(parUserNo))
        				partnerList.remove(i);
        		}
        	}
        	if(StringUtils.isNotEmpty(parRealName)){
        		for(int i = partnerList.size() -1;i > -1 ;i --){
        			JYPartnerDTO tem = partnerList.get(i);
        			if(!tem.getParRealName().contains(parRealName))
        				partnerList.remove(i);
        		}
        	}
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setData(partnerList);
			dataMsg.setTotalRows(partnerList.size());
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法selectJbpmJYPartner异常：", e);
        }
        return dataMsg;
    }
    
    /**
     * 选择任务 角色
     * @param request
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/selectJbpmJYRole")
    @ResponseBody
    public DataMsg selectJbpmJYRole(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
        try {
        	String roleCode = request.getParameter("roleCode");
        	String roleName = request.getParameter("roleName");
        	String formInfoId = request.getParameter("formInfoId");//jbpm4_form_id 的主键ID
        	Map<String, Object> param = new HashMap<String, Object>();
    		param.put("roleType", ConstantJBPM.JBPM_ROLE_TYPE);//只查询 工作流角色 调整 为 角色组的 概念
    		if(roleCode != null && !"".equals(roleCode)) {
    			param.put("roleCode", roleCode);
    			param.put("roleGroupCode", roleCode);
    		}
    		if(roleName != null && !"".equals(roleName)) {
    			param.put("roleName", roleName);
    			param.put("roleGroupName", roleName);
    		}
    		
        	List<Map<String, Object>> list = sysRoleAPI.queryRoleGroupList(0, 1, param);;//sysRoleAPI.queryRoleList(0, 1, param);
        	//追加 属性是否 选中
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setData(list);
			dataMsg.setTotalRows(list.size());
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法selectJbpmJYRole异常：", e);
        }
        return dataMsg;
    }
}
