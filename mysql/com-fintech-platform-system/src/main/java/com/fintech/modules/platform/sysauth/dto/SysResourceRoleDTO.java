package com.fintech.modules.platform.sysauth.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:资源管理表
 *@author
 *@version 1.0,
 *@date 2014-10-15 10:24:37
 */
public class SysResourceRoleDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;


	private java.lang.Long roleId;
	
	private java.lang.Long resoureId;

	/**类型，url或button*/
	private java.lang.String resoureType;

	/**URL地址*/
	private java.lang.String resoureUrl;

	/**权限标识*/
	private java.lang.String permission;


	private java.lang.String roleCode;
	
	public java.lang.Long getRoleId() {
		return roleId;
	}


	public void setRoleId(java.lang.Long roleId) {
		this.roleId = roleId;
	}


	public java.lang.Long getResoureId() {
		return resoureId;
	}


	public void setResoureId(java.lang.Long resoureId) {
		this.resoureId = resoureId;
	}


	public java.lang.String getResoureType() {
		return resoureType;
	}


	public void setResoureType(java.lang.String resoureType) {
		this.resoureType = resoureType;
	}


	public java.lang.String getResoureUrl() {
		return resoureUrl;
	}


	public void setResoureUrl(java.lang.String resoureUrl) {
		this.resoureUrl = resoureUrl;
	}


	public java.lang.String getPermission() {
		return permission;
	}


	public void setPermission(java.lang.String permission) {
		this.permission = permission;
	}


	public java.lang.String getRoleCode() {
		return roleCode;
	}


	public void setRoleCode(java.lang.String roleCode) {
		this.roleCode = roleCode;
	}


	
}