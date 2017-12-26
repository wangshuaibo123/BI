package com.fintech.platform.restservice.web.tag;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.OrgInfo;
import com.fintech.platform.api.sysconfig.SysConfigAPI;

public class SysOrgTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	private String orgId=""; //id
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTag() throws JspException {
		OrgInfo orgInfo = null;
		try {
			ApplicationContext context = null;
			try{
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				ServletContext servletContext = webApplicationContext.getServletContext();
				context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			}catch(Exception e){
				context = SysConfigAPI.getApplicationContext();
			}
			OrgAPI orgAPI = (OrgAPI)context.getBean("orgAPI");
			if(StringUtils.isNotEmpty(orgId)){
				orgInfo = orgAPI.getOrgInfo(orgId);
			}
			if(orgInfo != null){
				// 输出到页面
				pageContext.getOut().write(orgInfo.getOrgName());//写入名称
			}else{
				// 输出到页面
				pageContext.getOut().write("");//写入名称
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
