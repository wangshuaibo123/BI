package com.jy.platform.api.org;

import java.util.List;

/**<pre>
 * 类名中文描述:
 *
 * 基本操作功能:
 *
 * Module ID  : com-jy-platform-api 
 *
 * Create Date：2014年11月6日 下午2:01:32
 * 
 * CopyRight  :  Copyright(C) 2014-xxxx  捷越联合 <br/>
 * 
 * @since 0.1
 * @version: 0.1
 * @author <a href="mailto:chengyangyu@jieyuechina.com">cyy</a>
 * 
 * UserInfo properties:
 * 	private static final long serialVersionUID = 1L;
	private Long userId;		用户id
	private String userName;	用户名称
	private String userNo;		用户编码
	private String loginName;	登陆名称
	private Long orgId;			机构id
	private String orgName;		机构名称
	private String salt;		盐值
	private String password;	密码
	private List<OrgPositionInfo> orgPositionInfos;	机构岗位信息
 *
 * Change History Log
 * --------------------------------------------------------------------------------------------------------------
 * Date	      | Version | Author	   | Description			              
 * --------------------------------------------------------------------------------------------------------------
 * 2014年11月6日 | 0.1     | cyy| CREATE THE JAVA FILE: UserInfo.java.
 * --------------------------------------------------------------------------------------------------------------
 *
 * --------------------------------------------------------------------------------------------------------------
 *
 * </pre>
 */
public class UserInfo implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long userId;

	private String userName;
	private String userNo;
	private String loginName;
	private Long orgId;

	private String orgName;

	private String salt;
	
	private String password;
	
	private String email;
	
	private List<OrgPositionInfo> orgPositionInfos;
	
	private String loginDate; //登陆日期
	
	private String loginTime; //登陆时间
	
	private String orgParentId;//父组织ID
	
	private String orgParentName;//父组织名称
	public String getOrgParentId() {
		return orgParentId;
	}
	public void setOrgParentId(String orgParentId) {
		this.orgParentId = orgParentId;
	}
	public String getOrgParentName() {
		return orgParentName;
	}
	public void setOrgParentName(String orgParentName) {
		this.orgParentName = orgParentName;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<OrgPositionInfo> getOrgPositionInfos() {
		return orgPositionInfos;
	}
	public void setOrgPositionInfos(List<OrgPositionInfo> orgPositionInfos) {
		this.orgPositionInfos = orgPositionInfos;
	}
	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userName=" + userName
				+ ", userNo=" + userNo + ", loginName=" + loginName
				+ ", orgId=" + orgId + ", orgName=" + orgName + ", salt="
				+ salt + ", loginDate=" + loginDate + ", loginTime="
				+ loginTime + ", orgParentId=" + orgParentId
				+ ", orgParentName=" + orgParentName + "]";
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
