package com.fintech.modules.platform.sysauth.service.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.fintech.modules.platform.sysconfig.service.SysConfigService;
import com.fintech.platform.api.org.UserInfo;

/**
 * 强制退出控制 暂时不控制
 * @description: 暂时 使用 该类进行 非 CAS登录（ 仅开发环境时 配置使用）
 * @author
 * @date:2014年12月10日上午10:43:22
 */
public class ForceLogoutFilterV0 extends AccessControlFilter {
	//安全策略错误码
	private final static String JYPT_SECURITY_CODE = "JYPT_SECURITY_CODE";
	private final static String JYPT_SECURITY_CODE_FORBIDDENLOGIN = "USC0002";
	
	private static final Logger log = LoggerFactory.getLogger(ForceLogoutFilterV0.class);
	@Autowired
	@Qualifier("com.fintech.modules.platform.sysconfig.service.SysConfigService")
	private SysConfigService sysConfigService;
	//强出参数标识
	private String sysConfigParam;
	//强制退出跳转页面,默认为注销链接
	private String forceUrl;
	
	@Value("${app.datasource:YES}")
	private String appDatasource;
	
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	Subject currentSubject = SecurityUtils.getSubject();
    	HttpServletRequest r = (HttpServletRequest)request;
		UserInfo currentUser = (UserInfo)currentSubject.getSession().getAttribute("userInfo");
		if(currentUser != null){
			MDC.put("userId", currentUser.getLoginName());//增加用户日志
			log.info(r.getRequestURL().toString());
		}
		
		if("NO".equalsIgnoreCase(appDatasource)) return true;
		//没有登录信息
		//if(currentUser == null) return false;
		//业务系统唯护判断标识,此过滤器只取值
        String tag = sysConfigService.realGainValueByCode(sysConfigParam);
        if(!StringUtils.isEmpty(tag)){
        	Boolean tagBoolean = Boolean.parseBoolean(tag);
        	r.getSession().setAttribute(JYPT_SECURITY_CODE, JYPT_SECURITY_CODE_FORBIDDENLOGIN);
        	return !tagBoolean;
        }
        return true;
        
    }
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    
        return true;
    }
    
    @Override
    public void setLoginUrl(String loginUrl) {
    	super.setLoginUrl(loginUrl);
    }

	public String getSysConfigParam() {
		return sysConfigParam;
	}

	public void setSysConfigParam(String sysConfigParam) {
		this.sysConfigParam = sysConfigParam;
	}

	public String getForceUrl() {
		return forceUrl;
	}

	public void setForceUrl(String forceUrl) {
		this.forceUrl = forceUrl;
	}
}
