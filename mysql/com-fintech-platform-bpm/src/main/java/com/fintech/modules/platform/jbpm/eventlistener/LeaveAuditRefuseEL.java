package com.fintech.modules.platform.jbpm.eventlistener;

import javax.servlet.ServletContext;

import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.modules.platform.jbpm.dto.LeaveDemoInfoDTO;
import com.fintech.modules.platform.jbpm.service.LeaveDemoInfoService;
import com.fintech.platform.api.sysconfig.SysConfigAPI;

/**
 * @description 请假流程审批通过 修改请假表状态
 * @author
 * @Date 2015年1月28日下午3:10:11
 */
@Component("com.fintech.modules.platform.jbpm.eventlistener.LeaveAuditRefuseEL")
public class LeaveAuditRefuseEL implements EventListener{

    private static final long serialVersionUID = 7699406829603137598L;

    @Override
    public void notify(EventListenerExecution execution) throws Exception {
        //请假表主键
        String proBizInfId = (String) execution.getVariable("proBizInfId");
        ApplicationContext context = null;
		try{
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
			ServletContext servletContext = webApplicationContext.getServletContext();
			context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		}catch(Exception e){
			context = SysConfigAPI.getApplicationContext();
		}
        LeaveDemoInfoService leaveDemoInfoService = (LeaveDemoInfoService)context.getBean("com.fintech.modules.platform.jbpm.service.LeaveDemoInfoService");
        LeaveDemoInfoDTO dto = new LeaveDemoInfoDTO();
        dto.setId(Long.valueOf(proBizInfId));
        dto.setAppState("0");
        leaveDemoInfoService.updateLeaveState(dto);
    }

}
