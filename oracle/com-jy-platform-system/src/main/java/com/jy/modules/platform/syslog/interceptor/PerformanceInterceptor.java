package  com.jy.modules.platform.syslog.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;

import com.jy.modules.platform.syslog.dto.SysLogDTO;
import com.jy.platform.api.org.UserInfo;


/**
 * 平台监控一个方法执行速度的拦截器 wangxz
 */
public class PerformanceInterceptor extends PerformanceMonitorInterceptor
		implements ThrowsAdvice {
	
	private static final long serialVersionUID = 7302350984439928367L;
	
	static Logger logger = LoggerFactory.getLogger(PerformanceInterceptor.class);
	
	private boolean isLog=false;
	
	
	/* 一个方法执行时默认允许最大执行时间 */
	private Long maxAllowedTimeMillis = 1000L;

	/**
	 * 根据参数是DTO类型进行参数的解析
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	private StringBuilder getDTOToString(Object obj) throws Exception{
		StringBuilder sb=new StringBuilder();
		sb.append(obj.getClass().getName()).append("={");
		Field[] field=obj.getClass().getDeclaredFields();
		int count=0;
		for(Field fi:field){
			if(fi.getName().indexOf("serialVersionUID")==-1){//过滤DTO中的版本属性
				fi.setAccessible(true);//获取私有变量的value设置
				if(fi.get(obj)!=null){//判断类对象中的属性是否为空
					if(count==0){
						sb.append(fi.getName()).append("=").append(fi.get(obj));
					}else{
						sb.append(",").append(fi.getName()).append("=").append(fi.get(obj));
					}
					count++;
				}
			}
		}
		sb.append("}");
		return sb;
	}
	
	/**
	 * 参数的解析，现在针对的是简单类型（包含String），Map以及DTO对象
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private StringBuilder getArgumentsParams(Object[] object) throws Exception{
		int count=0;
		StringBuilder sb=new StringBuilder();
		for(Object obj: object){
			if(obj!=null){
				if(count>0){
					sb.append(",");
				}
				if(obj.getClass().getName().indexOf("DTO")>0){//判断参数为DTO对象的处理
					sb.append(getDTOToString(obj));
				}else if(obj.getClass().getName().indexOf("Map")!=-1){//参数为Map对象处理
					sb.append("HashMap={");
					int countMap=0;
					for(String key:((Map<String,?>)obj).keySet()){
						if(countMap>0){
							sb.append(",");
						}
						if(((Map)obj).get(key)!=null){
							if(((Map)obj).get(key).getClass().getName().indexOf("DTO")>0){//Map中存在的DTO处理
								sb.append(key).append("={");
								sb.append(getDTOToString(((Map)obj).get(key)));
								sb.append("}");
								continue;
							}
						}
						sb.append(key).append("=").append(((Map)obj).get(key));
						countMap++;
					}
					sb.append("}");
				}else{//简单类型处理
					sb.append(obj.getClass().getName()).append("=").append(obj);
				}
				count++;
			}
		}
		return sb;
	}
	
	/**
	 * 打印系统日志通过logback实现
	 * @param invocation
	 * @throws Exception
	 */
	private void printLog(MethodInvocation invocation) throws Exception{
		Object[] object=invocation.getArguments();
		SysLogDTO dto=new SysLogDTO();
		try {
			if(object.length>0&&invocation.getMethod().getDeclaringClass().getName().indexOf("SysLogService")==-1){//针对logService不进行日志记录
				Subject currentSubject = SecurityUtils.getSubject();
				if(currentSubject!=null){
					if(currentSubject instanceof WebDelegatingSubject){//根据登陆的shiro处理对象不同获取当前登陆用户信息
						UserInfo currentUser = (UserInfo)currentSubject.getSession().getAttribute("userInfo");
						if(currentUser!=null){
							dto.setUserId(currentUser.getLoginName());
						}else{
							dto.setUserId("-1");
						}
					}else{
						dto.setUserId("-1");
					}
					dto.setType("0");
					//获取模块名称
					String moduleName=invocation.getMethod().getDeclaringClass().getName().substring(15);
					moduleName=moduleName.substring(0, moduleName.indexOf("."));
					dto.setModuleName(moduleName);
					//获取类名称
					dto.setClassName(invocation.getMethod().getDeclaringClass().getName());
					//获取执行的方法名称
					dto.setMethodName(invocation.getMethod().getName());
					//获取执行方法中的参数信息
					dto.setParamInfo(getArgumentsParams(object).toString());
					logger.info(dto.toString());
				}
			}
		} catch (Exception e) {
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.aop.interceptor.AbstractTraceInterceptor#invoke(org
	 * .aopalliance.intercept.MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String name = createInvocationTraceName(invocation);
		if(isLog){
			printLog(invocation);
		}
		long beginTime = System.currentTimeMillis();
		try {
			return invocation.proceed();
		} finally {
			long runTime = System.currentTimeMillis() - beginTime;
			/* 执行时间超过1s时打印信息 */
			if (runTime > maxAllowedTimeMillis) {
				//System.out.println("方法执行时间是 " + runTime+ " ms，已超出速度允许值，请检查方法的执行效率:" + name);
				logger.warn("方法执行时间是 " + runTime+ " ms，存在执行效率问题，请优化:" + name);
				persistencePerformance(runTime, name);
			}
		}
	}

	/**
	 * 抛出异常的后处理
	 * 
	 * @param method
	 * @param args
	 * @param target
	 * @param throwable
	 */
	public void afterThrowing(Method method, Object[] args, Object obj,
			RuntimeException throwable) {
//		try {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("obj", obj);
//			map.put("method", method);
//			map.put("exception", throwable);
//			PlatformPerformancePersistenceInterface p = SpringFactory
//					.getBean(PlatformPerformancePersistenceInterface.class);
//			p.saveExceptionInfoToDB(map);
//		} catch (NoSuchBeanDefinitionException e1) {
//			defaultLogger.warn("没有找到持久化异常信息的实现类，应该是没有添加相应的jar包");
//		} catch (Exception e2) {
//			defaultLogger.error("将异常信息持久化到数据库时出错，请检查子工程。");
//		}
	}

	/**
	 * 持久化耗时的方法
	 * 
	 * @param runTime
	 * @param name
	 */
	private void persistencePerformance(long runTime, String name) {
//		try {
//			PlatformPerformancePersistenceInterface p = SpringFactory
//					.getBean(PlatformPerformancePersistenceInterface.class);
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("TIME", new Date());
//			map.put("SPEND_TIME", runTime);
//			map.put("METHOD_NAME", name);
//			p.saveMethodPerformanceToDB(map);
//		} catch (NoSuchBeanDefinitionException e1) {
//			defaultLogger.warn("没有找到持久化耗时信息的实现类，应该是没有添加相应的jar包");
//		} catch (Exception e2) {
//			defaultLogger.error("将耗时信息持久化到数据库时出错，请检查子工程。");
//		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.aop.interceptor.AbstractTraceInterceptor#isLogEnabled
	 * (org.apache.commons.logging.Log)
	 */
//	@Override
//	protected boolean isLogEnabled(Log logger) {
//		return true;
//	}

	/**
	 * @return the maxAllowedTimeMillis
	 */
	public Long getMaxAllowedTimeMillis() {
		return maxAllowedTimeMillis;
	}

	/**
	 * 设置一个方法执行时最大应该执行的时间（毫秒），超过这个时间将在控制台输出错误信息
	 * 
	 * @param maxAllowedTimeMillis
	 *            the maxAllowedTimeMillis to set
	 */
	public void setMaxAllowedTimeMillis(Long maxAllowedTimeMillis) {
		this.maxAllowedTimeMillis = maxAllowedTimeMillis;
	}

	public boolean isLog() {
		return isLog;
	}

	public void setLog(boolean isLog) {
		this.isLog = isLog;
	}

}
