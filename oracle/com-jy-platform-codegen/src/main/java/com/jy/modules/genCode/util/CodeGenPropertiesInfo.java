package com.jy.modules.genCode.util;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CodeGenPropertiesInfo {
    private static final Logger logger = LoggerFactory.getLogger(CodeGenPropertiesInfo.class);
	private static ResourceBundle ssoBundle = null;
	
	static{
	    try{
	        ssoBundle =  ResourceBundle.getBundle("codegen");
	    }
	    catch(Exception e){
	        logger.error("CodeGenPropertiesInfo error: ", e);
	    }
	}
	
	/**
	 * 通過 key 获取value值
	 * @param key
	 * @return
	 */
    public static String getValByKey(String key) {
        String value = null;
        if(ssoBundle!=null && key!=null && !"".equals(key.trim())) {
            value = ssoBundle.getString(key.trim());
        }
        return value;
    }
}
