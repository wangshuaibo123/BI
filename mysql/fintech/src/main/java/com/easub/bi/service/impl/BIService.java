package com.easub.bi.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.easub.bi.dao.BIDao;
import com.easub.bi.dto.ShopsDTO;
import com.easub.bi.service.IBIService;
import com.fintech.platform.tools.common.DateUtil;

@Service("com.easub.bi.service.impl.BIService")
public class BIService implements IBIService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9209982195909311692L;
	
	@Resource
	private BIDao dao;


	@Override
	public List<ShopsDTO> getShopsList(Map<String, Object> conditions) throws Exception{
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap();
		if(conditions != null && conditions.size() > 0) {
			
		}
		return this.dao.getShopsList(map);
	}

	@Override
	public List getVideoShopStat(Map<String, Object> conditions) throws Exception{
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap();
		if(conditions != null && conditions.size() > 0) {
			
		}
		return this.dao.getVideoShopStat(map);
	}
	
	@Override
	public List getVideoCopyrightStat(String startTime, String endTime) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap();
		//秒
		Date sTime = DateUtil.getDateFromString(startTime, "yyyy-MM-dd");
		map.put("startTime", sTime != null ? sTime.getTime()/1000 : "");//秒
		Date eTime = DateUtil.getDateFromString(endTime, "yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(eTime);
		cal.add(Calendar.DATE, 1);
		eTime = cal.getTime();
		map.put("endTime", eTime != null ? eTime.getTime()/1000 : "");
		return dao.getVideoCopyrightStat(map);
	}
	
	public Map<String,Object> getVideoStatCount(Map<String,Object> map) throws Exception{
		return dao.getVideoStatCount(map);
	}
	
	@Override
	public List getVideosSourceTypeStatCount(String startTime, String endTime) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap();
		//秒
		Date sTime = DateUtil.getDateFromString(startTime, "yyyy-MM-dd");
		map.put("startTime", sTime != null ? sTime.getTime()/1000 : "");//秒
		Date eTime = DateUtil.getDateFromString(endTime, "yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(eTime);
		cal.add(Calendar.DATE, 1);
		eTime = cal.getTime();
		map.put("endTime", eTime != null ? eTime.getTime()/1000 : "");
		return dao.getVideosSourceTypeStatCount(map);
	}

	@Override
	public List getVideosSourceTypeShareStatCount(Map<String, Object> conditions) throws Exception {
		// TODO Auto-generated method stub
		return this.dao.getVideosSourceTypeShareStatCount(conditions);
	}

	@Override
	public List getUploadMaterialsStatCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.dao.getUploadMaterialsStatCount(map);
	}

	@Override
	public List getUploadMaterialsActiveUserStatCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.dao.getUploadMaterialsActiveUserStatCount(map);
	}

	@Override
	public Integer getVideosStatCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.dao.getVideosStatCount(map);
	}

	@Override
	public Integer getVideosShareStatCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.dao.getVideosShareStatCount(map);
	}

	@Override
	public Long getVideoVV(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.dao.getVideoVV(map);
	}



	
	
	

}
