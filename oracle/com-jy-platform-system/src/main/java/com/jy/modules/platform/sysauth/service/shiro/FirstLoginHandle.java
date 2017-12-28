package com.jy.modules.platform.sysauth.service.shiro;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
/**
 * @ClassName: FirstLoginHandle 
 * @Description: 通过cookie判断是否是第一次登录
 * 第一次登录时,并未生成cookie
 * 第二次登录时,已生成cookie
 * 超时,cookie存在,持久化sesssion已过期
 * @author luoyr
 * @date 2015年6月2日 下午5:19:14 
 *
 */
@Component
@Qualifier("com.jy.modules.platform.sysauth.service.shiro.FirstLoginHandle")
public class FirstLoginHandle {
	private static final Logger log = LoggerFactory.getLogger(FirstLoginHandle.class);
	/***请求状态***/
	public enum CONNECTSTATUS{NORMAL,FIRSTLOGIN,TIMEOUT}
	
	/**
	 * 判断是否第一次请求或超时
	 * CONNECTSTATUS.NORMAL 正常 即第二次请求以上
	 * CONNECTSTATUS.FIRSTLOGIN 第一次请求
	 * CONNECTSTATUS.TIMEOUT 超时
	 * @param request
	 * @param response
	 * @param simpleCookie
	 * @param customShiroSessionDAO
	 * @return
	 */
	public Enum checkFirstLogin(ServletRequest request,
			ServletResponse response, SimpleCookie simpleCookie,
			AbstractSessionDAO customShiroSessionDAO) {
		HttpServletRequest r = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Cookie cookies[] = r.getCookies();
		if (cookies == null || cookies.length == 0) {
			log.debug("FirstLoginHandle result: first login");
			return CONNECTSTATUS.FIRSTLOGIN;
		}
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				String cookieName = simpleCookie.getName();
				String sessionId = cookies[i].getValue();
				if (cookies[i].getName().equals(cookieName)) {

					try {
						
				    	if(customShiroSessionDAO != null){
				    		//持久化判断方法
							Session session = customShiroSessionDAO
									.readSession(sessionId);
							if (session == null) {
								log.warn("FirstLoginHandle result: has login ,but session is valid  null,["+sessionId+"]");
								return CONNECTSTATUS.TIMEOUT;
								
							}
				    	}else{
				    		//新旧cookie与session id对比方法
				    		Subject currentSubject = SecurityUtils.getSubject();
				    		Serializable currentSessionId = currentSubject.getSession().getId();
				    		if(!sessionId.equals(currentSessionId)){//当前session id不与cookie相同,则表明已新创建session即已超时
				    			log.warn("FirstLoginHandle result: has login ,but old session is reset a new session ,["+sessionId+"]->["+currentSessionId+"]");
								return CONNECTSTATUS.TIMEOUT;
				    		}
				    	}
						
					}
					catch(UnknownSessionException e){
							log.warn("FirstLoginHandle result: has login ,but session is valid  exception,["+sessionId+"]");
							return CONNECTSTATUS.TIMEOUT;
						
					} catch (InvalidSessionException e) {
						log.warn("FirstLoginHandle result: has login ,but session is valid  exception,["+sessionId+"]");
						return CONNECTSTATUS.TIMEOUT;
					}
					
				}
			}
		}
		return CONNECTSTATUS.NORMAL;
	}
	public static void main(String[] args) {
		Enum t = FirstLoginHandle.CONNECTSTATUS.NORMAL;
		System.out.println(t.equals(FirstLoginHandle.CONNECTSTATUS.FIRSTLOGIN));
	}
}
