package com.jy.modules.platform.bizauth.vmuservmorgmap.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.bizauth.vmuservmorgmap.dto.VmuserVmorgMapDTO;
import com.jy.modules.platform.sysorg.dto.SysUserDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: VmuserVmorgMapDao
 * @description: 定义  员工虚拟组织关系表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  chen_gang
 */
@MyBatisRepository
public interface VmuserVmorgMapDao {
    
    /**
     * @author chen_gang
     * @description: 分页查询员工虚拟组织关系表
     * @date 2015-01-16 17:15:01
     * @param searchParams
     * @return
     */
    public List<VmuserVmorgMapDTO> searchVmuserVmorgMapByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author chen_gang
     * @description:查询对象员工虚拟组织关系表
     * @date 2015-01-16 17:15:01
     * @param searchParams
     * @return
     */
    public List<VmuserVmorgMapDTO> searchVmuserVmorgMap(Map<String,Object> searchParams);
    
    /**
     * @author chen_gang
     * @description:根据ORGID查询对象员工虚拟组织关系表
     * @date 2015-01-16 17:15:01
     * @param searchParams
     * @return
     */
    public List<VmuserVmorgMapDTO> searchVmuserVmorgMapByOrgId(Map<String,Object> searchParams);
    
    
    /**
     * @author dlg
     * @description:根据ORGID查询下一级的对象员工虚拟组织关系表
     * @date 2015-01-16 17:15:01
     * @param searchParams
     * @return
     */
    public List<VmuserVmorgMapDTO> searchVmuserVmorgMapByparent(Map<String,Object> searchParams);
    
    
    /**
     * @author dlg
     * @description:根据ORGID查询下一级的对象员工虚拟组织关系表
     * @date 2015-02-04 17:15:01
     * @param searchParams
     * @return
     */
    public List<VmuserVmorgMapDTO> searchVmuserVmorgMapByxnparent(Map<String,Object> searchParams);
    

    /**
     * @author chen_gang
     * @description:查询对象员工虚拟组织关系表
     * @date 2015-01-16 17:15:01
     * @param id
     * @return
     */
    public VmuserVmorgMapDTO findVmuserVmorgMapByPrimaryKey(String id);
    
    /**
     * @author chen_gang
     * @description: 新增对象员工虚拟组织关系表
     * @date 2015-01-16 17:15:01
     * @param paramMap
     * @return
     */
    public int insertVmuserVmorgMap(Map<String, Object> paramMap);
    
    /**
     * @author chen_gang
     * @description: 更新对象员工虚拟组织关系表
     * @date 2015-01-16 17:15:01
     * @param paramMap
     */
    public void updateVmuserVmorgMap(Map<String, Object> paramMap);
     
    /**
     * @author chen_gang
     * @description: 按主键删除员工虚拟组织关系表
     * @date 2015-01-16 17:15:01
     * @param ids
     * @return
     */ 
    public void deleteVmuserVmorgMapByPrimaryKey(Map<String, Object> paramMap);
    
    /**
     * @author dlg
     * @description: 按员工删除虚拟组织关系表
     * @date 2015-01-16 17:15:01
     * @param ids
     * @return
     */ 
    public void deleteVmuserVmorgMapByUserId(Map<String, Object> paramMap);
    
    
    /**
     * 按userId和orgId删除虚拟组织关系表
     * @param paramMap
     */
    public void deleteVmuserVmorgMapByUserIdAndOrgId(Map<String, Object> paramMap);
    
    /**
     * @author dlg
     * @description: 按ORGID删除虚拟组织关系表
     * @date 2015-01-16 17:15:01
     * @param ids
     * @return
     */ 
    public void deleteVmuserVmorgMapByOrgId(Map<String, Object> paramMap);
    
    
    
    
    public List<SysUserDTO> searchVmuserByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author dlg
     * @description: 根据userId,orgId,orgType查询虚拟组织关系表是否重复
     * @date 2015-01-16 17:15:01
     * @param ids
     * @return
     */ 
    public List<VmuserVmorgMapDTO> validateVmuserVmorgMapBycondtions(Map<String,Object> searchParams);

    /**
     * 清理离职人员在业务用户中的配置
     * @param paramMap
     */
	public void modifyCleanVmorgMap(Map<String, Object> paramMap);
    
}
