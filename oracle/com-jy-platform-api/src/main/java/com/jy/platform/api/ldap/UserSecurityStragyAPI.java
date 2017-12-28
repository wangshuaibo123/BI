package com.jy.platform.api.ldap;
/**
 * 用户安全策略
 * @author luoyr
 *
 */
public interface UserSecurityStragyAPI {
	
	/**
	 * 密码复杂度校验,符合条件返回true,不符合返回false;当未设置密码复杂度正则,返回true
	 * @param pwd
	 * @throws Exception
	 */
	public boolean checkPwdQuality(String pwd)  throws Exception ;
	
	
}
