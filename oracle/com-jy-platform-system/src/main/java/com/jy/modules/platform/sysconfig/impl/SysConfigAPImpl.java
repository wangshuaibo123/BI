package com.jy.modules.platform.sysconfig.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jy.modules.platform.sysconfig.service.SysConfigService;
import com.jy.platform.api.sysconfig.SysConfigAPI;

public class SysConfigAPImpl extends  SysConfigAPI{
	@Autowired
	@Qualifier("com.jy.modules.platform.sysconfig.service.SysConfigService")
	private SysConfigService sysConfigService;
	
	public String getValue(String key) {
		return sysConfigService.queryValueByCode(key);
	}

}
