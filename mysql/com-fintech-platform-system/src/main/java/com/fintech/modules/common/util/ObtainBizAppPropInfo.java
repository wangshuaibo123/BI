package com.fintech.modules.common.util;

import java.util.ResourceBundle;
/**
 * 获取 *.properties配置文件内容
 * @author
 *
 */
public class ObtainBizAppPropInfo {
	/**
	 * 
	 */
	private static ResourceBundle ssoBundle = ResourceBundle.getBundle("biz_app");
	/**
	 * 通過 key 获取value值
	 * @param key
	 * @return
	 */
    public static String getValByKey(String key) {
        String value = null;
        if(key != null && !"".equals(key.trim())) {
            value = ssoBundle.getString(key.trim());
        }
        return value;
    }
}
