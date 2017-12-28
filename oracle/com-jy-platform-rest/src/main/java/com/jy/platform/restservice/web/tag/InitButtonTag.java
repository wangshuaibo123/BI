package com.jy.platform.restservice.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * 初始按鈕
 * @author xyz
 * @date 2016-3-11
 *
 *
 */
public class InitButtonTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		String outStr=getDisplayContent();
		try {
			pageContext.getOut().print(outStr);
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return super.doStartTag();
	}

	private String getDisplayContent(){
		StringBuffer sb=new StringBuffer();
		sb.append("<input type=\"hidden\" value=\"\" id=\"partnerRuleJsonId\"/>");
		sb.append("<br/>");
		sb.append("<div id=\"divSubBtnId\"></div>");
		sb.append("</div>");
		return sb.toString();
	}
	
}
