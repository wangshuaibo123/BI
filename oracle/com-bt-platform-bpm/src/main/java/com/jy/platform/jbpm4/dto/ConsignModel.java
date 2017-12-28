package com.jy.platform.jbpm4.dto;

import java.io.Serializable;

public class ConsignModel implements Serializable{
	
	private Long id;//主键

    private String fromUserId;//委托人id

    private String toUserId;//被委托人id

    private String proDefKey;//流程定义key

    private String type;//委托类型（1：请假委托，2：离职委托，3：代理委托）

    private String reason;//事由

    private String startTimeStr;//生效时间
    
    private String endTimeStr;//终止时间
    
    private String valiDateState;//数据有效性（1：有效、0：无效）
    
    private String createdBy;//创建人id
    
    private String createdStr;//创建时间
    
    private String lastUpdBy;//最后更新人id
    
    private String lastUpdStr;//最后更新时间
    
    private String remark;//备注

    
    


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getProDefKey() {
		return proDefKey;
	}

	public void setProDefKey(String proDefKey) {
		this.proDefKey = proDefKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getCreatedStr() {
		return createdStr;
	}

	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}

	public String getLastUpdStr() {
		return lastUpdStr;
	}

	public void setLastUpdStr(String lastUpdStr) {
		this.lastUpdStr = lastUpdStr;
	}

	public String getValiDateState() {
		return valiDateState;
	}

	public void setValiDateState(String valiDateState) {
		this.valiDateState = valiDateState;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    
    

}