package com.easub.bi.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.easub.bi.dto.ShopsDTO;

public interface IBIService extends Serializable{

	
	List<ShopsDTO> getShopsList(Map<String,Object> conditions) throws Exception;
	/**
	 * 按商户统计视频数
	 * @param map
	 * @return
	 */
	List getVideoShopStat(Map<String,Object> conditions) throws Exception;
	/**
	 * 按版权统计时间段内的视频数和时长（小时）
	 * @param map
	 * @return
	 */
	List getVideoCopyrightStat(String startTime,String endTime) throws Exception;
	/**
	 * 按视频类型统计时间段内的视频数
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	List getVideosSourceTypeStatCount(String startTime,String endTime) throws Exception;
	/**
	 * 按视频类型统计时间段内的分享的视频数
	 * @param conditions
	 * @return
	 */
	List getVideosSourceTypeShareStatCount(Map<String,Object> conditions) throws Exception;
	
}
