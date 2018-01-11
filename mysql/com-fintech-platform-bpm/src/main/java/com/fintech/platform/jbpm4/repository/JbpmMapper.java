package com.fintech.platform.jbpm4.repository;

import java.util.List;
import java.util.Map;

import com.fintech.platform.core.mybatis.MyBatisRepository;
import com.fintech.platform.jbpm4.dto.JbpmBlobDTO;
import com.fintech.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;
import com.fintech.platform.jbpm4.temporaryJbpm4Info.dto.TemporaryJbpm4InfoDTO;
@MyBatisRepository
public interface JbpmMapper {
	
	/**
	 * 通过流程实例ID proInsId
	 * 获取流程定义信息
	 * @param param
	 * @return
	 */
	public Map<String,Object> queryProDefindInfo(Map<String, Object> param);
	
	public List queryTasksByMySql(Map<String, Object> param);
	public List queryTasksByMySqlByPaging(Map<String, Object> param);

	public List queryCompletedTasksByMySql2Temp(Map<String, Object> params);
	
	public List<Map<String,Object>> queryCompletedTasksByMySql(Map<String, Object> params);
	public List<Map<String,Object>> queryCompletedTasksByMySqlByPaging(Map<String, Object> params);
	
	/**
	 * 查询 个人的 已结任务 信息（办结）
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryPersonalEndTaskInfo(Map<String, Object> params);
	public List<Map<String,Object>> queryPersonalEndTaskInfoByPaging(Map<String, Object> params);

	public List queryActiveProInfoByMySqlByPaging(Map<String, Object> params);

	public List<Map<String,Object>> queryEndPrInfoByMySql(Map<String, Object> params);
	public List<Map<String,Object>> queryEndPrInfoByMySqlByPaging(Map<String, Object> params);

	public Map queryMainProInsIdByHuiQainSubPro(Map<String, Object> params);

	public List queryLastTaskInfoByMainProInsId(Map<String, Object> paramLast);

	public Map queryJbpm4LobInfoByProkey(Map<String,Object> param);

	public String queryMaxDeploymnetId(Map<String, Object> queryMap);

	public void updateJbpm4Lob(JbpmBlobDTO dto);

	public Long addJbpm4FormInfo(Map<String, Object> paramMap);

	public void deleteJbpm4FormInfo(Map<String, Object> paramMap);

	public List queryListOfJbpm4FormInfoPage(Map<String, Object> paramMap);

	public Jbpm4FormInfoDTO queryOneJbpm4FormInfo(Map<String, Object> paramMap);

	public int updateJbpm4FormInfo(Map<String, Object> paramMap);

	public String queryFormInfoIdBySql(Map<String, Object> paramMap);

	public void deleteOldProcessInfoBySql(Map<String, Object> paramMap);

	public Long addTemporaryJbpm4Info(Map<String, Object> paramMap);

	public void deleteTemporaryJbpm4Info(Map<String, Object> paramMap);


	public List<TemporaryJbpm4InfoDTO> queryListOfTemporaryJbpm4InfoPage(
			Map<String, Object> paramMap);

	public List query_some_temporary_jbpm4_info_by_sql(
			Map<String, Object> paramMap);

	public List querySomeTemporaryJbpm4InfoBySql(Map<String, Object> paramMap);

	public TemporaryJbpm4InfoDTO queryOneTemporaryJbpm4_info(
			Map<String, Object> paramMap);

	public int updateTemporaryJbpm4Info(Map<String, Object> paramMap);

	public String queryTempWorkflowIBySql(Map<String, Object> param);

	public int updateProPngOfTempJbpm4Info(JbpmBlobDTO pngDTO);

	public JbpmBlobDTO queryProPngByProNameAndVersion(Map paraMap);

	public void batchUpdateAssignee(Map<String, Object> param);

	public List getUpTaskDealPersonInfo(Map<String, Object> param);
	/**
	 * 获取 工作流与业务表关联表 的业务信息
	 * @param param
	 * @return
	 */
	public Map<String, Object> obtainBizTabInfo(Map<String, Object> param);

	public Map<String, Object> obtainJbpm4FormInfo(Map<String, Object> param);
	public Map<String, Object> obtainJbpm4FormInfoById(Map<String, Object> param);
	
	public Map<String, Object> getFirstTaskByBizTabId(Map<String, Object> param);
	
