package com.jy.modules.platform.sysauth.service.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.ShardedJedis;
import redis.clients.util.SafeEncoder;

import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.ehcache.ObtainPropertiesInfo;
import com.jy.platform.core.redis.JedisSentinelPool;

/**
 * 用于过滤一个用户名同一时间只能有一个session登录
 */
public class SingleLoginFilter extends AccessControlFilter{
	private static final Log logger = LogFactory.getLog(SingleLoginFilter.class);  
	
	@Autowired
	private JedisSentinelPool pool;
	
	private SessionManager sessionManager;
	
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		Subject currentSubject = SecurityUtils.getSubject();
		Session session = currentSubject.getSession();
		
		//如果没有登录，直接进行之后的流程
		if(!currentSubject.isAuthenticated()){
			return true;
		}
		
		//如果被踢出了，直接退出，重定向到登录页
        if (session.getAttribute("kickout") != null) {
            try {
            	currentSubject.logout();
            } catch (Exception e) { 
            }
            //saveRequest(request);
            WebUtils.issueRedirect(request, response, ((HttpServletRequest)request).getContextPath());
            return false;
        }
        
		if(pool != null){
			UserInfo currentUser = null;
			ShardedJedis jedis = null;
			try{
				currentUser = (UserInfo)session.getAttribute("userInfo");
				jedis = pool.getResource();
				
				//获取当前用户登录session信息
				byte[] sessionIdBytes = jedis.hget(SafeEncoder.encode(getCacheKey(currentUser)), SafeEncoder.encode(currentUser.getLoginName()));
				String currentSessionId = (String)currentSubject.getSession().getId();
				
				//redis中有当前用户session信息
				if(sessionIdBytes!=null && sessionIdBytes.length>0){
					String sessionId = new String(sessionIdBytes);
					
					//之前登录的session与当前的不同
					if(sessionIdBytes!=null && sessionIdBytes.length>0 && currentSessionId!=null && !"".equals(currentSessionId)
							&& !currentSessionId.equals(sessionId)){
						Session kickoutSession = null;
						try{
							kickoutSession = sessionManager.getSession(new DefaultSessionKey(sessionId));
						}
						catch(SessionException e){
						}
						
		                if(kickoutSession != null) {
		                    //设置会话的kickout属性表示踢出了
		                    kickoutSession.setAttribute("kickout", true);
		                }
						
		                //替换为当前sessionId
		                jedis.hset(SafeEncoder.encode(getCacheKey(currentUser)), SafeEncoder.encode(currentUser.getLoginName()), currentSessionId.getBytes());
					}
				}
				//redis没有当前用户session信息
				else{
					jedis.hset(SafeEncoder.encode(getCacheKey(currentUser)), SafeEncoder.encode(currentUser.getLoginName()), currentSessionId.getBytes());
				}
			}
			catch(Exception e){
				logger.error("SingleLoginFilter error:", e);
			}
			finally{
				if(jedis != null){
					pool.returnResource(jedis);
				}
			}
		}
		
		return true;
	}

	
	private String getCacheKey(UserInfo currentUser){
		StringBuilder sb = new StringBuilder();
		sb.append("shiro-login-session:")
		  .append(ObtainPropertiesInfo.getValByKey("app.code"));
		return sb.toString();
	}
}
