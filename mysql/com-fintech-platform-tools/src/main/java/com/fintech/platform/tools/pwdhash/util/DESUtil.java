package com.fintech.platform.tools.pwdhash.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import jodd.util.Base64;

/**
 * 系统内部向外提供接口加密工具类
 * 单例模式创建对象
 * @author guost
 *
 */
public class DESUtil {
	// 算法名称  
    public static final String KEY_ALGORITHM = "DESede";  
    // 算法名称/加密模式/填充方式  
    public static final String CIPHER_ALGORITHM_CBC = "DESede/CBC/PKCS5Padding";  
    
    public static byte[] DefaultIV = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
    
    private SecretKey secretKey;
    private Cipher cipher;
    private byte[] encryptData;
    private AlgorithmParameterSpec iv;
    
    private static DESUtil tripleDES = null;
    
    public static DESUtil getInstance(String key) throws Exception {
    	
    	if (tripleDES == null) {
    		synchronized (DESUtil.class) {
    			tripleDES = new DESUtil(key,"CBC");
			}
    	}
    	return tripleDES;
    }
    
    public DESUtil(String key,String mode) {        
        if("CBC".equals(mode)) {
        	//测试密钥--》RRYa6li5NGFodgKUtvS1I6fZwY8xpJjI
        	//生产密钥--》dgKU8xpYaRivfJS1R5NGFotZwY6lI6jI      访问前缀：http://rf-loan.jdpay.com/
        	byte[] keyData = Base64.decode(key);
            try {
				cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
            secretKey = new SecretKeySpec(keyData, KEY_ALGORITHM);
            iv = new IvParameterSpec(DefaultIV);
        }  
    }
    
    /**
     * 初始向量8位
     * @return
     */
    byte[] getIV() {  
    	return DefaultIV;
    }
    
    /** 
     * 加密 
     * @param str 
     * @return byte[]
     * @throws Exception 
     */  
    public byte[] encrypt(String str) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);  
        return encryptData = cipher.doFinal(str.getBytes("utf-8"));  
    }
    
    /** 
     * 解密 
     * @param encrypt 
     * @return byte[]
     * @throws Exception 
     */  
    public byte[] decrypt(byte[] encrypt) throws Exception {  
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);  
        return encryptData = cipher.doFinal(encrypt);  
    }
    
    /** 
     * TripleDes三层加密,并且base64编码
     * 默认utf-8
     * @param str 
     * @return String
     * @throws Exception 
     */  
    public String encryptStr(String str) throws Exception {  
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        encryptData = cipher.doFinal(str.getBytes("utf-8"));
        
        return Base64.encodeToString(encryptData);
    }
    
    /** 
     * 解密 返回字符串 ,,并且base64解码
     * 默认utf-8
     * @param encrypt 
     * @return String
     * @throws Exception 
     */  
    public String decryptStr(String data) throws Exception {
    	byte[] encrypt = Base64.decode(data);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        encryptData = cipher.doFinal(encrypt);
        
        return new String(encryptData,"UTF-8");
    }
    
    public static void main(String[] args) throws Exception {  
    	
    }
}	
