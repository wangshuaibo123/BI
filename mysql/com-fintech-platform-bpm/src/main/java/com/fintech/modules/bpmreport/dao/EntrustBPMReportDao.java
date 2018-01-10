package com.fintech.modules.bpmreport.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fintech.modules.bpmreport.dto.DataBean;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * entrust 服务层查询
 * @author
 * @date 2016-2-12
 */
@MyBatisRepository
public interface EntrustBPMReportDao extends Serializable {

	
	/**
	 * 获取 委托概况相关数据
	 * @param map
	 * @return
	 */
	public List<DataBean> getEntrustStateData(Map<String, Object> map) ;
	
	
	
	
	

}
