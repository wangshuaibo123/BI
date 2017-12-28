package com.jy.platform.tools.pwdhash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.platform.tools.pwdhash.util.Md5SaltEncode;
/**
 * Md5加盐加密算法hash pwd
 * @author luoyr
 *
 */
public class Md5SaltPasswordHashMatcher implements PasswordHashMatcher{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private PasswordHashMatcher matcher;
	private String salt;
	
    public Md5SaltPasswordHashMatcher(PasswordHashMatcher matcher){
    	super();
    	this.matcher = matcher;
    }
	
	public byte[]  hash(byte[] waitPwd) {
		byte[] middleValue = matcher.hash(waitPwd);
		if(salt == null || "".equals(salt)){
			salt="jysalt";
		}
		logger.debug("Md5SaltPasswordHashMatcher pwd hash by Md5Salt");
		Md5SaltEncode e = new Md5SaltEncode(salt, Md5SaltEncode.MD5);
		String md5salt = e.encode(new String(middleValue));
		logger.debug("Md5SaltPasswordHashMatcher pwd hash result:"+md5salt);
		return md5salt.getBytes();
		
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}



}
