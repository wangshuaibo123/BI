package com.fintech.modules.platform.sysvariablejob.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysvariablejob.dto.SysVariableJobDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysVariableJobDao
 * @description: 定义  异步任务参数表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  DELL
 */
@MyBatisRepository
public interface SysVariableJobDao {
    
    /**
     * @author DELL
     * @description: 分页查询异步任务参数表
     * @date 2016-09-19 13:51:01
     * @param searchParams
     * @return
     */
    public List<SysVariableJobDTO> searchSysVariableJobByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author DELL
     * @description:查询对象异步任务参数表
     * @date 2016-09-19 13:51:01
     * @param searchParams
     * @return
     */
    public List<SysVariableJobDTO> searchSysVariableJob(Map<String,Object> searchParams);

    /**
     * @author DELL
     * @description:查询对象异步任务参数表
     * @date 2016-09-19 13:51:01
     * @param id
     * @return
     */
    public SysVariableJobDTO findSysVariableJobByPrimaryKey(String id);
    
    /**
     * @author DELL
     * @description: 新增对象异步任务参数表
     * @date 2016-09-19 13:51:01
     * @param paramMap
     * @return
     */
    public int insertSysVariableJob(Map<String, Object> paramMap);
    
    /**
     * @author DELL
     * @description: 更新对象异步任务参数表
     * @date 2016-09-19 13:51:01
     * @param paramMap
     */
    public void updateSysVariableJob(Map<String, Object> paramMap);
     
    /**
     * @author DELL
     * @description: 按主键删除异步任务参数表
     * @date 2016-09-19 13:51:01
     * @param ids
     * @return
     */ 
    public void deleteSysVariableJobByPrimaryKey(Map<String, Object> paramMap);
    
    
}
