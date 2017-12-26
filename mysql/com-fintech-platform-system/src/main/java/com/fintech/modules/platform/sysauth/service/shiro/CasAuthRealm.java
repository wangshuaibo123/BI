package com.fintech.modules.platform.sysauth.service.shiro;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.Saml11TicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysauth.SysAuthAPI;
import com.fintech.platform.tools.common.DateUtil;
import com.google.common.base.Objects;

/**
 * @ClassName: CasAuthRealm 
 * @Description: 登录认证与授权处理-单点登录方式 
 * @author
 * @date 2015年6月2日 下午5:08:39 
 *
 */
public class CasAuthRealm extends CasRealm {
	private static final Logger log = LoggerFactory.getLogger(CasAuthRealm.class); 
	/***机构api**/
	protected OrgAPI orgAPI;
	/*****授权api*****/
	protected SysAuthAPI sysAuthAPI;

	public OrgAPI getOrgAPI() {
		return orgAPI;
	}

	public void setOrgAPI(OrgAPI orgAPI) {
		this.orgAPI = orgAPI;
	}

	public SysAuthAPI getSysAuthAPI() {
		return sysAuthAPI; 
	}

	public void setSysAuthAPI(SysAuthAPI sysAuthAPI) {
		this.sysAuthAPI = sysAuthAPI;
	}
	
	/***
	 * @Title: doGetAuthenticationInfo 
	 * @Description: 认证
	 * @param  AuthenticationToken    认证令牌
	 * @return AuthenticationInfo    认证信息
	 */
	 @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		 	//AuthenticationInfo doGetAuthenticationInfo = super.doGetAuthenticationInfo(token);
		 
		 	AuthenticationInfo authenticationInfo = null;

		 	CasToken casToken = (CasToken) token;
		
	        if (token == null) {
	        	authenticationInfo = null;
	        }
	        
	        String ticket = (String)casToken.getCredentials();
	        if (!StringUtils.hasText(ticket)) {
	        	authenticationInfo =  null;
	        }
	        org.jasig.cas.client.validation.TicketValidator ticketValidator = null;
			if ("saml".equalsIgnoreCase(getValidationProtocol())) {
				Saml11TicketValidator samTicketValidator = (Saml11TicketValidator) ensureTicketValidator();
				samTicketValidator.setEncoding("UTF-8");
				ticketValidator= samTicketValidator;
			}else{
				Cas20ServiceTicketValidator cas20TicketValidator = (Cas20ServiceTicketValidator) ensureTicketValidator();
				cas20TicketValidator.setEncoding("UTF-8");
				ticketValidator = cas20TicketValidator;
			}

	        log.debug("CasAuthRealm AuthenticationInfo doGetAuthenticationInfo ticket:"+ticket);
	        //org.jasig.cas.client.validation.TicketValidator ticketValidator = ensureTicketValidator();
	         //使用代理ticketValidator
	        //Cas20ServiceTicketValidator ticketValidator = (Cas20ServiceTicketValidator) ensureTicketValidator();
	        //ticketValidator.setEncoding("UTF-8");
	        //ticketValidator.setProxyGrantingTicketStorage(new EhcacheBackedProxyGrantingTicketStorageImpl());

	        try {
	            // contact CAS server to validate service ticket
	        	Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
	            // get principal, user id and attributes
	        	AttributePrincipal casPrincipal = casAssertion.getPrincipal();
	        	
	            String userName = casPrincipal.getName();
	    		UserInfo user = orgAPI.getUserInfoByLoginName(userName);
	    		if(user.getUserId() == null 
	    				|| org.apache.commons.lang3.StringUtils.isEmpty(user.getLoginName()) 
	    				|| org.apache.commons.lang3.StringUtils.isEmpty(user.getUserName())){
	    			//如果 用户ID 用户名 或用户姓名 为null 则报错
	    			String msg = " doGetAuthenticationInfo obtaion userinfo is null userid:"+ userName;
	    			log.error(msg);
	    			 throw new CasAuthenticationException(msg);
	    		}
	            log.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[]{
	                    ticket, getCasServerUrlPrefix(), userName
	            });

	            Map<String, Object> attributes = casPrincipal.getAttributes();
	            // refresh authentication token (user id + remember me)
	            casToken.setUserId(userName);
	            String rememberMeAttributeName = getRememberMeAttributeName();
	            String rememberMeStringValue = (String)attributes.get(rememberMeAttributeName);
	            boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
	            if (isRemembered) {
	                casToken.setRememberMe(true);
	            }
	            // create simple authentication info
	            authenticationInfo = new  SimpleAuthenticationInfo(new ShiroUser(user.getUserId(),user.getLoginName(),user.getUserName()),ticket, getName());
	            //authenticationInfo =  new SimpleAuthenticationInfo(new ShiroUser(user.getUserId(),user.getLoginName(),user.getUserName()),user.getPassword(), this.getName());;
	            
	    		user.setLoginDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
	            user.setLoginTime(DateUtil.format(new Date(), "HH:mm:ss"));
	            this.setSession("userInfo", user);
	        } catch (TicketValidationException e) { 
	            throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
	        }
		 
		 String primaryPrincipal = authenticationInfo.getPrincipals().getPrimaryPrincipal().toString();
		 log.debug("AuthenticationInfo login user:"+primaryPrincipal);
        return authenticationInfo;
    }
 
    
	/***
	 * @Title: doGetAuthorizationInfo 
	 * @Description: 授权
	 * @param  PrincipalCollection    认证信息
	 * @return AuthorizationInfo    授权信息
	 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
           PrincipalCollection principals) {
    	log.debug("doGetAuthorizationInfo permission begin");
    	String username = principals.getPrimaryPrincipal().toString(); //
    	if(username!=null){//cas 和本地数据库中的登录名称不一致
    		UserInfo user = orgAPI.getUserInfoByLoginName(username);
    		if(user.getUserId() == null 
    				|| org.apache.commons.lang3.StringUtils.isEmpty(user.getLoginName()) 
    				|| org.apache.commons.lang3.StringUtils.isEmpty(user.getUserName())){
    			//如果 用户ID 用户名 或用户姓名 为null 则报错
    			log.error("doGetAuthorizationInfo permission userid is null");
    			return null;
    		}
    		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    		info.addRoles(sysAuthAPI.findRoles(user.getUserId()));
    		
    		Set<String> findPermissions = sysAuthAPI.findPermissions(user.getUserId());
    		info.addStringPermissions(findPermissions);
    		info.addRole("cas_home");//为cas每一个用户添加默认访问首页权限
//    		user.setLoginDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
//            user.setLoginTime(DateUtil.format(new Date(), "HH:mm:ss"));
//            this.setSession("userInfo", user);
    		log.debug("doGetAuthorizationInfo permission end");
    		return info;
    	}
    	return null;
    	
    }
    /***
   	 * @Title: clearCachedAuthorizationInfo 
   	 * @Description: 清除授权信息
   	 * @param  PrincipalCollection    认证信息
   	 * @return void    
   	 */
    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection pc) {
       SimplePrincipalCollection principals= new SimplePrincipalCollection(pc, getName()); 
       super.clearCachedAuthorizationInfo(pc);
    }
    
    private void setSession(Object key, Object value){
    	Subject currentUser = SecurityUtils.getSubject();
    	if(currentUser != null){
    		Session session = currentUser.getSession();
    		if(session != null){
    			session.setAttribute(key, value);
    		}
    	}
    }
    
    /**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long id;
		public String loginName;
		public String name;

		public ShiroUser(Long id, String loginName, String name) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
		public Long getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}
	}
 
}
