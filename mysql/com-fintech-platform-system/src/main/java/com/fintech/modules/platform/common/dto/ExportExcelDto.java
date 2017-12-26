package com.fintech.modules.platform.common.dto;


public class ExportExcelDto {

	private String display;

	private String code;

	private String type;

	private com.fintech.platform.tools.excel.SelectValue [] value;
	
	private String isExport = "true";//是否导入

	public ExportExcelDto() {
	}

	public ExportExcelDto(String display, String code, String type,
			com.fintech.platform.tools.excel.SelectValue [] value) {
		super();
		this.display = display;
		this.code = code;
		this.type = type;
		this.value = value;
	}

	public ExportExcelDto(String display, String code, String type) {
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

	public String getIsExport() {
		return isExport;
	}

	public void setIsExport(String isExport) {
		this.isExport = isExport;
	}
	
	public com.fintech.platform.tools.excel.SelectValue[] getValue() {
		return value;
	}

	public void setValue(com.fintech.platform.tools.excel.SelectValue[] value) {
		this.value = value;
	}
}
