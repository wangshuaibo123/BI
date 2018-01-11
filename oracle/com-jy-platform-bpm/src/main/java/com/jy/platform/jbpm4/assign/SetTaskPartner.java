package com.jy.platform.jbpm4.assign;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * 
 * @Description: 动态设置 节点 的参与者
 * @author chen
 * @version 1.0, 
 * @date 2013-10-24 下午12:35:16
 */
@SuppressWarnings("all")
public class SetTaskPartner implements AssignmentHandler{
	private static final long serialVersionUID = 1L;
	//动态设计 该节点的参与者
	public void assign(Assignable assignable,OpenExecution execution) throws Exception {
			String assignee = (String)execution.getVariable("owner");
			TaskImpl task = (TaskImpl) assignable;
			String curActName = task.getActivityName();
			String taskId = task.getId();
			//JYLoggerUtil.error(this.getClass(), "-------------------assignee:"+assignee);
			
			String proTaskId = (String)execution.getVariable("proTaskId");//从流程变量中 获取当前的 任务的taskId 
			String proProcessInsId = (String)execution.getVariable("proProcessInsId");//从流程变量中 获取当前的 流程实例ID
			String proAcitityName = (String)execution.getVariable("proAcitityName");//从流程变量中 获取当前的 活动节点名称
			String proBizTabId = (String)execution.getVariable("proBizTabId");//从流程变量中 获取当前的 jbpm4_biz_tab 的主键ID
			String proBizTabName = (String)execution.getVariable("proBizTabName");//从流程变量中 获取当前的 业务表 名称
			String proBizInfId = (String)execution.getVariable("proBizInfId");//从流程变量中 获取当前的 业务表对应的主键ID
			
			String beanId = "";
			if(StringUtils.isNotEmpty(beanId)){
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				ServletContext servletContext = webApplicationContext.getServletContext();
				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
				//JYSelectPartnerService partnerService = (JYSelectPartnerService)context.getBean(beanId);
				
				String owner = "2";
				//assignable.setAssignee(owner);
			}else{
				//不进行处理
			}
		   
		}
}