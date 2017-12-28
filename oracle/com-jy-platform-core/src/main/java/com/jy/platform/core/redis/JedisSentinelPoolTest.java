package com.jy.platform.core.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.alibaba.fastjson.JSON;

public class JedisSentinelPoolTest  {

	public void testX() throws Exception {
		
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		
		List<String> masters = new ArrayList<String>();
		masters.add("mymaster");
//		masters.add("shard2");
		
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("10.1.1.151:26379");
		sentinels.add("10.1.1.152:26379");
    
		JedisSentinelPool pool = new JedisSentinelPool(masters, sentinels, config, 60000);
		
//		ShardedJedis jedis = null;
//		try {
//			jedis = pool.getResource();
//			// do somethind...
//			// ...
//		} finally {
//			if (jedis != null) pool.returnResource(jedis);
//			pool.destroy();
//		}
		
		ShardedJedis jedis = null;
		for (int i = 0; i < 100; i++) {
			try {
				jedis = pool.getResource();
				jedis.set("key"+i, "" + i);
				System.out.print(i);
				System.out.print(" ");
				Thread.sleep(500);
				pool.returnResource(jedis);
			} catch (JedisConnectionException e) {
				System.out.println("可能failover,等待......");
				i--;
				Thread.sleep(1000);
			}
		}
    
		System.out.println("");
    
		for (int i = 0; i < 100; i++) {
			try {
				jedis = pool.getResource();
				System.out.print(jedis.get("key"+i));
				System.out.print(" ");
				Thread.sleep(500);
				pool.returnResource(jedis);
			} catch (JedisConnectionException e) {
				System.out.println("可能failover,等待......");
				i--;
				Thread.sleep(1000);
			}
		}

		pool.destroy();
	}
	
	public void testJson() throws Exception {
		
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		
		List<String> masters = new ArrayList<String>();
		masters.add("mymaster");
//		masters.add("shard2");
		
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("10.1.1.151:26379");
		sentinels.add("10.1.1.152:26379");
    
		JedisSentinelPool pool = new JedisSentinelPool(masters, sentinels, config, 60000);
		
		ShardedJedis jedis = null;
		for (int i = 0; i < 10; i++) {
			try {
				jedis = pool.getResource();
				Student student = new Student();
				student.setAge(i);
				student.setName("学生"+i);
				jedis.set("student"+i, JSON.toJSONString(student));
				System.out.print(i);
				System.out.print(" ");
				Thread.sleep(500);
				pool.returnResource(jedis);
			} catch (JedisConnectionException e) {
				System.out.println("可能failover,等待......");
				i--;
				Thread.sleep(1000);
			}
		}
    
		System.out.println("");
    
		for (int i = 0; i < 10; i++) {
			try {
				jedis = pool.getResource();
				System.out.print(jedis.get("student"+i));
				System.out.print(" ");
				Thread.sleep(500);
				pool.returnResource(jedis);
			} catch (JedisConnectionException e) {
				System.out.println("可能failover,等待......");
				i--;
				Thread.sleep(1000);
			}
		}

		pool.destroy();
	}
	


	
	class Student{
		private String name;
		private int age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}

	}
	
	public  static void main(String []args){
		JedisSentinelPoolTest t = new JedisSentinelPoolTest();
		try {
			t.testJson();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
