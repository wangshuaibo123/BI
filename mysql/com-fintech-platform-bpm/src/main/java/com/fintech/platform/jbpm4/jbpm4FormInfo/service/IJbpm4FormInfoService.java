package com.fintech.platform.jbpm4.jbpm4FormInfo.service;

import java.util.List;
import java.util.Map;

import com.fintech.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;
/**
 * @Description: 定义工作流表单配置表接口
 * @author
 * @version 1.0, 
 * @date 2014-03-05 14:39:08
 *
 */
public interface IJbpm4FormInfoService {
	/**
	 * 分页查询 工作流表单配置表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Jbpm4FormInfoDTO> queryListOfJbpm4FormInfoByPage(Map<String,Object> paramMap) throws Exception;
	/**
	 * 查询 工作流表单配置表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<Jbpm4FormInfoDTO> queryListOfJbpm4FormInfo(Map<String,Object> paramMap) throws Exception;
	/**
	 * 查询某一个工作流表单配置表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Jbpm4FormInfoDTO queryOneJbpm4FormInfo(Map<String,Object> paramMap) throws Exception;
	/**
	 * 新增 工作流表单配置表
	 * @param paramMap
	 * @return 成功的主键ID
	 * @throws Exception
	 */
	public String addJbpm4FormInfo(Map<String,Object> paramMap) throws Exception;
	/**
	 * 修改 工作流表单配置表
	 * @param paramMap
	 * @return 主键ID
	 * @throws Exception
	 */
	public int updateJbpm4FormInfo(Map<String,Object> paramMap) throws Exception;
	/**
	 * 假删除 工作流表单配置表
	 * @param ids
	 * @return 主键ID
	 * @throws Exception
	 */
	public String deleteJbpm4FormInfo(String ids) throws Exception ;
	/**
	 * 通过流程编码 流程版本号，流程节点名称 查询是否有 Jbpm4FormInfoId
	 * @param proKey
	 * @param proVersion
	 * @param aName
	 * @return
	 */
	public String queryJbpm4FormInfoIdBysql(String proKey,String proVersion,String aName)throws Exception ;
	
}
