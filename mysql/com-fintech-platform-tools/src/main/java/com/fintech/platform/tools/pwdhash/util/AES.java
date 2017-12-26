package com.fintech.platform.tools.pwdhash.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 * aes中的key的位数有要求
 * AES-128-CBC加密模式，key需要为16位(128/8=16)
 * 与js对应
 * <pre>
 * 引入
<script src="mode-cbc.js"></script>
<script src="../rollups/md5.js"></script>
<script src="../rollups/aes.js"></script>
 *  <script>
   function encryptAes(word){  
         var key = CryptoJS.enc.Utf8.parse("2015030120123457");   
         var iv  = CryptoJS.enc.Utf8.parse('2015030120123456');   
         var srcs = CryptoJS.enc.Utf8.parse(word);  
         var encrypted = CryptoJS.AES.encrypt(srcs, key, { iv: iv,mode:CryptoJS.mode.CBC});  
         return encrypted.toString();  
    }  
	function decryptAes(word){  
         var key = CryptoJS.enc.Utf8.parse("2015030120123457");   
         var iv  = CryptoJS.enc.Utf8.parse('2015030120123456');   
		 var srcs = CryptoJS.enc.Utf8.parse(word);  
         var decrypt = CryptoJS.AES.decrypt(word, key, { iv: iv,mode:CryptoJS.mode.CBC});  
         return CryptoJS.enc.Utf8.stringify(decrypt).toString();   
    } 
   var message = "123456";
   var e = encryptAes(message);
   console.log(e);
   var d = decryptAes(e);
   console.log(d);
</script>
</pre>
 * @author
 *
 */
public class AES {
	/***默认向量常量**/
	public static final String IV = "2015030120123456";
	/**
	 * 加密
	 * @param message
	 * @param key AES-128-CBC加密模式，key需要为16位(128/8=16)
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String message, String key) throws Exception {
		if (key == null) {
			return null;
		}
		// 判断Key是否为16位
		if (key.length() != 16) {
			return null;
		}
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(message.getBytes());

		return Base64.encodeBase64String(encrypted);
	}

	/**
	 * 解密
	 * @param message
	 * @param key AES-128-CBC加密模式，key需要为16位(128/8=16)
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String message, String key) throws Exception {
		try {
			// 判断Key是否正确
			if (key == null) {
				return null;
			}
			// 判断Key是否为16位
			if (key.length() != 16) {
				return null;
			}
			byte[] raw = key.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(
					IV.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decodeBase64(message);
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		String message = "123456";
		String key = "2015030120123457";
		System.out.println(key.length());
		String e = encrypt(message, key);
		System.out.println(e);
		String decrypt = decrypt(e, key);
		System.out.println(decrypt);
		System.out.println(System.currentTimeMillis()/1000);
	}
}
