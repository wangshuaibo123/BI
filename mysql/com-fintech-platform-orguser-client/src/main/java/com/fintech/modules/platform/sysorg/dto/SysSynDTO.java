package com.fintech.modules.platform.sysorg.dto;

import java.util.Date;

import com.fintech.platform.tools.common.DateUtil;
/**<pre>
 * 类名中文描述:
 *
 * 基本操作功能:待同步的数据类
 *
 * Module ID  : com-pt-platform-orguser-client 
 *
 * Create Date：2015年1月26日 下午7:35:48
 * 
 * 
 * @since 0.1
 * @version: 0.1
 * @author 
 *
 * Change History Log
 * --------------------------------------------------------------------------------------------------------------
 * Date	      | Version | Author	   | Description			              
 * --------------------------------------------------------------------------------------------------------------
 * 2015年1月26日 | 0.1     | cyy| CREATE THE JAVA FILE: SysSynDTO.java.
 * --------------------------------------------------------------------------------------------------------------
 *
 * --------------------------------------------------------------------------------------------------------------
 *
 * </pre>
 */
@SuppressWarnings("all")
public class SysSynDTO{

	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;

	/**批次号*/
	private java.lang.Long version;
	
	/**创建时间*/
	private java.sql.Timestamp createDate;
	
	private int orguserSize;
	
	private int orgSize;
	
	private int userSize;
	
	private int positionSize;
	
	private String rowMsg;
	
	
	
	public java.lang.Long getId() {
		return version;
	}

	public java.lang.Long getVersion() {
		return version;
	}

	public void setVersion(java.lang.Long version) {
		this.version = version;
	}

	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}
	
	public java.lang.String getCreateDateFmt(){
		return DateUtil.format(new Date(this.createDate.getTime()), "yyyy-MM-dd HH:mm:ss");
	}

	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}

	public int getOrguserSize() {
		return orguserSize;
	}

	public void setOrguserSize(int orguserSize) {
		this.orguserSize = orguserSize;
	}

	public int getOrgSize() {
		return orgSize;
	}

	public void setOrgSize(int orgSize) {
		this.orgSize = orgSize;
	}

	public int getUserSize() {
		return userSize;
	}

	public void setUserSize(int userSize) {
		this.userSize = userSize;
	}

	public int getPositionSize() {
		return positionSize;
	}

	public void setPositionSize(int positionSize) {
		this.positionSize = positionSize;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRowMsg() {
		return rowMsg;
	}

	public void setRowMsg(String rowMsg) {
		this.rowMsg = rowMsg;
	}
}