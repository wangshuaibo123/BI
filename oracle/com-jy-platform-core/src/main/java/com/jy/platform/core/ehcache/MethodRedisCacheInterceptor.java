package com.jy.platform.core.ehcache;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.ShardedJedis;
import redis.clients.util.SafeEncoder;

import com.jy.platform.core.common.SerializeUtil;
import com.jy.platform.core.redis.JedisSentinelPool;


public class MethodRedisCacheInterceptor implements MethodInterceptor,InitializingBean {
	private static final Log logger = LogFactory.getLog(MethodRedisCacheInterceptor.class);  
	private JedisSentinelPool pool;

	public MethodRedisCacheInterceptor() {  
        super();  
    }  
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		 String targetName = invocation.getThis().getClass().getName();  
	        String methodName = invocation.getMethod().getName();  
	        Object[] arguments = invocation.getArguments();  
	        Object result;  
	      
	        String cacheKey = getCacheKey(targetName, methodName, arguments);
	        byte[] bytes = null;
	       
	        if(this.getPool()!=null && methodName.indexOf("ByPaging")==-1){
	        	ShardedJedis jedis = null;
				try {
					jedis = getPool().getResource();
					bytes=jedis.get(SafeEncoder.encode(cacheKey));
					//jedis.get(cacheKey);
		        	if(bytes==null || bytes.length==0){
		        		result = invocation.proceed();
		        		jedis.set(SafeEncoder.encode(cacheKey), SerializeUtil.serialise(result));
		        	}else{
		        		result=SerializeUtil.unserialize(bytes);
		        	}
		        	//getPool().returnResource(jedis);
				} catch (Exception e) {
					logger.error(e);
					//如果 redis 有问题 则不放入 redis 缓存
		        	result = invocation.proceed();
				}
				finally{
					//释放redis连接
					if(jedis != null){
						getPool().returnResource(jedis);
					}
				}
	        }else{
	        	 //如果 redis 有问题 则不放入 redis 缓存
	        	result = invocation.proceed();
	        }
	        return result;  
	}

	public JedisSentinelPool getPool() {
		return pool;
	}

	public void setPool(JedisSentinelPool pool) {
		this.pool = pool;
	}

	
	 /** 
     * 获得cache key的方法，cache key是Cache中一个Element的唯一标识 
     * cache key包括 包名+类名+方法名，如com.co.cache.service.UserServiceImpl.getAllUser 
     */  
    private String getCacheKey(String targetName, String methodName, Object[] arguments) {  
        StringBuffer sb = new StringBuffer();  
        sb.append(ObtainPropertiesInfo.getValByKey("app.code")).append(":").append("biz:")
        .append(targetName).append(":").append(methodName);  
        if ((arguments != null) && (arguments.length != 0)) {  
            for (int i = 0; i < arguments.length; i++) {  
                sb.append(":").append(arguments[i]);  
            }  
        }  
        return sb.toString();  
    }  

}
