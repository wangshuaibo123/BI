package  com.jy.platform.restservice.spring;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;

/**
 * 平台监控一个方法执行速度的拦截器 wangxz
 */
public class PerformanceInterceptor extends PerformanceMonitorInterceptor
		implements ThrowsAdvice {
	
	private static final long serialVersionUID = 7302350984439928367L;
	
	static Logger logger = LoggerFactory.getLogger(PerformanceInterceptor.class);

	/* 一个方法执行时默认允许最大执行时间 */
	private Long maxAllowedTimeMillis = 1000L;

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

}
