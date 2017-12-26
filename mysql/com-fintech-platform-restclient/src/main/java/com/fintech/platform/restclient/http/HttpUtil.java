package com.fintech.platform.restclient.http;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.core.message.ResponseStatus;
import com.fintech.platform.restclient.exception.RestClientException;
import com.fintech.platform.tools.common.JacksonMapper;

public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	@SuppressWarnings("unchecked")
	public  <T,D> ResponseMsg<T> execute(String appId, String url, String methodType, TypeReference<ResponseMsg<T>> typeReference,D data)throws RestClientException{
		logger.debug("request:{}  @ {}  @ {}",url,methodType,data);

		HttpUriRequest httpUriRequest= HttpFactory.getInstance().buildRequest(appId, url,methodType,data);

		CloseableHttpClient httpClient=HttpFactory.getInstance().getHttpClient();
		CloseableHttpResponse httpResponse=null;
		ResponseMsg<T> t = null;
		int statusCode = 200;
		String result = null;
		try {
			httpResponse=httpClient.execute(httpUriRequest);
			statusCode=httpResponse.getStatusLine().getStatusCode();
			if (statusCode==200) {
				result = EntityUtils.toString(httpResponse.getEntity());
				JacksonMapper mapper = JacksonMapper.getInstance();
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				t=mapper.readValue(result, typeReference);
			}else{
				logger.error("HTTP {} Failure, statusCode={}=result=={}",methodType,statusCode,EntityUtils.toString(httpResponse.getEntity()));
			}
			t = processStatus(statusCode,t);
			
		}catch(JsonMappingException e){
			logger.error("JsonMappingException HTTP {} Failure, statusCode={},{}",methodType,statusCode, e);
			//支持字符串 返回
			ResponseMsg<String> resultT = new ResponseMsg<String>();
			StringBuffer sb = new StringBuffer(result);
			resultT.setResponseBody(sb.toString());
			t = (ResponseMsg<T>) processStatus(statusCode,resultT);
		}catch(ConnectionPoolTimeoutException e){
			statusCode = 0;
			logger.error("HTTP {} Failure, statusCode={},{}",methodType,statusCode, e);
		} catch (Exception e) {
			logger.error("HTTP {} Failure, statusCode={},{}",methodType,statusCode, e);
		}finally {
			try {
				if(httpResponse !=null)httpResponse.close();
			} catch (IOException e) {}
			logger.debug("response: {} @ {} @ {}",url,methodType,t);
		}
		
		return t;	
	}

	private  <T> ResponseMsg<T>  processStatus(int statusCode,ResponseMsg<T> t)throws RestClientException{
		if (statusCode==200) {
			if(t.getRetCode()==null||t.getRetCode().equals("")){
				t.setRetCode(ResponseStatus.HTTP_OK);
			}
		}else{
			t = new ResponseMsg<T>();
			if (statusCode==404) {
				t.setRetCode(ResponseStatus.HTTP_NOT_FOUND);
			}else if (statusCode==500) {
				t.setRetCode(ResponseStatus.HTTP_SERVER_ERROR);
			}else {
				t.setRetCode(statusCode+"");
			}
		}
		return t;
	}

	public <T> String execute(String url, String methodType, T data)throws RestClientException{
		logger.debug("request: {} @ {} @ {}",url,methodType,data);

		HttpUriRequest httpUriRequest= HttpFactory.getInstance().buildRequest(url,methodType,data);

		CloseableHttpClient httpClient=HttpFactory.getInstance().getHttpClient();
		CloseableHttpResponse httpResponse=null;

		int statusCode = 200;
		String result = null;
		try {
			httpResponse=httpClient.execute(httpUriRequest);
			statusCode=httpResponse.getStatusLine().getStatusCode();
			if (statusCode==200) {
				result = EntityUtils.toString(httpResponse.getEntity());	
			}else{
				logger.error("HTTP {} Failure, statusCode={}=result=={}",methodType,statusCode,httpResponse.getEntity());
			}
			
		} catch(ConnectionPoolTimeoutException e){
			statusCode = 0;
			logger.error("HTTP {} Failure, statusCode={},{}",methodType,statusCode, e);
		} catch (Exception e) {
			logger.error("HTTP {} Failure, statusCode={},{}",methodType,statusCode, e);
		} finally {
			try {
				if(httpResponse !=null)httpResponse.close();
			} catch (IOException e) {}
			logger.debug("response: {} @ {}",url,methodType);
		}
		
		return result;	
	}
}
