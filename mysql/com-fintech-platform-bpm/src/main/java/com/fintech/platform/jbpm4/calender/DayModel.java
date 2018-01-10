package com.fintech.platform.jbpm4.calender;

public class DayModel {
	private int dayOfWeek;

	private int workTimeOfDay;

	private int hourOfWorkAM;

	private int hourOfRestAM;

	private int hourOfWorkPM;

	private int hourOfRestPM;

	private int minuteOfWorkAM;

	private int minuteOfRestAM;

	private int minuteOfWorkPM;

	private int minuteOfRestPM;

	public DayModel() {
		this(828, 828, 828, 828, 828, 828, 828, 828, 828, 828);
	}

	public DayModel(int dayOfWeek, int workTimeOfDay, int hourOfWorkAM,
			int minuteOfWorkAM, int hourOfRestAM, int minuteOfRestAM,
			int hourOfWorkPM, int minuteOfWorkPM, int hourOfRestPM,
			int minuteOfRestPM) {
		this.dayOfWeek = dayOfWeek;
		this.workTimeOfDay = workTimeOfDay;
		this.hourOfWorkAM = hourOfWorkAM;
		this.hourOfRestAM = hourOfRestAM;
		this.hourOfWorkPM = hourOfWorkPM;
		this.hourOfRestPM = hourOfRestPM;
		this.minuteOfWorkAM = minuteOfWorkAM;
		this.minuteOfRestAM = minuteOfRestAM;
		this.minuteOfWorkPM = minuteOfWorkPM;
		this.minuteOfRestPM = minuteOfRestPM;
	}
	
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public void setHourOfWorkAM(int hourOfWorkAM) {
		this.hourOfWorkAM = hourOfWorkAM;
	}

	public void setHourOfRestAM(int hourOfRestAM) {
		this.hourOfRestAM = hourOfRestAM;
	}

	public void setHourOfWorkPM(int hourOfWorkPM) {
		this.hourOfWorkPM = hourOfWorkPM;
	}

	public void setHourOfRestPM(int hourOfRestPM) {
		this.hourOfRestPM = hourOfRestPM;
	}

	public void setMinuteOfWorkAM(int minuteOfWorkAM) {
		this.minuteOfWorkAM = minuteOfWorkAM;
	}

	public void setMinuteOfRestAM(int minuteOfRestAM) {
		this.minuteOfRestAM = minuteOfRestAM;
	}

	public void setMinuteOfWorkPM(int minuteOfWorkPM) {
		this.minuteOfWorkPM = minuteOfWorkPM;
	}

	public void setMinuteOfRestPM(int minuteOfRestPM) {
		this.minuteOfRestPM = minuteOfRestPM;
	}

	public void setWorkTimeOfDay(int workTimeOfDay) {
		this.workTimeOfDay = workTimeOfDay;
	}

	public int getDayOfWeek() {
		return this.dayOfWeek;
	}

	public int getHourOfWorkAM() {
		return this.hourOfWorkAM;
	}

	public int getHourOfRestAM() {
		return this.hourOfRestAM;
	}

	public int getHourOfWorkPM() {
		return this.hourOfWorkPM;
	}

	public int getHourOfRestPM() {
		return this.hourOfRestPM;
	}

	public int getMinuteOfWorkAM() {
		return this.minuteOfWorkAM;
	}

	public int getMinuteOfRestAM() {
		return this.minuteOfRestAM;
	}

	public int getMinuteOfWorkPM() {
		return this.minuteOfWorkPM;
	}

	public int getMinuteOfRestPM() {
		return this.minuteOfRestPM;
	}

	public int getWorkTimeOfDay() {
		return this.workTimeOfDay;
	}


}