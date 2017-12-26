package com.fintech.modules.platform.sysauth.service.shiro;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.SimpleCookie;
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
 * @ClassName: ForceLogoutFilter 
 * @Description: 退出控制器
 * @author
 * @date 2015年6月2日 上午11:44:03 
 *
 */
public class ForceLogoutFilter extends AccessControlFilter {
	private static final Logger log = LoggerFactory.getLogger(ForceLogoutFilter.class);
	
	@Autowired
	@Qualifier("com.fintech.modules.platform.sysconfig.service.SysConfigService")
	private SysConfigService sysConfigService;
	@Autowired
	@Qualifier("com.fintech.modules.platform.sysauth.service.shiro.FirstLoginHandle")
	private FirstLoginHandle firstLoginHandle;
	//强出参数标识
	private String sysConfigParam;
	//强制退出跳转页面,默认为注销链接
	private String forceUrl;
	//长链接访问地址
	private List<String> longLinkAccess = new ArrayList<String>();
	//session时间设置是否与cas相同,不设置默认为相同(不能使用boolean对象,不能设置初始值)
	private String sameSessionSet;
	//访问令牌 存时间戳
	private final static String ACCESSTOKEN = "JYPT_ACCESS_TOKEN";
	//安全策略错误码
	private final static String JYPT_SECURITY_CODE = "JYPT_SECURITY_CODE";
	/**
	 * 会话超时
	 */
	private final static String JYPT_SECURITY_CODE_TIMEOUT = "USC0001";
	/**
	 * 访问正常 ,安静模式
	 */
	private final static String JYPT_SECURITY_CODE_QUIENT = "USC0000";
	/**
	 * 业务系统繁忙,强制退出,并且不允许再次登录
	 */
	private final static String JYPT_SECURITY_CODE_FORBIDDENLOGIN = "USC0002";
	
	private SimpleCookie simpleCookie;
	private AbstractSessionDAO customShiroSessionDAO;
	
	@Value("${app.datasource:YES}")
	private String appDatasource;
	
