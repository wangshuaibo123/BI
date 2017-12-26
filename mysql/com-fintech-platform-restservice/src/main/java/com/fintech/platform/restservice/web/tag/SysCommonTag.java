package com.fintech.platform.restservice.web.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.api.sysdict.SysCommonTagAPI;
/**
 * @description:定义实现SysCommonTagAPI 接口通用标签 
 * @author
 * @date: 2016年2月14日 下午1:57:45
 */
public class SysCommonTag extends TagSupport {
	private static Logger logger = LoggerFactory.getLogger(SysCommonTag.class);
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String defaultValue;
	private String onchange;
	private String className;
	private boolean hasBlank = true;
	private String language;
	private String type="select";
	private String extendProperty;
	private String emptyText="请选择";
	private	String codeType="";
	private String used = "";
	//实现类beanId
	private String beanID;

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		StringBuffer select = new StringBuffer();
		// options
		try {
			List<Map<String,String>> allList = null;
			List<Map<String,String>> list = null;
			
			ApplicationContext context = null;
			try{
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				ServletContext servletContext = webApplicationContext.getServletContext();
				context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			}catch(Exception e){
				context = SysConfigAPI.getApplicationContext();
			}
			SysCommonTagAPI commonTagAPI =null;
			if(StringUtils.isNotEmpty(this.getBeanID())){
				commonTagAPI = (SysCommonTagAPI)context.getBean(this.getBeanID());
			}else{
				//实例化通用默认的类
				commonTagAPI = (SysCommonTagAPI)context.getBean("com.pt.modules.platform.sysdict.impl.SysCommonTagAPImpl");
			}
			 
			if(used != null && used.length()>0){
				allList = commonTagAPI.getCommonDataByKey(codeType);
				if(allList != null && allList.size()>0){
					list = new ArrayList<Map<String,String>>();
					for(int j=0;j<allList.size();j++){
						if((","+used + ",").indexOf(","+allList.get(j).get("DICVALUE")+",") !=-1){
							list.add(allList.get(j));
						 }
					}
				}
			}else{
				list = commonTagAPI.getCommonDataByKey(codeType);
			}
			
			
			if(list != null && list.size()>0){
				if(type.equals("select")){
					
					select = this.makeupSelect(select, list);
				}else if(type.equals("json")){
					
					select = this.makeupJson(select, list);
				}else if(type.equals("radio")){
					
					select = this.makeupRadio(select, list);
				}else if(type.equals("checkbox")){
					
					select = this.makeupCheckbox(select, list);
				}else if(type.equals("text")){
					
					select = this.makeupText(select, list);
				}
			}else{
				if(type.equals("json")){
					select.append("{}");
				}
			}

		} catch (Exception e) {
			logger.error("===SysCommonTag doStartTag==error",e);
			e.printStackTrace();
		}
		
