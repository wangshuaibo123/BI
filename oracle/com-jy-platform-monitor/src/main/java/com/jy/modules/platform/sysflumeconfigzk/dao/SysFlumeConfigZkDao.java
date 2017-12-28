package com.jy.modules.platform.sysflumeconfigzk.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.sysflumeconfigzk.dto.SysFlumeConfigZkDTO;

import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysFlumeConfigZkDao
 * @description: 定义  Flume配置表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 */
@MyBatisRepository
public interface SysFlumeConfigZkDao {
    
    /**
     * @description: 分页查询Flume配置表
     * @param searchParams
     * @return
     */
    public List<SysFlumeConfigZkDTO> searchSysFlumeConfigZkByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @description:查询对象Flume配置表
     * @param searchParams
     * @return
     */
    public List<SysFlumeConfigZkDTO> searchSysFlumeConfigZk(Map<String,Object> searchParams);

    /**
     * @description:查询对象Flume配置表
     * @param id
     * @return
     */
    public SysFlumeConfigZkDTO findSysFlumeConfigZkByPrimaryKey(String id);
    
    /**
     * @description: 新增对象Flume配置表
     * @param paramMap
     * @return
     */
    public int insertSysFlumeConfigZk(Map<String, Object> paramMap);
    
    /**
     * @description: 更新对象Flume配置表
     * @param paramMap
     */
    public void updateSysFlumeConfigZk(Map<String, Object> paramMap);
     
    /**
     * @description: 按主键删除Flume配置表
     * @param ids
     * @return
     */ 
    public void deleteSysFlumeConfigZkByPrimaryKey(Map<String, Object> paramMap);
    
    
    /**
     * 物理删除
     * @param paramMap
     */
    public void deleteSysFlumeConfigZkByID(Map<String, Object> paramMap);
}
