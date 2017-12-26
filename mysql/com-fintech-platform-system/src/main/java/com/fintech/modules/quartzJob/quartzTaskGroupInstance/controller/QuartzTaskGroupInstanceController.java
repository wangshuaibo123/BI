package com.fintech.modules.quartzJob.quartzTaskGroupInstance.controller;

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

import com.fintech.modules.quartzJob.quartzTaskGroupDef.dto.QuartzGroupDTO;
import com.fintech.modules.quartzJob.quartzTaskGroupInstance.dto.QuartzTaskGroupInstanceDTO;
import com.fintech.modules.quartzJob.quartzTaskGroupInstance.service.QuartzTaskGroupInstanceService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: QuartzTaskGroupInstanceController
 * @description: 定义 分组任务实例表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/quartzTaskGroupInstance")
public class QuartzTaskGroupInstanceController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(QuartzTaskGroupInstanceController.class);

	@Autowired
	@Qualifier("com.fintech.modules.quartzJob.quartzTaskGroupInstance.service.QuartzTaskGroupInstanceService")
	private QuartzTaskGroupInstanceService service;

	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(@PathVariable("operate") String operate)
			throws AbaboonException {
		ModelAndView model = new ModelAndView();
		// String operate = this.getParameterString("operateData");
		if ("toQueryGroupPage".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/quartzJob/quartzTaskGroupInstance/queryQuartzGroup");
		} else if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/quartzJob/quartzTaskGroupInstance/queryQuartzTaskGroupInstance");
		} else if ("toAdd".equals(operate)) { // 跳转至 新增页面
			model.setViewName("platform/quartzJob/quartzTaskGroupInstance/addQuartzTaskGroupInstance");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/quartzJob/quartzTaskGroupInstance/updateQuartzTaskGroupInstance");
		} else if ("toView".equals(operate)) {// 跳转至 查看页面
			String id = this.getParameterString("batchNo");
			model = this.queryOneDTO(id);
			model.setViewName("platform/quartzJob/quartzTaskGroupInstance/viewQuartzTaskGroupInstance");
		}

		return model;
	}

	/**
	 * @author
	 * @description:查询分页列表
	 * @date 2014-10-14 21:37:39
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListQuartzTaskGroupInstance")
	@ResponseBody
	public DataMsg queryListQuartzTaskGroupInstance(HttpServletRequest request,
			QuartzTaskGroupInstanceDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		List<QuartzTaskGroupInstanceDTO> list = service
				.searchQuartzTaskGroupInstanceByPaging(params.getSearchParams());

		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}

	/**
	 * @author
	 * @description:查询分页列表
	 * @date 2014-10-14 21:37:26
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListQuartzGroup")
	@ResponseBody
	public DataMsg queryListQuartzGroup(HttpServletRequest request,
			QuartzGroupDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		List<QuartzGroupDTO> list = service.searchQuartzGroupByPaging(params
				.getSearchParams());

		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}

	/**
	 * @author
	 * @description:新增
	 * @date 2014-10-14 21:37:39
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertQuartzTaskGroupInstance")
	@ResponseBody
	public DataMsg insertQuartzTaskGroupInstance(HttpServletRequest request,
			QuartzTaskGroupInstanceDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (QuartzTaskGroupInstanceDTO) super.initDto(dto);

			service.insertQuartzTaskGroupInstance(dto);

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
	 * @date 2014-10-14 21:37:39
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateQuartzTaskGroupInstance")
	@ResponseBody
	public DataMsg updateQuartzTaskGroupInstance(HttpServletRequest request,
			QuartzTaskGroupInstanceDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (QuartzTaskGroupInstanceDTO) super.initDto(dto);

			service.updateQuartzTaskGroupInstance(dto);

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
	 * @date 2014-10-14 21:37:39
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteQuartzTaskGroupInstance")
	@ResponseBody
	public DataMsg deleteQuartzTaskGroupInstance(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {

		BaseDTO dto = super.initDto(null);
		String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			service.deleteQuartzTaskGroupInstanceByPrimaryKey(dto, ids);
			dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}

		return dataMsg;
	}

	/**
	 * @author
	 * @description:通过批次号查询 其明细信息
	 * @date 2014-10-14 21:37:39
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			QuartzTaskGroupInstanceDTO dto = new QuartzTaskGroupInstanceDTO();
//			dto.setGroupId(id);
			dto.setBatchNo(id);
			paramMap.put("dto", dto);
			List<QuartzTaskGroupInstanceDTO> list = service
					.searchQuartzTaskGroupDef(paramMap);
			// 将信息放入 model 以便于传至页面 使用
			if (list != null && list.size() > 0)
				model.addObject("dtoList", list);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}

	@RequestMapping(value = "/stopGroupTask")
	@ResponseBody
	public DataMsg stopGroupTask(HttpServletRequest request, String no,
			String val, String type, @ModelAttribute DataMsg dataMsg) {
		dataMsg = super.initDataMsg(dataMsg);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("no", no);
		paramMap.put("val", val);
		service.updateGroupStop(paramMap);
		if (type != null && "1".equals(type))
			service.updateTaskIsEndByNo(paramMap);
		return dataMsg;
	}
	
	@RequestMapping(value="/runAutoGroupTask")
	@ResponseBody
	public DataMsg runAutoGroupTask(HttpServletRequest request, String no,
			String autoExec, @ModelAttribute DataMsg dataMsg){
		dataMsg = super.initDataMsg(dataMsg);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("no", no);
		paramMap.put("autoExec", autoExec);
		try {
			service.updateAutoExceGroupTaskByNo(paramMap);
			dataMsg.setStatus(DataMsg.STATUS_OK);
			return dataMsg;
		} catch (Exception e) {
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
		}
		return dataMsg;
	}
	
	@RequestMapping(value="/resetTaskState")
	@ResponseBody
	public DataMsg resetTaskState(HttpServletRequest request, String id,@ModelAttribute DataMsg dataMsg){
		dataMsg = super.initDataMsg(dataMsg);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		try {
			service.resetTaskState(paramMap);
			dataMsg.setStatus(DataMsg.STATUS_OK);
			return dataMsg;
		} catch (Exception e) {
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
		}
		return dataMsg;
	}
	
	@RequestMapping(value="/updateAutoByTaskClass")
	@ResponseBody
	public DataMsg updateAutoByTaskClass(HttpServletRequest request, String beanId,@ModelAttribute DataMsg dataMsg){
		dataMsg = super.initDataMsg(dataMsg);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beanId", beanId);
		paramMap.put("autoExec", "1");
		try {
			service.updateAutoExceByTaskClass(paramMap);
			dataMsg.setStatus(DataMsg.STATUS_OK);
			return dataMsg;
		} catch (Exception e) {
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
		}
		return dataMsg;
	}
	
	@RequestMapping(value="/searchByTaskClassStatus")
	@ResponseBody
	public DataMsg searchByTaskClassStatus(HttpServletRequest request, String beanId,@ModelAttribute DataMsg dataMsg){
		dataMsg = super.initDataMsg(dataMsg);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beanId", beanId);
		try {
			String result=service.searchByTaskClassStatus(paramMap);
			if(result!=null){
				dataMsg.setStatus(DataMsg.STATUS_OK);
				dataMsg.setMsg("当前自动执行状态为："+(result.trim().equals("1")?"自动执行":"不自动执行"));
			}else{
				dataMsg.setStatus(DataMsg.STATUS_FAILED);
				dataMsg.setMsg("当天没有针对此任务的实例");
			}
			return dataMsg;
		} catch (Exception e) {
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
		}
		return dataMsg;
	}
}
