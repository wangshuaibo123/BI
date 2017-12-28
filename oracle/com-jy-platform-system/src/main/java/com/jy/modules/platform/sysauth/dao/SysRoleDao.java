package com.jy.modules.platform.sysauth.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.sysauth.dto.SysRoleDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysRoleDao
 * @description: 定义  角色表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  chen_gang
 */
@MyBatisRepository
public interface SysRoleDao {
    
    /**
     * @author chen_gang
     * @description: 分页查询角色表
     * @date 2014-10-15 10:24:59
     * @param searchParams
     * @return
     */
    public List<SysRoleDTO> searchSysRoleByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author chen_gang
     * @description:查询对象角色表
     * @date 2014-10-15 10:24:59
     * @param searchParams
     * @return
     */
    public List<SysRoleDTO> searchSysRole(Map<String,Object> searchParams);

    /**
     * @author chen_gang
     * @description:查询对象角色表
     * @date 2014-10-15 10:24:59
     * @param id
     * @return
     */
    public SysRoleDTO findSysRoleByPrimaryKey(String id);
    
    /**
     * @author chen_gang
     * @description: 新增对象角色表
     * @date 2014-10-15 10:24:59
     * @param paramMap
     * @return
     */
    public int insertSysRole(Map<String, Object> paramMap);
    
    /**
     * @author chen_gang
     * @description: 更新对象角色表
     * @date 2014-10-15 10:24:59
     * @param paramMap
     */
    public void updateSysRole(Map<String, Object> paramMap);
     
    /**
     * @author chen_gang
     * @description: 按主键删除角色表
     * @date 2014-10-15 10:24:59
     * @param ids
     * @return
     */ 
    public void deleteSysRoleByPrimaryKey(Map<String, Object> paramMap);
    
    public List<SysRoleDTO> getSysRoleByTargetId(Map<String, Object> paramMap);
    
    
    public int getRoleByOrgId(long userId);
}
