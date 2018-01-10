package com.jy.platform.jbpm4.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.platform.jbpm4.dto.BizCalendarHoliday;
import com.jy.platform.jbpm4.repository.BizCalendarHolidayMapper;

@Service("com.jy.platform.jbpm4.service.impl.BizCalendarService")
public class BizCalendarService {
	
    //假日
    Map<String, String> holidayMap = new HashMap<String,String>();
    //上午上班时间段
    String am = "9:00-12:00";
    //下午上班时间段
    String pm = "13:00-18:00";
	
	@Autowired
	private BizCalendarHolidayMapper bizCalendarHolidayMapper;

	public int insertBizHoliday(BizCalendarHoliday bizHoliday) {
		return bizCalendarHolidayMapper.insertBizHoliday(bizHoliday);
	}
	
	public int deleteBizHoliday(Map<String, String> map) {
		return bizCalendarHolidayMapper.deleteBizHoliday(map);
	}
	
	public List<String> queryBizHoliday(String yearmonth) {
		return bizCalendarHolidayMapper.queryBizHoliday(yearmonth);
	}
	
	public int batchInsertHoliday(List holidayList) {
		return bizCalendarHolidayMapper.batchInsertHoliday(holidayList);
	}
	
	public List<String> queryHolidayByStartAndEndTime(Map<String, Object> param){
	    return bizCalendarHolidayMapper.queryHolidayByStartAndEndTime(param);
	}
	
	/**
	 * 根据日历规则和上班时间段计算开始时间和截止时间之间的工作时间
	 * 默认上班时间段为：
	 * @param beginTime 毫秒 
	 * @param endTime  毫秒
	 * @author chizhaoxian
	 * @return 工作时间(精确到毫秒)
	 */
	public long getWorkTime(long beginTime,long endTime){
		long worktime = 0;
		if(beginTime>=endTime){
			return 0;
		}
		/**
		 * 取节假日数据
		 * 暂时只查询前5年后5年的数据,根据实际情况调整，配置的足够大就是取全部数据
		 * 如果效率影响不大可查全部数据
		 */   
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);
        String startDateStr = (nowYear-5)+"-01-01";
        String endDateStr = (nowYear+5)+"-12-31";
        Map<String, Object> param = new HashMap<String,Object>();
        param.put("startTime", startDateStr);
        param.put("endTime", endDateStr);
        List<String> calendarList =  queryHolidayByStartAndEndTime(param);
        for(String holiday:calendarList){
            holidayMap.put(holiday, holiday);
        }
		/**
		 * 如果开始时间是节假日，将开始时间推算到非节假日
		 */
        Calendar startCal = Calendar.getInstance();
       
