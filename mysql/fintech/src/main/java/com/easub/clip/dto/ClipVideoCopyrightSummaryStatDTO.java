package com.easub.clip.dto;

import java.util.Date;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:clip_video_copyright_summary_stat
 *@author jiangshuncheng
 *@version 1.0,
 *@date 2018-01-25 10:52:59
 */
public class ClipVideoCopyrightSummaryStatDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**id*/
	private java.lang.Long id;

	/**国外数量*/
	private java.lang.Integer abroadCount;

	/**国外视频长度，秒*/
	private Double abroadLength;

	/**国内数量*/
	private java.lang.Integer homeCount;

	/**国内视频长度，秒*/
	private Double homeLength;

	/**汇总日期*/
	private Date summaryDate;

	/**汇总日期毫秒数*/
	private java.lang.Long summaryDateMillisecond;

	/**
	 *方法: 获得id
	 *@return: java.lang.Long  id
	 */
	public java.lang.Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置id
	 *@param: java.lang.Long  id
	 */
	public void setId(java.lang.Long id){
		this.id = id;
	}

	/**
	 *方法: 获得abroadCount
	 *@return: java.lang.Integer  abroadCount
	 */
	public java.lang.Integer getAbroadCount(){
		return this.abroadCount;
	}

	/**
	 *方法: 设置abroadCount
	 *@param: java.lang.Integer  abroadCount
	 */
	public void setAbroadCount(java.lang.Integer abroadCount){
		this.abroadCount = abroadCount;
	}

	/**
	 *方法: 获得abroadLength
	 *@return: Double  abroadLength
	 */
	public Double getAbroadLength(){
		return this.abroadLength;
	}

	/**
	 *方法: 设置abroadLength
	 *@param: Double  abroadLength
	 */
	public void setAbroadLength(Double abroadLength){
		this.abroadLength = abroadLength;
	}

	/**
	 *方法: 获得homeCount
	 *@return: java.lang.Integer  homeCount
	 */
	public java.lang.Integer getHomeCount(){
		return this.homeCount;
	}

	/**
	 *方法: 设置homeCount
	 *@param: java.lang.Integer  homeCount
	 */
	public void setHomeCount(java.lang.Integer homeCount){
		this.homeCount = homeCount;
	}

	/**
	 *方法: 获得homeLength
	 *@return: Double  homeLength
	 */
	public Double getHomeLength(){
		return this.homeLength;
	}

	/**
	 *方法: 设置homeLength
	 *@param: Double  homeLength
	 */
	public void setHomeLength(Double homeLength){
		this.homeLength = homeLength;
	}

	/**
	 *方法: 获得summaryDate
	 *@return: Date  summaryDate
	 */
	public Date getSummaryDate(){
		return this.summaryDate;
	}

	/**
	 *方法: 设置summaryDate
	 *@param: Date  summaryDate
	 */
	public void setSummaryDate(Date summaryDate){
		this.summaryDate = summaryDate;
	}

	/**
	 *方法: 获得summaryDateMillisecond
	 *@return: java.lang.Long  summaryDateMillisecond
	 */
	public java.lang.Long getSummaryDateMillisecond(){
		return this.summaryDateMillisecond;
	}

	/**
	 *方法: 设置summaryDateMillisecond
	 *@param: java.lang.Long  summaryDateMillisecond
	 */
	public void setSummaryDateMillisecond(java.lang.Long summaryDateMillisecond){
		this.summaryDateMillisecond = summaryDateMillisecond;
	}

}