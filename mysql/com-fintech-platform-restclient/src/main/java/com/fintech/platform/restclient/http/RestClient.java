package com.fintech.platform.restclient.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restclient.exception.RestClientException;

public class RestClient {
	
	static Logger logger = LoggerFactory.getLogger(RestClient.class);

	public RestClient() {
	
	}
	

	public static <T> ResponseMsg<T> doGet(String appId, String url,TypeReference<ResponseMsg<T>> typeReference)throws RestClientException{
		if(appId==null || "".equals(appId)){
			throw new RestClientException("appId不允许为空");
		}
		
		ResponseMsg<T> t = new HttpUtil().execute(appId, url,"GET",typeReference,"");
		return t;	
	}
	
	
	public static <T,D> ResponseMsg<T> doPost(String appId, String url, D data, TypeReference<ResponseMsg<T>> typeReference)throws RestClientException{
		if(appId==null || "".equals(appId)){
			throw new RestClientException("appId不允许为空");
		}
		
		ResponseMsg<T> t = new HttpUtil().execute(appId, url,"POST",typeReference,data);
		return t;	
	}
	/**
	 * 新增
	 * @param appId
	 * @param url
	 * @param data
	 * @return
	 * @throws RestClientException
	 */
	public static <T,D> String doPost(String url, D data)throws RestClientException{
		String t = new HttpUtil().execute(url,"POST",data);
		return t;	
	}
	
}
