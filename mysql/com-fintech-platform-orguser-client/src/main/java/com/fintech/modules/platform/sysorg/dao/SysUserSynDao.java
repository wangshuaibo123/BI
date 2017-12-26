package com.fintech.modules.platform.sysorg.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysorg.dto.SysUserSynDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysUserSynDao
 * @description: 定义  系统用户表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysUserSynDao {
    
    /**
     * @author
     * @description: 分页查询系统用户表
     * @date 2015-01-17 17:09:03
     * @param searchParams
     * @return
     */
    public List<SysUserSynDTO> searchSysUserSynByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象系统用户表
     * @date 2015-01-17 17:09:03
     * @param searchParams
     * @return
     */
    public List<SysUserSynDTO> searchSysUserSyn(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象系统用户表
     * @date 2015-01-17 17:09:03
     * @param id
     * @return
     */
    public SysUserSynDTO findSysUserSynByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象系统用户表
     * @date 2015-01-17 17:09:03
     * @param paramMap
     * @return
     */
    public int insertSysUserSyn(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象系统用户表
     * @date 2015-01-17 17:09:03
     * @param paramMap
     */
    public void updateSysUserSyn(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除系统用户表
     * @date 2015-01-17 17:09:03
     * @param ids
     * @return
     */ 
    public void deleteSysUserSynByPrimaryKey(Map<String, Object> paramMap);
    /**
     * @author
     * @description: 查找最后同步时间记录
     * @date 2015-01-19 11:48:48
     * @param paramMap 默认为空
     * @return
     */ 
    public SysUserSynDTO searchSysUserSynByMaxCreateDate(Map<String, Object> paramMap);
    
}
