package com.easub.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.fintech.platform.tools.common.DateUtil;

public class TrackLogUtils {

	public static void getTrackLogFileListByTime(String path,String startTime,String endTime) {
		getTrackLogFileListByTime(path, DateUtil.getDateFromString(startTime), DateUtil.getDateFromString(endTime));
	}
	
	public static void getTrackLogFileListByTime(String path,Date startTime,Date endTime) {
		if(path == null || "".equals(path.trim())) {
			return;
		}
		if(startTime == null) {
			startTime = new Date();
		}
		if(endTime == null) {
			endTime = new Date();
		}
		CalendarUtils.clearHMS(startTime);
		CalendarUtils.clearHMS(endTime);
		
		File dir = new File(path);
		if(!dir.exists()) {
			return;
		}
		if(dir.isDirectory()) {
			File[] fileList = dir.listFiles();
			for(File file : fileList) {
				if(file.isDirectory() || file.isHidden()) {
					continue;
				}
				String fileName = file.getName();
				String extendName = getExtendName(fileName);
				Date tempDate = DateUtil.getDateFromString(extendName);
				if(tempDate == null) {
					continue;
				}
				CalendarUtils.clearHMS(tempDate);
				if(tempDate.getTime() >= startTime.getTime() && tempDate.getTime() <= endTime.getTime()) {
					List<TrackLog> list = parseTrackLogFile(file);
					for(TrackLog l : list) {
						System.out.println(l.getProperty("platformType"));
					}
				}
				
			}
		}
	}
	
	public static void main(String[] args) {
//		String extendName = getExtendName("sa.log.2018-01-08");
//		System.out.println(extendName);
		String path = "C:\\Users\\jiangshuncheng\\Desktop\\项目文件\\埋点文件";
		getTrackLogFileListByTime(path, "2018-01-01", "2018-01-10");
	}
	
	public static String getExtendName(String fileName) {
		if(fileName == null || "".equals(fileName.trim())) {
			return "";
		}
		int index = fileName.lastIndexOf(".");
		if(index == -1) {
			return "";
		}
		String name = fileName.substring(index + 1, fileName.length());
		
		return name;
	}
	
	
	/**
	 * 解析埋点的日志文件
	 * @param file
	 */
	public static List<TrackLog> parseTrackLogFile(File file) {
		
		if(file == null || !file.isFile() || !file.exists()) {
			return new ArrayList();
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			List<TrackLog> trackLogList = new ArrayList();
			
			String line = "";
			while((line = br.readLine()) != null) {
//				System.out.println(line);
//				JSONObject json = JSONObject.parseObject(line);
				TrackLog trackLog = JSONObject.parseObject(line, TrackLog.class);
				
//				net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(line);
//				TrackLog trackLog = (TrackLog) net.sf.json.JSONObject.toBean(jsonObject, TrackLog.class);
				
				trackLogList.add(trackLog);
			}
			return trackLogList;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList();
	}
	
}
