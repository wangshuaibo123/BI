package com.fintech.modules.jbpm4.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bsh.EvalError;
import bsh.Interpreter;

import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.common.JYLoggerUtil;
import com.fintech.platform.jbpm4.dto.JbpmDTO;
import com.fintech.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;
import com.fintech.platform.jbpm4.jbpm4FormInfo.service.IJbpm4FormInfoService;
import com.fintech.platform.jbpm4.service.IJbpmService;
import com.fintech.platform.jbpm4.service.JbpmTastService;
import com.fintech.platform.jbpm4.temporaryJbpm4Info.dto.TemporaryJbpm4InfoDTO;
import com.fintech.platform.jbpm4.temporaryJbpm4Info.service.ITemporaryJbpm4InfoService;
import com.fintech.platform.jbpm4.tool.ConstantJBPM;
import com.fintech.platform.jbpm4.tool.StringUtilTools;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.exception.ValidationException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * 
 * @Description: 工作流相关controller 
 * 仅 设计流程、 部署流程、流程监控使用该 控制层
 * @author
 * @version 1.0, 
 * @date 2013-10-21 上午09:56:35
 */
@Controller
@Scope("prototype")
@SuppressWarnings("all")
public class WorkflowController extends BaseController{
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.JbpmServiceImpl")
	private IJbpmService JbpmService;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.temporaryJbpm4Info.service.impl.TemporaryJbpm4InfoServiceImpl")
	private ITemporaryJbpm4InfoService tempService;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.jbpm4FormInfo.service.impl.Jbpm4FormInfoServiceImpl")
	private IJbpm4FormInfoService formService;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.JbpmTaskServceImpl")
	private JbpmTastService taskService;
	
