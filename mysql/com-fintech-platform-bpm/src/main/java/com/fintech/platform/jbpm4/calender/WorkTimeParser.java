package com.fintech.platform.jbpm4.calender;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class WorkTimeParser {

	//配置文件
	private String configFile;
	
	//工作时间
	private Map<String,DayModel> worktimes;
	
	//假期
	private List<String> holidays;
	
	private static WorkTimeParser Workinstance = new WorkTimeParser();
	
	/**
	 * 获取工作日历实例<BR>
	 * 默认使用当前构件包的META-INF/user-worktime.xml文件
	 * @return
	 */
	public static WorkTimeParser getInstance(){
		
		return Workinstance;
	}
	
	/**
	 * 构造器
	 * @param configFile 工作日历配置文件路径
	 */
	public WorkTimeParser(String configFile){
		this.configFile=configFile;
		this.worktimes=new HashMap<String,DayModel>();
		this.holidays=new ArrayList<String>();
		parseConfiguration();
		
	}

	private WorkTimeParser(){
		this.worktimes=new HashMap<String,DayModel>();
		this.holidays=new ArrayList<String>();
		parseConfiguration();
	}
	
	public Map<String,DayModel> getWorkTime() {
		return worktimes;
	}

	public List<String> getHolidays() {
		return holidays;
	}
	
	private void parseConfiguration(){
		try {
			Element workday = null;
			Element work_time_am = null;
			Element rest_time_am = null;
			Element work_time_pm = null;
			Element rest_time_pm = null;

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			File worktimeFile = getWorktimeFile();

			Document doc = null;//builder.parse(worktimeFile);
			if(doc == null) return;
			doc.normalize();
			NodeList dayOfWeeklinks = doc.getElementsByTagName("day_of_week");
			for (int i = 0; i < dayOfWeeklinks.getLength(); ++i) {
				DayModel dayModel = new DayModel();
				workday = (Element) dayOfWeeklinks.item(i);
				work_time_am = (Element) workday.getElementsByTagName("work_time_am").item(0);
				rest_time_am = (Element) workday.getElementsByTagName("rest_time_am").item(0);
				work_time_pm = (Element) workday.getElementsByTagName("work_time_pm").item(0);
				rest_time_pm = (Element) workday.getElementsByTagName("rest_time_pm").item(0);
				String a = workday.getAttribute("day");
				dayModel.setDayOfWeek(Integer.parseInt(workday.getAttribute("day")));
				dayModel.setHourOfRestAM(Integer.parseInt(rest_time_am.getFirstChild().getNodeValue()));
				dayModel.setHourOfRestPM(Integer.parseInt(rest_time_pm.getFirstChild().getNodeValue()));
				dayModel.setHourOfWorkAM(Integer.parseInt(work_time_am.getFirstChild().getNodeValue()));
				dayModel.setHourOfWorkPM(Integer.parseInt(work_time_pm.getFirstChild().getNodeValue()));
				dayModel.setMinuteOfRestAM(Integer.parseInt(rest_time_am.getAttribute("minute")));
				dayModel.setMinuteOfRestPM(Integer.parseInt(rest_time_pm.getAttribute("minute")));
				dayModel.setMinuteOfWorkAM(Integer.parseInt(work_time_am.getAttribute("minute")));
				dayModel.setMinuteOfWorkPM(Integer.parseInt(work_time_pm.getAttribute("minute")));
				dayModel.setWorkTimeOfDay(Integer.parseInt(workday.getAttribute("worktime_minute")));

				worktimes.put(workday.getAttribute("day"), dayModel);
			}
			
			NodeList dayOfMonthLinks = doc.getElementsByTagName("day_of_month");
			for (int i = 0; i < dayOfMonthLinks.getLength(); ++i) {
				holidays.add(((Element) dayOfMonthLinks.item(i)).getAttribute("date"));
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			System.out.println("请检查“user-worktime.xml”文件的正确性，您需要确定可能有以下几项：");
			System.out.println("1.日期、月份、星期、小时、分钟等各种属性的值是否为数值！为非数值的话将会引起时间计算异常。");
			System.out.println("2.是否有重复的值，这会造成输出结果不正确。");
			System.out.println("3.设定小时时请使用24小时格式，如下午3点请设为15点。");
			System.out.println("4.月份全部从1开始，如设定1为1月份，2为2月份，依此类推。");
			System.out.println("5.日期全部从1开始，如设定1为1号，2为2号，依此类推。");
			System.out.println("6.月份与日期之间用“/”隔开。");
			System.out.println("7.星期全部从1开始，如设定1为星期一，2为星期二，依此类推。");
			System.out.println("8.请按照范例填写各个属性值。");
			System.out.println("9.各个值的范例是否在规定范围内，月份不能大于12,星期不能大于7,小时不能大于24,分钟不能大于等于60等等！");
			System.out.println("");
		}
	}
	
	public void setConfigFile(String configFile){
		this.configFile=configFile;
	}


	private File getWorktimeFile() {
		if(configFile==null){
			URL contributionFile = WorkTimeParser.class.getResource("user-worktime.xml");
			//LoggerUtil.info(this.getClass(), "---------------"+contributionFile.getFile());
			File file =   new File(contributionFile.getFile());
			return file;
		}else{
			return new File(configFile);
		}
	}

	public static void main(String[] args) throws ParseException {
		/*WorkTimeParser parser = WorkTimeParser.getInstance();
		
		List<String> list = parser.getHolidays();
		System.out.println(list.size());
		*/
		/*String endDay = "2014-06-04 16:00:00";
		
		String startDay = "2014-06-03 08:30:00";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date end = sdf.parse(endDay) ; 
		Date start = sdf.parse(startDay);
		
		//工作结束时间
		Calendar endCalendar  = Calendar.getInstance();
		endCalendar.setTime(end);
		//工作开始时间
		Calendar startCalendar  = Calendar.getInstance();
		startCalendar.setTime(start);
		
		CalWorkTime calWork = CalWorkTime.getInstance();
		
		int total = calWork.calcWorkingTime(startCalendar);
		
		System.out.println("距离当前时间的工作分钟数为："+total);
		
		CalWorkTime myCalWork = new CalWorkTime(endCalendar);
		int myTotal = myCalWork.calcWorkingTime(startCalendar);
		
		System.out.println("距离指定工作结束时间的工作分钟数为myTotal:"+myTotal+"----");*/
	}

}