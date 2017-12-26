package com.fintech.modules.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fintech.platform.api.sysconfig.SysConfigAPI;

/*@EnableZuulProxy
@EnableDiscoveryClient*/
/*@EnableConfigServer*/
/*@EnableCircuitBreaker
@EnableHystrixDashboard*/

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
/*@MapperScan("com.fintech.modules.boot")*/
@EnableTransactionManagement
//@ImportResource(value= {"classpath:spring-base.xml"})
@ImportResource(value= {"${spring_basexml.path}"})
public class Application extends SpringBootServletInitializer{
    private static Logger logger = LoggerFactory.getLogger(Application.class);
    
   /*

    @Autowired
    private LoadBalancerClient loadBalancer;
    @Autowired
    private DiscoveryClient discovery;  */

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
