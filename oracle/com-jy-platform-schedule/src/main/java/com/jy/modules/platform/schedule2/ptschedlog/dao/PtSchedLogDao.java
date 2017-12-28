package com.jy.modules.platform.schedule2.ptschedlog.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.schedule2.ptschedlog.dto.PtSchedLogDTO;

import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: PtSchedLogDao
 * @description: 定义  pt_sched_log 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  JY-IT-D001
 */
@MyBatisRepository
public interface PtSchedLogDao {
    
    /**
     * @author JY-IT-D001
     * @description: 分页查询pt_sched_log
     * @date 2017-02-07 16:22:26
     * @param searchParams
     * @return
     */
    public List<PtSchedLogDTO> searchPtSchedLogByPaging(Map<String, Object> searchParams) ;

    /**
     * @author JY-IT-D001
     * @description: 新增对象pt_sched_log
     * @date 2017-02-07 16:22:26
     * @param paramMap
     * @return
     */
    public int insertPtSchedLog(Map<String, Object> paramMap);
    
    /**
     * @author JY-IT-D001
     * @description: 更新对象pt_sched_log
     * @date 2017-02-07 16:22:26
     * @param paramMap
     */
    public void updatePtSchedLog(Map<String, Object> paramMap);
    
    
}
