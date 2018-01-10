package com.fintech.platform.jbpm4.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fintech.platform.jbpm4.dto.JYPartnerDTO;

/**
 * 
 * @description:定义 选择参与者 接口
 * @author
 * @date:2014年11月6日上午10:39:19
 */
public interface JYSelectPartnerService extends Serializable{
	/**
	 * 系统选择 参与者
	 * @param bizInfId 业务表主键ID
	 * @param bizTabName 业务表名称
	 * @param bizTabId jbpm4_biz_tab 表主键ID
	 * @param proInsId 主流程实例ID
	 * @param curActName 当前活动的流程节点名称
	 * @param variables 流程实例业务变量 信息
	 * @return
	 */
	public JYPartnerDTO selectPartnerBySysRule(String bizInfId,String bizTabName,String bizTabId,String proInsId,String curActName,Map<String,Object> variables) throws Exception;
	/**
	 * 按规则引擎 选择 参与者
	 * @param bizInfId 业务表主键ID
	 * @param bizTabName 业务表名称
	 * @param bizTabId jbpm4_biz_tab 表主键ID
	 * @param proInsId 主流程实例ID
	 * @param curActName 当前活动的流程节点名称
	 * @param variables 流程实例业务变量 信息
	 * @return
	 */
	public JYPartnerDTO selectPartnerByDroolsRule(String bizInfId,String bizTabName,String bizTabId,String proInsId,String curActName,Map<String,Object> variables) throws Exception;
	/**
	 * 人工手动 选择 参与者
	 * @param bizInfId 业务表主键ID
	 * @param bizTabName 业务表名称
	 * @param bizTabId jbpm4_biz_tab 表主键ID
	 * @param proInsId 主流程实例ID
	 * @param curActName 当前活动的流程节点名称
	 * @param variables 流程实例业务变量 信息
	 * @return
	 */
	public List<JYPartnerDTO> selectPartnerByOperator(String bizInfId,String bizTabName,String bizTabId,String proInsId,String curActName,Map<String,Object> variables) throws Exception;
	
	
}
