package com.fintech.modules.platform.sysauth.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.util.SafeEncoder;

import com.fintech.modules.platform.sysauth.session.JedisManager;
import com.fintech.modules.platform.sysauth.session.SerializeUtil;

public class JedisShiroCache<K, V> implements Cache<K, V> {
	private static final Logger log = LoggerFactory.getLogger(JedisShiroCache.class);  
	
    private static final String REDIS_SHIRO_CACHE = "shiro-cache:";
    private static final int DB_INDEX = 1;

    private JedisManager jedisManager;

    private String name;

    public JedisShiroCache(String name, JedisManager jedisManager) {
        this.name = name;
        this.jedisManager = jedisManager;
    }

    /**
     * 自定义名称
     */
    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws CacheException {
        byte[] byteKey = SafeEncoder.encode(buildCacheKey(key));
        byte[] byteValue = new byte[0];
        try {
            byteValue = jedisManager.getValueByKey(DB_INDEX, byteKey);
        } catch (Exception e) {
            log.error("get cache error",e);
        }
        log.debug("get cache ["+SafeEncoder.encode(buildCacheKey(key))+"]");
        return (V) SerializeUtil.deserialize(byteValue);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
        	log.debug("put cache ["+SafeEncoder.encode(buildCacheKey(key))+"]");
            jedisManager.saveValueByKey(DB_INDEX, SafeEncoder.encode(buildCacheKey(key)),
                    SerializeUtil.serialize(value), -1);
        } catch (Exception e) {
            log.error("put cache error",e);
        }
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
        	log.debug("remove cache ["+SafeEncoder.encode(buildCacheKey(key))+"]");
            jedisManager.deleteByKey(DB_INDEX, SafeEncoder.encode(buildCacheKey(key)));
        } catch (Exception e) {
            log.error("remove cache error",e);
        }
        return previos;
    }

    @Override
    public void clear() throws CacheException {
        //TODO
    	log.debug("clear cache >>> ");
    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    @Override
    public Set<K> keys() {
    	Set<K> keys = null;
    	try {
	        Set<byte[]> byteSet = jedisManager.getKeys(DB_INDEX,SafeEncoder.encode(this.REDIS_SHIRO_CACHE + "*"));  
	        keys = new HashSet<K>(); 
	        //key需要解码
	        for (byte[] bs : byteSet) {  
	            keys.add((K) SafeEncoder.encode(bs));
	        }  
    	} catch (Exception e) {
			log.error("get keys cache error",e);
		} 
    	log.debug("keys cache size ["+keys.size()+"]");
        return keys;  
    }

    @Override
    public Collection<V> values() {
    	Set<byte[]> byteSet = null;
    	List<V> result = null;
		try {
			byteSet = jedisManager.getKeys(DB_INDEX,SafeEncoder.encode(this.REDIS_SHIRO_CACHE + "*"));
			 
	        result = new LinkedList<V>();  
	        for (byte[] bs : byteSet) {  
	            result.add((V) SerializeUtil.deserialize(jedisManager  
	                    .getValueByKey(DB_INDEX,bs)));  
	        } 
		} catch (Exception e) {
			log.error("get values cache error",e);
		} 
		log.debug("values cache size ["+result.size()+"]");
        return result; 
    }

    private String buildCacheKey(Object key) {
        return REDIS_SHIRO_CACHE + getName() + ":" + key;
    }
}
