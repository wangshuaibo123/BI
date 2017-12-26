package com.fintech.modules.platform.sysdict.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysdict.dto.SysDictDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysDictDao
 * @description: 定义  数据字典 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysDictDao {
    
    /**
     * @author
     * @description: 分页查询数据字典
     * @date 2014-10-15 10:28:27
     * @param searchParams
     * @return
     */
    public List<SysDictDTO> searchSysDictByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象数据字典
     * @date 2014-10-15 10:28:27
     * @param searchParams
     * @return
     */
    public List<SysDictDTO> searchSysDict(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象数据字典
     * @date 2014-10-15 10:28:27
     * @param id
     * @return
     */
    public SysDictDTO findSysDictByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象数据字典
     * @date 2014-10-15 10:28:27
     * @param paramMap
     * @return
     */
    public int insertSysDict(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象数据字典
     * @date 2014-10-15 10:28:27
     * @param paramMap
     */
    public void updateSysDict(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除数据字典
     * @date 2014-10-15 10:28:27
     * @param ids
     * @return
     */ 
    public void deleteSysDictByPrimaryKey(Map<String, Object> paramMap);

    /**
     * 唯一性验证
     * @param code
     * @return
     */
	public String queryDictCodeIsOk(String code);
    
	
	public Map<String,String> queryDictInfoByCode(String code);
    
}
