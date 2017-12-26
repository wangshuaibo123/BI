package com.fintech.modules.platform.sysauth.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fintech.modules.platform.sysauth.dto.SysRoleDTO;
import com.fintech.modules.platform.sysauth.dto.SysRoleGroupDTO;
import com.fintech.modules.platform.sysauth.dto.SysRoleUserDTO;
import com.fintech.modules.platform.sysauth.service.SysRoleGroupService;
import com.fintech.modules.platform.sysauth.service.SysRoleService;
import com.fintech.modules.platform.sysauth.service.SysRoleUserService;
import com.fintech.modules.platform.sysorg.service.SysUserService;
import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restclient.http.RestClientConfig;
import com.fintech.platform.tools.common.BeanUtil;

public class SysRoleAPImpl implements com.fintech.platform.api.sysauth.SysRoleAPI {

	private static final Logger logger = LoggerFactory.getLogger(SysRoleAPImpl.class);
	private String jyptAppId = "ptpt"; // rest服务appId
	private String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);// rest服务地址
	@Autowired
	private OrgAPI orgApi;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
    @Qualifier("com.fintech.modules.platform.sysauth.service.SysRoleService")
    private SysRoleService sysRoleService;
	
	@Autowired
    @Qualifier("com.fintech.modules.platform.sysrolegroup.service.SysRoleGroupService")
    private SysRoleGroupService sysRoleGroupService;
	
	@Autowired
    @Qualifier("com.fintech.modules.platform.sysauth.service.SysRoleUserService")
    private SysRoleUserService sysRoleUserService;
	

//	@Override
//	public List<Map<String, Object>> queryRoleList(Integer pageSize, Integer currentPage, Map<String, Object> param) {
//
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//		try {
//			Map<String, Object> searchParams = new HashMap<String, Object>();
//
//			// 默认为分页的url
//			String url = jyptURL + "/api/sysRole/searchByPage/v1";
//			PageParameter pageInfo = new PageParameter();
//			if (pageSize==null || pageSize == 0) {
//				url = jyptURL + "/api/sysRole/search/v1";
//			}else{
//				pageInfo.setPageSize(pageSize);
//				pageInfo.setCurrentPage(currentPage);
//			}
//			SysRoleDTO dtoParam = new SysRoleDTO();// 可能将param中的参数扩展到 dtoParam中
//			BeanUtils.populate(dtoParam, param);//
//
//			searchParams.put("dto", dtoParam);// 参数
//			searchParams.put("groupIds",param.get("groupIds"));//角色组的参数
//			QueryReqBean params = new QueryReqBean();
//			params.setSearchParams(searchParams);
//			params.setPageParameter(pageInfo);
//
//			ResponseMsg<QueryRespBean<SysRoleDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params,
//					new TypeReference<ResponseMsg<QueryRespBean<SysRoleDTO>>>() {
//					});
//
//			List<SysRoleDTO> list = responseMsg.getResponseBody().getResult();
//			for (SysRoleDTO sysRoleDTO : list) {
//				Map<String, Object> dtoMap = BeanUtil.transBean2Map(sysRoleDTO);
//				result.add(dtoMap);
//				dtoMap.put("totalCount", responseMsg.getResponseBody().getPageParameter().getTotalCount());
//				dtoMap.put("totalPage", responseMsg.getResponseBody().getPageParameter().getTotalPage());
//				dtoMap.put("totalRows", responseMsg.getResponseBody()
//						.getPageParameter().getTotalPage());
//			}
//
//		} catch (Exception e) {
//			logger.error("执行 接口查询异常：", e);
//		}
//		return result;
//	}
	
	@Override
	public List<Map<String, Object>> queryRoleList(Integer pageSize, Integer currentPage, Map<String, Object> param) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			PageParameter pageInfo = new PageParameter();
			
			SysRoleDTO dtoParam = new SysRoleDTO();// 可能将param中的参数扩展到 dtoParam中
			BeanUtils.populate(dtoParam, param);//

			searchParams.put("dto", dtoParam);// 参数
			searchParams.put("groupIds",param.get("groupIds"));//角色组的参数
			QueryReqBean params = new QueryReqBean();
			params.setSearchParams(searchParams);
			params.setPageParameter(pageInfo);
			
			
			List<SysRoleDTO> list = new ArrayList<SysRoleDTO>();
			//不分页
			if(pageSize==null || pageSize==0){
				list = sysRoleService.searchSysRole(params.getSearchParams());
			}
			//分页
			else{
				pageInfo.setPageSize(pageSize);
				pageInfo.setCurrentPage(currentPage);
				
				list = sysRoleService.searchSysRoleByPaging(params.getSearchParams());
			}

			for (SysRoleDTO sysRoleDTO : list) {
				Map<String, Object> dtoMap = BeanUtil.transBean2Map(sysRoleDTO);
				result.add(dtoMap);
				dtoMap.put("totalCount", params.getPageParameter().getTotalCount());
				dtoMap.put("totalPage", params.getPageParameter().getTotalPage());
				dtoMap.put("totalRows", params.getPageParameter().getTotalPage());
			}
		} 
		catch (Exception e) {
			logger.error("执行 接口查询异常：", e);
		}
		
		return result;
	}
	
	
