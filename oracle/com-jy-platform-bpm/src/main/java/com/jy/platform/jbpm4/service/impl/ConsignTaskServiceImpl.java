package com.jy.platform.jbpm4.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.platform.jbpm4.dto.ConsignTask;
import com.jy.platform.jbpm4.repository.ConsignTaskMapper;
import com.jy.platform.jbpm4.service.ConsignTaskService;

 /**
  * 
  * @Description 定义 实现 个人任务委托 业务接口
  * @author cxt
  * @date 2014年9月18日 上午9:31:04
  * @version V1.0
  */
@Service("com.jy.platform.jbpm4.service.impl.ConsignTaskServiceImpl")
public class ConsignTaskServiceImpl implements ConsignTaskService { 
	private static final long serialVersionUID = 5289628812643732089L;
	private static final Logger logger = LoggerFactory.getLogger(ConsignTaskServiceImpl.class);
    @Autowired
    private ConsignTaskMapper consignTaskMapper;
	
/*
 * (non-Javadoc)
 * @see com.jy.demo.dao.service.ConsignService#getConsignByPage(java.util.Map)
 */
    public List<Map> getConsignTaskByPage(Map<String, Object> param) {
    	
        Object systemIdObj = param.get("systemId");

        return consignTaskMapper.getConsignTaskByPage(param);


    }

/*
 * (non-Javadoc)
 * @see com.jy.demo.dao.service.ConsignService#findAllConsignCount(java.util.Map)
 */
    public int findAllConsignTaskCount(Map<String, Object> param) {


        return consignTaskMapper.findAllConsignTaskCount(param);
        
    }
    
/*
 * (non-Javadoc)
 * @see com.jy.demo.dao.service.ConsignService#addConsign(java.util.Map)
 */
    public void addConsignTask(Map<String, Object> param) {


    	consignTaskMapper.addConsignTask(param);
        
    }
    
/*
 * (non-Javadoc)
 * @see com.jy.demo.dao.service.ConsignService#updateConsign(java.util.Map)
 */
    public void updateConsignTask(Map<String, Object> param) {


    	consignTaskMapper.updateConsignTask(param);
        
    }
    
/*
 * (non-Javadoc)
 * @see com.jy.demo.dao.service.ConsignService#delById(java.util.Map)
 */
    public void delById(Map<String, Object> param) {


    	consignTaskMapper.delById(param);
        
    }

/*
 * (non-Javadoc)
 * @see com.jy.demo.dao.service.ConsignService#getById(java.util.Map)
 */
	public ConsignTask getById(Map<String, Object> param){
		
		return consignTaskMapper.getById(param);
	}

	  /**
     * @title: updateValidateState
     * @author 更新数据有效性
     * @description:
     * @date 2014年9月22日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	public void updateValidateState(Map<String, Object> param){
		consignTaskMapper.updateValidateState(param);
	}

}
