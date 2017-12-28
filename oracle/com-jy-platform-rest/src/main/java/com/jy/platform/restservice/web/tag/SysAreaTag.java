package com.jy.platform.restservice.web.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jy.platform.api.sysarea.SysAreaAPI;
import com.jy.platform.api.sysconfig.SysConfigAPI;

public class SysAreaTag extends TagSupport {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String pid="pid";//省级文本框id
	private String pname="";//省级文本框name
	private	String cid="cid";//市级文本框id
	private String cname="";//市级文本框name
	private String tid="tid";//乡镇级文本框id
	private String tname="";//乡镇级文本框name
	private int level=3;//默认级别为3级
	private String type="select";//默认类型为select，可以支持select、text、json三种
	private String defaultValue="";//默认值
	private String className="";//css样式
	private String onchange;//onchange事件
	private String emptyText="--请选择--";//空值显示
	private boolean hasBlank=true;
	private String extendProperty="";
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		StringBuffer select = new StringBuffer();
		try {
			ApplicationContext context = null;
            
            try{
                WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
                ServletContext servletContext = webApplicationContext.getServletContext();
                context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            }
            catch(Exception e){
                context = SysConfigAPI.getApplicationContext();  //spring boot启动时会注入
            }
			
			SysAreaAPI sysAreaAPI = (SysAreaAPI)context.getBean("sysAreaAPI");
			
			if(type.equals("select")){
				String defaultPvalue = "";
				String defaultCvalue = "";
				String defaultTvalue = "";
				List<Map> plist = sysAreaAPI.queryChildAreaByPid("0");
				List<Map> clist = null;
				List<Map> tlist = null;
 				if(level == 1){
					defaultPvalue = defaultValue;
				}else if(level == 2){
					if(defaultValue != null && defaultValue.length()>0){
						defaultCvalue = defaultValue;
						defaultPvalue = sysAreaAPI.queryParentCodeByCode(defaultCvalue);
						clist = sysAreaAPI.queryChildAreaByCode(defaultPvalue);
					}
				}else if(level == 3){
					if(defaultValue != null && defaultValue.length()>0){
						defaultTvalue = defaultValue;
						defaultCvalue = sysAreaAPI.queryParentCodeByCode(defaultTvalue);
						defaultPvalue = sysAreaAPI.queryParentCodeByCode(defaultCvalue);
						clist = sysAreaAPI.queryChildAreaByCode(defaultPvalue);
						tlist = sysAreaAPI.queryChildAreaByCode(defaultCvalue);
					} 
				}
				
				select.append("<select ");
				if(extendProperty!=null && !extendProperty.trim().equals("")){
					select.append(extendProperty).append(" ");
				}
				if(level != 1){
					if(cid != null && cid.length()>0){
						select.append(" title='");
						select.append(cid);
						if(level == 3 && tid != null && tid.length()>0){
							select.append(",");
							select.append(tid);
						}
						select.append("'");
					}
					select.append(" onchange='loadArea(this);' ");
				}
				select = getProvinceProp(select);
				select.append(">");
				// 默认有空白项
				if(hasBlank) {
					select.append("<option value=''>");
					select.append(emptyText);
					select.append("</option>");
				}
				if(plist != null && plist.size()>0){
					for(int i=0;i<plist.size();i++){
						select.append("<option value='");
						select.append(plist.get(i).get("AREACODE"));
						select.append("' ");
						if (plist.get(i).get("AREACODE").equals(defaultPvalue)) {
							select.append(" selected='selected'");
						 }
						select.append(">");
						select.append(plist.get(i).get("AREANAME"));
						select.append("</option>");
					}	
				}
				select.append("</select>");
				if(level >= 2){
					select.append("<select ");
					if(extendProperty!=null && !extendProperty.trim().equals("")){
						select.append(extendProperty).append(" ");
					}
					if(level != 2){
						if(cid != null && cid.length()>0){
							select.append(" title='"+tid+"'");
						}
						select.append(" onchange='loadArea(this);' ");
					}
					select = getCityProp(select);
					select.append(">");
					// 默认有空白项
					if(hasBlank) {
						select.append("<option value=''>");
						select.append(emptyText);
						select.append("</option>");
					}
					if(clist != null && clist.size()>0){
						for(int i=0;i<clist.size();i++){
							select.append("<option value='");
							select.append(clist.get(i).get("AREACODE"));
							select.append("'");
							if (clist.get(i).get("AREACODE").equals(defaultCvalue)) {
								select.append(" selected='selected' ");
							 }
							select.append(">");
							select.append(clist.get(i).get("AREANAME"));
							select.append("</option>");
						}	
					}
					select.append("</select>");
				}
				if(level == 3){
					select.append("<select ");
					if(extendProperty!=null && !extendProperty.trim().equals("")){
						select.append(extendProperty).append(" ");
					}
					select = getTownProp(select);
					select.append(">");
					// 默认有空白项
					if(hasBlank) {
						select.append("<option value=''>");
						select.append(emptyText);
						select.append("</option>");
					}
					if(tlist != null && tlist.size()>0){
						for(int i=0;i<tlist.size();i++){
							select.append("<option value='");
							select.append(tlist.get(i).get("AREACODE"));
							select.append("'");
							if (tlist.get(i).get("AREACODE").equals(defaultTvalue)) {
								select.append(" selected='selected' ");
							 }
							select.append(">");
							select.append(tlist.get(i).get("AREANAME"));
							select.append("</option>");
						}	
					}
					select.append("</select>");
				}
			}else if(type.equals("text")){
				String text = "";
				if(defaultValue != null && defaultValue.length()>0){
					Map map = sysAreaAPI.getAreaByCode(defaultValue);
					if(map.get("PARENTID") != null && (""+map.get("PARENTID")).length()>0 && !(""+map.get("PARENTID")).equals("0")){
						Map temp = sysAreaAPI.getAreaByCode(""+map.get("PCODE"));
						select.append(temp.get("PNAME"));
						select.append(" ");
					}
					if(map.get("PNAME") != null){
						select.append(map.get("PNAME"));
						select.append(" ");
					}
					select.append(map.get("AREANAME"));
					
				}
				select.append(text);
			}
			else if(type.equals("json")){
				if(defaultValue != null && defaultValue.length()>0){
					Map map = sysAreaAPI.getAreaByCode(defaultValue);
					select.append("[");
					if(map.get("PARENTID") != null && (""+map.get("PARENTID")).length()>0 && !(""+map.get("PARENTID")).equals("0")){
						Map temp = sysAreaAPI.getAreaByCode(""+map.get("PCODE"));
						select.append("{value:'");
						select.append(temp.get("PCODE"));
						select.append("',text:'");
						select.append(temp.get("PNAME"));
						select.append("'},");
					}
					if(map.get("PNAME") != null){
						
						select.append("{value:'");
						select.append(map.get("PCODE"));
						select.append("',text:'");
						select.append(map.get("PNAME"));
						select.append("'},");						
					}
					select.append("{value:'");
					select.append(map.get("PCODE"));
					select.append("',text:'");
					select.append(defaultValue);
					select.append("'}");
					select.append("]");
				}else{
					if(level==1){
						List<Map> plist = sysAreaAPI.queryChildAreaByPid("0");
						select.append("[");
						if(hasBlank){
							select.append("{value:'");
							select.append("");
							select.append("',text:'");
							select.append(emptyText).append("'}");
						}
						if(plist != null && plist.size()>0){
							select.append(",");
							for(int i=0;i<plist.size();i++){
								select.append("{value:'");
								select.append(plist.get(i).get("AREACODE"));
								select.append("',text:'");
								select.append(plist.get(i).get("AREANAME"));
								if(i==(plist.size()-1)){
									select.append("'}");
								}else{
									select.append("'},");
								}
							}	
						}				
						select.append("]");
					}
				}
			}
//				}else if(type.equals("text")){
//					for(int i=0;i<list.size();i++){
//						sysCode = list.get(i);
//						if(defaultValue != null && (","+defaultValue + ",").indexOf(","+sysCode.get("DICVALUE")+",") !=-1){
//						//if(sysCode.get("DICVALUE").equals(defaultValue)){
//							select.append(sysCode.get("DICNAME") + "&nbsp;&nbsp;");
//						}
//					}
//					
//				}

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
	 * 设置标签省的属性
	 * 
	 * @param StringBuffer
	 *            results 传入标签属性StringBuffer
	 * @return StringBuffer 返回加入标签属性后的StringBuffer
	 */
	private StringBuffer getProvinceProp(StringBuffer results) throws JspException {
		prepareAttribute(results, "id", this.getPid());
		prepareAttribute(results, "name", this.getPname());
		prepareAttribute(results, "className",getClassName());
		return results;
	}
	
	/**
	 * 设置标签市的属性
	 * 
	 * @param StringBuffer
	 *            results 传入标签属性StringBuffer
	 * @return StringBuffer 返回加入标签属性后的StringBuffer
	 */
	private StringBuffer getCityProp(StringBuffer results) throws JspException {
		prepareAttribute(results, "id", this.getCid());
		prepareAttribute(results, "name", this.getCname());
		prepareAttribute(results, "className",getClassName());
		return results;
	}
	
	/**
	 * 设置标签县的属性
	 * 
	 * @param StringBuffer
	 *            results 传入标签属性StringBuffer
	 * @return StringBuffer 返回加入标签属性后的StringBuffer
	 */
	private StringBuffer getTownProp(StringBuffer results) throws JspException {
		prepareAttribute(results, "id", this.getTid());
		prepareAttribute(results, "name", this.getTname());
		prepareAttribute(results, "className",getClassName());
		return results;
	}

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
		

	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}


