package com.jy.modules.platform.sysarea.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jy.modules.platform.sysarea.service.SysAreaService;
import com.jy.platform.api.sysarea.SysAreaAPI;

public class SysAreaAPImpl implements SysAreaAPI {

	@Autowired
	private SysAreaService sysAreaService;

	@Override
	public List<Map> queryChildAreaByCode(String areaCode) {
		// TODO Auto-generated method stub
		return sysAreaService.queryChildAreaByCode(areaCode);
	}

	@Override
	public List<Map> queryChildAreaByPid(String parentId) {
		// TODO Auto-generated method stub
		return sysAreaService.queryChildAreaByPid(parentId);
	}

	@Override
	public String queryParentCodeByCode(String areaCode) {
		// TODO Auto-generated method stub
		Map map = sysAreaService.getParentCodeByCode(areaCode);
		if(map != null){
			return "" + map.get("AREACODE");
		}
		return "";
	}

	@Override
	public Map getAreaByCode(String areaCode) {
		// TODO Auto-generated method stub
		return sysAreaService.getAreaByCode(areaCode);
	}



}
