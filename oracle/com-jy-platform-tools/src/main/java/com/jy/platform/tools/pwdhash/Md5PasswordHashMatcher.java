package com.jy.platform.tools.pwdhash;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.platform.tools.pwdhash.util.HashUtils;
/**
 * Md5加密算法hash pwd
 * @author luoyr
 *
 */
public class Md5PasswordHashMatcher implements PasswordHashMatcher{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private PasswordHashMatcher matcher;
	
    public Md5PasswordHashMatcher(PasswordHashMatcher matcher){
    	super();
    	this.matcher = matcher;
    }
	
	public byte[]  hash(byte[] waitPwd) {
		byte[] middleValue = matcher.hash(waitPwd);
		logger.debug("Md5PasswordHashMatcher pwd hash by Md5:"+Base64.encodeBase64String(middleValue));
		String md5 = HashUtils.MD5(middleValue);
		logger.debug("Md5PasswordHashMatcher pwd hash result:"+md5);
		return md5.getBytes();
		
	}

}
