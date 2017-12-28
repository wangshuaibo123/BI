package com.jy.platform.api.sysdict;

import java.util.List;

public class SysDictVo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	/**数据字典code*/
	private java.lang.String dictCode;

	/**数据字典名称*/
	private java.lang.String dictName;
	
	private List sysDictDetail;
	
	public java.lang.String getDictCode() {
		return dictCode;
	}

	public void setDictCode(java.lang.String dictCode) {
		this.dictCode = dictCode;
	}

	public java.lang.String getDictName() {
		return dictName;
	}

	public void setDictName(java.lang.String dictName) {
		this.dictName = dictName;
	}

	public List getSysDictDetail() {
		return sysDictDetail;
	}

	public void setSysDictDetail(List sysDictDetail) {
		this.sysDictDetail = sysDictDetail;
	}

}
