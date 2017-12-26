package com.fintech.platform.tools.pwdhash.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import jodd.util.Base64;

/**
 * 京东接口加密工具类
 * 单例模式创建对象
 * @author guost
 *
 */
public class TripleDES {
	// 算法名称  
    public static final String KEY_ALGORITHM = "DESede";  
    // 算法名称/加密模式/填充方式  
    public static final String CIPHER_ALGORITHM_CBC = "DESede/CBC/PKCS5Padding";  
    
    public static byte[] DefaultIV = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
    
    private SecretKey secretKey;
    private Cipher cipher;
    private byte[] encryptData;
    private AlgorithmParameterSpec iv;
    
    private static TripleDES tripleDES = null;
    
    public static TripleDES getInstance(String key) throws Exception {
    	
    	if (tripleDES == null) {
    		synchronized (TripleDES.class) {
    			tripleDES = new TripleDES(key,"CBC");
			}
    	}
    	return tripleDES;
    }
    
    public TripleDES(String key,String mode) {        
        if("CBC".equals(mode)) {
        	//TODO 这个要写到配置文件里
        	//测试密钥--》RRYa6li5NGFodgKUtvS1I6fZwY8xpJjI
        	//生产密钥--》dgKU8xpYaRivfJS1R5NGFotZwY6lI6jI      访问前缀：http://rf-loan.jdpay.com/
        	if (StringUtils.isEmpty(key)) {
        		key = "dgKU8xpYaRivfJS1R5NGFotZwY6lI6jI";
        	}
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
    	
        TripleDES tripleDES = TripleDES.getInstance("dgKU8xpYaRivfJS1R5NGFotZwY6lI6jI");
        Test1();
//        String data = "\"address\":\"北京市海淀区清河永泰园新地标\",\"applyNo\":\"09ecd729d09e461bb9ad0329ecddf163\",\"applyReason\":\"贷款测试\",\"creditAmount\":10,\"creditReason\":\"系统通过\",\"ext\":{},\"idCard\":\"101123\",\"jdPin\":\"washingtin\",\"name\":\"郭沈厅\",\"phone\":\"15120059711\",\"reqDate\":\"20160823135900\",\"returnParams\":\"\",\"riskRemark\":\"无\"";
//        System.out.println(HashUtils.MD5(data));
//        tripleDES.testEncrypt();
        tripleDES.testDecrypt();
    }
    
    public void testEncrypt() throws Exception {
    	String encryptstr = ("\"address\":\"北京市海淀区清河永泰园新地标\",\"applyNo\":\"09ecd729d09e461bb9ad0329ecddf163\",\"applyReason\":\"贷款测试\",\"creditAmount\":10,\"creditReason\":\"系统通过\",\"ext\":{},\"idCard\":\"101123\",\"jdPin\":\"washingtin\",\"name\":\"郭沈厅\",\"phone\":\"15120059711\",\"reqDate\":\"20160823135900\",\"returnParams\":\"\",\"riskRemark\":\"无\""); 
    	byte[] encryptData = encrypt(encryptstr);
        String enStr = encryptStr(encryptstr);
        System.out.println(new String(encryptData) + "加密后：" + enStr);
        String encodeStr = Base64.encodeToString(encryptData);
        System.out.println("base64加密后：" + encodeStr);
    }
    
    public void testDecrypt() throws Exception {
        String decodestr = "mrGJ/vVu0tsejmL56HP0PE5rUUxExWx67KzdoYhAk1BuCnanNeJq33zSfj9WfV3BYgERXZu/7j2bfBj7036jyT8r1VxPsdn+2sROuAyX5K/uRLu7ftdn9dWNXbnJe941";
        byte[] encryptData = Base64.decode(decodestr);
        System.out.println("解密后："+ new String(decrypt(encryptData)));
    }
    
    public static void Test1() throws Exception{
		String base64Str = "mrGJ/vVu0tsejmL56HP0PE5rUUxExWx67KzdoYhAk1BuCnanNeJq33zSfj9WfV3BYgERXZu/7j2bfBj7036jyT8r1VxPsdn+2sROuAyX5K/uRLu7ftdn9dWNXbnJe941";
		byte[] encData = Base64.decode(base64Str);
		Cipher enCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		byte[] keyData = Base64.decode("dgKU8xpYaRivfJS1R5NGFotZwY6lI6jI");
	    byte[] DefaultIV = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
	    AlgorithmParameterSpec iv = new IvParameterSpec(DefaultIV);
		SecretKey key = new SecretKeySpec(keyData, "DESede");
		enCipher.init(Cipher.DECRYPT_MODE, key,iv);
		byte[] decryptResult = enCipher.doFinal(encData);
		System.out.println(new String(decryptResult,"UTF-8"));
	}
}	
