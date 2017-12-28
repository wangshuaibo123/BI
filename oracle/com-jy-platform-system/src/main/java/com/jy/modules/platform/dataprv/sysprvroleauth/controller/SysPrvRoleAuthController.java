package com.jy.modules.platform.dataprv.sysprvroleauth.controller;

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

import com.jy.modules.platform.dataprv.sysprvrole.dto.SysPrvRoleDTO;
import com.jy.modules.platform.dataprv.sysprvrole.service.SysPrvRoleService;
import com.jy.modules.platform.dataprv.sysprvroleauth.dto.SysPrvRoleAuthDTO;
import com.jy.modules.platform.dataprv.sysprvroleauth.service.SysPrvRoleAuthService;
import com.jy.platform.api.org.OrgAPI;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysPrvRoleAuthController
 * @description: 定义 数据权限角色授权表 控制层
 * @author: wangxz
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysPrvRoleAuth")
public class SysPrvRoleAuthController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SysPrvRoleAuthController.class);

	@Autowired
	@Qualifier("com.jy.modules.platform.dataprv.sysprvroleauth.service.SysPrvRoleAuthService")
	private SysPrvRoleAuthService service;
	@Autowired
	@Qualifier("com.jy.modules.platform.dataprv.sysprvrole.service.SysPrvRoleService")
	private SysPrvRoleService rService;
	
	@Autowired
	private OrgAPI orgApi;

	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(@PathVariable("operate") String operate)
			throws AbaboonException {
		ModelAndView model = new ModelAndView();
		// String operate = this.getParameterString("operateData");
		String roleId = this.getParameterString("roleId");
		String userId = this.getParameterString("userId");
		String userId1 = this.getParameterString("userId1");
		String roleName = this.getParameterString("roleName");
		StringBuffer sb = new StringBuffer("[{'value':'0','text':'请选择'}");
		if (roleId != null && roleId.length() > 0)
			model.addObject("roleId", roleId);
		if (roleName != null && roleName.length() > 0)
			model.addObject("roleName",roleName);
		if (userId != null || userId1 != null) {
			model.addObject("userId1", userId1);
			model.addObject("userId", userId);
			try {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("dto", new SysPrvRoleDTO());
				if (userId1 != null)
					model.addObject("roles", rService.searchSysPrvRole(param));
				else{
					List<SysPrvRoleDTO> dtos = rService.searchSysPrvRole(param);
					for(SysPrvRoleDTO dto:dtos){
						sb.append(",{'value':'"+dto.getId()+"','text':'"+dto.getRoleName()+"'}");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sb.append("]");
		model.addObject("roleJson", sb.toString());
		if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/dataprv/sysprvroleauth/querySysPrvRoleAuth");
		} else if ("toAdd".equals(operate)) { // 跳转至 新增页面
			model.setViewName("platform/dataprv/sysprvroleauth/addSysPrvRoleAuth");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/dataprv/sysprvroleauth/updateSysPrvRoleAuth");
		}

		return model;
	}

	/**
	 * @author wangxz
	 * @description:查询分页列表
	 * @date 2014-10-18 16:07:22
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListSysPrvRoleAuth")
	@ResponseBody
	public DataMsg queryListSysPrvRoleAuth(HttpServletRequest request,
			SysPrvRoleAuthDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {
		String roleId = this.getParameterString("roleId");
		if (roleId != null && roleId.length() > 0)
			dto.setRoleId(Long.parseLong(roleId));
		String userId = this.getParameterString("userId1");
		if (userId != null && userId.length() > 0)
			dto.setUserId(Long.parseLong(userId));
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		List<SysPrvRoleAuthDTO> list = service
				.searchSysPrvRoleAuthByPaging(params.getSearchParams());
		Map<String,String> userMap = new HashMap<String,String>();
		userMap.put("userId", "createUserNameExt");
		
		list = orgApi.mappingOrgUser(list, userMap, null);
		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}

	/**
	 * @author wangxz
	 * @description:新增
	 * @date 2014-10-18 16:07:22
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertSysPrvRoleAuth")
	public ModelAndView insertSysPrvRoleAuth(String userIds, String roleIds,
			String type,String roleNames) {
		ModelAndView model = new ModelAndView();
		try {
			service.insertSysPrvRoleAuth(userIds, roleIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("roleName", roleNames);
		if ("0".equals(type))
			model.addObject("userId", userIds);
		if ("1".equals(type))
			model.addObject("roleId", roleIds);
		StringBuffer sb = new StringBuffer("[{'value':'0','text':'请选择'}");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dto", new SysPrvRoleDTO());
		List<SysPrvRoleDTO> dtos;
		try {
			dtos = rService.searchSysPrvRole(param);
			for(SysPrvRoleDTO dto:dtos){
				sb.append(",{'value':'"+dto.getId()+"','text':'"+dto.getRoleName()+"'}");
			}
		} catch (Exception e) {
			logger.error("执行方法insertSysPrvRoleAuth异常：", e);
		}
		sb.append("]");
		model.addObject("roleJson", sb.toString());
		model.setViewName("platform/dataprv/sysprvroleauth/querySysPrvRoleAuth");
		return model;
	}

	/**
	 * @author wangxz
	 * @description:编辑
	 * @date 2014-10-18 16:07:22
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateSysPrvRoleAuth")
	@ResponseBody
	public DataMsg updateSysPrvRoleAuth(HttpServletRequest request,
			SysPrvRoleAuthDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysPrvRoleAuthDTO) super.initDto(dto);

			service.updateSysPrvRoleAuth(dto);

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
	 * @author wangxz
	 * @description:删除
	 * @date 2014-10-18 16:07:22
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteSysPrvRoleAuth")
	@ResponseBody
	public DataMsg deleteSysPrvRoleAuth(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {

		BaseDTO dto = super.initDto(null);
		String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			service.deleteSysPrvRoleAuthByID(dto, ids);
			dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}

		return dataMsg;
	}

	/**
	 * @author wangxz
	 * @description:通过主键查询 其明细信息
	 * @date 2014-10-18 16:07:22
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			SysPrvRoleAuthDTO dto = service.querySysPrvRoleAuthByPrimaryKey(id);
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}

	// 唯一性验证
	@RequestMapping(value = "/queryRoleAuthByUser")
	@ResponseBody
	public DataMsg queryRoleAuthByUser(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {

		String userId = (String) this.getParameter("userId");// 格式: 1,2,3
		String roleId = (String) this.getParameter("roleId");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("roleIds", roleId);
		dataMsg.setData(service.queryRoleAuthByUser(map));
		return dataMsg;
	}
}
