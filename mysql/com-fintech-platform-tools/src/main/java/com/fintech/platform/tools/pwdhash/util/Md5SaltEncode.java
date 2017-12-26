package com.fintech.platform.tools.pwdhash.util;

import java.security.MessageDigest;
/**
 * md5加盐算法
 * @author
 */
public class Md5SaltEncode {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private Object salt;
	private String algorithm;
	public static final String MD5="MD5";
	public static final String SHA="SHA";

	/**
	 * @param salt
	 * @param algorithm Md5SaltEncode.MD5  Md5SaltEncode.SHA
	 */
	public Md5SaltEncode(Object salt, String algorithm) {
		this.salt = salt;
		this.algorithm = algorithm;
	}
	/**
	 * 加密
	 * @param rawPass
	 * @return
	 */
	public String encode(String rawPass) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			// 加密后的字符串
			result = byteArrayToHexString(md.digest(mergePasswordAndSalt(
					rawPass).getBytes("utf-8")));
		} catch (Exception ex) {
		}
		return result;
	}
	/**
	 * 验证密码是否有效
	 * @param encPass
	 * @param rawPass
	 * @return
	 */
	public boolean isPasswordValid(String encPass, String rawPass) {
		String dest = ""+encPass;//保证非空
		String orgi = encode(rawPass);
		return dest.equals(orgi);
	}
	/**
	 * 加盐
	 * @param password
	 * @return
	 */
	private String mergePasswordAndSalt(String password) {
		if (password == null) {
			password = "";
		}

		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}
