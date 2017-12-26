package com.fintech.modules.platform.sysasynjob.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysasynjob.dto.SysAsynJobDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysAsynJobDao
 * @description: 定义  异步接口任务表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  DELL
 */
@MyBatisRepository
public interface SysAsynJobDao {
    
    /**
     * @author DELL
     * @description: 分页查询异步接口任务表
     * @date 2016-09-12 14:55:27
     * @param searchParams
     * @return
     */
    public List<SysAsynJobDTO> searchSysAsynJobByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author DELL
     * @description:查询对象异步接口任务表
     * @date 2016-09-12 14:55:27
     * @param searchParams
     * @return
     */
    public List<SysAsynJobDTO> searchSysAsynJob(Map<String,Object> searchParams);

    /**
     * @author DELL
     * @description:查询对象异步接口任务表
     * @date 2016-09-12 14:55:27
     * @param id
     * @return
     */
    public SysAsynJobDTO findSysAsynJobByPrimaryKey(String id);
    
    /**
     * @author DELL
     * @description: 新增对象异步接口任务表
     * @date 2016-09-12 14:55:27
     * @param paramMap
     * @return
     */
    public int insertSysAsynJob(Map<String, Object> paramMap);
    
    /**
     * @author DELL
     * @description: 异步接口调用成功后 更新数据
     * @date 2016-09-12 14:55:27
     * @param paramMap
     */
    public void updateSysAsynJob(Map<String, Object> paramMap);
     
    /**
     * @author DELL
     * @description: 按主键删除/暂停 异步接口任务表
     * @date 2016-09-12 14:55:27
     * @param ids
     * @return
     */ 
    public void deleteSysAsynJobByPrimaryKey(Map<String, Object> paramMap);
    /**
     * 更改任务开发时间
     * @param paramMap
     */
    public void updateJobStartTime(Map<String, Object> paramMap);
    /**
     * 任务失败后，更改任务错误信息描述
     * @param paramMap
     */
    public void updateJobErrorMsg(Map<String, Object> paramMap);
    /**
     * 恢复、重置异步任务接口，允许任务重新执行
     * @param paramMap
     */
    public void updateRecoverySysBizJob(Map<String, Object> paramMap);
    
    
    
    
}
