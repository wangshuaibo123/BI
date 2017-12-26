package com.fintech.platform.core.redis;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisSentinelPoolSpringTest{
	
//	@Autowired
//	private JedisSentinelPool jedisSentinelPool;
	
	public void testX() throws Exception {

		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-redis.xml");
		JedisSentinelPool jedisSentinelPool = (JedisSentinelPool) ac.getBean("jedisSentinelPool");

		ShardedJedis j = null;
		for (int i = 0; i < 100; i++) {
			try {
				j = jedisSentinelPool.getResource();
			    j.set("KEY: " + i, "" + i);
			    System.out.print(i);
			    System.out.print(" ");
			    Thread.sleep(500);
			    jedisSentinelPool.returnResource(j);
			} catch (JedisConnectionException e) {
				System.out.print("x");
				i--;
				Thread.sleep(1000);
			}
		}
		
		System.out.println("");
		
		for (int i = 0; i < 100; i++) {
			try {
				j = jedisSentinelPool.getResource();
				System.out.print(j.get("KEY: " + i)+".");
				Thread.sleep(500);
				jedisSentinelPool.returnResource(j);
			} catch (JedisConnectionException e) {
				System.out.print("x");
				i--;
				Thread.sleep(1000);
			}
		}
		jedisSentinelPool.destroy();
  	}
	
	public  static void main(String []args){
		JedisSentinelPoolSpringTest t = new JedisSentinelPoolSpringTest();
		try {
			t.testX();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}