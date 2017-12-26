package com.fintech.modules.platform.sysauth.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.fintech.modules.platform.sysauth.service.SysResourceService;
import com.fintech.modules.platform.sysauth.service.SysRoleService;
import com.fintech.modules.platform.sysauth.service.shiro.AuthServiceImpl;
import com.fintech.platform.api.sysauth.SysAuthAPI;

public class SysAuthAPImpl implements SysAuthAPI {
    @Autowired
    @Lazy
	private SysRoleService sysRoleService;
    
    @Autowired
    @Lazy
	private SysResourceService sysResourceService;
    
    @Autowired
    @Lazy
	private AuthServiceImpl authServiceImpl;
    
    
	@Override
	public Set<String> findRoles(Long userId) {
		return authServiceImpl.findRoles(userId);
	}

	@Override
	public Set<String> findPermissions(Long userId) {
		return authServiceImpl.findPermissions(userId);
	}
	
	

}
