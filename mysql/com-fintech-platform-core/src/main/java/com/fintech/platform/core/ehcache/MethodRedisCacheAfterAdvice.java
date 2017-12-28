package com.fintech.platform.core.ehcache;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Pool;
import redis.clients.util.SafeEncoder;

import com.fintech.platform.core.redis.JedisSentinelPool;

public class MethodRedisCacheAfterAdvice implements AfterReturningAdvice,InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(MethodCacheAfterAdvice.class);  
	
	private Pool<ShardedJedis> pool;
	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		String className = target.getClass().getName();
		if(this.getPool() != null){//如果 redis 有问题 则不放入 redis 缓存
			ShardedJedis jedis= getPool().getResource();
	        Collection<Jedis> allJedis=jedis.getAllShards();
	        for(Jedis t:allJedis){
	        	Set<byte[]> keys=t.keys(SafeEncoder.encode("*"+ObtainPropertiesInfo.getValByKey("app.code")+"*"+className+"*"));
	        	for(byte[] key:keys){
	        		jedis.del(key);
	        		logger.info("remove redis cache " + SafeEncoder.encode(key));
	        	}
	        }
	        getPool().returnResource(jedis);
		}
        
	}

	public Pool<ShardedJedis> getPool() {
		return pool;
	}

	public void setPool(Pool<ShardedJedis> pool) {
		this.pool = pool;
	}

}
