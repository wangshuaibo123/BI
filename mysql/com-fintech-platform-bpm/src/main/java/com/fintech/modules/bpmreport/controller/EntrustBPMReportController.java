package com.fintech.modules.bpmreport.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.modules.bpmreport.dto.DataBean;
import com.fintech.modules.bpmreport.service.EntrustBPMReportService;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * 委托统计
 * @author
 * @date 2016-2-22
 */
@Controller
@Scope("prototype")
@RequestMapping("/dojbpm/entrustBPMReport")
public class EntrustBPMReportController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DeptBPMReportController.class);
	@Autowired
	private SessionAPI sessionAPI;
	@Resource(name="entrustBPMReportService")
	private EntrustBPMReportService entrustBPMReportService;
	/**
	 * 转发到 委托最多最多展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toEntrustMost", method = RequestMethod.GET)
	public ModelAndView toEntrustMost(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/entrust/entrustMost");
	}
	/**
	 * 转发到 乐于助人最多展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toSupportMost", method = RequestMethod.GET)
	public ModelAndView toSupportMost(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/entrust/supportMost");
	}
	/**
	 * 转发到 委托概况展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toEntrustState", method = RequestMethod.GET)
	public ModelAndView toEntrustState(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/entrust/entrustState");
	}
	
	/**
	 * 界面展示方式
	 * @param operate
	 * @return
	 * @throws AbaboonException
	 */
	@RequestMapping(value = "/showPage/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toEntrustMost".equals(operate)){//转发到 委托最多最多展示界面
        	model.setViewName("platform/jbpm/report/entrust/entrustMost");
        }else if("toSupportMost".equals(operate)){ // 转发到 乐于助人最多展示界面
        	model.setViewName("platform/jbpm/report/entrust/supportMost");
        }else if("toEntrustState".equals(operate)){//转发到 委托概况展示界面
        	model.setViewName("platform/jbpm/report/entrust/entrustState");
        }
        
        return model;
    }
	
	
	
	/**
	 * 获取委托概况的数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getEntrustStateData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getEntrustStateData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在进行委托概况相关数据的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=entrustBPMReportService.getEntrustStateData(userInfo);
		return list;
	}
	
}
