package com.jy.modules.platform.jbpm.eventlistener;

import javax.servlet.ServletContext;

import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jy.modules.platform.jbpm.dto.LeaveDemoInfoDTO;
import com.jy.modules.platform.jbpm.service.LeaveDemoInfoService;

/**
 * @description 请假流程审批通过 修改请假表状态
 * @author chizhaoxian
 * @Date 2015年1月28日下午3:10:11
 */
@Component("com.jy.modules.platform.jbpm.eventlistener.LeaveAuditPassEL")
public class LeaveAuditPassEL implements EventListener{

    private static final long serialVersionUID = 7699406829603137598L;

    @Override
    public void notify(EventListenerExecution execution) throws Exception {
        //请假表主键
        String proBizInfId = (String) execution.getVariable("proBizInfId");
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        ServletContext servletContext = webApplicationContext.getServletContext();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        LeaveDemoInfoService leaveDemoInfoService = (LeaveDemoInfoService)context.getBean("com.jy.modules.platform.jbpm.service.LeaveDemoInfoService");
        LeaveDemoInfoDTO dto = new LeaveDemoInfoDTO();
        dto.setId(Long.valueOf(proBizInfId));
        dto.setAppState("1");
        leaveDemoInfoService.updateLeaveState(dto);
    }

}
