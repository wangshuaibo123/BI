package com.jy.modules.platform.jbpm.custom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jy.platform.jbpm4.tool.MyJBPMTool;
/**
 * 
 * @author xyz
 *
 */
public class ConfigNodeInfoTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String proDefKey;
	private String proLevel;
	private String extNodeInfo;
	private String showSize;
	private String style;
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getShowSize() {
		return showSize;
	}
	public void setShowSize(String showSize) {
		this.showSize = showSize;
	}
	public String getExtNodeInfo() {
		return extNodeInfo;
	}
	public void setExtNodeInfo(String extNodeInfo) {
		this.extNodeInfo = extNodeInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProDefKey() {
		return proDefKey;
	}
	public void setProDefKey(String proDefKey) {
		this.proDefKey = proDefKey;
	}
	public String getProLevel() {
		return proLevel;
	}
	public void setProLevel(String proLevel) {
		this.proLevel = proLevel;
	}
	@Override
	public int doStartTag() throws JspException {
		try {
			String outStr=getOutStr();
			pageContext.getOut().write(outStr);
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return super.doStartTag();
	}
	private String getOutStr() {
		List<String> listAll=getAllName();
		List<String> selectName=getSelectName();
		StringBuffer  sb=new StringBuffer();
		sb.append("<select multiple=\"multiple\" size=\""+showSize+"\"   id=\""+id+"\" name=\""+id+"\"  STYLE=\""+style+"\">");
		sb.append("<option value=\"\"  >请选择... </option>");
		sb.append(getStrDesc(listAll,selectName));
		sb.append("</select>");
		return sb.toString();
	}
	/**
	 * 获取拼接字符串
	 * @param listAll
	 * @param selectName
	 * @return
	 */
	private String getStrDesc(List<String> listAll, List<String> selectName) {
		StringBuffer  sb=new StringBuffer();
		for(String str:listAll){
			sb.append("<option value=\""+str+"\"" +getSelectState(str,selectName) +">"+str+"</option>");
		}
		return sb.toString();
	}
	/**
	 * 获取拼接状态字符串
	 * @param str
	 * @param selectName
	 * @return
	 */
	private String getSelectState(String name, List<String> selectName) {
		StringBuffer  sb=new StringBuffer("");
		for(String temp:selectName){
			if(temp!=null&&temp.equals(name)){
				sb.append(" selected = \"selected\" ");break;
			}
		}
		
		return sb.toString();
	}
	private List<String> getSelectName() {
		List<String> listAll=new ArrayList<String>();
		if(extNodeInfo!=null&&!"".equals(extNodeInfo.trim())){
			String[] names=extNodeInfo.split(",");
			listAll=Arrays.asList(names);
		}
		
		return listAll;
	}
	/**
	 * 获取所有名称
	 * @return
	 */
	private List<String> getAllName(){
		List<String> listAll=new ArrayList<String>();
		  WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
			ServletContext servletContext = webApplicationContext.getServletContext();
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			MyJBPMTool myTool = (MyJBPMTool) context.getBean("MyJBPMTool");
			List<ProcessDefinition> listPDEF =myTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(proDefKey).list();
			for(ProcessDefinition pdef:listPDEF){
				if(pdef.getVersion()==Integer.parseInt(proLevel)){
					listAll=getTaskName(pdef);
					break;
				}
			}
			return listAll;
	}
	/**
	 * 获取所有任务名称
	 */
	private List<String> getTaskName(ProcessDefinition pdef) {
		List<String> listAll=new ArrayList<String>();
		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) pdef;
		List<ActivityImpl> lista= (List<ActivityImpl>) pd.getActivities();
		for(ActivityImpl ai:lista){
			if("task".equals(ai.getType())){
				listAll.add(ai.getName());
			}
		}
		return listAll;
	}
}
