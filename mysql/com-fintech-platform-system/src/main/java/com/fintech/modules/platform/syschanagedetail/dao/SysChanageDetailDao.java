package com.fintech.modules.platform.syschanagedetail.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.syschanagedetail.dto.SysChanageDetailDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysChanageDetailDao
 * @description: 定义  变更信息明细表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  DELL
 */
@MyBatisRepository
public interface SysChanageDetailDao {
    
    /**
     * @author DELL
     * @description: 分页查询变更信息明细表
     * @date 2016-09-09 18:08:38
     * @param searchParams
     * @return
     */
    public List<SysChanageDetailDTO> searchSysChanageDetailByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author DELL
     * @description:查询对象变更信息明细表
     * @date 2016-09-09 18:08:38
     * @param searchParams
     * @return
     */
    public List<SysChanageDetailDTO> searchSysChanageDetail(Map<String,Object> searchParams);

    /**
     * @author DELL
     * @description:查询对象变更信息明细表
     * @date 2016-09-09 18:08:38
     * @param id
     * @return
     */
    public SysChanageDetailDTO findSysChanageDetailByPrimaryKey(String id);
    
    /**
     * @author DELL
     * @description: 新增对象变更信息明细表
     * @date 2016-09-09 18:08:38
     * @param paramMap
     * @return
     */
    public int insertSysChanageDetail(Map<String, Object> paramMap);
    
    /**
     * @author DELL
     * @description: 更新对象变更信息明细表
     * @date 2016-09-09 18:08:38
     * @param paramMap
     */
    public void updateSysChanageDetail(Map<String, Object> paramMap);
     
    /**
     * @author DELL
     * @description: 按主键删除变更信息明细表
     * @date 2016-09-09 18:08:38
     * @param ids
     * @return
     */ 
    public void deleteSysChanageDetailByPrimaryKey(Map<String, Object> paramMap);

	public void updateNewVaToBizTab(Map<String, Object> paramMap);

	public void updateOldVaToBizTab(Map<String, Object> paramMap);
    
    
}
