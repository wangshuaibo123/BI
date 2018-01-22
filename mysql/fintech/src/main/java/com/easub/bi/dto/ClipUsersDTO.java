package com.easub.bi.dto;

import com.fintech.platform.core.common.BaseDTO;

public class ClipUsersDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3137719747030072973L;

	
	private Long id;
	private String uuid;
	private int isMaster;//0普通用户 1机构账号
	private int userCategory;//0普通用户 1子账号 2机构账号（管理员）    普通用户的管理员类型已经取消userCategory等于2表示机构账号
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getIsMaster() {
		return isMaster;
	}
	public void setIsMaster(int isMaster) {
		this.isMaster = isMaster;
	}
	public int getUserCategory() {
		return userCategory;
	}
	public void setUserCategory(int userCategory) {
		this.userCategory = userCategory;
	}
	
}
