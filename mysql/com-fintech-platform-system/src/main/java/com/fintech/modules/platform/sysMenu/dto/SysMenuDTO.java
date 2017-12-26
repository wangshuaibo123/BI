package com.fintech.modules.platform.sysMenu.dto;

import java.util.Date;

import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.mybatis.MyBatisDomain;
/**
 *@Description:菜单管理表
 *@author
 *@version 1.0,
 *@date 2014-10-14 20:53:03
 */
@MyBatisDomain
public class SysMenuDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;
	//菜单的层级 1,2,3
	private Long menuLevel;
	//菜单是三级时 的 其第一级 parentId
	private String parentId1;
	/**ID*/
	private java.lang.Long id;

	/**菜单编码*/
	private java.lang.String menuCode;

	/**菜单名称*/
	private java.lang.String menuName;

	/**菜单图标*/
	private java.lang.String menuIcon;

	/**菜单URL*/
	private java.lang.String menuUrl;

	/**父菜单ID*/
	private java.lang.String parentId;

	/**排序*/
	private java.lang.String orderBy;

	/**VALIDATE_STATE*/
	private java.lang.String validateState;

	/**系统ID，备用*/
	private java.lang.Long appId;

	/**乐观锁*/
	private java.lang.Long version;
	
	/**资源*/
	private java.lang.Long resourceId;

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
	 *方法: 获得menuCode
	 *@return: java.lang.String  menuCode
	 */
	public java.lang.String getMenuCode(){
		return this.menuCode;
	}

	/**
	 *方法: 设置menuCode
	 *@param: java.lang.String  menuCode
	 */
	public void setMenuCode(java.lang.String menuCode){
		this.menuCode = menuCode;
	}

	/**
	 *方法: 获得menuName
	 *@return: java.lang.String  menuName
	 */
	public java.lang.String getMenuName(){
		return this.menuName;
	}

	/**
	 *方法: 设置menuName
	 *@param: java.lang.String  menuName
	 */
	public void setMenuName(java.lang.String menuName){
		this.menuName = menuName;
	}

	/**
	 *方法: 获得menuIcon
	 *@return: java.lang.String  menuIcon
	 */
	public java.lang.String getMenuIcon(){
		return this.menuIcon;
	}

	/**
	 *方法: 设置menuIcon
	 *@param: java.lang.String  menuIcon
	 */
	public void setMenuIcon(java.lang.String menuIcon){
		this.menuIcon = menuIcon;
	}

	/**
	 *方法: 获得menuUrl
	 *@return: java.lang.String  menuUrl
	 */
	public java.lang.String getMenuUrl(){
		return this.menuUrl;
	}

	/**
	 *方法: 设置menuUrl
	 *@param: java.lang.String  menuUrl
	 */
	public void setMenuUrl(java.lang.String menuUrl){
		this.menuUrl = menuUrl;
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
	 *方法: 获得orderBy
	 *@return: java.lang.String  orderBy
	 */
	public java.lang.String getOrderBy(){
		return this.orderBy;
	}

	/**
	 *方法: 设置orderBy
	 *@param: java.lang.String  orderBy
	 */
	public void setOrderBy(java.lang.String orderBy){
		this.orderBy = orderBy;
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
	
	public static long generatorVersion(){
		return new Date().getTime();
	}

	public java.lang.Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(java.lang.Long resourceId) {
		this.resourceId = resourceId;
	}
	public Long getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Long menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getParentId1() {
		return parentId1;
	}

	public void setParentId1(String parentId1) {
		this.parentId1 = parentId1;
	}
}