	/**
     * 通过 流程实例ID 获取 异常终止 流程实例ID
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
     @RequestMapping(value="/exceptionStopByProInsId.do")
    	public ModelAndView exceptionStopByProInsId(HttpServletRequest requ, HttpServletResponse response) throws Exception{
     		String proInsId = requ.getParameter("proInsId");//流程实例 id
     		String exceptionRemark = requ.getParameter("exceptionRemark");
     		
     		taskService.exceptionStoProIns(proInsId, exceptionRemark);
     		String resultMsg = "操作成功";
     		this.outWriteString(response, resultMsg);
    		return null;
    	}
	/**
	 * 发布流程 ajax 调用
	 * 1.先保存最新的流程信息，再发布
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deployProcess.do")
	public ModelAndView deployProcess(HttpServletRequest requ, HttpServletResponse response) throws Exception{
		//start 计算 deployProcess 执行时间
		JYLoggerUtil.logCurrentTime("deployProcess.do", true, this.getClass());
		
		String msg = ConstantJBPM.ADD_SUCESS;
		//1.先保存需要发布的信息。
		TemporaryJbpm4InfoDTO dto = new TemporaryJbpm4InfoDTO();
		//当前登录系统的用户id
		String userId = String.valueOf(requ.getSession().getAttribute("userId"));
		//dto.setXmlContent(requ.getParameter("xml"));
		dto.setXmlContent(StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"xml")));
		
		dto.setProcName(StringUtilTools.filterSpecial(requ,"processName"));
		dto.setProcVersion(StringUtilTools.filterSpecial(requ,"procVersion"));
		
		dto.setProcCode(StringUtilTools.filterSpecial(requ,"procCode"));
		dto.setBizFile(StringUtilTools.filterSpecial(requ,"bizFile"));
		dto.setRemark(StringUtilTools.filterSpecial(requ,"remark"));
		//记录操作数据的人员id
		//dto.setUserIdOfOperateData(userId);
		String alertMsg = "发布成功";
		
		//用来判断是否在web流程设计器发布的流程 
		String lastId = StringUtilTools.filterSpecial(requ,"tempJpmbInfoId");
		
		try {
			String deployId = JbpmService.deployProcess(dto, lastId);
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.ADD_FAILED;
			alertMsg = msg+e.getMessage();
		}
		
		this.outWriteString(response, alertMsg);
		//end 计算 deployProcess 执行时间
		JYLoggerUtil.logCurrentTime("deployProcess.do", false, this.getClass());
		
		return null;
	}
	
	
	@RequestMapping(value="/deleteTempJbpm4Info.do")
	public ModelAndView deleteTempJbpm4Info(HttpServletRequest requ, HttpServletResponse response) throws Exception{
		String msg = ConstantJBPM.DELETE_SUCCESS;
		
		//用来判断是否在web流程设计器发布的流程 
		String ids = StringUtilTools.filterSpecial(requ,"ids");
		
		try {
			tempService.deleteTemporaryJbpm4Info(ids);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.DELETE_FAILED;
			msg = msg+e.getMessage();
		}
		
		this.outWriteString(response, msg);
		
		return null;
	}
	/**
	 * 取消发布流程 ajax 调用
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cancelDeployProcess.do")
	public ModelAndView cancelDeployProcess(HttpServletRequest requ, HttpServletResponse response) throws Exception{
		String msg = ConstantJBPM.ADD_SUCESS;
		//当前登录系统的用户id
		String userId = String.valueOf(requ.getSession().getAttribute("userId"));
		String deploymentId = StringUtilTools.filterSpecial(requ,"deploymentId");
		try {
		
			JbpmService.cancelPublishProcess(deploymentId);
			
			response.setContentType("text/Xml;charset=utf-8");
			Writer out = response.getWriter();
			out.write("成功取消发布");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.ADD_FAILED;
		}
		
		return null;
		
	}
	
	/**
	 * 流程监控中 发起流程 ajax 调用
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/startProcess.do")
	public ModelAndView startProcess(HttpServletRequest requ, HttpServletResponse response,JbpmDTO dto) throws Exception{
		response.setContentType("text/Xml;charset=utf-8");
		Writer out = response.getWriter();
		String msg = ConstantJBPM.OPERATE_SUCCESS;
		//当前登录系统的用户id
		String userId = String.valueOf(requ.getSession().getAttribute("userId"));
		//获取需要发起的流程编码 相同编码情况下，会取版本高的流程定义
		String proKey = StringUtilTools.filterSpecial(requ,"dto.processKey");
		try {
			if(StringUtils.isNotEmpty(proKey)){
				//通过 页面传输代码 来控制流程
				String otherParam = StringUtilTools.filterSpecial(requ,"otherParamJavaCode");
				
				otherParam = makeupOtherParamInfo(otherParam,dto);
				
				//定义启动流程时所需参数 信息
				Map<String,Object> variables = makeupDynamicMapParams(otherParam);
				
				//启动流程 返回流程实例
				ProcessInstance proIns = JbpmService.startProcessInstanceByKey(proKey, variables);
				
				JYLoggerUtil.info(this.getClass(), "-------新发起的流程实例id-------------"+proIns.getId());
				
				out.write("成功发起流程");
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.OPERATE_FAILED;
			
			out.write("发起流程失败！！！");
		}
		out.flush();
		
		return null;
	}
	
	
	/**
	 * 流程监控中 发起流程 ajax 调用
	 * 模拟发起 业务流程
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/startNewBizProcess.do")
	public ModelAndView startNewBizProcess(HttpServletRequest requ, HttpServletResponse response,JbpmDTO dto) throws Exception{
		response.setContentType("text/Xml;charset=utf-8");
		Writer out = response.getWriter();
		String msg = ConstantJBPM.OPERATE_SUCCESS;
		//获取需要发起的流程编码 相同编码情况下，会取版本高的流程定义
		String proKey = StringUtilTools.filterSpecial(requ,"dto.processKey");
		try {
			if(StringUtils.isNotEmpty(proKey)){
				BaseDTO baseDto = this.initDto(null);
				//通过 页面传输代码 来控制流程
				String otherParam = StringUtilTools.filterSpecial(requ,"otherParamJavaCode");
				otherParam = makeupOtherParamInfo(otherParam,dto);
				//定义启动流程时所需参数 信息
				Map<String,Object> variables = makeupDynamicMapParams(otherParam);
				String bizTabName = "jbpm4_biz_tab";
				String bizInfId = "1";
				String bizType = "1";
				String bizInfName ="我的业务任务名称";
				
				String orgId = baseDto.getUserOrgId().toString();
				String startProUserid = baseDto.getOpUserId().toString();
				String remark="remark";
				
				//启动流程 返回jbpm4_biz_tab 主键ID
				String id = taskService.startProIns(proKey, variables, 
						bizTabName, bizInfId, bizType, bizInfName, 
					startProUserid, orgId, remark);
				JYLoggerUtil.info(this.getClass(), "-------新发起的流程实例id-------------jbpm4_biz_tab.id:"+id);
				out.write("成功模拟业务发起流程");
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.OPERATE_FAILED;
			out.write("模拟业务发起流程失败！！！");
		}
		out.flush();
		return null;
	}
	
	
	/**
	 * 完成待办任务 通过提交form表单来完成
	 * 只有普通 执行（处理待办任务）操作才可以有会签 ，
	 * 快速处理，跨流程处理，回退暂时不考虑会签操作
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/executeTask.do")
	public ModelAndView executeTask(HttpServletRequest requ, HttpServletResponse response,JbpmDTO dto) {
		String msg = ConstantJBPM.OPERATE_SUCCESS;
		//计算某个方法耗时时间 方法开始前
		JYLoggerUtil.logCurrentTime("executeTask",true,getClass());
		
		//当前登录系统的用户id
		String userId = String.valueOf(requ.getSession().getAttribute("userId"));
		//待办任务的id 
		String taskId = dto.getTaskId();
		//验证当前人执行 该待办任务的权限。
		
		//流程流转方向
		String turnDir = dto.getTurnDirection();
		//turnDir = "to end ";
		try {
			//通过 页面传输代码 来控制流程
			String otherParam = StringUtilTools.filterSpecial(requ,"otherParamJavaCode");
			
			otherParam = makeupOtherParamInfo(otherParam,dto);

			//定义启动流程时所需参数 信息
			Map<String,Object> variables = makeupDynamicMapParams(otherParam);
			
			//如果 turnDir 不为null 则将 turnDir 放入 result 以便 子流程结束时，选择性的流转到主流程中
			if(StringUtils.isNotEmpty(turnDir)){
				variables.put("result", turnDir);
			}else{
				variables.put("result", "");
			}
			
			
			if(StringUtils.isNotEmpty(taskId)){
				JbpmService.completeTask(taskId,turnDir,variables);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.OPERATE_FAILED;
			
		}

		
		String url = "forward:/component/jbpm/executeTask.jsp";
		Map<String,Object> model= new HashMap<String, Object>();
		model.put(ConstantJBPM.MSG, msg);
		
		//计算某个方法耗时时间 方法执行完后
		JYLoggerUtil.logCurrentTime("executeTask",false,getClass());
		
		//return new ModelAndView(url,model);
		JYLoggerUtil.info(this.getClass(), "--------------执行待办任务："+msg+",任务ID:"+taskId);
		
		// ajax提交form 表单
		try {
			this.outWriteString(response, msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 完成待办任务 通过提交form表单来完成
	 * 只有普通 执行（处理待办任务）操作才可以有会签 ，
	 * 快速处理，跨流程处理，回退暂时不考虑会签操作
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/executeTask2.do")
	public ModelAndView executeTask2(HttpServletRequest requ, HttpServletResponse response,JbpmDTO dto) throws AbaboonException{
		String msg = ConstantJBPM.OPERATE_SUCCESS;
		
		String recallURI = requ.getParameter("V_RecallURI");
		//计算某个方法耗时时间 方法开始前
		JYLoggerUtil.logCurrentTime("executeTask",true,getClass());
		
		//当前登录系统的用户id
		String userId = String.valueOf(requ.getSession().getAttribute("userId"));
		//待办任务的id 
		String taskId = dto.getTaskId();
		//验证当前人执行 该待办任务的权限。
		
		//流程流转方向
		String turnDir = dto.getTurnDirection();
		//turnDir = "to end ";
		try {
			//通过 页面传输代码 来控制流程
			String otherParam = StringUtilTools.filterSpecial(requ,"otherParamJavaCode");
			
			otherParam = makeupOtherParamInfo(otherParam,dto);

			//定义启动流程时所需参数 信息
			Map<String,Object> variables = makeupDynamicMapParams(otherParam);
			
			//如果 turnDir 不为null 则将 turnDir 放入 result 以便 子流程结束时，选择性的流转到主流程中
			if(StringUtils.isNotEmpty(turnDir)){
				variables.put("result", turnDir);
			}else{
				variables.put("result", "");
			}
			
			
			if(StringUtils.isNotEmpty(taskId)){
				JbpmService.completeTask(taskId,turnDir,variables);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.OPERATE_FAILED;
			
		}

		
		String url = "forward:/component/jbpm/executeTask.jsp";
		Map<String,Object> model= new HashMap<String, Object>();
		model.put(ConstantJBPM.MSG, msg);
		
		//计算某个方法耗时时间 方法执行完后
		JYLoggerUtil.logCurrentTime("executeTask",false,getClass());
		
		//return new ModelAndView(url,model);
		JYLoggerUtil.info(this.getClass(), "--------------执行待办任务："+msg+",任务ID:"+taskId);
		
		throw new ValidationException("my error.");
		/*// ajax提交form 表单
		try {
			this.outWriteString(response, msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;*/
	}
	/**
	 * 组装 动态参数信息
	 * @param otherParam
	 * @param dto
	 * @return
	 */
	private String makeupOtherParamInfo(String otherParam, JbpmDTO dto) {
		//动态处理 otherParam 参数信息 获取参与者
		String partnerId = dto.getParamUserId();
		if(StringUtils.isNotEmpty(partnerId)){
			//更改其他参数信息 工作流的参与者参数约定指定为 owner
			String myStr = "map.put(\"owner\",\""+partnerId+"\");return map;";
			
			String  myEnd = "System.out.println(map);";
			String myNewValue = otherParam.substring(0,otherParam.indexOf(myEnd));
			otherParam = myNewValue + myStr;
		}
		return otherParam;
	}
	/**
	 * 回退功能实现 和快速处理类似
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doBackTask.do")
	public ModelAndView doBackTask(HttpServletRequest requ, HttpServletResponse response,JbpmDTO dto) throws Exception{
		String msg = ConstantJBPM.OPERATE_SUCCESS;
		
		//当前登录系统的用户id
		String userId = String.valueOf(requ.getSession().getAttribute("userId"));
		//待办任务的id 
		String taskId = dto.getTaskId();
		//验证当前人执行 该待办任务的权限。
		
		//流程流转到的 期望目标
		String destName = dto.getTurnDirection();
		//流程实例id
		String processInstanceId = dto.getProcessInsId();
		try {
			//通过 页面传输代码 来控制流程
			String otherParam = StringUtilTools.filterSpecial(requ,"otherParamJavaCode");
			
			otherParam = makeupOtherParamInfo(otherParam,dto);
			
			Map<String,Object> variables = makeupDynamicMapParams(otherParam);
			//后续需要自动的查询出上一节点的历史处理人
			//并将该人放入流程实例的参数信息中
			
			//通过 动态绘制 来达到特殊 快速处理 暂时只支持同一个流程内。
			String str = JbpmService.singleBackWay(taskId, destName, processInstanceId,variables);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.OPERATE_FAILED;
			
		}
		
		String url = "component/jbpm/fastDoTask.jsp";
		
		Map<String,Object> model= new HashMap<String, Object>();
		
		model.put(ConstantJBPM.MSG, msg);
		
		return new ModelAndView(url,model);
	}
	
	/**
	 * 快速处理待办任务 通过提交form表单来完成
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/fastDoTask.do")
	public ModelAndView fastDoTask(HttpServletRequest requ, HttpServletResponse response,JbpmDTO dto) throws Exception{
		String msg = ConstantJBPM.OPERATE_SUCCESS;
		
		//当前登录系统的用户id
		String userId = String.valueOf(requ.getSession().getAttribute("userId"));
		//待办任务的id 
		String taskId = dto.getTaskId();
		//验证当前人执行 该待办任务的权限。
		
		
		//流程流转到的 期望目标
		String destName = dto.getTurnDirection();
		//流程实例id
		String processInstanceId = dto.getProcessInsId();
		try {
			//通过 页面传输代码 来控制流程
			String otherParam = StringUtilTools.filterSpecial(requ,"otherParamJavaCode");
			
			otherParam = makeupOtherParamInfo(otherParam,dto);
			
			Map<String,Object> variables = makeupDynamicMapParams(otherParam);
			
			
			
			//通过 动态绘制 来达到特殊 快速处理 暂时只支持同一个流程内。
			JbpmService.singleBackWay(taskId, destName, processInstanceId,variables);
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.OPERATE_FAILED;
			
		}
		
		String url = "component/jbpm/fastDoTask.jsp";
		
		Map<String,Object> model= new HashMap<String, Object>();
		
		model.put(ConstantJBPM.MSG, msg);
		
		return new ModelAndView(url,model);
	}
	
	
	
	/**
	 * 跨流程处理 由子流程1 快速送至子流程2中任意一个节点
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toOtherProTask.do")
	public ModelAndView toOtherProTask(HttpServletRequest requ, HttpServletResponse response,JbpmDTO dto) throws Exception{
		String msg = ConstantJBPM.OPERATE_SUCCESS;
		
		//当前登录系统的用户id
		String userId = String.valueOf(requ.getSession().getAttribute("userId"));
		//待办任务的id 
		String taskId = dto.getTaskId();
		//验证当前人执行 该待办任务的权限。
		
		//流程流转 到的 目标子流程key
		String destSubProKey = dto.getSubProKey();
		//主流程中 目标子流程的name
		String destSubProName = dto.getDestSubProName();
		
		//流程流转到的 子流程 期望目标
		String destName = dto.getTurnDirection();
		//当前子流程key
		String curSubProKey = dto.getCurSubProKey();
		//当前子流程 最后的节点
		String curDestName = dto.getCurTurnDirection();
		//主流程实例id 
		String mainProIns = dto.getMainProInsId();
		//流程实例id
		String processInstanceId = dto.getProcessInsId();
		try {
			//通过 页面传输代码 来控制流程
			String otherParam = StringUtilTools.filterSpecial(requ,"otherParamJavaCode");
			
			otherParam = makeupOtherParamInfo(otherParam,dto);
			
			Map<String,Object> variables = makeupDynamicMapParams(otherParam);
			
			//通过 动态绘制 来达到特殊 快速处理 暂时只支持同一个流程内。
			JbpmService.toOtherProTask(taskId, curSubProKey,curDestName,destSubProKey,destSubProName,destName, processInstanceId,mainProIns,variables);
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.OPERATE_FAILED;
			
		}
		
		String url = "component/jbpm/toOtherProcessTask.jsp";
		
		Map<String,Object> model= new HashMap<String, Object>();
		
		model.put(ConstantJBPM.MSG, msg);
		
		return new ModelAndView(url,model);
	}
	/**
	 * 替换已经发布的流程 ajax 调用
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/immediatelyUpdateProcess.do")
	public ModelAndView immediatelyUpdateProcess(HttpServletRequest requ, HttpServletResponse response) throws Exception{
		String msg = ConstantJBPM.ADD_SUCESS;
		//1.先保存需要发布的信息。
		TemporaryJbpm4InfoDTO dto = new TemporaryJbpm4InfoDTO();
		//当前登录系统的用户id
		String userId = String.valueOf(requ.getSession().getAttribute("userId"));
		//dto.setXmlContent(requ.getParameter("xml")StringUtilTools.filterSpecial(requ,"xml"));
		
		dto.setXmlContent(StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"xml")));
		dto.setProcName(StringUtilTools.filterSpecial(requ,"processName"));
		dto.setProcVersion(StringUtilTools.filterSpecial(requ,"procVersion"));
		
		dto.setProcCode(StringUtilTools.filterSpecial(requ,"procCode"));
		dto.setBizFile(StringUtilTools.filterSpecial(requ,"bizFile"));
		dto.setRemark(StringUtilTools.filterSpecial(requ,"remark"));
		//记录操作数据的人员id
		//dto.setUserIdOfOperateData(userId);
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("dto", dto);
		
		response.setContentType("text/Xml;charset=utf-8");
		Writer out = response.getWriter();
		String depMsg = "发布成功";
		try {
			
			JbpmService.updateJbpm4LobTableImmediately(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			depMsg = "立即生效失败，请尝试发布功能。"+e.getMessage();
		}
		out.write(depMsg);
		out.flush();
		
		return null;
		
	}
	
	
	
	/**
	 * 新增
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addTemporaryJbpm4Info.do")
	public String addTemporaryJbpm4Info(HttpServletRequest request,HttpServletResponse response, TemporaryJbpm4InfoDTO dto)throws Exception {
		JYLoggerUtil.logCurrentTime("addTemporaryJbpm4Info.do", true, this.getClass());
		String msg = ConstantJBPM.OPERATE_SUCCESS;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		dto.setXmlContent(request.getParameter("xml"));
		dto.setProcName(request.getParameter("processName"));
		dto.setProcVersion(request.getParameter("procVersion"));
		
		dto.setProcCode(request.getParameter("procCode"));
		dto.setBizFile(request.getParameter("bizFile"));
		
		dto.setLastUpdBy("1");
		paramMap.put("dto", dto);
		try {
			
			tempService.addTemporaryJbpm4Info(paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.OPERATE_FAILED;
		}
		
		Writer out = response.getWriter();
		out.write(msg);
		out.flush();
		
		JYLoggerUtil.logCurrentTime("addTemporaryJbpm4Info.do", false, this.getClass());
		return null;
	}
	
	/**
	 * 新增 流程节点 的表单及选人规则配置信息
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addJbpm4FormInfo.do")
	public String addJbpm4FormInfo(HttpServletRequest request,HttpServletResponse response, Jbpm4FormInfoDTO dto)throws Exception {
		JYLoggerUtil.logCurrentTime("addJbpm4FormInfo.do", true, this.getClass());
		String msg = ConstantJBPM.OPERATE_SUCCESS;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		
		//dto.setLastUpdBy("1");
		paramMap.put("dto", dto);
		try {
			
			formService.addJbpm4FormInfo(paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.OPERATE_FAILED;
		}
		
		Writer out = response.getWriter();
		out.write(msg);
		out.flush();
		
		JYLoggerUtil.logCurrentTime("addJbpm4FormInfo.do", false, this.getClass());
		return null;
	}
	/**
	 * 更新 流程节点 的表单及选人规则配置信息
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateJbpm4FormInfo.do")
	public String updateJbpm4FormInfo(HttpServletRequest request,HttpServletResponse response, Jbpm4FormInfoDTO dto)throws Exception {
		JYLoggerUtil.logCurrentTime("addJbpm4FormInfo.do", true, this.getClass());
		String msg = ConstantJBPM.OPERATE_SUCCESS;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if(! (ConstantJBPM.PARTNER_TYPE_3.equals(dto.getParticipatorType()) 
			|| ConstantJBPM.PARTNER_TYPE_5.equals(dto.getParticipatorType()))
		){
			dto.setPartType("");
			dto.setOtherParams("");//清空 类型编号 信息
		}else{
			//只有人工 选择参与者  才可以使用 人工选择类型 、 类型编号（otherParams）参数
		}
		
		//dto.setLastUpdBy("1");
		paramMap.put("dto", dto);
		try {
			
			formService.updateJbpm4FormInfo(paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = ConstantJBPM.OPERATE_FAILED;
		}
		
		Writer out = response.getWriter();
		out.write(msg);
		out.flush();
		
		JYLoggerUtil.logCurrentTime("addJbpm4FormInfo.do", false, this.getClass());
		return null;
	}
	
	private Map<String,Object> makeupDynamicMapParams(String mapParams) throws EvalError{
		//定义启动流程时所需参数 信息
		Map<String,Object> variables = new HashMap<String,Object>(); 
		
		//通过 页面传输代码 来控制流程
		String otherParam = mapParams;//StringUtilTools.filterSpecial(requ,"otherParamJavaCode");
		if(StringUtils.isNotEmpty(otherParam)){
			Interpreter it=new Interpreter();
			Object obj = null;
			//将传递过来的双引号去掉
			obj = it.eval(otherParam);
			variables.putAll((Map)obj);
		}

		//定义 子流程之间的跨流程处理 outcome="#{result}" 主流程中子流程连接为to 
		
		//if(variables.get("result") == null)
		//variables.put("result", "to");
		
		return variables;
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
   		   //写数据至页面
           out.write(resultMsg);
           out.flush();
           out.close();
    }
}
