package com.easub.clip.dto;

import java.util.Date;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:clip_video_summary_stat_detail
 *@author jiangshuncheng
 *@version 1.0,
 *@date 2018-01-26 20:01:11
 */
public class ClipVideoSummaryStatDetailDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**id*/
	private java.lang.Long id;

	/**1全部、2除了imsiss、3新浪、4政务媒体*/
	private java.lang.Integer type;

	/**云剪系统中的video中source_type*/
	private java.lang.Integer sourceType;

	/**数量*/
	private java.lang.Integer count;

	/**共享数量*/
	private java.lang.Integer shareCount;

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
	 *方法: 获得type
	 *@return: java.lang.Integer  type
	 */
	public java.lang.Integer getType(){
		return this.type;
	}

	/**
	 *方法: 设置type
	 *@param: java.lang.Integer  type
	 */
	public void setType(java.lang.Integer type){
		this.type = type;
	}

	/**
	 *方法: 获得sourceType
	 *@return: java.lang.Integer  sourceType
	 */
	public java.lang.Integer getSourceType(){
		return this.sourceType;
	}

	/**
	 *方法: 设置sourceType
	 *@param: java.lang.Integer  sourceType
	 */
	public void setSourceType(java.lang.Integer sourceType){
		this.sourceType = sourceType;
	}

	/**
	 *方法: 获得count
	 *@return: java.lang.Integer  count
	 */
	public java.lang.Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置count
	 *@param: java.lang.Integer  count
	 */
	public void setCount(java.lang.Integer count){
		this.count = count;
	}

	/**
	 *方法: 获得shareCount
	 *@return: java.lang.Integer  shareCount
	 */
	public java.lang.Integer getShareCount(){
		return this.shareCount;
	}

	/**
	 *方法: 设置shareCount
	 *@param: java.lang.Integer  shareCount
	 */
	public void setShareCount(java.lang.Integer shareCount){
		this.shareCount = shareCount;
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