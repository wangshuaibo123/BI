package com.fintech.platform.jbpm4.service;

import java.util.Map;

import com.fintech.platform.jbpm4.temporaryJbpm4Info.dto.TemporaryJbpm4InfoDTO;

/**
 * 
 * @Description: 定义修改 流程表相关信息 接口
 * @author
 * @version 1.0, 
 * @date 2014-5-6 下午04:48:05
 */
public interface IModifyProInfoService {
	/**
	 * 更新流程信息 并马上生效（已经发起的流程实例，重新启动应用后会使用最新的流程）
	 * @return
	 */
	public String updateJbpm4LobTableImmediately(Map<String,Object> paramMap) throws Exception ;
	/**
	 * 发布流程
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String deployProcess(TemporaryJbpm4InfoDTO dto,String lastId) throws Exception ;
	/**
	 * 取消发布流程
	 * @param deploymentId
	 * @throws Exception
	 */
	public void cancelPublishProcess(String deploymentId)throws Exception ;
	
	/**
	 * 删除流程定义缓存
	 * @param deploymentId
	 */
	public void deleteRepositoryCache(String deploymentId);
	
}