//	@Override
//	public List<Map<String, Object>> queryRoleGroupList(Integer pageSize, Integer currentPage, Map<String, Object> param) {
//
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//		try {
//			Map<String, Object> searchParams = new HashMap<String, Object>();
//
//			// 默认为分页的url
//			String url = jyptURL + "/api/platform/sysRoleGroup/searchByPage/v1";
//			PageParameter pageInfo = new PageParameter();
//			if (pageSize==null || pageSize == 0) {
//				url = jyptURL + "/api/platform/sysRoleGroup/search/v1";
//			}else{
//				pageInfo.setPageSize(pageSize);
//				pageInfo.setCurrentPage(currentPage);
//			}
//			SysRoleGroupDTO dtoParam = new SysRoleGroupDTO();// 可能将param中的参数扩展到 dtoParam中
//			BeanUtils.populate(dtoParam, param);//
//
//			searchParams.put("dto", dtoParam);// 参数
//			QueryReqBean params = new QueryReqBean();
//			params.setSearchParams(searchParams);
//			params.setPageParameter(pageInfo);
//
//			ResponseMsg<QueryRespBean<SysRoleGroupDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params,
//					new TypeReference<ResponseMsg<QueryRespBean<SysRoleGroupDTO>>>() {
//					});
//
//			List<SysRoleGroupDTO> list = responseMsg.getResponseBody().getResult();
//			for (SysRoleGroupDTO sysRoleGroupDTO : list) {
//				Map<String, Object> dtoMap = BeanUtil.transBean2Map(sysRoleGroupDTO);
//				result.add(dtoMap);
//				dtoMap.put("totalCount", responseMsg.getResponseBody().getPageParameter().getTotalCount());
//				dtoMap.put("totalPage", responseMsg.getResponseBody().getPageParameter().getTotalPage());
//				dtoMap.put("totalRows", responseMsg.getResponseBody()
//						.getPageParameter().getTotalPage());
//			}
//		} catch (Exception e) {
//			logger.error("执行 接口查询异常：", e);
//		}
//		return result;
//	}
	
	@Override
	public List<Map<String, Object>> queryRoleGroupList(Integer pageSize, Integer currentPage, Map<String, Object> param) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			PageParameter pageInfo = new PageParameter();
			
			SysRoleGroupDTO dtoParam = new SysRoleGroupDTO();// 可能将param中的参数扩展到 dtoParam中
			BeanUtils.populate(dtoParam, param);//

			searchParams.put("dto", dtoParam);// 参数
			QueryReqBean params = new QueryReqBean();
			params.setSearchParams(searchParams);
			params.setPageParameter(pageInfo);
			
			List<SysRoleGroupDTO> list = new ArrayList<SysRoleGroupDTO>();
			//不分页
			if(pageSize==null || pageSize==0){
				list = sysRoleGroupService.searchSysRoleGroup(params.getSearchParams());
			}
			//分页
			else{
				pageInfo.setPageSize(pageSize);
				pageInfo.setCurrentPage(currentPage);
				
				list = sysRoleGroupService.searchSysRoleGroupByPaging(params.getSearchParams());
			}
			
			for (SysRoleGroupDTO sysRoleGroupDTO : list) {
				Map<String, Object> dtoMap = BeanUtil.transBean2Map(sysRoleGroupDTO);
				result.add(dtoMap);
				dtoMap.put("totalCount", params.getPageParameter().getTotalCount());
				dtoMap.put("totalPage", params.getPageParameter().getTotalPage());
				dtoMap.put("totalRows", params.getPageParameter().getTotalPage());
			}
		} 
		catch (Exception e) {
			logger.error("执行 接口查询异常：", e);
		}
		
		return result;
	}
	

