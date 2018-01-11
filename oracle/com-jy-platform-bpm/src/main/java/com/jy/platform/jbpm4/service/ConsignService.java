package com.jy.platform.jbpm4.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jy.platform.jbpm4.dto.Consign;
/**
 * 定义 个人任务委托 业务接口
 * @Description
 * @author chen_gng
 * @date 2014年9月18日 上午9:29:20
 * @version V1.0
 */
public interface ConsignService extends Serializable{
    /**
     * @title: getConsignByPage
     * @author 分页查询用户
     * @description:
     * @date 2014年9月3日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	List<Map> getConsignByPage(Map<String, Object> param);
    /**
     * @title: findAllConsignCount
     * @author cxt
     * @description:
     * @date 2014年9月3日 下午2:00:24
     * @param param
     * @return
     * @throws
     */
	int findAllConsignCount(Map<String, Object> param);
	
    /**
     * @throws Exception 
     * @title: addConsign
     * @author 新增
     * @description:
     * @date 2014年9月15日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	void addConsign(Map<String, Object> param) throws Exception;
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
     * @author byId获取consign
     * @description:
     * @date 2014年9月16日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	Consign getById(Map<String, Object> param);
	
    /**
     * 校验工作委托（办理人是否有委托）
     * @title: consignToOther
     * @author cxt
     * @description:
     * @date 2014年9月17日 下午2:00:24
     * @param param
     * @return
     * @throws
     */
	int consignToOther(Map<String, Object> param);
	
    /**
     * 校验工作委托（委托人是否已委托）
     * @title: repeatConsign
     * @author cxt
     * @description:
     * @date 2014年9月17日 下午2:00:24
     * @param param
     * @return
     * @throws
     */
	int repeatConsign(Map<String, Object> param);
	
    /**
     * @title: getConsignTask
     * @author 获取consign
     * @description:
     * @date 2014年9月16日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	List<Consign> getConsignTask(Map<String, Object> param);
	/**
	 * 更改 任务委托 失效的数据信息
	 * @param param
	 * @throws Exception 
	 */
	void updateConsignValidateState(Map<String, Object> param) throws Exception;
	

}
