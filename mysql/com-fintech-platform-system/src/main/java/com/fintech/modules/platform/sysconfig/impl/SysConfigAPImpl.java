package com.fintech.modules.platform.sysconfig.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fintech.modules.platform.sysconfig.service.SysConfigService;
import com.fintech.platform.api.sysconfig.SysConfigAPI;

public class SysConfigAPImpl extends  SysConfigAPI{
	@Autowired
	@Qualifier("com.fintech.modules.platform.sysconfig.service.SysConfigService")
	private SysConfigService sysConfigService;
	
	public String getValue(String key) {
		return sysConfigService.queryValueByCode(key);
	}

}
