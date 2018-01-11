package com.fintech.platform.jbpm4.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.platform.jbpm4.dto.ConsignTask;
import com.fintech.platform.jbpm4.repository.ConsignTaskMapper;
import com.fintech.platform.jbpm4.service.ConsignTaskService;

 /**
  * 
  * @Description 定义 实现 个人任务委托 业务接口
  * @author
  * @date 2014年9月18日 上午9:31:04
  * @version V1.0
  */
@Service("com.fintech.platform.jbpm4.service.impl.ConsignTaskServiceImpl")
public class ConsignTaskServiceImpl implements ConsignTaskService { 
	private static final long serialVersionUID = 5289628812643732089L;
	private static final Logger logger = LoggerFactory.getLogger(ConsignTaskServiceImpl.class);
    @Autowired
    private ConsignTaskMapper consignTaskMapper;
	
/*
 * (non-Javadoc)
 * @see com.fintech.demo.dao.service.ConsignService#getConsignByPage(java.util.Map)
 */
    public List<Map> getConsignTaskByPage(Map<String, Object> param) {
    	
        Object systemIdObj = param.get("systemId");

        return consignTaskMapper.getConsignTaskByPage(param);


    }

/*
 * (non-Javadoc)
 * @see com.fintech.demo.dao.service.ConsignService#findAllConsignCount(java.util.Map)
 */
    public int findAllConsignTaskCount(Map<String, Object> param) {


        return consignTaskMapper.findAllConsignTaskCount(param);
        
    }
    
/*
 * (non-Javadoc)
 * @see com.fintech.demo.dao.service.ConsignService#addConsign(java.util.Map)
 */
    public void addConsignTask(Map<String, Object> param) {


    	consignTaskMapper.addConsignTask(param);
        
    }
    
/*
 * (non-Javadoc)
 * @see com.fintech.demo.dao.service.ConsignService#updateConsign(java.util.Map)
 */
    public void updateConsignTask(Map<String, Object> param) {


    	consignTaskMapper.updateConsignTask(param);
        
    }
    
/*
 * (non-Javadoc)
 * @see com.fintech.demo.dao.service.ConsignService#delById(java.util.Map)
 */
    public void delById(Map<String, Object> param) {


    	consignTaskMapper.delById(param);
        
    }

/*
 * (non-Javadoc)
 * @see com.fintech.demo.dao.service.ConsignService#getById(java.util.Map)
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

	@Override
	public List<Map<String, Object>> makeupConsignInfo(List<Map<String, Object>> resultList) {
		try {
			for(int i=0;i < resultList.size();i ++){
				Map<String, Object> map = resultList.get(i);
				BigDecimal taskId = (BigDecimal)map.get("TASK_ID");
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("taskId", taskId);
				List<Map<String,Object>> data = consignTaskMapper.queryConsignInfo(param);
				if(data != null && data.size() >0){
					StringBuffer sb = new StringBuffer();
					for(Map<String,Object> temp:data){
						String fromUserName = (String) temp.get("FROM_USER_NAME");
						String fromUserId = (String) temp.get("FROM_USER_ID");
						String toUserName = (String) temp.get("TO_USER_NAME");
						String toUserId = (String) temp.get("TO_USER_ID");
						if(StringUtils.isEmpty(fromUserName)){
							sb.append("系统");
						}else{
							sb.append(fromUserName);
						}
						
						
						if(StringUtils.isEmpty(toUserName)){
							sb.append("->系统");
						}else{
							sb.append("->").append(toUserName);
						}
						sb.append("|");
					}
					map.put("CONSIGN_DESC", sb.toString());
				}
			}
		} catch (Exception e) {
			logger.error("======="+e.getMessage(),e);
		}
		
		return resultList;
	}

}
