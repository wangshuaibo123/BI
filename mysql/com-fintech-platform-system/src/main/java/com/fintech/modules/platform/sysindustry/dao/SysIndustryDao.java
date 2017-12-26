package com.fintech.modules.platform.sysindustry.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysindustry.dto.SysIndustryDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysIndustryDao
 * @description: 定义  sys_industry 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysIndustryDao {
    
    /**
     * @author
     * @description: 分页查询sys_industry
     * @date 2014-12-04 14:03:34
     * @param searchParams
     * @return
     */
    public List<SysIndustryDTO> searchSysIndustryByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象sys_industry
     * @date 2014-12-04 14:03:34
     * @param searchParams
     * @return
     */
    public List<SysIndustryDTO> searchSysIndustry(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象sys_industry
     * @date 2014-12-04 14:03:34
     * @param id
     * @return
     */
    public SysIndustryDTO findSysIndustryByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象sys_industry
     * @date 2014-12-04 14:03:34
     * @param paramMap
     * @return
     */
    public int insertSysIndustry(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象sys_industry
     * @date 2014-12-04 14:03:34
     * @param paramMap
     */
    public void updateSysIndustry(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除sys_industry
     * @date 2014-12-04 14:03:34
     * @param ids
     * @return
     */ 
    public void deleteSysIndustryByPrimaryKey(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description:查询对象sys_industry
     * @date 2014-12-04 14:03:34
     * @param searchParams
     * @return
     */
    public List<Map> searchIndustries(Map<String,Object> searchParams);
    
    /**
     * @author
     * @description:查询对象sys_industry
     * @date 2014-12-04 14:03:34
     * @param searchParams
     * @return
     */
    public List<Map> searchPositionsOfSameIndustryByID(Map<String,Object> searchParams);
    

	/**
	 * 根据行业ID查询其下的职位
	 * @param id
	 * @return
	 * @throws Exception
	 */
    public List<Map> queryPositionByIndustry(String defaultCode);
    
	/**
	 * 根据行业编码查询其下的职位
	 * @param id
	 * @return
	 * @throws Exception
	 */
    public List<Map> getPositionsByIndustry(Map<String,Object> searchParams);
    
}
