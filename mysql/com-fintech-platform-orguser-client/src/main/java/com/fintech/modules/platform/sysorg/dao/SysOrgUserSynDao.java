package com.fintech.modules.platform.sysorg.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysorg.dto.SysOrgUserSynDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysOrgUserSynDao
 * @description: 定义  SYS_ORG_USER_SYN 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysOrgUserSynDao {
    
    /**
     * @author
     * @description: 分页查询SYS_ORG_USER_SYN
     * @date 2015-01-19 11:48:48
     * @param searchParams
     * @return
     */
    public List<SysOrgUserSynDTO> searchSysOrgUserSynByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象SYS_ORG_USER_SYN
     * @date 2015-01-19 11:48:48
     * @param searchParams
     * @return
     */
    public List<SysOrgUserSynDTO> searchSysOrgUserSyn(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象SYS_ORG_USER_SYN
     * @date 2015-01-19 11:48:48
     * @param id
     * @return
     */
    public SysOrgUserSynDTO findSysOrgUserSynByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象SYS_ORG_USER_SYN
     * @date 2015-01-19 11:48:48
     * @param paramMap
     * @return
     */
    public int insertSysOrgUserSyn(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象SYS_ORG_USER_SYN
     * @date 2015-01-19 11:48:48
     * @param paramMap
     */
    public void updateSysOrgUserSyn(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除SYS_ORG_USER_SYN
     * @date 2015-01-19 11:48:48
     * @param ids
     * @return
     */ 
    public void deleteSysOrgUserSynByPrimaryKey(Map<String, Object> paramMap);
    /**
     * @author
     * @description: 查找上一次同步时间记录
     * @date 2015-01-19 11:48:48
     * @param paramMap 默认为空
     * @return
     */ 
    public SysOrgUserSynDTO searchSysOrgUserSynByMaxCreateDate(Map<String, Object> paramMap);
    
    
}
