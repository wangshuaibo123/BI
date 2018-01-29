package com.easub.bi.quartzjob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.easub.bi.Constant;
import com.easub.bi.service.IBIService;
import com.easub.bi.service.impl.BIService;
import com.easub.biextra.service.IBIExtraService;
import com.easub.biextra.service.impl.BIExtraService;
import com.easub.clip.dto.ClipVideoCopyrightSummaryStatDTO;
import com.easub.clip.dto.ClipVideoSummaryStatDTO;
import com.easub.clip.dto.ClipVideoSummaryStatDetailDTO;
import com.easub.clip.service.ClipVideoCopyrightSummaryStatService;
import com.easub.clip.service.ClipVideoSummaryStatDetailService;
import com.easub.clip.service.ClipVideoSummaryStatService;
import com.easub.utils.BaseUtils;
import com.easub.utils.CalendarUtils;
import com.fintech.platform.tools.common.DateUtil;

/**
 * 每天定时查询视频累计数据
 * 按版权（国内、国外）汇总累计数量和时长（秒）
 */
@Component("com.easub.bi.quartzjob.CumulativeVideoDataStatJobTask")
@SuppressWarnings("all")
public class CumulativeVideoDataStatJobTask implements Job,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4186189624449566313L;
	private static final Logger logger = LoggerFactory.getLogger(CumulativeVideoDataStatJobTask.class);
	private static boolean isNext = true;
	private IBIService biService;
	private IBIExtraService biExtraService;
	private ClipVideoSummaryStatService clipVideoSummaryStatService;
	private ClipVideoSummaryStatDetailService clipVideoSummaryStatDetailService;
	private ClipVideoCopyrightSummaryStatService clipVideoCopyrightSummaryStatService;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		if(!isNext) {
			return;
		}
		logger.info("------------CumulativeVideoDataStatJobTask-------start-------------");
		isNext = false;
		try {
			SchedulerContext context = arg0.getScheduler().getContext();
			ApplicationContext ctx = (ApplicationContext) context.get("applicationContextKey");
			this.biService = ctx.getBean(BIService.class);
			this.biExtraService = ctx.getBean(BIExtraService.class);
			this.clipVideoSummaryStatService = ctx.getBean(ClipVideoSummaryStatService.class);
			this.clipVideoSummaryStatDetailService = ctx.getBean(ClipVideoSummaryStatDetailService.class);
			this.clipVideoCopyrightSummaryStatService = ctx.getBean(ClipVideoCopyrightSummaryStatService.class);
			//获取前一天
			Calendar cal = Calendar.getInstance();
			CalendarUtils.clearHMS(cal);
			cal.add(Calendar.DATE, -1);
			Date date = cal.getTime();
			//按视频来源汇总视频累计数据=========
			logger.info("....ClipVideoSummaryStat start..................");
			List<ClipVideoSummaryStatDTO> statList = this.getClipVideoWeeklyData(date);
			Map<String,Object> paramMap = new HashMap();
			paramMap.put("summaryDateMillisecond", date.getTime());
			//删除这一天的数据
			this.clipVideoSummaryStatService.deleteClipVideoSummaryStat(paramMap);
			//插入
			this.clipVideoSummaryStatService.batchInsert(statList);
			logger.info("....ClipVideoSummaryStat end..................");
			//按视频来源汇总视频累计数据=========end=====================
			
			//按视频来源汇总视频数据=========start
			//每天的视频汇总数据
			logger.info("....ClipVideoSummaryStatDetail start..................");
			List<ClipVideoSummaryStatDetailDTO> detailDtoList = this.getClipVideoDetailData(date);
			paramMap = new HashMap();
			paramMap.put("summaryDateMillisecond", date.getTime());
			this.clipVideoSummaryStatDetailService.deleteClipVideoSummaryStatDetail(paramMap);
			this.clipVideoSummaryStatDetailService.batchInsert(detailDtoList);
			logger.info("....ClipVideoSummaryStatDetail end..................");
			//按视频来源汇总每天视频数据=========end=====================
			
			//按版权（国内、国外）汇总累计数量和时长（秒）
			logger.info("....ClipVideoCopyrightSummaryStat start..................");
			ClipVideoCopyrightSummaryStatDTO copyrightDto = this.getClipVideoCopyrightSummaryStat(date);
			//先删除今天的，再插入
			paramMap = new HashMap();
			paramMap.put("summaryDateMillisecond", date.getTime());
			this.clipVideoCopyrightSummaryStatService.deleteClipVideoCopyrightSummaryStat(paramMap);
			this.clipVideoCopyrightSummaryStatService.insertClipVideoCopyrightSummaryStat(copyrightDto);
			logger.info("....ClipVideoCopyrightSummaryStat end..................");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CumulativeVideoDataStatJobTask error:" + e.getMessage());
		}finally {
			logger.info("------------CumulativeVideoDataStatJobTask-------end-------------");
			isNext = true;
		}
	}
	
	//截止到Date的累计视频数据
	private List<ClipVideoSummaryStatDTO> getClipVideoWeeklyData(Date date) throws Exception{
		if(date == null) {
			date = new Date();
		}
		CalendarUtils.clearHMS(date);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		Date date1 = cal.getTime();
		
		Map<String,Object> conditions = new HashMap();
		conditions.put("endTime", date1.getTime()/1000);
		//获取累计数据
		List<Map<String,Object>> clipDataList = this.biService.getVideosSourceTypeStatCount("", DateUtil.format(date1,"yyyy-MM-dd"));
		List<Map<String,Object>> clipExtraDataList = this.biExtraService.getVideosSourceTypeStatCount(conditions);
		List<Map<String,Object>> allList = new ArrayList();
		allList.addAll(clipDataList);
		allList.addAll(clipExtraDataList);
		Map<String,Integer> clipDataMap = new HashMap();
		List list = new ArrayList();
		Set<String> keySet = new HashSet();
		for(Map<String,Object> m : allList) {
			int sourceType = BaseUtils.parseToInt(m.get("sourceType"));
			int type = BaseUtils.parseToInt(m.get("type"));////1全部、2除了ims\iss、3新浪、4政务媒体
			int count = BaseUtils.parseToInt(m.get("c"));
			String key = type + "_" + sourceType;
			Integer tempCount = clipDataMap.get(key);
			if(tempCount == null) {
				tempCount = 0;
			}
			clipDataMap.put(key, tempCount + count);
			keySet.add(key);
		}
		//累计共享数据
		conditions.clear();
		conditions.put("endTime", date1.getTime()/1000);//秒
		List<Map<String,Object>> clipShareDataList = this.biService.getVideosSourceTypeShareStatCount(conditions);
		List<Map<String,Object>> clipExtraShareDataList = this.biExtraService.getVideosSourceTypeShareStatCount(conditions);
		List<Map<String,Object>> allExtraList = new ArrayList();
		allExtraList.addAll(clipShareDataList);
		allExtraList.addAll(clipExtraShareDataList);
		Map<String,Integer> clipShareDataMap = new HashMap();
		for(Map<String,Object> m : allExtraList) {
			int sourceType = BaseUtils.parseToInt(m.get("sourceType"));
			int type = BaseUtils.parseToInt(m.get("type"));////1全部、2除了ims\iss、3新浪、4政务媒体
			int count = BaseUtils.parseToInt(m.get("c"));
			String key = type + "_" + sourceType;
			Integer tempCount = clipShareDataMap.get(key);
			if(tempCount == null) {
				tempCount = 0;
			}
			clipShareDataMap.put(key, tempCount + count);
			keySet.add(key);
		}
		
		for(String key : keySet) {
			String[] keyArr = key.split("_");
			int type = BaseUtils.parseToInt(keyArr[0]);//类型1全部、2除了ims\iss、3新浪、4政务媒体
			int sourceType = BaseUtils.parseToInt(keyArr[1]);//视频来源，参考云剪系统中video表中的source_type
			int count = BaseUtils.parseToInt(clipDataMap.get(key));//累计视频数量
			int shareCount = BaseUtils.parseToInt(clipShareDataMap.get(key));//累计共享数量
			ClipVideoSummaryStatDTO dto = new ClipVideoSummaryStatDTO();
			dto.setType(type);
			dto.setSourceType(sourceType);
			dto.setCount(count);
			dto.setShareCount(shareCount);
			dto.setSummaryDate(date);
			dto.setSummaryDateMillisecond(date.getTime());
			list.add(dto);
		}
		return list;
	}
	
	//汇总视频Date这天的数据
	private List<ClipVideoSummaryStatDetailDTO> getClipVideoDetailData(Date date) throws Exception{
		if(date == null) {
			date = new Date();
		}
		CalendarUtils.clearHMS(date);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		Date nextDate = cal.getTime();
		
		Map<String,Object> conditions = new HashMap();
		conditions.put("startTime", date.getTime()/1000);
		conditions.put("endTime", nextDate.getTime()/1000);
		//获取数据
		List<Map<String,Object>> clipDataList = this.biService.getVideosSourceTypeStatCount(CalendarUtils.format(date), CalendarUtils.format(nextDate));
		List<Map<String,Object>> clipExtraDataList = this.biExtraService.getVideosSourceTypeStatCount(conditions);
		List<Map<String,Object>> allList = new ArrayList();
		allList.addAll(clipDataList);
		allList.addAll(clipExtraDataList);
		Map<String,Integer> clipDataMap = new HashMap();
		List list = new ArrayList();
		Set<String> keySet = new HashSet();
		for(Map<String,Object> m : allList) {
			int sourceType = BaseUtils.parseToInt(m.get("sourceType"));
			int type = BaseUtils.parseToInt(m.get("type"));////1全部、2除了ims\iss、3新浪、4政务媒体
			int count = BaseUtils.parseToInt(m.get("c"));
			String key = type + "_" + sourceType;
			Integer tempCount = clipDataMap.get(key);
			if(tempCount == null) {
				tempCount = 0;
			}
			clipDataMap.put(key, tempCount + count);
			keySet.add(key);
		}
		//共享数据
		conditions.clear();
		conditions.put("startTime", date.getTime()/1000);//秒
		conditions.put("endTime", nextDate.getTime()/1000);//秒
		List<Map<String,Object>> clipShareDataList = this.biService.getVideosSourceTypeShareStatCount(conditions);
		List<Map<String,Object>> clipExtraShareDataList = this.biExtraService.getVideosSourceTypeShareStatCount(conditions);
		List<Map<String,Object>> allExtraList = new ArrayList();
		allExtraList.addAll(clipShareDataList);
		allExtraList.addAll(clipExtraShareDataList);
		Map<String,Integer> clipShareDataMap = new HashMap();
		for(Map<String,Object> m : allExtraList) {
			int sourceType = BaseUtils.parseToInt(m.get("sourceType"));
			int type = BaseUtils.parseToInt(m.get("type"));////1全部、2除了ims\iss、3新浪、4政务媒体
			int count = BaseUtils.parseToInt(m.get("c"));
			String key = type + "_" + sourceType;
			Integer tempCount = clipShareDataMap.get(key);
			if(tempCount == null) {
				tempCount = 0;
			}
			clipShareDataMap.put(key, tempCount + count);
			keySet.add(key);
		}
		
		for(String key : keySet) {
			String[] keyArr = key.split("_");
			int type = BaseUtils.parseToInt(keyArr[0]);//类型1全部、2除了ims\iss、3新浪、4政务媒体
			int sourceType = BaseUtils.parseToInt(keyArr[1]);//视频来源，参考云剪系统中video表中的source_type
			int count = BaseUtils.parseToInt(clipDataMap.get(key));//视频数量
			int shareCount = BaseUtils.parseToInt(clipShareDataMap.get(key));//共享数量
			ClipVideoSummaryStatDetailDTO dto = new ClipVideoSummaryStatDetailDTO();
			dto.setType(type);
			dto.setSourceType(sourceType);
			dto.setCount(count);
			dto.setShareCount(shareCount);
			dto.setSummaryDate(date);
			dto.setSummaryDateMillisecond(date.getTime());
			list.add(dto);
		}
		return list;
	}
	
	private ClipVideoCopyrightSummaryStatDTO getClipVideoCopyrightSummaryStat(Date date) throws Exception{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		Date date1 = cal.getTime();
		ClipVideoCopyrightSummaryStatDTO dto = new ClipVideoCopyrightSummaryStatDTO();
		Map<String,Object> map = new HashMap();
		map.put("mediaState", Constant.Videos.MEDIA_STATE_SUCCESS);
		map.put("isPublic", 1);
		map.put("isAbroad", 1);
		map.put("userIds", "3458,3459,3460,3463,3464,5321,3467,5088");
		map.put("endTime", date1.getTime()/1000);//秒
		//国外
		Map<String,Object> resultMap = this.biService.getVideoStatCount(map);
		int abroadCount = 0;
		int homeCount = 0;
		double abroadLength = 0;
		double homeLength = 0;
		if(resultMap != null) {
			abroadCount = BaseUtils.parseToInt(resultMap.get("c"));
			abroadLength = BaseUtils.parseToDouble(resultMap.get("sec"));
		}
		//国内
		map.put("isAbroad", 0);
		map.put("userIds", "4270,4271,4272,4274,4277,4275,4484,4495,4496,4631,4366,4741");
		resultMap = this.biService.getVideoStatCount(map);
		if(resultMap != null) {
			homeCount = BaseUtils.parseToInt(resultMap.get("c"));
			homeLength = BaseUtils.parseToDouble(resultMap.get("sec"));
		}
		dto.setAbroadCount(abroadCount);
		dto.setAbroadLength(abroadLength);
		dto.setHomeCount(homeCount);
		dto.setHomeLength(homeLength);
		dto.setSummaryDate(date);
		dto.setSummaryDateMillisecond(date.getTime());
		return dto;
	}

	
}
