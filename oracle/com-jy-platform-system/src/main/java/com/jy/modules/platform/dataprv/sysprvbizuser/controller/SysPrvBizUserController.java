package com.jy.modules.platform.dataprv.sysprvbizuser.controller;

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

import com.jy.modules.platform.dataprv.sysprvbizuser.dto.SysPrvBizUserDTO;
import com.jy.modules.platform.dataprv.sysprvbizuser.service.SysPrvBizUserService;
import com.jy.platform.api.org.OrgAPI;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysPrvBizUserController
 * @description: 定义 业务数据用户权限表 控制层
 * @author: wangxz
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysPrvBizUser")
public class SysPrvBizUserController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SysPrvBizUserController.class);

	@Autowired
	@Qualifier("com.jy.modules.platform.dataprv.sysprvbizuser.service.SysPrvBizUserService")
	private SysPrvBizUserService service;

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
			model.setViewName("platform/dataprv/sysprvbizuser/querySysPrvBizUser");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			model.setViewName("platform/dataprv/sysprvbizuser/updateSysPrvBizUser");
		} else if("allResult".equals(operate)){// 跳转至用户所有权限结果页面——暂时用户测试
		 model.setViewName("platform/dataprv/sysprvbizuser/viewSysPrvBizUserALLResult");   
		}
		return model;
	}

	/**
	 * @author wangxz
	 * @description:查询分页列表
	 * @date 2014-10-18 16:05:11
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListSysPrvBizUser")
	@ResponseBody
	public DataMsg queryListSysPrvBizUser(HttpServletRequest request,
			SysPrvBizUserDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		List<SysPrvBizUserDTO> list = service.searchSysPrvBizUserByPaging(params.getSearchParams());
		Map<String,String> userMap = new HashMap<String,String>();
		userMap.put("userId", "createUserNameExt");
		list = orgApi.mappingOrgUser(list, userMap, null);
		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}

	/**
	 * @author wangxz
	 * @description:编辑
	 * @date 2014-10-18 16:05:11
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateSysPrvBizUser")
	@ResponseBody
	public DataMsg updateSysPrvBizUser(HttpServletRequest request,
			SysPrvBizUserDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysPrvBizUserDTO) super.initDto(dto);
			String fromUserId = getParameterString("fromUserId");
			String toUserId = getParameterString("toUserId");
			Map<String,Object> updateParam = new HashMap<String,Object>();
			updateParam.put("fromUserId", fromUserId);
			updateParam.put("toUserId", toUserId);
			service.updateSysPrvBizUser(updateParam);

			dataMsg = super.initDataMsg(dataMsg);
			dataMsg.setMsg("修改成功");
			dataMsg.setData(this.makeJSONData(dto.getId()));
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法updateSysResource异常：", e);
		}
		return dataMsg;
	}

}
