package com.jy.platform.api.sysconfig;

import org.springframework.context.ApplicationContext;


public abstract class SysConfigAPI {
    public static ApplicationContext applicationContext;


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public static void setApplicationContext(ApplicationContext applicationContext) {
        SysConfigAPI.applicationContext = applicationContext;
    }
    
	/**
	 * 通过系统参数key获取value
	 * @param key
	 * @return
	 */
	public abstract String getValue(String key);
}
