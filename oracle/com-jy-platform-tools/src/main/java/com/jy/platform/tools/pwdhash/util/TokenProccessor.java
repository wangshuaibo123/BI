package com.jy.platform.tools.pwdhash.util;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 令牌生成
 * 令牌可作为解密的key
 * @author luoyr
 *
 */
public class TokenProccessor {
	private static final TokenProccessor instance = new TokenProccessor();
	protected Logger logger = LoggerFactory.getLogger(getClass());
	public static final String PWDTOKENKEY = "PWDTOKENKEY_JYPT";

	public static final String PWDTOKENKEY_SUFFIX = "JYC";

	public static TokenProccessor getInstance() {
		return instance;
	}
	/**
	 * 生成16位长度随机令牌
	 * @return
	 */
	public String makeToken() {
		String token = (System.currentTimeMillis() + new Random()
				.nextInt(999999999)) + PWDTOKENKEY_SUFFIX;
		logger.debug("TokenProccessor makeToken:"+token+" lenght:"+token.length());
		return token;
	}
	/**
	 * 将生成的二进制令牌存于session中
	 * 然后经过md5 16位处理返回,供客户端调用
	 * 服务器端使用令牌时,要求从session取出byte[]令牌,需要手动进行md5 16位处理
	 * @param request
	 */
	public String putTokenToSession(final HttpServletRequest request) {
		String makeToken = makeToken();
		request.getSession().setAttribute(TokenProccessor.PWDTOKENKEY, makeToken.getBytes());
		String token = HashUtils.MD5R16(makeToken);
		logger.debug("TokenProccessor token md5R16 handle:"+token+" lenght:"+token.length());
		return token;
	}
	/**
	 * 从session中获取令牌,当session中无令牌返回null
	 * @param request
	 * @return
	 */
	public String obtainTokenFromSession(final HttpServletRequest request){
		Object serverTokenObj = request.getSession().getAttribute(TokenProccessor.PWDTOKENKEY);
		if(serverTokenObj == null){
			return null;
		}
		byte[] serverToken = (byte[])serverTokenObj;
		String token = HashUtils.MD5R16(new String(serverToken));
		removeToke(request);//移除
		return token;
	}
	
	/**
	 * 判断token
     * 有效token则返回,无效不允许继续操作则返回null
     * @param request
     * @return
     */
    public String checkToken(final HttpServletRequest request){
    	String clientToken = request.getParameter(TokenProccessor.PWDTOKENKEY);
    	String serverToken = (String) request.getSession().getAttribute(TokenProccessor.PWDTOKENKEY);
    	if(!StringUtils.isEmpty(serverToken) && (serverToken+"").equals((clientToken+""))){
    		logger.debug("TokenProccessor checkToken:"+serverToken);
    		return serverToken;
    	}
    	logger.debug("TokenProccessor checkToken fail,return null");
    	return null;
    }
    /**
     * 移除token
     * @param request
     */
    public void removeToke(final HttpServletRequest request){
    	request.getSession().removeAttribute(TokenProccessor.PWDTOKENKEY);//移除session中的token
    }
    
	public static void main(String[] args) {
		System.out.println(TokenProccessor.getInstance().makeToken().length());
	}

}
