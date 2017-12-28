package com.jy.modules.platform.dataprv.sysprvroleauth.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.dataprv.sysprvroleauth.dto.SysPrvRoleAuthDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysPrvRoleAuthDao
 * @description: 定义  数据权限角色授权表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  wangxz
 */
@MyBatisRepository
public interface SysPrvRoleAuthDao {
    
    /**
     * @author wangxz
     * @description: 分页查询数据权限角色授权表
     * @date 2014-10-18 16:07:22
     * @param searchParams
     * @return
     */
    public List<SysPrvRoleAuthDTO> searchSysPrvRoleAuthByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author wangxz
     * @description:查询对象数据权限角色授权表
     * @date 2014-10-18 16:07:22
     * @param searchParams
     * @return
     */
    public List<SysPrvRoleAuthDTO> searchSysPrvRoleAuth(Map<String,Object> searchParams);

    /**
     * @author wangxz
     * @description:查询对象数据权限角色授权表
     * @date 2014-10-18 16:07:22
     * @param id
     * @return
     */
    public SysPrvRoleAuthDTO findSysPrvRoleAuthByPrimaryKey(String id);
    
    /**
     * @author wangxz
     * @description: 新增对象数据权限角色授权表
     * @date 2014-10-18 16:07:22
     * @param paramMap
     * @return
     */
    public int insertSysPrvRoleAuth(Map<String, Object> paramMap);
    
    /**
     * @author wangxz
     * @description: 更新对象数据权限角色授权表
     * @date 2014-10-18 16:07:22
     * @param paramMap
     */
    public void updateSysPrvRoleAuth(Map<String, Object> paramMap);
    
    /**
     * 更新对象数据角色的更新状态值
     */
    public void updateSysPrvRoleAuthSyn();
     
    /**
     * @author wangxz
     * @description: 按主键删除数据权限角色授权表
     * @date 2014-10-18 16:07:22
     * @param ids
     * @return
     */ 
    public void deleteSysPrvRoleAuthByID(Map<String, Object> paramMap);
    
    /**
     * 根据角色ID获取授权信息
     * @param roleIds
     * @return
     */
    public List<SysPrvRoleAuthDTO> searchUserAuthByRoleIds(String roleIds);
    
    
    public void deleteSysPrvRoleAuthByRoleIds(Map<String, Object> paramMap);

    //唯一性验证
	public String queryRoleAuthByUser(Map<String, Object> params);
	
	
	/**
	 * 通过ids查询授权的信息
	 * @param searchParams
	 * @return
	 */
	public List<SysPrvRoleAuthDTO> findSysPrvRoleAuthByPrimaryKeys(Map<String, Object> searchParams);
    
}
