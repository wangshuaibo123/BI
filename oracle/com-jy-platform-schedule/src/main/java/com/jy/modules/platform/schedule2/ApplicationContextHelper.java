package com.jy.modules.platform.schedule2;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHelper implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	private static Map<String, String> config;

	@Override
	public void setApplicationContext(ApplicationContext aContext)
			throws BeansException {
	    ApplicationContextHelper.applicationContext = aContext;
	}

	/**
	 * 从spring容器中，根据name获取指定的bean
	 * @param name bean的名字
	 * @return bean实例
	 */
	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	/**
	 * 从spring容器中，根据类型获取指定的bean
	 * @param requiredType 指定要获取的类型
	 * @return bean实例
	 */
	public static <T> T getBean(Class<T> requiredType) {
	    return applicationContext.getBean(requiredType);
	}
	
	/**
	 * 设置配置setter方法，spring注入使用
	 * @param config
	 */
	public void setConfig(Map<String, String> config) {
	    ApplicationContextHelper.config = config;
	}
	
	/**
	 * 根据key获取配置信息
	 * @param key 配置名称
	 * @return 配置值，没有配置的话返回null
	 */
	public static String getConfig(String key) {
	    if (config == null) {
	        return null;
	    } else {
	        return config.get(key);
	    }
	}
}
