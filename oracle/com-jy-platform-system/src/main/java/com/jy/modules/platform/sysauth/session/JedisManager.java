package com.jy.modules.platform.sysauth.session;

import java.util.Collection;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.jy.platform.core.redis.JedisSentinelPool;
/**
 * @ClassName: JedisManager 
 * @Description: redis管理
 * @author luoyr
 * @date 2015年6月2日 下午4:52:22 
 *
 */
public class JedisManager {
	/***redis 监听器连接池**/
	private JedisSentinelPool jedisSentinelPool;
	/**
	 * @Title: getJedis 
	 * @Description: 得到redis管理类
	 * @return ShardedJedis    返回类型 
	 * @throws
	 */
    public ShardedJedis getJedis() {
    	ShardedJedis jedis = null;
        try {
        	jedis = getJedisSentinelPool().getResource();
        } catch (Exception e) {
            throw new JedisConnectionException(e);
        }
        return jedis;
    }
    /**
     * @Title: returnResource 
     * @Description: 操作完成后,释放资源
     * @param jedis
     * @param isBroken     
     * @return void    返回类型 
     * @throws
     */
    public void returnResource(ShardedJedis jedis, boolean isBroken) {
        if (jedis == null)
            return;
        if (isBroken)
        	getJedisSentinelPool().returnBrokenResource(jedis);
        else
        	getJedisSentinelPool().returnResource(jedis);
    }
    /**
     * @Title: getValueByKey 
     * @Description: 得到内容 
     * @param  dbIndex 数据库标识
     * @param  key 关键key
     * @throws Exception    
     * @return byte[]    返回类型 
     */
    public byte[] getValueByKey(int dbIndex, byte[] key) throws Exception {
        ShardedJedis jedis = null;
        byte[] result = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
        return result;
    }
    /**
     * @Title: getKeys 
     * @Description: 得到所有key匹配内容
     * @param  dbIndex 数据库标识
     * @param  keyPattern
     * @throws Exception    
     * @return Set<byte[]>    返回类型 
     */
    public Set<byte[]> getKeys(int dbIndex,byte[] keyPattern)throws Exception{
    	ShardedJedis jedis = null;
    	boolean isBroken = false;
    	Set<byte[]> result = null;
    	try {
            jedis = getJedis();
            //result = jedis.hkeys(keyPattern);
            Collection<Jedis> allJedis=jedis.getAllShards();
	        for(Jedis t:allJedis){
	        	Set<byte[]> keys=t.keys(keyPattern);
	        	if(result == null){
	        		result  = keys;
	        	}else{
	        		result.addAll(keys);
	        	}
	        }
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    	return result;
    }
    /**
     * @Title: deleteByKey 
     * @Description: 删除元素
     * @param  dbIndex
     * @param  key
     * @throws Exception    
     * @return void    返回类型 
     */
    public void deleteByKey(int dbIndex, byte[] key) throws Exception {
        ShardedJedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.del(key);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }
    /**
     * @Title: saveValueByKey 
     * @Description: 保存内容 
     * @param  dbIndex
     * @param  key
     * @param  value
     * @param  expireTime
     * @throws Exception    
     * @return void    返回类型 
     */
    public void saveValueByKey(int dbIndex, byte[] key, byte[] value, int expireTime)
            throws Exception {
        ShardedJedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            jedis.set(key, value);
            if (expireTime > 0)
                jedis.expire(key, expireTime);
        } catch (Exception e) {
            isBroken = true;
            throw e;
        } finally {
            returnResource(jedis, isBroken);
        }
    }

	public JedisSentinelPool getJedisSentinelPool() {
		return jedisSentinelPool;
	}

	public void setJedisSentinelPool(JedisSentinelPool jedisSentinelPool) {
		this.jedisSentinelPool = jedisSentinelPool;
	}

   
}
