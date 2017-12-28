package com.jy.modules.platform.sysauth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.modules.common.util.ObtainPropertiesInfo;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SafeLogoutController
 * @description: 安全退出
 * @author:  luoyr
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
    	response.sendRedirect(login);
    	request.getSession().invalidate();
    }
    

    
    
}
