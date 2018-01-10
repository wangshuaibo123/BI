package com.jy.platform.jbpm4.tool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 比较时间大小
	 * @author 豆永亮
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compareDate(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				//dt1在dt2之后
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				//dt1在dt2之前
				return -1;
			} else {
				//dt1与dt2相等
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			//compare_date方法出现异常
			return 2;
		}
	}
}
