package com.jy.modules.platform.sysauth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jy.modules.platform.sysauth.dto.SysRoleUserDTO;
import com.jy.modules.platform.sysauth.service.SysRoleUserService;
import com.jy.modules.platform.sysorg.dto.SysUserDTO;
import com.jy.platform.api.org.OrgAPI;
import com.jy.platform.api.org.OrgInfo;
import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restclient.http.RestClient;
import com.jy.platform.restclient.http.RestService;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysRoleUserController
 * @description: 定义 角色用户表 控制层
 * @author: chen_gang
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysRoleUser")
public class SysRoleUserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SysRoleUserController.class);
	private String jyptAppId = "jypt"; // rest服务appId
	private String jyptURL = RestService.getServiceUrl(jyptAppId);// rest服务地址
	@Autowired
	@Qualifier("com.jy.modules.platform.sysauth.service.SysRoleUserService")
	private SysRoleUserService service;

	@Autowired
	private OrgAPI orgService;
	
	@Autowired
	private SessionAPI sessionAPI;

	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		// String operate = this.getParameterString("operateData");

		if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			if (id != null && id.length() > 0)
				model.addObject("roleId", id);
			model.setViewName("platform/sysauth/sysRoleUser/querySysRoleUser");
		} else if ("toAdd".equals(operate)) { // 跳转至 新增页面
			model.setViewName("platform/sysauth/sysRoleUser/addSysRoleUser");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/sysauth/sysRoleUser/updateSysRoleUser");
		} else if ("toView".equals(operate)) {// 跳转至 查看页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/sysauth/sysRoleUser/viewSysRoleUser");
		} else if ("selectUser".equals(operate)) {

			String id = this.getParameterString("roleId");
			model.addObject("roleId", id);
			model.setViewName("platform/sysauth/sysRoleUser/selectSysUser");
		} else if ("selectOrg".equals(operate)) {
			model.setViewName("platform/sysauth/sysRoleUser/selectOrg");
		}

		return model;
	}

	/**
	 * @author chen_gang
	 * @description:查询分页列表
	 * @date 2014-10-15 10:25:12
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListSysRoleUser")
	@ResponseBody
	public DataMsg queryListSysRoleUser(HttpServletRequest request, SysRoleUserDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {
		Long roleId = dto.getRoleId();
		if (roleId != 0) {
			UserInfo user = sessionAPI.getCurrentUserInfo();
			//获取当前用户管理的机构
			Map<String, Object> userParam=new HashMap<String, Object>();
			userParam.put("orgId", user.getOrgId());
			userParam.put("userId", user.getUserId());
			SysRoleUserDTO sDto=service.findSysRoleOrgByCurrentUser(userParam);
			//获取当前用户管理的机构
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("dto", dto);
			searchParams.put("userName", dto.getTargetName());
			if(sDto!=null)
				searchParams.put("orgId", sDto.getTargetId());
			QueryReqBean params = new QueryReqBean();
			params.setSearchParams(searchParams);
			PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
			params.setPageParameter(pageInfo);

			List<SysRoleUserDTO> list = service.searchSysRoleUserByPaging(params.getSearchParams());
			for (SysRoleUserDTO sysRoleUserDTO : list) {
				String targetName = null;
				String roleIds = null;
				if ("org".equals(sysRoleUserDTO.getTargetType())) {
					roleIds = String.valueOf(sysRoleUserDTO.getTargetId());
					OrgInfo orgInfo = orgService.getOrgInfo(roleIds);
					targetName = orgInfo.getOrgName();
					sysRoleUserDTO.setTargetName(targetName);
				} else if ("user".equals(sysRoleUserDTO.getTargetType())) {
					roleIds = String.valueOf(sysRoleUserDTO.getTargetId());
					UserInfo userInfoDetail = orgService.getUserInfoDetail(roleIds);
					targetName = userInfoDetail.getUserName();
					sysRoleUserDTO.setTargetName(targetName);
				}
			}
			dataMsg.setData(list);
			dataMsg.setTotalRows(pageInfo.getTotalCount());
		} else {
			dataMsg.setData(new ArrayList<SysRoleUserDTO>());
			dataMsg.setTotalRows(0);
		}
		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:新增
	 * @date 2014-10-15 10:25:12
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertSysRoleUser")
	@ResponseBody
	public DataMsg insertSysRoleUser(HttpServletRequest request, String roleId, String userIds, String targetType,
			@ModelAttribute DataMsg dataMsg) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		boolean falg = true;
		if (!userIds.equals("")) {
			try {
				String[] roleIds = roleId.split(",");
				String[] userIdArray = userIds.split(",");
				SysRoleUserDTO dto = null;
				for (String tarGetId : userIdArray) {
					dto=new SysRoleUserDTO();
					dto.setRoleId(Long.valueOf(roleIds[0]));
					dto.setTargetId(Long.valueOf(tarGetId));
					dto.setTargetType(targetType);
					dto.setValidateState("1");
					searchParams.put("dto", dto);
					List<SysRoleUserDTO> searchSysRoleUser = service.searchSysRoleUser(searchParams);
					if (searchSysRoleUser.size() > 0) {
						falg= false;
						dataMsg.setMsg("请不要重复添加用户");
						break;
					}
					service.insertSysRoleUser(dto);
					dataMsg = super.initDataMsg(dataMsg);
				}
				if(falg)
				{	
					dataMsg.setMsg("新增成功");
					dataMsg.setData(this.makeJSONData(dto.getId()));
				}
			} catch (Exception e) {
				dataMsg.failed(e.getMessage());
				logger.error("执行方法insertSysResource异常：", e);
			}
		}
		else{
			dataMsg.setMsg("请选择用户");
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
		}
		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:编辑
	 * @date 2014-10-15 10:25:12
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateSysRoleUser")
	@ResponseBody
	public DataMsg updateSysRoleUser(HttpServletRequest request, SysRoleUserDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysRoleUserDTO) super.initDto(dto);

			service.updateSysRoleUser(dto);

			dataMsg = super.initDataMsg(dataMsg);
			dataMsg.setMsg("修改成功");
			dataMsg.setData(this.makeJSONData(dto.getId()));
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法updateSysResource异常：", e);
		}
		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:删除
	 * @date 2014-10-15 10:25:12
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteSysRoleUser")
	@ResponseBody
	public DataMsg deleteSysRoleUser(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {

		BaseDTO dto = super.initDto(null);
		String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			service.deleteSysRoleUserByPrimaryKey(dto, ids);
			dataMsg.setMsg("删除成功");
			dataMsg.setStatus(DataMsg.STATUS_OK);
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
			logger.error("执行方法deleteSysResource异常：", e);

		}

		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:通过主键查询 其明细信息
	 * @date 2014-10-15 10:25:12
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			SysRoleUserDTO dto = service.querySysRoleUserByPrimaryKey(id);
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}

	@RequestMapping(value = "/queryListSysUser")
	@ResponseBody
	public DataMsg searchUserList(HttpServletRequest request, SysUserDTO dto, @ModelAttribute DataMsg dataMsg) {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        dto.setPositionCN("");
    	searchParams.put("dto", dto);
    	UserInfo user = sessionAPI.getCurrentUserInfo();
    	
    	//获取当前用户管理的机构
		Map<String, Object> userParam=new HashMap<String, Object>();
		userParam.put("orgId", user.getOrgId());
		userParam.put("userId", user.getUserId());
		SysRoleUserDTO sDto=service.findSysRoleOrgByCurrentUser(userParam);
		//获取当前用户管理的机构
		
		
		if(sDto!=null)
			searchParams.put("orgId", sDto.getTargetId()+"");
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
		try {
			String url= jyptURL+"/api/platform/SysUserRest/searchByPage/v1";
			ResponseMsg<QueryRespBean<SysUserDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params, new TypeReference<ResponseMsg<QueryRespBean<SysUserDTO>>>(){});
			List<SysUserDTO> list  = responseMsg.getResponseBody().getResult();
			dataMsg.setData(list);
			dataMsg.setTotalRows(responseMsg.getResponseBody().getPageParameter().getTotalCount());
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
        	logger.error("执行方法queryListSysUser异常：", e);
		}
		return dataMsg;
	}

	
	/**
	 * @author gyl
	 * @description:清理当前登陆用户管理机构下的离职人员的操作权限
	 * @date 2015-07-10 10:25:12
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/cleanSysRoleUser")
	@ResponseBody
	public DataMsg cleanSysRoleUser(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {
		UserInfo user = sessionAPI.getCurrentUserInfo();
		//获取当前用户管理的机构
		Map<String, Object> userParam=new HashMap<String, Object>();
		userParam.put("orgId", user.getOrgId());
		userParam.put("userId", user.getUserId());
		SysRoleUserDTO sDto=service.findSysRoleOrgByCurrentUser(userParam);
		//获取当前用户管理的机构
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("orgId", user.getOrgId());
		if(sDto!=null)
			searchParams.put("orgId", sDto.getTargetId());
		dataMsg = super.initDataMsg(dataMsg);
		try {
			service.modifySysRoleUserByOrgId(searchParams);
			dataMsg.setMsg("离职人员操作权限清理成功");
			dataMsg.setStatus(DataMsg.STATUS_OK);
		} catch (Exception e) {
			dataMsg.failed("离职人员操作权限清理失败："+e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
			logger.error("执行方法deleteSysResource异常：", e);

		}

		return dataMsg;
	}
	
}
