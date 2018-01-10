package com.jy.platform.jbpm4.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Description: 定义 工作流 参数信息
 * @author chen
 * @version 1.0, 
 * @date 2013-8-29 下午01:11:59
 */
public class JbpmDTO implements Serializable{
	//任务描述
	private String remark;
	//业务任务状态 
	private String bizTaskType;
	//定义跨流程时目标 子流程 key
	private String subProKey;
	//定义 目标子流程 在主流程图中的节点名称
	private String destSubProName;
	//定义当前子流程 的节点名称
	private String curTurnDirection;
	//定义 当前子流程 key
	private String curSubProKey;
	//定义当前子流程 的主流程实例
	private String curMainProIns;
	//定义选择参与者的sqlid
	private String partnerSqlId;
	
	private String processDefinitionId;
	
	private String proVersion;
	
	private String processDefinitionKey;
	
	private String processDefinitionName;
	
	private String processParticipationName;
	
	private String paramUserId;
	
	/**
	 * 支持根据登陆人和登陆人归属角色查询,以","分隔
	 */
	private String paramUserIds;
	private String processType;
	
	private String processState;
	
	private Date processStartDate;
	
	private Date processEndDate;
	
	private String processInsId;
	//节点名称
	private String acitityName;
	
	private String busInfoId;
	
	private String busInfoName;
	
	private String operatePro;
	// 法律事务 添加 主流实例id
	private String mainProInsId;
	//业务数据类型描述（(-1：合同；0：非保险类诉讼/仲裁案件;1：保险类诉讼/仲裁案件；2：拒赔案件;3:律师审核)）
	private String busType;
	//待办任务ID
	private String taskId;
	//待办任务创建的开始时间
	private String startTime;
	//待办任务创建的结束时间
	private String endTime;
	//流程流转 方向
	private String turnDirection;
	//任务类型
	private String bizType;
	//任务名称
	private String bizInfName;
	//控制个性化定义sql
	private String customSQL;	
	//组织机构ID
	private String orgId;	
	//流程发起的开始时间
	private String proStartTime;
	//流程发起的结束时间
	private String proEndTime;
	//角色映射sql
	private String roleMappingSql;
	//是否是管理监控 1：是  0：否
	private String isMonitor;
	//排序字段名
	private String sortName;
	//升序或降序
	private String sortType;
	
	private String extSort;
	
	
    public String getExtSort() {
        return extSort;
    }

    
    public void setExtSort(String extSort) {
        this.extSort = extSort;
    }

    public String getCustomSQL() {
		return customSQL;
	}

	public void setCustomSQL(String customSQL) {
		this.customSQL = customSQL;
	}

	public String getBizInfName() {
		return bizInfName;
	}

	public void setBizInfName(String bizInfName) {
		this.bizInfName = bizInfName;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getProcessParticipationName() {
		return processParticipationName;
	}

	public void setProcessParticipationName(String processParticipationName) {
		this.processParticipationName = processParticipationName;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}


	public String getProcessInsId() {
		return processInsId;
	}

	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}

	public String getAcitityName() {
		return acitityName;
	}

	public void setAcitityName(String acitityName) {
		this.acitityName = acitityName;
	}

	public Date getProcessStartDate() {
		return processStartDate;
	}

	public void setProcessStartDate(Date processStartDate) {
		this.processStartDate = processStartDate;
	}

	public Date getProcessEndDate() {
		return processEndDate;
	}

	public void setProcessEndDate(Date processEndDate) {
		this.processEndDate = processEndDate;
	}

	public String getBusInfoId() {
		return busInfoId;
	}

	public void setBusInfoId(String busInfoId) {
		this.busInfoId = busInfoId;
	}

	public String getBusInfoName() {
		return busInfoName;
	}

	public void setBusInfoName(String busInfoName) {
		this.busInfoName = busInfoName;
	}

	public String getParamUserId() {
		return paramUserId;
	}

	public void setParamUserId(String paramUserId) {
		this.paramUserId = paramUserId;
	}

	public String getOperatePro() {
		return operatePro;
	}

	public void setOperatePro(String operatePro) {
		this.operatePro = operatePro;
	}

	public String getMainProInsId() {
		return mainProInsId;
	}

	public void setMainProInsId(String mainProInsId) {
		this.mainProInsId = mainProInsId;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public String getTurnDirection() {
		return turnDirection;
	}

	public void setTurnDirection(String turnDirection) {
		this.turnDirection = turnDirection;
	}

	public String getProVersion() {
		return proVersion;
	}

	public void setProVersion(String proVersion) {
		this.proVersion = proVersion;
	}

	public String getPartnerSqlId() {
		return partnerSqlId;
	}

	public void setPartnerSqlId(String partnerSqlId) {
		this.partnerSqlId = partnerSqlId;
	}

	public String getSubProKey() {
		return subProKey;
	}

	public void setSubProKey(String subProKey) {
		this.subProKey = subProKey;
	}


	public String getCurTurnDirection() {
		return curTurnDirection;
	}

	public void setCurTurnDirection(String curTurnDirection) {
		this.curTurnDirection = curTurnDirection;
	}

	public String getCurSubProKey() {
		return curSubProKey;
	}

	public void setCurSubProKey(String curSubProKey) {
		this.curSubProKey = curSubProKey;
	}

	public String getCurMainProIns() {
		return curMainProIns;
	}

	public void setCurMainProIns(String curMainProIns) {
		this.curMainProIns = curMainProIns;
	}

	public String getDestSubProName() {
		return destSubProName;
	}

	public void setDestSubProName(String destSubProName) {
		this.destSubProName = destSubProName;
	}
	public String getBizTaskType() {
		return bizTaskType;
	}

	public void setBizTaskType(String bizTaskType) {
		this.bizTaskType = bizTaskType;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
		public String getParamUserIds() {
		return paramUserIds;
	}

	public void setParamUserIds(String paramUserIds) {
		this.paramUserIds = paramUserIds;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getProStartTime() {
		return proStartTime;
	}

	public void setProStartTime(String proStartTime) {
		this.proStartTime = proStartTime;
	}

	public String getProEndTime() {
		return proEndTime;
	}

	public void setProEndTime(String proEndTime) {
		this.proEndTime = proEndTime;
	}

	public String getRoleMappingSql() {
		return roleMappingSql;
	}

	public void setRoleMappingSql(String roleMappingSql) {
		this.roleMappingSql = roleMappingSql;
	}

	public String getIsMonitor() {
		return isMonitor;
	}

	public void setIsMonitor(String isMonitor) {
		this.isMonitor = isMonitor;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	
	
	
}
