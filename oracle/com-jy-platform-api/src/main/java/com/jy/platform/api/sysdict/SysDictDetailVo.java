package com.jy.platform.api.sysdict;


public class SysDictDetailVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**数据名称*/
	private java.lang.String dictDetailName;

	/**数据值*/
	private java.lang.String dictDetailValue;

	/**排序*/
	private java.lang.String orderBy;
	
	public java.lang.String getDictDetailName() {
		return dictDetailName;
	}

	public void setDictDetailName(java.lang.String dictDetailName) {
		this.dictDetailName = dictDetailName;
	}

	public java.lang.String getDictDetailValue() {
		return dictDetailValue;
	}

	public void setDictDetailValue(java.lang.String dictDetailValue) {
		this.dictDetailValue = dictDetailValue;
	}

	public java.lang.String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(java.lang.String orderBy) {
		this.orderBy = orderBy;
	}
	
}
