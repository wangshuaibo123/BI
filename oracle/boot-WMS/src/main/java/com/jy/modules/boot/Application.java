package com.jy.modules.boot;

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

import com.jy.platform.api.sysconfig.SysConfigAPI;

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
    
   /* @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
    	//使用druid 数据库连接池
        return new com.alibaba.druid.pool.DruidDataSource();
    }*/
    /**
     * 分页插件
     * @return
     *//*
    @Bean(name="pageInterceptor")
    public Interceptor getPageInterceptor() {
    	PageInterceptor pi= new PageInterceptor(); 
        return pi;
    }*/
    /**
     * 定义sqlSessionFactory
     * @return
     * @throws Exception
     */
    /*@Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
    	Resource[] allRS = new Resource[]{};
        List<Resource> list = new ArrayList<Resource>();
         
        com.fintech.platform.restservice.mybatis.SqlSessionFactoryBean sqlSessionFactoryBean = new com.fintech.platform.restservice.mybatis.SqlSessionFactoryBean();
        //SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] rs1 = resolver.getResources("classpath*:com/portal/*daoMapper.xml");
        Resource[] rs2 = resolver.getResources("classpath*:com/pt/*daoMapper.xml");
        Resource[] rs3 = resolver.getResources("classpath:/mybatis/*.xml");
       
        
        if(rs1 != null) list.addAll(Arrays.asList(rs1));
        if(rs2 != null) list.addAll(Arrays.asList(rs2));
        if(rs3 != null) list.addAll(Arrays.asList(rs3));
        allRS = list.toArray(allRS);
        
        sqlSessionFactoryBean.setMapperLocations(allRS);
        //自动扫描domain目录, 配置包的基路径即可，多个包用;分割 
        sqlSessionFactoryBean.setTypeAliasesPackage("com.pt;com.portal");
        
        Interceptor interceptor = page;
        Interceptor[] pluins = new Interceptor[]{};
        List<Interceptor> ps = new ArrayList<Interceptor>();
        
        if(interceptor != null)  ps.add(interceptor);
        pluins = ps.toArray(pluins);
        //设置分页插件
        sqlSessionFactoryBean.setPlugins(pluins);
        
        return sqlSessionFactoryBean.getObject();
    }*/
    /*@Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }*/
    
    

}
