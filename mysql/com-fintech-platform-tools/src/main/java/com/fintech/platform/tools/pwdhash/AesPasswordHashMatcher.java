package com.fintech.platform.tools.pwdhash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.platform.tools.pwdhash.util.HashUtils;
/**
 * ase加密算法hash pwd
 * 可逆,需要添加加密密钥 属于对称算法,每一次加密后的byte[]值不一样
 * @author
 *
 */
public class AesPasswordHashMatcher implements PasswordHashMatcher{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private PasswordHashMatcher matcher;
	/*****加密密钥*****/
	private String pkey;
	
    public AesPasswordHashMatcher(PasswordHashMatcher matcher){
    	super();
    	this.matcher = matcher;
    }
	
	public byte[] hash(byte[] waitPwd) {
		byte[] middleValue = matcher.hash(waitPwd);
		logger.debug("AesPasswordHashMatcher pwd hash by ase");
		if(pkey == null || "".equals(pkey)){
			pkey = "JY";
		}
		logger.debug("AesPasswordHashMatcher pwd hash key:"+pkey+" input:"+new String(middleValue));
		byte[] ase = null;
		
		ase = HashUtils.aseEncrypt(new String(middleValue), pkey);
		
		logger.debug("AesPasswordHashMatcher pwd hash key:"+pkey+" result:"+ase);
		return ase;
	}

	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	
}
