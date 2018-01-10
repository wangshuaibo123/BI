package com.fintech.platform.jbpm4.service;

import java.io.Serializable;
/**
 * 定义工作流  节点 单独配置 <java ></java>
 * @description:定义 工作流 实现java 代码调用的 接口
 * @author
 * @date:2014年11月9日下午2:09:47
 */
public interface JYJbpmJavaService extends Serializable {
	/**
	 * 定义接口实现方法 
	 * @param bizTabId jbpm4_biz_tab 主键ID
	 * @param proInsId 流程实例ID (主流程)
	 * @param bizInfId 业务表主键ID
	 * @param bizTabName 业务表名称
	 * @return 返回流程 路径名称
	 */
	public String executeJavaMethod(String bizTabId,String proInsId,String bizInfId,String bizTabName);

}
