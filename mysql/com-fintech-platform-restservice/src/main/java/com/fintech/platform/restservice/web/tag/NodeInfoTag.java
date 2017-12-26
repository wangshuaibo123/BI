package com.fintech.platform.restservice.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * 节点信息标签类
 * 
 * @author
 * @date 2016-3-10
 * 
 * 
 */
public class NodeInfoTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taskId;
	private String processInsId;
	private String acitityName;
	private String bizTabId;
	private String bizTabName;
	private String bizInfId;

	@Override
	public int doStartTag() throws JspException {
		
		
		
		
		try {
			String outStr=getDisplayContent();
			pageContext.getOut().write(outStr);
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		
		}
		return super.doStartTag();
	}
	public String getDisplayContent(){
		StringBuffer sb=new StringBuffer();
		sb.append("");
		
		
		
		sb.append("<table id=\"bizInfoTableId\" class=\"formTableSwap\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\" style=\"display: none\">"); 
		sb.append("<tr><th > 指定参与者 ：</th>");
		sb.append("<td align=\"left\" >");
		sb.append("<input type=\"hidden\" maxlength=\"10\" id=\"nameInfo\" name=\"nameInfo\" value=\"-1\"  />");
		sb.append("<input type=\"hidden\"  maxlength=\"50\" id=\"dtoparamUserId\" name=\"paramUserId\" value=\"\" />");
		sb.append("<input type=\"hidden\"  maxlength=\"50\" id=\"foreachJoinId\" name=\"foreachJoin\" value=\"N\" />");
		sb.append("</td>");
		sb.append("<th> 任务ID ：</th>");
		if(StringUtils.isNotEmpty(taskId)){
			sb.append("<td><input type=\"hidden\"  readonly=\"readonly\"  id=\"disTaskId\" name=\"disTaskId\" value=\""+taskId+"\" />");
			sb.append("<input type=\"hidden\"  id=\"dtotaskId\" name=\"taskId\" value=\""+taskId+"\" />");
		}
		
		if(StringUtils.isNotEmpty(processInsId)){
			sb.append("<input type=\"hidden\"  id=\"processInsId\" name=\"processInsId\" value=\""+processInsId+"\" />");
		}
		
		if(StringUtils.isNotEmpty(acitityName)){
			sb.append("<input type=\"hidden\"  id=\"acitityName\" name=\"acitityName\" value=\""+acitityName+"\" />");
		}
		
		if(StringUtils.isNotEmpty(bizTabId)){
			sb.append("<input type=\"hidden\"  id=\"bizTabId\" name=\"bizTabId\" value=\""+bizTabId+"\" />");
		}
		
		if(StringUtils.isNotEmpty(bizTabName)){
			sb.append("<input type=\"hidden\"  id=\"bizTabName\" name=\"bizTabName\" value=\""+bizTabName+"\" />");
		}
		
		if(StringUtils.isNotEmpty(bizInfId)){
			sb.append("<input type=\"hidden\"  id=\"bizInfId\" name=\"bizInfId\" value=\""+bizInfId+"\" />");
		}
		
		   	
		sb.append("<input type=\"hidden\"  id=\"intoCustRefId\" name=\"intoCustRefId\"  />");
		sb.append("</td>");
		sb.append("<th> 流程流转方向 ：</th>");
		sb.append("<td><select id=\"dtoturnDirection\" name=\"turnDirection\"  style=\"width:150px;\"><option value=\"\">--默认--</option></select></td>");
		sb.append("</tr>");
		sb.append("<tr><th>流程参数信息 ：</th>");
		sb.append("<td colspan=\"5\">");
		sb.append("<textarea class=\"validate[maxSize[500]] input_hui\"  id=\"dtootherParamJavaCode\" name=\"otherParamJavaCode\" rows=\"5\" cols=\"120\" title='' ></textarea>"); 
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");
		return sb.toString();
	}
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInsId() {
		return processInsId;
	}

	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}

	public String getAcitityName() {
		return acitityName;
	}

	public void setAcitityName(String acitityName) {
		this.acitityName = acitityName;
	}

	public String getBizTabId() {
		return bizTabId;
	}

	public void setBizTabId(String bizTabId) {
		this.bizTabId = bizTabId;
	}

	public String getBizTabName() {
		return bizTabName;
	}

	public void setBizTabName(String bizTabName) {
		this.bizTabName = bizTabName;
	}

	public String getBizInfId() {
		return bizInfId;
	}

	public void setBizInfId(String bizInfId) {
		this.bizInfId = bizInfId;
	}

}
