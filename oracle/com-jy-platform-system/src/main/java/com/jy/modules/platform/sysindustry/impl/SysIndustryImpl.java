/**
 * 
 */
package com.jy.modules.platform.sysindustry.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jy.modules.platform.sysindustry.dto.SysIndustryDTO;
import com.jy.modules.platform.sysindustry.service.SysIndustryService;
import com.jy.platform.api.sysindustry.SysIndustryAPI;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restclient.http.RestService;

/**
 * <p>行业字典数据实现类</p>	
 * @author lin
 * @2014年12月4日 下午2:13:59
 */
public class SysIndustryImpl implements SysIndustryAPI {

	private static final Logger logger =  LoggerFactory.getLogger(SysIndustryImpl.class);

	private static final String jyptAppId = "jypt";
	
	private static final String jyptURL = RestService.getServiceUrl(jyptAppId);
	
	@Autowired
    @Qualifier("com.jy.modules.platform.sysindustry.service.SysIndustryService")
    private SysIndustryService sysIndustryService;
	
	
//	@SuppressWarnings("rawtypes")
//	@Override
//	public List<Map> getIndustries(String parentCode) throws Exception {
//		if (parentCode == null) {
//			throw new Exception("param parentId is not null");
//		}
//		final String requestUrl = jyptURL + "/api/platform/sysIndustry/searchIndustries/v1";
//		Map<String, Object> searchParams = new HashMap<String, Object>();
//		SysIndustryDTO dto = new SysIndustryDTO();
//		dto.setParentCode(parentCode);
//		dto.setIndustryType("industry");
//    	searchParams.put("dto", dto);
//    	QueryReqBean params = new QueryReqBean();
//		params.setSearchParams(searchParams);
//		ResponseMsg<QueryRespBean<Map>> responseData = RestClient.doPost(jyptAppId,requestUrl, params,
//				new TypeReference<ResponseMsg<QueryRespBean<Map>>>() {
//				});
//		if (ResponseStatus.HTTP_OK.equals(responseData.getRetCode())) {
//			return responseData.getResponseBody().getResult();
//		}
//		return null;
//	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getIndustries(String parentCode) throws Exception {
		if (parentCode == null) {
			throw new Exception("param parentId is not null");
		}
		Map<String, Object> searchParams = new HashMap<String, Object>();
		SysIndustryDTO dto = new SysIndustryDTO();
		dto.setParentCode(parentCode);
		dto.setIndustryType("industry");
    	searchParams.put("dto", dto);
    	QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		
		return sysIndustryService.searchIndustries(params.getSearchParams());
	}

	
//	@SuppressWarnings("rawtypes")
//	@Override
//	public List<Map> getPositionsByIndustry(String defaultIndustry)
//			throws Exception {
//		final String requestUrl = jyptURL + "/api/platform/sysIndustry/getPositionsByIndustry/v1";
//		Map<String, Object> searchParams = new HashMap<String, Object>();
//		SysIndustryDTO dto = new SysIndustryDTO();
//		dto.setParentCode(defaultIndustry);
//		dto.setIndustryType("position");
//    	searchParams.put("dto", dto);
//    	QueryReqBean params = new QueryReqBean();
//		params.setSearchParams(searchParams);
//		ResponseMsg<QueryRespBean<Map>> responseData = RestClient.doPost(jyptAppId,requestUrl, params,
//				new TypeReference<ResponseMsg<QueryRespBean<Map>>>() {
//				});
//		if (ResponseStatus.HTTP_OK.equals(responseData.getRetCode())) {
//			return responseData.getResponseBody().getResult();
//		}
//		return null;
//	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getPositionsByIndustry(String defaultIndustry) throws Exception {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		SysIndustryDTO dto = new SysIndustryDTO();
		dto.setParentCode(defaultIndustry);
		dto.setIndustryType("position");
    	searchParams.put("dto", dto);
    	QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		
		return sysIndustryService.getPositionsByIndustry(params.getSearchParams());
	}
	
	
	
	
}
