package com.fintech.modules.platform.sysmessage.util;

import com.fintech.platform.restclient.http.RestClientConfig;

/**
 * @Description: 消息服务rest接口地址
 * @author
 * @date 2014年11月17日 上午11:13:08
 */
@Deprecated
public final class RestConstant {
	
	public static final String jyptAppId = "ptpt"; // rest服务appId
	
	public static final String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);// rest服务地址
	
	public static final String ADD_ONE_MSG_URL   = jyptURL + "/api/platform/sysMessage/add/v1"; 
	
	public static final String GET_ONE_MSG_URL  = jyptURL + "/api/platform/sysMessage/get/v1/";
	
	public static final String GET_MORE_MSG_URL = jyptURL + "/api/platform/sysMessage/search/v1";
	
	public static final String ADD_USER_MSG_REL_URL = jyptURL + "/api/platform/sysUserMsgRelation/create/v1";
	
	public static final String GET_USER_MSG_REL_URL = jyptURL + "/api/platform/sysUserMsgRelation/search/v1";
	
	
	
	

}
