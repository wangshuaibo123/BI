package com.fintech.modules.platform.sysversion.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysversion.dto.SysVersionDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysVersionDao
 * @description: 定义  系统版本号表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysVersionDao {
    
    /**
     * @author
     * @description: 分页查询系统版本号表
     * @date 2015-03-17 10:32:51
     * @param searchParams
     * @return
     */
    public List<SysVersionDTO> searchSysVersionByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象系统版本号表
     * @date 2015-03-17 10:32:51
     * @param searchParams
     * @return
     */
    public List<SysVersionDTO> searchSysVersion(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象系统版本号表
     * @date 2015-03-17 10:32:51
     * @param id
     * @return
     */
    public SysVersionDTO findSysVersionByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象系统版本号表
     * @date 2015-03-17 10:32:51
     * @param paramMap
     * @return
     */
    public int insertSysVersion(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象系统版本号表
     * @date 2015-03-17 10:32:51
     * @param paramMap
     */
    public void updateSysVersion(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除系统版本号表
     * @date 2015-03-17 10:32:51
     * @param ids
     * @return
     */ 
    public void deleteSysVersionByPrimaryKey(Map<String, Object> paramMap);
    
    
}
