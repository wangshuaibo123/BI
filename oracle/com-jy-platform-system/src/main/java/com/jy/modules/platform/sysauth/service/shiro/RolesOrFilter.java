package com.jy.modules.platform.sysauth.service.shiro;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在进行角色检测时，只要有一个满足即通
 *
 */
public class RolesOrFilter extends AuthorizationFilter {
	private static final Logger log = LoggerFactory.getLogger(RolesOrFilter.class);  
    
    @SuppressWarnings({"unchecked"})
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
    	log.debug("access filter isAccessAllowed");
    	
        String[] rolesArray = (String[])mappedValue;
        
        if(rolesArray == null|| rolesArray.length== 0) {
            //no roles specified, so nothing to check - allow access.
            return true;
        }
        
        //当此访问session已经失效,不进行授权判断
        HttpServletRequest r = (HttpServletRequest)request;
        if(r.getSession(false) == null){
        	log.debug("session is valid");
        	return false;
        }
        Subject subject = getSubject(request,response);
        log.debug("access filter isAccessAllowed rolesArray:"+Arrays.toString(rolesArray));
        for(String role : rolesArray) {
         if(subject.hasRole(role)) {
             return true;
         }
        }
 
        //允许开发环境 非CAS 成功登陆
        /*if(rolesArray != null && rolesArray.length == 1 && "cas_home".equalsIgnoreCase(rolesArray[0])){
        	return true;
        }*/
        
        return false;
        
    }
    
}
