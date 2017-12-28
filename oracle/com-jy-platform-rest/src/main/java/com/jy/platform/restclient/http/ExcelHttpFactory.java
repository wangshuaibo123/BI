package com.jy.platform.restclient.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;

import com.jy.platform.core.message.MediaType;
import com.jy.platform.tools.common.CommonUtil;
import com.jy.platform.tools.common.JacksonMapper;

public class ExcelHttpFactory {

	
//	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
//	private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;
	

	
	private CloseableHttpClient httpClient=null;

	HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

	    public boolean retryRequest(
	            IOException exception,
	            int executionCount,
	            HttpContext context) {
	        if (executionCount >= 3) {
	            // Do not retry if over max retry count
	            return false;
	        }
	        if (exception instanceof InterruptedIOException) {
	            // Timeout
	            return false;
	        }
	        if (exception instanceof UnknownHostException) {
	            // Unknown host
	            return false;
	        }
	        if (exception instanceof ConnectTimeoutException) {
	            // Connection refused
	            return false;
	        }
	        if (exception instanceof SSLException) {
	            // SSL handshake exception
	            return false;
	        }
	        HttpClientContext clientContext = HttpClientContext.adapt(context);
	        HttpRequest request = clientContext.getRequest();
	        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
	        if (idempotent) {
	            // Retry if the request is considered idempotent
	            return true;
	        }
	        return false;
	    }
	};
	

	private ExcelHttpFactory(int timeOut){
//		Registry<ConnectionSocketFactory> registry=RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory())
//                .register("https", SSLConnectionSocketFactory.getSocketFactory())
//                .build();
//		PoolingHttpClientConnectionManager connManager=new PoolingHttpClientConnectionManager(registry);
//		connManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
//		connManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
//
//		
//		RequestConfig config=RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
//				.setConnectionRequestTimeout(REQUEST_TIMEOUT).setSocketTimeout(CONNECT_TIMEOUT).build();
//		httpClient=HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(config).setRetryHandler(myRetryHandler).build();
		 
		RequestConfig config = RequestConfig.custom()
		  .setConnectTimeout(timeOut * 1000)
		  .setConnectionRequestTimeout(timeOut * 1000)
		  .setSocketTimeout(timeOut * 1000).build();
		 httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setRetryHandler(myRetryHandler).build();
	}

	public static ExcelHttpFactory getInstance(int timeOut){
			synchronized (ExcelHttpFactory.class) {
					return new ExcelHttpFactory(timeOut);
			}
	}

	
	/*private HttpFactory(int timeOut){
		RequestConfig config = RequestConfig.custom()
		  .setConnectTimeout(timeOut * 1000)
		  .setConnectionRequestTimeout(timeOut * 1000)
		  .setSocketTimeout(timeOut * 1000).build();
		 httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).setRetryHandler(myRetryHandler).build();
	}

	public static HttpFactory getInstance(int timeOut){
		if (null==httpClientFactory) {
			synchronized (HttpFactory.class) {
				if (null==httpClientFactory) {
					httpClientFactory=new HttpFactory(timeOut);
				}
			}
		}
		return httpClientFactory;
	}*/
	
	public CloseableHttpClient getHttpClient(){
		return httpClient;
	}

	
	
	private  <T> void  processHttpRequest(String appId, HttpUriRequest request,String contentType, T t){

		if (request instanceof HttpPost) {
			if (t instanceof String) {
				((HttpPost)request).setEntity(new StringEntity((String)t, "utf-8"));
			}else {
				((HttpPost)request).setEntity(new StringEntity(JacksonMapper.getInstance().writeValueAsString(t), "utf-8"));
			}
		}
		if(appId!=null&&!appId.equals("")){
			request.setHeader(new BasicHeader("Authorization", encrypt(appId, RestService.getAppPwd(appId))));
		}
		

		if (contentType!=null&&!contentType.equals("")) {
			request.setHeader(new BasicHeader("Accept", contentType));
			request.setHeader(new BasicHeader("content-Type", contentType+";charset=utf-8"));
		}else {
			request.setHeader(new BasicHeader("Accept", MediaType.APPLICATION_JSON_VALUE));
			request.setHeader(new BasicHeader("content-Type", MediaType.APPLICATION_JSON_VALUE));
		}
	}
	
	private String encrypt(String appId,String appPwd){
		String v=appId+":"+appPwd;
		return Base64.encodeBase64String(v.getBytes());
	}

	public <T> HttpUriRequest buildRequest(String appId, String url,String methodType,T t){
		HttpUriRequest httpUriRequest=null;
		try{
			if(methodType!=null&&methodType.equals("POST")){
				HttpPost post=new HttpPost();
				post.setURI(CommonUtil.string2Uri(url));
		
				httpUriRequest=post;
			}else if(methodType!=null&&methodType.equals("GET")){
				HttpGet get=new HttpGet();
				get.setURI(CommonUtil.string2Uri(url));
				httpUriRequest=get;
			}else{
				HttpPost post=new HttpPost();
				post.setURI(CommonUtil.string2Uri(url));
				httpUriRequest=post;
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		processHttpRequest(appId,httpUriRequest,null,t);
		return httpUriRequest;
	}
	
	

}
