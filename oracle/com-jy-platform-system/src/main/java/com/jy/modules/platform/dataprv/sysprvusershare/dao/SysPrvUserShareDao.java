package com.jy.modules.platform.dataprv.sysprvusershare.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.dataprv.sysprvusershare.dto.SysPrvUserShareDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysPrvUserShareDao
 * @description: 定义  数据共享表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  wangxz
 */
@MyBatisRepository
public interface SysPrvUserShareDao {
    
    /**
     * @author wangxz
     * @description: 分页查询数据共享表
     * @date 2014-10-18 16:07:49
     * @param searchParams
     * @return
     */
    public List<SysPrvUserShareDTO> searchSysPrvUserShareByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author wangxz
     * @description:查询对象数据共享表
     * @date 2014-10-18 16:07:49
     * @param searchParams
     * @return
     */
    public List<SysPrvUserShareDTO> searchSysPrvUserShare(Map<String,Object> searchParams);

    /**
     * @author wangxz
     * @description:查询对象数据共享表
     * @date 2014-10-18 16:07:49
     * @param id
     * @return
     */
    public SysPrvUserShareDTO findSysPrvUserShareByPrimaryKey(String id);
    
    /**
     * @author wangxz
     * @description: 新增对象数据共享表
     * @date 2014-10-18 16:07:49
     * @param paramMap
     * @return
     */
    public int insertSysPrvUserShare(Map<String, Object> paramMap);
    
    /**
     * @author wangxz
     * @description: 更新对象数据共享表
     * @date 2014-10-18 16:07:49
     * @param paramMap
     */
    public void updateSysPrvUserShare(Map<String, Object> paramMap);
     
    /**
     * 更新对象数据共享的更新状态
     */
    public void updateSysPrvUserShareSyn();
    
    /**
     * @author wangxz
     * @description: 按主键删除数据共享表
     * @date 2014-10-18 16:07:49
     * @param ids
     * @return
     */ 
    public void deleteSysPrvUserShareByID(Map<String, Object> paramMap);
    
 
    public List<SysPrvUserShareDTO> searchSysPrvUserShareByIds(Map<String, Object> paramMap);
    
    /**
     * 唯一性验证
     * @param paramMap
     * @return
     */
    public String queryInfoNumByUser(Map<String,Object> paramMap);
    
    public int searchSysPrvUserShareCount(Map<String,Object> paramMap);
    
    
    public List<SysPrvUserShareDTO> searchysPrvUserShareByID(Map<String, Object> searchParams);
}
