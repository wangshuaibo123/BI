package com.fintech.platform.jbpm4.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fintech.platform.jbpm4.dto.ConsignTask;
/**
 * 定义 个人任务委托 业务接口
 * @Description
 * @author
 * @date 2014年9月18日 上午9:29:20
 * @version V1.0
 */
public interface ConsignTaskService extends Serializable{
    /**
     * @title: getConsignByPage
     * @author 分页查询用户
     * @description:
     * @date 2014年9月3日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	List<Map> getConsignTaskByPage(Map<String, Object> param);
    /**
     * @title: findAllConsignCount
     * @author
     * @description:
     * @date 2014年9月3日 下午2:00:24
     * @param param
     * @return
     * @throws
     */
	int findAllConsignTaskCount(Map<String, Object> param);
	
    /**
     * @title: addConsign
     * @author 新增
     * @description:
     * @date 2014年9月15日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	void addConsignTask(Map<String, Object> param);
	
    /**
     * @title: updateConsign
     * @author 分页查询用户
     * @description:
     * @date 2014年9月15日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	void updateConsignTask(Map<String, Object> param);
	
    /**
     * @title: delConsign
     * @author 逻辑删除
     * @description:
     * @date 2014年9月15日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	void delById(Map<String, Object> param);
	
    /**
     * @title: getById
     * @author获取consign
     * @description:
     * @date 2014年9月16日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	ConsignTask getById(Map<String, Object> param);
	
	  /**
     * @title: updateValidateState
     * @author 更新数据有效性
     * @description:
     * @date 2014年9月22日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	void updateValidateState(Map<String, Object> param);
	List<Map<String, Object>> makeupConsignInfo(List<Map<String, Object>> resultList);
	

}
