package com.fintech.modules.platform.sysbizlog.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysbizlog.dto.SysBizLogDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysBizLogDao
 * @description: 定义  业务日志表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysBizLogDao {
    
    /**
     * @author
     * @description: 分页查询业务日志表
     * @date 2014-10-15 16:30:13
     * @param searchParams
     * @return
     */
    public List<SysBizLogDTO> searchSysBizLogByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象业务日志表
     * @date 2014-10-15 16:30:13
     * @param searchParams
     * @return
     */
    public List<SysBizLogDTO> searchSysBizLog(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象业务日志表
     * @date 2014-10-15 16:30:13
     * @param id
     * @return
     */
    public SysBizLogDTO findSysBizLogByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象业务日志表
     * @date 2014-10-15 16:30:13
     * @param paramMap
     * @return
     */
    public int insertSysBizLog(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象业务日志表
     * @date 2014-10-15 16:30:13
     * @param paramMap
     */
    public void updateSysBizLog(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除业务日志表
     * @date 2014-10-15 16:30:13
     * @param ids
     * @return
     */ 
    public void deleteSysBizLogByPrimaryKey(Map<String, Object> paramMap);
    
    
}
