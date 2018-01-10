package com.jy.platform.jbpm4.repository;

import java.util.List;
import java.util.Map;

import com.jy.platform.core.mybatis.MyBatisRepository;
import com.jy.platform.jbpm4.dto.BizCalendarHoliday;
/*
 * 
 */
@MyBatisRepository
public interface BizCalendarHolidayMapper {
	public int insertBizHoliday(BizCalendarHoliday bizHoliday);
	
	public int deleteBizHoliday(Map<String, String> map);
	
	public List<String> queryBizHoliday(String yearmonth);
	
	public int batchInsertHoliday(List holidayList);
	
	public List<String> queryHolidayByStartAndEndTime(Map<String, Object> param);
}
