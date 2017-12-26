package com.fintech.modules.logmonitor.sysappftpinfo.dao;


import com.fintech.modules.logmonitor.sysappftpinfo.dto.SysAppFtpInfoDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;

import java.util.List;
import java.util.Map;
/**
 * @classname: SysAppFtpInfoDao
 * @description: 定义  业务系统节点FTP配置表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  lei
 */
@MyBatisRepository
public interface SysAppFtpInfoDao {
    
    /**
     * @author lei
     * @description: 分页查询业务系统节点FTP配置表
     * @date 2015-04-03 10:06:17
     * @param searchParams
     * @return
     */
    public List<SysAppFtpInfoDTO> searchSysAppFtpInfoByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author lei
     * @description:查询对象业务系统节点FTP配置表
     * @date 2015-04-03 10:06:17
     * @param searchParams
     * @return
     */
    public List<SysAppFtpInfoDTO> searchSysAppFtpInfo(Map<String, Object> searchParams);

    /**
     * @author lei
     * @description:查询对象业务系统节点FTP配置表
     * @date 2015-04-03 10:06:17
     * @param id
     * @return
     */
    public SysAppFtpInfoDTO findSysAppFtpInfoByPrimaryKey(String id);
    
    /**
     * @author lei
     * @description: 新增对象业务系统节点FTP配置表
     * @date 2015-04-03 10:06:17
     * @param paramMap
     * @return
     */
    public int insertSysAppFtpInfo(Map<String, Object> paramMap);
    
    /**
     * @author lei
     * @description: 更新对象业务系统节点FTP配置表
     * @date 2015-04-03 10:06:17
     * @param paramMap
     */
    public void updateSysAppFtpInfo(Map<String, Object> paramMap);
     
    /**
     * @author lei
     * @description: 按主键删除业务系统节点FTP配置表
     * @date 2015-04-03 10:06:17
     * @param ids
     * @return
     */ 
    public void deleteSysAppFtpInfoByPrimaryKey(Map<String, Object> paramMap);
    
    
}
