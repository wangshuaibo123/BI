package com.fintech.modules.quartzJob.quartzTaskGroupDef.controller;

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

import com.fintech.modules.quartzJob.core.service.impl.JYQuartzService;
import com.fintech.modules.quartzJob.quartzTaskGroupDef.dto.QuartzGroupDTO;
import com.fintech.modules.quartzJob.quartzTaskGroupDef.dto.QuartzTaskGroupDefDTO;
import com.fintech.modules.quartzJob.quartzTaskGroupDef.service.QuartzTaskGroupDefService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: QuartzTaskGroupDefController
 * @description: 定义 任务分组定义表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/quartzTaskGroupDef")
public class QuartzTaskGroupDefController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(QuartzTaskGroupDefController.class);

	@Autowired
	@Qualifier("com.fintech.modules.quartzJob.quartzTaskGroupDef.service.QuartzTaskGroupDefService")
	private QuartzTaskGroupDefService service;
	@Autowired
	private JYQuartzService myQuartzService;

	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(@PathVariable("operate") String operate)
			throws AbaboonException {
		ModelAndView model = new ModelAndView();
		// String operate = this.getParameterString("operateData");

		if ("toQueryGroupPage".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/quartzJob/quartzTaskGroupDef/queryQuartzGroup");
		} else if ("toAdd".equals(operate)) { // 跳转至 新增页面
			model.setViewName("platform/quartzJob/quartzTaskGroupDef/addQuartzTaskGroupDef");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/quartzJob/quartzTaskGroupDef/updateQuartzTaskGroupDef");
		} else if ("toView".equals(operate)) {// 跳转至 查看页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/quartzJob/quartzTaskGroupDef/viewQuartzTaskGroupDef");
		}

		return model;
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
	@RequestMapping(value = "/queryListQuartzTaskGroupDef")
	@ResponseBody
	public DataMsg queryListQuartzTaskGroupDef(HttpServletRequest request,
			QuartzTaskGroupDefDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		//获取前台页面传递的分页信息
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		//放入查询条件
		params.setPageParameter(pageInfo);

		List<QuartzTaskGroupDefDTO> list = service
				.searchQuartzTaskGroupDefByPaging(params.getSearchParams());

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
	 * @description:查询分页列表
	 * @date 2014-10-14 21:37:26
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/stopTaskById")
	@ResponseBody
	public DataMsg stopTaskById(HttpServletRequest request, String taskName,
			String isEnd, @ModelAttribute DataMsg dataMsg) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskName", taskName);
		paramMap.put("isEnd", isEnd);
		service.updateTaskIsEndByTaskId(paramMap);
		dataMsg = super.initDataMsg(dataMsg);
		dataMsg.setMsg("修改成功");
		return dataMsg;
	}

	/**
	 * @author
	 * @description:新增
	 * @date 2014-10-14 21:37:26
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertQuartzTaskGroupDef")
	@ResponseBody
	public DataMsg insertQuartzTaskGroupDef(HttpServletRequest request,
			QuartzGroupDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (QuartzGroupDTO) super.initDto(dto);
			if(dto.getTaskList()!=null&&dto.getTaskList().size()>0){
				List<QuartzTaskGroupDefDTO> tasks=dto.getTaskList();
				for(QuartzTaskGroupDefDTO subDto:tasks){
					if(subDto.getTaskName()!=null&&!subDto.getTaskName().trim().equals("")
							&&subDto.getBeanId()!=null&&!subDto.getBeanId().trim().equals("")){//判断对象不能为空
						if(subDto.getPreStep()!=null&&subDto.getPreStep().equals(subDto.getTaskName())){
							dataMsg.failed("任务不能依赖自身,数据新增失败");
							dataMsg.setStatus(DataMsg.STATUS_FAILED);
							return dataMsg;
						}
					}else{
						dataMsg.failed("请填写具体任务信息,任务名称或对应的类名不能为空,数据新增失败");
						dataMsg.setStatus(DataMsg.STATUS_FAILED);
						return dataMsg;
					}
				}
				
				service.insertQuartzGroup(dto);

				dataMsg = super.initDataMsg(dataMsg);
				dataMsg.setMsg("新增成功");
				dataMsg.setStatus(DataMsg.STATUS_OK);
			}else{
				dataMsg.failed("请填写具体任务信息，数据新增失败");
				dataMsg.setStatus(DataMsg.STATUS_FAILED);
			}
			
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
			logger.error("执行方法insertSysResource异常：", e);
		}
		return dataMsg;
	}

	/**
	 * @author
	 * @description:编辑
	 * @date 2014-10-14 21:37:26
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateQuartzTaskGroupDef")
	@ResponseBody
	public DataMsg updateQuartzTaskGroupDef(HttpServletRequest request,
			QuartzGroupDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (QuartzGroupDTO) super.initDto(dto);
			if(dto.getTaskList()!=null&&dto.getTaskList().size()>0){
				List<QuartzTaskGroupDefDTO> tasks=dto.getTaskList();
				for(QuartzTaskGroupDefDTO subDto:tasks){
					if(subDto.getTaskName()!=null&&!subDto.getTaskName().trim().equals("")
							&&subDto.getBeanId()!=null&&!subDto.getBeanId().trim().equals("")){//判断对象不能为空
						if(subDto.getPreStep()!=null&&subDto.getPreStep().equals(subDto.getTaskName())){
							dataMsg.failed("任务不能依赖自身,数据修改失败");
							dataMsg.setStatus(DataMsg.STATUS_FAILED);
							return dataMsg;
						}
					}else{
						dataMsg.failed("请填写具体任务信息,任务名称或对应的类名不能为空,数据修改失败");
						dataMsg.setStatus(DataMsg.STATUS_FAILED);
						return dataMsg;
					}
				}
				service.updateQuartzGroup(dto);

				dataMsg = super.initDataMsg(dataMsg);
				dataMsg.setMsg("修改成功");
				dataMsg.setStatus(DataMsg.STATUS_OK);
			}else{
				dataMsg.failed("请添加具体任务信息，修改失败");
				dataMsg.setStatus(DataMsg.STATUS_FAILED);
			}
			
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
			logger.error("执行方法updateSysResource异常：", e);
		}
		return dataMsg;
	}

	/**
	 * @author
	 * @description:删除
	 * @date 2014-10-14 21:37:26
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteQuartzTaskGroupDef")
	@ResponseBody
	public DataMsg deleteQuartzTaskGroupDef(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {

		BaseDTO dto = super.initDto(null);
		String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			service.deleteQuartzTaskGroupDefByPrimaryKey(dto, ids);
			dataMsg.setMsg("删除成功");
			dataMsg.setStatus(DataMsg.STATUS_OK);
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
			logger.error("执行方法deleteSysResource异常：", e);

		}

		return dataMsg;
	}

	/**
	 * @author
	 * @description:通过主键查询 其明细信息
	 * @date 2014-10-14 21:37:26
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			QuartzTaskGroupDefDTO dto = new QuartzTaskGroupDefDTO();
			dto.setGroupId(id);
			paramMap.put("dto", dto);
			List<QuartzTaskGroupDefDTO> list = service
					.searchQuartzTaskGroupDef(paramMap);
			// 将信息放入 model 以便于传至页面 使用
			if (list != null && list.size() > 0)
				model.addObject("dtoList", list);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}

	@RequestMapping(value = "/updateGroupStateByGroupId")
	@ResponseBody
	public DataMsg updateGroupStateByGroupId(HttpServletRequest request,
			String groupId, String groupState, @ModelAttribute DataMsg dataMsg) {
		dataMsg = super.initDataMsg(dataMsg);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("groupId", groupId);
		paramMap.put("groupState", groupState);
		try {
			service.updateGroupStateByGroupId(paramMap);
			dataMsg.setMsg("发布成功");
			dataMsg.setStatus(DataMsg.STATUS_OK);
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
			logger.error("执行方法updateGroupStateByGroupId异常：", e);
		}
		return dataMsg;
	}

	@RequestMapping(value = "/insertToInstanceByGroupId")
	@ResponseBody
	public DataMsg insertToInstanceByGroupId(HttpServletRequest request,
			String groupId, @ModelAttribute DataMsg dataMsg) {
		dataMsg = super.initDataMsg(dataMsg);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("groupId", groupId);
		try {
			myQuartzService.initGroupInstanceInfoOne(param);
			dataMsg.setMsg("执行成功");
			dataMsg.setStatus(DataMsg.STATUS_OK);
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
			logger.error("执行方法insertToInstanceByGroupId异常：", e);
		}
		return dataMsg;
	}
}
