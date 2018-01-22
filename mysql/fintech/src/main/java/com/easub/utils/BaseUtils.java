package com.easub.utils;

import java.text.DecimalFormat;

public class BaseUtils {

	public static int parseToInt(Object obj) {
		try {
			if(obj != null && !"".equals(obj.toString().trim())) {
				return Integer.parseInt(obj.toString());
			}
		} catch (Exception e) {
		}
		return 0;
	}
	
	public static String parseToString(Object obj) {
		if(obj == null || "".equals(obj.toString().trim()) || "null".equalsIgnoreCase(obj.toString().trim())) {
			return "";
		}
		return obj.toString();
	}
	
	public static Double parseToDouble(Object obj) {
		try {
			if(obj != null && !"".equals(obj.toString().trim())) {
				return Double.parseDouble(obj.toString());
			}
		} catch (Exception e) {
		}
		return 0d;
	}
	/**
	 * 把double格式化为字符串，保留2位小数
	 * @param d
	 * @return
	 */
	public static String doubleToString(double d) {
		return doubleToString(d, 2);
	}
	/**
	 * 根据指定位数格式化double数值
	 * @param d 要格式化的数字
	 * @param digit 保留的位数，小于0，格式化为整数
	 * @return
	 */
	public static String doubleToString(double d,int digit) {
		if(digit < 0){
			digit = 0;
		}
		String pattern = "0";
		if(digit > 0){
			pattern += ".";
		}
		for(int i = 0 ; i < digit; i ++){
			pattern += "0";
		}
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(d);
	}
	
	
}
