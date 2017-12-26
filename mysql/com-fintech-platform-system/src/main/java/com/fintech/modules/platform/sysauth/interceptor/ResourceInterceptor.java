package com.fintech.modules.platform.sysauth.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.modules.platform.sysauth.dto.SysResourceRoleDTO;
import com.fintech.modules.platform.sysauth.dto.SysRoleDTO;
import com.fintech.modules.platform.sysauth.service.SysResourceService;
import com.fintech.modules.platform.sysauth.service.SysRoleService;
import com.fintech.modules.platform.syslog.dto.SysLogDTO;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
/**
 * 导入资源拦截器
 * 只做controller 到JSP关系的映射缓存，不做其他操作
 * @author
 *
 */
public class ResourceInterceptor implements HandlerInterceptor{
	
	 private static final Logger log = LoggerFactory.getLogger(ResourceInterceptor.class);  
	 //线程安全的MAP
	 public static Map<String, String> controllerToJsp  = new ConcurrentHashMap<String, String>();
	 
	 //判断是否记录日志
	 private boolean isLog=false;
	 
	 @Value("${app.datasource:YES}")
	 private String appDatasource;

	 /** 
	     * 在业务处理器处理请求之前被调用 
	     * 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
	     * 如果返回true   执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 
	     * 然后进入拦截器链, 从最后一个拦截器往回执行所有的postHandle() 
	     * 接着再从最后一个拦截器往回执行所有的afterCompletion() 
	     */  
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String params="";
		Map map=request.getParameterMap();
		for(Object key:map.keySet()){
			if(key instanceof String){
				params=params+key+"="+request.getParameter((String)key)+";";
			}
		}
		if(handler instanceof HandlerMethod){
			ResourceBundle illegalProperties = null;
			//非法字符
			String illegalCharacters = null;
			//例外类
			String excludeClasses = null;
			try{
				/**
				  * 非法字符配置文件
				  */
				illegalProperties = ResourceBundle.getBundle("biz_app");
				illegalCharacters = illegalProperties.getString("illegalCharacter");
				excludeClasses = illegalProperties.getString("excludeClass");
			}catch(Exception e){
				
			}
			if(null!=illegalCharacters && !"".equals(illegalCharacters)){
				HandlerMethod hand=(HandlerMethod) handler;
				String className = hand.getMethod().getDeclaringClass().getName();
				if((null == excludeClasses || "".equals(excludeClasses) || excludeClasses.indexOf(className)<0) && params.length()>0){
					String paramLower = params.toLowerCase();
					String[] illegalCharacterArr = illegalCharacters.split(",");
					for(int i=0;i<illegalCharacterArr.length;i++){
						String illegalCharacter = illegalCharacterArr[i];
						if(null!=illegalCharacter && illegalCharacter.length()>0){
							if(paramLower.indexOf(illegalCharacter.toLowerCase())>-1){
								throw new Exception("非法请求字符："+illegalCharacterArr[i]);
							}
						}
						
					}
				}
			}
		}
		
