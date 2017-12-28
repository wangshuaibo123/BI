package com.jy.platform.core.zookeeper.zkclient;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.jy.platform.core.zookeeper.support.AbstractZookeeperClient;

public class ZkclientZookeeperClient extends AbstractZookeeperClient{
	private final ZkClient client;

	private volatile KeeperState state = KeeperState.SyncConnected;
	
	public ZkclientZookeeperClient(String connectstring){
		super(connectstring);
		client = new ZkClient(connectstring);
	}
	
	/**
	 * 创建持久节点
	 */
	public void createPersistent(String path) {
		try{
			client.createPersistent(path);
		}
		catch(ZkNodeExistsException e){
		}
	}
	
	/**
	 * 创建带数据持久节点
	 */
	public void createPersistent(String path, String data) {
		try{
			client.createPersistent(path, data);
		}
		catch(ZkNodeExistsException e){
		}
	}

	/**
	 * 创建临时节点
	 */
	public void createEphemeral(String path) {
		try{
			client.createEphemeral(path);
		}
		catch(ZkNodeExistsException e){
		}
	}
	
	/**
	 * 创建带数据临时节点
	 */
	public void createEphemeral(String path, String data) {
		try{
			client.createEphemeral(path, data);
		}
		catch(ZkNodeExistsException e){
		}
	}
	
	/**
	 * 节点是否存在
	 */
	public boolean exists(String path){
		return client.exists(path);
	}
	
	/**
	 * 更新节点数据
	 */
	public void setData(String path, String data) {
		client.writeData(path, data);
	}

	/**
	 * 获取节点数据
	 */
	public String getDate(String path) {
		try{
			return client.readData(path);
		}
		catch(ZkNoNodeException e){
			return null;
		}
	}
	
	/**
	 * 删除节点
	 */
	public void delete(String path) {
		try{
			client.delete(path);
		}
		catch(ZkNoNodeException e){
		}
	}
	
	/**
	 * 获取子节点列表
	 * 返回的是path的相对路径
	 */
	public List<String> getChildren(String path) {
		try{
			return client.getChildren(path);
        } 
		catch(ZkNoNodeException e){
            return null;
        }
	}

	/**
	 * 是否连接
	 */
	public boolean isConnected() {
		return state == KeeperState.SyncConnected;
	}

	/**
	 * 关闭客户端
	 */
	public void close() {
		client.close();
	}
	
	
	public static void main(String[] args) throws Exception{
		ZkclientZookeeperClient client = new ZkclientZookeeperClient("172.18.100.176:8081,172.18.100.149:8081,172.18.100.169:8081");
		
		System.out.println("----------------------");
		System.out.println(client.getDate("/flume/app_172_18_100_143"));
		
		Thread.sleep(10 * 1000);
	}
}
