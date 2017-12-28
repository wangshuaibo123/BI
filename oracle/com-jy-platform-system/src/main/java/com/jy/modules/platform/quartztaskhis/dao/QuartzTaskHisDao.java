package com.jy.modules.platform.quartztaskhis.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.quartztaskhis.dto.QuartzTaskHisDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: QuartzTaskHisDao
 * @description: 定义  定时任务执行轨迹表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  lei
 */
@MyBatisRepository
public interface QuartzTaskHisDao {
    
    /**
     * @author lei
     * @description: 分页查询定时任务执行轨迹表
     * @date 2014-12-24 10:12:17
     * @param searchParams
     * @return
     */
    public List<QuartzTaskHisDTO> searchQuartzTaskHisByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author lei
     * @description:查询对象定时任务执行轨迹表
     * @date 2014-12-24 10:12:17
     * @param searchParams
     * @return
     */
    public List<QuartzTaskHisDTO> searchQuartzTaskHis(Map<String,Object> searchParams);

    /**
     * @author lei
     * @description:查询对象定时任务执行轨迹表
     * @date 2014-12-24 10:12:17
     * @param id
     * @return
     */
    public QuartzTaskHisDTO findQuartzTaskHisByPrimaryKey(String id);
    
    /**
     * @author lei
     * @description: 新增对象定时任务执行轨迹表
     * @date 2014-12-24 10:12:17
     * @param paramMap
     * @return
     */
    public int insertQuartzTaskHis(Map<String, Object> paramMap);
    
    /**
     * @author lei
     * @description: 更新对象定时任务执行轨迹表
     * @date 2014-12-24 10:12:17
     * @param paramMap
     */
    public void updateQuartzTaskHis(Map<String, Object> paramMap);
     
    /**
     * @author lei
     * @description: 按主键删除定时任务执行轨迹表
     * @date 2014-12-24 10:12:17
     * @param ids
     * @return
     */ 
    public void deleteQuartzTaskHisByPrimaryKey(Map<String, Object> paramMap);
    
    
}