		//过滤所有URL判断是否在资源中进行设置，如果在资源中设置，判断是否当前用户是否有权限
		if(handler instanceof HandlerMethod){
			HandlerMethod hand=(HandlerMethod) handler;
			if(hand.getMethod().getDeclaringClass().getSimpleName().endsWith("Controller")){//判断请求的都是Controller不是rest请求
				if("NO".equalsIgnoreCase(appDatasource)) return true;
				
				String uri=request.getRequestURI();
				uri=uri.replace(request.getContextPath()+"/", "");
				ApplicationContext context = null;
				try{
					WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
					ServletContext servletContext = webApplicationContext.getServletContext();
					context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
				}catch(Exception e){
					context = SysConfigAPI.getApplicationContext();
				}
				SysResourceService service=(SysResourceService) context.getBean("com.fintech.modules.platform.sysauth.service.SysResourceService");
				List<SysResourceRoleDTO> allUrl = service.getSysResourceAllUrl("");
				SessionAPI sessionAPI =(SessionAPI) context.getBean("sessionAPI");
				SysRoleService roleService= (SysRoleService) context.getBean("com.fintech.modules.platform.sysauth.service.SysRoleService");
				
				List<SysRoleDTO> roleList= new ArrayList<SysRoleDTO>();
				try {
					UserInfo userinfo = sessionAPI.getCurrentUserInfo();
					roleList = roleService.getRoleByUserId(userinfo.getUserId());
				} catch (Exception e) {
					roleList= new ArrayList<SysRoleDTO>();
					log.error("ResourceInterceptor getRoleByUserId error:");
				}
				List<SysResourceRoleDTO> allRoleRe=new ArrayList<SysResourceRoleDTO>();
				for(SysRoleDTO role: roleList){
					allRoleRe.addAll(service.getSysResourceUrl(role.getRoleCode()));
				}
				
				//判断url是否在定义资源表内
				boolean exist=false;
				for(SysResourceRoleDTO resouce: allUrl){
					if(uri.equals(resouce.getResoureUrl())){
						exist=true;
						break;
					}
				}
				
				//如果不在资源表内，就可以直接进行显示，否则判断是否有权限
				boolean existRole=true;
				if(exist){
					for(SysResourceRoleDTO rescouce : allRoleRe){
						if(uri.equals(rescouce.getResoureUrl())){
							existRole=false;
							break;
						}
					}
				}
				if(existRole && exist){
					throw new Exception("非法请求URL："+uri);
				}
			}
		}
		
		if(isLog){
			if(handler instanceof HandlerMethod){//判断是否方法handler
				HandlerMethod hand=(HandlerMethod) handler;
				if(hand.getMethod().getDeclaringClass().getSimpleName().endsWith("Controller")){//判断请求的都是Controller不是rest请求
					SysLogDTO sysDto=new SysLogDTO();
					sysDto.setType("1");
					String contName=hand.getMethod().getDeclaringClass().getName();
					sysDto.setClassName(contName);
					String moduleName=contName.substring(15);
					moduleName=moduleName.substring(0, moduleName.indexOf("."));
					sysDto.setMethodName(hand.getMethod().getName());
					sysDto.setModuleName(moduleName);
					
					ApplicationContext context = null;
					try{
						WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
						ServletContext servletContext = webApplicationContext.getServletContext();
						context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
					}catch(Exception e){
						context = SysConfigAPI.getApplicationContext();
					}
					SessionAPI sessionAPI =(SessionAPI) context.getBean("sessionAPI");
					UserInfo userinfo = sessionAPI.getCurrentUserInfo();
					if(userinfo != null && StringUtils.isNotEmpty(userinfo.getLoginName())){
						sysDto.setUserId(userinfo.getLoginName());
					}else{
						sysDto.setUserId("-1");//-1标志为 未登录的用户 或匿名 信息
					}

					if(params.length()<1000){
						sysDto.setParamInfo(params);
					}
					sysDto.setUri(request.getRequestURI());
					log.info(sysDto.toString());
				}

			}
		}
		return true;
	}

	//在业务处理器处理请求执行完成后,生成视图之前执行的动作
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		final String [] noFilter = {"images","css","toLogin","login"}; 
		 boolean beFilter = true; 
	        for (String tmp : noFilter) {  
	            if (request.getRequestURI().indexOf(tmp) != -1) {  
	                beFilter = false;  
	                break;  
	            }  
	        }
	        
		if (beFilter && modelAndView != null) {
			String jspRealPath = request.getSession().getServletContext().getRealPath("");
			jspRealPath += "/WEB-INF/jsp/"+modelAndView.getViewName()+".jsp";
			final String key =  request.getRequestURI()!=null?request.getRequestURI().replace(request.getContextPath(), ""):"";
			if (!controllerToJsp.containsKey(key)) {
				controllerToJsp.put(key, jspRealPath);
			}
		}
	}

	//在DispatcherServlet完全处理完请求后被调用
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	public boolean isLog() {
		return isLog;
	}

	public void setLog(boolean isLog) {
		this.isLog = isLog;
	}
}
