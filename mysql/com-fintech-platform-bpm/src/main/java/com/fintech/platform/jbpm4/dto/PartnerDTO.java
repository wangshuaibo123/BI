package com.fintech.platform.jbpm4.dto;

import java.io.Serializable;


/**
 * 查询 参与者 参数信息
 * @author
 *
 */
public class PartnerDTO implements Serializable{
	//定义选择参与者的sqlid
	private String partnerSqlId;
	
	
	//登录人的 userId
	private String userId;
	//登录人 登录后所选部门
	private String deptId;
	//操作信息
	private String operate;
	//选人规则
	private String ruleInfo;
	//
	private String deptName;
	
	private String realdName;
	
	private String userName;
	
	private String branchLevel;
	
	private String ids;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getRuleInfo() {
		return ruleInfo;
	}
	public void setRuleInfo(String ruleInfo) {
		this.ruleInfo = ruleInfo;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getRealdName() {
		return realdName;
	}
	public void setRealdName(String realdName) {
		this.realdName = realdName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBranchLevel() {
		return branchLevel;
	}
	public void setBranchLevel(String branchLevel) {
		this.branchLevel = branchLevel;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getPartnerSqlId() {
		return partnerSqlId;
	}
	public void setPartnerSqlId(String partnerSqlId) {
		this.partnerSqlId = partnerSqlId;
	}
	
}
