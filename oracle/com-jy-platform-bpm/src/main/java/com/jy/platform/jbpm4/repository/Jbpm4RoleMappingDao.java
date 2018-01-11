package com.jy.platform.jbpm4.repository;

import java.util.List;
import java.util.Map;

import com.jy.platform.core.mybatis.MyBatisRepository;
import com.jy.platform.jbpm4.dto.Jbpm4RoleMappingDTO;
/**
 * @classname: Jbpm4RoleMappingDao
 * @description: 定义  工作流角色映射表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  JY-IT-D001
 */
@MyBatisRepository
public interface Jbpm4RoleMappingDao {
    
    /**
     * @author JY-IT-D001
     * @description: 分页查询工作流角色映射表
     * @date 2015-06-05 10:32:34
     * @param searchParams
     * @return
     */
    public List<Jbpm4RoleMappingDTO> searchJbpm4RoleMappingByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author JY-IT-D001
     * @description:查询对象工作流角色映射表
     * @date 2015-06-05 10:32:34
     * @param searchParams
     * @return
     */
    public List<Jbpm4RoleMappingDTO> searchJbpm4RoleMapping(Map<String,Object> searchParams);

    /**
     * @author JY-IT-D001
     * @description:查询对象工作流角色映射表
     * @date 2015-06-05 10:32:34
     * @param id
     * @return
     */
    public Jbpm4RoleMappingDTO findJbpm4RoleMappingByPrimaryKey(String id);
    
    /**
     * @author JY-IT-D001
     * @description: 新增对象工作流角色映射表
     * @date 2015-06-05 10:32:34
     * @param paramMap
     * @return
     */
    public int insertJbpm4RoleMapping(Map<String, Object> paramMap);
    
    /**
     * @author JY-IT-D001
     * @description: 更新对象工作流角色映射表
     * @date 2015-06-05 10:32:34
     * @param paramMap
     */
    public void updateJbpm4RoleMapping(Map<String, Object> paramMap);
     
    /**
     * @author JY-IT-D001
     * @description: 按主键删除工作流角色映射表
     * @date 2015-06-05 10:32:34
     * @param ids
     * @return
     */ 
    public void deleteJbpm4RoleMappingByPrimaryKey(Map<String, Object> paramMap);
    
    
}
