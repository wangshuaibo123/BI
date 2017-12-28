package com.jy.platform.api.msg;

import java.util.Arrays;
import java.util.Date;

public class Msg implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String conent;
	private String url;
	private String state;
	private Date startTime;
	private Date endTime;
	private String urgentFlag;//紧急标识 0:普通；1：紧急；2：特急 default:0
	private String creator;//消息发布者
    private String msgType;//消息类型 0：全局 1：专有
    private String sysFlag;//系统标识，来源于字典表数据
    private String userIds;//专有消息用户字符串，格式：Id1,Id2,Id3
    private String [] specificUserIds;//专有消息用户字符串
    private String isRead;//是否已读 0 
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getSysFlag() {
		return sysFlag;
	}
	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String[] getSpecificUserIds() {
		return specificUserIds;
	}
	public void setSpecificUserIds(String[] specificUserIds) {
		this.specificUserIds = specificUserIds;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getConent() {
		return conent;
	}
	public void setConent(String conent) {
		this.conent = conent;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getUrgentFlag() {
		return urgentFlag;
	}
	public void setUrgentFlag(String urgentFlag) {
		this.urgentFlag = urgentFlag;
	}

	@Override
	public String toString() {
		return "Msg [id=" + id + ", title=" + title + ", conent=" + conent
				+ ", url=" + url + ", state=" + state + ", startTime="
				+ startTime + ", endTime=" + endTime + ", urgentFlag="
				+ urgentFlag + ", creator=" + creator + ", msgType=" + msgType
				+ ", sysFlag=" + sysFlag + ", userIds=" + userIds
				+ ", specificUserIds=" + Arrays.toString(specificUserIds) + "]";
	}
	
}
