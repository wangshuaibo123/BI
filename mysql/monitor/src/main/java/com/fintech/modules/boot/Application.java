package com.fintech.modules.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fintech.platform.api.sysconfig.SysConfigAPI;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@EnableTransactionManagement
@ImportResource(value= {"${spring_basexml.path}"})
//@EnableEurekaClient
public class Application extends SpringBootServletInitializer{
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

    	return application.sources(Application.class);
    }
    /**
     * 通过@ImportResource，亦可以通过代码@bean方式实现
     * @param args
     */
    public static void main(String[] args) {
    	logger.info("========SpringBoot start=================================");
    	final ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
    	SysConfigAPI.setApplicationContext(applicationContext);
        logger.info("==============SpringBoot Start Success=========");
        
        
    }

}
