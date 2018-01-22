package com.easub.bi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.easub.bi.dao.ClipUsersDao;
import com.easub.bi.dto.ClipUsersDTO;
import com.easub.bi.service.IClipUsersService;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Service("com.easub.bi.service.impl.ClipUsersService")
public class ClipUsersService implements IClipUsersService{

	@Resource
	private ClipUsersDao dao;
	
	@Override
	public List<ClipUsersDTO> getUsersList(Map<String, Object> conditions) throws Exception {
		Map<String,Object> map = new HashMap();
		if(conditions != null && conditions.size() > 0) {
			
		}
		return this.dao.getUsersList(map);
	}

	@Override
	public Integer getUsersAccountCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.dao.getUsersAccountCount(map);
	}

	
}
