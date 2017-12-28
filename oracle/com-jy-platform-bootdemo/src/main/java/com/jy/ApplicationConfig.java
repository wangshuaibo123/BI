/*
 * 加载配置文件，可以是classpath下的文件，也可以是外部文件file:...
 * classpath路径：locations={"classpath:application-bean1.xml","classpath:application-bean2.xml"}
 *      file路径：locations={"file:d:/test/application-bean1.xml"};
 */
package com.jy;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.jy.platform.bootbase.filter.HttpAuthFilter;

@Configuration
@ImportResource(locations = { "classpath:spring-base.xml" })
//@ImportResource(locations = { "file:./spring-base.xml" })
public class ApplicationConfig {

    private Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        if (logger.isDebugEnabled()) {
            logger.debug("##开始Filter配置");
        }
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        HttpAuthFilter authFilter = new HttpAuthFilter();
        registrationBean.setFilter(authFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}