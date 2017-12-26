package com.fintech.modules.common.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.platform.api.sysconfig.SysConfigAPI;

public class AppContextBean implements ServletContextListener
{
	  private static WebApplicationContext appContext;
	    
	    public AppContextBean() {
	        super();
	    }
	    public void contextInitialized(ServletContextEvent event) {
	    	appContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	    	//支持hibernate 高版本
	    	SysConfigAPI.setApplicationContext(appContext);
	    }

	    public void contextDestroyed(ServletContextEvent event) {
	    }
	    
	    public static ApplicationContext getApplicationContext() {
	        return appContext;
	    }
}
