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
	 * 按版权(海外、国内两种类型)统计时间段内的视频数和时长（小时）
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
	 * 按条件获取视频数量
	 * @param map
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
	 * 按视频类型统计时间段内的分享的视频数
	 * @param conditions
	 * @return
	 */
	List getVideosSourceTypeShareStatCount(Map<String,Object> conditions) throws Exception;
	
	
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
}
