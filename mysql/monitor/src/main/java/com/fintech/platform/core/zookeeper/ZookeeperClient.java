package com.fintech.platform.core.zookeeper;

import java.util.List;

public interface ZookeeperClient {
	
	/**
	 * 是否连接
	 */
	boolean isConnected();

	
	/**
	 * 关闭连接
	 */
	void close();
	
	/**
	 * 节点是否存在
	 * @param path
	 * @return
	 */
	boolean exists(String path);
	
	/**
	 * 创建节点
	 * @param path
	 * @param ephemeral
	 */
	void create(String path, boolean ephemeral);
	
	/**
	 * 创建带数据节点
	 * @param path
	 * @param ephemeral
	 */
	void create(String path, boolean ephemeral, String data);
	
	
	/**
	 * 更新节点数据
	 * @param path
	 * @param data
	 */
	void setData(String path, String data);

	
	/**
	 * 删除节点
	 * @param path
	 */
	void delete(String path);
	
	
	/**
	 * 获取节点数据
	 * @param path
	 * @return
	 */
	String getDate(String path);

	/**
	 * 获取子节点列表
	 * @param path
	 * @return
	 */
	List<String> getChildren(String path);
}
