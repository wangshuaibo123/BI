package com.fintech.modules.platform.sysorg.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.modules.platform.sysorg.dto.SysOrgDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.modules.platform.sysorg.service.SysOrgService;
import com.fintech.modules.platform.sysorg.service.SysOrgUserService;
import com.fintech.modules.platform.sysorg.service.SysUserService;
import com.fintech.platform.api.org.OrgInfo;
import com.fintech.platform.api.org.OrgPositionInfo;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.message.QueryReqBean;

@SuppressWarnings("all")
public class OrgAPImplHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(OrgAPImplHandler.class);

	//@Autowired
    public SysOrgService sysOrgService;
	
	//@Autowired
    public SysUserService sysUserService;
	
	//@Autowired
	public SysOrgUserService sysOrgUserService;
	
	public OrgAPImplHandler(){
		ApplicationContext context = null;
		try{
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
			ServletContext servletContext = webApplicationContext.getServletContext();
			context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		}catch(Exception e){
			context = SysConfigAPI.getApplicationContext();
		}
		sysUserService = (SysUserService) context.getBean("com.fintech.modules.platform.sysorg.service.SysUserService");
		sysOrgService = (SysOrgService) context.getBean("com.fintech.modules.platform.sysorg.service.SysOrgService");
		sysOrgUserService = (SysOrgUserService) context.getBean("com.fintech.modules.platform.sysorg.service.SysOrgUserService");
	}
	
	public UserInfo getUserInfoByLoginName(String loginName){
		UserInfo user = new UserInfo();
		try {
			List<SysUserDTO> list = sysUserService.getUserInfoByLoginName(loginName);
			if (list != null && list.size() > 0) {
				SysUserDTO resultDto = list.get(0);
				user.setUserId(resultDto.getId());
				user.setLoginName(resultDto.getLoginName());
				user.setUserName(resultDto.getUserName());
				user.setPassword(resultDto.getPassword());
				user.setUserNo(resultDto.getUserNo());
				user.setSalt(resultDto.getSalt());
				List<SysOrgUserDTO> sysOrgUserDTOs = resultDto.getSysOrgUserDTOs();
				SysOrgUserDTO sysOrgUserDTOMain = new SysOrgUserDTO();// 主机构岗位
				if (sysOrgUserDTOs != null && sysOrgUserDTOs.size() > 0) {
					for (SysOrgUserDTO sysOrgUserDTO : sysOrgUserDTOs) {
						if ("1".equals(sysOrgUserDTO.getIsMainOrg())) {// 是主机构
							sysOrgUserDTOMain = sysOrgUserDTO;
						}
					}
				}
				user.setOrgId(sysOrgUserDTOMain.getOrgId());
				user.setOrgName(sysOrgUserDTOMain.getOrgName());
				user.setOrgParentId(sysOrgUserDTOMain.getParentId());
			}
		} catch (Exception e) {
			logger.error("handler执行方法getUserInfoByLoginName异常：", e);
		}
		return user;
	}
	
	public OrgInfo getOrgInfo(String orgId) {
		OrgInfo orgInfo = new OrgInfo();
		try {
			SysOrgDTO entity = sysOrgService.querySysOrgByPrimaryKey(orgId);
			BeanUtils.copyProperties(orgInfo, entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("handler执行方法getOrgInfo异常：", e);
		}
		return orgInfo;
	}
	
	public UserInfo getUserInfoDetail(String userId) {
		UserInfo userInfo = null;
		try {
			userInfo = new UserInfo();
			SysUserDTO entity = sysUserService.querySysUserFullByPrimaryKey(userId);
			if (entity != null) {
				userInfo = new UserInfo();
				List<OrgPositionInfo> orgPositionInfos = new ArrayList<OrgPositionInfo>();
				BeanUtils.copyProperties(userInfo, entity);
				userInfo.setUserId(Long.parseLong(userId));//id
				for (SysOrgUserDTO sysOrgUserDTO : entity.getSysOrgUserDTOs()) {
					OrgPositionInfo orgPositionInfo = new OrgPositionInfo();
					BeanUtils.copyProperties(orgPositionInfo, sysOrgUserDTO);
					if("1".equals (sysOrgUserDTO.getIsMainOrg())){//是主机构
						userInfo.setOrgId(sysOrgUserDTO.getOrgId());
						userInfo.setOrgName(sysOrgUserDTO.getOrgName());
						userInfo.setOrgParentId(sysOrgUserDTO.getParentId());
					}
					orgPositionInfos.add(orgPositionInfo);
				}
				userInfo.setOrgPositionInfos(orgPositionInfos);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("handler执行方法getUserInfoDetail异常：", e);
		}
		return userInfo;
	}
	
	public List<String> getUsersByOrgId(String... orgIds) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("orgIds", org.apache.commons.lang3.StringUtils.join(orgIds, ","));
		searchParams.put("dto", new SysUserDTO());
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		List<String> result = null;
		String oIds = (String)params.getSearchParams().get("orgIds");
		String orgId = (String)params.getSearchParams().get("orgId");
		String ids = oIds!=null?oIds:orgId;
		params.getSearchParams().put("orgId", ids);
  
		List<SysUserDTO> list;
		try {
			list = sysUserService.searchSysUser(params.getSearchParams());
			if (list != null && list.size() > 0) {
				result = new ArrayList<String>();
				for (SysUserDTO sysUserDTO : list) {
					result.add(sysUserDTO.getId().toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("handler执行方法getUsersByOrgId异常：", e);
		}
		return result;
	}
	
	public List<SysUserDTO> queryUserByRolePage(QueryReqBean params){
		List<SysUserDTO> list = new ArrayList<SysUserDTO>();
		try{
			Map<String, Object> searchParams = new HashMap<String, Object>();
			list = sysUserService.searchUserRoleByPaging(params.getSearchParams());
		}catch (Exception e) {
			logger.error("handler执行方法queryUserByRolePage异常：", e);
		}
		return list;
	}
	
	public List<SysUserDTO> queryUserListPage(QueryReqBean params){
		List<SysUserDTO> list = new ArrayList<SysUserDTO>();
		try{
			list = sysUserService.searchSysUserByPaging(params.getSearchParams());
		}catch (Exception e) {
			logger.error("handler执行方法queryUserByRolePage异常：", e);
		}
		return list;
	}
	
	public List<SysUserDTO> queryUserList(QueryReqBean params){
		List<SysUserDTO> list = new ArrayList<SysUserDTO>();
		try{
			list = sysUserService.searchSysUser(params.getSearchParams());
		}catch (Exception e) {
			logger.error("handler执行方法queryUserByRolePage异常：", e);
		}
		return list;
	}
	
	/**
	 * 
	 * 查找用户所在的营业部
	 * 
	 */
	public String queryDepartmentByUserId(String userId)throws Exception
	{
		String orgId = null ;
		//查找用户的主任职机构
		String mainOrgId=queryMainOrgByUserId(userId);
		SysOrgDTO sysOrgDTO=sysOrgService.querySysOrgById(mainOrgId);
		String ids = null;
		String parentIds=sysOrgDTO.getParentIds();
		String newParentIds=parentIds.substring(1);
		ids=newParentIds.substring(0,newParentIds.length()-1).replaceAll("/",",");
		List<SysOrgDTO> list=sysOrgService.searchSysOrgByIds(ids);	
		boolean falg = false;
		int i = 0;
		int j = 0;
		for(SysOrgDTO dto : list)
		{	
			i++;
			if(dto.getOrgLevel().equals("4"))
			{
				falg = true;
				orgId=dto.getId().toString();
				break;
			}
			if(dto.getOrgLevel().equals("5"))
			{
				j=i;
			}
		}
		//首先判断是否有机构层级为4的  falg=false代表没有 直接取层级为5的
		if(!falg)
		{
			if(j==0) //代表既没有层级为4的 也没有层级为5的  直接取主任职机构
			{
				orgId= mainOrgId;
			}else{
				SysOrgDTO dto=(SysOrgDTO)list.get(j--);
				orgId = dto.getId().toString();
			}
		}
		return orgId;
	}
	
	/**
	 * 
	 * 通过用户ID查询出该用户的主任职机构
	 * 
	 */
	
	private String queryMainOrgByUserId(String userId)throws Exception 
	{
		String orgId = null ;
		Map<String,Object> searchParams = new HashMap<String,Object>();
		SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
		sysOrgUserDTO.setIsMainOrg("1");
		sysOrgUserDTO.setUserId(Long.parseLong(userId));
		searchParams.put("dto", sysOrgUserDTO);
		List<SysOrgUserDTO> list=sysOrgUserService.searchSysOrgUser(searchParams);
		if(list!=null && list.size()>0)
		{
			SysOrgUserDTO dto= (SysOrgUserDTO)list.get(0);
			orgId = dto.getOrgId().toString();
		}
		return orgId;
	}
}
