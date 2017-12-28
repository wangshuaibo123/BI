package com.jy.platform.tuomin.monitor;

/**
 * 实体bean
 * @author zhangyu
 *
 */
public class MonitorBean implements Comparable<MonitorBean> {

	private String threadNo; // 线程编号
	private String sysName; // 系统名称
	private String tableName; // 表名
	private String beginTime; // 开始时间
	private String endTime; // 结束时间
	private String status; // 状态

	public String getThreadNo() {
		return threadNo;
	}

	public void setThreadNo(String threadNo) {
		this.threadNo = threadNo;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 根据线程编号来比较大小（t0.0 > t0.1）
	 */
	@Override
	public int compareTo(MonitorBean o) {
		return threadNo.compareTo(o.getThreadNo());
	}

}
