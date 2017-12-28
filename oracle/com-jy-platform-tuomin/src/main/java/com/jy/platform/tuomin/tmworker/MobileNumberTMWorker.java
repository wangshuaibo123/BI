package com.jy.platform.tuomin.tmworker;

/**
 * 手机号，脱敏后四位——采用和固话号码相同的规则
 * 
 * @author dell
 * 
 */
public class MobileNumberTMWorker {

	public static String getNewValue(String oldValue) {
		return PhoneNumberTMWorker.getNewValue(oldValue);
	}

	public static void main(String[] args) {
		System.out.println("123=" + getNewValue("123"));
		System.out.println("1234=" + getNewValue("1234"));
		System.out.println("12345=" + getNewValue("12345"));
		System.out.println("13812345678=" + getNewValue("13812345678"));
		System.out.println("13812345678=" + getNewValue("13812345678"));
	}
}
