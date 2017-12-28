package com.jy.platform.tools.pwdhash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 加密算法入口
 * @author luoyr
 *
 */
public class BasePasswordHashMatcher implements PasswordHashMatcher{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private PasswordHashMatcher matcher;
	
	public BasePasswordHashMatcher(){
		
	}
	
    public BasePasswordHashMatcher(PasswordHashMatcher matcher){
    	super();
    	this.matcher = matcher;
    }
	
	public byte[] hash(byte[] waitPwd) {
		logger.debug("BasePasswordHashMatcher pwd hash input:"+new String(waitPwd));
		return waitPwd;
	}

	public byte[] dehash(byte[] waitPwd) {
		logger.debug("BasePasswordHashMatcher pwd dehash input:"+new String(waitPwd));
		return waitPwd;
	}

}
