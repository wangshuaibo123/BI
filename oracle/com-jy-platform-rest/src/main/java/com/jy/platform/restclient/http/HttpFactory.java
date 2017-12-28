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
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.platform.core.message.MediaType;
import com.jy.platform.restclient.cfg.RestConfig;
import com.jy.platform.tools.common.CommonUtil;
import com.jy.platform.tools.common.JacksonMapper;

public class HttpFactory {
    private static Logger logger = LoggerFactory.getLogger(HttpFactory.class);
    
	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 800;
	private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 20;
	private static final int DEFAULT_TIMEOUT = 60;
	
	private static volatile HttpFactory httpClientFactory = new HttpFactory();
	private CloseableHttpClient httpClient=null;
    private static RequestConfig config = null;
    private static PoolingHttpClientConnectionManager cm = null;  
    static {
        cm = new PoolingHttpClientConnectionManager();

        // 将最大连接数增加到800
        Integer maxTotalConnections = RestConfig.getInteger("maxTotalConnections");
        if (maxTotalConnections == null) {
        	maxTotalConnections = DEFAULT_MAX_TOTAL_CONNECTIONS;
        }
        cm.setMaxTotal(maxTotalConnections);

        // 将每个路由基础的连接增加到20
        Integer maxConnectionsPerRoute = RestConfig.getInteger("maxConnectionsPerRoute");
        if (maxConnectionsPerRoute == null) {
        	maxConnectionsPerRoute = DEFAULT_MAX_CONNECTIONS_PER_ROUTE;
        }
        cm.setDefaultMaxPerRoute(maxConnectionsPerRoute);

        //将目标主机的最大连接数增加到50
        //HttpHost localhost = new HttpHost("www.jieyuechina.com", 80);
        //cm.setMaxPerRoute(new HttpRoute(localhost), 50);
        
        Integer connectTimeout = RestConfig.getInteger("connectTimeout");
        if (connectTimeout == null) {
        	connectTimeout = DEFAULT_TIMEOUT;
        }
        Integer connectionRequestTimeout = RestConfig.getInteger("connectionRequestTimeout");
        if (connectionRequestTimeout == null) {
        	connectionRequestTimeout = DEFAULT_TIMEOUT;
        }
        Integer socketTimeout = RestConfig.getInteger("socketTimeout");
        if (socketTimeout == null) {
        	socketTimeout = DEFAULT_TIMEOUT;
        }
        
        config = RequestConfig.custom()
                .setConnectTimeout(connectTimeout * 1000)
                .setConnectionRequestTimeout(connectionRequestTimeout * 1000)
                .setSocketTimeout(socketTimeout * 1000).build();
    }
	
    private static HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

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
	

	private HttpFactory(){
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

	}

	public static HttpFactory getInstance(){
		if (null==httpClientFactory) {
			synchronized (HttpFactory.class) {
				if (null==httpClientFactory) {
					httpClientFactory=new HttpFactory();
				}
			}
		}
		return httpClientFactory;
	}

	
	
	public CloseableHttpClient getHttpClient(){
        httpClient = HttpClients.custom()
               .setConnectionManager(cm)
               .setRetryHandler(myRetryHandler)
               .setDefaultRequestConfig(config)
               .build();
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
		    logger.error("buildRequest error1 ",e);
			e.printStackTrace();
		}

		processHttpRequest(appId,httpUriRequest,null,t);
		return httpUriRequest;
	}
	
	public <T> HttpUriRequest buildRequest(String url,String methodType,T t){
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
            logger.error("buildRequest error2 ",e);
            e.printStackTrace();
        }

        processHttpRequest(httpUriRequest,null,t);
        return httpUriRequest;
    }
	
	private  <T> void  processHttpRequest(HttpUriRequest request,String contentType, T t){

        if (request instanceof HttpPost) {
            if (t instanceof String) {
                ((HttpPost)request).setEntity(new StringEntity((String)t, "utf-8"));
            }else {
                ((HttpPost)request).setEntity(new StringEntity(JacksonMapper.getInstance().writeValueAsString(t), "utf-8"));
            }
        }

        if (contentType!=null&&!contentType.equals("")) {
            request.setHeader(new BasicHeader("Accept", contentType));
            request.setHeader(new BasicHeader("content-Type", contentType+";charset=utf-8"));
        }else {
            request.setHeader(new BasicHeader("Accept", MediaType.APPLICATION_JSON_VALUE));
            request.setHeader(new BasicHeader("content-Type", MediaType.APPLICATION_JSON_VALUE));
        }
    }
}
