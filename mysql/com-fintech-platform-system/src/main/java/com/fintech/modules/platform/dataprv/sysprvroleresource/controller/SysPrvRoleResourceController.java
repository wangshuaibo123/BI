package com.fintech.modules.platform.dataprv.sysprvroleresource.controller;

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

import com.fintech.modules.platform.dataprv.sysprvroleresource.dto.SysPrvRoleResourceDTO;
import com.fintech.modules.platform.dataprv.sysprvroleresource.service.SysPrvRoleResourceService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysPrvRoleResourceController
 * @description: 定义 数据角色资源表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysPrvRoleResource")
public class SysPrvRoleResourceController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SysPrvRoleResourceController.class);

	@Autowired
	@Qualifier("com.fintech.modules.platform.dataprv.sysprvroleresource.service.SysPrvRoleResourceService")
	private SysPrvRoleResourceService service; 
	

	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(@PathVariable("operate") String operate)
			throws AbaboonException {
		ModelAndView model = new ModelAndView();
		// String operate = this.getParameterString("operateData");

		if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
			String roleId = this.getParameterString("roleId");
			if (roleId != null && roleId.length() > 0)
				model.addObject("roleId", roleId);
			String roleName = this.getParameterString("roleName");
			if (roleName != null && roleName.length() > 0)
				model.addObject("roleName", roleName);
			model.setViewName("platform/dataprv/sysprvroleresource/querySysPrvRoleResource");
		} else if ("toAdd".equals(operate)) { // 跳转至 新增页面
			String roleId = this.getParameterString("roleId");
			if (roleId != null && roleId.length() > 0)
				model.addObject("roleId", roleId);
			String roleName = this.getParameterString("roleName");
			if (roleName != null && roleName.length() > 0)
				model.addObject("roleName", roleName);
			model.setViewName("platform/dataprv/sysprvroleresource/addSysPrvRoleResource");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/dataprv/sysprvroleresource/updateSysPrvRoleResource");
		} else if ("toView".equals(operate)) {// 跳转至 查看页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/dataprv/sysprvroleresource/viewSysPrvRoleResource");
		}

		return model;
	}

	/**
	 * @author
	 * @description:查询分页列表
	 * @date 2014-10-18 16:07:31
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListSysPrvRoleResource")
	@ResponseBody
	public DataMsg queryListSysPrvRoleResource(HttpServletRequest request,
			SysPrvRoleResourceDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {
		String roleId = this.getParameterString("roleId");
		if (roleId != null && roleId.length() > 0)
			dto.setRoleId(Long.parseLong(roleId));
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		List<SysPrvRoleResourceDTO> list = service.searchSysPrvRoleResourceByPaging(params.getSearchParams());

		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}

	/**
	 * @author
	 * @description:新增
	 * @date 2014-10-18 16:07:31
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertSysPrvRoleResource")
	@ResponseBody
	public ModelAndView insertSysPrvRoleResource(String roleId,
			String roleName, String resourceIds, String resourceType) {
		ModelAndView model = new ModelAndView();
		try {
			service.insertSysPrvRoleResource(roleId, resourceIds, resourceType);
			model.addObject("roleId", roleId);
			model.addObject("roleName", roleName);
			model.setViewName("platform/dataprv/sysprvroleresource/querySysPrvRoleResource");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * @author
	 * @description:编辑
	 * @date 2014-10-18 16:07:31
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateSysPrvRoleResource")
	@ResponseBody
	public DataMsg updateSysPrvRoleResource(HttpServletRequest request,
			SysPrvRoleResourceDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysPrvRoleResourceDTO) super.initDto(dto);

			service.updateSysPrvRoleResource(dto);

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
	 * @date 2014-10-18 16:07:31
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteSysPrvRoleResource")
	@ResponseBody
	public DataMsg deleteSysPrvRoleResource(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {

		BaseDTO dto = super.initDto(null);
		String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			service.deleteSysPrvRoleResourceByID(dto, ids);
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
	 * @date 2014-10-18 16:07:31
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			SysPrvRoleResourceDTO dto = service
					.querySysPrvRoleResourceByPrimaryKey(id);
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}

	// 唯一性验证
	@RequestMapping(value = "/queryRoleResourceByResource")
	@ResponseBody
	public DataMsg queryRoleResourceByResource(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {
		String resourceId = (String) this.getParameter("resourceIds");
		String resourceType = (String) this.getParameter("resourceType");
		String roleId = (String) this.getParameter("roleId");
		dataMsg = super.initDataMsg(dataMsg);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resourceIds", resourceId);
		map.put("resourceType", resourceType);
		map.put("roleId", roleId);
		dataMsg.setData(service.queryRoleResourceByResource(map));
		return dataMsg;
	}
}
