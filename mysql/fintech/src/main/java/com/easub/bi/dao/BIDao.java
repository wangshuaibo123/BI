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
	
	
}
