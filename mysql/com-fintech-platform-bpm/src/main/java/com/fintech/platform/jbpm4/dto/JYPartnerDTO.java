package com.fintech.platform.jbpm4.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 * 
 * @description:定义 参与者实体类
 * @author
 * @date:2014年11月6日上午10:41:24
 */
public class JYPartnerDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	//定义 用户 最大可以接受待办任务数量的上限值 0 代表无上限
	private int parMaxTaskCunt = 0;
	//用户姓名
	private String parRealName;
	//用户ID
	private String parUserId;
	//用户编号
	private String parUserNo;
	//用户归属部门id 可以是多个
	private String parDeptId;
	//用户归属部门名称 可以是多个
	private String parDeptName;
	//用户归属部门级别 可以是多个
	private String parDeptLevel;
	//用户归属机构ID 可以是多个
	private String parOrgId;
	//用户归属机构名称 可以是多个
	private String parOrgName;
	//用户归属机构级别 可以是多个
	private String parOrgLevel;
	//用户拥有角色 ID 可以是多个
	private String parRoleId;
	//用户拥有角色 名称 可以是多个
	private String parRoleName;
	//用户级别（自动分配任务时可以使用）
	private String parUserLevel;
	
	
	public String getParUserLevel() {
		return parUserLevel;
	}
	public void setParUserLevel(String parUserLevel) {
		this.parUserLevel = parUserLevel;
	}
	public String getParRealName() {
		return parRealName;
	}
	public void setParRealName(String parRealName) {
		this.parRealName = parRealName;
	}
	public String getParUserId() {
		return parUserId;
	}
	public void setParUserId(String parUserId) {
		this.parUserId = parUserId;
	}
	public String getParUserNo() {
		return parUserNo;
	}
	public void setParUserNo(String parUserNo) {
		this.parUserNo = parUserNo;
	}
	public String getParDeptId() {
		return parDeptId;
	}
	public void setParDeptId(String parDeptId) {
		this.parDeptId = parDeptId;
	}
	public String getParDeptName() {
		return parDeptName;
	}
	public void setParDeptName(String parDeptName) {
		this.parDeptName = parDeptName;
	}
	public String getParDeptLevel() {
		return parDeptLevel;
	}
	public void setParDeptLevel(String parDeptLevel) {
		this.parDeptLevel = parDeptLevel;
	}
	public String getParOrgId() {
		return parOrgId;
	}
	public void setParOrgId(String parOrgId) {
		this.parOrgId = parOrgId;
	}
	public String getParOrgName() {
		return parOrgName;
	}
	public void setParOrgName(String parOrgName) {
		this.parOrgName = parOrgName;
	}
	public String getParOrgLevel() {
		return parOrgLevel;
	}
	public void setParOrgLevel(String parOrgLevel) {
		this.parOrgLevel = parOrgLevel;
	}
	public String getParRoleId() {
		return parRoleId;
	}
	public void setParRoleId(String parRoleId) {
		this.parRoleId = parRoleId;
	}
	public String getParRoleName() {
		return parRoleName;
	}
	public void setParRoleName(String parRoleName) {
		this.parRoleName = parRoleName;
	}
	public int getParMaxTaskCunt() {
		return parMaxTaskCunt;
	}
	public void setParMaxTaskCunt(int parMaxTaskCunt) {
		this.parMaxTaskCunt = parMaxTaskCunt;
	}
}
