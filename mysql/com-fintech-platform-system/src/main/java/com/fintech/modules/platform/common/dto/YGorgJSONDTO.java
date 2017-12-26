package com.fintech.modules.platform.common.dto;

public class YGorgJSONDTO {
	private String id;// "0101",
	private String pid;// "01",
	private String name;// "东方银谷",
	private String isParent;// true,
	private String isDelete;// "1",
	
	private YGorgDTO2 org;
	
	public YGorgDTO2 getOrg() {
		return org;
	}
	public void setOrg(YGorgDTO2 org) {
		this.org = org;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
