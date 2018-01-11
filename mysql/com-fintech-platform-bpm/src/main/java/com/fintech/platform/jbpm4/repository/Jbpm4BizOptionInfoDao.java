package com.fintech.platform.jbpm4.repository;

import java.util.List;
import java.util.Map;

import com.fintech.platform.core.mybatis.MyBatisRepository;
import com.fintech.platform.jbpm4.dto.Jbpm4BizOptionInfoDTO;
/**
 * @classname: Jbpm4BizOptionInfoDao
 * @description: 定义  业务流程节点意见表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface Jbpm4BizOptionInfoDao {
    
    /**
     * @author
     * @description: 分页查询业务流程节点意见表
     * @date 2014-10-29 14:37:17
     * @param searchParams
     * @return
     */
    public List<Jbpm4BizOptionInfoDTO> searchJbpm4BizOptionInfoByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象业务流程节点意见表
     * @date 2014-10-29 14:37:17
     * @param searchParams
     * @return
     */
    public List<Jbpm4BizOptionInfoDTO> searchJbpm4BizOptionInfo(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象业务流程节点意见表
     * @date 2014-10-29 14:37:17
     * @param id
     * @return
     */
    public Jbpm4BizOptionInfoDTO findJbpm4BizOptionInfoByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象业务流程节点意见表
     * @date 2014-10-29 14:37:17
     * @param paramMap
     * @return
     */
    public int insertJbpm4BizOptionInfo(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象业务流程节点意见表
     * @date 2014-10-29 14:37:17
     * @param paramMap
     */
    public void updateJbpm4BizOptionInfo(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除业务流程节点意见表
     * @date 2014-10-29 14:37:17
     * @param ids
     * @return
     */ 
    public void deleteJbpm4BizOptionInfoByPrimaryKey(Map<String, Object> paramMap);
    /**
     * 删除用户在同一流程同一节点下的多次意见备注信息
     * @param param
     */
	public void deleteOldSomeBizOptionInf(Map<String, String> param);

	public List<Jbpm4BizOptionInfoDTO> searchJbpm4BizOptionInfoByPage(Map<String, Object> searchParams);
    
    
}
