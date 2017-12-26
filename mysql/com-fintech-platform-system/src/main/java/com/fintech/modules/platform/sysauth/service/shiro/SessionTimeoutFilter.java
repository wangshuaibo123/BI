package com.fintech.modules.platform.sysauth.service.shiro;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: SessionTimeoutFilter 
 * @Description: session超时控制 
 * @author
 * @date 2015年6月2日 下午5:18:15 
 *
 */
public class SessionTimeoutFilter extends AccessControlFilter {
	private static final Logger log = LoggerFactory.getLogger(SessionTimeoutFilter.class);
	private SessionDAO sessionDAO;
	/***
	 * @Title: isAccessAllowed 
	 * @Description: 是否允许访问此次请求
	 * @param  request    请求对象
	 * @param  response   响应对象
	 * @return boolean    是否处理下一个过滤器 
	 */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  {
    	log.debug("SessionTimeoutFilter isAccessAllowed begin");
    	HttpServletRequest r = (HttpServletRequest)request;
    	sessionDAO.readSession(r.getSession().getId());
    	SecurityManager securityManager = SecurityUtils.getSecurityManager();
    	if(securityManager instanceof DefaultWebSecurityManager){
    		DefaultWebSecurityManager sm = (DefaultWebSecurityManager)securityManager;
    		SessionManager sessionManager = sm.getSessionManager();
    	}
    	Subject subject = SecurityUtils.getSubject();
    	boolean islogin = subject.isAuthenticated();
        log.debug("SessionTimeoutFilter isAccessAllowed end ["+islogin+"]");
        return islogin;
    }
    
    /***
	 * @Title: onAccessDenied 
	 * @Description: 不允许访问此次请求
	 * @param  request    请求对象
	 * @param  response   响应对象
	 * @return boolean    是否处理下一个过滤器 
	 */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	log.debug("SessionTimeoutFilter onAccessDenied begin");
    	HttpServletRequest r = (HttpServletRequest)request;
    	HttpServletResponse res = (HttpServletResponse)response;
    	String header = r.getHeader("X-Requested-With");
    	boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
    	String portString = request.getServerPort() == 80 ? "" : ":"+request.getServerPort();
    	String jump = request.getScheme()+"://"+request.getServerName()+portString+r.getContextPath()+"/invalidSession.jsp";
    	if(isAjax){//为ajax请求不处理,如主页面请求消息数量等
        	res.setContentType("application/json;charset=UTF-8"); 
        	res.setCharacterEncoding("UTF-8");
        	res.setHeader("sessionstatus","timeout");
        	PrintWriter writer = res.getWriter();
        	String responseContent = "{\"status\":\"forceLogout\",\"forceLogoutUrl\":\""+jump+"\"}";
        	writer.print(responseContent);
        	log.debug("ForceLogoutFilter onAccessDenied ajax response:"+responseContent);
        	return false;
        }
    	res.sendRedirect(jump);
        res.flushBuffer();
    	return false;
    }


	public SessionDAO getSessionDAO() {
		return sessionDAO;
	}


	public void setSessionDAO(SessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}
    

	
}
