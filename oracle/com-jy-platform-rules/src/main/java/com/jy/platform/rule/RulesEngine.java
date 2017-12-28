package com.jy.platform.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

import com.jy.platform.core.ehcache.ObtainPropertiesInfo;
import com.jy.platform.core.redis.JedisSentinelPool;

public class RulesEngine {

	private static final Logger logger =  LoggerFactory.getLogger(RulesEngine.class);

	/**
	 * 规则定义服务
	 */
	private RuleDefService ruleDefService;
	
	/**
	 * redis缓存
	 */
	private JedisSentinelPool jedisSentinelPool;

	public void setRuleDefService(RuleDefService ruleDefService) {
		this.ruleDefService = ruleDefService;
	}

	/** redis中缓存的规则定义的key */
	private static final String versionKey = ObtainPropertiesInfo.getValByKey("app.code") + ":" + "ruleDefVersion";
	
	/** 本地缓存的KnowledgeBase */
	private Map<String, KnowledgeBase> knowledgeBaseCache = new HashMap<String, KnowledgeBase>();
	
	/** 本地缓存的规则定义的版本号 */
	private Map<String, String> versionCache = new HashMap<String, String>();

    /**
     * 触发规则处理
     * @param rules 要触发的规则编码
     * @param input 输入参数列表
     * @param global 输出参数
     * @throws Exception
     */
	public void execute(String rules, List<Object> input,
	        Map<String, Object> global) throws Exception {

	    KnowledgeBase kbase = loadKnowledgeBase(rules);
	    StatefulKnowledgeSession kSession = kbase.newStatefulKnowledgeSession();

	    if (input != null) {
	        for (Object o : input) {
	            kSession.insert(o);
	        }
	    }
	
	    if (global != null) {
	        Iterator<Entry<String, Object>> it = global.entrySet().iterator();
	        while (it.hasNext()) {
	            Entry<String, Object> entry = it.next();
	            kSession.setGlobal(entry.getKey(), entry.getValue());
	        }
	    }
	
	    kSession.fireAllRules();
	    kSession.dispose();
	}

	/**
	 * 触发规则处理
	 * @param rules 要触发的规则编码
	 * @param inputParam 输入参数列表
	 * @param global 输出参数
	 * @throws Exception
	 */
	public void execute(String rules, InputParam inputParam,
	        Map<String, Object> global) throws Exception {

		List<Object> list = new ArrayList<Object>();
		list.add(inputParam);

		execute(rules, list, global);
	}

	/**
	 * 根据指定的规则，获取KnowledgeBase
	 * @param rules 规则编号
	 * @return
	 * @throws Exception
	 */
	private synchronized KnowledgeBase loadKnowledgeBase(String rules) throws Exception {
		KnowledgeBase kbase = knowledgeBaseCache.get(rules);
		// 如果缓存中不存在或者缓存的kbase已经失效，则重新加载创建
		if (kbase == null || !cacheValidate(rules)) {
	        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	        //kbuilder.add(ResourceFactory.newClassPathResource("Sample.drl"), ResourceType.DRL);
	        String version = loadRules(kbuilder, rules);
	        KnowledgeBuilderErrors errors = kbuilder.getErrors();
	        if (errors.size() > 0) {
	            for (KnowledgeBuilderError error: errors) {
	            	//System.err.println(error);
	                logger.error("解析规则异常：", error.toString());
	            }
	            throw new IllegalArgumentException("解析规则异常：" + rules);
	        }
	        kbase = KnowledgeBaseFactory.newKnowledgeBase();
	        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	        
	        // 放到缓存中
	        knowledgeBaseCache.put(rules, kbase);
	        versionCache.put(rules, version);
	        
	        if (jedisSentinelPool != null) {
	        	try {
	        		ShardedJedis jedis = jedisSentinelPool.getResource();
	        		jedis.hset(versionKey, rules, version);
	        		jedisSentinelPool.returnResource(jedis);
	        	} catch (Exception e) {
	        		logger.warn("redis设置规则版本号缓存异常：" + e.getMessage());
	        	}
			}
		}
        return kbase;
    }
	
	/**
	 * 检查缓存是否有效
	 * */
	private boolean cacheValidate(String rules) {
		// 只有本地缓存的版本号和实际的版本号一直，才认为本地缓存有效
		String localVersion = versionCache.get(rules);
		String factVersion = null;
		if (jedisSentinelPool == null) {
			return false;
		}
		try {
			ShardedJedis jedis = jedisSentinelPool.getResource();
			factVersion = jedis.hget(versionKey, rules);
			jedisSentinelPool.returnResource(jedis);
		} catch (Exception e) {
			logger.warn("redis获取缓存的规则版本号异常：" + e.getMessage());
		}
		
		return localVersion.equals(factVersion);
	}

	
	/**
	 * 加载指定的规则定义
	 * @param kBuilder
	 * @param rules
	 * @throws Exception
	 */
    private String loadRules(KnowledgeBuilder kBuilder, String rules) throws Exception {
    	// 查询指定规则对应的定义
    	@SuppressWarnings("unchecked")
		List<RuleDef> ruleList = (List<RuleDef>) ruleDefService.searchRuleDefinition(rules);
    	if (ruleList == null || ruleList.isEmpty()) {
    		throw new RuntimeException("没有查询到" + rules + "对应的规则定义");
    	}

    	StringBuilder sb = new StringBuilder();
    	for (RuleDef dto: ruleList) {
    		sb.append(".").append(dto.getRuleId()).append(".").append(dto.getRuleVersion());
    		// 定义如果为空，不处理
    		if (dto.getRuleResource() == null) {
    			continue;
    		}
    		// 类型不支持，不处理
    		if ("DRL".indexOf(dto.getResourceType()) < 0) {
    			continue;
    		}
    		kBuilder.add(ResourceFactory.newByteArrayResource(dto.getRuleResource().getBytes()), 
    				parseResourceType(dto.getResourceType()));
    	}
    	return sb.toString();
    }

    /**
     * 根据定义类型的名字返回定义的类型
     * @param name 定义类型名
     * @return
     */
    private ResourceType parseResourceType(String name) {
    	return ResourceType.getResourceType(name);
    }

	public void setJedisSentinelPool(JedisSentinelPool jedisSentinelPool) {
		this.jedisSentinelPool = jedisSentinelPool;
	}

}
