package com.fintech.platform.restservice.web.base;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * 
 * @description:定义 表单绑定 Float 类型的转换
 * @author
 * @date:2014年10月13日下午5:57:54
 */
public class FloatEditor extends PropertiesEditor{

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(text == null || "".equals(text.trim())){
			text = "0";
		}
		setValue(Float.parseFloat(text));
	}

	@Override
	 public String getAsText() {  
        return getValue().toString();  
    } 

}
