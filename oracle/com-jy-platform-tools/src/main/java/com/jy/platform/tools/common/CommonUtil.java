package com.jy.platform.tools.common;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommonUtil {

    private static final String DATE_STYLE = "yyyy-MM-dd";
    private static final String TIME_STYLE = "yyyy-MM-dd HH:mm:ss";
    
    public static String EMPTY = "";
    public static String SEMICOLON = ";";

	public static URI string2Uri(String s)throws Exception{
		if (s==null||s.equals("")) {
			return null;
		}else {
			URI uri=null;
			try {
				uri = new URI(s);
			} catch (URISyntaxException e) {
				throw new Exception(e);
			}
			return uri;
		}
	}

    public static String getId() {
        return UUID.randomUUID().toString();
    }

    public static String replaceNull2Space(String s) {
        if (s == null)
            return "";
        if (s.trim().toUpperCase().equals("NULL"))
            return "";
        return s.trim();
    }

    public static String Date2String(Date dt) {
        if (dt == null || dt.equals(""))
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_STYLE);
        try {
            return sdf.format(dt);
        } catch (Exception ex) {
            return "";
        }
    }

    public static String Time2String(Date dt) {
        if (dt == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_STYLE);
        try {
            return sdf.format(dt);
        } catch (Exception ex) {
            return "";
        }
    }
    
    public static Date String2Time(String time) {
    	if("".equals(replaceNull2Space(time))){
    		return null;
    	}
        SimpleDateFormat format = new SimpleDateFormat(TIME_STYLE);
        Date d = null;
        if (time != null && !time.equals("")) {
            try {
                d = format.parse(time);
            } catch (Exception ex) {
            }
        }
        return d;
    }
    
    public static Date String2Date(String date) {
    	if("".equals(replaceNull2Space(date))){
    		return null;
    	}
        SimpleDateFormat format = new SimpleDateFormat(DATE_STYLE);
        Date d = null;
        if (date != null && !date.equals("")) {
            try {
                d = format.parse(date);
            } catch (Exception ex) {
            }
        }
        return d;
    }
}
