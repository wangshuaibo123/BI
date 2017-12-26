package com.fintech.modules.platform.dataprv.sysprvrole.controller;

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

import com.fintech.modules.platform.dataprv.sysprvrole.dto.SysPrvRoleDTO;
import com.fintech.modules.platform.dataprv.sysprvrole.service.SysPrvRoleService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysPrvRoleController
 * @description: 定义 数据权限角色定义 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysPrvRole")
public class SysPrvRoleController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SysPrvRoleController.class);

	@Autowired
	@Qualifier("com.fintech.modules.platform.dataprv.sysprvrole.service.SysPrvRoleService")
	private SysPrvRoleService service;

	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(@PathVariable("operate") String operate)
			throws AbaboonException {
		ModelAndView model = new ModelAndView();
		// String operate = this.getParameterString("operateData");

		if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/dataprv/sysprvrole/querySysPrvRole");
		} else if ("toAdd".equals(operate)) { // 跳转至 新增页面
			model.setViewName("platform/dataprv/sysprvrole/addSysPrvRole");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/dataprv/sysprvrole/updateSysPrvRole");
		} else if ("toView".equals(operate)) {// 跳转至 查看页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/dataprv/sysprvrole/viewSysPrvRole");
		} else if ("toManager".equals(operate)) {
			model.setViewName("platform/dataprv/sysprvrole/sysPrvRoleManager");
		}

		return model;
	}

	/**
	 * @author
	 * @description:查询分页列表
	 * @date 2014-10-18 16:07:13
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListSysPrvRole")
	@ResponseBody
	public DataMsg queryListSysPrvRole(HttpServletRequest request,
			SysPrvRoleDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		List<SysPrvRoleDTO> list = service.searchSysPrvRoleByPaging(params
				.getSearchParams());

		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}

	/**
	 * @author
	 * @description:新增
	 * @date 2014-10-18 16:07:13
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertSysPrvRole")
	@ResponseBody
	public DataMsg insertSysPrvRole(HttpServletRequest request,
			SysPrvRoleDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysPrvRoleDTO) super.initDto(dto);

			service.insertSysPrvRole(dto);

			dataMsg = super.initDataMsg(dataMsg);
			dataMsg.setMsg("新增成功");
			dataMsg.setData(this.makeJSONData(dto.getId()));
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法insertSysResource异常：", e);
		}
		return dataMsg;
	}

	/**
	 * @author
	 * @description:编辑
	 * @date 2014-10-18 16:07:13
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateSysPrvRole")
	@ResponseBody
	public DataMsg updateSysPrvRole(HttpServletRequest request,
			SysPrvRoleDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysPrvRoleDTO) super.initDto(dto);

			service.updateSysPrvRole(dto);

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
	 * @date 2014-10-18 16:07:13
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteSysPrvRole")
	@ResponseBody
	public DataMsg deleteSysPrvRole(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {

		BaseDTO dto = super.initDto(null);
		String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			service.deleteSysPrvRoleByID(dto, ids);
			dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}

		return dataMsg;
	}

	/**
	 * @author
	 * @description:删除
	 * @date 2014-10-18 16:07:13
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/queryRoleByCode")
	@ResponseBody
	public DataMsg queryRoleByCode(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {
		String code = (String) this.getParameter("code");
		dataMsg = super.initDataMsg(dataMsg);
		dataMsg.setData(service.queryRoleByCode(code));
		dataMsg.setMsg("删除成功");
		return dataMsg;
	}

	/**
	 * @author
	 * @description:通过主键查询 其明细信息
	 * @date 2014-10-18 16:07:13
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			SysPrvRoleDTO dto = service.querySysPrvRoleByPrimaryKey(id);
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}
}
