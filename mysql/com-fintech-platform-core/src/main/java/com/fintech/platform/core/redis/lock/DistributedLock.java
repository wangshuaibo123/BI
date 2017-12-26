package com.fintech.platform.core.redis.lock;

/**
 * 分布式锁接口
 */
public interface DistributedLock{
	
	/**
	 * 加锁
	 * @param module 模块名称
	 * @param bizKey 业务标示
	 * @param expireTime 过期时间，以秒为单位（默认60s）
	 * @return
	 * true ：加锁成功
	 * false ： 加锁失败，锁正在被占用
	 */
	public boolean getLock(String module, String bizKey, int expireTime);
	
	
	/**
	 * 解锁
	 * @param module 模块名称
	 * @param bizKey 业务标示
	 */
	public void unLock(String module, String bizKey);
	
	
}
