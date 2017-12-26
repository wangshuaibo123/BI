package com.fintech.modules.platform.syslog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.modules.platform.syslog.dto.SysLogDTO;
import com.fintech.modules.platform.syslog.service.SysLogService;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

@Controller
@Scope("prototype")
@RequestMapping("/sysLog")
public class SysLogController extends BaseController {

	@Autowired
	@Qualifier("com.fintech.modules.platform.syslog.service.SysLogService")
	private SysLogService service;
	
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(@PathVariable("operate") String operate)
			throws AbaboonException{
		ModelAndView model = new ModelAndView();
		// String operate = this.getParameterString("operateData");

		if ("toQueryLogPage".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/sysLog/querySysLog");
		} else if("toView".equals(operate)){
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/sysLog/viewSysLog");
		}

		return model;
	}
	
	@RequestMapping(value="/queryListSysLog")
	@ResponseBody
	public DataMsg querySysLog(HttpServletRequest request,
			SysLogDTO dto, @ModelAttribute DataMsg dataMsg)throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
		List<SysLogDTO> list=service.searchSysLogByPaging(params.getSearchParams());
		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}
	
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			SysLogDTO dto = service.querySysLogByPrimaryKey(id);
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}
}
