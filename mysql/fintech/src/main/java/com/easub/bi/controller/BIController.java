package com.easub.bi.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.easub.analysis.service.impl.ClipAnalysisService;
import com.easub.bi.BIConfig;
import com.easub.bi.Constant;
import com.easub.bi.dto.ClipUsersDTO;
import com.easub.bi.dto.ShopsDTO;
import com.easub.bi.excel.ExcelHelper;
import com.easub.bi.service.IBIService;
import com.easub.bi.service.IClipUsersService;
import com.easub.biextra.service.IBIExtraService;
import com.easub.clip.dto.ClipVideoCopyrightSummaryStatDTO;
import com.easub.clip.dto.ClipVideoSummaryStatDTO;
import com.easub.clip.dto.ClipVideoSummaryStatDetailDTO;
import com.easub.clip.service.ClipVideoCopyrightSummaryStatService;
import com.easub.clip.service.ClipVideoSummaryStatDetailService;
import com.easub.clip.service.ClipVideoSummaryStatService;
import com.easub.utils.BaseUtils;
import com.easub.utils.CalendarUtils;
import com.easub.utils.TrackLog;
import com.easub.utils.TrackLogUtils;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.restservice.web.base.BaseController;
import com.fintech.platform.tools.common.DateUtil;
import com.sun.tools.javac.util.Abort;

import net.sf.json.JSONObject;


@SuppressWarnings({"unchecked","rawtypes"})
@Controller
@RequestMapping("/bi")
public class BIController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(BIController.class);
	
	@Resource(name="com.easub.bi.service.impl.BIService")
	private IBIService biService;
	@Resource(name="com.easub.biextra.service.impl.BIExtraService")
	private IBIExtraService biExtraService;
	
	@Resource(name="com.easub.bi.service.impl.ClipUsersService")
	private IClipUsersService clipUsersService;
	@Resource(name="com.easub.clip.service.ClipVideoSummaryStatService")
	private ClipVideoSummaryStatService clipVideoSummaryStatService;
	@Resource(name="com.easub.clip.service.ClipVideoSummaryStatDetailService")
	private ClipVideoSummaryStatDetailService clipVideoSummaryStatDetailService;
	@Resource(name="com.easub.clip.service.ClipVideoCopyrightSummaryStatService")
	private ClipVideoCopyrightSummaryStatService clipVideoCopyrightSummaryStatService;
	@Resource(name="com.easub.analysis.service.impl.ClipAnalysisService")
	private ClipAnalysisService clipAnalysisService;
	
	
	@RequestMapping(value = "/prepareExecute/{operate}") 
    public ModelAndView execute(@PathVariable("operate") String operate) throws Exception  {
		ModelAndView model = new ModelAndView();
		if("biList".equals(operate)) {
			//统计分析列表页面
			model.setViewName("bi/biList");
		} else if ("toVideoShopStat".equals(operate)) {
			//跳转至按商户（版权方）统计视频数
			model.setViewName("bi/toVideoShopStat");
		} else if ("toVideoCopyrightStat".equals(operate)) {
			//跳转至按版权统计视频数和视频时长
			model.setViewName("bi/toVideoCopyrightStat");
		} else if ("toVideosSourceTypeStatCount".equals(operate)) {
			//按视频类型统计时间段内的视频数
			//获取当前日期上周的周四和上上周的周五（周五至下周四为1周）
			//每周从周日开始，周末结束
			Calendar cal = Calendar.getInstance();
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//当前周几
			//上周的周四
			cal.add(Calendar.DATE, Calendar.SUNDAY - dayOfWeek - 3);//上周四
			Date endTime = cal.getTime();
			//上上周的周五
			cal.add(Calendar.DATE, -6);//上上周的周五
			Date startTime = cal.getTime();
			model.addObject("startTime", DateUtil.format(startTime, "yyyy-MM-dd"));
			model.addObject("endTime", DateUtil.format(endTime, "yyyy-MM-dd"));
			model.setViewName("bi/toVideosSourceTypeStatCount");
		} else if ("toVideosSourceTypeShareStatCount".equals(operate)) {
			//按视频类型统计时间段内的视频数
			//获取当前日期上周的周四和上上周的周五（周五至下周四为1周）
			//每周从周日开始，周末结束
			Calendar cal = Calendar.getInstance();
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//当前周几
			//上周的周四
			cal.add(Calendar.DATE, Calendar.SUNDAY - dayOfWeek - 3);//上周四
			Date endTime = cal.getTime();
			//上上周的周五
			cal.add(Calendar.DATE, -6);//上上周的周五
			Date startTime = cal.getTime();
			model.addObject("startTime", DateUtil.format(startTime, "yyyy-MM-dd"));
			model.addObject("endTime", DateUtil.format(endTime, "yyyy-MM-dd"));
			model.setViewName("bi/toVideosSourceTypeShareStatCount");
		} else if ("toCloudClipWeeklyStat".equals(operate)) {
			//云剪业务周报
			//获取当前日期上周的周四和上上周的周五（周五至下周四为1周）
			//每周从周日开始，周末结束
			Calendar cal = Calendar.getInstance();
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//当前周几
			//上周的周四
			cal.add(Calendar.DATE, Calendar.SUNDAY - dayOfWeek - 3);//上周四
			Date endTime = cal.getTime();
			//上上周的周五
			cal.add(Calendar.DATE, -6);//上上周的周五
			Date startTime = cal.getTime();
			model.addObject("startTime", DateUtil.format(startTime, "yyyy-MM-dd"));
			model.addObject("endTime", DateUtil.format(endTime, "yyyy-MM-dd"));
			model.setViewName("bi/toCloudClipWeeklyStat");
		} else if ("toClipVideoCumulativeDataStat".equals(operate)) {
			//统计云剪累计视频数据
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);//默认上一天
			model.addObject("endTime", DateUtil.format(cal.getTime(), "yyyy-MM-dd"));
			model.setViewName("bi/toClipVideoCumulativeDataStat");
		}
		return model;
	}
	
	
//	@RequestMapping("/biList")
//	public String biList(HttpServletRequest request) {
//		return "bi/biList";
//	}
	
	@ResponseBody
	@RequestMapping("/getBiList")
	public DataMsg getBiList(HttpServletRequest request) {
		DataMsg dataMsg = new DataMsg();
		try {
			//从数据库中查询报表列表
			List list = new ArrayList();
			
			//暂时写死。。后续可能需要存储到数据库中
			Map map = new HashMap();
//			map.put("id", 1);
//			map.put("name", "统计视频每周分享转发播放数");
//			map.put("des", "统计视频每周分享转发播放数");
//			list.add(map);
//			map = new HashMap();
//			map.put("id", 2);
//			map.put("name", "按用户类型统计每周开通账号数");
//			map.put("des", "按用户类型统计每周开通账号数");
//			list.add(map);
			map = new HashMap();
			map.put("id", 3);
			map.put("name", "按版权方统计视频数");
			map.put("des", "按版权方统计视频数");
			list.add(map);
			map = new HashMap();
			map.put("id", 4);
			map.put("name", "按版权统计视频数");
			map.put("des", "按版权统计视频数");
			list.add(map);
			map = new HashMap();
			map.put("id", 5);
			map.put("name", "按视频类型统计时间段内的视频数");
			map.put("des", "按视频类型统计时间段内的视频数");
			list.add(map);
			map = new HashMap();
			map.put("id", 6);
			map.put("name", "按视频类型统计时间段内的分享数");
			map.put("des", "按视频类型统计时间段内的分享数");
			list.add(map);
			map = new HashMap();
			map.put("id", 7);
			map.put("name", "时间段内的云剪业务周报");
			map.put("des", "时间段内的云剪业务周报");
			list.add(map);
//			int id = 8;
//			map = new HashMap();
//			map.put("id", id++);
//			map.put("name", "按时间段统计视频数量趋势图");
//			map.put("des", "按时间段统计视频数量趋势图");
//			list.add(map);
//			map = new HashMap();
//			map.put("id", id++);
//			map.put("name", "按时间段统计视频分享数量趋势图");
//			map.put("des", "按时间段统计视频分享数量趋势图");
//			list.add(map);
			
			dataMsg.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			dataMsg.failed(e.getMessage());
			logger.error("", e);;
		}
		return dataMsg;
	}
	
