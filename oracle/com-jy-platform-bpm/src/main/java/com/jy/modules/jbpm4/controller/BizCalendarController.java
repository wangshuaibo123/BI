package com.jy.modules.jbpm4.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jy.platform.jbpm4.service.impl.BizCalendarService;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

@Controller
@Scope("prototype")
@RequestMapping("/bizCalendar")
public class BizCalendarController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(BizCalendarController.class);
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.service.impl.BizCalendarService")
	private BizCalendarService bizCalendarService;
	
	@RequestMapping("/intiBizCalendar")
	public ModelAndView intiBizCalendar(String year, String month, HttpServletRequest request)
			throws AbaboonException {	
		ModelAndView model = new ModelAndView();
		model.setViewName("forward:/component/jbpm/bizCalendar/manageBizCalendar.jsp");
		return model;
	}

	/**
	 * 获取日历信息
	 */
	@RequestMapping("/getBizHolidayListByYearMonth")
	@ResponseBody
	public List<String> getBizHolidayListByYearMonth(String yearmonth)
			throws AbaboonException {

		List<String> holidayList = bizCalendarService.queryBizHoliday(yearmonth);

		return holidayList;
	}
	
	/**
	 * 更改节假日信息
	 */
	@RequestMapping("/changeBizHolidaysByYearMonth")
	@ResponseBody
	public void changeBizHolidaysByYearMonth(String holidays)
			throws AbaboonException {
		if(holidays != null && holidays.trim() != "") {
			List<String> holidayList = new ArrayList<String>();
			String[] holidayArr = holidays.split(",");
			holidayList = Arrays.asList(holidayArr);
			
			String yearMonth = holidayList.get(0).substring(0, 7);
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("yearmonth", yearMonth);
			bizCalendarService.deleteBizHoliday(map);
			
			bizCalendarService.batchInsertHoliday(holidayList);
		}
	}
}
