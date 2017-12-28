package com.jy.platform.api.sysauth;

import java.util.List;
import java.util.Set;

public interface SysAuthAPI {
	   /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
	public Set<String> findRoles(Long userId);
	   /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
	public Set<String> findPermissions(Long userId);
	
}
