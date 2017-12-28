package com.jy.modules.quartzJob.qrtzextlog.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.quartzJob.qrtzextlog.dto.QrtzExtLogDTO;

import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: QrtzExtLogDao
 * @description: 定义  QRTZ_EXT_LOG 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  dell
 */
@MyBatisRepository
public interface QrtzExtLogDao {
    
    /**
     * @author dell
     * @description: 分页查询QRTZ_EXT_LOG
     * @date 2016-11-15 16:08:47
     * @param searchParams
     * @return
     */
    public List<QrtzExtLogDTO> searchQrtzExtLogByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author dell
     * @description:查询对象QRTZ_EXT_LOG
     * @date 2016-11-15 16:08:47
     * @param id
     * @return
     */
    public QrtzExtLogDTO findQrtzExtLogByPrimaryKey(String id);
    
    /**
     * @author dell
     * @description: 新增对象QRTZ_EXT_LOG
     * @date 2016-11-15 16:08:47
     * @param paramMap
     * @return
     */
    public int insertQrtzExtLog(Map<String, Object> paramMap);
    
    /**
     * @author dell
     * @description: 更新对象QRTZ_EXT_LOG（结束）
     * @date 2016-11-15 16:08:47
     * @param paramMap
     */
    public int updateQrtzExtLogFinish(Map<String, Object> paramMap);
    
    
    /**
     * 查询配置的这个定时任务是否记录日志
     * @param paramMap
     * @return
     */
    public Map<String, Object> getQrtzTriggersWriteLogByPrimaryKey(Map<String, Object> paramMap);
}
