package com.fintech.modules.platform.sysauth.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysauth.dto.SysAclDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysAclDao
 * @description: 定义  操作权限控制表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysAclDao {
    
    /**
     * @author
     * @description: 分页查询操作权限控制表
     * @date 2014-10-15 10:23:44
     * @param searchParams
     * @return
     */
    public List<SysAclDTO> searchSysAclByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象操作权限控制表
     * @date 2014-10-15 10:23:44
     * @param searchParams
     * @return
     */
    public List<SysAclDTO> searchSysAcl(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象操作权限控制表
     * @date 2014-10-15 10:23:44
     * @param id
     * @return
     */
    public SysAclDTO findSysAclByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象操作权限控制表
     * @date 2014-10-15 10:23:44
     * @param paramMap
     * @return
     */
    public int insertSysAcl(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象操作权限控制表
     * @date 2014-10-15 10:23:44
     * @param paramMap
     */
    public void updateSysAcl(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除操作权限控制表
     * @date 2014-10-15 10:23:44
     * @param ids
     * @return
     */ 
    public void deleteSysAclByPrimaryKey(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 按主键删除操作权限控制表
     * @date 2014-10-15 10:23:44
     * @param ids
     * @return
     */ 
    public void deleteSysAclByPrimaryKeys(Map<String, Object> paramMap);
    
    /**
     * @author
     * 获取一组资源id
     * @param roleId
     * @return
     */
     public List<Long> getResourceIdsByRoleId(Long roleId);
}
