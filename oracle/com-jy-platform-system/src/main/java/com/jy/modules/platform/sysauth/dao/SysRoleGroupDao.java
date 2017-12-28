package com.jy.modules.platform.sysauth.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.sysauth.dto.SysRoleGroupDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysRoleGroupDao
 * @description: 定义  角色组 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  yuchengyang-pc
 */
@MyBatisRepository
public interface SysRoleGroupDao {
    
    /**
     * @author yuchengyang-pc
     * @description: 分页查询角色组
     * @date 2014-11-28 15:38:04
     * @param searchParams
     * @return
     */
    public List<SysRoleGroupDTO> searchSysRoleGroupByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author yuchengyang-pc
     * @description:查询对象角色组
     * @date 2014-11-28 15:38:04
     * @param searchParams
     * @return
     */
    public List<SysRoleGroupDTO> searchSysRoleGroup(Map<String,Object> searchParams);

    /**
     * @author yuchengyang-pc
     * @description:查询对象角色组
     * @date 2014-11-28 15:38:04
     * @param id
     * @return
     */
    public SysRoleGroupDTO findSysRoleGroupByPrimaryKey(String id);
    
    /**
     * @author yuchengyang-pc
     * @description: 新增对象角色组
     * @date 2014-11-28 15:38:04
     * @param paramMap
     * @return
     */
    public int insertSysRoleGroup(Map<String, Object> paramMap);
    
    /**
     * @author yuchengyang-pc
     * @description: 更新对象角色组
     * @date 2014-11-28 15:38:04
     * @param paramMap
     */
    public void updateSysRoleGroup(Map<String, Object> paramMap);
     
    /**
     * @author yuchengyang-pc
     * @description: 按主键删除角色组
     * @date 2014-11-28 15:38:04
     * @param ids
     * @return
     */ 
    public void deleteSysRoleGroupByPrimaryKey(Map<String, Object> paramMap);
    
    
}
