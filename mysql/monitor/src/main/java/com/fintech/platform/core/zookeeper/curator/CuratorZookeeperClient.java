package com.fintech.platform.core.zookeeper.curator;

import java.util.List;

import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.platform.core.zookeeper.ZookeeperClient;
import com.fintech.platform.core.zookeeper.support.AbstractZookeeperClient;

public class CuratorZookeeperClient extends AbstractZookeeperClient{
	private static final Logger logger =  LoggerFactory.getLogger(CuratorZookeeperClient.class);
	private final CuratorFramework client;
	
	
	public CuratorZookeeperClient(String connectstring){
		super(connectstring);
		client = CuratorFrameworkFactory.builder()
				      .connectString(connectstring)
				      .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
				      .connectionTimeoutMs(5000)
				      .build();
		client.start();
	}
	
	/**
	 * 创建持久节点
	 */
	public void createPersistent(String path) {
		try{
			client.create().forPath(path);
		} 
		catch(NodeExistsException e){
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	/**
	 * 创建带数据持久节点
	 */
	public void createPersistent(String path, String data) {
		try{
			client.create().forPath(path, data.getBytes());
		} 
		catch(NodeExistsException e){
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * 创建临时节点
	 */
	public void createEphemeral(String path) {
		try{
			client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
		} 
		catch(NodeExistsException e){
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	/**
	 * 创建带数据临时节点
	 */
	public void createEphemeral(String path, String data) {
		try{
			client.create().withMode(CreateMode.EPHEMERAL).forPath(path, data.getBytes());
		} 
		catch(NodeExistsException e){
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	/**
	 * 节点是否存在
	 */
	public boolean exists(String path) {
		try{
			return client.checkExists().forPath(path)==null ? false : true;
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	/**
	 * 更新节点数据
	 */
	public void setData(String path, String data) {
		try{
			client.setData().forPath(path, data.getBytes());
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取节点数据
	 */
	public String getDate(String path) {
		try{
			return new String(client.getData().forPath(path));
		} 
		catch(NoNodeException e){
			return null;
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	/**
	 * 删除节点
	 */
	public void delete(String path) {
		try{
			client.delete().forPath(path);
		} 
		catch(ZkNodeExistsException e){
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取子节点列表
	 * 返回的是path的相对路径
	 */
	public List<String> getChildren(String path) {
		try{
			return client.getChildren().forPath(path);
		} 
		catch(NoNodeException e){
			return null;
		} 
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	/**
	 * 是否连接
	 */
	public boolean isConnected() {
		client.getConnectionStateListenable();
		return client.getZookeeperClient().isConnected();
	}

	/**
	 * 关闭客户端
	 */
	public void close() {
		client.close();
	}

	
	public static void main(String[] args) throws Exception{
		ZookeeperClient client = new CuratorZookeeperClient("172.18.100.176:8081,172.18.100.149:8081,172.18.100.169:8081");
		
		
		Thread.sleep(5 * 1000);
	}

}
