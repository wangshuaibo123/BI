package com.fintech.platform.core.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Pool;
import redis.clients.util.SafeEncoder;

import com.fintech.platform.core.ehcache.ObtainPropertiesInfo;

/**
 * Redis分布式锁
 */
public class RedisLock implements DistributedLock{
	private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);
	private static final int DEFAULT_EXPIRE_TIME = 60;//默认的过期时间：60s
	private static final String LOCK_KEY_PREFIX = "REDIS_LOCK";//默认的过期时间：60s
//	private ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();
	private Pool<ShardedJedis> pool;
	
	public Pool<ShardedJedis> getPool(){
		return pool;
	}

	public void setPool(Pool<ShardedJedis> pool){
		this.pool = pool;
	}
	
	
	/**
	 * 加锁
	 * @param module 模块名称
	 * @param bizKey 业务标示
	 * @param expireTime 过期时间，以秒为单位（默认60s）
	 * @return
	 * true ：加锁成功
	 * false ： 加锁失败，锁正在被占用
	 */
	@Override
	public boolean getLock(String module, String bizKey, int expireTime){
		if(module==null || "".equals(module) || bizKey==null || "".equals(bizKey)){
			logger.error("parameters is null");
			return false;
		}
		
		if(getPool() != null){
			ShardedJedis jedis = null;
			String lockKey = getLockKey(module,bizKey);
			
			try{
				jedis = getPool().getResource();
				
				long currentTime = System.currentTimeMillis();
				long result = jedis.setnx(SafeEncoder.encode(lockKey), SafeEncoder.encode(currentTime+""));
				
				//设置锁成功
				if(result == 1L){
//					threadLocal.set(currentTime);//设置获取锁的时间
					
					//参数中有过期时间则使用
					if(expireTime > 0){
						jedis.expire(SafeEncoder.encode(lockKey), expireTime);
						if(logger.isDebugEnabled()){
							logger.debug("key：" + lockKey + " locked and expire time:" + expireTime + "s");
						}
					}
					else{
						jedis.expire(SafeEncoder.encode(lockKey), DEFAULT_EXPIRE_TIME);
						if(logger.isDebugEnabled()){
							logger.debug("key：" + lockKey + " locked and expire time:" + DEFAULT_EXPIRE_TIME + "s");
						}
					}
					
					return true;
				}
				else{
					if(logger.isDebugEnabled()){
						logger.debug("key：" + lockKey + " has already bean locked");
					}
					return false;
				}
			}
			catch(Exception e){
				logger.error("lock error", e);
				return false;
			}
			finally{
				getPool().returnResource(jedis);
			}
		}
		else{
			logger.error("jedisSentinelPool is null");
			return true;//不做严格限制，pool为空返回true
		}
	}
	
	
	/**
	 * 解锁
	 * @param module 模块名称
	 * @param bizKey 业务标示
	 */
	@Override
	public void unLock(String module, String bizKey){
		if(module==null || "".equals(module) || bizKey==null || "".equals(bizKey)){
			logger.error("parameters is null");
		}
		
		if(getPool() != null){
			ShardedJedis jedis = null;
			String lockKey = getLockKey(module,bizKey);
			
			try{
				jedis = getPool().getResource();
				
				//解锁
				jedis.del(SafeEncoder.encode(lockKey));
			}
			catch(Exception e){
				logger.error("unlock error", e);
			}
			finally{
				getPool().returnResource(jedis);
			}
		}
		else{
			logger.error("jedisSentinelPool is null");
		}
	}
	

	/**
	 * 解锁（暂不使用）
	 * 即使持有锁，在解锁前也应先检查锁是否过期
	 * 且只能删除自己设置的锁
	 * @param module 模块名称
	 * @param bizKey 业务标示
	 */
//	@Override
//	public void unLock(String module, String bizKey){
//		if(module==null || "".equals(module) || bizKey==null || "".equals(bizKey)){
//			logger.error("parameters is null");
//		}
//		
//		if(this.getPool() != null){
//			ShardedJedis jedis = null;
//			byte[] bytes = null;
//			String lockKey = getLockKey(module,bizKey);
//			
//			try{
//				jedis = getPool().getResource();
//				
//				bytes = jedis.get(SafeEncoder.encode(lockKey));
//				
//				//锁存在
//				if(bytes!=null && bytes.length!=0){
//					if(logger.isDebugEnabled()){
//						logger.debug("key：" + lockKey + " exists!");
//					}
//					Long lockTime = Long.valueOf(SafeEncoder.encode(bytes));
//					
//					//是当前线程上的锁
//					if(lockTime!=null && threadLocal.get()!=null 
//							&& lockTime.longValue()==threadLocal.get().longValue()){
//						if(logger.isDebugEnabled()){
//							logger.debug("-----------lockTime=="+lockTime+",threadLocal.get()=="+threadLocal.get());
//						}
//						//解锁
//						jedis.del(SafeEncoder.encode(lockKey));
//					}
//					else{
//						if(logger.isDebugEnabled()){
//							logger.debug("###########lockTime=="+lockTime+",threadLocal.get()=="+threadLocal.get());
//						}
//					}
//				}
//				//锁已经不存在
//				else{
//					if(logger.isDebugEnabled()){
//						logger.debug("key：" + lockKey + " has already bean unlocked");
//					}
//				}
//			}
//			catch(Exception e){
//				logger.error("unlock error", e);
//			}
//		}
//		else{
//			logger.error("jedisSentinelPool is null");
//		}
//	}

	
	/**
	 * 拼装
	 * @param module
	 * @param bizKey
	 * @return
	 */
	private String getLockKey(String module, String bizKey){
		StringBuffer sb = new StringBuffer();
		sb.append(LOCK_KEY_PREFIX)
		  .append(":").append(ObtainPropertiesInfo.getValByKey("app.code"))
		  .append(":").append(module)
		  .append(":").append(bizKey);
		
		return sb.toString();
	}
}
