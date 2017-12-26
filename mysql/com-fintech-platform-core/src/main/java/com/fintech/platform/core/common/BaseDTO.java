package com.fintech.platform.core.common;

import java.io.Serializable;

/**
 * @Description 定义基础 dto 类
 * @author
 * @date 2014年9月24日 下午8:48:28
 * @version V1.0
 */
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = -1641854051866090244L;

	//操作当前系统的 user 的org id
    private Long userOrgId;
	//操作当前系统的 userId
	private Long opUserId;
	
	private String createUserNameExt;
	private String createOrgNameExt;
	
	private String baseExt2;
	private String baseExt3;
	private String baseExt4;
	private String baseExt5;
	
	private String baseExt6;
	private String baseExt7;
	
	
	public String getBaseExt6() {
		return baseExt6;
	}
	public void setBaseExt6(String baseExt6) {
		this.baseExt6 = baseExt6;
	}
	public String getBaseExt7() {
		return baseExt7;
	}
	public void setBaseExt7(String baseExt7) {
		this.baseExt7 = baseExt7;
	}
	public String getCreateUserNameExt() {
		return createUserNameExt;
	}
	public void setCreateUserNameExt(String createUserNameExt) {
		this.createUserNameExt = createUserNameExt;
	}
	public String getCreateOrgNameExt() {
		return createOrgNameExt;
	}
	public void setCreateOrgNameExt(String createOrgNameExt) {
		this.createOrgNameExt = createOrgNameExt;
	}

    
    public Long getUserOrgId() {
		return userOrgId;
	}
	public void setUserOrgId(Long userOrgId) {
		this.userOrgId = userOrgId;
	}
	public Long getOpUserId() {
		return opUserId;
	}
	public void setOpUserId(Long opUserId) {
		this.opUserId = opUserId;
	}
	public String getBaseExt2() {
		return baseExt2;
	}
	public void setBaseExt2(String baseExt2) {
		this.baseExt2 = baseExt2;
	}
	public String getBaseExt3() {
		return baseExt3;
	}
	public void setBaseExt3(String baseExt3) {
		this.baseExt3 = baseExt3;
	}
	public String getBaseExt4() {
		return baseExt4;
	}
	public void setBaseExt4(String baseExt4) {
		this.baseExt4 = baseExt4;
	}
	public String getBaseExt5() {
		return baseExt5;
	}
	public void setBaseExt5(String baseExt5) {
		this.baseExt5 = baseExt5;
	}
}
