package com.jy.platform.jbpm4.temporaryJbpm4Info.service;

import java.util.List;
import java.util.Map;

import com.jy.platform.jbpm4.dto.JbpmBlobDTO;
import com.jy.platform.jbpm4.temporaryJbpm4Info.dto.TemporaryJbpm4InfoDTO;
/**
 * @Description: 定义工作流暂存表接口
 * @author chen
 * @version 1.0, 
 * @date 2013-10-16 14:58:04
 *
 */
public interface ITemporaryJbpm4InfoService {
	/**
	 * 分页查询 工作流暂存表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<TemporaryJbpm4InfoDTO> queryListOfTemporaryJbpm4InfoByPage(Map<String,Object> paramMap) throws Exception;
	/**
	 * 获取 流程设计暂存表 某一个对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TemporaryJbpm4InfoDTO getOneTemporaryJbpm4InfoDTO(String id) throws Exception ;
	/**
	 * 查询 工作流暂存表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List<TemporaryJbpm4InfoDTO> queryListOfTemporaryJbpm4Info(Map<String,Object> paramMap) throws Exception;
	/**
	 * 查询某一个工作流暂存表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public TemporaryJbpm4InfoDTO queryOneTemporaryJbpm4Info(Map<String,Object> paramMap) throws Exception;
	/**
	 * 新增 工作流暂存表
	 * @param paramMap
	 * @return 成功的主键ID
	 * @throws Exception
	 */
	public String addTemporaryJbpm4Info(Map<String,Object> paramMap) throws Exception;
	/**
	 * 修改 工作流暂存表
	 * @param paramMap
	 * @return 主键ID
	 * @throws Exception
	 */
	public int updateTemporaryJbpm4Info(Map<String,Object> paramMap) throws Exception;
	/**
	 * 假删除 工作流暂存表
	 * @param ids
	 * @return 主键ID
	 * @throws Exception
	 */
	public String deleteTemporaryJbpm4Info(String ids) throws Exception ;
	/**
	 * 流程度设计时，需要查询的流程信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public List querySomeTempJbpm4InfoBySql(Map<String, Object> paramMap) throws Exception ;
	/**
	 * 通过暂存表主键获取流程信息
	 * @param id
	 * @return
	 */
	public String getOneProcessXMLContent(String id);
	
	/**
	 * 通过流程名称，版本号或者流程实例id
	 * 查询出其在暂存表中的流程信息id
	 * @param id
	 * @return
	 */
	public String queryTempJbpm4Info(String name,String version,String processInstanceId);
	
	/**
	 * 更新流程图片信息 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int updateProPngOfTempJbpm4Info(JbpmBlobDTO pngDTO) throws Exception;
	/**
	 * 获取流程图片信息 返回图片
	 * @param paraMap
	 * @return
	 * @throws Exception
	 */
	public byte[] fetchProPng(Map paraMap)throws Exception;
	/**
	 * 获取流程图片信息 返回图片 by xyz
	 * @param paraMap
	 * @return
	 * @throws Exception
	 */
	public byte[] fetchProPngByHist(Map paraMap)throws Exception;
}
