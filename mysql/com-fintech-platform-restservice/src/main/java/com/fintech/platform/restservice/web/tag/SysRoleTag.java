package com.fintech.platform.restservice.web.tag;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.platform.api.sysconfig.SysConfigAPI;
/**
 * 
 * @description: 获取 userId 的拥有角色列表信息
 * @author
 * @date:2014年12月13日下午7:12:51
 */
public class SysRoleTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private	String userId ;//如果没有值 则获取当前 登录人的userId
	private String var;//设置 变量名称
	private String scope = "page" ;//设置 作用域
	
	public int doEndTag () throws JspException{
		int cScope = PageContext.PAGE_SCOPE ;
        if ( scope==null || scope.equalsIgnoreCase("page") ){
        	cScope = PageContext.PAGE_SCOPE ;
        }else if ( "request".equalsIgnoreCase( scope ) ){
        	cScope = PageContext.REQUEST_SCOPE ;
        }else if ( "session".equalsIgnoreCase( scope ) ){
        	 cScope = PageContext.SESSION_SCOPE ;
        }else{
        	cScope = PageContext.APPLICATION_SCOPE ;
        }

        BodyContent body = this.getBodyContent () ;
        
		if (body != null) body.clearBody () ;
		
		StringBuilder sb = new StringBuilder();
		try{
			ApplicationContext context = null;
			try{
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				ServletContext servletContext = webApplicationContext.getServletContext();
				context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			}catch(Exception e){
				context = SysConfigAPI.getApplicationContext();
			}
			//如果没有值 则获取当前 登录人的userId
			if(StringUtils.isEmpty(userId)){
				com.fintech.platform.api.org.SessionAPI sessionAPI = (com.fintech.platform.api.org.SessionAPI)context.getBean("sessionAPI");
				com.fintech.platform.api.org.UserInfo userInfo = sessionAPI.getCurrentUserInfo(); 
				this.setUserId(userInfo.getUserId().toString());
			}
			
			com.fintech.platform.api.sysauth.SysRoleAPI roleAPI = (com.fintech.platform.api.sysauth.SysRoleAPI)context.getBean("sysRoleAPI");
			List<Map<String,Object>> dataList = roleAPI.getRoleByUserId(userId);//用户ID
			if(!CollectionUtils.isEmpty(dataList)){
				for (Map<String, Object> map : dataList) {
					String roleCode = (String) map.get("roleCode");
					if(StringUtils.isNotEmpty(sb))
						sb.append(",");
					sb.append(roleCode);
				}
			}
			
			pageContext.setAttribute(getVar(), sb.toString(),cScope);
		}catch (Exception ex){
			throw new JspException (ex.toString(), ex) ;
		}

		return EVAL_PAGE ;
	}
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
