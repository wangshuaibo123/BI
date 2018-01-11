package com.fintech.modules.platform.jbpm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.fintech.modules.platform.jbpm.dto.LeaveDemoInfoDTO;
import com.fintech.modules.platform.jbpm.service.LeaveDemoInfoService;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.jbpm4.dto.Jbpm4BizOptionInfoDTO;
import com.fintech.platform.jbpm4.tool.JbpmTools;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: LeaveDemoInfoController
 * @description: 定义  申请请假表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/dojbpm/leaveDemoInfo")
public class LeaveDemoInfoController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(LeaveDemoInfoController.class);
    @Autowired
	private SessionAPI sessionAPI;
    @Autowired
    @Qualifier("com.fintech.modules.platform.jbpm.service.LeaveDemoInfoService")
    private LeaveDemoInfoService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/jbpm/leavedemoinfo/queryLeaveDemoInfo");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/jbpm/leavedemoinfo/addLeaveDemoInfo");
        }else if("toSubProInfo".equals(operate)){//跳转至 确认提交申请 请假页面
        	String bizInfId = this.getParameterString("bizInfId");
        	model = this.queryOneDTO(bizInfId);
        	model.setViewName("platform/jbpm/leavedemoinfo/updateLeaveDemoInfo");
        }else if("toViewForWorkflow".equals(operate)){//跳转至 查看页面
        	String bizInfId = this.getParameterString("bizInfId");
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(bizInfId);
        	model.setViewName("platform/jbpm/leavedemoinfo/viewLeaveDemoInfo");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/jbpm/leavedemoinfo/viewLeaveDemoInfo");
        }else if("toSelectPersonInfo".equals(operate)){
        	model.setViewName("platform/jbpm/leavedemoinfo/selectPersonInfo");
        }
        
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-10-30 17:07:12
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListLeaveDemoInfo")
    @ResponseBody
    public DataMsg queryListLeaveDemoInfo(HttpServletRequest request, LeaveDemoInfoDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<LeaveDemoInfoDTO> list = service.searchLeaveDemoInfoByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    /**
     * 发起审批流程
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/startLeaveDemoInfo")
    @ResponseBody
    public DataMsg startLeaveDemoInfo(HttpServletRequest request, LeaveDemoInfoDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (LeaveDemoInfoDTO)super.initDto(dto);

			Map<String, Object> variables = new HashMap<String,Object>();
        	variables.put("owner", sessionAPI.getCurrentUserInfo().getUserId()+ "");
			
			Map<String,Object> bizMap = new HashMap<String,Object>();
			
			service.startProIns("", variables, dto, bizMap);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("成功发起流程，请到我的待办任务中处理");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法startLeaveDemoInfo异常：", e);
        }
        return dataMsg;
    }

    @RequestMapping(value = "/executeTastLeaveDemoInfo.do")
    @ResponseBody
    public DataMsg executeTastLeaveDemoInfo(HttpServletRequest request, LeaveDemoInfoDTO dto,Jbpm4BizOptionInfoDTO bizOption, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (LeaveDemoInfoDTO)super.initDto(dto);
        	
        	bizOption = (Jbpm4BizOptionInfoDTO)this.initDto(bizOption);
        	
        	String proInstanceId = this.getParameterString("processInsId");
        	String bizTabId = this.getParameterString("bizTabId");
        	bizOption.setProInstanceId(proInstanceId);//主流程实例ID
        	bizOption.setFkJbpmBizTabId(bizTabId);//jbpm4_biz_tab 主键ID
        	String isManager = this.getParameterString("isManager");
        	//任务ID todo 当前任务是否已经执行，并且是当前执行人的待办任务
    		String taskId = this.getParameterString("taskId");
    		//流程走向
    		String turnDir = this.getParameterString("turnDirection");
    		Map<String,Object> variables = new HashMap<String,Object>();//makeupDynamicMapParams(otherParam);
        	//动态编译 
        	String otherParamJavaCode = this.getParameterString("otherParamJavaCode");
        	Map<String, Object> otherMap = JbpmTools.getInstance().makeupDynamicMap(otherParamJavaCode);
        	//放入工作流 业务变量中 
        	variables.putAll(otherMap);
        	variables.put("isManager", isManager);
        	variables.put("day",dto.getAppDay());
			//存放 业务数据
        	Map<String,Object> bizMap = new HashMap<String,Object>();
        	bizMap.put("bizDTO", dto);
        	//节点意见信息
        	bizMap.put("bizOptionDTO", bizOption);
			//执行待办任务
        	service.completeTask(taskId, turnDir, variables,bizMap);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("待办任务执行成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed("执行失败，"+e.getMessage());
            logger.error("执行方法executeTastLeaveDemoInfo异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:删除
     * @date 2014-10-30 17:07:12
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteLeaveDemoInfo")
    @ResponseBody
    public DataMsg deleteLeaveDemoInfo(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteLeaveDemoInfoByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author
     * @description:通过主键查询 其明细信息
     * @date 2014-10-30 17:07:12
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	String taskId = this.getParameterString("taskId");
        	String processInsId = this.getParameterString("processInsId");
        	String acitityName = this.getParameterString("acitityName");
        	String bizTabName = this.getParameterString("bizTabName");
        	String bizTabId = this.getParameterString("bizTabId");
        	
        	model.addObject("taskId", taskId);
        	model.addObject("processInsId", processInsId);
        	model.addObject("acitityName", acitityName);
        	model.addObject("bizTabName", bizTabName);//业务主表名称
        	model.addObject("bizInfId", id);//业务主表主键ID
        	
        	model.addObject("bizTabId", bizTabId);//jbpm4_biz_tab 主键ID
        	LeaveDemoInfoDTO dto = service.queryLeaveDemoInfoByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
