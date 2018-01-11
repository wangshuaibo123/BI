package com.fintech.platform.jbpm4.calender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


/**
 * 
 * @Description: 计算工作时间
 * @author
 * @version 1.0, 
 * @date 2014-6-5 上午09:43:59
 */
public class CalWorkTime {

	private static List holidayList;

	private static Map worktimeMap;

	private int weekAdjust;

	private Calendar today;

	private Calendar temp;

	private int total;

	private DayModel dayModel;

	private static CalWorkTime _instance = new CalWorkTime();

	private CalWorkTime() {
		holidayList = WorkTimeParser.getInstance().getHolidays();
		worktimeMap = WorkTimeParser.getInstance().getWorkTime();

		this.setToday(Calendar.getInstance());
		this.setTemp(Calendar.getInstance());
		this.setWeekAdjust(-1);
		this.setTotal(0);
	}

	
	/**
	 * 自定义 工作日结束时间 
	 * @param startCalendar
	 */
	public CalWorkTime(Calendar endCalendar ) {
		holidayList = WorkTimeParser.getInstance().getHolidays();
		worktimeMap = WorkTimeParser.getInstance().getWorkTime();

		this.setToday(endCalendar);
		this.setTemp(endCalendar);
		this.setWeekAdjust(-1);
		this.setTotal(0);
		
	}
	
	public CalWorkTime(String configFile) {
		WorkTimeParser parser = new WorkTimeParser(configFile);
		holidayList = parser.getHolidays();
		worktimeMap = parser.getWorkTime();

		this.setToday(Calendar.getInstance());
		this.setTemp(Calendar.getInstance());
		this.setWeekAdjust(-1);
		this.setTotal(0);
	}

	public static CalWorkTime getInstance() {
		
		return _instance;
	}

	public Calendar getTemp() {
		return this.temp;
	}

	public void setTemp(Calendar temp) {
		this.temp = temp;
	}

	public Calendar getToday() {
		return this.today;
	}

