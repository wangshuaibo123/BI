package com.jy.modules.logmonitor.sysapperrorinfo.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.logmonitor.sysapperrorinfo.dto.SysAppErrorInfoDTO;
import com.jy.modules.logmonitor.sysapperrorinfo.dto.SysAppErrorLevelDetailDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysAppErrorInfoDao
 * @description: 定义  业务系统节点错误日志 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  lei
 */
@MyBatisRepository
public interface SysAppErrorInfoDao {
    
    /**
     * @author lei
     * @description: 分页查询业务系统节点错误日志
     * @date 2015-04-03 10:07:07
     * @param searchParams
     * @return
     */
    public List<SysAppErrorInfoDTO> searchSysAppErrorInfoByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author lei
     * @description:查询对象业务系统节点错误日志
     * @date 2015-04-03 10:07:07
     * @param searchParams
     * @return
     */
    public List<SysAppErrorInfoDTO> searchSysAppErrorInfo(Map<String,Object> searchParams);

    /**
     * @author lei
     * @description:查询对象业务系统节点错误日志
     * @date 2015-04-03 10:07:07
     * @param id
     * @return
     */
    public SysAppErrorInfoDTO findSysAppErrorInfoByPrimaryKey(String id);
    
    /**
     * @author lei
     * @description: 新增对象业务系统节点错误日志
     * @date 2015-04-03 10:07:07
     * @param paramMap
     * @return
     */
    public int insertSysAppErrorInfo(List<SysAppErrorInfoDTO> paramMap);
    
    /**
     * @author lei
     * @description: 更新对象业务系统节点错误日志
     * @date 2015-04-03 10:07:07
     * @param paramMap
     */
    public void updateSysAppErrorInfo(Map<String, Object> paramMap);
     
    /**
     * @author lei
     * @description: 按主键删除业务系统节点错误日志
     * @date 2015-04-03 10:07:07
     * @param ids
     * @return
     */ 
    public void deleteSysAppErrorInfoByPrimaryKey(Map<String, Object> paramMap);
    
    /**
     * @author  yongliangguo
     * @author luoyr
     * @description: 按查询条件统计月中每天错误日志 
     * @date 2015-04-03 10:07:07
     * @param searchParams 查询条件
     * @return
     */ 
    public List<SysAppErrorInfoDTO> countSysAppErrorByDay(Map<String,Object> searchParams);
    /**
     * @author luoyr
     * @description: 按查询条件统计天中每时错误日志 
     * @date 2015-04-03 10:07:07
     * @param searchParams 查询条件
     * @return
     */ 
    public List<SysAppErrorInfoDTO> countSysAppErrorByHour(Map<String,Object> searchParams);
    /**
     * @author luoyr
     * @description: 按查询条件统计天中每时错误日志 
     * @date 2015-04-03 10:07:07
     * @param searchParams 查询条件
     * @return
     */ 
    public List<SysAppErrorInfoDTO> percentSysAppErrorByHour(Map<String,Object> searchParams);
    /**
     * @author luoyr
     * @description: 按查询条件统计月中每天错误日志 
     * @date 2015-04-03 10:07:07
     * @param searchParams 查询条件
     * @return
     */ 
    public List<SysAppErrorInfoDTO> percentSysAppErrorByDay(Map<String,Object> searchParams);
    /**
     * @author lei yongliangguo
     * @deprecated: 根据关键字给错误日志设置级别
     * @date 2015-06-17
     * @param paramMap
     */
    public void updateSysAppErrorInfoLevel(Map<String, Object> paramMap);
    
    
    public void updateSysAppErrorMatched();
    
    /**
     * 根据时间的形式获取当前区域时间中产生的错误数
     * @param paramMap
     * @return
     */
    public List<SysAppErrorLevelDetailDTO> getErrorForLevelCount(Map<String, Object> paramMap);
    
}