        startCal.setTimeInMillis(beginTime);
        dealStartTime(startCal);		
		/**
		 * 如果截止时间是假节日，将截止时间推算到非节假日
		 */
        Calendar endCal = Calendar.getInstance();
        endCal.setTimeInMillis(endTime);
        dealEndTime(endCal);
        /**
         * 如果都处于一段连续的节假日内，有可能计算出的截止时间小于开始时间
         */
        if(startCal.getTimeInMillis()>=endCal.getTimeInMillis()){
        	return 0;
        }
		//如果开始时间和截止时间在同一天，计算工作时间		
		if(startCal.get(Calendar.YEAR) == endCal.get(Calendar.YEAR) && startCal.get(Calendar.DAY_OF_YEAR)==endCal.get(Calendar.DAY_OF_YEAR)){
			worktime = getWorktimeInSameDay(startCal,endCal);
		}else{
			//计算开始时间当天的工作时间
			worktime = getStartWorkTimeInOneDay(startCal);
			//计算开始时间跟截止时间之间的工作时间(不包括开始时间当天和截止时间当天)
			worktime += getDiffTime(startCal,endCal);
			//计算截止时间当天的工作时间
			worktime += getEndWorkTimeInOneDay(endCal);
		}
		return worktime;
	}
	
	/**
	 * 计算开始时间跟截止时间之间的工作时间(不包括开始时间当天和截止时间当天)
	 * @param fromCal
	 * @param toCal
	 * @return
	 */
	private long getDiffTime(Calendar fromCal,Calendar toCal){
		fromCal.add(Calendar.DAY_OF_MONTH, 1);
		long worktime = 0;
		int day = 0;
		while(fromCal.getTimeInMillis()<toCal.getTimeInMillis() 
				&& !(fromCal.get(Calendar.YEAR) == toCal.get(Calendar.YEAR)
				&& fromCal.get(Calendar.DAY_OF_YEAR)==toCal.get(Calendar.DAY_OF_YEAR))){
			String key = getDateKey(fromCal);
	        if(!holidayMap.containsKey(key)){
	        	day++;
	        }
	        fromCal.add(Calendar.DAY_OF_MONTH, 1);
		}
		if(day>0){
	        long fromAm = parseTime(fromCal, am,"from");
	        long toAm = parseTime(fromCal,am,"to");
	        long fromPm = parseTime(fromCal,pm,"from");
	        long toPm = parseTime(fromCal,pm,"to");
	        worktime =  day * (toAm - fromAm + toPm - fromPm);
		}
		return worktime;
	}
	
	/**
	 * 计算开始时间当天的工作时间
	 * @param cal
	 * @return
	 */
	private long getStartWorkTimeInOneDay(Calendar cal){
		long worktime = 0;
        long startTime = cal.getTimeInMillis();
        long fromAm = parseTime(cal, am,"from");
        long toAm = parseTime(cal,am,"to");
        long fromPm = parseTime(cal,pm,"from");
        long toPm = parseTime(cal,pm,"to");

        //上班时间之前
        if(startTime<fromAm){
        	worktime = toAm - fromAm + toPm - fromPm;
        }else if(startTime>=fromAm && startTime<=toAm){//在上午上班时间段内
        	worktime = toAm - startTime + toPm - fromPm;
        }else if(startTime>toAm && startTime<fromPm){//在中午休息时间段
           worktime = toPm - fromPm;
        }else if(startTime>=fromPm && startTime<=toPm){//在下午上班时间段内
        	worktime = toPm - startTime;
        }else if(startTime>toPm){//在下午下班之后
        	worktime = 0;
        }
        return worktime;
	}
	
	/**
	 * 计算截止时间当天的工作时间
	 * @param cal
	 * @return
	 */
	private long getEndWorkTimeInOneDay(Calendar cal){
		long worktime = 0;
        long toTime = cal.getTimeInMillis();
        long fromAm = parseTime(cal, am,"from");
        long toAm = parseTime(cal,am,"to");
        long fromPm = parseTime(cal,pm,"from");
        long toPm = parseTime(cal,pm,"to");

        //上班时间之前
        if(toTime<fromAm){
        	worktime = 0;
        }else if(toTime>=fromAm && toTime<=toAm){//在上午上班时间段内
        	worktime = toTime - fromAm;
        }else if(toTime>toAm && toTime<fromPm){//在中午休息时间段
           worktime = toAm - fromAm;
        }else if(toTime>=fromPm && toTime<=toPm){//在下午上班时间段内
        	worktime = toAm - fromAm + toTime - fromPm;
        }else if(toTime>toPm){//在下午下班之后
        	worktime = toAm - fromAm + toPm - fromPm;
        }
        return worktime;
	}
	
	/**
	 * 开始时间和截止时间在同一天
	 * @return
	 */
	private long getWorktimeInSameDay(Calendar fromCal,Calendar toCal){
		long worktime = 0;
		long fromTime = fromCal.getTimeInMillis();
		long toTime = toCal.getTimeInMillis();
        long fromAm = parseTime(fromCal, am,"from");
        long toAm = parseTime(fromCal,am,"to");
        long fromPm = parseTime(fromCal,pm,"from");
        long toPm = parseTime(fromCal,pm,"to");
        
        //截止时间都在上午上班时间之前
        if(toTime<=fromAm){
        	worktime = 0;
        }else if(fromTime>=toPm){//开始时间在下午下班之后
        	worktime = 0;
        }else if(toTime>fromAm && toTime<=toAm){//截止时间在上午上班时间段内
        	if(fromTime<=fromAm){
        		worktime = toTime - fromAm;
        	}else{
        		worktime = toTime - fromTime;
        	}
        }else if(toTime>toAm && toTime<=fromPm){//截止时间在中午休息时间
        	if(fromTime<=fromAm){
        		worktime = toAm - fromAm;
        	}else if(fromTime>fromAm && fromTime<=toAm){
        		worktime = toAm - fromTime;
        	}else if(fromTime>toAm && fromTime <= fromPm){
        		worktime = 0;
        	}
        }else if(toTime>fromPm && toTime<=toPm){//截止时间在下午上班时间段内
        	if(fromTime<=fromAm){
        		worktime = toAm - fromAm + toTime - fromPm;
        	}else if(fromTime>fromAm && fromTime<=toAm){
        		worktime = toAm - fromTime + toTime - fromPm;
        	}else if(fromTime>toAm && fromTime <= fromPm){
        		worktime = toTime - fromPm;
        	}else if(fromTime>fromPm && fromTime < toPm){
        		worktime = toTime - fromTime;
        	}
        }else if(toTime>toPm){//截止时间在下午下班之后
        	if(fromTime<=fromAm){
        		worktime = toAm - fromAm + toPm - fromPm;
        	}else if(fromTime>fromAm && fromTime<=toAm){
        		worktime = toAm - fromTime + toPm - fromPm;
        	}else if(fromTime>toAm && fromTime <= fromPm){
        		worktime = toPm - fromPm;
        	}else if(fromTime>fromPm && fromTime < toPm){
        		worktime = toPm - fromTime;
        	}
        }
        return worktime;
	}
	
    /**
     * 处理开始时间在节假日的情况
        * @title: dealStartTime
        * @author
        * @description:
        * @date 2015年1月30日 上午10:57:15
        * @param cal
        * @throws
     */
    private void dealStartTime(Calendar cal){
        String key = getDateKey(cal);
        if(holidayMap.containsKey(key)){
        	cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt((am.split("-")[0]).split(":")[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt((am.split("-")[0]).split(":")[1]));
            cal.set(Calendar.SECOND, 0);
            addDay(cal, 1);
        }
    }
    
    /**
     * 处理截止时间不在节假日的情况
     * @param cal
     */
    private void dealEndTime(Calendar cal){
    	String key = getDateKey(cal);
    	if(holidayMap.containsKey(key)){
    		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt((pm.split("-")[1]).split(":")[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt((pm.split("-")[1]).split(":")[1]));
            cal.set(Calendar.SECOND, 0);
    		minusDay(cal, 1);
        }
    }
    
    private String getDateKey(Calendar cal){
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH)+1;
    	int day = cal.get(Calendar.DAY_OF_MONTH);
    	String key = year+"-"+(month>9?month:"0"+month)+"-"+(day>9?day:"0"+day);
    	return key;
    }
    
    /**
     * 计算增加一定天数的日期,考虑节假日
    	* @title: addDay
    	* @author
    	* @description:
    	* @date 2015年1月30日 上午10:42:22
    	* @param cal
    	* @param day
    	* @throws
     */
    private void addDay(Calendar cal,int day){
        for(int i=1;i<=day;i++){
            cal.add(Calendar.DAY_OF_MONTH, 1);
            String key = getDateKey(cal);
            if(holidayMap.containsKey(key)){
                addDay(cal, 1);
            }
        }
    }
    
    /**
     * 计算减去一定天数的日期，考虑节假日
     * @param cal
     * @param day
     */
    private void minusDay(Calendar cal,int day){
    	for(int i=1;i<=day;i++){
            cal.add(Calendar.DAY_OF_MONTH, -1);
            String key = getDateKey(cal);
            if(holidayMap.containsKey(key)){
            	minusDay(cal, 1);
            }
        }
    }  
    private long parseTime(Calendar cal,String timeStr,String formOrTo){
        if(null!=timeStr && !"".equals(timeStr)){
            String from = timeStr.split("-")[0];
            String to = timeStr.split("-")[1];
            Calendar c = Calendar.getInstance();
            c.setTime(cal.getTime());
            if("from".equals(formOrTo)){
                c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(from.split(":")[0]));
                c.set(Calendar.MINUTE, Integer.parseInt(from.split(":")[1]));
                c.set(Calendar.SECOND, 0);
            }else{
                c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(to.split(":")[0]));
                c.set(Calendar.MINUTE, Integer.parseInt(to.split(":")[1]));
                c.set(Calendar.SECOND, 0); 
            }
            return c.getTimeInMillis();
        }
        return 0l;
    }
}
