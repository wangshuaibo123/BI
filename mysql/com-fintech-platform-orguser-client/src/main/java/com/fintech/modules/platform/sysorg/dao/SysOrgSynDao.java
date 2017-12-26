package com.fintech.modules.platform.sysorg.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysorg.dto.SysOrgSynDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysOrgSynDao
 * @description: 定义  机构部门表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysOrgSynDao {
    
    /**
     * @author
     * @description: 分页查询机构部门表
     * @date 2015-01-20 10:24:15
     * @param searchParams
     * @return
     */
    public List<SysOrgSynDTO> searchSysOrgSynByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象机构部门表
     * @date 2015-01-20 10:24:15
     * @param searchParams
     * @return
     */
    public List<SysOrgSynDTO> searchSysOrgSyn(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象机构部门表
     * @date 2015-01-20 10:24:15
     * @param id
     * @return
     */
    public SysOrgSynDTO findSysOrgSynByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象机构部门表
     * @date 2015-01-20 10:24:15
     * @param paramMap
     * @return
     */
    public int insertSysOrgSyn(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象机构部门表
     * @date 2015-01-20 10:24:15
     * @param paramMap
     */
    public void updateSysOrgSyn(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除机构部门表
     * @date 2015-01-20 10:24:15
     * @param ids
     * @return
     */ 
    public void deleteSysOrgSynByPrimaryKey(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 查找上一次同步时间记录
     * @date 2015-01-19 11:48:48
     * @param paramMap 默认为空
     * @return
     */ 
    public SysOrgSynDTO searchSysOrgSynByMaxCreateDate(Map<String, Object> paramMap);
}
