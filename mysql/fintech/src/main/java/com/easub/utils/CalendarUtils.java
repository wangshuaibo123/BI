package com.easub.utils;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

	/**
	 * 清空时分秒毫秒
	 * @param calendar
	 */
	public static void clearHMS(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * 清空时分秒毫秒
	 * @param date
	 */
	public static void clearHMS(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		clearHMS(calendar);
		date = calendar.getTime();
	}
	
	
	
	
	
}
