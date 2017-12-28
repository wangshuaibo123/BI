package com.jy.platform.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.restclient.http.HttpFactory;

/**
 * Servlet Filter implementation class DispatcherFilter
 */
public class RemoteMVCDispatcherFilter implements Filter {
    
    private Logger logger = LoggerFactory.getLogger(RemoteMVCDispatcherFilter.class);

    /**
     * 远程服务地址（TODO 临时使用）
     */
    private String remoteServer;

    /**
     * Default constructor.
     */
    public RemoteMVCDispatcherFilter() {
        
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        
        // 非远程调用的，忽略掉
        if (isExcludeAble(req)) {
            chain.doFilter(request, response);
        }
        else {
            CloseableHttpClient httpClient = HttpFactory.getInstance().getHttpClient();
            HttpUriRequest httpRequest = null;
            try {
                httpRequest = buildHttpUriRequest(req);
            }
            catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            CloseableHttpResponse httpResponse = httpClient.execute(httpRequest);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            // 成功(返回200)
            if (statusCode == 200) {
                HttpEntity entity = httpResponse.getEntity();
                String str = EntityUtils.toString(entity);
                response.getWriter().println(str);
            } 
            // 失败
            else {
                logger.warn("请求远程服务失败:{}, {}", req.getRequestURI(), statusCode);
                DataMsg dataMsg = new DataMsg();
                dataMsg.failed("请求远程服务失败:" + statusCode);
                response.getWriter().println(JSONObject.toJSONString(dataMsg));
            }
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO 目前先通过配置实现，以后改成自动发现
        remoteServer = fConfig.getInitParameter("remoteServer");
    }

    /**
     * 判断是否可以排除
     * @param request HttpServletRequest
     * @return
     */
    private boolean isExcludeAble(HttpServletRequest request) {
        String uri = request.getRequestURI();
        // 老版本的页面跳转的请求排除掉
        if (uri.contains("/prepareExecute/")) {
            return true;
        }
        // 新版本的页面跳转的请求排除掉
        if (uri.contains("/view/")) {
            return true;
        }
        return false;
    }
    
    /**
     * 构造请求
     * @param req HttpServletRequest
     * @return
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException
     */
    private HttpUriRequest buildHttpUriRequest(HttpServletRequest req) throws UnsupportedEncodingException, URISyntaxException {
        HttpUriRequest request = null;
        if (HttpPost.METHOD_NAME.equalsIgnoreCase(req.getMethod())) {
            request = buildHttpPost(req);
        }
        else if (HttpGet.METHOD_NAME.equalsIgnoreCase(req.getMethod())) {
            request = buildHttpGet(req);
        }
        else {
            throw new RuntimeException("暂不支持的http[" + req.getMethod() + "]请求");
        }

        request.setHeader(new BasicHeader("Authorization", Base64.encodeBase64String("jypt:xx".getBytes())));
        
        //request.setHeader(new BasicHeader("Accept", MediaType.APPLICATION_JSON_VALUE));
        // TODO content-Type千万不能设置成json，必须保持和原页面传过来的一样
        // post.setHeader(new BasicHeader("content-Type", MediaType.APPLICATION_JSON_VALUE));

//        String contentType = req.getContentType();
//        if (contentType != null && !contentType.equals("")) {
//            request.setHeader(new BasicHeader("Accept", contentType));
//            request.setHeader(new BasicHeader("content-Type", contentType + ";charset=utf-8"));
//        }
//        else {
//            request.setHeader(new BasicHeader("Accept", MediaType.APPLICATION_JSON_VALUE));
//            request.setHeader(new BasicHeader("content-Type", MediaType.APPLICATION_JSON_VALUE));
//        }
        return request;
    }

    /**
     * 构造Post请求
     * @param req HttpServletRequest
     * @return
     * @throws UnsupportedEncodingException
     * @throws URISyntaxException
     */
    private HttpPost buildHttpPost(HttpServletRequest req) throws UnsupportedEncodingException, URISyntaxException {
        HttpPost post = new HttpPost();
        String uri = req.getRequestURI();
        uri = uri.replace(req.getContextPath(), "");
        post.setURI(new URI(remoteServer + uri));
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        // 获取参数
        @SuppressWarnings("unchecked")
        Map<String, String[]> pMap = req.getParameterMap();
        if (pMap != null && !pMap.isEmpty()) {
            Iterator<Entry<String, String[]>> it = pMap.entrySet().iterator();
            Entry<String, String[]> param;
            while (it.hasNext()) {
                param = it.next();
                if (param.getValue().length == 1) {
                    nvps.add(new BasicNameValuePair(param.getKey(), param.getValue()[0]));
                } else {
                    for (int i = 0; i < param.getValue().length; i++) {
                        nvps.add(new BasicNameValuePair(param.getKey()+"["+i+"]", param.getValue()[i]));
                    }
                }
            }
        }
        post.setEntity(new UrlEncodedFormEntity(nvps));
        return post;
    }
    
    private HttpGet buildHttpGet(HttpServletRequest req) throws URISyntaxException {
        HttpGet get = new HttpGet();
        String uri = req.getRequestURI();
        uri = uri.replace(req.getContextPath(), "");
        String queryString = req.getQueryString();
        if (queryString != null && queryString.length() > 0) {
            uri = uri + "?" + queryString; 
        }
        get.setURI(new URI(remoteServer + uri));
        return get;
    }
}
