package com.jy.platform.jbpm4.dto;

import com.jy.platform.core.common.BaseDTO;

public class BizCalendarHoliday extends BaseDTO {
	private static final long serialVersionUID = -6515117351137352277L;
	private long id;
	private String holiday;
	private String validateState;

	public BizCalendarHoliday(String holiday) {
		this.holiday = holiday;
		this.validateState = "1";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	public String getValidateState() {
		return validateState;
	}

	public void setValidateState(String validateState) {
		this.validateState = validateState;
	}

}
