package com.fintech.modules.platform.sysorg.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysorg.dto.SysOrgDTO;
import com.fintech.modules.platform.sysorg.dto.VmtreeInfoDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;

/**
 * @classname: SysOrgDao
 * @description: 定义 机构部门表 持久层 接口 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring
 *               Context中生成实现. 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysOrgDao {

    /**
     * @author
     * @description: 分页查询机构部门表
     * @date 2014-10-15 10:26:06
     * @param searchParams
     * @return
     */
    public List<SysOrgDTO> searchSysOrgByPaging(Map<String, Object> searchParams);

    /**
     * @author
     * @description:查询对象机构部门表
     * @date 2014-10-15 10:26:06
     * @param searchParams
     * @return
     */
    public List<SysOrgDTO> searchSysOrg(Map<String, Object> searchParams);

    /**
     * @author
     * @description:查询列表 SYS_ORG 根据人员id，岗位id查询org的parentIds author : cxt 、cyy
     * @date 2014-12-09 20:29:06
     * @param searchParams
     * @return
     */
    public List<SysOrgDTO> searchSysOrgByUserIdAndParentIds(Map<String, Object> searchParams);

    /**
     * @author
     * @description:查询对象机构部门表
     * @date 2014-10-15 10:26:06
     * @param id
     * @return
     */
    public SysOrgDTO findSysOrgByPrimaryKey(String id);

    /**
     * @author
     * @description: 新增对象机构部门表
     * @date 2014-10-15 10:26:06
     * @param paramMap
     * @return
     */
    public int insertSysOrg(Map<String, Object> paramMap);

    /**
     * @author
     * @description: 更新对象机构部门表
     * @date 2014-10-15 10:26:06
     * @param paramMap
     */
    public void updateSysOrg(Map<String, Object> paramMap);
    
    /**
     *  @Description    : TODO luobangpeng 方法描述
     *  @Method_Name    : updateSysOrgParentIds
     *  @param paramMap
     *  @return         : void
     *  @Creation Date  : 2015-8-20 下午3:30:48 
     *  @author
     */
    public void  updateSysOrgParentIds(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新叶子节点的属性
     * @date 2014-10-15 10:26:06
     * @param paramMap
     */
    public void updateSysOrgLeef(Map<String, Object> paramMap);

    /**
     * @author
     * @description: 按主键删除机构部门表
     * @date 2014-10-15 10:26:06
     * @param ids
     * @return
     */
    public void deleteSysOrgByPrimaryKey(Map<String, Object> paramMap);
    
    
    /**
     * 
     * 查询多个机构
     * @param paramMap
     * @return
     */
    
    public List<SysOrgDTO> searchSysOrgByIds(Map<String, Object> paramMap);
    
    
    /**
     * 
     * 通过orgId 查询虚拟树信息
     * @param searchParams
     * @return
     */
    
    public VmtreeInfoDTO findVmtreeInfoByOrgId(String orgId);

	public List<SysOrgDTO> queryTreeSysOrgByOrgLevel(Map<String, Object> searchParams);
	
	/**
	 * 根据条件查询机构
	 * @param searchParams
	 * @return
	 */
	public List<SysOrgDTO>searchSysOrgByWhere(Map<String, Object> searchParams);

}
