package com.jy.platform.restservice.web.base;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * 
 * @description:定义 表单绑定 Long 类型的转换
 * @author chen_gang
 * @date:2014年10月13日下午5:57:54
 */
public class LongEditor extends PropertiesEditor{

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(text == null || "".equals(text.trim())){
			text = "0";
		}
		setValue(Long.parseLong(text));
	}

	@Override
	 public String getAsText() {  
        return getValue().toString();  
    } 

}
