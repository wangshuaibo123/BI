package com.fintech.modules.platform.dataprv.sysprvorgauth.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.dataprv.sysprvorgauth.dto.SysPrvOrgAuthDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysPrvOrgAuthDao
 * @description: 定义  组织授权表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysPrvOrgAuthDao {
    
    /**
     * @author
     * @description: 分页查询组织授权表
     * @date 2014-10-18 16:07:01
     * @param searchParams
     * @return
     */
    public List<SysPrvOrgAuthDTO> searchSysPrvOrgAuthByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象组织授权表
     * @date 2014-10-18 16:07:01
     * @param searchParams
     * @return
     */
    public List<SysPrvOrgAuthDTO> searchSysPrvOrgAuth(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象组织授权表
     * @date 2014-10-18 16:07:01
     * @param id
     * @return
     */
    public SysPrvOrgAuthDTO findSysPrvOrgAuthByPrimaryKey(String id);
    
    /**
     * 更新对象组织机构授权的更新状态
     */
    public void updateSysPrvOrgAuthSyn();
    
    /**
     * 根据ids获取数据
     * @param ids
     * @return
     */
    public List<SysPrvOrgAuthDTO> searchSysPrvOrgAuthsByPrimaryKeys(Map<String, Object> paramMap);
    /**
     * @author
     * @description: 新增对象组织授权表
     * @date 2014-10-18 16:07:01
     * @param paramMap
     * @return
     */
    public int insertSysPrvOrgAuth(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象组织授权表
     * @date 2014-10-18 16:07:01
     * @param paramMap
     */
    public void updateSysPrvOrgAuth(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除组织授权表
     * @date 2014-10-18 16:07:01
     * @param ids
     * @return
     */ 
    public void deleteSysPrvOrgAuthByID(Map<String, Object> paramMap);
    
    /**
     * 唯一性验证
     * @param param
     * @return
     */
    public String queryInfoByUserAndOrg(Map<String,Object> param);
}
