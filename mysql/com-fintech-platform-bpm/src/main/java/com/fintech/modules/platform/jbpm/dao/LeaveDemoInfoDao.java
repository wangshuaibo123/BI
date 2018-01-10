package com.fintech.modules.platform.jbpm.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.jbpm.dto.LeaveDemoInfoDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: LeaveDemoInfoDao
 * @description: 定义  申请请假表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface LeaveDemoInfoDao {
    
    /**
     * @author
     * @description: 分页查询申请请假表
     * @date 2014-10-30 17:07:12
     * @param searchParams
     * @return
     */
    public List<LeaveDemoInfoDTO> searchLeaveDemoInfoByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象申请请假表
     * @date 2014-10-30 17:07:12
     * @param searchParams
     * @return
     */
    public List<LeaveDemoInfoDTO> searchLeaveDemoInfo(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象申请请假表
     * @date 2014-10-30 17:07:12
     * @param id
     * @return
     */
    public LeaveDemoInfoDTO findLeaveDemoInfoByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象申请请假表
     * @date 2014-10-30 17:07:12
     * @param paramMap
     * @return
     */
    public int insertLeaveDemoInfo(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象申请请假表
     * @date 2014-10-30 17:07:12
     * @param paramMap
     */
    public void updateLeaveDemoInfo(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新请假表状态
     * @date 2014-10-30 17:07:12
     * @param paramMap
     */
    public void updateLeaveState(Map<String, Object> paramMap);
    
     
    /**
     * @author
     * @description: 按主键删除申请请假表
     * @date 2014-10-30 17:07:12
     * @param ids
     * @return
     */ 
    public void deleteLeaveDemoInfoByPrimaryKey(Map<String, Object> paramMap);
    
    
}
