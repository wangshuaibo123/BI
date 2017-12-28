package com.jy.platform.restclient.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restclient.exception.RestException;

public class RestClient {
	
	static Logger logger = LoggerFactory.getLogger(RestClient.class);

	public RestClient() {
	
	}
	

	public static <T> ResponseMsg<T> doGet(String appId, String url,TypeReference<ResponseMsg<T>> typeReference)throws RestException{
		if(appId==null || "".equals(appId)){
			throw new RestException("appId不允许为空");
		}
		
		ResponseMsg<T> t = new HttpUtil().execute(appId, url,"GET",typeReference,"");
		return t;	
	}
	
	
	public static <T,D> ResponseMsg<T> doPost(String appId, String url, D data, TypeReference<ResponseMsg<T>> typeReference)throws RestException{
		if(appId==null || "".equals(appId)){
			throw new RestException("appId不允许为空");
		}
		
		ResponseMsg<T> t = new HttpUtil().execute(appId, url,"POST",typeReference,data);
		return t;	
	}
	
	
	
}
