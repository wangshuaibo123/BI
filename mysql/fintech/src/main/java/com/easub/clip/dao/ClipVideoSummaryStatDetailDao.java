package com.easub.clip.dao;

import java.util.List;
import java.util.Map;

import com.easub.clip.dto.ClipVideoSummaryStatDetailDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: ClipVideoSummaryStatDetailDao
 * @description: 定义  clip_video_summary_stat_detail 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  jiangshuncheng
 */
@MyBatisRepository
public interface ClipVideoSummaryStatDetailDao {
    
    /**
     * @author jiangshuncheng
     * @description: 分页查询clip_video_summary_stat_detail
     * @date 2018-01-26 20:01:12
     * @param searchParams
     * @return
     */
    public List<ClipVideoSummaryStatDetailDTO> searchClipVideoSummaryStatDetailByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author jiangshuncheng
     * @description:查询对象clip_video_summary_stat_detail
     * @date 2018-01-26 20:01:12
     * @param searchParams
     * @return
     */
    public List<ClipVideoSummaryStatDetailDTO> searchClipVideoSummaryStatDetail(Map<String,Object> searchParams);

    /**
     * @author jiangshuncheng
     * @description:查询对象clip_video_summary_stat_detail
     * @date 2018-01-26 20:01:12
     * @param id
     * @return
     */
    public ClipVideoSummaryStatDetailDTO findClipVideoSummaryStatDetailByPrimaryKey(String id);
    
    /**
     * @author jiangshuncheng
     * @description: 新增对象clip_video_summary_stat_detail
     * @date 2018-01-26 20:01:12
     * @param paramMap
     * @return
     */
    public int insertClipVideoSummaryStatDetail(Map<String, Object> paramMap);
    
    /**
     * @author jiangshuncheng
     * @description: 更新对象clip_video_summary_stat_detail
     * @date 2018-01-26 20:01:12
     * @param paramMap
     */
    public void updateClipVideoSummaryStatDetail(Map<String, Object> paramMap);
     
    /**
     * @author jiangshuncheng
     * @description: 按主键删除clip_video_summary_stat_detail
     * @date 2018-01-26 20:01:12
     * @param ids
     * @return
     */ 
    public void deleteClipVideoSummaryStatDetailByPrimaryKey(Map<String, Object> paramMap);
    
    public void deleteClipVideoSummaryStatDetail(Map<String, Object> paramMap);
    
    public void batchInsert(Map<String, Object> paramMap);
    
}
