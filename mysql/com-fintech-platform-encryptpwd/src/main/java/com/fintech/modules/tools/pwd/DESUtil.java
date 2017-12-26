package com.fintech.modules.tools.pwd;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * DES算法加密/解密工具
 * @author
 * @date: 2016年5月19日 
 */
public class DESUtil{
	private static final Logger logger =  LoggerFactory.getLogger(DESUtil.class);
    private static String CHARSETNAME = "UTF-8"; //编码
    private static String ALGORITHM = "DES"; //加密类型
    private static String PREFIX = "JY_PLATFORM_";//密匙前缀
 
    
    /**
     * 创建密匙对象
     * @param keyStr
     * @return
     */
    private static Key getKey(String keyStr){
    	Key key = null;
    	
    	try{
    		String finalKey = PREFIX + keyStr;
    		
    		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
    	    secureRandom.setSeed(finalKey.getBytes());  
    		
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            generator.init(secureRandom);
            key = generator.generateKey();
            generator = null;
        } 
        catch(Exception e){
        	logger.error("create key error:",e);
        	throw new RuntimeException(e);
        }
    	
    	return key;
    }
    
 
    /**
     * 加密
     * @param str
     * @param keyStr 密匙
     * @return
     */
    public static String getEncryptString(String str, String keyStr) {
        try {
            byte[] bytes = str.getBytes(CHARSETNAME);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            Key key = getKey(keyStr);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] doFinal = cipher.doFinal(bytes);
            return Base64.encodeBase64String(doFinal);
        } 
        catch(Exception e){
        	logger.error("encrypt key error:",e);
            throw new RuntimeException(e);
        }
    }
 
    
    /**
     * 解密
     * @param str
     * @param keyStr 密匙
     * @return
     */
    public static String getDecryptString(String str, String keyStr) {
        try {
            byte[] bytes = Base64.decodeBase64(str);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            Key key = getKey(keyStr);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] doFinal = cipher.doFinal(bytes);
            return new String(doFinal, CHARSETNAME);
        } 
        catch(Exception e){
        	logger.error("decrypt key error:",e);
            throw new RuntimeException(e);
        }
    }
    
}