//	@RequestMapping("/toBiDetailPage")
//	public String toBiDetailPage(HttpServletRequest request) {
//		//获取当前日期上周的周四和上上周的周五（周五至下周四为1周）
//		//每周从周日开始，周末结束
//		Calendar cal = Calendar.getInstance();
//		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//当前周几
//		//上周的周四
//		cal.add(Calendar.DATE, Calendar.SUNDAY - dayOfWeek - 3);//上周四
//		Date endTime = cal.getTime();
//		//上上周的周五
//		cal.add(Calendar.DATE, -6);//上上周的周五
//		Date startTime = cal.getTime();
//		
//		request.setAttribute("startTime", DateUtil.format(startTime, "yyyy-MM-dd"));
//		request.setAttribute("endTime", DateUtil.format(endTime, "yyyy-MM-dd"));
//		
//		return "bi/toBiDetailPage";
//	}
	
	
	@ResponseBody
	@RequestMapping("/getVideoShopStat")
	public DataMsg getVideoShopStat(HttpServletRequest request) {
		//按版权统计视频数
		DataMsg dataMsg = new DataMsg();
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			Map<String,Object> conditions = new HashMap();
			
			List<ShopsDTO> shopsList = this.biService.getShopsList(conditions);
			List<Map<String,Object>> list = this.biService.getVideoShopStat(conditions);
			Map<Integer,ShopsDTO> shopsMap = new HashMap();
			for(ShopsDTO shop : shopsList) {
				shopsMap.put(shop.getId(), shop);
			}
			List dataList = new ArrayList();
			for(Map<String,Object> map : list) {
				int id = BaseUtils.parseToInt(map.get("shop_id"));//商户ID
				int count = BaseUtils.parseToInt(map.get("c"));
				ShopsDTO shop = shopsMap.get(id);
				if(shop == null) {
					continue;
				}
				Map<String,Object> tempMap = new HashMap();
				tempMap.put("id", id);
				tempMap.put("name", shop.getName());
				tempMap.put("showName", shop.getShowName());
				tempMap.put("count", count);
				dataList.add(tempMap);
			}
			dataMsg.setData(dataList);
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			e.printStackTrace();
		}
		return dataMsg;
	}
	
	@ResponseBody
	@RequestMapping("/getVideoCopyrightStat")
	public DataMsg getVideoCopyrightStat(HttpServletRequest request) {
		//按版权统计视频数
		DataMsg dataMsg = new DataMsg();
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			
			List<Map<String,Object>> list = this.biService.getVideoCopyrightStat(startTime, endTime);
			
			List dataList = new ArrayList();
			for(Map<String,Object> map : list) {
				Object oIsAbroad = map.get("abroad");//0(false)为国内视频，1(true)为国外视频
				if(oIsAbroad == null || "".equals(oIsAbroad.toString().trim())) {
					continue;
				}
				int abroad = BaseUtils.parseToInt(oIsAbroad);
				int count = BaseUtils.parseToInt(map.get("c"));
				double second = BaseUtils.parseToDouble(map.get("sec"));//视频时长，秒
				String abroadName = "";
				if(abroad == 1) {
					abroadName = "国外版权";
				}else{
					abroadName = "国内版权";
				}
				
				Map<String,Object> tempMap = new HashMap();
				tempMap.put("abroad", abroad);
				tempMap.put("name", abroadName);
				tempMap.put("count", count);
				tempMap.put("hour", Math.round(second / 3600.0));//换成小时，四舍五入
//				dataMap.put(isAbroad +"", tempMap);
				dataList.add(tempMap);
			}
			dataMsg.setData(dataList);
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			e.printStackTrace();
		}
		return dataMsg;
	}
	
	@ResponseBody
	@RequestMapping("/getVideosSourceTypeStatCount")
	public DataMsg getVideosSourceTypeStatCount(HttpServletRequest request) {
		//按视频类型统计时间段内的视频数
		DataMsg dataMsg = new DataMsg();
		try {
			//0上传,1拉取（youtube）,2剪辑,3直播,4直播剪辑,5广告,6混剪（合集）,7购买,8普通图片,9 360图片,10导播直播,11视频共享,
			//12私库共享,13公共库共享,14微博接入,15
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			if(startTime == null || "".equals(startTime.trim()) || endTime == null || "".equals(endTime)) {
				//获取当前日期上周的周四和上上周的周五（周五至下周四为1周）
				//每周从周日开始，周末结束
				Calendar cal = Calendar.getInstance();
				int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//当前周几
				//上周的周四
				cal.add(Calendar.DATE, Calendar.SUNDAY - dayOfWeek - 3);//上周四
				Date endTime1 = cal.getTime();
				//上上周的周五
				cal.add(Calendar.DATE, -6);//上上周的周五
				Date startTime1 = cal.getTime();
				startTime = DateUtil.format(startTime1, "yyyy-MM-dd");
				endTime = DateUtil.format(endTime1, "yyyy-MM-dd");
			}
			long dayCount = DateUtil.daysOfTwo(endTime, startTime) + 1;
			if(dayCount < 0) {
				dayCount = 0;
			}
			
			List<Map<String,Object>> list = this.biService.getVideosSourceTypeStatCount(startTime, endTime);
			Map<String,Integer> dataMap = new HashMap();
			Map<Integer,Map<String,Integer>> tempMap = new HashMap();
			Set<String> sourceTypeNameSet = new HashSet();
			Map<String,String> sourceTypeMap = new HashMap();
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_UPLOAD +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_UPLOAD));
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_PULL +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_PULL));
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_CLIP +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_CLIP));
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_LIVE_BROADCAST +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_LIVE_BROADCAST));
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_LIVE_BROADCAST_CLIP +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_LIVE_BROADCAST_CLIP));
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_MIXING_CLIP +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_MIXING_CLIP));
			Integer[] typeArr = {1,2,3,4};
			String[] typeNameArr = {"全部","iss、ims除外","新浪","政务媒体"};
			for(Map<String,Object> map : list) {
				Object oSourceType = map.get("sourceType");
				Object oType = map.get("type");//1全部、2除了ims\iss、3新浪、4政务媒体
				Object oCount = map.get("c");
				if(oSourceType == null || "".equals(oSourceType.toString().trim())) {
					continue;
				}
				if(oCount == null || "".equals(oSourceType.toString().trim())) {
					oCount = 0;
				}
				int sourceType = Integer.parseInt(oSourceType.toString());
				int type = Integer.parseInt(oType.toString());
				int count = Integer.parseInt(oCount.toString());
				
				String sourceTypeName = Constant.Videos.sourceTypeMap.get(sourceType);
				if(sourceTypeName == null) {
					sourceTypeName = "其他";
				}
				sourceTypeNameSet.add(sourceTypeName);
				
				sourceTypeMap.put(sourceType +"", sourceTypeName);
				
				String key = type + "_" + sourceType;
				Integer t = dataMap.get(key);
				if(t == null) {
					t = 0;
				}
				dataMap.put(key, t + count);
				
				//表格数据
				Map m = tempMap.get(sourceType);
				if(m == null) {
					m = new HashMap();
					m.put("sourceTypeName", "");
					m.put("allCount", 0);
					m.put("allAvg", 0);
					m.put("issImsCount", 0);
					m.put("issImsAvg", 0);
					m.put("sinaCount", 0);
					m.put("sinaAvg", 0);
					m.put("zwCount", 0);
					m.put("zwAvg", 0);
				}
				m.put("sourceTypeName", sourceTypeName);
				int avg = dayCount == 0 ? 0 : Math.round(count/dayCount);
				switch (type) {
				case 1:
					m.put("allCount", count);
					m.put("allAvg", avg);
					break;
				case 2:
					m.put("issImsCount", count);
					m.put("issImsAvg", avg);
					break;
				case 3:
					m.put("sinaCount", count);
					m.put("sinaAvg", avg);
					break;
				case 4:
					m.put("zwCount", count);
					m.put("zwAvg", avg);
					break;
				}
				tempMap.put(sourceType, m);
			}
			List tableDataList = new ArrayList(tempMap.values());
			Map data = new HashMap();
			data.put("sourceTypeMap", sourceTypeMap);
			data.put("dataMap", dataMap);
			data.put("typeArr", typeArr);
			data.put("typeNameArr", typeNameArr);
			data.put("sourceTypeNameSet", sourceTypeMap.values());
			data.put("tableDataList", tableDataList);
			data.put("startTime", startTime);
			data.put("endTime", endTime);
			dataMsg.setData(data);
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			e.printStackTrace();
		}
		return dataMsg;
	}
	
	@ResponseBody
	@RequestMapping("/getVideosSourceTypeShareStatCount")
	public DataMsg getVideosSourceTypeShareStatCount(HttpServletRequest request) {
		//按视频类型统计时间段内的视频数
		DataMsg dataMsg = new DataMsg();
		try {
			//0上传,1拉取（youtube）,2剪辑,3直播,4直播剪辑,5广告,6混剪（合集）,7购买,8普通图片,9 360图片,10导播直播,11视频共享,
			//12私库共享,13公共库共享,14微博接入,15
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			if(startTime == null || "".equals(startTime.trim()) || endTime == null || "".equals(endTime)) {
				//获取当前日期上周的周四和上上周的周五（周五至下周四为1周）
				//每周从周日开始，周末结束
				Calendar cal = Calendar.getInstance();
				int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//当前周几
				//上周的周四
				cal.add(Calendar.DATE, Calendar.SUNDAY - dayOfWeek - 3);//上周四
				Date endTime1 = cal.getTime();
				//上上周的周五
				cal.add(Calendar.DATE, -6);//上上周的周五
				Date startTime1 = cal.getTime();
				startTime = DateUtil.format(startTime1, "yyyy-MM-dd");
				endTime = DateUtil.format(endTime1, "yyyy-MM-dd");
			}
			long dayCount = DateUtil.daysOfTwo(endTime, startTime) + 1;
			if(dayCount < 0) {
				dayCount = 0;
			}
			Map<String,Object> conditions = new HashMap();
			Date sTime = DateUtil.getDateFromString(startTime, "yyyy-MM-dd");
			if(sTime != null) {
				conditions.put("startTime", sTime.getTime()/1000);//秒
			}
			Date eTime = DateUtil.getDateFromString(endTime, "yyyy-MM-dd");
			if(eTime != null) {
				conditions.put("endTime", eTime.getTime()/1000);
			}
			List<Map<String,Object>> list = this.biService.getVideosSourceTypeShareStatCount(conditions);
			Map<String,Integer> dataMap = new HashMap();
			Map<Integer,Map<String,Integer>> tempMap = new HashMap();
			Set<String> sourceTypeNameSet = new HashSet();
			Map<String,String> sourceTypeMap = new HashMap();
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_UPLOAD +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_UPLOAD));
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_PULL +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_PULL));
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_CLIP +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_CLIP));
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_LIVE_BROADCAST +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_LIVE_BROADCAST));
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_LIVE_BROADCAST_CLIP +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_LIVE_BROADCAST_CLIP));
			sourceTypeMap.put(Constant.Videos.SOURCE_TYPE_MIXING_CLIP +"", Constant.Videos.sourceTypeMap.get(Constant.Videos.SOURCE_TYPE_MIXING_CLIP));
			Integer[] typeArr = {1,2,3,4};
			String[] typeNameArr = {"全部","iss、ims除外","新浪","政务媒体"};
			for(Map<String,Object> map : list) {
				Object oSourceType = map.get("sourceType");
				Object oType = map.get("type");//1全部、2除了ims\iss、3新浪、4政务媒体
				Object oCount = map.get("c");
				if(oSourceType == null || "".equals(oSourceType.toString().trim())) {
					continue;
				}
				if(oCount == null || "".equals(oSourceType.toString().trim())) {
					oCount = 0;
				}
				int sourceType = Integer.parseInt(oSourceType.toString());
				int type = Integer.parseInt(oType.toString());
				int count = Integer.parseInt(oCount.toString());
				
				String sourceTypeName = Constant.Videos.sourceTypeMap.get(sourceType);
				if(sourceTypeName == null) {
					sourceTypeName = "其他";
				}
				sourceTypeNameSet.add(sourceTypeName);
				
				sourceTypeMap.put(sourceType +"", sourceTypeName);
				
				String key = type + "_" + sourceType;
				Integer t = dataMap.get(key);
				if(t == null) {
					t = 0;
				}
				dataMap.put(key, t + count);
				
				
				//表格数据
				Map m = tempMap.get(sourceType);
				if(m == null) {
					m = new HashMap();
					m.put("sourceTypeName", "");
					m.put("allCount", 0);
					m.put("allAvg", 0);
					m.put("issImsCount", 0);
					m.put("issImsAvg", 0);
					m.put("sinaCount", 0);
					m.put("sinaAvg", 0);
					m.put("zwCount", 0);
					m.put("zwAvg", 0);
				}
				m.put("sourceTypeName", sourceTypeName);
				int avg = dayCount == 0 ? 0 : Math.round(count/dayCount);
				switch (type) {
				case 1:
					m.put("allCount", count);
					m.put("allAvg", avg);
					break;
				case 2:
					m.put("issImsCount", count);
					m.put("issImsAvg", avg);
					break;
				case 3:
					m.put("sinaCount", count);
					m.put("sinaAvg", avg);
					break;
				case 4:
					m.put("zwCount", count);
					m.put("zwAvg", avg);
					break;
				}
				tempMap.put(sourceType, m);
			}
			List tableDataList = new ArrayList(tempMap.values());
			Map data = new HashMap();
			data.put("sourceTypeMap", sourceTypeMap);
			data.put("dataMap", dataMap);
			data.put("typeArr", typeArr);
			data.put("typeNameArr", typeNameArr);
			data.put("sourceTypeNameSet", sourceTypeMap.values());
			data.put("tableDataList", tableDataList);
			data.put("startTime", startTime);
			data.put("endTime", endTime);
			dataMsg.setData(data);
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return dataMsg;
	}
	
	@ResponseBody
	@RequestMapping("/getCloudClipWeeklyStatData")
	public DataMsg getCloudClipWeeklyStatData(HttpServletRequest request) {
		DataMsg dataMsg = new DataMsg();
		try {
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			long b = System.currentTimeMillis();
			System.out.println("============start:" + b);
			Map dataMap = this.getCloudClipWeeklyData(startTime, endTime);
			System.out.println("============end:" + (System.currentTimeMillis() - b));
			dataMsg.setData(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMsg.failed(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return dataMsg;
	}
	
	private Map getCloudClipWeeklyData(String sStartTime,String sEndTime) throws Exception{
		Calendar cal = Calendar.getInstance();
		CalendarUtils.clearHMS(cal);
		if(sStartTime == null || "".equals(sStartTime.trim()) || sEndTime == null || "".equals(sEndTime)) {
			//获取当前日期上周的周四和上上周的周五（周五至下周四为1周）
			//每周从周日开始，周末结束
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//当前周几
			//上周的周四
			cal.add(Calendar.DATE, Calendar.SUNDAY - dayOfWeek - 3);//上周四
			Date endTime1 = cal.getTime();
			//上上周的周五
			cal.add(Calendar.DATE, -6);//上上周的周五
			Date startTime1 = cal.getTime();
			sStartTime = DateUtil.format(startTime1, "yyyy-MM-dd");
			sEndTime = DateUtil.format(endTime1, "yyyy-MM-dd");
		}
		Date startTime = DateUtil.getDateFromString(sStartTime, "yyyy-MM-dd");
		Date endTime = DateUtil.getDateFromString(sEndTime, "yyyy-MM-dd");
		cal.setTime(endTime);
		cal.add(Calendar.DATE, 1);
		Date nextEndTime = cal.getTime();//下一天，日期判断小于下一天
		
		//获取开始时间的上一周数据
		cal.setTime(startTime);
		cal.add(Calendar.DATE, -1);
		Date lastEndTime = cal.getTime();
		cal.add(Calendar.DATE, -6);
		Date lastStartTime = cal.getTime();
		Map<String,Object> conditions = new HashMap();
		
		//这个时间段内新增的账号数
		conditions.clear();
//		conditions.put("userPoliciesState", 1);
//		conditions.put("startCreatedTime", startTime.getTime()/1000);
//		conditions.put("endCreatedTime", nextEndTime.getTime()/1000);
//		Integer newAddUserCount = this.clipUsersService.getUsersAccountCount(conditions);
		conditions.put("isStartUsing", 1);
		conditions.put("notUserType", "'admin','dev','test'");
		conditions.put("startTime", startTime.getTime()/1000);
		conditions.put("endTime", nextEndTime.getTime()/1000);
		Integer newAddUserCount = this.clipUsersService.getUserOperationLogsCount(conditions);
		
		//本周云剪后台数据
		conditions.clear();
//		conditions.put("startTime", sStartTime);
//		conditions.put("endTime", sEndTime);
//		//本周云剪数据key=thisWeek_sourceType/thisWeekNoIssIms_sourceType
//		Map<String,Integer> thisClipDataMap = this.getClipWeeklyData(conditions);
//		//本周云剪分享数据key=thisWeek_sourceType/thisWeekNoIssIms_sourceType
//		Map<String,Integer> thisClipShareDataMap = this.getClipWeeklyShareData(conditions);
//		
//		System.out.println("========本周");
//		//上周
//		conditions.clear();
//		conditions.put("startTime", CalendarUtils.format(lastStartTime));
//		conditions.put("endTime", CalendarUtils.format(lastEndTime));
//		Map<String,Integer> lastClipDataMap = this.getClipWeeklyData(conditions);
//		Map<String,Integer> lastClipShareDataMap = this.getClipWeeklyShareData(conditions);
//		System.out.println("========上周");
		
		//从统计库中查询================
		Map<String,Integer> thisClipDataMap = new HashMap();
		Map<String,Integer> thisClipShareDataMap = new HashMap();
		Map<String,Integer> lastClipDataMap = new HashMap();
		Map<String,Integer> lastClipShareDataMap = new HashMap();
		//本周视频数量和共享数量
		this.setClipStatData(sStartTime,sEndTime,thisClipDataMap,thisClipShareDataMap);
		//上周视频数量和共享数量
		this.setClipStatData(CalendarUtils.format(lastStartTime),CalendarUtils.format(lastEndTime),lastClipDataMap,lastClipShareDataMap);
		System.out.println("===============视频数和共享数量=============");
		
		//活跃用户，读取埋点文件2.0
		long dayCount = DateUtil.daysOfTwo(sEndTime, sStartTime) + 1;
		if(dayCount < 0) {
			dayCount = 0;
		}
		String path = BIConfig.getValue("FRONTEND_TRACK_LOG_PATH");;
 		Map<Date,List<TrackLog>> trackLogMap = TrackLogUtils.getTrackLogFileListByTime(path, sStartTime, sEndTime);
		Set<String> distinctIdSet = new HashSet();
		
		Map<Date,Set<String>> totalDistinctCountMap = new HashMap();
		for(Date trackLogDate : trackLogMap.keySet()) {
			List<TrackLog> logList = trackLogMap.get(trackLogDate);
			Set<String> set = totalDistinctCountMap.get(trackLogDate);
			if(set == null) {
				set = new HashSet();
			}
			for(TrackLog log : logList) {
				if(log.getDistinctId() != null && "page_visit".equalsIgnoreCase(log.getEvent()) && log.getDistinctId().trim().length() == 36) {
					//登录用户
					distinctIdSet.add(log.getDistinctId().trim());
					set.add(log.getDistinctId().trim());
				}
			}
			totalDistinctCountMap.put(trackLogDate, set);
		}
		int totalDistinctCount = 0;
		for(Date trackLogDate : totalDistinctCountMap.keySet()) {
			Set<String> set = totalDistinctCountMap.get(trackLogDate);
			totalDistinctCount += set.size();
		}
		
		int activeUserCount = distinctIdSet.size();
		int activeAvgUserCount = dayCount == 0 ? 0 : (int)(totalDistinctCount/dayCount);//几天的用户总数/7
		//1.0的埋点日志
		String path1_0 = BIConfig.getValue("FRONTEND_TRACK_LOG_PATH_1_0");
		Map<Date,List<TrackLog>> trackLogMap1 = TrackLogUtils.getTrackLogFileListByTime(path1_0, sStartTime, sEndTime);
		Set<String> distinctIdSet1 = new HashSet();
		Map<Date,Set<String>> totalDistinctCountMap1 = new HashMap();
		for(Date trackLogDate : trackLogMap1.keySet()) {
			List<TrackLog> logList = trackLogMap1.get(trackLogDate);
			Set<String> set = totalDistinctCountMap1.get(trackLogDate);
			if(set == null) {
				set = new HashSet();
			}
			for(TrackLog log : logList) {
				if(log.getDistinctId() != null && "page_visit".equalsIgnoreCase(log.getEvent()) && log.getDistinctId().trim().length() == 36) {
					distinctIdSet1.add(log.getDistinctId().trim());
					set.add(log.getDistinctId().trim());
				}
			}
			totalDistinctCountMap.put(trackLogDate, set);
		}
		int totalDistinctCount_1_0 = 0;
		for(Date trackLogDate : totalDistinctCountMap.keySet()) {
			Set<String> set = totalDistinctCountMap.get(trackLogDate);
			totalDistinctCount_1_0 += set.size();
		}
		int activeUserCount_1_0 = distinctIdSet1.size();
		int activeAvgUserCount_1_0 = dayCount == 0 ? 0 : (int)(totalDistinctCount_1_0/dayCount);
		
		Set<String> allDistinctIdSet = new HashSet();
		allDistinctIdSet.addAll(distinctIdSet);
		allDistinctIdSet.addAll(distinctIdSet1);
		int allActiveUserCount = allDistinctIdSet.size();
		int allActiveAvgUserCount = dayCount == 0 ? 0 : (int)((totalDistinctCount + totalDistinctCount_1_0)/dayCount);
				
		//视频分享数
		conditions.clear();
		//秒
//		conditions.put("startShareTime", startTime.getTime()/1000);//秒
//		conditions.put("endShareTime", nextEndTime.getTime()/1000);
//		conditions.put("state", Constant.Videos.SHARE_STATE_SHARE_SUCESS);
//		conditions.put("sourceTypes", "0,1,2,3,4,6");
//		Integer videoShareCount1 = this.biService.getVideosShareStatCount(conditions);//本周共享视频
//		Integer videoExtraShareCount = this.biExtraService.getVideosShareStatCount(conditions);
//		Integer videoShareCount = videoShareCount1 + videoExtraShareCount;
		
		int[] sourceTypeArr = {0,1,2,3,4,6};//上传、拉取、剪辑、直播、直播剪辑、混剪
		int videoShareCount = 0;
		for(int st : sourceTypeArr) {
			String key = "1_" + st;
			Integer count = thisClipShareDataMap.get(key);
			videoShareCount += BaseUtils.parseToInt(count);
		}
		System.out.println("=====视频分享数" + videoShareCount);
		int videoAvgShareCount = dayCount == 0 ? 0 : (int)(videoShareCount/dayCount);
		
		//视频播放量
		conditions.clear();
		conditions.put("startTime", startTime.getTime()/1000);
		conditions.put("endTime",  nextEndTime.getTime()/1000);
//		Long videoVVCount1 = this.biService.getVideoVV(conditions);
//		Long extraVVCount = this.biExtraService.getVideoVV(conditions);
//		Long videoVVCount = videoVVCount1 + extraVVCount;
		Long videoVVCount = BaseUtils.parseToLong(this.clipAnalysisService.getVideoVV(conditions));
		long videoAvgVVCount = dayCount == 0 ? 0 : (videoVVCount/dayCount);
		System.out.println("=====视频播放量");
		Map dataMap = new HashMap();
		dataMap.put("newAddUserCount", newAddUserCount);
		dataMap.put("thisClipDataMap", thisClipDataMap);
		dataMap.put("thisClipShareDataMap", thisClipShareDataMap);
		dataMap.put("lastClipDataMap", lastClipDataMap);
		dataMap.put("lastClipShareDataMap", lastClipShareDataMap);
		
		dataMap.put("activeUserCount", activeUserCount);
		dataMap.put("activeAvgUserCount", activeAvgUserCount);
		dataMap.put("activeUserCount_1_0", activeUserCount_1_0);
		dataMap.put("activeAvgUserCount_1_0", activeAvgUserCount_1_0);
		dataMap.put("allActiveUserCount", allActiveUserCount);
		dataMap.put("allActiveAvgUserCount", allActiveAvgUserCount);
		dataMap.put("videoShareCount", videoShareCount);
		dataMap.put("videoAvgShareCount", videoAvgShareCount);
		dataMap.put("videoVVCount", videoVVCount);
		dataMap.put("videoAvgVVCount", videoAvgVVCount);
		
		return dataMap;
	}
	
	//云剪周报数据
	private Map<String,Integer> getClipWeeklyData(Map<String,Object> conditions) throws Exception{
		if(conditions == null) {
			return new HashMap();
		}
		String sStartTime = BaseUtils.parseToString(conditions.get("startTime"));
		String sEndTime = BaseUtils.parseToString(conditions.get("endTime"));
		Date startTime = DateUtil.getDateFromString(sStartTime, "yyyy-MM-dd");
		Date endTime = DateUtil.getDateFromString(sEndTime, "yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(endTime);
		cal.add(Calendar.DATE, 1);
		Date nextEndTime = cal.getTime();
		
		Map<String,Object> map = new HashMap();
		map.put("startTime", startTime.getTime()/1000);
		map.put("endTime", nextEndTime.getTime()/1000);
		List<Map<String,Object>> clipDataList = this.biService.getVideosSourceTypeStatCount(sStartTime, sEndTime);
		List<Map<String,Object>> clipExtraDataList = this.biExtraService.getVideosSourceTypeStatCount(map);
		List<Map<String,Object>> allClipDataList = new ArrayList();
		allClipDataList.addAll(clipDataList);
		allClipDataList.addAll(clipExtraDataList);
		
		Map<String,Integer> clipDataMap = new HashMap();
		for(Map<String,Object> m : allClipDataList) {
			int sourceType = BaseUtils.parseToInt(m.get("sourceType"));
			int type = BaseUtils.parseToInt(m.get("type"));////1全部、2除了ims\iss、3新浪、4政务媒体
			int count = BaseUtils.parseToInt(m.get("c"));
			if(sourceType == Constant.Videos.SOURCE_TYPE_UPLOAD || sourceType == Constant.Videos.SOURCE_TYPE_PULL) {
				sourceType = Constant.Videos.SOURCE_TYPE_UPLOAD;//上传和拉取汇总到上传
			}else if(sourceType == Constant.Videos.SOURCE_TYPE_CLIP || sourceType == Constant.Videos.SOURCE_TYPE_MIXING_CLIP) {
				sourceType = Constant.Videos.SOURCE_TYPE_CLIP;//剪辑和混剪汇总到剪辑
			}
			String key = type + "_" + sourceType;
			Integer tempCount = clipDataMap.get(key);
			if(tempCount == null) {
				tempCount = 0;
			}
			clipDataMap.put(key, tempCount + count);
		}
		return clipDataMap;
	}
	//云剪周报分享数据
	private Map<String,Integer> getClipWeeklyShareData(Map<String,Object> conditions) throws Exception{
		if(conditions == null) {
			return new HashMap();
		}
		String sStartTime = BaseUtils.parseToString(conditions.get("startTime"));
		String sEndTime = BaseUtils.parseToString(conditions.get("endTime"));
		
		Date startTime = DateUtil.getDateFromString(sStartTime, "yyyy-MM-dd");
		Date endTime = DateUtil.getDateFromString(sEndTime, "yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(endTime);
		cal.add(Calendar.DATE, 1);
		Date nextEndTime = cal.getTime();
		conditions.put("startTime",startTime.getTime()/1000);//秒
		conditions.put("endTime", nextEndTime.getTime()/1000);
		
		List<Map<String,Object>> clipShareDataList = this.biService.getVideosSourceTypeShareStatCount(conditions);
		List<Map<String,Object>> clipExtraShareDataList = this.biExtraService.getVideosSourceTypeShareStatCount(conditions);
		List<Map<String,Object>> allClipShareDataList = new ArrayList();
		allClipShareDataList.addAll(clipShareDataList);
		allClipShareDataList.addAll(clipExtraShareDataList);
		Map<String,Integer> clipShareDataMap = new HashMap();
		for(Map<String,Object> m : allClipShareDataList) {
			int sourceType = BaseUtils.parseToInt(m.get("sourceType"));
			int type = BaseUtils.parseToInt(m.get("type"));////1全部、2除了ims\iss、3新浪、4政务媒体
			int count = BaseUtils.parseToInt(m.get("c"));
			if(sourceType == Constant.Videos.SOURCE_TYPE_UPLOAD || sourceType == Constant.Videos.SOURCE_TYPE_PULL) {
				sourceType = Constant.Videos.SOURCE_TYPE_UPLOAD;//上传和拉取汇总到上传
			}else if(sourceType == Constant.Videos.SOURCE_TYPE_CLIP || sourceType == Constant.Videos.SOURCE_TYPE_MIXING_CLIP) {
				sourceType = Constant.Videos.SOURCE_TYPE_CLIP;//剪辑和混剪汇总到剪辑
			}
			
			String key = type + "_" + sourceType;
			Integer tempCount = clipShareDataMap.get(key);
			if(tempCount == null) {
				tempCount = 0;
			}
			clipShareDataMap.put(key, tempCount + count);
		}
		return clipShareDataMap;
	}
	//环比增长率
	private String getPercent(Integer thisCount,Integer lastCount) {
		double d = (lastCount == 0 ? ((thisCount-lastCount)*100) : ((thisCount-lastCount)*100.0/lastCount));
		String returnValue = BaseUtils.doubleToString(d) + "%";
		return returnValue;
	}
	
	private void setClipStatData(String sStartTime,String sEndTime,Map<String,Integer> clipDataMap,Map<String,Integer> clipShareDataMap) throws Exception{
		Date startTime = CalendarUtils.parseDate(sStartTime);
		Date endTime = CalendarUtils.parseDate(sEndTime);
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(endTime);
//		cal.add(Calendar.DATE, 1);
//		Date nextEndTime = cal.getTime();
		Map<String,Object> conditions = new HashMap();
		conditions.put("startSummaryDateMillisecond", startTime.getTime());
		conditions.put("endSummaryDateMillisecond", endTime.getTime());
		List<ClipVideoSummaryStatDetailDTO> dtoList = clipVideoSummaryStatDetailService.searchClipVideoSummaryStatDetail(conditions);
		for(ClipVideoSummaryStatDetailDTO dto : dtoList) {
			int sourceType = dto.getSourceType();
			if(sourceType == Constant.Videos.SOURCE_TYPE_UPLOAD || sourceType == Constant.Videos.SOURCE_TYPE_PULL) {
				sourceType = Constant.Videos.SOURCE_TYPE_UPLOAD;//上传和拉取汇总到上传
			}else if(sourceType == Constant.Videos.SOURCE_TYPE_CLIP || sourceType == Constant.Videos.SOURCE_TYPE_MIXING_CLIP) {
				sourceType = Constant.Videos.SOURCE_TYPE_CLIP;//剪辑和混剪汇总到剪辑
			}
			String key = dto.getType() +"_" + sourceType;
			//数量
			Integer tempCount = clipDataMap.get(key);
			if(tempCount == null) {
				tempCount = 0;
			}
			clipDataMap.put(key, tempCount + dto.getCount());
			//共享数量
			tempCount = clipShareDataMap.get(key);
			if(tempCount == null) {
				tempCount = 0;
			}
			clipShareDataMap.put(key, tempCount + dto.getShareCount());
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/getClipVideoCumulativeDataStat")
	public DataMsg getClipVideoCumulativeDataStat(HttpServletRequest request) {
		DataMsg dataMsg = new DataMsg();
		try {
			String sEndTime = request.getParameter("endTime");
			Date endTime = CalendarUtils.parseDate(sEndTime);
			Map<String,Object> searchParams = new HashMap();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(endTime);
			cal.add(Calendar.DATE, 1);
			Date nextEndTime = cal.getTime();
			String sNextEndTime = CalendarUtils.format(nextEndTime);
			
			Map<String,Object> conditions = new HashMap();
			//用户情况
			//iss/ims账号数
			conditions.put("userTypes", "'iss','ims'");
			conditions.put("userPoliciesState", 1);
			conditions.put("endTime", nextEndTime.getTime()/1000);
			int issImsUserCount = this.biExtraService.getUserAccount(conditions);
			//政务媒体账号数
			conditions.clear();
//			conditions.put("userType", "'weibo_zhengwu','weibo_newsmedia'");
			conditions.put("userPoliciesState", 1);
			conditions.put("notUserType", "'iss','ims','admin','dev','test'");
			conditions.put("endCreatedTime", nextEndTime.getTime()/1000);
			Integer zwMediaUserCount = this.clipUsersService.getUsersAccountCount(conditions);
			//总账号数
			Integer allUserCount = issImsUserCount + zwMediaUserCount;
			//视频版权情况（国内、海外）
			conditions.clear();
			ClipVideoCopyrightSummaryStatDTO copyrightdto = new ClipVideoCopyrightSummaryStatDTO();
			copyrightdto.setSummaryDateMillisecond(endTime.getTime());
			searchParams.put("dto", copyrightdto);
			List<ClipVideoCopyrightSummaryStatDTO> copyrightDtoList = this.clipVideoCopyrightSummaryStatService.searchClipVideoCopyrightSummaryStat(searchParams);
			Map<String,Object> copyrightDataMap = new HashMap();
			//只有一条数据
			if(copyrightDtoList.size() > 0) {
				ClipVideoCopyrightSummaryStatDTO copyrightDto = copyrightDtoList.get(0);
				copyrightDataMap.put("abroadCount", copyrightDto.getAbroadCount());
				copyrightDataMap.put("abroadHour", Math.round(copyrightDto.getAbroadLength() / 3600));
				copyrightDataMap.put("homeCount", copyrightDto.getHomeCount());
				copyrightDataMap.put("homeHour", Math.round(copyrightDto.getHomeLength() / 3600));
			}
			ClipVideoSummaryStatDTO dto = new ClipVideoSummaryStatDTO();
			dto.setSummaryDateMillisecond(endTime.getTime());
			searchParams.put("dto", dto);
			List<ClipVideoSummaryStatDTO> dtoList = this.clipVideoSummaryStatService.searchClipVideoSummaryStat(searchParams);
			Map dataMap = new HashMap();
			Map<String,Integer> thisCumulativeClipDataMap = new HashMap();
			Map<String,Integer> thisCumulativeClipShareDataMap = new HashMap();
			Map<String,Integer> lastCumulativeClipDataMap = new HashMap();
			Map<String,Integer> lastCumulativeClipShareDataMap = new HashMap();
			
			Integer ljvideoShareCount = 0;//累计视频共享总数
			for(ClipVideoSummaryStatDTO stat : dtoList) {
				int sourceType = stat.getSourceType();
				if(sourceType == Constant.Videos.SOURCE_TYPE_UPLOAD || sourceType == Constant.Videos.SOURCE_TYPE_PULL) {
					sourceType = Constant.Videos.SOURCE_TYPE_UPLOAD;//上传和拉取汇总到上传
				}else if(sourceType == Constant.Videos.SOURCE_TYPE_CLIP || sourceType == Constant.Videos.SOURCE_TYPE_MIXING_CLIP) {
					sourceType = Constant.Videos.SOURCE_TYPE_CLIP;//剪辑和混剪汇总到剪辑
				}
				String key = stat.getType() +"_" + sourceType;
				//数量
				Integer tempCount = thisCumulativeClipDataMap.get(key);
				if(tempCount == null) {
					tempCount = 0;
				}
				thisCumulativeClipDataMap.put(key, tempCount + stat.getCount());
				//共享
				tempCount = thisCumulativeClipShareDataMap.get(key);
				if(tempCount == null) {
					tempCount = 0;
				}
				thisCumulativeClipShareDataMap.put(key, tempCount + stat.getShareCount());
				if(stat.getType() == 1) {
					ljvideoShareCount += stat.getShareCount();
				}
			}
			//上一周的累计数据
			cal.setTime(endTime);
			cal.add(Calendar.DATE, -7);
			CalendarUtils.clearHMS(cal);
			dto.setSummaryDateMillisecond(cal.getTimeInMillis());
			searchParams.clear();
			searchParams.put("dto", dto);
			List<ClipVideoSummaryStatDTO> dtoList1 = this.clipVideoSummaryStatService.searchClipVideoSummaryStat(searchParams);
			for(ClipVideoSummaryStatDTO stat : dtoList1) {
				int sourceType = stat.getSourceType();
				if(sourceType == Constant.Videos.SOURCE_TYPE_UPLOAD || sourceType == Constant.Videos.SOURCE_TYPE_PULL) {
					sourceType = Constant.Videos.SOURCE_TYPE_UPLOAD;//上传和拉取汇总到上传
				}else if(sourceType == Constant.Videos.SOURCE_TYPE_CLIP || sourceType == Constant.Videos.SOURCE_TYPE_MIXING_CLIP) {
					sourceType = Constant.Videos.SOURCE_TYPE_CLIP;//剪辑和混剪汇总到剪辑
				}
				String key = stat.getType() +"_" + sourceType;
				//数量
				Integer tempCount = lastCumulativeClipDataMap.get(key);
				if(tempCount == null) {
					tempCount = 0;
				}
				lastCumulativeClipDataMap.put(key, tempCount + stat.getCount());
				//共享
				tempCount = lastCumulativeClipShareDataMap.get(key);
				if(tempCount == null) {
					tempCount = 0;
				}
				lastCumulativeClipShareDataMap.put(key, tempCount + stat.getShareCount());
			}
			//视频累计播放量
			conditions = new HashMap();
			conditions.put("endTime",  nextEndTime.getTime()/1000);
//			Long ljvideoVVCount1 = this.biService.getVideoVV(conditions);//累计播放量
//			Long ljvideoExtraVVCount = this.biExtraService.getVideoVV(conditions);//累计播放量
//			Long ljvideoVVCount = ljvideoVVCount1 + ljvideoExtraVVCount;
			Long ljvideoVVCount = this.clipAnalysisService.getVideoVV(conditions);
			
			dataMap.put("allUserCount", allUserCount);
			dataMap.put("issImsUserCount", issImsUserCount);
			dataMap.put("zwMediaUserCount", zwMediaUserCount);
			dataMap.put("copyrightDataMap", copyrightDataMap);
			
			dataMap.put("thisCumulativeClipDataMap", thisCumulativeClipDataMap);
			dataMap.put("thisCumulativeClipShareDataMap", thisCumulativeClipShareDataMap);
			dataMap.put("lastCumulativeClipDataMap", lastCumulativeClipDataMap);
			dataMap.put("lastCumulativeClipShareDataMap", lastCumulativeClipShareDataMap);
			dataMap.put("ljvideoShareCount", ljvideoShareCount);
			dataMap.put("ljvideoVVCount", ljvideoVVCount);
			dataMsg.setData(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMsg.failed(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return dataMsg;
	}
	
	@RequestMapping("/exportCloudClipWeeklyStatData")
	public void exportCloudClipWeeklyStatData(HttpServletRequest request,HttpServletResponse response) {
		try {
			String sStartTime = BaseUtils.parseToString(request.getParameter("startTime"));
			String sEndTime = BaseUtils.parseToString(request.getParameter("endTime"));
			//1：导出前设置一个标志，前端监听这个标志是否存在，不存在表示导出完成，关闭提示。监听文件是否组装完毕，让客户端显示正在下载的提示。
			//2:也可使用ajax请求，组装的Excel文件写在服务器上，返回文件路径给前端，然后前端处理完成下载。
			//暂时使用第一种方式
			request.getSession().setAttribute("CloudClipWeeklyStatData_sesion", "true");
			//获取数据
			Map dataMap = this.getCloudClipWeeklyData(sStartTime, sEndTime);
			Integer newAddUserCount = BaseUtils.parseToInt(dataMap.get("newAddUserCount"));
			
			Map<String,Integer> thisClipDataMap = (Map) dataMap.get("thisClipDataMap");
			Map<String,Integer> thisClipShareDataMap = (Map) dataMap.get("thisClipShareDataMap");
			Map<String,Integer> lastClipDataMap = (Map) dataMap.get("lastClipDataMap");
			Map<String,Integer> lastClipShareDataMap = (Map) dataMap.get("lastClipShareDataMap");
			
			Integer allActiveUserCount = BaseUtils.parseToInt(dataMap.get("allActiveUserCount"));
			Integer allActiveAvgUserCount = BaseUtils.parseToInt(dataMap.get("allActiveAvgUserCount"));
			Integer activeUserCount = BaseUtils.parseToInt(dataMap.get("activeUserCount"));
			Integer activeAvgUserCount = BaseUtils.parseToInt(dataMap.get("activeAvgUserCount"));
			Integer activeUserCount_1_0 = BaseUtils.parseToInt(dataMap.get("activeUserCount_1_0"));
			Integer activeAvgUserCount_1_0 = BaseUtils.parseToInt(dataMap.get("activeAvgUserCount_1_0"));
			Integer videoShareCount = BaseUtils.parseToInt(dataMap.get("videoShareCount"));
			Integer videoAvgShareCount = BaseUtils.parseToInt(dataMap.get("videoAvgShareCount"));
			Integer videoVVCount = BaseUtils.parseToInt(dataMap.get("videoVVCount"));
			Integer videoAvgVVCount = BaseUtils.parseToInt(dataMap.get("videoAvgVVCount"));
			
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet();
			for(int i = 0 ; i < 9 ; i ++) {
				sheet.setColumnWidth(i, 255*25);
			}
//			sheet.setDefaultColumnWidth(30);
			
			XSSFFont font1 = wb.createFont();
			font1.setBold(true);
			font1.setFontHeightInPoints((short)16);
			XSSFFont font2 = wb.createFont();
			font2.setBold(true);
			
			XSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
			style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			
			XSSFCellStyle style1 = wb.createCellStyle();
			style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			style1.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
			style1.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			style1.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
			style1.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			style1.setFont(font1);
			
			XSSFCellStyle style2 = wb.createCellStyle();
			style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			style2.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
			style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			style2.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
			style2.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			style2.setFont(font2);
			
			XSSFCellStyle style3 = wb.createCellStyle();
			style3.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style3.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			style3.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
			style3.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			style3.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
			style3.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			style3.setFillForegroundColor(new XSSFColor(new Color(211, 211, 211)));// 设置背景色
			style3.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			
			int height = 500;
			//表头
			int rownum = 0;
			int col = 0;
			XSSFRow row = sheet.createRow(rownum);
			XSSFCell cell = row.createCell(0);
			for(int i = 1 ; i < 9 ; i ++) {
				XSSFCell cell1 = row.createCell(i);
				cell1.setCellValue("");
				cell1.setCellStyle(style1);
			}
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 8));
			cell.setCellValue("本周云剪数据");
			cell.setCellStyle(style1);
			row.setHeight((short) 1000);
			
			rownum ++;
			row = sheet.createRow(rownum);
			cell = row.createCell(0);
			for(int i = 1 ; i < 9 ; i ++) {
				XSSFCell cell1 = row.createCell(i);
				cell1.setCellValue("");
				cell1.setCellStyle(style2);
			}
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 8));
			String strStartTime = DateUtil.getStringDateFromDate(DateUtil.getDateFromString(sStartTime), "yyyy年MM月dd日");
			String strEndTime = DateUtil.getStringDateFromDate(DateUtil.getDateFromString(sEndTime), "yyyy年MM月dd日");
			cell.setCellValue("日期：" + strStartTime + "-" + strEndTime);
			cell.setCellStyle(style2);
			row.setHeight((short) height);
			
			rownum ++;
			row = sheet.createRow(rownum);
			cell = row.createCell(1);
			row.setHeight((short) height);
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 1, 8));
			for(int i = 2 ; i < 9 ; i ++) {
				XSSFCell cell1 = row.createCell(i);
				cell1.setCellValue("");
				cell1.setCellStyle(style2);
			}
			cell.setCellValue("基本数据情况");
			cell.setCellStyle(style2);
			
			rownum ++;
			col = 0;
			row = sheet.createRow(rownum);
			cell = row.createCell(col);
			row.setHeight((short) height);
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + 1, col, col));
			cell.setCellValue("用户情况");
			cell.setCellStyle(style2);
			String[] headers = {"本周新增账号总数","","","","","","",""};
			Object[] values = {newAddUserCount,"","","","","","",""};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			cell = row.createCell(col);
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + 9, col, col));
			cell.setCellStyle(style2);
			cell.setCellValue("本周云剪后台数据");
//			rownum ++;
//			row = sheet.createRow(rownum);
//			row.setHeight((short) height);
//			col = 0;
			headers = new String[]{"活跃用户总数","日均活跃用户总数","活跃用户数（1.0）","日均活跃用户数（1.0）","活跃用户数（2.0）","日均活跃用户数（2.0）","",""};
			values = new Object[]{allActiveUserCount,allActiveAvgUserCount,
					activeUserCount_1_0,activeAvgUserCount_1_0,
					activeUserCount,activeAvgUserCount,"",""};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			headers = new String[]{"视频分享数","日均视频分享数","视频播放量","日均视频播放量","","","",""};
			values = new Object[]{videoShareCount,videoAvgShareCount,
					videoVVCount,videoAvgVVCount,"","","",""};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			
			rownum++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			headers = new String[]{"接入直播数量","环比增长","直播内容剪辑数量","环比增长","直播内容剪辑分享","环比增长","",""};
			Integer zbCount = BaseUtils.parseToInt(thisClipDataMap.get("1_3"));
			Integer lastZbCount = BaseUtils.parseToInt(lastClipDataMap.get("1_3"));
			Integer zbjzCount = BaseUtils.parseToInt(thisClipDataMap.get("1_4"));
			Integer lastZbjzCount = BaseUtils.parseToInt(lastClipDataMap.get("1_4"));
			Integer zbjzShareCount = BaseUtils.parseToInt(thisClipShareDataMap.get("1_4"));
			Integer lastZbjzShareCount = BaseUtils.parseToInt(lastClipShareDataMap.get("1_4"));
			
			values = new Object[]{zbCount,this.getPercent(zbCount, lastZbCount),zbjzCount,this.getPercent(zbjzCount, lastZbjzCount),zbjzShareCount,this.getPercent(zbjzShareCount, lastZbjzShareCount),"",""};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			headers = new String[]{"上传数量","环比增长","上传数量（去除ISS/IMS）","环比增长","上传后分享","环比增长","上传后分享（去除ISS/IMS）","环比增长"};
			
			Integer uploadCount = BaseUtils.parseToInt(thisClipDataMap.get("1_0"));
			Integer lastUploadCount = BaseUtils.parseToInt(lastClipDataMap.get("1_0"));
			Integer uploadNotIssImsCount = BaseUtils.parseToInt(thisClipDataMap.get("2_0"));
			Integer lastUploadNotIssImsCount = BaseUtils.parseToInt(lastClipDataMap.get("2_0"));
			Integer uploadShareCount = BaseUtils.parseToInt(thisClipShareDataMap.get("1_0"));
			Integer lastUploadShareCount = BaseUtils.parseToInt(lastClipShareDataMap.get("1_0"));
			Integer uploadNotIssImsShareCount = BaseUtils.parseToInt(lastClipShareDataMap.get("2_0"));
			Integer lastUploadNotIssImsShareCount = BaseUtils.parseToInt(lastClipShareDataMap.get("2_0"));
			
			values = new Object[]{uploadCount,this.getPercent(uploadCount, lastUploadCount),
					uploadNotIssImsCount,this.getPercent(uploadNotIssImsCount, lastUploadNotIssImsCount),
					uploadShareCount,this.getPercent(uploadShareCount, lastUploadShareCount),
					uploadNotIssImsShareCount,this.getPercent(uploadNotIssImsShareCount, lastUploadNotIssImsShareCount)};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			headers = new String[]{"剪辑数量","环比增长","剪辑数量（去除ISS/IMS）","环比增长","剪辑后分享","环比增长","剪辑后分享（去除ISS/IMS）","环比增长"};
			Integer jzCount = BaseUtils.parseToInt(thisClipDataMap.get("1_2"));
			Integer lastJzCount = BaseUtils.parseToInt(lastClipDataMap.get("1_2"));
			Integer jzNotIssImsCount = BaseUtils.parseToInt(thisClipDataMap.get("2_2"));
			Integer lastJzNotIssImsCount = BaseUtils.parseToInt(lastClipDataMap.get("2_2"));
			Integer jzShareCount = BaseUtils.parseToInt(thisClipShareDataMap.get("1_2"));
			Integer lastJzShareCount = BaseUtils.parseToInt(lastClipShareDataMap.get("1_2"));
			Integer jzNotIssImsShareCount = BaseUtils.parseToInt(lastClipShareDataMap.get("2_2"));
			Integer lastJzNotIssImsShareCount = BaseUtils.parseToInt(lastClipShareDataMap.get("2_2"));
			
			values = new Object[]{jzCount,this.getPercent(jzCount, lastJzCount),
					jzNotIssImsCount,this.getPercent(jzNotIssImsCount, lastJzNotIssImsCount),
					jzShareCount,this.getPercent(jzShareCount, lastJzShareCount),
					jzNotIssImsShareCount,this.getPercent(jzNotIssImsShareCount, lastJzNotIssImsShareCount)};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			
			ServletOutputStream out = response.getOutputStream();
			String fileName = new String(("本周云剪数据").getBytes(), "ISO8859_1");  
			response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
			wb.write(out);
			out.flush();
			out.close();
			request.getSession().removeAttribute("CloudClipWeeklyStatData_sesion");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@ResponseBody
	@RequestMapping("/checkExportCloudClipWeeklyStatDataSuccess")
	public int checkExportCloudClipWeeklyStatDataSuccess(HttpServletRequest request) {
		Object t = request.getSession().getAttribute("CloudClipWeeklyStatData_sesion");
		return t == null ? 1 : 0;
	}
	
	@RequestMapping("/exportClipVideoCumulativeStatData")
	public void exportClipVideoCumulativeStatData(HttpServletRequest request,HttpServletResponse response) {
		try {
			String sEndTime = BaseUtils.parseToString(request.getParameter("endTime"));
			//1：导出前设置一个标志，前端监听这个标志是否存在，不存在表示导出完成，关闭提示。监听文件是否组装完毕，让客户端显示正在下载的提示。
			//2:也可使用ajax请求，组装的Excel文件写在服务器上，返回文件路径给前端，然后前端处理完成下载。
			//暂时使用第一种方式
			request.getSession().setAttribute("ClipVideoCumulativeStatData_Session", "true");
			//获取数据
			Date endTime = CalendarUtils.parseDate(sEndTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(endTime);
			cal.add(Calendar.DATE, 1);
			Date nextEndTime = cal.getTime();
			String sNextEndTime = CalendarUtils.format(nextEndTime);
			Map<String,Object> conditions = new HashMap();
			Map<String,Object> searchParams = new HashMap();
			//用户情况
			//iss/ims账号数
			conditions.put("userTypes", "'iss','ims'");
			conditions.put("userPoliciesState", 1);
			conditions.put("endTime", nextEndTime.getTime()/1000);
			int issImsUserCount = this.biExtraService.getUserAccount(conditions);
			//政务媒体账号数
			conditions.clear();
//			conditions.put("userType", "'weibo_zhengwu','weibo_newsmedia'");
			conditions.put("userPoliciesState", 1);
			conditions.put("notUserType", "'iss','ims','admin','dev','test'");
			conditions.put("endCreatedTime", nextEndTime.getTime()/1000);
			Integer zwMediaUserCount = this.clipUsersService.getUsersAccountCount(conditions);
			//总账号数
			Integer allUserCount = issImsUserCount + zwMediaUserCount;
			//视频版权情况（国内、海外）
			conditions.clear();
			ClipVideoCopyrightSummaryStatDTO copyrightdto = new ClipVideoCopyrightSummaryStatDTO();
			copyrightdto.setSummaryDateMillisecond(endTime.getTime());
			searchParams.put("dto", copyrightdto);
			List<ClipVideoCopyrightSummaryStatDTO> copyrightDtoList = this.clipVideoCopyrightSummaryStatService.searchClipVideoCopyrightSummaryStat(searchParams);
			Map<String,Object> copyrightDataMap = new HashMap();
			//只有一条数据
			if(copyrightDtoList.size() > 0) {
				ClipVideoCopyrightSummaryStatDTO copyrightDto = copyrightDtoList.get(0);
				copyrightDataMap.put("abroadCount", copyrightDto.getAbroadCount());
				copyrightDataMap.put("abroadHour", Math.round(copyrightDto.getAbroadLength() / 3600));
				copyrightDataMap.put("homeCount", copyrightDto.getHomeCount());
				copyrightDataMap.put("homeHour", Math.round(copyrightDto.getHomeLength() / 3600));
			}
			
			searchParams.clear();
			ClipVideoSummaryStatDTO dto = new ClipVideoSummaryStatDTO();
			dto.setSummaryDateMillisecond(endTime.getTime());
			searchParams.put("dto", dto);
			List<ClipVideoSummaryStatDTO> dtoList = this.clipVideoSummaryStatService.searchClipVideoSummaryStat(searchParams);
			Map dataMap = new HashMap();
			Map<String,Integer> thisCumulativeClipDataMap = new HashMap();
			Map<String,Integer> thisCumulativeClipShareDataMap = new HashMap();
			Map<String,Integer> lastCumulativeClipDataMap = new HashMap();
			Map<String,Integer> lastCumulativeClipShareDataMap = new HashMap();
			
			Integer ljvideoShareCount = 0;//累计视频共享总数
			for(ClipVideoSummaryStatDTO stat : dtoList) {
				int sourceType = stat.getSourceType();
				if(sourceType == Constant.Videos.SOURCE_TYPE_UPLOAD || sourceType == Constant.Videos.SOURCE_TYPE_PULL) {
					sourceType = Constant.Videos.SOURCE_TYPE_UPLOAD;//上传和拉取汇总到上传
				}else if(sourceType == Constant.Videos.SOURCE_TYPE_CLIP || sourceType == Constant.Videos.SOURCE_TYPE_MIXING_CLIP) {
					sourceType = Constant.Videos.SOURCE_TYPE_CLIP;//剪辑和混剪汇总到剪辑
				}
				String key = stat.getType() +"_" + sourceType;
				//数量
				Integer tempCount = thisCumulativeClipDataMap.get(key);
				if(tempCount == null) {
					tempCount = 0;
				}
				thisCumulativeClipDataMap.put(key, tempCount + stat.getCount());
				//共享
				tempCount = thisCumulativeClipShareDataMap.get(key);
				if(tempCount == null) {
					tempCount = 0;
				}
				thisCumulativeClipShareDataMap.put(key, tempCount + stat.getShareCount());
				
				if(stat.getType() == 1) {
					ljvideoShareCount += stat.getShareCount();
				}
			}
			//上一周的累计数据
			cal.setTime(endTime);
			CalendarUtils.clearHMS(cal);
			cal.add(Calendar.DATE, -7);
			dto.setSummaryDateMillisecond(cal.getTimeInMillis());
			searchParams.clear();
			searchParams.put("dto", dto);
			List<ClipVideoSummaryStatDTO> dtoList1 = this.clipVideoSummaryStatService.searchClipVideoSummaryStat(searchParams);
			for(ClipVideoSummaryStatDTO stat : dtoList1) {
				int sourceType = stat.getSourceType();
				if(sourceType == Constant.Videos.SOURCE_TYPE_UPLOAD || sourceType == Constant.Videos.SOURCE_TYPE_PULL) {
					sourceType = Constant.Videos.SOURCE_TYPE_UPLOAD;//上传和拉取汇总到上传
				}else if(sourceType == Constant.Videos.SOURCE_TYPE_CLIP || sourceType == Constant.Videos.SOURCE_TYPE_MIXING_CLIP) {
					sourceType = Constant.Videos.SOURCE_TYPE_CLIP;//剪辑和混剪汇总到剪辑
				}
				String key = stat.getType() +"_" + sourceType;
				//数量
				Integer tempCount = lastCumulativeClipDataMap.get(key);
				if(tempCount == null) {
					tempCount = 0;
				}
				lastCumulativeClipDataMap.put(key, tempCount + stat.getCount());
				//共享
				tempCount = lastCumulativeClipShareDataMap.get(key);
				if(tempCount == null) {
					tempCount = 0;
				}
				lastCumulativeClipShareDataMap.put(key, tempCount + stat.getShareCount());
			}
			//视频累计播放量
			conditions = new HashMap();
			conditions.put("endTime", nextEndTime.getTime()/1000);
//			Long ljvideoVVCount1 = this.biService.getVideoVV(conditions);//累计播放量
//			Long ljvideoExtraVVCount = this.biExtraService.getVideoVV(conditions);//累计播放量
//			Long ljvideoVVCount = ljvideoVVCount1 + ljvideoExtraVVCount;
			Long ljvideoVVCount = this.clipAnalysisService.getVideoVV(conditions);
			
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet();
			for(int i = 0 ; i < 9 ; i ++) {
				sheet.setColumnWidth(i, 255*25);
			}
//			sheet.setDefaultColumnWidth(30);
			
			XSSFFont font1 = wb.createFont();
			font1.setBold(true);
			font1.setFontHeightInPoints((short)16);
			XSSFFont font2 = wb.createFont();
			font2.setBold(true);
			
			XSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
			style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			
			XSSFCellStyle style1 = wb.createCellStyle();
			style1.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			style1.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
			style1.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			style1.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
			style1.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			style1.setFont(font1);
			
			XSSFCellStyle style2 = wb.createCellStyle();
			style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			style2.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
			style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			style2.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
			style2.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			style2.setFont(font2);
			
			XSSFCellStyle style3 = wb.createCellStyle();
			style3.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style3.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			style3.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
			style3.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			style3.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
			style3.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			style3.setFillForegroundColor(new XSSFColor(new Color(211, 211, 211)));// 设置背景色
			style3.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			
			int height = 500;
			//表头
			int rownum = 0;
			int col = 0;
			XSSFRow row = sheet.createRow(rownum);
			XSSFCell cell = row.createCell(0);
			for(int i = 1 ; i < 9 ; i ++) {
				XSSFCell cell1 = row.createCell(i);
				cell1.setCellValue("");
				cell1.setCellStyle(style1);
			}
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 8));
			cell.setCellValue("累计云剪数据");
			cell.setCellStyle(style1);
			row.setHeight((short) 1000);
			
			rownum ++;
			row = sheet.createRow(rownum);
			cell = row.createCell(0);
			for(int i = 1 ; i < 9 ; i ++) {
				XSSFCell cell1 = row.createCell(i);
				cell1.setCellValue("");
				cell1.setCellStyle(style2);
			}
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 8));
			String strEndTime = DateUtil.getStringDateFromDate(DateUtil.getDateFromString(sEndTime), "yyyy年MM月dd日");
			cell.setCellValue("截止日期：" + strEndTime);
			cell.setCellStyle(style2);
			row.setHeight((short) height);
			
			rownum ++;
			row = sheet.createRow(rownum);
			cell = row.createCell(1);
			row.setHeight((short) height);
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 1, 8));
			for(int i = 2 ; i < 9 ; i ++) {
				XSSFCell cell1 = row.createCell(i);
				cell1.setCellValue("");
				cell1.setCellStyle(style2);
			}
			cell.setCellValue("基本数据情况");
			cell.setCellStyle(style2);
			
			//账户数
			rownum ++;
			col = 0;
			row = sheet.createRow(rownum);
			cell = row.createCell(col);
			row.setHeight((short) height);
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + 1, col, col));
			cell.setCellValue("用户情况");
			cell.setCellStyle(style2);
			String[] headers = {"ISS/IMS账号数","政务媒体账号数","账号总数","","","","",""};
			Object[] values = {allUserCount,issImsUserCount,zwMediaUserCount,"","","","",""};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			cell = row.createCell(col);
			cell.setCellStyle(style2);
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + 1, col, col));
			cell.setCellValue("版权情况");
			headers = new String[]{"海外片段数量","海外总时长","国内片段数量","国内总时长","总计片段数量","总计时长","",""};
			Integer abroadCount = BaseUtils.parseToInt(copyrightDataMap.get("abroadCount"));
			Double abroadHour = BaseUtils.parseToDouble(copyrightDataMap.get("abroadHour"));
			Integer homeCount = BaseUtils.parseToInt(copyrightDataMap.get("homeCount"));
			Double homeHour = BaseUtils.parseToDouble(copyrightDataMap.get("homeHour"));
			
			values = new Object[]{abroadCount,abroadHour,homeCount,homeHour,abroadCount + homeCount,abroadHour + homeHour,"",""};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			
			//累计云剪后台数据
			
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			cell = row.createCell(col);
			sheet.addMergedRegion(new CellRangeAddress(rownum, rownum + 7, col, col));
			cell.setCellStyle(style2);
			cell.setCellValue("累计云剪后台数据");
			
			headers = new String[]{"视频分享数","视频播放量","","","","","",""};
			values = new Object[]{ljvideoShareCount,ljvideoVVCount,"","","","","",""};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			
			headers = new String[]{"接入直播数量","环比增长","直播内容剪辑数量","环比增长","直播内容剪辑分享","环比增长","",""};
			Integer ljzbCount = BaseUtils.parseToInt(thisCumulativeClipDataMap.get("1_3"));
			Integer ljlastZbCount = BaseUtils.parseToInt(lastCumulativeClipDataMap.get("1_3"));
			Integer ljzbjzCount = BaseUtils.parseToInt(thisCumulativeClipDataMap.get("1_4"));
			Integer ljlastZbjzCount = BaseUtils.parseToInt(lastCumulativeClipDataMap.get("1_4"));
			Integer ljzbjzShareCount = BaseUtils.parseToInt(thisCumulativeClipShareDataMap.get("1_4"));
			Integer ljlastZbjzShareCount = BaseUtils.parseToInt(lastCumulativeClipShareDataMap.get("1_4"));
			
			values = new Object[]{ljzbCount,this.getPercent(ljzbCount, ljlastZbCount),
					ljzbjzCount,this.getPercent(ljzbjzCount, ljlastZbjzCount),
					ljzbjzShareCount,this.getPercent(ljzbjzShareCount, ljlastZbjzShareCount),"",""};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			headers = new String[]{"上传数量","环比增长","上传数量（去除ISS/IMS）","环比增长","上传后分享","环比增长","上传后分享（去除ISS/IMS）","环比增长"};
			
			Integer ljuploadCount = BaseUtils.parseToInt(thisCumulativeClipDataMap.get("1_0"));
			Integer ljlastUploadCount = BaseUtils.parseToInt(lastCumulativeClipDataMap.get("1_0"));
			Integer ljuploadNotIssImsCount = BaseUtils.parseToInt(thisCumulativeClipDataMap.get("2_0"));
			Integer ljlastUploadNotIssImsCount = BaseUtils.parseToInt(lastCumulativeClipDataMap.get("2_0"));
			Integer ljuploadShareCount = BaseUtils.parseToInt(thisCumulativeClipShareDataMap.get("1_0"));
			Integer ljlastUploadShareCount = BaseUtils.parseToInt(lastCumulativeClipShareDataMap.get("1_0"));
			Integer ljuploadNotIssImsShareCount = BaseUtils.parseToInt(lastCumulativeClipShareDataMap.get("2_0"));
			Integer ljlastUploadNotIssImsShareCount = BaseUtils.parseToInt(lastCumulativeClipShareDataMap.get("2_0"));
			
			values = new Object[]{ljuploadCount,this.getPercent(ljuploadCount, ljlastUploadCount),
					ljuploadNotIssImsCount,this.getPercent(ljuploadNotIssImsCount, ljlastUploadNotIssImsCount),
					ljuploadShareCount,this.getPercent(ljuploadShareCount, ljlastUploadShareCount),
					ljuploadNotIssImsShareCount,this.getPercent(ljuploadNotIssImsShareCount, ljlastUploadNotIssImsShareCount)};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			headers = new String[]{"剪辑数量","环比增长","剪辑数量（去除ISS/IMS）","环比增长","剪辑后分享","环比增长","剪辑后分享（去除ISS/IMS）","环比增长"};
			Integer ljjzCount = BaseUtils.parseToInt(thisCumulativeClipDataMap.get("1_2"));
			Integer ljlastJzCount = BaseUtils.parseToInt(lastCumulativeClipDataMap.get("1_2"));
			Integer ljjzNotIssImsCount = BaseUtils.parseToInt(thisCumulativeClipDataMap.get("2_2"));
			Integer ljlastJzNotIssImsCount = BaseUtils.parseToInt(lastCumulativeClipDataMap.get("2_2"));
			Integer ljjzShareCount = BaseUtils.parseToInt(thisCumulativeClipShareDataMap.get("1_2"));
			Integer ljlastJzShareCount = BaseUtils.parseToInt(lastCumulativeClipShareDataMap.get("1_2"));
			Integer ljjzNotIssImsShareCount = BaseUtils.parseToInt(lastCumulativeClipShareDataMap.get("2_2"));
			Integer ljlastJzNotIssImsShareCount = BaseUtils.parseToInt(lastCumulativeClipShareDataMap.get("2_2"));
			
			values = new Object[]{ljjzCount,this.getPercent(ljjzCount, ljlastJzCount),
					ljjzNotIssImsCount,this.getPercent(ljjzNotIssImsCount, ljlastJzNotIssImsCount),
					ljjzShareCount,this.getPercent(ljjzShareCount, ljlastJzShareCount),
					ljjzNotIssImsShareCount,this.getPercent(ljjzNotIssImsShareCount, ljlastJzNotIssImsShareCount)};
			for(String h : headers) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style3);
				cell.setCellValue(h);
			}
			rownum ++;
			row = sheet.createRow(rownum);
			row.setHeight((short) height);
			col = 0;
			for(Object o : values) {
				col ++;
				cell = row.createCell(col);
				cell.setCellStyle(style);
				ExcelHelper.setExcelValue(cell, o);
			}
			
			
			ServletOutputStream out = response.getOutputStream();
			String fileName = new String(("累计云剪数据").getBytes(), "ISO8859_1");  
			response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
			wb.write(out);
			out.flush();
			out.close();
			request.getSession().removeAttribute("ClipVideoCumulativeStatData_Session");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping("/checkExportClipVideoCumulativeStatDataSuccess")
	public int checkExportClipVideoCumulativeStatDataSuccess(HttpServletRequest request) {
		Object t = request.getSession().getAttribute("ClipVideoCumulativeStatData_Session");
		return t == null ? 1 : 0;
	}
	
	
	//pv、uv值
	@ResponseBody
	@RequestMapping("/getPVUVData")
	public DataMsg getPVUVData(HttpServletRequest request) throws Exception{
		DataMsg dataMsg = new DataMsg();
		try {
			//读取埋点日志文件
			String path = BIConfig.getValue("FRONTEND_TRACK_LOG_PATH");;
			Calendar cal = Calendar.getInstance();
			CalendarUtils.clearHMS(cal);
			Date endTime = cal.getTime();
			cal.add(Calendar.DATE, -6);
			Date startTime = cal.getTime();
			Map<Date,List<TrackLog>> trackLogMap = TrackLogUtils.getTrackLogFileListByTime(path, startTime, endTime);
			
			List<String> dateList = new ArrayList();
			Map<String,Integer> pvCountMap = new HashMap(); //每天访问数，key=date value=数量
			Map<String,Integer> uvCountMap = new HashMap(); //每天用户访问数，key=date value=数量
			
			cal.setTime(startTime);
			while(cal.getTimeInMillis() <= endTime.getTime()) {
				List<TrackLog> trackLogList = trackLogMap.get(cal.getTime());
				String sTempTime = DateUtil.format(cal.getTime(),"yyyy-MM-dd");
				
				Integer pvCount = pvCountMap.get(sTempTime);
				if(pvCount == null) {
					pvCount = 0;
				}
				Integer uvCount = uvCountMap.get(sTempTime);
				if(uvCount == null) {
					uvCount = 0;
				}
				
				if(trackLogList != null) {
					Set<String> distinctIdSet = new HashSet();
					for(TrackLog log : trackLogList) {
						String event = BaseUtils.parseToString(log.getEvent()).trim();
						String type = BaseUtils.parseToString(log.getType()).trim();
//						if(log.getTime() != null) {
//							Date tDate = new Date(log.getTime());
//							System.out.println(DateUtil.getStringDateFromDate(tDate, "yyyy-MM-dd hh:mm:ss"));
//						}
						if("page_visit".equalsIgnoreCase(event)) {
							pvCount ++;
						}
						if("profile_set_once".equalsIgnoreCase(type)) {
							if(!distinctIdSet.contains(log.getDistinctId())) {
								uvCount ++;
								distinctIdSet.add(log.getDistinctId().trim());
							}
						}
					}
				}
				pvCountMap.put(sTempTime, pvCount);
				uvCountMap.put(sTempTime, uvCount);
				dateList.add(sTempTime);
				cal.add(Calendar.DATE, 1);
			}
			
			Map dataMap = new HashMap();
			dataMap.put("dateList", dateList);
			dataMap.put("pvCountMap", pvCountMap);
			dataMap.put("uvCountMap", uvCountMap);
			dataMsg.setData(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMsg.failed(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return dataMsg;
	}
	
	//近7天活跃用户（机构用户、个人用户）
	@ResponseBody
	@RequestMapping("/getActiveUserData")
	public DataMsg getActiveUserData(HttpServletRequest request) throws Exception{
		DataMsg dataMsg = new DataMsg();
		try {
			//读取埋点日志文件
			String path = BIConfig.getValue("FRONTEND_TRACK_LOG_PATH");;
			Calendar cal = Calendar.getInstance();
			CalendarUtils.clearHMS(cal);
			cal.add(Calendar.DATE, 1);
			Date endTime = cal.getTime();
			cal.add(Calendar.DATE, -7);
			Date startTime = cal.getTime();
			Map<Date,List<TrackLog>> trackLogMap = TrackLogUtils.getTrackLogFileListByTime(path, startTime, endTime);
			
			List<String> dateList = new ArrayList();
			Map<String,Integer> orgCountMap = new HashMap(); //机构数，key=date value=数量
			Map<String,Integer> commCountMap = new HashMap(); //普通用户数，key=date value=数量
			Map<String,Integer> otherCountMap = new HashMap(); //普通用户数，key=date value=数量
			Set<String> distinctIdSet = new HashSet();
			Map<String,Set<String>> distinctIdMap = new HashMap();
			cal.setTime(startTime);
			while(cal.getTimeInMillis() <= endTime.getTime()) {
				List<TrackLog> trackLogList = trackLogMap.get(cal.getTime());
				String sTempTime = DateUtil.format(cal.getTime(),"yyyy-MM-dd");
				Set<String> tempUserIDSet = distinctIdMap.get(sTempTime);
				if(tempUserIDSet == null) {
					tempUserIDSet = new HashSet();
				}
				if(trackLogList != null) {
					for(TrackLog log : trackLogList) {
						String event = BaseUtils.parseToString(log.getEvent()).trim();
//						if(log.getTime() != null) {
//							Date tDate = new Date(log.getTime());
//							System.out.println(DateUtil.getStringDateFromDate(tDate, "yyyy-MM-dd hh:mm:ss"));
//						}
						if(log.getDistinctId() != null && "page_visit".equalsIgnoreCase(event)) {
							distinctIdSet.add(log.getDistinctId().trim());
							tempUserIDSet.add(log.getDistinctId().trim());
						}
					}
				}
				distinctIdMap.put(sTempTime, tempUserIDSet);
				orgCountMap.put(sTempTime, 0);
				commCountMap.put(sTempTime, 0);
				otherCountMap.put(sTempTime, 0);
				dateList.add(sTempTime);
				cal.add(Calendar.DATE, 1);
			}
			
			Map<String,Object> conditions = new HashMap();
			conditions.put("uuids", distinctIdSet);
			List<ClipUsersDTO> clipUsersList = this.clipUsersService.getUsersList(conditions);
			Map<String,ClipUsersDTO> clipUsersMap = new HashMap();
			for(ClipUsersDTO clipUsers : clipUsersList) {
				clipUsersMap.put(clipUsers.getUuid(), clipUsers);
			}
			
			for(String key : distinctIdMap.keySet()) {
				int orgUserCount =0;
				int commUserCount = 0;
				int otherUserCount = 0;
				Set<String> tempValueSet = distinctIdMap.get(key);
				for(String distinctId : tempValueSet) {
					ClipUsersDTO clipUsers = clipUsersMap.get(distinctId);
					if(clipUsers != null) {
						if(clipUsers.getIsMaster() == 0 && clipUsers.getUserCategory() == 0) {
							//普通账号
							commUserCount ++;
						}else if(clipUsers.getIsMaster() == 1 && clipUsers.getUserCategory() == 2) {
							//机构账号
							orgUserCount ++;
						}
					}else {
						otherUserCount ++;
					}
				}
				orgCountMap.put(key, orgUserCount);
				commCountMap.put(key, commUserCount);
				otherCountMap.put(key, otherUserCount);
			}
			
			Map dataMap = new HashMap();
			dataMap.put("dateList", dateList);
			dataMap.put("orgCountMap", orgCountMap);
			dataMap.put("commCountMap", commCountMap);
			dataMap.put("otherCountMap", otherCountMap);
			dataMsg.setData(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMsg.failed(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return dataMsg;
	}
	
	//一周内剪视频，剪直播的活跃用户数
	@ResponseBody
	@RequestMapping("/getClipLiveVideoActiveUserData")
	public DataMsg getClipLiveVideoActiveUserData(HttpServletRequest request) throws Exception{
		DataMsg dataMsg = new DataMsg();
		try {
			//读取埋点日志文件
			String path = BIConfig.getValue("FRONTEND_TRACK_LOG_PATH");
			Calendar cal = Calendar.getInstance();
			CalendarUtils.clearHMS(cal);
			Date endTime = cal.getTime();
			cal.add(Calendar.DATE, -6);
			Date startTime = cal.getTime();
			Map<Date,List<TrackLog>> trackLogMap = TrackLogUtils.getTrackLogFileListByTime(path, startTime, endTime);
			
			List<String> dateList = new ArrayList();
			Map<String,Integer> jspCountMap = new HashMap(); //剪视频活跃用户数，key=date value=数量
			Map<String,Integer> jzbCountMap = new HashMap(); //剪直播活跃用户数，key=date value=数量
			cal.setTime(startTime);
			while(cal.getTimeInMillis() <= endTime.getTime()) {
				List<TrackLog> trackLogList = trackLogMap.get(cal.getTime());
				String sTempTime = DateUtil.format(cal.getTime(),"yyyy-MM-dd");
				Set<String> jspSet = new HashSet(); 
				Set<String> jzbSet = new HashSet(); 
				if(trackLogList != null) {
					for(TrackLog log : trackLogList) {
						String event = BaseUtils.parseToString(log.getEvent()).trim();
						if(log.getDistinctId() != null && !"".equals(log.getDistinctId().trim()) && "page_visit".equalsIgnoreCase(event)) {
							String title = BaseUtils.parseToString(log.getProperty("title"));
							if("剪视频".equals(title)) {
								jspSet.add(log.getDistinctId());
							}else if("剪直播".equals(title)) {
								jzbSet.add(log.getDistinctId());
							}
						}
					}
				}
				jspCountMap.put(sTempTime, jspSet.size());
				jzbCountMap.put(sTempTime, jzbSet.size());
				dateList.add(sTempTime);
				cal.add(Calendar.DATE, 1);
			}
			
			Map dataMap = new HashMap();
			dataMap.put("dateList", dateList);
			dataMap.put("jspCountMap", jspCountMap);
			dataMap.put("jzbCountMap", jzbCountMap);
			dataMsg.setData(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMsg.failed(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return dataMsg;
	}
	
	//一周内上传素材数
	@ResponseBody
	@RequestMapping("/getUploadMaterialsStatCount")
	public DataMsg getUploadMaterialsStatCount(HttpServletRequest request) throws Exception{
		DataMsg dataMsg = new DataMsg();
		try {
			Calendar cal = Calendar.getInstance();
			CalendarUtils.clearHMS(cal);
			cal.add(Calendar.DATE, 1);
			Date endTime = cal.getTime();
			cal.add(Calendar.DATE, -7);
			Date startTime = cal.getTime();
			
			Map<String,Object> conditions = new HashMap();
			conditions.put("isDeleted", 0);
//			conditions.put("sourceType", 0);
			conditions.put("mediaState", 2);
			conditions.put("startTime", startTime != null ? startTime.getTime()/1000 : "");//秒
			conditions.put("endTime", endTime != null ? endTime.getTime()/1000 : "");
			List<Map<String,Object>> list = this.biService.getUploadMaterialsStatCount(conditions);
			Map<String,Integer> uploadMaterialsCountMap = new HashMap(); //上传素材数，key=date value=数量
			Map<String,Integer> buyMaterialsCountMap = new HashMap(); //购买素材数，key=date value=数量
			for(Map<String,Object> map : list) {
				int sourceType = BaseUtils.parseToInt(map.get("sourceType"));//来源
				String dateStr = BaseUtils.parseToString(map.get("d"));//日期
				int count = BaseUtils.parseToInt(map.get("c"));//素材数量
				switch (sourceType) {
					case Constant.Materials.SOURCE_TYPE_UPLOAD:{
						//上传
						Integer c = uploadMaterialsCountMap.get(dateStr);
						if(c == null) {
							c = 0;
						}
						uploadMaterialsCountMap.put(dateStr, c + count);
						break;
					}
					case Constant.Materials.SOURCE_TYPE_BUY:{
						//购买
						Integer c = buyMaterialsCountMap.get(dateStr);
						if(c == null) {
							c = 0;
						}
						buyMaterialsCountMap.put(dateStr, c + count);
						break;
					}
				}
				
				
			}
			
			List<String> dateList = new ArrayList();
			cal.setTime(startTime);
			while(cal.getTimeInMillis() <= endTime.getTime()) {
				String sTempTime = DateUtil.format(cal.getTime(),"yyyy-MM-dd");
				Integer c = uploadMaterialsCountMap.get(sTempTime);
				if(c == null) {
					c = 0;
				}
				uploadMaterialsCountMap.put(sTempTime, c);
				c = buyMaterialsCountMap.get(sTempTime);
				if(c == null) {
					c = 0;
				}
				buyMaterialsCountMap.put(sTempTime, c);
				
				dateList.add(sTempTime);
				cal.add(Calendar.DATE, 1);
			}
			
			Map dataMap = new HashMap();
			dataMap.put("dateList", dateList);
			dataMap.put("uploadMaterialsCountMap", uploadMaterialsCountMap);
			dataMap.put("buyMaterialsCountMap", buyMaterialsCountMap);
			dataMsg.setData(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMsg.failed(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return dataMsg;
	}
	
	//一周内上传/购买素材的活跃用户量
	@ResponseBody
	@RequestMapping("/getUploadMaterialsActiveUserStatCount")
	public DataMsg getUploadMaterialsActiveUserStatCount(HttpServletRequest request) throws Exception{
		DataMsg dataMsg = new DataMsg();
		try {
			Calendar cal = Calendar.getInstance();
			CalendarUtils.clearHMS(cal);
			cal.add(Calendar.DATE, 1);
			Date endTime = cal.getTime();
			cal.add(Calendar.DATE, -7);
			Date startTime = cal.getTime();
			
			Map<String,Object> conditions = new HashMap();
			conditions.put("isDeleted", 0);
			conditions.put("sourceType", 0);
			conditions.put("mediaState", 2);
			conditions.put("startTime", startTime != null ? startTime.getTime()/1000 : "");//秒
			conditions.put("endTime", endTime != null ? endTime.getTime()/1000 : "");
			List<Map<String,Object>> list = this.biService.getUploadMaterialsActiveUserStatCount(conditions);
			Map<String,Integer> uploadActiveUserCountMap = new HashMap(); //上传素材活跃用户数，key=date value=数量
			Map<String,Integer> buyActiveUserCountMap = new HashMap(); //购买素材活跃用户数，key=date value=数量
			for(Map<String,Object> map : list) {
				String dateStr = BaseUtils.parseToString(map.get("d"));//日期
				int sourceType = BaseUtils.parseToInt(map.get("sourceType"));//素材来源
				int count = BaseUtils.parseToInt(map.get("c"));//活跃用户量
				
				switch (sourceType) {
					case Constant.Materials.SOURCE_TYPE_UPLOAD:{
						//上传
						Integer c = uploadActiveUserCountMap.get(dateStr);
						if(c == null) {
							c = 0;
						}
						uploadActiveUserCountMap.put(dateStr, c + count);
						break;
					}
					case Constant.Materials.SOURCE_TYPE_BUY:{
						//购买
						Integer c = buyActiveUserCountMap.get(dateStr);
						if(c == null) {
							c = 0;
						}
						buyActiveUserCountMap.put(dateStr, c + count);
						break;
					}
				}
				
				
				
			}
			
			List<String> dateList = new ArrayList();
			cal.setTime(startTime);
			while(cal.getTimeInMillis() <= endTime.getTime()) {
				String sTempTime = DateUtil.format(cal.getTime(),"yyyy-MM-dd");
				Integer c = uploadActiveUserCountMap.get(sTempTime);
				if(c == null) {
					c = 0;
				}
				uploadActiveUserCountMap.put(sTempTime, c);
				c = buyActiveUserCountMap.get(sTempTime);
				if(c == null) {
					c = 0;
				}
				buyActiveUserCountMap.put(sTempTime, c);
				dateList.add(sTempTime);
				cal.add(Calendar.DATE, 1);
			}
			
			Map dataMap = new HashMap();
			dataMap.put("dateList", dateList);
			dataMap.put("uploadActiveUserCountMap", uploadActiveUserCountMap);
			dataMap.put("buyActiveUserCountMap", buyActiveUserCountMap);
			dataMsg.setData(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			dataMsg.failed(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		return dataMsg;
	}
	

}
