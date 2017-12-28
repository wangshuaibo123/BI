package com.jy.modules.platform.sysauth.service.shiro;

import java.util.List;
import java.util.Set;

import com.jy.modules.platform.sysMenu.dto.SysMenuDTO;
/**
 * 
 * @description：定义授权接口 
 * @author chen_gang
 * @date:2015年1月11日下午5:58:07
 */
public interface IAuthService {
	/**
     * 加载过滤配置信息 供filterChainDefinitions 配置使用
     * @return
     */
    public String loadFilterChainDefinitions();
    
    /**
     * 重新构建权限过滤器
     * 一般在修改了用户角色、用户等信息时，需要再次调用该方法
     */
    public void reCreateFilterChains();
    
    
    public List<SysMenuDTO> findMenusByUserId(Long userId) throws Exception;
    
    public Set<String> findRoles(Long userId) ;
    
    public Set<String> findPermissions(Long userId);

}
