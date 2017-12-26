package com.fintech.modules.platform.common.interceptor;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fintech.modules.platform.sysconfig.service.SysConfigService;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.sysdict.SysDictAPI;

/**
 * 需审计的URL拦截器
 */
public class AuditURLInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(AuditURLInterceptor.class);
	
	@Autowired
	private SysDictAPI sysDictAPI;
	
	@Autowired
	private SessionAPI sessionAPI;
	
	@Autowired
	@Qualifier("com.fintech.modules.platform.sysconfig.service.SysConfigService")
	private SysConfigService sysConfigService;
	
	@Value("${app.datasource:YES}")
	private String appDatasource;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception{
		try{
			if("NO".equalsIgnoreCase(appDatasource)) return true;
			//拦截审计URL开关
			String accept = sysConfigService.queryValueByCode("audit_url_accept");
			
			//没有打开开关
			if(!"Y".equalsIgnoreCase(accept)){
				return true;
			}
			
			if(!(handler instanceof HandlerMethod)) return true;
			
			HandlerMethod hand = (HandlerMethod) handler;
			
			Class<?> clazz = hand.getMethod().getDeclaringClass();
			String clazzName = clazz.getName();
			//只过滤Controller的请求
			if (!clazzName.endsWith("Controller")) return true;
			
			String url = request.getRequestURI() != null ? request.getRequestURI().replace(request.getContextPath(), "") : "";
			
			//需审计的URL数据字典
			List<Map> aduitURLDictList = sysDictAPI.getDictByKey("AUDIT_URL");
			if(aduitURLDictList ==null ||  aduitURLDictList.size() == 0) return true;
			
			String dictKey = "";
			String aduitURL = "";
			Pattern pattern = null;
			Matcher matcher = null;  
			String paramStr = "";
			String loginName = "";
			
			//循环需审计URL
			for(Map aduitURLDict : aduitURLDictList){
				dictKey = (String)aduitURLDict.get("DICNAME");
				aduitURL = (String)aduitURLDict.get("DICVALUE");
				
				pattern = Pattern.compile(aduitURL);//规则
				matcher = pattern.matcher(url);  
				
				//匹配到审计URL规则
				if(matcher.matches()){
					paramStr = this.getRequestParam(request);//获取请求参数
					loginName = sessionAPI.getCurrentUserInfo().getLoginName();
					
					logger.info("request_URL:"+request.getRequestURI()+", request_method:"+request.getMethod()+", request_param:"+paramStr+", remote_addr:"+request.getRemoteAddr()+", login_name:"+loginName);
				}
			}
		}catch(Exception e){
			logger.error("audit_url_error:",e);
		}
		
		return true;
	}
	
	
	/**
	 * 获取请求参数
	 * 目前只能获取页面请求的参数，对于Rest调用的无法获取
	 * @param request
	 * @param method
	 * @return
	 */
	private String getRequestParam(HttpServletRequest request){
		String paramStr = "";
		
		Map<String, String[]> params = request.getParameterMap(); 
		
		if(params!=null && !params.isEmpty()){
			String[] values = null;
			for(String key : params.keySet()){  
	            values = params.get(key);  
	            for (int i = 0; i < values.length; i++) {  
	                paramStr += key + "=" + values[i] + "&";  
	            }  
	        }  
		}
		
		return paramStr;
	}
	
	
	
	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
	
	
	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
}
