package com.fintech.modules.platform.sysprvauthpool.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysprvauthpool.dto.SysPrvAuthPoolDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysPrvAuthPoolDao
 * @description: 定义  SYS_PRV_AUTH_POOL 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysPrvAuthPoolDao {
    
    /**
     * @author
     * @description: 分页查询SYS_PRV_AUTH_POOL
     * @date 2015-01-12 20:13:17
     * @param searchParams
     * @return
     */
    public List<SysPrvAuthPoolDTO> searchSysPrvAuthPoolByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象SYS_PRV_AUTH_POOL
     * @date 2015-01-12 20:13:17
     * @param searchParams
     * @return
     */
    public List<SysPrvAuthPoolDTO> searchSysPrvAuthPool(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象SYS_PRV_AUTH_POOL
     * @date 2015-01-12 20:13:17
     * @param id
     * @return
     */
    public SysPrvAuthPoolDTO findSysPrvAuthPoolByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象SYS_PRV_AUTH_POOL
     * @date 2015-01-12 20:13:17
     * @param paramMap
     * @return
     */
    public int insertSysPrvAuthPool(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象SYS_PRV_AUTH_POOL
     * @date 2015-01-12 20:13:17
     * @param paramMap
     */
    public void updateSysPrvAuthPool(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除SYS_PRV_AUTH_POOL
     * @date 2015-01-12 20:13:17
     * @param ids
     * @return
     */ 
    public void deleteSysPrvAuthPoolByPrimaryKey(Map<String, Object> paramMap);
    
    
    /**
     * 
     * 通过条件进行删除
     */
    public void deleteSysPrvAuthPoolByMap(Map<String, Object> paramMap);
    
}