	public String getPname() {
		return pname;
	}


	public void setPname(String pname) {
		this.pname = pname;
	}


	public String getCid() {
		return cid;
	}


	public void setCid(String cid) {
		if(null == cid){
			this.cid = "cid";
		}else{
			this.cid = cid;
		}
	}


	public String getCname() {
		return cname;
	}


	public void setCname(String cname) {
		this.cname = cname;
	}


	public String getTid() {
		return tid;
	}


	public void setTid(String tid) {
		if(null == tid){
			this.tid = "tid";
		}else{
			this.tid = tid;
		}
	}


	public String getTname() {
		return tname;
	}


	public void setTname(String tname) {
		this.tname = tname;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		if(level >3){
			this.level = 3;
		}else{
			this.level = level;
		}
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


	public String getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String getOnchange() {
		return onchange;
	}


	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}


	public String getEmptyText() {
		return emptyText;
	}


	public void setEmptyText(String emptyText) {
		if(null==emptyText){
			this.emptyText="==请选择==";
		}else{
			this.emptyText = emptyText;
		}
		
	}


	public boolean isHasBlank() {
		return hasBlank;
	}


	public void setHasBlank(boolean hasBlank) {
		this.hasBlank = hasBlank;
	}


	public String getExtendProperty() {
		return extendProperty;
	}


	public void setExtendProperty(String extendProperty) {
		this.extendProperty = extendProperty;
	}


}
