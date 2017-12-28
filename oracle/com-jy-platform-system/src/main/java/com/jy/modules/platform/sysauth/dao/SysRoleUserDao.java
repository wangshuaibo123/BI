package com.jy.modules.platform.sysauth.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.sysauth.dto.SysRoleUserDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysRoleUserDao
 * @description: 定义  角色用户表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  chen_gang
 */
@MyBatisRepository
public interface SysRoleUserDao {
    
    /**
     * @author chen_gang
     * @description: 分页查询角色用户表
     * @date 2014-10-15 10:25:12
     * @param searchParams
     * @return
     */
    public List<SysRoleUserDTO> searchSysRoleUserByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author chen_gang
     * @description:查询对象角色用户表
     * @date 2014-10-15 10:25:12
     * @param searchParams
     * @return
     */
    public List<SysRoleUserDTO> searchSysRoleUser(Map<String,Object> searchParams);

    /**
     * @author chen_gang
     * @description:查询对象角色用户表
     * @date 2014-10-15 10:25:12
     * @param id
     * @return
     */
    public SysRoleUserDTO findSysRoleUserByPrimaryKey(String id);
    
    /**
     * @author chen_gang
     * @description: 新增对象角色用户表
     * @date 2014-10-15 10:25:12
     * @param paramMap
     * @return
     */
    public int insertSysRoleUser(Map<String, Object> paramMap);
    
    /**
     * @author chen_gang
     * @description: 更新对象角色用户表
     * @date 2014-10-15 10:25:12
     * @param paramMap
     */
    public void updateSysRoleUser(Map<String, Object> paramMap);
     
    /**
     * @author chen_gang
     * @description: 按主键 删除角色用户表
     * @date 2014-10-15 10:25:12
     * @param ids
     * @return
     */ 
    public void deleteSysRoleUserByPrimaryKey(Map<String, Object> paramMap);
   
    /**
     * @author chen_gang
     * @description: 按roleId键删除角色用户表
     * @date 2014-10-15 10:25:12
     * @param ids
     * @return
     */ 
    public void deleteSysRoleUserByRoleId(Map<String, Object> paramMap);
    
    
    
    public List<SysRoleUserDTO> findSysRoleOrgByRoleId(Map<String, Object> searchParams);
    
    
    /**
     * @author gyl
     * @deprecated: 获取当前用户是管理员的情况下，能管理的机构和权限信息
     * @return
     */
    public SysRoleUserDTO findSysRoleOrgByCurrentUser(Map<String, Object> params);
    
    /**
     * @author gyl
     * @deprecated:	根据当前机构清理机构下离职人员的操作权限
     * @param searchParams
     * @return
     */
    public void modifySysRoleUserByOrgId(Map<String, Object> searchParams);
}
