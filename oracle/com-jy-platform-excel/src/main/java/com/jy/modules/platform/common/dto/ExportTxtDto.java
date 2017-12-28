package com.jy.modules.platform.common.dto;

import com.jy.platform.tools.excel.SelectValue;


public class ExportTxtDto {

	private String display;

	private String code;

	private String type;

	private SelectValue[] value;

	public ExportTxtDto() {
	}

	public ExportTxtDto(String display, String code, String type,
			SelectValue[] value) {
		super();
		this.display = display;
		this.code = code;
		this.type = type;
		this.value = value;
	}

	public ExportTxtDto(String display, String code, String type) {
		super();
		this.display = display;
		this.code = code;
		this.type = type;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SelectValue[] getValue() {
		return value;
	}

	public void setValue(SelectValue[] value) {
		this.value = value;
	}
}
