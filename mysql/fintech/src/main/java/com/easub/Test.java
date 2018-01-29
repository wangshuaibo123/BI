package com.easub;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easub.bi.Constant;
import com.easub.bi.service.IBIService;
import com.easub.biextra.service.IBIExtraService;
import com.easub.clip.dto.ClipVideoCopyrightSummaryStatDTO;
import com.easub.clip.dto.ClipVideoSummaryStatDTO;
import com.easub.clip.dto.ClipVideoSummaryStatDetailDTO;
import com.easub.clip.service.ClipVideoCopyrightSummaryStatService;
import com.easub.clip.service.ClipVideoSummaryStatDetailService;
import com.easub.clip.service.ClipVideoSummaryStatService;
import com.easub.utils.BaseUtils;
import com.easub.utils.CalendarUtils;
import com.fintech.platform.tools.common.DateUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-base.xml", "classpath:spring-bi.xml" })
public class Test {

	@Resource(name = "com.easub.bi.service.impl.BIService")
	private IBIService biService;
	@Resource(name = "com.easub.biextra.service.impl.BIExtraService")
	private IBIExtraService biExtraService;

	@Resource(name = "com.easub.clip.service.ClipVideoSummaryStatService")
	private ClipVideoSummaryStatService clipVideoSummaryStatService;

	@Resource(name = "com.easub.clip.service.ClipVideoSummaryStatDetailService")
	private ClipVideoSummaryStatDetailService clipVideoSummaryStatDetailService;

	@Resource(name = "com.easub.clip.service.ClipVideoCopyrightSummaryStatService")
	private ClipVideoCopyrightSummaryStatService clipVideoCopyrightSummaryStatService;

	 @org.junit.Test
	 public void test() {
		 System.out.println("=============excuteCopyrightSummaryData start=========");
		 //this.excuteCopyrightSummaryData();
		 System.out.println("=============excuteCopyrightSummaryData end=========");
		 System.out.println("=============excuteVideoDetailData start=========");
		 this.excuteVideoDetailData();
		 System.out.println("=============excuteVideoDetailData end=========");
		 System.out.println("=============excuteVideoLjData start=========");
		 this.excuteVideoLjData();
		 System.out.println("=============excuteVideoLjData end=========");
	
	 }

