package com.jy.modules.logmonitor.sysappmanagecontactway.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.logmonitor.sysappmanagecontactway.dto.SysAppManageContactWayDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysAppManageContactWayDao
 * @description: 定义  系统管理者联系方式 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  lei
 */
@MyBatisRepository
public interface SysAppManageContactWayDao {
    
    /**
     * @author lei
     * @description: 分页查询系统管理者联系方式
     * @date 2015-06-12 16:34:26
     * @param searchParams
     * @return
     */
    public List<SysAppManageContactWayDTO> searchSysAppManageContactWayByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author lei
     * @description:查询对象系统管理者联系方式
     * @date 2015-06-12 16:34:26
     * @param searchParams
     * @return
     */
    public List<SysAppManageContactWayDTO> searchSysAppManageContactWay(Map<String,Object> searchParams);

    /**
     * @author lei
     * @description:查询对象系统管理者联系方式
     * @date 2015-06-12 16:34:26
     * @param id
     * @return
     */
    public SysAppManageContactWayDTO findSysAppManageContactWayByPrimaryKey(String id);
    
    /**
     * @author lei
     * @description: 新增对象系统管理者联系方式
     * @date 2015-06-12 16:34:26
     * @param paramMap
     * @return
     */
    public int insertSysAppManageContactWay(Map<String, Object> paramMap);
    
    /**
     * @author lei
     * @description: 更新对象系统管理者联系方式
     * @date 2015-06-12 16:34:26
     * @param paramMap
     */
    public void updateSysAppManageContactWay(Map<String, Object> paramMap);
     
    /**
     * @author lei
     * @description: 按主键删除系统管理者联系方式
     * @date 2015-06-12 16:34:26
     * @param ids
     * @return
     */ 
    public void deleteSysAppManageContactWayByPrimaryKey(Map<String, Object> paramMap);
    
    
}
