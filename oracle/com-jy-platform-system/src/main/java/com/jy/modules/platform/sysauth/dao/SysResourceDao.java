package com.jy.modules.platform.sysauth.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.sysauth.dto.SysResourceDTO;
import com.jy.modules.platform.sysauth.dto.SysResourceRoleDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysResourceDao
 * @description: 定义  资源管理表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  chen_gang
 */
@MyBatisRepository
public interface SysResourceDao {
    
    /**
     * @author chen_gang
     * @description: 分页查询资源管理表
     * @date 2014-10-15 10:24:37
     * @param searchParams
     * @return
     */
    public List<SysResourceDTO> searchSysResourceByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author chen_gang
     * @description:查询对象资源管理表
     * @date 2014-10-15 10:24:37
     * @param searchParams
     * @return
     */
    public List<SysResourceDTO> searchSysResource(Map<String,Object> searchParams);

    /**
     * @author chen_gang
     * @description:查询对象资源管理表
     * @date 2014-10-15 10:24:37
     * @param id
     * @return
     */
    public SysResourceDTO findSysResourceByPrimaryKey(String id);
    
    /**
     * @author chen_gang
     * @description: 新增对象资源管理表
     * @date 2014-10-15 10:24:37
     * @param paramMap
     * @return
     */
    public int insertSysResource(Map<String, Object> paramMap);
    
    /**
     * @author chen_gang
     * @description: 更新对象资源管理表
     * @date 2014-10-15 10:24:37
     * @param paramMap
     */
    public void updateSysResource(Map<String, Object> paramMap);
     
    /**
     * @author chen_gang
     * @description: 按主键删除资源管理表
     * @date 2014-10-15 10:24:37
     * @param ids
     * @return
     */ 
    public void deleteSysResourceByPrimaryKey(Map<String, Object> paramMap);
    
    /**
     * @author zhanglin 真实删除
     * @description: 按主键删除资源管理表
     * @date 2014-11-11 17:25:37
     * @param ids
     * @return
     */ 
    public void deleteSysResourceByPrimaryKeys(Map<String, Object> paramMap);
    
    
    
    /**
     * @author fangchao
     * 按一组资源id获取资源信息
     * @param resourceList
     * @return
     */
    public List<SysResourceDTO> getSysResourceByIds(List<Long> resourceList);
    /**
     * 获取资源和角色信息
     * @param paramMap
     * @return
     */
    public List<SysResourceRoleDTO> getSysResourceRole(Map<String, Object> paramMap);

    /**
     * 获取所有URL地址
     * @param paramMap
     * @return
     */
	public List<SysResourceRoleDTO> getSysResourceAllUrl(
			Map<String, Object> paramMap);
	
	public List<Map<String,Object>> getUserRoleByTargetId(Map<String, Object> searchParams);
}
