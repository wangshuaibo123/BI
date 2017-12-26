package com.fintech.modules.platform.systemplate.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.systemplate.dto.SysTemplateDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysTemplateDao
 * @description: 定义  模板 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysTemplateDao {
    
    /**
     * @author
     * @description: 分页查询模板
     * @date 2014-10-27 14:30:25
     * @param searchParams
     * @return
     */
    public List<SysTemplateDTO> searchSysTemplateByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象模板
     * @date 2014-10-27 14:30:25
     * @param searchParams
     * @return
     */
    public List<SysTemplateDTO> searchSysTemplate(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象模板
     * @date 2014-10-27 14:30:25
     * @param id
     * @return
     */
    public SysTemplateDTO findSysTemplateByPrimaryKey(String id);
    
    /**Description: 为了更新templatecontent字段，需要锁定该行数据,慎用
     * Create Date: 2014年10月28日下午3:01:56<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param id
     * @return
     */
    public SysTemplateDTO findSysTemplateByPrimaryKeyForUpdate(String id);
    
    /**
     * @author
     * @description: 新增对象模板
     * @date 2014-10-27 14:30:25
     * @param paramMap
     * @return
     */
    public int insertSysTemplate(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象模板
     * @date 2014-10-27 14:30:25
     * @param paramMap
     */
    public void updateSysTemplate(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除模板
     * @date 2014-10-27 14:30:25
     * @param ids
     * @return
     */ 
    public void deleteSysTemplateByPrimaryKey(Map<String, Object> paramMap);
    
    public void updateSysTemplateContent(SysTemplateDTO dto);
}
