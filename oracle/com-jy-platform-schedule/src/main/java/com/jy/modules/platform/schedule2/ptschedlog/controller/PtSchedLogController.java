package com.jy.modules.platform.schedule2.ptschedlog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jy.modules.platform.schedule2.ptschedlog.dto.PtSchedLogDTO;
import com.jy.modules.platform.schedule2.ptschedlog.service.PtSchedLogService;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: PtSchedLogController
 * @description: 定义 pt_sched_log 控制层
 * @author: JY-IT-D001
 */
@Controller
@RequestMapping("/ptSchedLog")
public class PtSchedLogController extends BaseController {

	@Autowired
	@Qualifier("com.jy.modules.platform.schedule2.ptschedlog.service.PtSchedLogService")
	private PtSchedLogService service;

	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
		ModelAndView model = new ModelAndView();

		if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/ptschedlog/queryPtSchedLog");
		}

		return model;
	}

	/**
	 * @author JY-IT-D001
	 * @description:查询分页列表
	 * @date 2017-02-07 16:22:26
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListPtSchedLog")
	@ResponseBody
	public DataMsg queryListPtSchedLog(PtSchedLogDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		List<PtSchedLogDTO> list = service.searchPtSchedLogByPaging(params.getSearchParams());

		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}

}
