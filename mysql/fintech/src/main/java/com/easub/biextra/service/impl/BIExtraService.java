package com.easub.biextra.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.easub.biextra.dao.BIExtraDao;
import com.easub.biextra.service.IBIExtraService;

@Service("com.easub.biextra.service.impl.BIExtraService")
public class BIExtraService implements IBIExtraService{

	@Resource
	private BIExtraDao dao;
	
	@Override
	public Integer getUserAccount(Map<String, Object> map) throws Exception {
		return this.dao.getUserAccount(map);
	}

	@Override
	public List getVideosSourceTypeStatCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return this.dao.getVideosSourceTypeStatCount(map);
	}

	@Override
	public List getVideosSourceTypeShareStatCount(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return this.dao.getVideosSourceTypeShareStatCount(map);
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
