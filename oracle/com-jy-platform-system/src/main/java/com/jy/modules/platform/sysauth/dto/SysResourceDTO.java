package com.jy.modules.platform.sysauth.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:资源管理表
 *@author chen_gang
 *@version 1.0,
 *@date 2014-10-15 10:24:37
 */
public class SysResourceDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**名称*/
	private java.lang.String resoureName;

	/**类型，url或button*/
	private java.lang.String resoureType;

	/**URL地址*/
	private java.lang.String resoureUrl;

	/**权限标识*/
	private java.lang.String permission;

	/**父ID*/
	private java.lang.String parentId;

	/**父ID串，以/分割*/
	private java.lang.String parentIds;

	/**应用ID，备用字段*/
	private java.lang.Long appId;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;

	/**乐观锁*/
	private java.lang.Long version;
	




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
	 *方法: 获得resoureName
	 *@return: java.lang.String  resoureName
	 */
	public java.lang.String getResoureName(){
		return this.resoureName;
	}

	/**
	 *方法: 设置resoureName
	 *@param: java.lang.String  resoureName
	 */
	public void setResoureName(java.lang.String resoureName){
		this.resoureName = resoureName;
	}

	/**
	 *方法: 获得resoureType
	 *@return: java.lang.String  resoureType
	 */
	public java.lang.String getResoureType(){
		return this.resoureType;
	}

	/**
	 *方法: 设置resoureType
	 *@param: java.lang.String  resoureType
	 */
	public void setResoureType(java.lang.String resoureType){
		this.resoureType = resoureType;
	}

	/**
	 *方法: 获得resoureUrl
	 *@return: java.lang.String  resoureUrl
	 */
	public java.lang.String getResoureUrl(){
		return this.resoureUrl;
	}

	/**
	 *方法: 设置resoureUrl
	 *@param: java.lang.String  resoureUrl
	 */
	public void setResoureUrl(java.lang.String resoureUrl){
		this.resoureUrl = resoureUrl;
	}

	/**
	 *方法: 获得permission
	 *@return: java.lang.String  permission
	 */
	public java.lang.String getPermission(){
		return this.permission;
	}

	/**
	 *方法: 设置permission
	 *@param: java.lang.String  permission
	 */
	public void setPermission(java.lang.String permission){
		this.permission = permission;
	}

	/**
	 *方法: 获得parentId
	 *@return: java.lang.String  parentId
	 */
	public java.lang.String getParentId(){
		return this.parentId;
	}

	/**
	 *方法: 设置parentId
	 *@param: java.lang.String  parentId
	 */
	public void setParentId(java.lang.String parentId){
		this.parentId = parentId;
	}

	/**
	 *方法: 获得parentIds
	 *@return: java.lang.String  parentIds
	 */
	public java.lang.String getParentIds(){
		return this.parentIds;
	}

	/**
	 *方法: 设置parentIds
	 *@param: java.lang.String  parentIds
	 */
	public void setParentIds(java.lang.String parentIds){
		this.parentIds = parentIds;
	}

	/**
	 *方法: 获得appId
	 *@return: java.lang.Long  appId
	 */
	public java.lang.Long getAppId(){
		return this.appId;
	}

	/**
	 *方法: 设置appId
	 *@param: java.lang.Long  appId
	 */
	public void setAppId(java.lang.Long appId){
		this.appId = appId;
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
	
}