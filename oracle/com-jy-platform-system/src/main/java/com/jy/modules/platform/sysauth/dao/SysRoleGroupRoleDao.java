package com.jy.modules.platform.sysauth.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.sysauth.dto.SysRoleGroupRoleDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysRoleGroupRoleDao
 * @description: 定义  角色组角色中间表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  yuchengyang-pc
 */
@MyBatisRepository
public interface SysRoleGroupRoleDao {
    
    /**
     * @author yuchengyang-pc
     * @description: 分页查询角色组角色中间表
     * @date 2014-11-28 17:38:38
     * @param searchParams
     * @return
     */
    public List<SysRoleGroupRoleDTO> searchSysRoleGroupRoleByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author yuchengyang-pc
     * @description:查询对象角色组角色中间表
     * @date 2014-11-28 17:38:38
     * @param searchParams
     * @return
     */
    public List<SysRoleGroupRoleDTO> searchSysRoleGroupRole(Map<String,Object> searchParams);

    /**
     * @author yuchengyang-pc
     * @description:查询对象角色组角色中间表
     * @date 2014-11-28 17:38:38
     * @param id
     * @return
     */
    public SysRoleGroupRoleDTO findSysRoleGroupRoleByPrimaryKey(String id);
    
    /**
     * @author yuchengyang-pc
     * @description: 新增对象角色组角色中间表
     * @date 2014-11-28 17:38:38
     * @param paramMap
     * @return
     */
    public int insertSysRoleGroupRole(Map<String, Object> paramMap);
    
    /**
     * @author yuchengyang-pc
     * @description: 更新对象角色组角色中间表
     * @date 2014-11-28 17:38:38
     * @param paramMap
     */
    public void updateSysRoleGroupRole(Map<String, Object> paramMap);
     
    /**
     * @author yuchengyang-pc
     * @description: 按主键删除角色组角色中间表
     * @date 2014-11-28 17:38:38
     * @param ids
     * @return
     */ 
    public void deleteSysRoleGroupRoleByPrimaryKey(Map<String, Object> paramMap);
    
    /**
     * @author yuchengyang-pc
     * @description: 按主键删除角色组角色中间表
     * @date 2014-11-28 17:38:38
     * @param ids
     * @return
     */ 
    public void deleteSysRoleGroupRoleByID(Map<String, Object> paramMap);
    
    
}
