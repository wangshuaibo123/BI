package com.fintech.modules.platform.sysauth.service.shiro;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.SafeEncoder;

import com.fintech.platform.core.common.SerializeUtil;
import com.fintech.platform.core.redis.JedisSentinelPool;

public class BaseRedisCacheAspect implements InitializingBean {

	 protected final Logger log = LoggerFactory.getLogger(getClass());
//	@Autowired
	private JedisSentinelPool pool;
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}
	
	public void clear(String preFix){
		if(this.getPool() != null){//如果 redis 有问题 则不放入 redis 缓存
			ShardedJedis jedis= getPool().getResource();
	        Collection<Jedis> allJedis=jedis.getAllShards();
	        for(Jedis t:allJedis){
	        	Set<byte[]> keys=t.keys(SafeEncoder.encode("*"+preFix+"*"));
	        	for(byte[] key:keys){
	        		jedis.del(key);
	        		log.info("remove redis cache " + SafeEncoder.encode(key));
	        	}
	        }
	        getPool().returnResource(jedis);
	}

}

	public JedisSentinelPool getPool() {
		return pool;
	}

	public void setPool(JedisSentinelPool pool) {
		this.pool = pool;
	}
	
	public <T> T get(String key){
        try {
			log.debug("cacheName:{}, get key:{}", key);
			if (StringUtils.isEmpty(key)) {
			    return null;
			}
			byte[] bytes = null;
			if(getPool()!=null){
				ShardedJedis jedis = null;
				jedis = getPool().getResource();
				bytes=jedis.get(SafeEncoder.encode(key));
				getPool().returnResource(jedis);
			}else{
				return null;
			}
			
			if(bytes==null || bytes.length==0){
				return null;
			}else{
				log.debug("Find object from redis");
				return (T) SerializeUtil.unserialize(bytes);
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public void put(String key, Object value){
		if(getPool()!=null){
			ShardedJedis jedis = null;
        	jedis = getPool().getResource();
        	jedis.set(SafeEncoder.encode(key), SerializeUtil.serialise(value));
        	getPool().returnResource(jedis);
		}
	}
}