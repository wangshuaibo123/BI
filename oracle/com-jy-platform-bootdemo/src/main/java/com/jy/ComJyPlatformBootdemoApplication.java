/*
 * NOTE:必须在最顶层目录下，以保证能扫描所有的package
 * 
 * 排除：@ComponentScan(excludeFilters={@ComponentScan.Filter(type=FilterType.REGEX, pattern="com.jy.modules.platform.syslog.*" )})
 */
package com.jy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ComJyPlatformBootdemoApplication {
    
    // private Logger logger = LoggerFactory.getLogger(ComJyPlatformBootdemoApplication.class);

	@RequestMapping("/")
    public String index() {
        return "your application is running @ " + new java.util.Date();
    }

	public static void main(String[] args) {
		SpringApplication.run(ComJyPlatformBootdemoApplication.class, args);
		//SpringApplication.run("classpath:spring-base.xml", args);
	}
}
