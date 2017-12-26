package com.fintech.platform.tools.pwdhash.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 * RC4中的key的位数有要求
 * RC4-128-ECB加密模式，key需要为16位(128/8=16)
 * 与js对应
 * <pre>
 * 引入
<script src="../rollups/md5.js"></script>
<script src="../rollups/rc4.js"></script>
<script src="mode-ecb.js"></script>
 *  <script>
   function encryptRC4(word){  
         var key = CryptoJS.enc.Utf8.parse("2015030120123457");   
         var iv  = CryptoJS.enc.Utf8.parse('2015030120123456');   
         var srcs = CryptoJS.enc.Utf8.parse(word);  
         var encrypted = CryptoJS.RC4.encrypt(srcs, key, {mode:CryptoJS.mode.ECB});  
         return encrypted.toString();  
    }  
	function decryptRC4(word){  
         var key = CryptoJS.enc.Utf8.parse("2015030120123457");   
         var iv  = CryptoJS.enc.Utf8.parse('2015030120123456');   
		 var srcs = CryptoJS.enc.Utf8.parse(word);  
         var decrypt = CryptoJS.RC4.decrypt(word, key, {mode:CryptoJS.mode.ECB});  
         return CryptoJS.enc.Utf8.stringify(decrypt).toString();   
    } 
   var message = "123456";
   var e = encryptRC4(message);
   console.log(e);
   var d = decryptRC4(e);
   console.log(d);
</script>
</pre>
 * @author
 *
 */
public class RC4 {
	/**
	 * 加密
	 * @param message
	 * @param key RC4-128-ECB加密模式，key需要为16位(128/8=16)
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String message, String key) throws Exception {
		if (key == null) {
			return null;
		}
		// 判断Key是否为16位
		/*if (key.length() != 16) {
			//return null;
		}*/
		byte[] raw = key.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "RC4");
		Cipher cipher = Cipher.getInstance("RC4/ECB/NoPadding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(message.getBytes());

		return Base64.encodeBase64String(encrypted);
	}

	/**
	 * 解密
	 * @param message
	 * @param key RC4-128-ECB加密模式，key需要为16位(128/8=16)
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
				//return null;
			}
			byte[] raw = key.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "RC4");
			Cipher cipher = Cipher.getInstance("RC4/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
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
		String key = "1234567890123456";
		System.out.println(key.length());
		String e = encrypt(message, key);
		System.out.println(e);
		String decrypt = decrypt("EYKghRHT", "1234567890123456");
		System.out.println(decrypt);
	}
}
