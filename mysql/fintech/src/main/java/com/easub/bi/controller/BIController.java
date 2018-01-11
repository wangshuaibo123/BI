package com.easub.bi.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.easub.bi.Constant;
import com.easub.bi.dto.ShopsDTO;
import com.easub.bi.service.IBIService;
import com.easub.utils.BaseUtils;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.restservice.web.base.BaseController;
import com.fintech.platform.tools.common.DateUtil;


@SuppressWarnings({"unchecked","rawtypes"})
@Controller
@RequestMapping("/bi")
public class BIController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(BIController.class);
	
	@Resource(name="com.easub.bi.service.impl.BIService")
	private IBIService biService;
	
	
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
			Map map = new HashMap();
			map.put("id", 1);
			map.put("name", "统计视频每周分享转发播放数");
			map.put("des", "统计视频每周分享转发播放数");
			list.add(map);
			map = new HashMap();
			map.put("id", 2);
			map.put("name", "按用户类型统计每周开通账号数");
			map.put("des", "按用户类型统计每周开通账号数");
			list.add(map);
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
				int id = BaseUtils.nullToInt(map.get("shop_id"));//商户ID
				int count = BaseUtils.nullToInt(map.get("c"));
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
			Map<String,Object> dataMap = new HashMap();
			for(Map<String,Object> map : list) {
				Object oIsAbroad = map.get("abroad");//0(false)为国内视频，1(true)为国外视频
				Object oCount = map.get("c");
				Object oSecond = map.get("sec");//视频时长，秒
				if(oIsAbroad == null || "".equals(oIsAbroad.toString().trim())) {
					continue;
				}
				if(oCount == null || "".equals(oCount.toString().trim())) {
					oCount = 0;
				}
				if(oSecond == null || "".equals(oSecond.toString().trim())) {
					oSecond = 0;
				}
				boolean isAbroad = "true".equals(oIsAbroad.toString());
				int count = Integer.parseInt(oCount.toString());
				double second = Double.parseDouble(oSecond.toString());
				String abroadName = "";
				if(isAbroad) {
					abroadName = "国外版权";
				}else{
					abroadName = "国内版权";
				}
				
				Map<String,Object> tempMap = new HashMap();
				tempMap.put("abroad", isAbroad);
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
			conditions.put("startTime", startTime);
			conditions.put("endTime", endTime);
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
		}
		return dataMsg;
	}
	
	

}
