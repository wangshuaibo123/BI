package com.jy.platform.api.ldap;

public class LdapDepartmentVo {
	
	/*****机构ID*****/
	private String orgId;
	
	/*****机构名称*****/
	private String orgName;
	
	/*****机构地址*****/
	private String address;
	
	/*****联系电话*****/
	private String phone;
	/*****机构编码*****/
	private String code;
	
	/*****上级机构id 若无则为空*****/
	private String parentId;
	
	
	/*****机构ID*****/
	public String getOrgId() {
		return orgId;
	}
	/*****机构ID*****/
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/*****机构名称*****/
	public String getOrgName() {
		return orgName;
	}
	/*****机构名称*****/
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/*****机构地址*****/
	public String getAddress() {
		return address;
	}
	/*****机构地址*****/
	public void setAddress(String address) {
		this.address = address;
	}
	
	/*****上级机构id 若无则为空*****/
	public String getParentId() {
		return parentId;
	}
	/*****上级机构id 若无则为空*****/
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/*****联系电话*****/
	public String getPhone() {
		return phone;
	}
	/*****联系电话*****/
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/*****机构编码*****/
	public String getCode() {
		return code;
	}
	/*****机构编码*****/
	public void setCode(String code) {
		this.code = code;
	}
	
	

}
