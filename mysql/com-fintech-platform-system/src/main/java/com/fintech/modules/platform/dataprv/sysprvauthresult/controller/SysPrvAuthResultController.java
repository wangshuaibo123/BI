package com.fintech.modules.platform.dataprv.sysprvauthresult.controller;

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

import com.fintech.modules.platform.dataprv.sysprvauthresult.dto.SysPrvAuthResultDTO;
import com.fintech.modules.platform.dataprv.sysprvauthresult.service.SysPrvAuthResultService;
import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysPrvAuthResultController
 * @description: 定义 数据权限授权结果表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysPrvAuthResult")
public class SysPrvAuthResultController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SysPrvAuthResultController.class);

	@Autowired
	@Qualifier("com.fintech.modules.platform.dataprv.sysprvauthresult.service.SysPrvAuthResultService")
	private SysPrvAuthResultService service;
	@Autowired
	private OrgAPI orgApi;
	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");

		if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/dataprv/sysprvauthresult/querySysPrvAuthResult");
		}

		return model;
	}

	/**
	 * @author
	 * @description:查询分页列表
	 * @date 2014-10-18 16:06:51
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListSysPrvAuthResult")
	@ResponseBody
	public DataMsg queryListSysPrvAuthResult(HttpServletRequest request,
			SysPrvAuthResultDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		List<SysPrvAuthResultDTO> list = service
				.searchSysPrvAuthResultByPaging(params.getSearchParams());
	     Map<String,String> userMap = new HashMap<String,String>();
			userMap.put("userIdFrom", "createUserNameExt");
			userMap.put("userIdTo", "createOrgNameExt");
			
			list = orgApi.mappingOrgUser(list, userMap, null);
		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}

	/**
	 * @author
	 * @description:通过主键查询 其明细信息
	 * @date 2014-10-18 16:06:51
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/insertSysAuthResult")
	@ResponseBody
	public DataMsg insertSysAuthResult(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) throws AbaboonException {
		dataMsg = super.initDataMsg(dataMsg);
		try {
			service.insertSysAuthResult(null);
			dataMsg.setMsg("权限刷新成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}

		return dataMsg;
	}
}
