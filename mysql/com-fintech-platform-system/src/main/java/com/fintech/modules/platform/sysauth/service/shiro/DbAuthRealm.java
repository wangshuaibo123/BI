package com.fintech.modules.platform.sysauth.service.shiro;

import java.io.Serializable;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysauth.SysAuthAPI;
import com.fintech.platform.tools.common.DateUtil;
import com.google.common.base.Objects;
/**
 * @ClassName: DbAuthRealm 
 * @Description: 登录认证与授权处理-数据库方式认证 
 * @author
 * @date 2015年6月2日 下午5:08:39 
 *
 */
public class DbAuthRealm extends AuthorizingRealm {
	
	private static final Logger logger = LoggerFactory.getLogger(DbAuthRealm.class);
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
	 * @Title: DbAuthRealm 
	 * @Description: 构造 初始化用户名密码加密码方式
	 */
    public DbAuthRealm() {
       super();
       // 设置认证token的实现类为用户名密码模式
       this.setAuthenticationTokenClass(UsernamePasswordToken.class);
       //设置验证方式，用户自行设定密码加密方式
       this.setCredentialsMatcher( new CredentialsMatcher() {   
           public boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info) {
        	
              UsernamePasswordToken upToken = (UsernamePasswordToken)token;
              String pwd = new String(upToken.getPassword());
              UserInfo user = orgAPI.getUserInfoByLoginName(upToken.getUsername());
//              if(user.getPassword().equals(DigestUtils.md5Hex(pwd))){
              if(user.getPassword().equals(pwd)){
                  //用户名及密码验证通过
                  return true;
              }
              //用户名或密码不正确
              return false;
           }
       });
    }
    /***
	 * @Title: doGetAuthenticationInfo 
	 * @Description: 认证
	 * @param  AuthenticationToken    认证令牌
	 * @return AuthenticationInfo    认证信息
	 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
           AuthenticationToken token) throws AuthenticationException {
      
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		UserInfo user = orgAPI.getUserInfoByLoginName(upToken.getUsername());
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }       
        SimpleAuthenticationInfo saInfo = new SimpleAuthenticationInfo(new ShiroUser(user.getUserId(),user.getLoginName(),user.getUserName()),user.getPassword(), this.getName());
        user.setLoginDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
        user.setLoginTime(DateUtil.format(new Date(), "HH:mm:ss"));
        this.setSession("userInfo", user);
        return saInfo;

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
 
    	ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
       //UserInfo user = orgAPI.getUserInfoByLoginName(shiroUser.loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(sysAuthAPI.findRoles(shiroUser.id));
		info.addStringPermissions(sysAuthAPI.findPermissions(shiroUser.id));
		return info;
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
    	if(currentUser!=null){
    		Session session = currentUser.getSession();
    		if(session!=null){
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

		public void setId(Long id) {
			this.id = id;
		}

		public String getLoginName() {
			return loginName;
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
			return name;
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
