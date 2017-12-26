package com.fintech.modules.platform.sysorg.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysorg.dto.SysPositionDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysPositionDao
 * @description: 定义  岗位定义表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysPositionDao {
    
    /**
     * @author
     * @description: 分页查询岗位定义表
     * @date 2014-10-15 10:26:19
     * @param searchParams
     * @return
     */
    public List<SysPositionDTO> searchSysPositionByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象岗位定义表
     * @date 2014-10-15 10:26:19
     * @param searchParams
     * @return
     */
    public List<SysPositionDTO> searchSysPosition(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象岗位定义表
     * @date 2014-10-15 10:26:19
     * @param id
     * @return
     */
    public SysPositionDTO findSysPositionByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象岗位定义表
     * @date 2014-10-15 10:26:19
     * @param paramMap
     * @return
     */
    public int insertSysPosition(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象岗位定义表
     * @date 2014-10-15 10:26:19
     * @param paramMap
     */
    public void updateSysPosition(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除岗位定义表
     * @date 2014-10-15 10:26:19
     * @param ids
     * @return
     */ 
    public void deleteSysPositionByPrimaryKey(Map<String, Object> paramMap);
    
    
}