//	@Override
//	public List<Map<String, Object>> queryUserList(Integer pageSize, Integer currentPage, Map<String, Object> param) {
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//		try {
//			Map<String, Object> searchParams = new HashMap<String, Object>();
//			QueryReqBean params = new QueryReqBean();
//			
//			searchParams.put("userName", param.get("userName"));// 参数
//			searchParams.put("userCode", param.get("userCode"));// 参数
//			searchParams.put("roleCode", param.get("roleCode"));// 参数
//			searchParams.put("roleCodes", param.get("roleCodes"));// 参数
//			// 默认为分页的url
//			String url = null;
//			if (pageSize==null || pageSize == 0) {
//				url = jyptURL + "/api/sysRoleUser/search/v1";
//			} else{
//				url = jyptURL + "/api/sysRoleUser/searchByPage/v1";
//				PageParameter pageInfo = new PageParameter();
//				pageInfo.setPageSize(pageSize);
//				pageInfo.setCurrentPage(currentPage);
//				params.setPageParameter(pageInfo);
//			}
//			SysRoleUserDTO dtoParam = new SysRoleUserDTO();// 可能将param中的参数扩展到 dtoParam中
//			dtoParam.setTargetType("user");
//			BeanUtils.populate(dtoParam, param);//
//			dtoParam.setValidateState("1");//默认设置查询有效的
//			searchParams.put("dto", dtoParam);// 参数
//			params.setSearchParams(searchParams);
//
//			ResponseMsg<QueryRespBean<SysRoleUserDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params,
//					new TypeReference<ResponseMsg<QueryRespBean<SysRoleUserDTO>>>() {
//					});
//			List<SysRoleUserDTO> list = responseMsg.getResponseBody().getResult();
//			for (SysRoleUserDTO sysRoleUserDTO : list) {
//				Map<String, Object> dtoMap = new HashMap<String, Object>();
//				UserInfo userInfoDetail = orgApi.getUserInfoDetail(sysRoleUserDTO.getTargetId().toString());
//				dtoMap = BeanUtil.transBean2Map(userInfoDetail);
//				result.add(dtoMap);
//				dtoMap.put("totalCount", responseMsg.getResponseBody().getPageParameter().getTotalCount());
//				dtoMap.put("totalPage", responseMsg.getResponseBody().getPageParameter().getTotalPage());
//				dtoMap.put("totalRows", responseMsg.getResponseBody()
//						.getPageParameter().getTotalPage());
//			}
//		} catch (Exception e) {
//			logger.error("执行 接口查询异常：", e);
//		}
//		return result;
//	}
	
	@Override
	public List<Map<String, Object>> queryUserList(Integer pageSize, Integer currentPage, Map<String, Object> param) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			QueryReqBean params = new QueryReqBean();
			
			searchParams.put("userName", param.get("userName"));// 参数
			searchParams.put("userCode", param.get("userCode"));// 参数
			searchParams.put("roleCode", param.get("roleCode"));// 参数
			searchParams.put("roleCodes", param.get("roleCodes"));// 参数
			
			SysRoleUserDTO dtoParam = new SysRoleUserDTO();// 可能将param中的参数扩展到 dtoParam中
			dtoParam.setTargetType("user");
			BeanUtils.populate(dtoParam, param);//
			dtoParam.setValidateState("1");//默认设置查询有效的
			searchParams.put("dto", dtoParam);// 参数
			params.setSearchParams(searchParams);
			
			List<SysRoleUserDTO> list = new ArrayList<SysRoleUserDTO>();
			//不分页
			if(pageSize==null || pageSize == 0){
				list = sysRoleUserService.searchSysRoleUser(params.getSearchParams());
			}
			//分页
			else{
				PageParameter pageInfo = new PageParameter();
				pageInfo.setPageSize(pageSize);
				pageInfo.setCurrentPage(currentPage);
				params.setPageParameter(pageInfo);
				
				list = sysRoleUserService.searchSysRoleUserByPaging(params.getSearchParams());
			}
			
			for (SysRoleUserDTO sysRoleUserDTO : list) {
				Map<String, Object> dtoMap = new HashMap<String, Object>();
				UserInfo userInfoDetail = orgApi.getUserInfoDetail(sysRoleUserDTO.getTargetId().toString());
				dtoMap = BeanUtil.transBean2Map(userInfoDetail);
				result.add(dtoMap);
				dtoMap.put("totalCount", params.getPageParameter().getTotalCount());
				dtoMap.put("totalPage", params.getPageParameter().getTotalPage());
				dtoMap.put("totalRows", params.getPageParameter().getTotalPage());
			}
		} 
		catch (Exception e) {
			logger.error("执行 接口查询异常：", e);
		}
		
		return result;
	}


