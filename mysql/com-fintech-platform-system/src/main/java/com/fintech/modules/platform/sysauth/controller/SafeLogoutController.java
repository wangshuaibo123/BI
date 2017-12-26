package com.fintech.modules.platform.sysauth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fintech.modules.common.util.ObtainPropertiesInfo;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SafeLogoutController
 * @description: 安全退出
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/user")
public class SafeLogoutController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SafeLogoutController.class);

 	
    
    /**
     * 安全退出,先删除本机session,再调用cas的登出接口
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/safelogout")
    @ResponseBody
    public void safelogout(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	//request.getSession().invalidate();
    	String login = null;
    	try{
    		login = ObtainPropertiesInfo.getValByKey("cas.safelogout.url");
    	}catch(java.util.MissingResourceException e){
    		logger.debug("cas.safelogout.url not define");
    		login = ObtainPropertiesInfo.getValByKey("cas.force.url");
    	}
    			
    	if(StringUtils.isEmpty(login)){
    		login = ObtainPropertiesInfo.getValByKey("cas.force.url");
    	}
    	logger.debug("进行安全退出操作,将进行重定向至:"+login);
    	try {
			request.getSession().invalidate();
			logger.info("============使其session失效=============");
			response.sendRedirect(login);
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				UserInfo userinfo =((UserInfo)subject.getSession().getAttribute("userInfo"));
				subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
				
				if(userinfo!=null){
					String loginName = 	userinfo.getLoginName();
					logger.info("用户:"+loginName+"============使其session失效=============退出登录==");
				}
				
			}
		} catch (Exception e) {
			logger.error("======SafeLogoutController==error====",e);
		}
    }
    

    
    
}
