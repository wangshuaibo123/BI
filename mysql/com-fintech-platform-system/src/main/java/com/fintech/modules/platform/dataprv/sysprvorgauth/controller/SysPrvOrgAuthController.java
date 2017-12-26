package com.fintech.modules.platform.dataprv.sysprvorgauth.controller;

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

import com.fintech.modules.platform.dataprv.sysprvorgauth.dto.SysPrvOrgAuthDTO;
import com.fintech.modules.platform.dataprv.sysprvorgauth.service.SysPrvOrgAuthService;
import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysPrvOrgAuthController
 * @description: 定义 组织授权表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysPrvOrgAuth")
public class SysPrvOrgAuthController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SysPrvOrgAuthController.class);

	@Autowired
	@Qualifier("com.fintech.modules.platform.dataprv.sysprvorgauth.service.SysPrvOrgAuthService")
	private SysPrvOrgAuthService service;
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

		if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
			String userId = this.getParameterString("userId");
			if (userId != null && userId.length() > 0)
				model.addObject("userId", userId);
			model.setViewName("platform/dataprv/sysprvorgauth/querySysPrvOrgAuth");
		} else if ("toAdd".equals(operate)) { // 跳转至 新增页面
			model.setViewName("platform/dataprv/sysprvorgauth/addSysPrvOrgAuth");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/dataprv/sysprvorgauth/updateSysPrvOrgAuth");
		} else if ("toView".equals(operate)) {// 跳转至 查看页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/dataprv/sysprvorgauth/viewSysPrvOrgAuth");
		} else if ("toManager".equals(operate)) {
			model.setViewName("platform/dataprv/sysprvorgauth/sysPrvOrgAuthManager");
		}

		return model;
	}

	/**
	 * @author
	 * @description:查询分页列表
	 * @date 2014-10-18 16:07:01
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListSysPrvOrgAuth")
	@ResponseBody
	public DataMsg queryListSysPrvOrgAuth(HttpServletRequest request,
			SysPrvOrgAuthDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {

		String userId = this.getParameterString("userId1");
		if (userId != null && userId.length() > 0)
			dto.setUserId(Long.parseLong(userId));
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);

		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		List<SysPrvOrgAuthDTO> list = service
				.searchSysPrvOrgAuthByPaging(params.getSearchParams());

		Map<String,String> userMap = new HashMap<String,String>();
		userMap.put("userId", "createUserNameExt");
		
		Map<String,String> orgMap = new HashMap<String,String>();
		orgMap.put("orgId", "createOrgNameExt");
		list = orgApi.mappingOrgUser(list, userMap, orgMap);
		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}

	/**
	 * @author
	 * @description:新增
	 * @date 2014-10-18 16:07:01
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertSysPrvOrgAuth")
	public ModelAndView insertSysPrvOrgAuth(String userId, String orgIds) {
		ModelAndView model = new ModelAndView();
		try {
			service.insertSysPrvOrgAuth(userId, orgIds);
			model.addObject("userId", userId);
			model.setViewName("platform/dataprv/sysprvorgauth/querySysPrvOrgAuth");
		} catch (Exception e) {
			logger.error("执行方法insertSysResource异常：", e);
		}
		return model;
	}

	/**
	 * @author
	 * @description:编辑
	 * @date 2014-10-18 16:07:01
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateSysPrvOrgAuth")
	@ResponseBody
	public DataMsg updateSysPrvOrgAuth(HttpServletRequest request,
			SysPrvOrgAuthDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysPrvOrgAuthDTO) super.initDto(dto);

			service.updateSysPrvOrgAuth(dto);

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
	 * @author
	 * @description:删除
	 * @date 2014-10-18 16:07:01
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteSysPrvOrgAuth")
	@ResponseBody
	public DataMsg deleteSysPrvOrgAuth(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {

		BaseDTO dto = super.initDto(null);
		String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			service.deleteSysPrvOrgAuthByID(dto, ids);
			dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}

		return dataMsg;
	}

	/**
	 * @author
	 * @description:通过主键查询 其明细信息
	 * @date 2014-10-18 16:07:01
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			SysPrvOrgAuthDTO dto = service.querySysPrvOrgAuthByPrimaryKey(id);
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}

	/**
	 * @author
	 * @description:删除
	 * @date 2014-10-18 16:07:01
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/queryInfoByUserAndOrg")
	@ResponseBody
	public DataMsg queryInfoByUserAndOrg(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {

		String orgIds = this.getParameterString("orgIds");// 格式: 1,2,3
		String userId = this.getParameterString("userId");
		dataMsg = super.initDataMsg(dataMsg);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orgIds", orgIds);
		param.put("userId", userId);
		dataMsg.setData(service.queryInfoByUserAndOrg(param));
		return dataMsg;
	}
}