		// 输出到页面
		try {
			pageContext.getOut().write(select.toString());
		} catch (IOException e) {
			logger.error("===SysCommonTag doStartTag write ==error",e);
			e.printStackTrace();
		}
		return 0;
	}

	private StringBuffer makeupText(StringBuffer select, List<Map<String, String>> list) {
		for(int i=0;i<list.size();i++){
			Map<String,String> sysCode = list.get(i);
			if(defaultValue != null && (","+defaultValue + ",").indexOf(","+sysCode.get("DICVALUE")+",") !=-1){
			//if(sysCode.get("DICVALUE").equals(defaultValue)){
				select.append(sysCode.get("DICNAME") + "&nbsp;&nbsp;");
			}
		}
		return select;
	}

	private StringBuffer makeupCheckbox(StringBuffer select, List<Map<String, String>> list) {
		for(int i=0;i<list.size();i++){
			Map<String,String> sysCode = list.get(i);
			
			select.append("<input type='checkbox' id='"+name+i+"' name='");
			select.append(name);
			select.append("'");
			if(defaultValue != null && (","+defaultValue + ",").indexOf(","+sysCode.get("DICVALUE")+",") !=-1){
				select.append(" checked");
			 }
			select.append(" value='");
			select.append(sysCode.get("DICVALUE"));
			select.append("'>");
			select.append("<label for='"+name+i+"'>"+sysCode.get("DICNAME")+"</label>");
			//select.append(sysCode.get("DICNAME"));
			select.append("&nbsp;&nbsp;");
		}
		return select;
	}

	private StringBuffer makeupRadio(StringBuffer select,List<Map<String, String>> list) {
		for(int i=0;i<list.size();i++){
			Map<String,String> sysCode = list.get(i);
			
			select.append("<input type='radio' id='"+name+i+"' name='");
			select.append(name);
			select.append("'");
			if (sysCode.get("DICVALUE").equals(defaultValue)) {
				select.append(" checked");
			 }
			select.append(" value='");
			select.append(sysCode.get("DICVALUE"));
			select.append("'>");
			select.append("<label for='"+name+i+"'>"+sysCode.get("DICNAME")+"</label>");
			//select.append(sysCode.get("DICNAME"));
			select.append("&nbsp;&nbsp;");
		}
		return select;
	}

	private StringBuffer makeupJson(StringBuffer select,List<Map<String, String>> list) {
		select.append("[");
		if(list != null && list.size()>0){
			if(hasBlank){
				select.append("{value:'',text:'--");
				select.append(emptyText);
				select.append("--'},");
			}
			for(int i=0;i<list.size();i++){
				select.append("{value:'");
				select.append(list.get(i).get("DICVALUE"));
				select.append("',text:'");
				select.append(list.get(i).get("DICNAME"));
				if(i==(list.size()-1)){
					select.append("'}");
				}else{
					select.append("'},");
				}
			}
		}else{
			select.append("{}");
		}
		select.append("]");
		
		return select;
	}

	private StringBuffer makeupSelect(StringBuffer select,List<Map<String,String>> list) throws JspException {
		Map<String,String> sysCode;
		select.append("<select ");
		select = getAllProp(select);
		select.append(">");
		// 默认有空白项
		if(hasBlank) {
			select.append("<option value=''>");
			select.append(emptyText);
			select.append("</option>");
		} 
		for(int i=0;i<list.size();i++){
			 sysCode = list.get(i);
			 select.append("<option value='");
			 select.append(sysCode.get("DICVALUE"));
			 select.append("'");
			 if (sysCode.get("DICVALUE").equals(defaultValue)) {
				select.append(" selected='selected'");
			 }
			 select.append(">");
			 select.append(sysCode.get("DICNAME"));
			 select.append("</option>");
		}
		select.append("</select>");
		return select;
	}


	/**
	 * 设置标签的属性
	 * 
	 * @param StringBuffer
	 *            results 传入标签属性StringBuffer
	 * @return StringBuffer 返回加入标签属性后的StringBuffer
	 */
	private StringBuffer getAllProp(StringBuffer results) throws JspException {

		prepareAttribute(results, "id", getId());
		prepareAttribute(results, "name", getName());
		prepareAttribute(results, "onchange", getOnchange());
		prepareAttribute(results, "className",getClassName());
		results.append(" "+getExtendProperty());
		return results;
	}
	
	protected void prepareAttribute(StringBuffer handlers, String name,Object value) {
		if (value != null) {
			handlers.append(" ");
			handlers.append(name);
			handlers.append("=\"");
			handlers.append(value);
			handlers.append("\"");
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the onchange
	 */
	public String getOnchange() {
		return onchange;
	}

	/**
	 * @param onchange the onchange to set
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}


	/**
	 * @return the hasBlank
	 */
	public boolean isHasBlank() {
		return hasBlank;
	}
	/**
	 * @param hasBlank the hasBlank to set
	 */
	public void setHasBlank(boolean hasBlank) {
		this.hasBlank = hasBlank;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if(null == type){
			this.type = "select";
		}else{
			this.type = type;
		}
	}


	public String getExtendProperty() {
		return extendProperty;
	}

	public void setExtendProperty(String extendProperty) {
		this.extendProperty = extendProperty;
	}

	public String getEmptyText() {
		return emptyText;
	}

	public void setEmptyText(String emptyText) {
		if(null==emptyText){
			this.emptyText="请选择";
		}else{
			this.emptyText = emptyText;
		}
		
	}


	public String getCodeType() {
		return codeType;
	}


	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}
	public String getBeanID() {
		return beanID;
	}


	public void setBeanID(String beanId) {
		this.beanID = beanId;
	}
}
