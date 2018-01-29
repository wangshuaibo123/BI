package com.easub.clip.dao;

import java.util.List;
import java.util.Map;

import com.easub.clip.dto.ClipVideoCopyrightSummaryStatDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: ClipVideoCopyrightSummaryStatDao
 * @description: 定义  clip_video_copyright_summary_stat 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  jiangshuncheng
 */
@MyBatisRepository
public interface ClipVideoCopyrightSummaryStatDao {
    
    /**
     * @author jiangshuncheng
     * @description: 分页查询clip_video_copyright_summary_stat
     * @date 2018-01-25 10:51:05
     * @param searchParams
     * @return
     */
    public List<ClipVideoCopyrightSummaryStatDTO> searchClipVideoCopyrightSummaryStatByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author jiangshuncheng
     * @description:查询对象clip_video_copyright_summary_stat
     * @date 2018-01-25 10:51:05
     * @param searchParams
     * @return
     */
    public List<ClipVideoCopyrightSummaryStatDTO> searchClipVideoCopyrightSummaryStat(Map<String,Object> searchParams);

    /**
     * @author jiangshuncheng
     * @description:查询对象clip_video_copyright_summary_stat
     * @date 2018-01-25 10:51:05
     * @param id
     * @return
     */
    public ClipVideoCopyrightSummaryStatDTO findClipVideoCopyrightSummaryStatByPrimaryKey(String id);
    
    /**
     * @author jiangshuncheng
     * @description: 新增对象clip_video_copyright_summary_stat
     * @date 2018-01-25 10:51:05
     * @param paramMap
     * @return
     */
    public int insertClipVideoCopyrightSummaryStat(Map<String, Object> paramMap);
    
    /**
     * @author jiangshuncheng
     * @description: 更新对象clip_video_copyright_summary_stat
     * @date 2018-01-25 10:51:05
     * @param paramMap
     */
    public void updateClipVideoCopyrightSummaryStat(Map<String, Object> paramMap);
     
    /**
     * @author jiangshuncheng
     * @description: 按主键删除clip_video_copyright_summary_stat
     * @date 2018-01-25 10:51:05
     * @param ids
     * @return
     */ 
    public void deleteClipVideoCopyrightSummaryStatByPrimaryKey(Map<String, Object> paramMap);
    
    public void deleteClipVideoCopyrightSummaryStat(Map<String, Object> paramMap);
    
    
    
}
