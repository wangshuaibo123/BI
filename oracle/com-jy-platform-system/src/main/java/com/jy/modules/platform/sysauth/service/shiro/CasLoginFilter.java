package com.jy.modules.platform.sysauth.service.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @ClassName: CasLoginFilter 
 * @Description: 服务端认证成功后，客户端登录成后处理
 * @author luoyr
 * @date 2015年6月2日 下午5:03:11 
 *
 */
public class CasLoginFilter extends CasFilter{
	private static Logger logger = LoggerFactory.getLogger(CasLoginFilter.class);
	
	/***
	 * @Title: onLoginSuccess 
	 * @Description: 重写登录成功方法,支持动态跳转指定页面
	 * @param  AuthenticationToken    认证令牌
	 * @param  Subject    认证主题
	 * @return boolean    是否处理下一个过滤器 
	 */
	@Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
		HttpServletRequest r = (HttpServletRequest)request;
		StringBuffer requestURL = r.getRequestURL();
        String queryString = r.getQueryString();
        String target = r.getParameter("target");
        String targetparams = r.getParameter("targetparams");
        //对原始参数进行处理
        if(StringUtils.isNotEmpty(target)){
        	String oriParams = "";
        	if(StringUtils.isNotEmpty(targetparams)){
        		//targetparams=s_2/f_3/g_4的情况
        		//下面将原始参数转回来
        		String[] tarrays = targetparams.split("/");
        		for (String params : tarrays) {
        			String[] paramArray = params.split("_");
        			oriParams = oriParams +"&"+ paramArray[0]+"=" + paramArray[1] ;
				}
        		oriParams = oriParams.replaceFirst("&", "?");
        	}
        	logger.debug("access other system:"+target+oriParams);
        	WebUtils.redirectToSavedRequest(request, response, target+oriParams);
        }else{
        	issueSuccessRedirect(request, response);
        }
        
        return false;
    }
}
