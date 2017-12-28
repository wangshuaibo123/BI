package com.jy.modules.platform.dataprv.sysprvrole.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.dataprv.sysprvrole.dto.SysPrvRoleDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysPrvRoleDao
 * @description: 定义  数据权限角色定义 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  wangxz
 */
@MyBatisRepository
public interface SysPrvRoleDao {
    
    /**
     * @author wangxz
     * @description: 分页查询数据权限角色定义
     * @date 2014-10-18 16:07:13
     * @param searchParams
     * @return
     */
    public List<SysPrvRoleDTO> searchSysPrvRoleByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author wangxz
     * @description:查询对象数据权限角色定义
     * @date 2014-10-18 16:07:13
     * @param searchParams
     * @return
     */
    public List<SysPrvRoleDTO> searchSysPrvRole(Map<String,Object> searchParams);

    /**
     * @author wangxz
     * @description:查询对象数据权限角色定义
     * @date 2014-10-18 16:07:13
     * @param id
     * @return
     */
    public SysPrvRoleDTO findSysPrvRoleByPrimaryKey(String id);
    
    /**
     * @author wangxz
     * @description: 新增对象数据权限角色定义
     * @date 2014-10-18 16:07:13
     * @param paramMap
     * @return
     */
    public int insertSysPrvRole(Map<String, Object> paramMap);
    
    /**
     * @author wangxz
     * @description: 更新对象数据权限角色定义
     * @date 2014-10-18 16:07:13
     * @param paramMap
     */
    public void updateSysPrvRole(Map<String, Object> paramMap);
     
    /**
     * @author wangxz
     * @description: 按主键删除数据权限角色定义
     * @date 2014-10-18 16:07:13
     * @param ids
     * @return
     */ 
    public void deleteSysPrvRoleByID(Map<String, Object> paramMap);
    
    public List<Map> queryUserRoleResourceListByIds(Map<String,Object> paramMap);
    
    //唯一性验证
    public String queryRoleByCode(String code);
    
    /**
     * 查询所有角色的资源
     * @author pengliuxiang
     * @param roleId
     * @return
     */
    public List<Map> queryRoleResourceByRoleId(String roleId);
    
    
    
    /**
     * 
     * 递归查询组织机构下面所有的节点
     * 
     * 
     */
    public List<Map> queryRecursiveByOrgId(String orgId);
    
    
    
    
    
}
