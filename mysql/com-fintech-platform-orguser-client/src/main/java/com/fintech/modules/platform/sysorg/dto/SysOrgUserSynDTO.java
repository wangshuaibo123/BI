package com.fintech.modules.platform.sysorg.dto;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:SYS_ORG_USER_SYN
 *@author
 *@version 1.0,
 *@date 2015-01-28 11:11:48
 */
public class SysOrgUserSynDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**机构ID*/
	private java.lang.Long orgId;

	/**用户ID*/
	private java.lang.Long userId;

	/**岗位ID*/
	private java.lang.Long positionId;

	/**是否主部门，1是（主部门），0否（兼职部门），默认1*/
	private java.lang.String isMainOrg;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;

	/**乐观锁*/
	private java.lang.Long version;

	/**创建时间*/
	private java.sql.Timestamp createDate;

	/**orguserid*/
	private java.lang.Long orgUserId;

	/**机构名称*/
	private java.lang.String orgName;

	/**用户名称*/
	private java.lang.String userName;

	/**岗位名称*/
	private java.lang.String positionName;
	
	/**岗位编码*/
	private java.lang.String positionCode;
	
	/**父部门ID*/
	private String parentId;

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
	 *方法: 获得positionId
	 *@return: java.lang.Long  positionId
	 */
	public java.lang.Long getPositionId(){
		return this.positionId;
	}

	/**
	 *方法: 设置positionId
	 *@param: java.lang.Long  positionId
	 */
	public void setPositionId(java.lang.Long positionId){
		this.positionId = positionId;
	}

	/**
	 *方法: 获得isMainOrg
	 *@return: java.lang.String  isMainOrg
	 */
	public java.lang.String getIsMainOrg(){
		return this.isMainOrg;
	}

	/**
	 *方法: 设置isMainOrg
	 *@param: java.lang.String  isMainOrg
	 */
	public void setIsMainOrg(java.lang.String isMainOrg){
		this.isMainOrg = isMainOrg;
	}

	/**
	 *方法: 获得validateState
	 *@return: java.lang.String  validateState
	 */
	public java.lang.String getValidateState(){
		return this.validateState;
	}

	/**
	 *方法: 设置validateState
	 *@param: java.lang.String  validateState
	 */
	public void setValidateState(java.lang.String validateState){
		this.validateState = validateState;
	}

	/**
	 *方法: 获得version
	 *@return: java.lang.Long  version
	 */
	public java.lang.Long getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置version
	 *@param: java.lang.Long  version
	 */
	public void setVersion(java.lang.Long version){
		this.version = version;
	}

	/**
	 *方法: 获得createDate
	 *@return: java.sql.Timestamp  createDate
	 */
	public java.sql.Timestamp getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置createDate
	 *@param: java.sql.Timestamp  createDate
	 */
	public void setCreateDate(java.sql.Timestamp createDate){
		this.createDate = createDate;
	}

	/**
	 *方法: 获得orgUserId
	 *@return: java.lang.Long  orgUserId
	 */
	public java.lang.Long getOrgUserId(){
		return this.orgUserId;
	}

	/**
	 *方法: 设置orgUserId
	 *@param: java.lang.Long  orgUserId
	 */
	public void setOrgUserId(java.lang.Long orgUserId){
		this.orgUserId = orgUserId;
	}

	/**
	 *方法: 获得orgName
	 *@return: java.lang.String  orgName
	 */
	public java.lang.String getOrgName(){
		return this.orgName;
	}

	/**
	 *方法: 设置orgName
	 *@param: java.lang.String  orgName
	 */
	public void setOrgName(java.lang.String orgName){
		this.orgName = orgName;
	}

	/**
	 *方法: 获得userName
	 *@return: java.lang.String  userName
	 */
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置userName
	 *@param: java.lang.String  userName
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}

	/**
	 *方法: 获得positionName
	 *@return: java.lang.String  positionName
	 */
	public java.lang.String getPositionName(){
		return this.positionName;
	}

	/**
	 *方法: 设置positionName
	 *@param: java.lang.String  positionName
	 */
	public void setPositionName(java.lang.String positionName){
		this.positionName = positionName;
	}

	public java.lang.String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(java.lang.String positionCode) {
		this.positionCode = positionCode;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}