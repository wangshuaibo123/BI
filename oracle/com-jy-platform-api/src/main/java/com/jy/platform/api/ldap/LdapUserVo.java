package com.jy.platform.api.ldap;

public class LdapUserVo {
	/*****用户ID*****/
	private String userId;
	/*****用户中文姓名*****/
	private String name;
	/*****用户登录名*****/
	private String loginname;
	/*****用户密码*****/
	private String password;
	/*****email*****/
	private String email;
	/*****手机*****/
	private String mobile;
	/*****电话*****/
	private String phone;
	/*****所属机构ID*****/
	private String orgId;
	/*****员工号*****/
	private String code;
	/*****联系地址*****/
	private String address;
	/*****性别 男 女*****/
	private String sex;
	
	/*****用户ID*****/
	public String getUserId() {
		return userId;
	}
	/*****用户ID*****/
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/*****员工号*****/
	public String getCode() {
		return code;
	}
	/*****员工号*****/
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

}
