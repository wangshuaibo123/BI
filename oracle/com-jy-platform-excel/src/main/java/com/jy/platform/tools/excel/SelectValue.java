package com.jy.platform.tools.excel;

public class SelectValue {

	@Override
	public String toString() {
		return "SelectValue [text=" + text + ", value=" + value + "]";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String text;
	private String value;
}
