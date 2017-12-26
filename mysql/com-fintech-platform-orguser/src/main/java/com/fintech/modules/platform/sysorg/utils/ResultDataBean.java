package com.fintech.modules.platform.sysorg.utils;

import java.io.Serializable;
import java.util.List;

public class ResultDataBean<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<T> result = null;
	
	private String msg = null;

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
