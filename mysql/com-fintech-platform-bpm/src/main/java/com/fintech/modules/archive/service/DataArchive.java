package com.fintech.modules.archive.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.platform.api.sysconfig.SysConfigAPI;


/**
 * 
 * @author
 *
 */
public abstract class DataArchive implements archive {

	protected List<DataArchive> dealList=new ArrayList<DataArchive>();
	protected Object getBean(String name){
		ApplicationContext context = null;
		try{
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
			ServletContext servletContext = webApplicationContext.getServletContext();
			context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		}catch(Exception e){
			context = SysConfigAPI.getApplicationContext();
		}
		return  context.getBean(name);
	}
	
}
