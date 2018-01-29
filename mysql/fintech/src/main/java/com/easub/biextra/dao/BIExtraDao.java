package com.easub.biextra.dao;

import java.util.List;
import java.util.Map;

import com.fintech.platform.core.mybatis.MyBatisRepository;

@MyBatisRepository
public interface BIExtraDao {

	public Integer getUserAccount(Map<String,Object> map);
	
	/**
	 * 按视频类型统计时间段内的视频数
	 * @param map
	 * @return
	 */
	public List getVideosSourceTypeStatCount(Map<String,Object> map);
	/**
	 * 按视频类型统计时间段内的分享的视频数
	 * @param map
	 * @return
	 */
	public List getVideosSourceTypeShareStatCount(Map<String,Object> map);
	/**
	 * 按条件获取视频分享数量
	 * @param map 条件
	 * @return
	 */
	Integer getVideosShareStatCount(Map<String,Object> map);
	/**
	 * 获取视频播放量
	 * @param map
	 * @return
	 */
	Long getVideoVV(Map<String,Object> map);
}
