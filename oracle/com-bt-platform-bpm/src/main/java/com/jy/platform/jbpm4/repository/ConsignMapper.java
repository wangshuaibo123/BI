package com.jy.platform.jbpm4.repository;

import java.util.List;
import java.util.Map;

import com.jy.platform.core.mybatis.MyBatisRepository;
import com.jy.platform.jbpm4.dto.Consign;
/**
 * 
 * @Description 定义 持久层 接口 
 * @author chen_gng
 * @date 2014年9月18日 上午9:32:48
 * @version V1.0
 */
@MyBatisRepository
public interface ConsignMapper {

    public int findAllConsignCount(Map<String, Object> param);

    /**
     * @title: getConsignByPage
     * @author cxt
     * @description:
     * @date 2014年9月3日 下午1:35:45
     * @param param
     * @return
     * @throws
     */
    public List<Map> getConsignByPage(Map<String, Object> param);
    
    /**
     * @title: findQuartzCronByName
     * @author cxt
     * @description:
     * @date 2014年9月3日 下午1:35:45
     * @param param
     * @return
     * @throws
     */
    public String findQuartzCronByName(Map<String, Object> param);
    
    /**
     * @title: findQuartzJobByName
     * @author cxt
     * @description:
     * @date 2014年9月3日 下午1:35:45
     * @param param
     * @return
     * @throws
     */
    public Map findQuartzJobByName(Map<String, Object> param);
    
    /**
     * @title: addConsign
     * @author cxt
     * @description:
     * @date 2014年9月3日 下午1:35:45
     * @param param
     * @return
     * @throws
     */
    public void addConsign(Map<String, Object> param);
    
    /**
     * @title: delById
     * @author cxt
     * @description:
     * @date 2014年9月16日 下午16:35:45
     * @param param
     * @return
     * @throws
     */
    public void delById(Map<String, Object> param);
    
    /**
     * @title: getById
     * @author byId获取consign
     * @description:
     * @date 2014年9月16日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
	public Consign getById(Map<String, Object> param);
	
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
	public int consignToOther(Map<String, Object> param);
	
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
	public int repeatConsign(Map<String, Object> param);
	
    /**
     * @title: getConsignTask
     * @author byId获取consign
     * @description:
     * @date 2014年9月16日 下午2:00:05
     * @param param
     * @return
     * @throws
     */
    public List<Consign> getConsignTask(Map<String, Object> param);
    /**
     * 通过定时任务 更改失效的任务委托 数据
     * @param param
     */
	public void updateConsignValidateState(Map<String, Object> param);
    


}
