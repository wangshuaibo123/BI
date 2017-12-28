package com.jy.modules.tools.pwd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 用于对spring配置文件中读取的属性配置做转换操作
 * 目前主要是解密
 * @author sunli
 * @date: 2016年5月19日 
 */
public class ConvertPropertyConfigurer extends PropertyPlaceholderConfigurer{
	private static final Logger logger =  LoggerFactory.getLogger(ConvertPropertyConfigurer.class);
	private String key;//密匙
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	
	/**
	 * 对所有encrypt.开头的属性做解密操作
	 */
	@Override  
    protected String convertProperty(String propertyName, String propertyValue){  
        //属性以encrypt.开头
		if(propertyName!=null && !"".equals(propertyName) && propertyName.startsWith("encrypt.")){
			return DESUtil.getDecryptString(propertyValue , key);
		}
		
        return propertyValue;  
    }  

}
