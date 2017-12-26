package com.fintech.modules.platform.sysorg.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysorg.dto.SysPositionSynDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysPositionSynDao
 * @description: 定义  岗位待同步表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysPositionSynDao {
    
    /**
     * @author
     * @description: 分页查询岗位待同步表
     * @date 2015-01-16 16:26:31
     * @param searchParams
     * @return
     */
    public List<SysPositionSynDTO> searchSysPositionSynByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象岗位待同步表
     * @date 2015-01-16 16:26:31
     * @param searchParams
     * @return
     */
    public List<SysPositionSynDTO> searchSysPositionSyn(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象岗位待同步表
     * @date 2015-01-16 16:26:31
     * @param id
     * @return
     */
    public SysPositionSynDTO findSysPositionSynByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象岗位待同步表
     * @date 2015-01-16 16:26:31
     * @param paramMap
     * @return
     */
    public int insertSysPositionSyn(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象岗位待同步表
     * @date 2015-01-16 16:26:31
     * @param paramMap
     */
    public void updateSysPositionSyn(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除岗位待同步表
     * @date 2015-01-16 16:26:31
     * @param ids
     * @return
     */ 
    public void deleteSysPositionSynByPrimaryKey(Map<String, Object> paramMap);
    /**
     * @author
     * @description: 查找最后同步时间记录
     * @date 2015-01-19 11:48:48
     * @param paramMap 默认为空
     * @return
     */ 
    public SysPositionSynDTO searchSysPositionSynByMaxCreateDate(Map<String, Object> paramMap);
    
}
