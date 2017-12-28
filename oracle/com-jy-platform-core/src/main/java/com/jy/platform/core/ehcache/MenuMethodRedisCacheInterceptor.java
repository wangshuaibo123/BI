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

/**
 * 
 * 菜单查询redis缓存切面处理类
 * @author JY-IT-D001
 *
 */
public class MenuMethodRedisCacheInterceptor implements MethodInterceptor,InitializingBean {
	private static final Log logger = LogFactory.getLog(MenuMethodRedisCacheInterceptor.class);  
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
	        if(this.getPool()!=null){
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
		        	//getPool().returnResource(jedis);
				} catch (Exception e) {
					logger.error(e);
					//如果 redis 有问题 则不放入 redis 缓存
		        	result = invocation.proceed();
				}
				finally{
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
}
