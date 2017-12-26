package com.fintech.modules.platform.bizauth.vmrulemapping.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.bizauth.vmrulemapping.dto.VmruleMappingDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: VmruleMappingDao
 * @description: 定义  映射表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface VmruleMappingDao {
    
    /**
     * @author
     * @description: 分页查询映射表
     * @date 2015-01-16 17:14:38
     * @param searchParams
     * @return
     */
    public List<VmruleMappingDTO> searchVmruleMappingByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象映射表
     * @date 2015-01-16 17:14:38
     * @param searchParams
     * @return
     */
    public List<VmruleMappingDTO> searchVmruleMapping(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象映射表
     * @date 2015-01-16 17:14:38
     * @param id
     * @return
     */
    public VmruleMappingDTO findVmruleMappingByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象映射表
     * @date 2015-01-16 17:14:38
     * @param paramMap
     * @return
     */
    public int insertVmruleMapping(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象映射表
     * @date 2015-01-16 17:14:38
     * @param paramMap
     */
    public void updateVmruleMapping(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除映射表
     * @date 2015-01-16 17:14:38
     * @param ids
     * @return
     */ 
    public void deleteVmruleMappingByPrimaryKey(Map<String, Object> paramMap);
    
    
    public void deleteVmrleMappingByUserId(Map<String, Object> paramMap);
    
    public void  deleteVmDataPrivByUserId(Map<String, Object> paramMap);
    
    
    public void  deleteVmrleMappingByOrgId(Map<String, Object> paramMap);
    
    public void deleteVmDataPrivByOrgId(Map<String, Object> paramMap);
    
    
    public VmruleMappingDTO findVmruleMappingByPrimaryKeyAndTableName(Map<String, Object> paramMap);
    
    /**
     * 查询该用户的所有的数据权限
     * @param paramMap
     * @return
     */
    public List<SysUserDTO> findVmruleMappingByUserId(Map<String, Object> paramMap);

    /**
     * 清理离职人员业务映射
     * @param mappingMap
     */
	public void modifyCleanVmruleMapping(Map<String, Object> mappingMap);
}
