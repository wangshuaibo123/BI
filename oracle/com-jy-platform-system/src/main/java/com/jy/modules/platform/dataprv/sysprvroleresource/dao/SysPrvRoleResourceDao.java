package com.jy.modules.platform.dataprv.sysprvroleresource.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.dataprv.sysprvroleresource.dto.SysPrvRoleResourceDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysPrvRoleResourceDao
 * @description: 定义  数据角色资源表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  wangxz
 */
@MyBatisRepository
public interface SysPrvRoleResourceDao {
    
    /**
     * @author wangxz
     * @description: 分页查询数据角色资源表
     * @date 2014-10-18 16:07:31
     * @param searchParams
     * @return
     */
    public List<SysPrvRoleResourceDTO> searchSysPrvRoleResourceByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author wangxz
     * @description:查询对象数据角色资源表
     * @date 2014-10-18 16:07:31
     * @param searchParams
     * @return
     */
    public List<SysPrvRoleResourceDTO> searchSysPrvRoleResource(Map<String,Object> searchParams);

    /**
     * @author wangxz
     * @description:查询对象数据角色资源表
     * @date 2014-10-18 16:07:31
     * @param id
     * @return
     */
    public SysPrvRoleResourceDTO findSysPrvRoleResourceByPrimaryKey(String id);
    
    /**
     * @author wangxz
     * @description: 新增对象数据角色资源表
     * @date 2014-10-18 16:07:31
     * @param paramMap
     * @return
     */
    public int insertSysPrvRoleResource(Map<String, Object> paramMap);
    
    /**
     * @author wangxz
     * @description: 更新对象数据角色资源表
     * @date 2014-10-18 16:07:31
     * @param paramMap
     */
    public void updateSysPrvRoleResource(Map<String, Object> paramMap);
     
    /**
     * @author wangxz
     * @description: 按主键删除数据角色资源表
     * @date 2014-10-18 16:07:31
     * @param ids
     * @return
     */ 
    public void deleteSysPrvRoleResourceByID(Map<String, Object> paramMap);
    
    public List<SysPrvRoleResourceDTO> searchSysPrvRoleResourceByPrimaryKeys(String ids);
    
    public void deleteSysPrvRoleResourceByRoleIds(Map<String, Object> paramMap);

    //唯一性验证
	public String queryRoleResourceByResource(Map<String, Object> map);
}