	/**
	 * 2014-11-3 19:10:19 chj
	 * 更新 待办任务表 的字段
	 * @param param
	 */
	public void updateDataLockByPrimaryKey(Map<String,Object> param);
	
	/**
	 * 2014-11-3 19:10:19 chj
	 * 流程监控 我的代办 批量挂起、批量恢复 更新字段
	 * @param param
	 */
    public void batchUpdateStateByIds(Map<String,Object> param);
    /**
     * 通过 userId 获取 该人的名下的待办任务数量
     * @param paraMap
     * @return
     */
	public Map<String, Object> getTaskCunOfPseron(Map<String, Object> paraMap);
    /**
     * 通过 userId 获取 该人的名下当天的任务数量（包括待办、已办、已结）
     * @param paraMap
     * @return
     */
	public Map<String, Object> getTaskCunOfPseronToday(Map<String, Object> paraMap);
	  /**
     *  2014-11-10 19:20:26 chj 查询 参与者
     * @param mainId
     * @param name
     * @return
     */
	public String queryCurrentPlayer(Map<String,Object> param);
	/**
	 * 查看流程 执行轨迹
	 * @param searchParams
	 * @return
	 */
	public List<Map<String, Object>> viewWorkflowHisLogByPaging(
			Map<String, Object> searchParams);
	
	/**
     * 查看流程 执行轨迹 查询活动历史表 
     * 包含task、custom、decision 类型的活动
     * @param searchParams
     * @return
     */
    public List<Map<String, Object>> viewActHisLogByPaging(
            Map<String, Object> searchParams);
	
	
	/**
	 * 获取 任务节点 在 该流程实例下 执行历史 信息
	 * @param param
	 * @return
	 */
	public List<Map<String, String>> queryExecuteHisLog(Map<String, String> param);
	/**
	 * 通过 流程实例ID 获取 其名下的未执行 的待办任务信息
	 * @param param
	 * @return
	 */
	public List<Map<String, String>> getTaskInfoByProInsId(Map<String, String> param);

	public Map<String, Object> getTaskInfByTaskId(Map<String, String> param);
	/**
	 * 获取 某个节点 下的历史任务 信息
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getHisTaskInf(Map<String, String> param);
	
	/**
     * 获取 某个节点 下的历史任务 信息
     * @param param
     * @return
     */
    public List<Map<String, Object>> getHisTaskInfByBizType(Map<String, String> param);
	
	/**
     * 根据流程实例ID获取 某个节点 下的历史任务 信息
     * @param param
     * @return
     */
    public List<Map<String, Object>> getHisTaskInfByProInstId(Map<String, String> param);
	
	/**
	 * 根据流程实例ID和活动名称获取活动信息
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getActInstByProInstIdAndActName(Map<String, String> param);
	
	public List<Map<String, Object>> getCurTaskInfo(Map<String, String> param);

	public void updateJbpm4BizTabTaskEmergentState(Map<String, Object> param);

	/**
	 * 根据流程实例ID获取父流程
	 * @param param
	 * @return
	 */
	public String getParentProcess(Map<String,Object> param);
	
	public List<Map<String, Object>> getLastHisUserOfActiveByBizType(
			Map<String, String> param);
	/**
	 * 判断员工是否请假
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> checkLeaveByUserId(Map<String, Object> paramMap) throws Exception;
	/***
	 * 查询个人已结的历史任务 by xyz
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryPersonalEndHistTaskInfoByPaging(Map<String, Object> params);
	/**
	 * 查询流程定义相关信息 by xyz
	 * @param param
	 * @return
	 */
	public Map<String,Object> queryProDefindInfoByHistData(Map<String, Object> param);
	/**
	 * 分页查询历史数据 byxyz
	 * @param searchParams
	 * @return
	 */
	public List<Map<String, Object>> viewActHisLogForHistByPaging(Map<String, Object> searchParams);
	/**
	 * 获取图片内容 byxyz
	 * @param paraMap
	 * @return
	 */
	public JbpmBlobDTO queryProPngByProNameAndVersionByHist(Map paraMap);

	public void updateProPngOfTempJbpm4Info1(Map<String, Object> paramMap);

	public void updatejbpm4Task(Map<String,Object> temp);

	public void updatejbpm4HistTask(Map<String,Object> temp);
}
