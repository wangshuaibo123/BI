package com.jy.modules.platform.sysprvauthpool.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:SYS_PRV_AUTH_POOL
 *@author lei
 *@version 1.0,
 *@date 2015-01-12 20:13:17
 */
public class SysPrvAuthPoolDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**USERID*/
	private java.lang.Long userid;

	/**OWNERID*/
	private java.lang.Long ownerid;

	/**ORGID*/
	private java.lang.Long orgid;

	/**TABLEID*/
	private java.lang.String tableid;

	/**BIZID*/
	private java.lang.Long bizid;

	/**STATUS*/
	private java.lang.String status;
	
	/**
	 * DATESOURCE
	 */
	private java.lang.String datesource;
	
	/**
	 * DATESOURCEID
	 */
	private java.lang.String datesourceid;
	
	

	public java.lang.String getDatesource() {
		return datesource;
	}

	public void setDatesource(java.lang.String datesource) {
		this.datesource = datesource;
	}

	public java.lang.String getDatesourceid() {
		return datesourceid;
	}

	public void setDatesourceid(java.lang.String datesourceid) {
		this.datesourceid = datesourceid;
	}

	/**
	 *方法: 获得userid
	 *@return: java.lang.Long  userid
	 */
	public java.lang.Long getUserid(){
		return this.userid;
	}

	/**
	 *方法: 设置userid
	 *@param: java.lang.Long  userid
	 */
	public void setUserid(java.lang.Long userid){
		this.userid = userid;
	}

	/**
	 *方法: 获得ownerid
	 *@return: java.lang.Long  ownerid
	 */
	public java.lang.Long getOwnerid(){
		return this.ownerid;
	}

	/**
	 *方法: 设置ownerid
	 *@param: java.lang.Long  ownerid
	 */
	public void setOwnerid(java.lang.Long ownerid){
		this.ownerid = ownerid;
	}

	/**
	 *方法: 获得orgid
	 *@return: java.lang.Long  orgid
	 */
	public java.lang.Long getOrgid(){
		return this.orgid;
	}

	/**
	 *方法: 设置orgid
	 *@param: java.lang.Long  orgid
	 */
	public void setOrgid(java.lang.Long orgid){
		this.orgid = orgid;
	}

	/**
	 *方法: 获得tableid
	 *@return: java.lang.String  tableid
	 */
	public java.lang.String getTableid(){
		return this.tableid;
	}

	/**
	 *方法: 设置tableid
	 *@param: java.lang.String  tableid
	 */
	public void setTableid(java.lang.String tableid){
		this.tableid = tableid;
	}

	/**
	 *方法: 获得bizid
	 *@return: java.lang.Long  bizid
	 */
	public java.lang.Long getBizid(){
		return this.bizid;
	}

	/**
	 *方法: 设置bizid
	 *@param: java.lang.Long  bizid
	 */
	public void setBizid(java.lang.Long bizid){
		this.bizid = bizid;
	}

	/**
	 *方法: 获得status
	 *@return: java.lang.String  status
	 */
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置status
	 *@param: java.lang.String  status
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}

}