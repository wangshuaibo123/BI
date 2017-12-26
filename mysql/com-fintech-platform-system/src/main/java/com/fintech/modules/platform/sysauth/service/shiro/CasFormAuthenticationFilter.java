package com.fintech.modules.platform.sysauth.service.shiro;


import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CasFormAuthenticationFilter extends FormAuthenticationFilter{
	private static final Logger log = LoggerFactory.getLogger(CasFormAuthenticationFilter.class);
	
	public CasFormAuthenticationFilter(){
		log.debug("CasFormAuthenticationFilter==========");
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletRequest r = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String header = r.getHeader("X-Requested-With");  
		log.debug("ForceLogoutFilter onAccessDenied is ajax request:"+header);
        boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
        if(isAjax){//为ajax请求不处理,如主页面请求消息数量等
        	res.setContentType("application/json;charset=UTF-8"); 
        	res.setCharacterEncoding("UTF-8");
        	res.setHeader("sessionstatus","timeout");
        	PrintWriter writer = res.getWriter();
        	String jump = r.getScheme() + "://"+ r.getServerName() + ":" + r.getServerPort()+ r.getContextPath() + "/";
        	String responseContent = "{\"status\":\"forceLogout\",\"forceLogoutUrl\":\""+jump+"\"}";
        	writer.print(responseContent);
        	log.debug("ForceLogoutFilter onAccessDenied ajax response:"+responseContent);
        	
        	return false;
        }
        
		if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
                        "Authentication url [" + getLoginUrl() + "]");
            }
            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
	}

}
