package com.jy.modules.platform.bizauth.vmuservmorgmap.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:员工虚拟组织关系表
 *@author chen_gang
 *@version 1.0,
 *@date 2015-01-16 17:15:01
 */
public class VmuserVmorgMapDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键ID*/
	private java.lang.Long id;

	/**用户ID*/
	private java.lang.Long userId;

	/**虚拟树的Org_ID*/
	private java.lang.Long orgId;

	/**虚拟树代码*/
	private java.lang.String orgType;
	
	
	private java.lang.String userIds;
	
	/**用户名*/
	private java.lang.String userName;
	
	/**组织名称*/
	private java.lang.String orgName;
	
	private String validateState;
	
	/**
	 * 岗位名称
	 */
	private String positionCN;
	
	

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getOrgName() {
		return orgName;
	}

	public void setOrgName(java.lang.String orgName) {
		this.orgName = orgName;
	}

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
	 *方法: 获得userId
	 *@return: java.lang.Long  userId
	 */
	public java.lang.Long getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置userId
	 *@param: java.lang.Long  userId
	 */
	public void setUserId(java.lang.Long userId){
		this.userId = userId;
	}

	/**
	 *方法: 获得orgId
	 *@return: java.lang.Long  orgId
	 */
	public java.lang.Long getOrgId(){
		return this.orgId;
	}

	/**
	 *方法: 设置orgId
	 *@param: java.lang.Long  orgId
	 */
	public void setOrgId(java.lang.Long orgId){
		this.orgId = orgId;
	}

	/**
	 *方法: 获得orgType
	 *@return: java.lang.String  orgType
	 */
	public java.lang.String getOrgType(){
		return this.orgType;
	}

	/**
	 *方法: 设置orgType
	 *@param: java.lang.String  orgType
	 */
	public void setOrgType(java.lang.String orgType){
		this.orgType = orgType;
	}

	public java.lang.String getUserIds() {
		return userIds;
	}

	public void setUserIds(java.lang.String userIds) {
		this.userIds = userIds;
	}

	public String getValidateState() {
		return validateState;
	}

	public void setValidateState(String validateState) {
		this.validateState = validateState;
	}

	public String getPositionCN() {
		return positionCN;
	}

	public void setPositionCN(String positionCN) {
		this.positionCN = positionCN;
	}
	
	

}