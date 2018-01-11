package com.jy.modules.bpmreport.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jy.modules.bpmreport.dto.DataBean;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * 流程维度统计分析DAO
 * @author xyz
 *
 */
@MyBatisRepository
public interface DeptBPMReportDao extends Serializable {
	
    /**
     * 积压TOP10
     * @param map
     * @return
     */
	public  List<DataBean> getOverStockMostDataList(Map<String, Object> map);
	/**
	 * 获取 已办最多TOP10的数据
	 * @param map
	 * @return
	 */
	public List<DataBean> getProcessedMostData(Map<String, Object> map);
	/**
	 * 获取发起最多TOP10的数据
	 * @param map
	 * @return
	 */
	public List<DataBean> getStartUpMostData(Map<String, Object> map);
	
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
	 * 获取 委托 最多 TOP10 数据
	 * @param map
	 * @return
	 */
	public List<DataBean> getEntrustMostData(Map<String, Object> map) ;
	/**
	 * 获取乐于助人最多TOP10 数据
	 * @param map
	 * @return
	 */
	public List<DataBean> getSupportMostData(Map<String, Object> map) ;
	/**
	 * 获取委托状况 数据
	 * @param map
	 * @return
	 */
	public List<DataBean> getEntrustStateData(Map<String, Object> map) ;
}
