package com.fintech.modules.bpmreport.dto;

import java.io.Serializable;
/**
 * 数据输出bean
 * @author
 *
 */
public class DataBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private  long num;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public DataBean(String name, int num) {
		this.name = name;
		this.num = num;
	}
	public DataBean() {
	}
}