	public void setToday(Calendar today) {
		this.today = today;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	/**
	 * 计算工作时间<BR>
	 * 即当前时间减去aday，并扣除非工作时间(休息时间以及节假日)
	 * aday 为工作开始时间
	 */
	public int calcWorkingTime(Calendar aday) {
		return calculateWorkTime(aday);
	}

	private int calculateWorkTime(Calendar createDay) {
		if (createDay.after(this.today))
			return 0;
		//判断当前年份是否相同，且一年中的某一天是否相同
		if ((createDay.get(Calendar.DAY_OF_YEAR) == this.today.get(Calendar.DAY_OF_YEAR))
				&& (createDay.get(Calendar.YEAR) == this.today.get(Calendar.YEAR))) {
			
			if (isWorkday(createDay)) {
				this.total = calToday(createDay, this.today);
				return this.total;
			}
			return 0;
		}
		//如果不是同一年的时间 则 如下计算
		if (isWorkday(createDay)) {
			this.temp.set(Calendar.HOUR_OF_DAY, 23);
			this.temp.set(Calendar.MINUTE, 59);
			this.total += calToday(createDay, this.temp);
		}
		if (isWorkday(this.today)) {
			this.temp.set(Calendar.HOUR_OF_DAY, 1);
			this.temp.set(Calendar.MINUTE, 0);
			this.total += calToday(this.temp, this.today);
		}
		for (createDay.add(Calendar.DAY_OF_YEAR, 1); 
		(createDay.get(Calendar.YEAR) < this.today.get(Calendar.YEAR))| (
				(createDay.get(Calendar.YEAR) == this.today.get(Calendar.YEAR)) && 
				(createDay.get(Calendar.DAY_OF_YEAR) != this.today.get(Calendar.DAY_OF_YEAR))); 
		createDay.add(Calendar.DAY_OF_YEAR, 1)) {
			
			
			if (!isWorkday(createDay)) {
				continue;
			}

			this.dayModel = ((DayModel) worktimeMap.get(String.valueOf(weekday(createDay))));
			this.total += this.dayModel.getWorkTimeOfDay();
		}

		return this.total;
	}

	/**
	 * 
	 * @param day
	 * @param temp
	 * @return
	 */
	public int calToday(Calendar day, Calendar temp) {
		this.dayModel = ((DayModel) worktimeMap.get(String.valueOf(weekday(day))));
		int createTime = timeOfSpace(day);
		int currentlyTime = timeOfSpace(temp);
		if (currentlyTime == 5) {
			switch (createTime) {
			case 1:
				return calMinute(this.dayModel.getHourOfWorkAM(), 
						this.dayModel.getMinuteOfWorkAM(), 
						this.dayModel.getHourOfRestAM(),
						this.dayModel.getMinuteOfRestAM())+ 
						calMinute(this.dayModel.getHourOfWorkPM(),
								this.dayModel.getMinuteOfWorkPM(),
								this.dayModel.getHourOfRestPM(), 
								this.dayModel.getMinuteOfRestPM());
			case 2:
				return calMinute(day.get(Calendar.HOUR_OF_DAY), 
						day.get(Calendar.MINUTE), 
						this.dayModel.getHourOfRestAM(), 
						this.dayModel.getMinuteOfRestAM())+ 
						calMinute(this.dayModel.getHourOfWorkPM(),
								this.dayModel.getMinuteOfWorkPM(),
								this.dayModel.getHourOfRestPM(), 
								this.dayModel.getMinuteOfRestPM());
			case 3:
				return calMinute(this.dayModel.getHourOfWorkPM(), 
						this.dayModel.getMinuteOfWorkPM(), 
						this.dayModel.getHourOfRestPM(),
						this.dayModel.getMinuteOfWorkPM());
			case 4:
				return calMinute(day.get(Calendar.HOUR_OF_DAY), 
						day.get(Calendar.MINUTE), 
						this.dayModel.getHourOfRestPM(), 
						this.dayModel.getMinuteOfWorkPM());
			case 5:
				return 0;
			}
		} else if (currentlyTime == 4) {
			switch (createTime) {
			case 1:
				return calMinute(this.dayModel.getHourOfWorkAM(), 
						this.dayModel.getMinuteOfWorkAM(), 
						this.dayModel.getHourOfRestAM(),
						this.dayModel.getMinuteOfRestAM())+ 
						calMinute(this.dayModel.getHourOfWorkPM(),
								this.dayModel.getMinuteOfWorkPM(), 
								this.today.get(Calendar.HOUR_OF_DAY), 
								this.today.get(Calendar.MINUTE));
			case 2:
				return calMinute(day.get(Calendar.HOUR_OF_DAY), 
						day.get(Calendar.MINUTE), 
						this.dayModel.getHourOfRestAM(), 
						this.dayModel.getMinuteOfRestAM())+ 
						calMinute(this.dayModel.getHourOfWorkPM(),
								this.dayModel.getMinuteOfWorkPM(), 
								this.today.get(Calendar.HOUR_OF_DAY), 
								this.today.get(Calendar.MINUTE));
			case 3:
				return calMinute(this.dayModel.getHourOfWorkPM(), 
						this.dayModel.getMinuteOfWorkPM(), 
						this.today.get(Calendar.HOUR_OF_DAY), 
						this.today.get(Calendar.MINUTE));
			case 4:
				return calMinute(day.get(Calendar.HOUR_OF_DAY), 
						day.get(Calendar.MINUTE), 
						this.today.get(Calendar.HOUR_OF_DAY),
						this.today.get(Calendar.MINUTE));
			case 5:
				return 0;
			}
		} else if (currentlyTime == 3) {
			switch (createTime) {
			case 1:
				return calMinute(this.dayModel.getHourOfWorkAM(), 
						this.dayModel.getMinuteOfWorkAM(), 
						this.dayModel.getHourOfRestAM(),
						this.dayModel.getMinuteOfRestAM());
			case 2:
				return calMinute(day.get(Calendar.HOUR_OF_DAY), 
						day.get(Calendar.MINUTE), 
						this.dayModel.getHourOfRestAM(), 
						this.dayModel.getMinuteOfRestAM());
			case 3:
				return 0;
			case 4:
				return 0;
			case 5:
				return 0;
			}
		} else if (currentlyTime == 2) {
			switch (createTime) {
			case 1:
				return calMinute(this.dayModel.getHourOfWorkAM(), 
						this.dayModel.getMinuteOfWorkAM(), 
						this.today.get(Calendar.HOUR_OF_DAY), 
						this.today.get(Calendar.MINUTE));
			case 2:
				return calMinute(day.get(Calendar.HOUR_OF_DAY), 
						day.get(Calendar.MINUTE), 
						this.today.get(Calendar.HOUR_OF_DAY),
						this.today.get(Calendar.MINUTE));
			case 3:
				return 0;
			case 4:
				return 0;
			case 5:
				return 0;
			}
		}
		return 0;
	}

	/**
	 * 获取该日期 在该工作日中所在的时间段 情况
	 * @param day
	 * @return
	 */
	private int timeOfSpace(Calendar day) {
		if ((day.get(Calendar.HOUR_OF_DAY) < this.dayModel.getHourOfWorkAM())
				|| ((day.get(Calendar.HOUR_OF_DAY) == this.dayModel.getHourOfWorkAM()) && (day.get(Calendar.MINUTE) <= this.dayModel.getMinuteOfWorkAM()))) {
			return 1;
		}
		if ((day.get(Calendar.HOUR_OF_DAY) < this.dayModel.getHourOfRestAM())
				|| ((day.get(Calendar.HOUR_OF_DAY) == this.dayModel.getHourOfRestAM()) && (day.get(Calendar.MINUTE) <= this.dayModel.getMinuteOfRestAM()))) {
			return 2;
		}
		if ((day.get(Calendar.HOUR_OF_DAY) < this.dayModel.getHourOfWorkPM())
				|| ((day.get(Calendar.HOUR_OF_DAY) == this.dayModel.getHourOfWorkPM()) && (day.get(Calendar.MINUTE) <= this.dayModel.getMinuteOfWorkPM()))) {
			return 3;
		}
		if ((day.get(Calendar.HOUR_OF_DAY) < this.dayModel.getHourOfRestPM())
				|| ((day.get(Calendar.HOUR_OF_DAY) == this.dayModel.getHourOfRestPM()) && (day.get(Calendar.MINUTE) <= this.dayModel.getMinuteOfRestPM()))) {
			return 4;
		}

		return 5;
	}

	private int calMinute(int firstHour, int firstMinute, int secondHour,int secondMinute) {
		if ((firstHour > secondHour)|| ((firstHour == secondHour) && (firstMinute >= secondMinute)))
			return 0;
		if (firstHour == secondHour) {
			return secondMinute - firstMinute;
		}
		return (secondHour - firstHour) * 60 + secondMinute - firstMinute;
	}

	/**
	 * 是否节假日
	 * @param day 日期
	 * @return true/false
	 */
	public boolean isHoliday(Calendar day) {
		ListIterator iter = holidayList.listIterator();
		String currDate = day.get(Calendar.MONTH) + 1 + "/" + day.get(Calendar.DATE);

		while (iter.hasNext()) {
			String a = (String) iter.next();
			if (currDate.equals(a)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取下一个工作日<BR>
	 * 如果当前日期为非工作日，则顺延下一天止至工作日
	 * @param day 当前日期
	 * @param amount 偏移时间
	 * @param pattern 日期格式
	 * @return
	 */
	public String getNextWorkDay(String dateString,int amount,String pattern){
		Calendar day=Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			day.setTime(sdf.parse(dateString));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		day.add(Calendar.DAY_OF_MONTH, amount);
		while(!isWorkday(day)){
			day.add(Calendar.DAY_OF_MONTH, 1);
		}
		return sdf.format(day.getTime());
	}
	/**
	 * 判断 该日期是否是工作日
	 * @param day
	 * @return
	 */
	public boolean isWorkday(Calendar day) {
		return (worktimeMap.containsKey(String.valueOf(weekday(day))))&& (!isHoliday(day));
	}
	
	/**
	 * 获取该日期是周几
	 * @param day
	 * @return
	 */
	private int weekday(Calendar day) {
		return (day.get(Calendar.DAY_OF_WEEK) + this.weekAdjust == 0) ? 7 : day.get(Calendar.DAY_OF_WEEK)+ this.weekAdjust;
	}
	
	public void setWeekAdjust(int weekAdjust) {
		this.weekAdjust = weekAdjust;
	}

	public int getWeekAdjust() {
		return this.weekAdjust;
	}

}