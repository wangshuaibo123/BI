package com.fintech.modules.jbpm4.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.common.JYLoggerUtil;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.jbpm4.service.IJbpmService;
import com.fintech.platform.jbpm4.service.JbpmTastService;
import com.fintech.platform.jbpm4.tool.ConstantJBPM;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.exception.ErrorException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * 
 * @Description 执行工作流的 任务 信息
 * @author
 * @date 2014年9月24日 下午2:49:13
 * @version V1.0
 */
@Controller
@SuppressWarnings("all")
@Scope("prototype")
@RequestMapping("/workFlowTask")
public class WorkflowTaskController extends BaseController{
	//获取 访问业务层的对象
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.JbpmTaskServceImpl")
	private JbpmTastService taskService;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.JbpmServiceImpl")
	private IJbpmService bpmService;
	
	@RequestMapping(value="/execute.do")
	@ResponseBody
	public ModelAndView execute(HttpServletRequest requ, HttpServletResponse response) throws AbaboonException {
		ModelAndView  mav = new ModelAndView();
		//获取参数 判断调用对应 private method
		String operateData = this.getParameterString("operateData");
		
		String resultMsg = "";
		try {
			
			if("batchUpdateAssignee".equals(operateData)){
				resultMsg = this.batchUpdateAssignee();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ErrorException(e.getMessage());
		}
		
		mav.addObject("msg", resultMsg);
		mav.setViewName("forward:/component/jbpm/updateAssignee.jsp");
		this.outWriteString(response, resultMsg);
		return null;
	}
	/**
	 * 批量 修改 待办任务的处理人信息（任务转移）
	 * @return
	 * @throws Exception
	 */
	private  String batchUpdateAssignee() throws Exception{
		//start 计算 deployProcess 执行时间
		JYLoggerUtil.logCurrentTime("batchUpdateAssignee.do", true, this.getClass());
		String msg = ConstantJBPM.OPERATE_SUCCESS;
		String alertMsg = "修改成功";
		//接受待办任务的人员
		String toUserId = this.getParameterString("toUserId");
		String tasks = this.getParameterString("tasks");
		//需要转移的待办任务ID
		List<String> taskIds = new ArrayList<String>();
		if(StringUtils.isNotEmpty(tasks)){
			String[] ts = tasks.split(",");
			for(String str: ts){
				String taskId = str;
				taskIds.add(taskId);
			}
		}
		//通过session 获取既可 系统登录人员
		ApplicationContext context = null;
		try{
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
			ServletContext servletContext = webApplicationContext.getServletContext();
			context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		}catch(Exception e){
			context = SysConfigAPI.getApplicationContext();
		}
		SessionAPI sessionAPI =(SessionAPI) context.getBean("sessionAPI");
		try {
			UserInfo userinfo = sessionAPI.getCurrentUserInfo();
			String operateId = userinfo.getUserId().toString();
			//调用 业务逻辑 批量处理
			taskService.batchUpdateAssignee(taskIds, toUserId, operateId);
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.OPERATE_FAILED;
			alertMsg = msg+e.getMessage();
		}
		
		//end 计算 deployProcess 执行时间
		JYLoggerUtil.logCurrentTime("batchUpdateAssignee.do", false, this.getClass());
		
		return msg;
	}
	
	 /**
     * 写 字符串 至前台页面
     * @param response
     * @param resultMsg
     * @throws IOException 
     */
    private void outWriteString( HttpServletResponse response,String resultMsg){
   			response.setContentType("text/Xml;charset=utf-8");
			try {
				Writer out = response.getWriter();
				//写数据至页面
	            out.write(resultMsg);
	            out.flush();
	            out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
    /*
     * 更新 待办任务表 的字段
     * 
     */
    @RequestMapping(value = "/updateDataLock")
    @ResponseBody
    public DataMsg updateDataLock(HttpServletRequest requst , @ModelAttribute DataMsg dataMsg){
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	String state = (String) this.getParameter("state");
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			taskService.updateDataLockByPrimaryKey(dto,ids,state);
			 dataMsg.setMsg("操作成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法updateDataLock异常：", e);
		}
		return dataMsg;
    }
    /**
     * 2014-11-3 13:48:30 chj 流程监控 批量挂起，批量恢复 数据
     * @param request
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/batchUpdateDataState")
    @ResponseBody
    public DataMsg batchUpdateDataState(HttpServletRequest requst,@ModelAttribute DataMsg dataMsg){
    	BaseDTO dto = super.initDto(null);
    	String ids = (String) this.getParameterString("ids");
    	String state = (String) this.getParameter("state");
    	dataMsg = super.initDataMsg(dataMsg);
    	try {
    		taskService.batchUpdateStateByIds(dto, ids, state);
			dataMsg.setMsg("操作成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法batchUpdateDataState异常", e);
		}
		return dataMsg;
    }
    /**
     * 回退待办任务 新的待办任务参与者 为该节点的 历史最后一个 参与者
     * @param requst
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/backTaskToDesc.do")
    @ResponseBody
    public DataMsg backTaskToDesc(HttpServletRequest requst , @ModelAttribute DataMsg dataMsg){
    	String msg = ConstantJBPM.OPERATE_SUCCESS;
    	BaseDTO dto = super.initDto(null);
   	 	String taskId = this.getParameterString("taskId");
   	 	String destName = this.getParameterString("turnDirection");
   	 	String processInsId = this.getParameterString("processInsId");
   	 	String oldUserId = this.getParameterString("oldUserId");//回退目标节点的 历史任务处理人
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			Map<String,Object> variables = new HashMap<String,Object>();
			//通过 动态绘制 来达到特殊 快速处理 暂时只支持同一个流程内。
			String str = bpmService.singleBackWay(taskId, destName, processInsId,variables);
			
			//1、通过流程实例ID 获取 其 名下 为执行的待办任务ID
			Map<String,String> lastTaskInfo = taskService.getTaskInfoByProInsId(processInsId); 
			String newTaskId = lastTaskInfo.get("TASK_ID");
			String toUserId = oldUserId;
			
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
			ServletContext servletContext = webApplicationContext.getServletContext();
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			SessionAPI sessionAPI =(SessionAPI) context.getBean("sessionAPI");
			//通过session 获取既可 系统登录人员
			UserInfo userinfo = sessionAPI.getCurrentUserInfo();
			String operateId = userinfo.getUserId().toString();
			//2、更新 待办任务处理人员信息
			List<String> newTaskIds = new ArrayList<String>();
			newTaskIds.add(newTaskId);
			taskService.batchUpdateAssignee(newTaskIds, toUserId, operateId);
			dataMsg.setMsg("操作成功");
		} catch (Exception e) {
			dataMsg.failed("操作失败，"+e.getMessage());
			logger.error("执行方法backTaskToDesc异常：", e);
		}
		return dataMsg;
    }

}
