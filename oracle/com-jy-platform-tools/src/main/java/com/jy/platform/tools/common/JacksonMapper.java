package com.jy.platform.tools.common;


import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
public class JacksonMapper extends ObjectMapper {
	

	private static final long serialVersionUID = 1L;
	private static JacksonMapper instance = null;
	//private static ObjectMapper m = null;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	private JacksonMapper() {}
	
	public  static synchronized JacksonMapper getInstance(){
		if(instance==null){
			instance = new JacksonMapper();
			//m = new ObjectMapper(); 
		}
		return instance;
	}
	
	@Override
	public String writeValueAsString(Object value){
		try {
		    super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);//去掉时间戳
		    //m.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);//去掉时间戳
		    super.setDateFormat(dateFormat);
		    //m.setDateFormat(dateFormat);
			return super.writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
