package com.easub.utils;

public class BaseUtils {

	
	public static int nullToInt(Object obj) {
		try {
			if(obj != null && !"".equals(obj.toString().trim())) {
				return Integer.parseInt(obj.toString());
			}
		} catch (Exception e) {
		}
		return 0;
	}
	
	public static String nullToString(Object obj) {
		try {
			if(obj != null) {
				return obj.toString();
			}
		} catch (Exception e) {
		}
		return "";
	}
	
}
