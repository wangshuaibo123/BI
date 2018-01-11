package com.fintech.platform.jbpm4.repository;

import java.util.List;
import java.util.Map;

import com.fintech.platform.core.mybatis.MyBatisRepository;
import com.fintech.platform.jbpm4.dto.ConsignTask;
/**
 * 
 * @Description 定义 持久层 接口 
 * @author
 * @date 2014年9月18日 上午9:32:48
 * @version V1.0
 */
@MyBatisRepository
public interface ConsignTaskMapper {

    public int findAllConsignTaskCount(Map<String, Object> param);

    /**
     * @title: getConsignByPage
     * @author
     * @description:
     * @date 2014年9月3日 下午1:35:45
     * @param param
     * @return
     * @throws
     */
    public List<Map> getConsignTaskByPage(Map<String, Object> param);
    
    
    
    /**
     * @title: addConsignTask
     * @author
     * @description:
     * @date 2014年9月3日 下午1:35:45
     * @param param
     * @return
     * @throws
     */
    public void addConsignTask(Map<String, Object> param);
    
    /**
     * @title: updateConsignTask
     * @author
     * @description:
     * @date 2014年9月3日 下午1:35:45
     * @param param
     * @return
     * @throws
     */
    public void updateConsignTask(Map<String, Object> param);
    
    /**
     * @title: delById
     * @author
     * @description:
     * @date 2014年9月16日 下午16:35:45
     * @param param
     * @return
     * @throws
     */
    public void delById(Map<String, Object> param);
    
    /**
     * @title: getById
     * @author获取consign
     * @description:
     * @date 2014年9月16日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	public ConsignTask getById(Map<String, Object> param);

	  /**
     * @title: updateValidateState
     * @author 更新数据有效性
     * @description:
     * @date 2014年9月22日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	public void updateValidateState(Map<String, Object> param);

	public List<Map<String, Object>> queryConsignInfo(Map<String, Object> param);

}
