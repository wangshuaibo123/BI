package com.jy.modules.bpmreport.controller;

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

import com.jy.modules.bpmreport.dto.DataBean;
import com.jy.modules.bpmreport.service.RoleBPMReportService;
import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * 角色统计分析
 * @author xyz
 * @date 2016-2-19
 */
@Controller
@Scope("prototype")
@RequestMapping("/dojbpm/roleBPMReport")
public class RoleBPMReportController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RoleBPMReportController.class);
	@Autowired
	private SessionAPI sessionAPI;
	@Resource(name="roleBPMReportService")
	private RoleBPMReportService roleBPMReportService;
	/**
	 * 转发到 积压最多展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toOverStockMost", method = RequestMethod.GET)
	public ModelAndView toOverStockMost(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/role/overStockMost");
	}
	/**
	 * 界面展示方式
	 */
	@RequestMapping(value = "/showPage/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toOverStockMost".equals(operate)){//转发到 积压最多展示界面
        	model.setViewName("platform/jbpm/report/role/overStockMost");
        }else if("toProcessedMost".equals(operate)){ // 转发到 已办最多展示界面
        	model.setViewName("platform/jbpm/report/role/processedMost");
        }else if("toStartUpMost".equals(operate)){//转发到 发起最多展示界面
        	model.setViewName("platform/jbpm/report/role/startUpMost");
        }else if("toAvgEfficientHighest".equals(operate)){// 转发到 平均速率最高展示界面
        	model.setViewName("platform/jbpm/report/role/avgEfficientHighest");
        }else if("toAvgEfficientLowest".equals(operate)){//转发到 平均速率最低展示界面
        	model.setViewName("platform/jbpm/report/role/avgEfficientLowest");
        }else if("toEntrustMost".equals(operate)){//转发到 委托最多展示界面
        	model.setViewName("platform/jbpm/report/role/entrustMost");
        }else if("toSupportMost".equals(operate)){// 转发到乐于助人最多展示界面
        	model.setViewName("platform/jbpm/report/role/supportMost");
        }else if("toEntrustState".equals(operate)){// 转发到委托状况展示界面
        	model.setViewName("platform/jbpm/report/role/entrustState");
        }
        
        return model;
    }
	
	
	/**
	 * 获取积压最多的数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getOverStockMostData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getOverStockMostData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询角色维度 积压最多流程的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=roleBPMReportService.getOverStockMostData(userInfo);
		return list;
	}
	
	/**
	 * 转发到 已办最多展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toProcessedMost", method = RequestMethod.GET)
	public ModelAndView toProcessedMost(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/role/processedMost");
	}

	/**
	 * 获取已办最多TOP10的流程数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getProcessedMostData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getProcessedMostData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询角色维度已办最多流程的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=roleBPMReportService.getProcessedMostData(userInfo);
		return list;
	}
	
	
	/**
	 * 转发到 发起最多展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toStartUpMost", method = RequestMethod.GET)
	public ModelAndView toStartUpMost(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/role/startUpMost");
	}

	/**
	 * 获取发起最多TOP10的流程数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStartUpMostData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getStartUpMostData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询角色维度发起最多流程的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=roleBPMReportService.getStartUpMostData(userInfo);
		return list;
	}
	/**
	 * 转发到 平均速率最高展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toAvgEfficientHighest", method = RequestMethod.GET)
	public ModelAndView toAvgEfficientHighest(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/role/avgEfficientHighest");
	}

	/**
	 * 获取平均速率最高TOP10的流程数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAvgEfficientHighestData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getAvgEfficientHighestData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询角色维度平均速率最高TOP10的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=roleBPMReportService.getAvgEfficientHighestData(userInfo);
		return list;
	}
	
	
	/**
	 * 转发到 平均速率最低展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toAvgEfficientLowest", method = RequestMethod.GET)
	public ModelAndView toAvgEfficientLowest(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/role/avgEfficientLowest");
	}

	/**
	 * 获取平均速率最低TOP10的流程数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAvgEfficientLowestData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getAvgEfficientLowestData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询角色维度平均速率最低TOP10的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=roleBPMReportService.getAvgEfficientLowestData(userInfo);
		return list;
	}
	
	
	
	
	/**
	 * 转发到 委托最多展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toEntrustMost", method = RequestMethod.GET)
	public ModelAndView toEntrustMost(HttpServletRequest request, HttpServletResponse response) {
	

		 return new ModelAndView("platform/jbpm/report/role/entrustMost");
	}

	/**
	 * 获取委托最多TOP10的流程数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getEntrustMostData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getEntrustMostData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询角色维度委托最多TOP10的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=roleBPMReportService.getEntrustMostData(userInfo);
		return list;
	}
	
	
	
	/**
	 * 转发到乐于助人最多展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toSupportMost", method = RequestMethod.GET)
	public ModelAndView toSupportMost(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/role/supportMost");
	}

	/**
	 * 获取乐于助人最多TOP10的流程数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getSupportMostData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getSupportMostData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询角色维度乐于助人最多TOP10的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=roleBPMReportService.getSupportMostData(userInfo);
		return list;
	}
	
	/**
	 * 转发到委托状况展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toEntrustState", method = RequestMethod.GET)
	public ModelAndView toEntrustState(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/role/entrustState");
	}

	/**
	 * 获取委托状况的流程数据
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
		logger.info("用户ID 为：" + userId + " 用户正在查询角色维度委托状况的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=roleBPMReportService.getEntrustStateData(userInfo);
		return list;
	}


}
