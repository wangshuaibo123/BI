package com.jy.modules.logmonitor.sysapplogpriv.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.logmonitor.sysapplogpriv.dto.SysAppLogPrivDTO;

import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysAppLogPrivDao
 * @description: 定义  日志访问权限表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  sunli
 */
@MyBatisRepository
public interface SysAppLogPrivDao {
    
    /**
     * @author sunli
     * @description: 分页查询日志访问权限表
     * @date 2016-05-30 11:30:54
     * @param searchParams
     * @return
     */
    public List<SysAppLogPrivDTO> searchSysAppLogPrivByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author sunli
     * @description:查询对象日志访问权限表
     * @date 2016-05-30 11:30:54
     * @param searchParams
     * @return
     */
    public List<SysAppLogPrivDTO> searchSysAppLogPriv(Map<String,Object> searchParams);

    /**
     * @author sunli
     * @description:查询对象日志访问权限表
     * @date 2016-05-30 11:30:54
     * @param id
     * @return
     */
    public SysAppLogPrivDTO findSysAppLogPrivByPrimaryKey(String id);
    
    /**
     * @author sunli
     * @description: 新增对象日志访问权限表
     * @date 2016-05-30 11:30:54
     * @param paramMap
     * @return
     */
    public int insertSysAppLogPriv(Map<String, Object> paramMap);
    
    /**
     * @author sunli
     * @description: 更新对象日志访问权限表
     * @date 2016-05-30 11:30:54
     * @param paramMap
     */
    public void updateSysAppLogPriv(Map<String, Object> paramMap);
     
    /**
     * @author sunli
     * @description: 按主键删除日志访问权限表 - 逻辑删除
     * @date 2016-05-30 11:30:54
     * @param ids
     * @return
     */ 
    public void deleteSysAppLogPrivByPrimaryKey(Map<String, Object> paramMap);
    
    /**
     * 按主键删除日志访问权限表 - 物理删除
     * @param paramMap
     */
    public void deleteSysAppLogPrivByID(Map<String, Object> paramMap);
    
    
    /**
     * 获取用户权限
     * @param paramMap
     * @return
     */
    public SysAppLogPrivDTO getUserAppPriv(Map<String, Object> paramMap);
}
