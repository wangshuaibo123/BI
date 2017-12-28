package com.jy.modules.platform.sysMenu.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.sysMenu.dto.SysMenuDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysMenuDao
 * @description: 定义  菜单管理表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  Administrator
 */
@MyBatisRepository
public interface SysMenuDao {
    
    /**
     * @author Administrator
     * @description: 分页查询菜单管理表
     * @date 2014-10-14 20:53:04
     * @param searchParams
     * @return
     */
    public List<SysMenuDTO> searchSysMenuByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author Administrator
     * @description:查询对象菜单管理表
     * @date 2014-10-14 20:53:04
     * @param searchParams
     * @return
     */
    public List<SysMenuDTO> searchSysMenu(Map<String,Object> searchParams);
    /**
     * 首页菜单显示专用
     * @param searchParams
     * @return
     */
    public List<SysMenuDTO> searchSysMenuForHome(Map<String,Object> searchParams);

    /**
     * @author Administrator
     * @description:查询对象菜单管理表
     * @date 2014-10-14 20:53:04
     * @param id
     * @return
     */
    public SysMenuDTO findSysMenuByPrimaryKey(String id);
    
    /**
     * @author Administrator
     * @description: 新增对象菜单管理表
     * @date 2014-10-14 20:53:04
     * @param paramMap
     * @return
     */
    public int insertSysMenu(Map<String, Object> paramMap);
    
    /**
     * @author Administrator
     * @description: 更新对象菜单管理表
     * @date 2014-10-14 20:53:04
     * @param paramMap
     */
    public void updateSysMenu(Map<String, Object> paramMap);
     
    /**
     * @author Administrator
     * @description: 按主键删除菜单管理表
     * @date 2014-10-14 20:53:04
     * @param ids
     * @return
     */ 
    public void deleteSysMenuByPrimaryKey(Map<String, Object> paramMap);
    
    
}
