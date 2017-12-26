package com.fintech.modules.platform.dataprv.sysprvbizuser.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.dataprv.sysprvbizuser.dto.SysPrvBizUserDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysPrvBizUserDao
 * @description: 定义  业务数据用户权限表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysPrvBizUserDao {
    
    /**
     * @author
     * @description: 分页查询业务数据用户权限表
     * @date 2014-10-18 16:05:11
     * @param searchParams
     * @return
     */
    public List<SysPrvBizUserDTO> searchSysPrvBizUserByPaging(Map<String, Object> searchParams) ;
    
    
    /**
     * @author
     * @description: 分页查询业务数据用户权限表 关联用户表查询用户名
     * @date 2014-11-26 13:05:11
     * @param searchParams
     * @return
     */
    public List<SysPrvBizUserDTO> searchSysPrvBizUserNameByPaging(Map<String, Object> searchParams) ;
    
    
    /**
     * @author
     * @description:查询对象业务数据用户权限表
     * @date 2014-10-18 16:05:11
     * @param searchParams
     * @return
     */
    public List<SysPrvBizUserDTO> searchSysPrvBizUser(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象业务数据用户权限表
     * @date 2014-10-18 16:05:11
     * @param id
     * @return
     */
    public SysPrvBizUserDTO findSysPrvBizUserByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象业务数据用户权限表
     * @date 2014-10-18 16:05:11
     * @param paramMap
     * @return
     */
    public int insertSysPrvBizUser(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象业务数据用户权限表
     * @date 2014-10-18 16:05:11
     * @param paramMap
     */
    public void updateSysPrvBizUser(Map<String, Object> paramMap);
    
    /**
     * 更新对象业务用户权限的更新状态
     */
    public void updateSysPrvBizUserSyn();
     
    /**
     * @author
     * @description: 按主键删除业务数据用户权限表
     * @date 2014-10-18 16:05:11
     * @param ids
     * @return
     */ 
    public void deleteSysPrvBizUserByPrimaryKey(Map<String, Object> paramMap);
    
    
    /**
     * 根据业务表名，业务ID删除记录
     * @param paramMap
     */
    public void deleteSysPrvBizUserByParams(Map<String,Object> paramMap);
    
}
