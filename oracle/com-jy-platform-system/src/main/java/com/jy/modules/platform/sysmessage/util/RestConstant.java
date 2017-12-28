package com.jy.modules.platform.sysmessage.util;

import com.jy.platform.restclient.http.RestService;

/**
 * @Description: 消息服务rest接口地址
 * @author zhanglin
 * @date 2014年11月17日 上午11:13:08
 */
@Deprecated
public final class RestConstant {
	
	public static final String jyptAppId = "jypt"; // rest服务appId
	
	public static final String jyptURL = RestService.getServiceUrl(jyptAppId);// rest服务地址
	
	public static final String ADD_ONE_MSG_URL   = jyptURL + "/api/platform/sysMessage/add/v1"; 
	
	public static final String GET_ONE_MSG_URL  = jyptURL + "/api/platform/sysMessage/get/v1/";
	
	public static final String GET_MORE_MSG_URL = jyptURL + "/api/platform/sysMessage/search/v1";
	
	public static final String ADD_USER_MSG_REL_URL = jyptURL + "/api/platform/sysUserMsgRelation/create/v1";
	
	public static final String GET_USER_MSG_REL_URL = jyptURL + "/api/platform/sysUserMsgRelation/search/v1";
	
	
	
	

}
