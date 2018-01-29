package com.easub.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
	/**
	 * 日期格式化:yyyy-MM-dd
	 */
	public static final int DATE_FORMAT = 1;
	/**
	 * 日期格式化:yyyy-MM-dd HH:mm
	 */
	public static final int DATE_TIME_FORMAT = 2;
	/**
	 * 日期格式化:yyyy-MM-dd HH:mm:ss
	 */
	public static final int DATE_TIMESTAMP_FORMAT = 3;
	
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
	
	
	public static String format(Date date) {
		return format(date, DATE_FORMAT);
	}
	
	public static String format(Date date,int pattern) {
		String sPattern = getDatePattern(pattern);
		return format(date, sPattern);
	}
	
	public static String format(Date date,String pattern) {
		try {
			if(date == null) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern(pattern);
			return sdf.format(date);
		} catch (Exception e) {
		}
		return null;
	}
	
	private static String getDatePattern(int pattern) {
		String sPattern = "";
		switch (pattern) {
		case DATE_FORMAT:
			sPattern="yyyy-MM-dd";
			break;
		case DATE_TIME_FORMAT:
			sPattern="yyyy-MM-dd hh:mm";
			break;
		case DATE_TIMESTAMP_FORMAT:
			sPattern="yyyy-MM-dd hh:mm:ss";
			break;
		default:
			sPattern="yyyy-MM-dd";
			break;
		}
		return sPattern;
	}
	
	/**
	 * 解析字符串类型的日期为yyyy-MM-dd的日期类型
	 * @param sDate 
	 * @return
	 */
	public static Date parseDate(String sDate){
		return parseDate(sDate, "");
	}
	/**
	 * 根据格式化类型，解析字符串类型的日期为日期类型
	 * @param sDate 
	 * @param format 格式化类型
	 * @return
	 */
	public static Date parseDate(String sDate,int format){
		String sFormat = getDatePattern(format);
		return parseDate(sDate, sFormat);
	}
	/**
	 * 根据格式化字符串，解析字符串类型的日期为日期类型
	 * @param sDate 
	 * @param format 格式化字符串：比如:yyyy-MM-dd等
	 * @return String
	 */
	public static Date parseDate(String sDate,String format){
		try {
			if(sDate == null || "".equals(sDate.trim())){
				return null;
			}
			if(format == null || "".equals(format.trim())){
				format = "yyyy-MM-dd";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(sDate);
		} catch (ParseException e) {
		}
		return null;
	}
	
	
}
