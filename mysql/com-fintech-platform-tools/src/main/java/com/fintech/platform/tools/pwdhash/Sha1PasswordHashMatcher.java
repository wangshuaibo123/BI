package com.fintech.platform.tools.pwdhash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.platform.tools.pwdhash.util.HashUtils;
/**
 * sha1加密算法hash pwd
 * @author
 *
 */
public class Sha1PasswordHashMatcher implements PasswordHashMatcher{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private PasswordHashMatcher matcher;
	
	public Sha1PasswordHashMatcher(){
		
	}
	
    public Sha1PasswordHashMatcher(PasswordHashMatcher matcher){
    	super();
    	this.matcher = matcher;
    }
	
	public byte[] hash(byte[] waitPwd) {
		
		byte[] middleValue = matcher.hash(waitPwd);
		
		logger.debug("Sha1PasswordHashMatcher pwd hash by Sha1");
		String sha1 = HashUtils.SHA1(new String(middleValue));
		logger.debug("Sha1PasswordHashMatcher pwd hash result:"+sha1);
		return sha1.getBytes();
	}



}
