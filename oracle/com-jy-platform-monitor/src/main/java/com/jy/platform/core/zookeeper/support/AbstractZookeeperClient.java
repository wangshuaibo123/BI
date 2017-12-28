package com.jy.platform.core.zookeeper.support;

import com.jy.platform.core.zookeeper.ZookeeperClient;

public abstract class AbstractZookeeperClient implements ZookeeperClient{
	private String connectstring;//zookeeper服务器列表
	
	public String getConnectstring() {
		return connectstring;
	}

	public void setConnectstring(String connectstring) {
		this.connectstring = connectstring;
	}
	
	
	public AbstractZookeeperClient(String connectstring){
		this.connectstring = connectstring;
	}


	/**
	 * 创建节点
	 * @param path
	 * @param ephemeral 是否临时节点
	 */
	public void create(String path, boolean ephemeral) {
		int i = path.lastIndexOf("/");
		
		//如果path中包含 / ，需要先调用创建父节点，且父节点只能是持久节点
		if(i > 0){
			create(path.substring(0, i), false);
		}
		
		//是否为临时节点
		if(ephemeral){
			createEphemeral(path);
		} 
		else{
			createPersistent(path);
		}
	}
	
	/**
	 * 创建带数据节点
	 * @param path
	 * @param ephemeral 是否临时节点
	 */
	public void create(String path, boolean ephemeral, String data) {
		int i = path.lastIndexOf("/");
		
		//如果path中包含 / ，需要先调用创建父节点，且父节点只能是持久节点
		if(i > 0){
			create(path.substring(0, i), false);
		}
		
		//是否为临时节点
		if(ephemeral){
			createEphemeral(path, data);
		} 
		else{
			createPersistent(path, data);
		}
	}

	
	/** 创建持久节点 **/
	protected abstract void createPersistent(String path);
	/** 创建带数据持久节点 **/
	protected abstract void createPersistent(String path, String data);
	/** 创建临时节点 **/
	protected abstract void createEphemeral(String path);
	/** 创建带数据临时节点 **/
	protected abstract void createEphemeral(String path, String data);
	
}
