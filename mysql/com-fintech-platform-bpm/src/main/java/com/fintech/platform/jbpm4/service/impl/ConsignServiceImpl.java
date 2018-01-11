package com.fintech.platform.jbpm4.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.jbpm4.dto.Consign;
import com.fintech.platform.jbpm4.repository.ConsignMapper;
import com.fintech.platform.jbpm4.service.ConsignService;

/**
 * 
 * @Description 定义 实现 个人任务委托 业务接口
 * @author
 * @date 2014年9月18日 上午9:31:04
 * @version V1.0
 */
@Service("com.fintech.platform.jbpm4.service.impl.ConsignServiceImpl")
public class ConsignServiceImpl implements ConsignService { 
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ConsignServiceImpl.class);
	private static int POOL_SIZE = 2;
	private static int cpuNums = Runtime.getRuntime().availableProcessors();
	private static ExecutorService executor = Executors.newFixedThreadPool(cpuNums * POOL_SIZE );
	//private static ThreadPoolExecutor executor = ThreadPool.getThreadPool(15,30, 3000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2000));
    @Autowired
    private ConsignMapper consignMapper;
    //通过 userId 获取 用户 详细 信息
  	@Autowired
  	private OrgAPI orgApi;
    /*
     * (non-Javadoc)
     * @see ${iServicePackageName}.${iServiceClassName}#findAllConsignCount(java.util.Map)
     */
    public List<Map> getConsignByPage(Map<String, Object> param) {
        List<Map> resultList = consignMapper.getConsignByPage(param);
        if(resultList != null && resultList.size() >0)
        	resultList = this.makeupUserInfo(resultList);
        return resultList;

    }
    /**
     * 通过 组装人员ID 组装人员基本信息
     * @param dataList
     * @return
     */
    private List<Map> makeupUserInfo(List<Map> dataList) {
    	 if(dataList == null || dataList.size() ==0) return dataList;
    	 try {
				//线程池初始化
				//得到线程池中线程队列
				int count = dataList.size() ;
				final CountDownLatch countDownLatch = new CountDownLatch(count);
				for(int i = 0;i < count;i ++){
					final Map temp = dataList.get(i);
					
					String fromUserId = (String) temp.get("FROM_USER_ID");
					String toUserId = (String) temp.get("TO_USER_ID");
					String createdBy = (String) temp.get("CREATED_BY");
					
					
					final Map<String,Object> tempParam = new HashMap<String,Object>();
					tempParam.put("fromUserId", fromUserId);
					tempParam.put("toUserId", toUserId);
					tempParam.put("createdBy", createdBy);
					
					executor.execute(new Runnable(){
						public void run() {
							try {
								String rfromUserId  = (String) tempParam.get("fromUserId");
								String rtoUserId = (String) tempParam.get("toUserId");
								String rcreatedBy = (String)tempParam.get("createdBy");
								Map<String,Object> resultMap = new HashMap<String,Object>();
								if(StringUtils.isNotEmpty(rfromUserId)){//通过 用户ID接口 获取 用户的其他信息
									UserInfo user = orgApi.getUserInfoDetail(rfromUserId);
									if(user != null ) resultMap.put("FROM_USER_NAME", user.getUserName());
								}
								if(StringUtils.isNotEmpty(rtoUserId)){
									UserInfo user = orgApi.getUserInfoDetail(rtoUserId);
									if(user != null ) resultMap.put("TO_USER_NAME", user.getUserName());
								}
								if(StringUtils.isNotEmpty(rcreatedBy)){
									UserInfo user = orgApi.getUserInfoDetail(rcreatedBy);
									if(user != null ) resultMap.put("CREATED_BY_NAME", user.getUserName());
								}
								
								//回写数据信息
								if(resultMap != null) temp.putAll(resultMap);
							} catch (Exception e) {
								logger.error("makeupUserInfo error"+e.getMessage(),e);
							}
							
							countDownLatch.countDown();
						}
					});
				}
				//等待子线程都执行完了再执行主线程剩下的动作
				countDownLatch.await();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		return dataList;
	}

	/*
     * (non-Javadoc)
     * @see com.fintech.demo.dao.service.ConsignService#findAllConsignCount(java.util.Map)
     */
    public int findAllConsignCount(Map<String, Object> param) {

        return consignMapper.findAllConsignCount(param);

    }

    /*
     * (non-Javadoc)
     * @see com.fintech.demo.dao.service.ConsignService#addConsign(java.util.Map)
     */
    @Transactional(rollbackFor=Exception.class)
    public void addConsign(Map<String, Object> param) throws Exception{

        consignMapper.addConsign(param);

    }


    /*
     * (non-Javadoc)
     * @see com.fintech.demo.dao.service.ConsignService#delById(java.util.Map)
     */
    public void delById(Map<String, Object> param) {

        consignMapper.delById(param);

    }

    /*
     * (non-Javadoc)
     * @see com.fintech.demo.dao.service.ConsignService#getById(java.util.Map)
     */
    public Consign getById(Map<String, Object> param) {

        return consignMapper.getById(param);
    }

    /*
     * (non-Javadoc)
     * @see com.fintech.demo.dao.service.ConsignService#consignToOther(java.util.Map)
     */
    public int consignToOther(Map<String, Object> param) {

        return consignMapper.consignToOther(param);

    }

    /*
     * (non-Javadoc)
     * @see com.fintech.demo.dao.service.ConsignService#repeatConsign(java.util.Map)
     */
    public int repeatConsign(Map<String, Object> param) {

        return consignMapper.repeatConsign(param);

    }
    
	public List<Consign> getConsignTask(Map<String, Object> param){
		
		return consignMapper.getConsignTask(param);
	}
	@Transactional(rollbackFor=Exception.class)
	public void updateConsignValidateState(Map<String, Object> param) throws Exception{
		consignMapper.updateConsignValidateState(param);
	}
	


}
