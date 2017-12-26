package com.fintech.modules.logmonitor.sysapplevelsetup.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.logmonitor.sysapplevelsetup.dto.SysAppLevelSetupDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysAppLevelSetupDao
 * @description: 定义  错误级别设定表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  lei
 */
@MyBatisRepository
public interface SysAppLevelSetupDao {
    
    /**
     * @author lei
     * @description: 分页查询错误级别设定表
     * @date 2015-06-12 16:33:50
     * @param searchParams
     * @return
     */
    public List<SysAppLevelSetupDTO> searchSysAppLevelSetupByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author lei
     * @description:查询对象错误级别设定表
     * @date 2015-06-12 16:33:50
     * @param searchParams
     * @return
     */
    public List<SysAppLevelSetupDTO> searchSysAppLevelSetup(Map<String, Object> searchParams);

    /**
     * @author lei
     * @description:查询对象错误级别设定表
     * @date 2015-06-12 16:33:50
     * @param id
     * @return
     */
    public SysAppLevelSetupDTO findSysAppLevelSetupByPrimaryKey(String id);
    
    /**
     * @author lei
     * @description: 新增对象错误级别设定表
     * @date 2015-06-12 16:33:50
     * @param paramMap
     * @return
     */
    public int insertSysAppLevelSetup(Map<String, Object> paramMap);
    
    /**
     * @author lei
     * @description: 更新对象错误级别设定表
     * @date 2015-06-12 16:33:50
     * @param paramMap
     */
    public void updateSysAppLevelSetup(Map<String, Object> paramMap);
     
    /**
     * @author lei
     * @description: 按主键删除错误级别设定表
     * @date 2015-06-12 16:33:50
     * @param ids
     * @return
     */ 
    public void deleteSysAppLevelSetupByPrimaryKey(Map<String, Object> paramMap);
    
    /**
     * 查询最大的频率
     * @param searchParams
     * @return
     */
    public Integer searchMaxRateOfUnit(Map<String, Object> searchParams);
    
    
}
