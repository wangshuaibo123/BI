package com.fintech.platform.core.redis;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisClusterFactory implements FactoryBean<JedisCluster>,
		InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(JedisClusterFactory.class);
	
	private String addressPort;
	private JedisCluster jedisCluster;
	private Integer timeout;
	private Integer maxRedirections;
	private GenericObjectPoolConfig genericObjectPoolConfig;
	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
	
	public JedisCluster getObject() throws Exception {
		return jedisCluster;
	}
	public Class<? extends JedisCluster> getObjectType() {
		return (this.jedisCluster != null ? this.jedisCluster.getClass(): JedisCluster.class);
	}
	public boolean isSingleton() {
		return true;
	}
	private Set<HostAndPort> parseHostAndPort(){
		Set<HostAndPort> haps = new HashSet<HostAndPort>();
		try {
			String[] url = this.addressPort.split(",");
			for (String val :url) {
				boolean isIpPort = p.matcher(val).matches();
				if (!isIpPort) {
					logger.error("====ip 或 port 不合法");
					continue;
				}
				String[] ipAndPort = val.split(":");
				HostAndPort hap = new HostAndPort(ipAndPort[0],Integer.parseInt(ipAndPort[1]));
				haps.add(hap);
			}
		}catch (Exception ex) {
			logger.error("====连接redis 集群失败===",ex);
		}
		
		return haps;
	}
	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> haps = this.parseHostAndPort();
		
		if(haps.size() !=0)
		jedisCluster = new JedisCluster(haps, timeout, maxRedirections,genericObjectPoolConfig);

	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public void setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
	}
	public void setGenericObjectPoolConfig(
			GenericObjectPoolConfig genericObjectPoolConfig) {
		this.genericObjectPoolConfig = genericObjectPoolConfig;
	}
	public String getAddressPort() {
		return addressPort;
	}
	public void setAddressPort(String addressPort) {
		this.addressPort = addressPort;
	}
}
