package com.fintech.platform.jbpm4.dto;

import java.io.Serializable;
import java.util.Date;

public class ConsignTask implements Serializable{
	
	private Long id;//主键

    private String fromUserId;//委托人id

    private String toUserId;//被委托人id

    private String proDefKey;//流程定义key

    private String type;//委托类型（1：请假委托，2：离职委托，3：代理委托）

    private String reason;//事由

    private Date startTime;//生效时间
    
    private Date endTime;//终止时间
    
    private String valiDateState;//数据有效性（1：有效、0：无效）
    
    private String createdBy;//创建人id
    
    private Date created;//创建时间
    
    private String lastUpdBy;//最后更新人id
    
    private Date lastUpd;//最后更新时间
    
    private String remark;//备注
    private String taskId;//任务id

    private String taskType;//任务类型

    private String orgId;//组织id
    
    


	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastUpd() {
		return lastUpd;
	}

	public void setLastUpd(Date lastUpd) {
		this.lastUpd = lastUpd;
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