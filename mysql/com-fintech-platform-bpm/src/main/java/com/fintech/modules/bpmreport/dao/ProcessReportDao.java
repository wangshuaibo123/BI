package com.fintech.modules.bpmreport.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fintech.modules.bpmreport.dto.DataBean;
import com.fintech.modules.bpmreport.dto.SysDictDetailDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * 流程维度统计分析DAO
 * @author
 *
 */
@MyBatisRepository
public interface ProcessReportDao extends Serializable {
	/**
	 * 用户所用户的角色列表
	 * @param userId
	 * @return
	 */
	public List<String> getRoleCodListByUserId(Long userId);
    /**
     * 积压TOP10
     * @param map
     * @return
     */
	public  List<DataBean> getOverStockMoreDataList(Map<String, Object> map);
	/**
	 * 获取 已办最多TOP10的数据
	 * @param map
	 * @return
	 */
	public List<DataBean> getProcessedMoreData(Map<String, Object> map);
	/**
	 * 获取发起最多TOP10的数据
	 * @param map
	 * @return
	 */
	public List<DataBean> getStartUpMoreData(Map<String, Object> map);
	
	/**
	 * 平均速率最高TOP10
	 * @param map
	 * @return
	 */
	public List<DataBean> getAvgEfficientHighestData(Map<String, Object> map) ;
	/**
	 * 平均速率最低TOP10
	 * @param map
	 * @return
	 */
	public List<DataBean> getAvgEfficientLowestData(Map<String, Object> map);
	/**
	 * 平均人工速率最高TOP10
	 * @param map
	 * @return
	 */
	public List<DataBean> getAvgHumanEfficientHighestData(Map<String, Object> map) ;
	/**
	 * 平均人工速率最低TOP10
	 * @param map
	 * @return
	 */
	public List<DataBean> getAvgHumanEfficientLowestData(Map<String, Object> map) ;
	/**
	 * 
	 * @param processDefDictCode
	 * @return
	 */
	public List<SysDictDetailDTO> getSysDictDetailList(String processDefDictCode);

}
