package com.easub.clip.dao;

import java.util.List;
import java.util.Map;

import com.easub.clip.dto.ClipVideoSummaryStatDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: ClipVideoSummaryStatDao
 * @description: 定义  clip_video_summary_stat 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  jiangshuncheng
 */
@MyBatisRepository
public interface ClipVideoSummaryStatDao {
    
    /**
     * @author jiangshuncheng
     * @description: 分页查询clip_video_summary_stat
     * @date 2018-01-24 11:48:49
     * @param searchParams
     * @return
     */
    public List<ClipVideoSummaryStatDTO> searchClipVideoSummaryStatByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author jiangshuncheng
     * @description:查询对象clip_video_summary_stat
     * @date 2018-01-24 11:48:49
     * @param searchParams
     * @return
     */
    public List<ClipVideoSummaryStatDTO> searchClipVideoSummaryStat(Map<String,Object> searchParams);

    /**
     * @author jiangshuncheng
     * @description:查询对象clip_video_summary_stat
     * @date 2018-01-24 11:48:49
     * @param id
     * @return
     */
    public ClipVideoSummaryStatDTO findClipVideoSummaryStatByPrimaryKey(String id);
    
    /**
     * @author jiangshuncheng
     * @description: 新增对象clip_video_summary_stat
     * @date 2018-01-24 11:48:49
     * @param paramMap
     * @return
     */
    public int insertClipVideoSummaryStat(Map<String, Object> paramMap);
    
    /**
     * @author jiangshuncheng
     * @description: 更新对象clip_video_summary_stat
     * @date 2018-01-24 11:48:49
     * @param paramMap
     */
    public void updateClipVideoSummaryStat(Map<String, Object> paramMap);
     
    /**
     * @author jiangshuncheng
     * @description: 按主键删除clip_video_summary_stat
     * @date 2018-01-24 11:48:49
     * @param ids
     * @return
     */ 
    public void deleteClipVideoSummaryStatByPrimaryKey(Map<String, Object> paramMap);
    
    public void deleteClipVideoSummaryStat(Map<String, Object> paramMap);
    
    public void batchInsert(Map<String, Object> paramMap);
    
}