//	@Override
//	public List<Map<String, Object>> getRoleByUserId(String userId) {
//		
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//		try {
//			Map<String, Object> searchParams = new HashMap<String, Object>();
//			QueryReqBean params = new QueryReqBean();
//			
//			searchParams.put("userId", userId);// 参数
//			// 默认为分页的url
//			String url = jyptURL + "/api/sysRole/getRoleByUserId/v1";
//			params.setSearchParams(searchParams);
//
//			ResponseMsg<QueryRespBean<SysRoleDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params,
//					new TypeReference<ResponseMsg<QueryRespBean<SysRoleDTO>>>() {
//					});
//			List<SysRoleDTO> list = responseMsg.getResponseBody().getResult();
//			for (SysRoleDTO SysRoleDTO : list) {
//				Map<String, Object> dtoMap = new HashMap<String, Object>();
//				dtoMap = BeanUtil.transBean2Map(SysRoleDTO);
//				result.add(dtoMap);
//			}
//		} catch (Exception e) {
//			logger.error("执行 接口查询异常：", e);
//		}
//		return result;
//	}
	
	@Override
	public List<Map<String, Object>> getRoleByUserId(String userId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try{
			List<SysRoleDTO> list = sysRoleService.getRoleByUserId(Long.parseLong(userId));
			
			for (SysRoleDTO SysRoleDTO : list) {
				Map<String, Object> dtoMap = new HashMap<String, Object>();
				dtoMap = BeanUtil.transBean2Map(SysRoleDTO);
				result.add(dtoMap);
			}
		} 
		catch (Exception e) {
			logger.error("执行 接口查询异常：", e);
		}
		
		return result;
	}


//	@Override
//	public List<Map<String, Object>> getRoleGroupByUserId(String userId) {
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//		try {
//			Map<String, Object> searchParams = new HashMap<String, Object>();
//			QueryReqBean params = new QueryReqBean();
//			
//			searchParams.put("userID", userId);// 参数
//			searchParams.put("dto", new SysRoleGroupDTO());
//			// 默认为分页的url
//			String url = jyptURL + "/api/platform/sysRoleGroup/search/v1";
//			params.setSearchParams(searchParams);
//
//			ResponseMsg<QueryRespBean<SysRoleGroupDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params,
//					new TypeReference<ResponseMsg<QueryRespBean<SysRoleGroupDTO>>>() {
//					});
//			List<SysRoleGroupDTO> list = responseMsg.getResponseBody().getResult();
//			for (SysRoleGroupDTO SysRoleGroupDTO : list) {
//				Map<String, Object> dtoMap = new HashMap<String, Object>();
//				dtoMap = BeanUtil.transBean2Map(SysRoleGroupDTO);
//				result.add(dtoMap);
//			}
//		} catch (Exception e) {
//			logger.error("执行 接口查询异常：", e);
//		}
//		return result;
//	}
	
	@Override
	public List<Map<String, Object>> getRoleGroupByUserId(String userId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("userID", userId);// 参数
			searchParams.put("dto", new SysRoleGroupDTO());
			QueryReqBean params = new QueryReqBean();
			params.setSearchParams(searchParams);

			List<SysRoleGroupDTO> list = sysRoleGroupService.searchSysRoleGroup(params.getSearchParams());
			
			for (SysRoleGroupDTO SysRoleGroupDTO : list) {
				Map<String, Object> dtoMap = new HashMap<String, Object>();
				dtoMap = BeanUtil.transBean2Map(SysRoleGroupDTO);
				result.add(dtoMap);
			}
		} 
		catch (Exception e) {
			logger.error("执行 接口查询异常：", e);
		}
		
		return result;
	}

}
