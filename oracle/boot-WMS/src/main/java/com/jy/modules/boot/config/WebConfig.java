package com.jy.modules.boot.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.jy.platform.restservice.rest.AuthFilter;

@Configuration
public class WebConfig {
	private static Logger logger = LoggerFactory.getLogger(WebConfig.class);
	
	
	@Bean(name="beanNameAutoProxyCreator")
	public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
		logger.info("初始化druid-spring监控");
		BeanNameAutoProxyCreator autoProxyCreator = new BeanNameAutoProxyCreator();
		autoProxyCreator.setProxyTargetClass(true);
		String[] beanNames = {"*Mapper","*Service","*Controller"};
		autoProxyCreator.setBeanNames(beanNames);
		String[] interceptorNames = {"druid-stat-interceptor"};
		autoProxyCreator.setInterceptorNames(interceptorNames);
		return autoProxyCreator;
	}
	
	@Bean(name="encodingFilter")
	public FilterRegistrationBean encodingFilter() {
		FilterRegistrationBean filterBean = new FilterRegistrationBean();
		filterBean.setName("encodingFilter");
        
		CharacterEncodingFilter en = new CharacterEncodingFilter();
		filterBean.setFilter(en);
		
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        filterBean.setUrlPatterns(urlPatterns);
        
        filterBean.addInitParameter("encoding", "UTF-8");
        filterBean.addInitParameter("forceEncoding", "true");
        return filterBean;
	}
	
	@Bean(name="ssessionListener")
	public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> ssessionListener() {
		ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> bean = new ServletListenerRegistrationBean<SingleSignOutHttpSessionListener>();
		bean.setName("ssessionListener");
        
		SingleSignOutHttpSessionListener lis = new SingleSignOutHttpSessionListener();
		bean.setListener(lis);
		
        return bean;
	}
	
	@Bean(name="myContextListenerBean")
	@Lazy
	public ServletListenerRegistrationBean<MyContextListenerBean> myContextListenerBean() {
		ServletListenerRegistrationBean<MyContextListenerBean> bean = new ServletListenerRegistrationBean<MyContextListenerBean>();
		bean.setName("myContextListenerBean");
        
		MyContextListenerBean lis = new MyContextListenerBean();
		bean.setListener(lis);
		
        return bean;
	}
	
	@Bean(name="singleSignOutFilter")
	public FilterRegistrationBean singleSignOutFilter() {
		FilterRegistrationBean filterBean = new FilterRegistrationBean();
		filterBean.setName("encodingFilter");
        
		SingleSignOutFilter bean = new SingleSignOutFilter();
		filterBean.setFilter(bean);
		
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        filterBean.setUrlPatterns(urlPatterns);
        return filterBean;
	}
	
	@Bean(name="shiroFilter")
    public FilterRegistrationBean springSecurityFilterChain() {     
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setName("shiroFilter");
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        delegatingFilterProxy.setTargetFilterLifecycle(true);
        filterRegBean.setFilter(delegatingFilterProxy);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        filterRegBean.setUrlPatterns(urlPatterns);
       
        return filterRegBean;
    }
	//是否启动reset api请求安全过滤
	//@Bean(name="authFilter")
	public FilterRegistrationBean authFilter() {
		FilterRegistrationBean filterBean = new FilterRegistrationBean();
		filterBean.setName("authFilter");
        
		AuthFilter bean = new AuthFilter();
		filterBean.setFilter(bean);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/api/*");
        filterBean.setUrlPatterns(urlPatterns);
        filterBean.addInitParameter("excludeUrl", "js,css,jpg,gif,png");
        
        return filterBean;
	}
	
	@Bean(name="druidWebStatFilter")
    public FilterRegistrationBean druidWebStatFilter() {     
        FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        filterRegBean.setName("druidWebStatFilter");
        
        WebStatFilter wsf = new WebStatFilter();
        
        filterRegBean.setFilter(wsf);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        filterRegBean.setUrlPatterns(urlPatterns);
        filterRegBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        
        return filterRegBean;
    }
	
	@Bean(name="druidStatView")
	public ServletRegistrationBean druidStatView() {     
		ServletRegistrationBean bean = new ServletRegistrationBean();
		bean.setName("druidStatView");
        
		StatViewServlet ws = new StatViewServlet();
        bean.setServlet(ws);
        Collection<String> urlMappings = new LinkedHashSet<String>();
        urlMappings.add("/druid/*");
		bean.setUrlMappings(urlMappings);
       
        return bean;
    }
	
	@Bean(name="druid-stat-interceptor")
	public DruidStatInterceptor druidStatInterceptor() {
		DruidStatInterceptor interceptor = new DruidStatInterceptor();
		return interceptor;
	}
}