	/**
	 * 处理session是否配置与cas相同业务处理
	 * 判断不出是因为
	 * 
	 * 
	 * 超时,还是因为第一次登录,不能正确显示错误信息
	 * @param request
	 * @return
	 */
	private boolean  sessionSameHandle(ServletRequest request){
		Subject subject = ThreadContext.getSubject();
		String sameSessionSet = this.getSameSessionSet();
		boolean tag = Boolean.parseBoolean(sameSessionSet);
		if(!tag){//session时间设置不相同情况
			if(subject != null){
	    		boolean authenticated = subject.isAuthenticated();
	    		if(!authenticated){//未认证,则表明为第一次登录
	    			log.debug("ForceLogoutFilter SessionSameHandle authenticated false");
	    			if(subject.getPrincipal() == null){
		    			HttpServletRequest hsr = (HttpServletRequest) request;
		    			hsr.getSession().setAttribute(JYPT_SECURITY_CODE, JYPT_SECURITY_CODE_QUIENT);
		    			return false;
		    		}
	    		}
	    		
	    	}
		}
		return true;
	}
	/***
	 * @Title: isAccessAllowed 
	 * @Description: 是否允许访问此次请求
	 * @param  request    请求对象
	 * @param  response   响应对象
	 * @return boolean    是否处理下一个过滤器 
	 */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  {
    	log.debug("ForceLogoutFilter isAccessAllowed begin");
    	HttpServletRequest r = (HttpServletRequest)request;
    	Subject currentSubject = SecurityUtils.getSubject();
		UserInfo currentUser = (UserInfo)currentSubject.getSession().getAttribute("userInfo");
		if(currentUser != null){
			 MDC.put("userId", currentUser.getLoginName());//增加用户日志
			 log.info(r.getRequestURL().toString());
		}
    	boolean sessionSameHandle = sessionSameHandle(request);
    	if(!sessionSameHandle){
    		return false;
    	}
    	boolean checkLongLinkAccess = checkLongLinkAccess(request);
    	if(!checkLongLinkAccess){//当返回false时,进行退出操作
    		return false;
    	}
    	if("NO".equalsIgnoreCase(appDatasource)) return true;
    	
    	//业务系统唯护判断标识,此过滤器只取值
        String tag = sysConfigService.realGainValueByCode(sysConfigParam);//这个参数值不能有缓存,更新时需要刷新缓存或无缓存
        log.debug("ForceLogoutFilter isAccessAllowed "+sysConfigParam+":"+tag);
        if(!StringUtils.isEmpty(tag)){//数据库有此值
        	//判断重新登录的条件,当返回false时,进入onAccessDenied方法,进入重新登录操作
        	Boolean tagBoolean = Boolean.parseBoolean(tag);//false可以登录,true不能登录
        	r.getSession().setAttribute(JYPT_SECURITY_CODE, JYPT_SECURITY_CODE_FORBIDDENLOGIN);
        	log.debug("ForceLogoutFilter isAccessAllowed end "+!tagBoolean);
        	return !tagBoolean;
        }
        log.debug("ForceLogoutFilter isAccessAllowed end true");
       
        return true;
        
    }
    /**
     * 检查长链接是否在session时间范围内,当超时则强制退出 
     * @param request
     * @return 返回false强制退出
     */
    private boolean checkLongLinkAccess(ServletRequest request){
    	//对每一个请求生成一个令牌(排除轮询的访问路径)
    	HttpServletRequest hsr = (HttpServletRequest) request;
    	String requestUrl = hsr.getRequestURL().toString();
        String params = hsr.getQueryString();
        String access = params == null ? requestUrl : requestUrl.concat(params);
        //http://app.test.com:8180/com-pt-platform-portal/sysMessagecenter/doCountMessage
        log.debug("ForceLogoutFilter access:"+access);
        log.debug("ForceLogoutFilter longLinkAccess size:"+longLinkAccess.size());
        /*if(longLinkAccess == null || longLinkAccess.size() == 0){//未配置长链接过滤
        	return true;
        }*/
        if(longLinkAccess.contains(access)){
        	log.debug("ForceLogoutFilter longLinkAccess contain:"+access);
        	
        	long timestamp = System.currentTimeMillis();
        	if(hsr.getSession() != null){
        		Object timestampStr = hsr.getSession().getAttribute(ACCESSTOKEN);
        		if(timestampStr != null){
        			timestamp = Long.parseLong(timestampStr.toString());
        		}
        		
        	}
        	
        	//取得设置的超时时间 以秒为单位
        	int maxInactiveInterval = hsr.getSession().getMaxInactiveInterval();
        	log.debug("ForceLogoutFilter session maxInactiveInterval:"+maxInactiveInterval+" Seconds access timestamp:"+timestamp);
        	long current = System.currentTimeMillis();
        	long allowTime = maxInactiveInterval*1000 + timestamp;
        	log.debug("ForceLogoutFilter longLinkAccess allowTime:" + allowTime + " currenttime:" + current + " match result:" + (current >= allowTime));
        	if(current >= allowTime ){//超出合法访问时间,session已经失效
        		hsr.getSession().setAttribute(JYPT_SECURITY_CODE, JYPT_SECURITY_CODE_TIMEOUT);
        		return false;
        	}
        }else{
        	//非论询请求生成时间戳
        	hsr.getSession().setAttribute(ACCESSTOKEN, System.currentTimeMillis());
        }
        return true;
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
    	log.debug("ForceLogoutFilter onAccessDenied begin");
    	//重新跳转到登录页面,cas统一管理页面,并传入暂时不允许登录标识
    	HttpServletRequest r = (HttpServletRequest)request;
    	HttpServletResponse res = (HttpServletResponse)response;
    	
    	String requestUrl = r.getRequestURL().toString();
        String params = r.getQueryString();
        log.debug("ForceLogoutFilter onAccessDenied request " + requestUrl + "?" + params);
    	//判断是否是ajax请求
    	String header = r.getHeader("X-Requested-With");  
    	log.debug("ForceLogoutFilter onAccessDenied is ajax request:"+header);
        boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
        
        String jump = forceUrl;
        Enum checkFirstLogin = null;
        /*if(customShiroSessionDAO != null){
        	
        }*/
        checkFirstLogin = firstLoginHandle.checkFirstLogin(request, response, simpleCookie, customShiroSessionDAO);
        if(FirstLoginHandle.CONNECTSTATUS.FIRSTLOGIN.equals(checkFirstLogin)){
        	 jump = getLoginUrl();
             log.debug("ForceLogoutFilter onAccessDenied status normal,first login:"+jump);
        }else if(FirstLoginHandle.CONNECTSTATUS.TIMEOUT.equals(checkFirstLogin)){
        	//判断跳转链接追加业务错误码
        	jump = jump.concat("?bussinessCode="+JYPT_SECURITY_CODE_TIMEOUT);
        	log.debug("ForceLogoutFilter onAccessDenied status timeout,redirect:"+jump);
        }else{
        	//判断跳转链接追加业务错误码
        	String securityCode = getSecurityCode(r);
            jump = jump.concat("?bussinessCode="+securityCode);
        }
        
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
        log.debug("ForceLogoutFilter onAccessDenied issueRedirect:"+jump);
        return false;
    }
    /**
     * 取得安全策略错误码,默认返回强制退出,不允许登录
     * @param r
     * @return
     */
    private String getSecurityCode(HttpServletRequest r){
    	
    	String securityCode =  getAttributeFromSession(r, JYPT_SECURITY_CODE);
        if(securityCode != null){
        	return securityCode ;
        }else{
        	return JYPT_SECURITY_CODE_QUIENT;
        }
    }
    /**
     * 从session中取属性值,获取不到为null
     * @param r
     * @param attribute
     * @return
     */
    private String getAttributeFromSession(HttpServletRequest r,String attribute){
    	if(r.getSession() == null){
    		return null;
    	}
    	Object resultObj = r.getSession().getAttribute(attribute);
    	if(resultObj != null){
    		return (String)resultObj;
    	}
    	
    	return null;
    	
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

	public List getLongLinkAccess() {
		return longLinkAccess;
	}

	public void setLongLinkAccess(List longLinkAccess) {
		this.longLinkAccess = longLinkAccess;
	}
	public String getSameSessionSet() {
	
		return sameSessionSet;
	}
	public void setSameSessionSet(String sameSessionSet) {
		
		this.sameSessionSet = sameSessionSet;
	}
	public void setSimpleCookie(SimpleCookie simpleCookie) {
		this.simpleCookie = simpleCookie;
	}
	public void setCustomShiroSessionDAO(AbstractSessionDAO customShiroSessionDAO) {
		this.customShiroSessionDAO = customShiroSessionDAO;
	}
	
}
