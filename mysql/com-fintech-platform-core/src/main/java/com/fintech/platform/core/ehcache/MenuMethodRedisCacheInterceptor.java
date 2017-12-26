package com.fintech.platform.core.ehcache;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.ShardedJedis;
import redis.clients.util.SafeEncoder;

import com.fintech.platform.core.common.SerializeUtil;
import com.fintech.platform.core.redis.JedisSentinelPool;

/**
 * 
 * 菜单查询redis缓存切面处理类
 * @author
 *
 */
public class MenuMethodRedisCacheInterceptor implements MethodInterceptor,InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(MenuMethodRedisCacheInterceptor.class);  
	private JedisSentinelPool pool;
	private String menusKeyPrefix = "sys-menuCache-"+ObtainPropertiesInfo.getValByKey("app.code")+":biz:-menus-";

	public MenuMethodRedisCacheInterceptor() {  
        super();  
    }  
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
	        Object result;  
	        String cacheKey = this.menusKeyPrefix+"all";
	        byte[] bytes = null;
	        if(this.getPool() != null){
	        	ShardedJedis jedis = null;
				try {
					jedis = getPool().getResource();
					bytes=jedis.get(SafeEncoder.encode(cacheKey));
					//jedis.get(cacheKey);
		        	if(bytes==null || bytes.length==0){
		        		result = invocation.proceed();
		        		jedis.set(SafeEncoder.encode(cacheKey), SerializeUtil.serialise(result));
		        	}else{
		        		logger.debug("Find object from redis");
		        		result=SerializeUtil.unserialize(bytes);
		        	}
		        	getPool().returnResource(jedis);
				} catch (Exception e) {
					logger.error(e.getMessage(),e);
					//如果 redis 有问题 则不放入 redis 缓存
		        	result = invocation.proceed();
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
}