	// 生成视频累计数据
	@org.junit.Test
	public void excuteVideoLjData() {
		Date startTime = DateUtil.getDateFromString("2017-12-25", "yyyy-MM-dd");
		Date endTime = DateUtil.getDateFromString("2018-01-28", "yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		try {
			long b = System.currentTimeMillis();
			while (cal.getTimeInMillis() <= endTime.getTime()) {
				System.out.println(
						"==================" + DateUtil.format(cal.getTime(), "yyyy-MM-dd") + "===============start");
				List<ClipVideoSummaryStatDTO> statList = this.getClipVideoWeeklyData(cal.getTime());
				// 删除这一天的数据
				Map<String, Object> paramMap = new HashMap();
				paramMap.put("summaryDateMillisecond", cal.getTimeInMillis());
				clipVideoSummaryStatService.deleteClipVideoSummaryStat(paramMap);
				// 插入
				this.clipVideoSummaryStatService.batchInsert(statList);
				System.out.println("==================" + DateUtil.format(cal.getTime(), "yyyy-MM-dd")
						+ "===============end" + (System.currentTimeMillis() - b));
				cal.add(Calendar.DATE, 1);
			}
			System.out.println(System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private List<ClipVideoSummaryStatDTO> getClipVideoWeeklyData(Date date) throws Exception {
		if (date == null) {
			date = new Date();
		}
		CalendarUtils.clearHMS(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		Date date1 = cal.getTime();

		Map<String, Object> conditions = new HashMap();
		conditions.put("endTime", date1.getTime() / 1000);
		// 获取累计数据
		List<Map<String, Object>> clipDataList = this.biService.getVideosSourceTypeStatCount("",
				DateUtil.format(date, "yyyy-MM-dd"));
		List<Map<String, Object>> clipExtraDataList = this.biExtraService.getVideosSourceTypeStatCount(conditions);
		List<Map<String, Object>> allList = new ArrayList();
		allList.addAll(clipDataList);
		allList.addAll(clipExtraDataList);
		Map<String, Integer> clipDataMap = new HashMap();
		List list = new ArrayList();
		Set<String> keySet = new HashSet();
		for (Map<String, Object> m : allList) {
			int sourceType = BaseUtils.parseToInt(m.get("sourceType"));
			int type = BaseUtils.parseToInt(m.get("type"));//// 1全部、2除了ims\iss、3新浪、4政务媒体
			int count = BaseUtils.parseToInt(m.get("c"));
			String key = type + "_" + sourceType;
			Integer tempCount = clipDataMap.get(key);
			if (tempCount == null) {
				tempCount = 0;
			}
			clipDataMap.put(key, tempCount + count);
			keySet.add(key);
		}
		// 累计共享数据
		conditions = new HashMap();
		conditions.put("endTime", date1.getTime() / 1000);// 秒
		List<Map<String, Object>> clipShareDataList = this.biService.getVideosSourceTypeShareStatCount(conditions);
		List<Map<String, Object>> clipExtraShareDataList = this.biExtraService
				.getVideosSourceTypeShareStatCount(conditions);
		List<Map<String, Object>> allExtraList = new ArrayList();
		allExtraList.addAll(clipShareDataList);
		allExtraList.addAll(clipExtraShareDataList);
		Map<String, Integer> clipShareDataMap = new HashMap();
		for (Map<String, Object> m : allExtraList) {
			int sourceType = BaseUtils.parseToInt(m.get("sourceType"));
			int type = BaseUtils.parseToInt(m.get("type"));//// 1全部、2除了ims\iss、3新浪、4政务媒体
			int count = BaseUtils.parseToInt(m.get("c"));
			String key = type + "_" + sourceType;
			Integer tempCount = clipShareDataMap.get(key);
			if (tempCount == null) {
				tempCount = 0;
			}
			clipShareDataMap.put(key, tempCount + count);
			keySet.add(key);
		}

		for (String key : keySet) {
			String[] keyArr = key.split("_");
			int type = BaseUtils.parseToInt(keyArr[0]);// 类型1全部、2除了ims\iss、3新浪、4政务媒体
			int sourceType = BaseUtils.parseToInt(keyArr[1]);// 视频来源，参考云剪系统中video表中的source_type
			int count = BaseUtils.parseToInt(clipDataMap.get(key));// 累计视频数量
			int shareCount = BaseUtils.parseToInt(clipShareDataMap.get(key));// 累计共享数量
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

	// 生成视频每天明细数据
	@org.junit.Test
	public void excuteVideoDetailData() {
		Date startTime = DateUtil.getDateFromString("2017-12-25", "yyyy-MM-dd");
		Date endTime = DateUtil.getDateFromString("2018-01-28", "yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		try {
			long b = System.currentTimeMillis();
			while (cal.getTimeInMillis() <= endTime.getTime()) {
				System.out.println(
						"==================" + DateUtil.format(cal.getTime(), "yyyy-MM-dd") + "===============start");
				List<ClipVideoSummaryStatDetailDTO> detailDtoList = this.getClipVideoDetailData(cal.getTime());
				// 删除这一天的数据
				Map<String, Object> paramMap = new HashMap();
				paramMap.put("summaryDateMillisecond", cal.getTimeInMillis());
				this.clipVideoSummaryStatDetailService.deleteClipVideoSummaryStatDetail(paramMap);
				this.clipVideoSummaryStatDetailService.batchInsert(detailDtoList);
				System.out.println("==================" + DateUtil.format(cal.getTime(), "yyyy-MM-dd")
						+ "===============end" + (System.currentTimeMillis() - b));
				cal.add(Calendar.DATE, 1);
			}
			System.out.println(System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 汇总视频Date这天的数据
	private List<ClipVideoSummaryStatDetailDTO> getClipVideoDetailData(Date date) throws Exception {
		if (date == null) {
			date = new Date();
		}
		CalendarUtils.clearHMS(date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		Date nextDate = cal.getTime();

		Map<String, Object> conditions = new HashMap();
		conditions.put("startTime", date.getTime() / 1000);
		conditions.put("endTime", nextDate.getTime() / 1000);
		// 获取数据
		List<Map<String, Object>> clipDataList = this.biService.getVideosSourceTypeStatCount(CalendarUtils.format(date),
				CalendarUtils.format(date));
		List<Map<String, Object>> clipExtraDataList = this.biExtraService.getVideosSourceTypeStatCount(conditions);
		List<Map<String, Object>> allList = new ArrayList();
		allList.addAll(clipDataList);
		allList.addAll(clipExtraDataList);
		Map<String, Integer> clipDataMap = new HashMap();
		List list = new ArrayList();
		Set<String> keySet = new HashSet();
		for (Map<String, Object> m : allList) {
			int sourceType = BaseUtils.parseToInt(m.get("sourceType"));
			int type = BaseUtils.parseToInt(m.get("type"));//// 1全部、2除了ims\iss、3新浪、4政务媒体
			int count = BaseUtils.parseToInt(m.get("c"));
			String key = type + "_" + sourceType;
			Integer tempCount = clipDataMap.get(key);
			if (tempCount == null) {
				tempCount = 0;
			}
			clipDataMap.put(key, tempCount + count);
			keySet.add(key);
		}
		// 共享数据
		conditions.clear();
		conditions.put("startTime", date.getTime() / 1000);// 秒
		conditions.put("endTime", nextDate.getTime() / 1000);// 秒
		List<Map<String, Object>> clipShareDataList = this.biService.getVideosSourceTypeShareStatCount(conditions);
		List<Map<String, Object>> clipExtraShareDataList = this.biExtraService
				.getVideosSourceTypeShareStatCount(conditions);
		List<Map<String, Object>> allExtraList = new ArrayList();
		allExtraList.addAll(clipShareDataList);
		allExtraList.addAll(clipExtraShareDataList);
		Map<String, Integer> clipShareDataMap = new HashMap();
		for (Map<String, Object> m : allExtraList) {
			int sourceType = BaseUtils.parseToInt(m.get("sourceType"));
			int type = BaseUtils.parseToInt(m.get("type"));//// 1全部、2除了ims\iss、3新浪、4政务媒体
			int count = BaseUtils.parseToInt(m.get("c"));
			String key = type + "_" + sourceType;
			Integer tempCount = clipShareDataMap.get(key);
			if (tempCount == null) {
				tempCount = 0;
			}
			clipShareDataMap.put(key, tempCount + count);
			keySet.add(key);
		}

		for (String key : keySet) {
			String[] keyArr = key.split("_");
			int type = BaseUtils.parseToInt(keyArr[0]);// 类型1全部、2除了ims\iss、3新浪、4政务媒体
			int sourceType = BaseUtils.parseToInt(keyArr[1]);// 视频来源，参考云剪系统中video表中的source_type
			int count = BaseUtils.parseToInt(clipDataMap.get(key));// 视频数量
			int shareCount = BaseUtils.parseToInt(clipShareDataMap.get(key));// 共享数量
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

	@org.junit.Test
	public void excuteCopyrightSummaryData() {
		Date startTime = DateUtil.getDateFromString("2017-12-25", "yyyy-MM-dd");
		Date endTime = DateUtil.getDateFromString("2018-01-28", "yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(startTime);
		long b = System.currentTimeMillis();
		try {
			while (cal.getTimeInMillis() <= endTime.getTime()) {
				System.out.println(
						"==================" + DateUtil.format(cal.getTime(), "yyyy-MM-dd") + "===============start");
				ClipVideoCopyrightSummaryStatDTO copyrightDto = this.getClipVideoCopyrightSummaryStat(cal.getTime());
				// 删除这一天的数据
				Map<String, Object> paramMap = new HashMap();
				paramMap.put("summaryDateMillisecond", cal.getTimeInMillis());
				this.clipVideoCopyrightSummaryStatService.deleteClipVideoCopyrightSummaryStat(paramMap);
				this.clipVideoCopyrightSummaryStatService.insertClipVideoCopyrightSummaryStat(copyrightDto);
				System.out.println("==================" + DateUtil.format(cal.getTime(), "yyyy-MM-dd")
						+ "===============end" + (System.currentTimeMillis() - b));
				cal.add(Calendar.DATE, 1);

			}
			System.out.println("=================================end" + (System.currentTimeMillis() - b));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private ClipVideoCopyrightSummaryStatDTO getClipVideoCopyrightSummaryStat(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		Date date1 = cal.getTime();
		ClipVideoCopyrightSummaryStatDTO dto = new ClipVideoCopyrightSummaryStatDTO();
		Map<String, Object> map = new HashMap();
		map.put("mediaState", Constant.Videos.MEDIA_STATE_SUCCESS);
		map.put("isPublic", 1);
		map.put("isAbroad", 1);
		map.put("userIds", "3458,3459,3460,3463,3464,5321,3467,5088");
		map.put("endTime", date1.getTime() / 1000);// 秒
		// 国外
		Map<String, Object> resultMap = this.biService.getVideoStatCount(map);
		int abroadCount = 0;
		int homeCount = 0;
		double abroadLength = 0;
		double homeLength = 0;
		if (resultMap != null) {
			abroadCount = BaseUtils.parseToInt(resultMap.get("c"));
			abroadLength = BaseUtils.parseToDouble(resultMap.get("sec"));
		}
		// 国内
		map.put("isAbroad", 0);
		map.put("userIds", "4270,4271,4272,4274,4277,4275,4484,4495,4496,4631,4366,4741");
		resultMap = this.biService.getVideoStatCount(map);
		if (resultMap != null) {
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

	public static void main(String[] args) {

		// String url = "redis://:222@127.0.0.1:6379/0";
		// URI uri = URI.create(url);
		// String userInfo = uri.getUserInfo();
		// if (userInfo != null) {
		// String s = userInfo.split(":", 2)[1];
		// System.out.println(s);
		// }
		// String[] pathSplit = uri.getPath().split("/",2);
		// System.out.println(pathSplit[1]);

//		System.out.println(CalendarUtils.format(new Date(), CalendarUtils.DATE_TIMESTAMP_FORMAT));
//
//		System.out.println(DateUtil.getDateFromString("2018-01-26", "yyyy-MM-dd").getTime());
//		System.out.println(DateUtil.getDateFromString("2018-01-29", "yyyy-MM-dd").getTime());

		 //连接本地的 Redis 服务
		String host = "http://127.0.0.1:6379/1";
		JedisShardInfo js = new JedisShardInfo(host);
		
		 Jedis jedis = new Jedis(js);
		 jedis.auth("000000aA");
		 System.out.println("连接成功");
		 //查看服务是否运行
		 System.out.println("服务正在运行: "+jedis.ping());
		 
		 String str = jedis.get("clip_system_front_log");
		 System.out.println(str);

	}

}
