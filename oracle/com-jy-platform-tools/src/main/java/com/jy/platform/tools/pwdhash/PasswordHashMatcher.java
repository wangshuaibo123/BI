package com.jy.platform.tools.pwdhash;
/**
 * 密码hash处理
 * @author luoyr
 *
 */
public interface PasswordHashMatcher {
	/**
	 *  hash密码 用户输入密码hash计算
	 * @param waitPwd 等待处理密码
	 * @return
	 */
	public byte[] hash(byte[] waitPwd);
	
}
