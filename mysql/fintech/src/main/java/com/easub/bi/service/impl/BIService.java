package com.easub.bi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.easub.bi.dao.BIDao;
import com.easub.bi.dto.ShopsDTO;
import com.easub.bi.service.IBIService;

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
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return dao.getVideoCopyrightStat(map);
	}
	
	@Override
	public List getVideosSourceTypeStatCount(String startTime, String endTime) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return dao.getVideosSourceTypeStatCount(map);
	}

	@Override
	public List getVideosSourceTypeShareStatCount(Map<String, Object> conditions) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap();
		if(conditions != null && conditions.size() > 0) {
			String startTime  = (String) conditions.get("startTime");
			String endTime  = (String) conditions.get("endTime");
			if(startTime != null && !"".equals(startTime)) {
				map.put("startTime", startTime);
			}
			if(endTime != null && !"".equals(endTime)) {
				map.put("endTime", endTime);
			}
		}
		return this.dao.getVideosSourceTypeShareStatCount(map);
	}



	
	
	

}
