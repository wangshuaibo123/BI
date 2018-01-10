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
import com.fintech.modules.bpmreport.service.ProcessReportService;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * 
 * @author 流程维度 统计分析
 */
@Controller
@Scope("prototype")
@RequestMapping("/dojbpm/processReport")
public class ProcessReportController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ProcessReportController.class);
	@Autowired
	private SessionAPI sessionAPI;
	@Resource(name="processReportService")
	private ProcessReportService processReportService;
	/**
	 * 转发到 积压最多展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toOverStockMore", method = RequestMethod.GET)
	public ModelAndView toOverStockMore(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/process/overStockMore");
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
        
        if("toOverStockMore".equals(operate)){//转发到 积压最多展示界面
        	model.setViewName("platform/jbpm/report/process/overStockMore");
        }else if("toProcessedMore".equals(operate)){ // 转发到 已办最多展示界面
        	model.setViewName("platform/jbpm/report/process/processedMore");
        }else if("toStartUpMore".equals(operate)){//转发到 发起最多展示界面
        	model.setViewName("platform/jbpm/report/process/startUpMore");
        }else if("toAvgEfficientHighest".equals(operate)){// 转发到 平均速率最高展示界面
        	model.setViewName("platform/jbpm/report/process/avgEfficientHighest");
        }else if("toAvgEfficientLowest".equals(operate)){//转发到 平均速率最低展示界面
        	model.setViewName("platform/jbpm/report/process/avgEfficientLowest");
        }else if("toAvgHumanEfficientHighest".equals(operate)){//转发到人工 平均速率最高展示界面
        	model.setViewName("platform/jbpm/report/process/avgHumanEfficientHighest");
        }else if("toAvgHumanEfficientLowest".equals(operate)){// 转发到 平均人工活动速率最低展示界面
        	model.setViewName("platform/jbpm/report/process/avgHumanEfficientLowest");
        }
        
        return model;
    }
	
	
	
	
	/**
	 * 获取积压最多的流程数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getOverStockMoreData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getOverStockMoreData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询流程维度 积压最多流程的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=processReportService.getOverStockMoreData(userInfo);
		return list;
	}
	
	/**
	 * 转发到 已办最多展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toProcessedMore", method = RequestMethod.GET)
	public ModelAndView toProcessedMore(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/process/processedMore");
	}

	/**
	 * 获取已办最多TOP10的流程数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getProcessedMoreData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getProcessedMoreData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询流程维度已办最多流程的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=processReportService.getProcessedMoreData(userInfo);
		return list;
	}
	
	
	/**
	 * 转发到 发起最多展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toStartUpMore", method = RequestMethod.GET)
	public ModelAndView toStartUpMore(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/process/startUpMore");
	}

	/**
	 * 获取发起最多TOP10的流程数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getStartUpMoreData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getStartUpMoreData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询流程维度发起最多流程的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=processReportService.getStartUpMoreData(userInfo);
		return list;
	}
	//////////////////////////////////////////////////////////////////////////
	/**
	 * 转发到 平均速率最高展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toAvgEfficientHighest", method = RequestMethod.GET)
	public ModelAndView toAvgEfficientHighest(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/process/avgEfficientHighest");
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
		logger.info("用户ID 为：" + userId + " 用户正在查询流程维度平均速率最高TOP10的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=processReportService.getAvgEfficientHighestData(userInfo);
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
	
		 return new ModelAndView("platform/jbpm/report/process/avgEfficientLowest");
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
		logger.info("用户ID 为：" + userId + " 用户正在查询流程维度平均速率最低TOP10的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=processReportService.getAvgEfficientLowestData(userInfo);
		return list;
	}
	
	
	/**
	 * 转发到人工 平均速率最高展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toAvgHumanEfficientHighest", method = RequestMethod.GET)
	public ModelAndView toAvgHumanEfficientHighest(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/process/avgHumanEfficientHighest");
	}

	/**
	 * 获取平均人工活动速率最高TOP10的流程数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAvgHumanEfficientHighestData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getAvgHumanEfficientHighestData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询流程维度平均人工活动速率最高TOP10的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=processReportService.getAvgHumanEfficientHighestData(userInfo);
		return list;
	}
	
	
	/**
	 * 转发到 平均人工活动速率最低展示界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toAvgHumanEfficientLowest", method = RequestMethod.GET)
	public ModelAndView toAvgHumanEfficientLowest(HttpServletRequest request, HttpServletResponse response) {
	
		 return new ModelAndView("platform/jbpm/report/process/avgHumanEfficientLowest");
	}

	/**
	 * 获取平均人工活动速率最低TOP10的流程数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getAvgHumanEfficientLowestData", method = RequestMethod.POST)
	@ResponseBody
	public List<DataBean> getAvgHumanEfficientLowestData(HttpServletRequest request, HttpServletResponse response) {
		// 当前登录系统的用户id
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
		String userId = userInfo.getUserId() + "";
		logger.info("用户ID 为：" + userId + " 用户正在查询流程维度平均人工活动速率最低TOP10的查询");
		List<DataBean> list = new ArrayList<DataBean>();
		list=processReportService.getAvgHumanEfficientLowestData(userInfo);
		return list;
	}
	
}
