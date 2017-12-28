package com.jy.modules.platform.sysarea.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.sysarea.dto.SysAreaDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysAreaDao
 * @description: 定义  行政区域 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  lin
 */
@MyBatisRepository
public interface SysAreaDao {
    
    /**
     * @author lin
     * @description: 分页查询行政区域
     * @date 2014-10-23 09:53:30
     * @param searchParams
     * @return
     */
    public List<SysAreaDTO> searchSysAreaByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author lin
     * @description:查询对象行政区域
     * @date 2014-10-23 09:53:30
     * @param searchParams
     * @return
     */
    public List<SysAreaDTO> searchSysArea(Map<String,Object> searchParams);

    /**
     * @author lin
     * @description:查询对象行政区域
     * @date 2014-10-23 09:53:30
     * @param id
     * @return
     */
    public SysAreaDTO findSysAreaByPrimaryKey(String id);
    
    /**
     * @author lin
     * @description: 新增对象行政区域
     * @date 2014-10-23 09:53:30
     * @param paramMap
     * @return
     */
    public int insertSysArea(Map<String, Object> paramMap);
    
    /**
     * @author lin
     * @description: 更新对象行政区域
     * @date 2014-10-23 09:53:30
     * @param paramMap
     */
    public void updateSysArea(Map<String, Object> paramMap);
     
    /**
     * @author lin
     * @description: 按主键删除行政区域
     * @date 2014-10-23 09:53:30
     * @param ids
     * @return
     */ 
    public void deleteSysAreaByPrimaryKey(Map<String, Object> paramMap);
    

    /**
     * @author lin
     * @description: 按城市编码查询父行政区域
     * @param paramMap
     * @return
     */
    public List<SysAreaDTO> searchSysAreaByAreaCode(Map<String, Object> paramMap);
    
    /**
     * @author bieshuangping
     * @param code
     * @return
     */
    public List<Map> queryChildAreaByCode(String areaCode);
    
    /**
     * @author bieshuangping
     * @param pid
     * @return
     */
    public List<Map> queryChildAreaByPid(String parentId);
    
    /**
     * @author bieshuangping
     * @param pid
     * @return
     */
    public Map getParentCodeByCode(String areaCode);
    
    /**
     * @author bieshuangping
     * @param pid
     * @return
     */
   public Map getAreaByCode(String areaCode);

}
