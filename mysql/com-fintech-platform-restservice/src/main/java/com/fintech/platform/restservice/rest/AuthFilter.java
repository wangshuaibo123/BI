package com.fintech.platform.restservice.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.platform.restservice.cfg.AuthConfig;
/**
 * 
 * @description：安全访问过滤器
 *
 */
public class AuthFilter implements Filter{
	static Logger logger = LoggerFactory.getLogger(AuthFilter.class);

   
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;		
		
		//从header中获取租户id和password
		String authorization = httpRequest.getHeader("authorization");
		String appId= "";
		String appPwd= "";
		if(authorization!=null && !"".equals(authorization)){
			  String idPwd = new String(Base64.decodeBase64(authorization));
			  if(idPwd.indexOf(":") != -1){
				  appId = idPwd.split(":")[0];
				  appPwd = idPwd.split(":")[1];
			  }
		}
		
		//校验通过
		if(checkAuth(appId,appPwd)){
			chain.doFilter(request, response);
		}
		else {
			PrintWriter writer = null;
	        try {
	        	response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");
	            writer = response.getWriter();
	            StringBuilder sb = new StringBuilder("{\"errorDesc\":\"appId或appPwd不正确，拒绝访问该服务。\",\"responseBody\":null,\"retCode\":\"500\"}");
	            writer.append(sb);
	            writer.flush();
	        }
	        catch(Exception e){
	        	throw new ServletException(e);
	        }
	        finally{
	            if(writer != null){
	                writer.close();
	            }
	        }
	        logger.error("appId或appPwd不正确，拒绝访问该服务。");
			//throw new ServletException("appId或appPwd不正确，拒绝访问该服务。");
		}
	}

	
	/**
	 * 检测客户端访问的合法性，以后需要从数据库里读取授权ID和密码
	 * @param appId
	 * @param appPwd
	 * @return
	 */
	private boolean checkAuth(String appId,String appPwd){
		boolean isAuth = false;
		if(appId==null||appId.equals("")||appPwd==null||appPwd.equals("")){
			return false;
		}
		
		if(AuthConfig.check(appId, appPwd)){
			isAuth = true;
		}
		return isAuth;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		AuthConfig.load();
	}
	
	@Override
	public void destroy() {

	}

}
