package com.jy.modules.platform.common.interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jy.platform.api.sysconfig.SysConfigAPI;
/**
 * 1、增加流量限流控制功能 不考虑JS、CSS、Image
 * 配置bizModule、maxLimit属性即可
 * 参考在spring-mvc.xml 配置 
 * <bean id="loanAuditAPP" class="com.jy.modules.platform.common.interceptor.LimitURLInterceptor" scope="prototype">
            </bean>
    配置数据字典：SYS_APP_LIMIT
 * @author chengang
 * 
 */
public class LimitURLInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(LimitURLInterceptor.class);
	//定义流量限制控制 线程安全的map
	private static Map<String,Vector<String>> URL_STREAM = new ConcurrentHashMap<String, Vector<String>>();
	//定义限流的业务模块名称 如：com.jy.modules.befloan.creditaudit.creditmarry.controller.AuditMatchController.toMatch(.*)
	//定义模块 并发访问上线
	private NamedThreadLocal<Integer> maxLimit = new NamedThreadLocal<Integer>("platformApp_maxLimit");
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		if(!(handler instanceof HandlerMethod)) return true;
		//限制流程控制
		this.addOneForList(request, response, handler);
		
		return true;
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

			this.removeOneForList(request, response, handler);
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

			this.removeOneForList(request, response, handler);
	}
	/**
	 * 
	 * @description:并发控制流量加一
	 * @author chengang
	 * @date: 2016年5月5日 下午4:37:31
	 * @param request
	 * @param response
	 * @param handler
	 * @throws Exception
	 */
	private void addOneForList(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) return;
		
		HandlerMethod hand = (HandlerMethod) handler;
		Class<?> clazz = hand.getMethod().getDeclaringClass();
		String clazzName = clazz.getName();
		StringBuffer val = new StringBuffer(clazzName);
		val.append(".").append(hand.getMethod().getName()).append(":").append(Thread.currentThread().getName());
		logger.debug("========start==="+val);
		
		ApplicationContext context = null;
        try{
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
            ServletContext servletContext = webApplicationContext.getServletContext();
            context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        }
        catch(Exception e){
            context = SysConfigAPI.getApplicationContext();  //spring boot启动时会注入
        }
		com.jy.platform.api.sysdict.SysDictAPI dictAPI = (com.jy.platform.api.sysdict.SysDictAPI) context.getBean(com.jy.platform.api.sysdict.SysDictAPI.class);
		//设置限流的模块
		List<Map> listDic = null;
		//是否是系统级的匹配
		boolean flag = true;
		try {
			listDic = dictAPI.getDictByKey("SYS_APP_LIMIT");
			//如果没有配置限流 则不限制
			if(listDic == null || listDic.size() ==0) return ;
			
			for(Map temp:listDic){
				String key = (String) temp.get("DICNAME");
				String value = (String) temp.get("DICVALUE");
				int maxCunTemp = 9999;//999代表无上限
				if (StringUtils.isNotEmpty(value)) maxCunTemp = Integer.parseInt(value);
				//防止过小设置
				if(maxCunTemp < 5) maxCunTemp= 9999;
				
				//初始化 通过参数配置 该模块的最大并发量设置
				if(URL_STREAM.get(key) == null) URL_STREAM.put(key, new Vector<String>());
				
				//设置系统级别的最大并发量
				if("SYS_APP".equals(key)) this.maxLimit.set(maxCunTemp);
				
				//如果匹配成功则在对应的额模块下加一
				if(!"SYS_APP".equals(key) && val.toString().matches(key)){
					Vector<String> vector = URL_STREAM.get(key);
					logger.debug("========start==="+key+"==="+vector.size());
					//超过最大的并发设置时则 提示
					if(maxCunTemp != 9999 && vector.size() >= maxCunTemp ) {
						logger.error("========start==="+key+"==="+vector.size());
						throw new Exception("系统繁忙，请稍后重试！");
					}
					vector.add(val.toString());
					flag = false;
					break;
				}
			}
			
		} catch (Exception e) {
			String msg = e.getMessage();
			if(msg != null && msg.indexOf("系统繁忙，请稍后重试！") > -1){
				throw new Exception("系统繁忙，请稍后重试！");
			}else{
				logger.error("=======addOneForList=error:",e);
			}
			
			if(listDic == null || listDic.size() ==0) return ;
		}
		
		if(!flag) return;
		//没有匹配上说明是系统级别的
		Vector<String> vector = URL_STREAM.get("SYS_APP");
		//logger.info("========start===SYS_APP==="+vector.size());
		//超过最大的并发设置时则 提示
		if(this.maxLimit.get() != 9999 
				&& this.maxLimit.get() > 0 
				&& vector.size() >= this.maxLimit.get()) {
			logger.error("========start===SYS_APP==="+vector.size());
			throw new Exception("系统繁忙，请稍后重试！");
		}
		vector.add(val.toString());
		
		
		
	}
	/**
	 * 
	 * @description:并发控制流量减一
	 * @author chengang
	 * @date: 2016年5月5日 下午4:38:03
	 * @param request
	 * @param response
	 * @param handler
	 * @throws Exception
	 */
	private void removeOneForList(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) return;
		if( URL_STREAM.isEmpty()) return ;
		
		try {
			HandlerMethod hand = (HandlerMethod) handler;
			Class<?> clazz = hand.getMethod().getDeclaringClass();
			String clazzName = clazz.getName();
			StringBuffer val = new StringBuffer(clazzName);
			val.append(".").append(hand.getMethod().getName()).append(":").append(Thread.currentThread().getName());
			
			logger.debug("========end==="+val);
			
			//冗余逻辑 减一
			//按悲观处理
			Iterator<String> it = URL_STREAM.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				Vector<String> vector3 = URL_STREAM.get(key);
				vector3.remove(val.toString());
				logger.debug("========end===" + key + "=="+ vector3.size());
			}
			
		} catch (Exception e) {
			logger.error("=====removeOneForList===error:",e);
			//异常后 将并限流控制初始化
			URL_STREAM = new ConcurrentHashMap<String, Vector<String>>();
		}
		
	}
	
}
