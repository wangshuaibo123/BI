package com.fintech.modules.boot.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.platform.api.sysconfig.SysConfigAPI;
/**
 * 自定义 ServletContextListener
 * 用于tomcat启动项目时获取 ApplicationContext
 * @author Administrator
 *
 */
public class MyContextListenerBean implements ServletContextListener{
    
    public MyContextListenerBean() {
        super();
    }
    
    public void contextInitialized(ServletContextEvent event) {
    	WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
    	SysConfigAPI.setApplicationContext(context);
    }

    public void contextDestroyed(ServletContextEvent event) {
    }
    
}
