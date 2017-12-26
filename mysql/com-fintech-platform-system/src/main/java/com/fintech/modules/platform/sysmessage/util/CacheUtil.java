package com.fintech.modules.platform.sysmessage.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisException;

import com.fintech.platform.api.msg.Msg;
import com.fintech.platform.core.redis.JedisSentinelPool;

/**
 * @Description: 缓存操作类
 * @author
 * @date 2014年11月14日 下午3:29:42
 */
public class CacheUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheUtil.class);
	
	/**消息在cache中的存储前缀名称，使用的数据库表名**/
	public static final String KEY_PREFIX = "T_MESSAGE";
	/**消息用户关系在cache中的存储前缀名称，使用的数据库表名**/
	public static final String KEY_PREFIX_REL = "T_USER_MSG_RELATION";
	/**消息类型key**/
	public static final boolean MSG_KEY_TYPE = true; //默认为消息类型KEY，FALSE为建立消息关系KEY

	
	/**
	 * <p>构建存储到缓存中的key 
	 * <p>共分两种key:
	 * <p>1.消息类型KEY： 格式 T_MESSAGE : 信息ID：系统标识：消息类型
	 * <p>2.消息关系类型KEY： 格式 T_USER_MSG_RELATION : 用户ID：系统标识：消息ID
	 * @param msg 消息实体
	 * @param userId 用户ID，只针对专有用户
	 * @param MSG_KEY_TYPE 
	 * @return
	 */
	public static String buildKey(Msg msg,String userId,boolean KeyType){
		StringBuilder builder = new StringBuilder();
		if (KeyType) {
			builder.append(KEY_PREFIX);
			builder.append(":");
			builder.append(msg.getId());
			builder.append(":");
			builder.append(msg.getSysFlag());
			builder.append(":");
			builder.append(msg.getMsgType());
		}else {
			builder.append(KEY_PREFIX_REL);
			builder.append(":");
			builder.append(userId);
			builder.append(":");
			builder.append(msg.getSysFlag());
			builder.append(":");
			builder.append(msg.getId());
		}
		return builder.toString();
	}

	/**
	 * 根据key值获取value
	 * @param key
	 * @return
	 */
	public static String get(JedisSentinelPool pool,String key) throws Exception {
		String result = null;
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			result = jedis.get(key);
			/*if (logger.isDebugEnabled()) {
				logger.debug("[key:" + key + "  value:" + result + "]");
			}*/
		} catch (JedisException je) {
			throw je;
		} finally {
			if (jedis != null)
				pool.returnResource(jedis);
		}
		return result;
	}
	
	
	/**
	 * 缓存中是否存在KEY值
	 * @param key
	 * @return
	 */
	public static boolean isExistKey(JedisSentinelPool pool,String key) throws Exception {
		boolean result = false;
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			result = jedis.exists(key);
		} catch (JedisException je) {
			throw je;
		} finally {
			if (jedis != null)
				pool.returnResource(jedis);
		}
		return result;
	}
	
	
	/**
	 * 存储到redis服务中
	 * @param target
	 */
	public static void put(JedisSentinelPool pool,String key, String value)throws Exception {

		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set(key, value);
		} catch (JedisException je) {
			logger.error("存储key:" + key + "异常", je);
			throw je;
		} finally {
			if (jedis != null)
				pool.returnResource(jedis);
		}
	}
	
	/**
	 * 根据带有正则的key查询所有符合条件的key值
	 * @param entityKey
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getKeyByCondition(JedisSentinelPool pool,String entityKey) throws Exception{
		String result = null;
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			Collection allJedis = jedis.getAllShards();
			StringBuilder builder = new StringBuilder();
			for (Iterator<?> iterator = allJedis.iterator(); iterator.hasNext();) {
				Jedis object = (Jedis) iterator.next();
				builder.append(object.keys(entityKey));
			}
			result = builder.toString();
		} catch (JedisException je) {
			throw je;
		} finally {
			if (jedis != null)
				pool.returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 打印redis中数据 紧供测试
	 * @param pool
	 * @param entityKey
	 * @return
	 * @throws Exception
	 */
	public static void printRedisData(JedisSentinelPool pool,String entityKey) throws Exception{
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			System.out.println("#######################print redis data begin#############################");
			Collection allJedis = jedis.getAllShards();
			for (Iterator<?> iterator = allJedis.iterator(); iterator.hasNext();) {
				Jedis object = (Jedis) iterator.next();
				Set<String> keys = object.keys(entityKey);
				for (Iterator iterator2 = keys.iterator(); iterator2.hasNext();) {
					String key = (String) iterator2.next();
					String value = jedis.get(key);
					System.out.println("key:"+key +"  value:" +value);
				}
			}
			System.out.println("#######################print redis data end#############################");
		} catch (JedisException je) {
			throw je;
		} finally {
			if (jedis != null)
				pool.returnResource(jedis);
		}
		
	}
	
	
	
	public static void clear(JedisSentinelPool pool) throws Exception{
		ShardedJedis jedis = null;
		try {
			jedis = pool.getResource();
			Collection allJedis = jedis.getAllShards();
			for (Iterator<?> iterator = allJedis.iterator(); iterator.hasNext();) {
				Jedis object = (Jedis) iterator.next();
				object.flushDB();
			}
		} catch (JedisException je) {
			throw je;
		} finally {
			if (jedis != null)
				pool.returnResource(jedis);
		}
	}
	
	
	
	
	
}
