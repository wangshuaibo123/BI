package com.jy.platform.api.org;

/**<pre>
 * 类名中文描述:
 *
 * 基本操作功能:
 *
 * Module ID  : com-jy-platform-api 
 *
 * Create Date：2014年10月22日 下午1:33:03
 * 
 * CopyRight  :  Copyright(C) 2014-xxxx  捷越联合 <br/>
 * 
 * @since 0.1
 * @version: 0.1
 * @author <a href="mailto:chengyangyu@jieyuechina.com">cyy</a>
 *
 * Change History Log
 * --------------------------------------------------------------------------------------------------------------
 * Date	      | Version | Author	   | Description			              
 * --------------------------------------------------------------------------------------------------------------
 * 2014年10月22日 | 0.1     | cyy| CREATE THE JAVA FILE: OrgPositionInfo.java.
 * --------------------------------------------------------------------------------------------------------------
 *
 * --------------------------------------------------------------------------------------------------------------
 *
 * </pre>
 */
public class OrgPositionInfo {
	
	/**机构ID*/
	private java.lang.Long orgId;
	
	/**机构名称*/
	private java.lang.String orgName;

	/**用户ID*/
	private java.lang.Long userId;

	/**岗位ID*/
	private java.lang.Long positionId;
	
	/**岗位名称*/
	private java.lang.String positionName;

	/**是否主部门，1是（主部门），0否（兼职部门），默认1*/
	private java.lang.String isMainOrg;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;

	public java.lang.Long getOrgId() {
		return orgId;
	}

	public void setOrgId(java.lang.Long orgId) {
		this.orgId = orgId;
	}

	public java.lang.String getOrgName() {
		return orgName;
	}

	public void setOrgName(java.lang.String orgName) {
		this.orgName = orgName;
	}

	public java.lang.Long getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Long userId) {
		this.userId = userId;
	}

	public java.lang.Long getPositionId() {
		return positionId;
	}

	public void setPositionId(java.lang.Long positionId) {
		this.positionId = positionId;
	}

	public java.lang.String getPositionName() {
		return positionName;
	}

	public void setPositionName(java.lang.String positionName) {
		this.positionName = positionName;
	}

	public java.lang.String getIsMainOrg() {
		return isMainOrg;
	}

	public void setIsMainOrg(java.lang.String isMainOrg) {
		this.isMainOrg = isMainOrg;
	}

	public java.lang.String getValidateState() {
		return validateState;
	}

	public void setValidateState(java.lang.String validateState) {
		this.validateState = validateState;
	}
	
	
	

}
