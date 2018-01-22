package com.easub.bi.dao;

import java.util.List;
import java.util.Map;

import com.fintech.platform.core.mybatis.MyBatisRepository;

@MyBatisRepository
public interface BIDao {

	List getShopsList(Map<String,Object> map);
	
	/**
	 * 按商户统计视频数
	 * @param map
	 * @return
	 */
	List getVideoShopStat(Map<String,Object> map);
	/**
	 * 按版权统计时间段内的视频数和时长（小时）
	 * @param map
	 * @return
	 */
	List getVideoCopyrightStat(Map<String,Object> map);
	
	
	/**
	 * 按条件获取视频数量
	 * @param map 条件
	 * @return
	 */
	Integer getVideosStatCount(Map<String,Object> map);
	/**
	 * 按条件获取视频分享数量
	 * @param map 条件
	 * @return
	 */
	Integer getVideosShareStatCount(Map<String,Object> map);
	
	/**
	 * 按视频类型统计时间段内的视频数
	 * @param map
	 * @return
	 */
	List getVideosSourceTypeStatCount(Map<String,Object> map);
	/**
	 * 按视频类型统计时间段内的分享的视频数
	 * @param map
	 * @return
	 */
	List getVideosSourceTypeShareStatCount(Map<String,Object> map);
	
	/**
	 * 本地上传素材量
	 * @param map
	 * @return
	 */
	List getUploadMaterialsStatCount(Map<String,Object> map);
	/**
	 * 本地上传素材活跃用户
	 * @param map
	 * @return
	 */
	List getUploadMaterialsActiveUserStatCount(Map<String,Object> map);
	/**
	 * 获取视频播放量
	 * @param map
	 * @return
	 */
	Integer getVideoVV(Map<String,Object> map);
	
	/**
	 * 按视频来源和时间分组统计视频数量
	 * @param map
	 * @return List 
	 */
	List<Map<String,Object>> getVideoSourceTypeCreateTimeCount(Map<String,Object> map);
	/**
	 * 按视频来源和时间分组统计视频分享数量
	 * @param map
	 * @return List
	 */
	List<Map<String,Object>> getVideoShareSourceTypeCreateTimeCount(Map<String,Object> map);
}
