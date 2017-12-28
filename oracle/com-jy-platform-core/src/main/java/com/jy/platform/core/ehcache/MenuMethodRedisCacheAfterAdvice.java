package com.jy.platform.core.ehcache;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.SafeEncoder;
import com.jy.platform.core.redis.JedisSentinelPool;

/**
 * 菜单增删改查缓存切面处理类
 * @author JY-IT-D001
 *
 */
public class MenuMethodRedisCacheAfterAdvice implements AfterReturningAdvice,InitializingBean {
	private static final Log logger = LogFactory.getLog(MethodCacheAfterAdvice.class);  
	
	private JedisSentinelPool pool;
	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		if(this.getPool() != null){//如果 redis 有问题 则不放入 redis 缓存
			ShardedJedis jedis = null;
			try{
				jedis = getPool().getResource();
		        Collection<Jedis> allJedis=jedis.getAllShards();
		        for(Jedis t:allJedis){
		        	Set<byte[]> keys=t.keys(SafeEncoder.encode("*sys-menuCache-"+ObtainPropertiesInfo.getValByKey("app.code")+"*"));
		        	for(byte[] key:keys){
		        		jedis.del(key);
		        		logger.info("remove redis cache " + SafeEncoder.encode(key));
		        	}
		        }
			}
			catch(Exception e){
				logger.error(e);
			}
			finally{
				getPool().returnResource(jedis);
			}
		}
	}

	public JedisSentinelPool getPool() {
		return pool;
	}

	public void setPool(JedisSentinelPool pool) {
		this.pool = pool;
	}

}
