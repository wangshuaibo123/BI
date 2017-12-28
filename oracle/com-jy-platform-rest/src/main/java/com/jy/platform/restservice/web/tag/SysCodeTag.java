package com.jy.platform.restservice.web.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jy.platform.api.sysconfig.SysConfigAPI;
import com.jy.platform.api.sysdict.SysDictAPI;

public class SysCodeTag extends TagSupport {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String defaultValue;
	private String onchange;
	private String className;
	private boolean hasBlank = true;
	//private boolean notNull = false;
	private String language;
	private String type="select";
	private String extendProperty;
	private String emptyText="请选择";
	private	String codeType="";
	private String used = "";
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		StringBuffer select = new StringBuffer();
		// options
		try {
			List<Map> allList = null;
			List<Map> list = null;
			
			if(codeType != null && !codeType.equals("")){
				ApplicationContext context = null;
				
				try{
                    WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
                    ServletContext servletContext = webApplicationContext.getServletContext();
                    context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                }
				catch(Exception e){
                    context = SysConfigAPI.getApplicationContext();  //spring boot启动时会注入
                }
				
				SysDictAPI sysDictAPI = (SysDictAPI)context.getBean("sysDictAPI");
				if(used != null && used.length()>0){
					allList = sysDictAPI.getDictByKey(codeType);
					if(allList != null && allList.size()>0){
						list = new ArrayList<Map>();
						for(int j=0;j<allList.size();j++){
							if((","+used + ",").indexOf(","+allList.get(j).get("DICVALUE")+",") !=-1){
								list.add(allList.get(j));
							 }
						}
					}
				}else{
					list = sysDictAPI.getDictByKey(codeType);
				}
			}
			if(list != null && list.size()>0){
				Map sysCode;
				if(type.equals("select")){
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
				}else if(type.equals("json")){
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
				}else if(type.equals("radio")){
					if(list != null && list.size()>0){
						for(int i=0;i<list.size();i++){
							sysCode = list.get(i);
							
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
					}
				}else if(type.equals("checkbox")){
					if(list != null && list.size()>0){
						for(int i=0;i<list.size();i++){
							sysCode = list.get(i);
							
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
					}
				}else if(type.equals("text")){
					for(int i=0;i<list.size();i++){
						sysCode = list.get(i);
						if(defaultValue != null && (","+defaultValue + ",").indexOf(","+sysCode.get("DICVALUE")+",") !=-1){
						//if(sysCode.get("DICVALUE").equals(defaultValue)){
							select.append(sysCode.get("DICNAME") + "&nbsp;&nbsp;");
						}
					}
					
				}
			}else{
				if(type.equals("json")){
					select.append("{}");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 输出到页面
		try {
			pageContext.getOut().write(select.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
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
		//prepareAttribute(results, "notNull",isNotNull());
		results.append(" "+getExtendProperty());
		return results;
	}
	
	/**
	 * Prepares an attribute if the value is not null, appending it to the the
	 * given StringBuffer.
	 * 
	 * @param handlers
	 *            The StringBuffer that output will be appended to.
	 */
	protected void prepareAttribute(StringBuffer handlers, String name,
			Object value) {
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


//	public boolean isNotNull() {
//		return notNull;
//	}
//
//
//	public void setNotNull(boolean notNull) {
//		this.notNull = notNull;
//	}


	public String getUsed() {
		return used;
	}


	public void setUsed(String used) {
		this.used = used;
	}
	
}
