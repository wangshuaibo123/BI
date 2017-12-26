package com.fintech.modules.platform.sysauth.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysauth.dto.SysRoleGroupDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysRoleGroupDao
 * @description: 定义  角色组 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysRoleGroupDao {
    
    /**
     * @author
     * @description: 分页查询角色组
     * @date 2014-11-28 15:38:04
     * @param searchParams
     * @return
     */
    public List<SysRoleGroupDTO> searchSysRoleGroupByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象角色组
     * @date 2014-11-28 15:38:04
     * @param searchParams
     * @return
     */
    public List<SysRoleGroupDTO> searchSysRoleGroup(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象角色组
     * @date 2014-11-28 15:38:04
     * @param id
     * @return
     */
    public SysRoleGroupDTO findSysRoleGroupByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象角色组
     * @date 2014-11-28 15:38:04
     * @param paramMap
     * @return
     */
    public int insertSysRoleGroup(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象角色组
     * @date 2014-11-28 15:38:04
     * @param paramMap
     */
    public void updateSysRoleGroup(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除角色组
     * @date 2014-11-28 15:38:04
     * @param ids
     * @return
     */ 
    public void deleteSysRoleGroupByPrimaryKey(Map<String, Object> paramMap);
    
    
}
