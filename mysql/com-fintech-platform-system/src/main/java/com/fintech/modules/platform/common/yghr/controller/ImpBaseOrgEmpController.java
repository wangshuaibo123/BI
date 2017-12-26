package com.fintech.modules.platform.common.yghr.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.fintech.modules.platform.common.dto.HrOrgDTO;
import com.fintech.modules.platform.common.dto.YGorgJSONDTO;
import com.fintech.modules.platform.common.yghr.service.YgHrService;
import com.fintech.modules.platform.common.ygorg.service.YgOrgService;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * 通过json 导入基础的用户组织机构岗位信息
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/system/impBaseOrgEmp")
public class ImpBaseOrgEmpController extends BaseController{
	private static final Logger logger =  LoggerFactory.getLogger(ImpBaseOrgEmpController.class);
	@Autowired
	@Qualifier("com.fintech.modules.platform.common.yghr.service.YgHrService")
	private YgHrService hrBiz;
	@Autowired
	@Qualifier("com.fintech.modules.platform.common.ygorg.service.YgOrgService")
	private YgOrgService orgBiz;
	@Autowired
	private SessionAPI sessionAPI;
	@Autowired
	private SysConfigAPI sysConfig;

	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 * @throws Exception 
	 */
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(HttpServletRequest request,@PathVariable("operate") String operate) throws Exception {
		ModelAndView model = new ModelAndView();

		if("toQueryPage".equals(operate)){//跳转至 查询页面
			String isAdmin = request.getParameter("isAdmin");
			model.addObject("isAdmin", isAdmin);
			model.setViewName("platform/leaveinfo/queryLeaveInfo");
			
		}
		return model;
	}

	/**
	 * 导入json数据 入口
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/impBaseJSON")
	@ResponseBody
	public DataMsg impBaseJSON(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) throws Exception {
		try {
			List<HrOrgDTO> dataList = hrBiz.parseJson();
			if(dataList == null) throw new Exception("同步数据为null");
			hrBiz.insertBatchYgHr(dataList);
			//orgBiz.insertBatchYgOrg(dataList);
			//将银谷hr 临时表数据 同步至相关 用户组织机构表数据
			
			hrBiz.insertBatchToSys();
			dataMsg.setMsg("导入成功！");
		} catch (Exception e) {
			logger.error("=========impBaseJSON=error=",e);
			dataMsg.setMsg("导入失败！"+e.getMessage());
		}
		
		return dataMsg;
	}

	@RequestMapping(value = "/impOrgBaseJSON")
	@ResponseBody
	public DataMsg impOrgBaseJSON(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) throws Exception {
		try {
			List<YGorgJSONDTO> dataList = new ArrayList<YGorgJSONDTO>();
			String orgURL = sysConfig.getValue("YG_ORG_URL");
			
			orgBiz.parseOrgJson(orgURL,dataList);
			if(dataList == null) throw new Exception("同步数据为null");
			hrBiz.truncateYgORGTable();
			orgBiz.insertBatchYGorgJSONDTO(dataList);
			//将银谷hr 临时表数据 同步至相关 用户组织机构表数据
			
			hrBiz.insertBatchOrgToSys();
			dataMsg.setMsg("导入成功！");
		} catch (Exception e) {
			logger.error("=========impOrgBaseJSON=error=",e);
			dataMsg.setMsg("导入失败！"+e.getMessage());
		}
		
		return dataMsg;
	}
	
	public static void main(String[] args) throws Exception {
		logger.info("====start==========");
		YgHrService base = new YgHrService();
		List<HrOrgDTO> list = base.parseJson();
		logger.info("====end==========");
	}
}




