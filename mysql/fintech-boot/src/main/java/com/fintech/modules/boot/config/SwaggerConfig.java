package com.fintech.modules.boot.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Administrator
 *http://127.0.0.1:8080/fintech-boot/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig{

	@Bean
    public Docket restApi(){
    	Docket docket = new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.fintech.modules.etl."))
        //.paths(PathSelectors.any())
        .paths(PathSelectors.regex("/api/.*"))//过滤的接口
        .build()
        ;

    	return docket;
    }
    

	private ApiInfo apiInfo(){
        ApiInfo apiInfo= (new ApiInfoBuilder())
                .title("fintech-boot集成Swagger项目")
                .description("fintech-boot集成Swagger的Demo API")
                .termsOfServiceUrl("http://localhost:8080/")
                .contact(new Contact("fintech","","gangchen1@jieyuechina.com"))
                .version("1.0")
                .build();
        return apiInfo;
    }
}