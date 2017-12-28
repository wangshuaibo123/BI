package com.jy.modules.platform.dataprv.sysprvauthresult.dto;

import com.jy.platform.core.common.BaseDTO;

/**
 * @Description:数据权限授权结果表
 * @author wangxz
 * @version 1.0,
 * @date 2014-10-18 16:06:51
 */
public class SysPrvAuthResultDTO extends BaseDTO {

	@Override
	public String toString() {
		return "SysPrvAuthResultDTO [userIdFrom=" + userIdFrom + ", userIdTo="
				+ userIdTo + ", insertFrom=" + insertFrom + "]";
	}

	private static final long serialVersionUID = 1L;

	/** 主键 */
	private java.lang.Long id;

	/** 授权用户ID */
	private java.lang.Long userIdFrom;

	/** 被授权用户ID */
	private java.lang.Long userIdTo;

	/** 有效性 */
	private java.lang.String validateState;

	private java.lang.String insertFrom;

	public java.lang.String getInsertFrom() {
		return insertFrom;
	}

	public void setInsertFrom(java.lang.String insertFrom) {
		this.insertFrom = insertFrom;
	}

	/**
	 * 方法: 获得id
	 * 
	 * @return: java.lang.Long id
	 */
	public java.lang.Long getId() {
		return this.id;
	}

	/**
	 * 方法: 设置id
	 * 
	 * @param: java.lang.Long id
	 */
	public void setId(java.lang.Long id) {
		this.id = id;
	}

	/**
	 * 方法: 获得userIdFrom
	 * 
	 * @return: java.lang.Long userIdFrom
	 */
	public java.lang.Long getUserIdFrom() {
		return this.userIdFrom;
	}

	/**
	 * 方法: 设置userIdFrom
	 * 
	 * @param: java.lang.Long userIdFrom
	 */
	public void setUserIdFrom(java.lang.Long userIdFrom) {
		this.userIdFrom = userIdFrom;
	}

	/**
	 * 方法: 获得userIdTo
	 * 
	 * @return: java.lang.Long userIdTo
	 */
	public java.lang.Long getUserIdTo() {
		return this.userIdTo;
	}

	/**
	 * 方法: 设置userIdTo
	 * 
	 * @param: java.lang.Long userIdTo
	 */
	public void setUserIdTo(java.lang.Long userIdTo) {
		this.userIdTo = userIdTo;
	}

	/**
	 * 方法: 获得validateState
	 * 
	 * @return: java.lang.String validateState
	 */
	public java.lang.String getValidateState() {
		return this.validateState;
	}

	/**
	 * 方法: 设置validateState
	 * 
	 * @param: java.lang.String validateState
	 */
	public void setValidateState(java.lang.String validateState) {
		this.validateState = validateState;
	}

}