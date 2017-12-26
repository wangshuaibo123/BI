package com.fintech.platform.restservice.web.base;

import java.math.BigDecimal;

import org.springframework.beans.propertyeditors.PropertiesEditor;
/**
 * 
 * @description:定义 表单绑定 BigDecimal 类型的转换
 * @author
 * @date:2015年01月14日下午5:57:54
 */
public class BigDecimalEditor extends PropertiesEditor{


	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(this.formatBigDecimalData(text));
	}

	@Override
	 public String getAsText() {  
        return getValue().toString();  
    } 
	
	public BigDecimal formatBigDecimalData(String text){
		if(text == null || "".equals(text.trim())){
			return null;
		}else{
			String finalStr=text.replaceAll("￥","").replaceAll(",", "");
			return  new BigDecimal(finalStr.trim());
		}
	}
}
