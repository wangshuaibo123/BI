package com.jy.modules.platform.sysdict.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jy.modules.platform.sysdict.dto.SysDictDetailDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.core.message.ResponseStatus;
import com.jy.platform.restclient.http.RestClient;
import com.jy.platform.restclient.http.RestService;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysDictDetailController
 * @description: 定义 数据字典明细表 控制层
 * @author: chen_gang
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysDictDetail")
public class SysDictDetailController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SysDictDetailController.class);
	private String jyptAppId = "jypt"; // rest服务appId
	private String jyptURL = RestService.getServiceUrl(jyptAppId);// rest服务地址

	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	@RequestMapping(value = "/prepareExecute/{operate}") 
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");

		if ("toQueryList".equals(operate)) {// 跳转至 查询页面
			String dictId = this.getParameterString("dictId");
			String type = this.getParameterString("type");
			if (dictId != null && dictId.length() > 0)
				model.addObject("dictId", dictId);
			if (type != null && type.length() > 0)
				model.addObject("type", type);
			model.setViewName("platform/sysdict/sysDictDetail/querySysDictDetail");
		} else if ("toAdd".equals(operate)) { // 跳转至 新增页面
			String dictId = this.getParameterString("dictId");
			if (dictId != null && dictId.length() > 0) {
				model = this.querySysDictMaxOrderBy(dictId);
				model.addObject("dictId", dictId);
			}
			model.setViewName("platform/sysdict/sysDictDetail/addSysDictDetail");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/sysdict/sysDictDetail/updateSysDictDetail");
		} else if ("toView".equals(operate)) {// 跳转至 查看页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/sysdict/sysDictDetail/viewSysDictDetail");
		}

		return model;
	}

	/**
	 * @author chen_gang
	 * @description:查询分页列表
	 * @date 2014-10-15 10:28:43
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListSysDictDetail")
	@ResponseBody
	public DataMsg queryListSysDictDetail(HttpServletRequest request,
			SysDictDetailDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		String dictId = this.getParameterString("dictId");
		if (dictId != null && dictId.length() > 0)
			dto.setDictId(Long.parseLong(dictId));
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		String url = jyptURL
				+ "/api/platform/sysdict/SysDictDetailRest/searchByPage/v1/";
		ResponseMsg<QueryRespBean<SysDictDetailDTO>> responseMsg = RestClient
				.doPost(jyptAppId,
						url,
						params,
						new TypeReference<ResponseMsg<QueryRespBean<SysDictDetailDTO>>>() {
						});
		List<SysDictDetailDTO> list = responseMsg.getResponseBody().getResult();

		dataMsg.setData(list);
		dataMsg.setTotalRows(responseMsg.getResponseBody().getPageParameter().getTotalCount());
		/*if (list !=null) {
			dataMsg.setTotalRows(responseMsg.getResponseBody().getPageParameter().getTotalCount());
		}*/
		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:查询分页列表
	 * @date 2014-10-15 10:28:43
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	public ModelAndView querySysDictMaxOrderBy(String dictId)
			throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			String url = jyptURL
					+ "/api/platform/sysdict/SysDictDetailRest/findMaxOrderBy/v1/"
					+ dictId;
			ResponseMsg<String> responseMsg = RestClient.doGet(jyptAppId, url,
					new TypeReference<ResponseMsg<String>>() {
					});
			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())) {
				// 将信息放入 model 以便于传至页面 使用
				model.addObject("maxOrderBy", responseMsg.getResponseBody());
			}
		} catch (Exception e) {
			throw new AbaboonException("执行querySysDictMaxOrderBy异常：", e);
		}
		return model;

	}

	/**
	 * @author chen_gang
	 * @description:查询分页列表
	 * @date 2014-10-15 10:28:43
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDetailCodeIsOk")
	@ResponseBody
	public DataMsg queryDetailCodeIsOk(HttpServletRequest request,
			SysDictDetailDTO dto, @ModelAttribute DataMsg dataMsg)
			throws AbaboonException {
		dataMsg = super.initDataMsg(dataMsg);
		try {
			String code = this.getParameterString("code");
			String dictId = this.getParameterString("dictId");

			if (code != null && code.length() > 0 && dictId != null
					&& dictId.length() > 0) {
				QueryReqBean params = new QueryReqBean();
				Map<String, Object> searchParams = new HashMap<String, Object>();
				dto.setDictDetailName(code);
				dto.setDictId(Long.parseLong(dictId));
				searchParams.put("dto", dto);
				params.setSearchParams(searchParams);
				String url = jyptURL
						+ "/api/platform/sysdict/SysDictDetailRest/queryDetailCodeIsOk/v1/";
				ResponseMsg<String> responseMsg = RestClient.doPost(jyptAppId,
						url, params, new TypeReference<ResponseMsg<String>>() {
						});
				if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()))
					dataMsg.setData(responseMsg.getResponseBody());
				else
					throw new AbaboonException("执行queryDetailCodeIsOk异常：");
			}else{
				dataMsg.setData(0);
			}
		} catch (Exception e) {
			throw new AbaboonException("执行queryDetailCodeIsOk异常：", e);
		}
		return dataMsg;

	}

	/**
	 * @author chen_gang
	 * @description:新增
	 * @date 2014-10-15 10:28:43
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertSysDictDetail")
	@ResponseBody
	public DataMsg insertSysDictDetail(HttpServletRequest request,
			SysDictDetailDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysDictDetailDTO) super.initDto(dto);
			String url = jyptURL
					+ "/api/platform/sysdict/SysDictDetailRest/create/v1";
			ResponseMsg<SysDictDetailDTO> responseMsg = RestClient.doPost(
					jyptAppId, url, dto,
					new TypeReference<ResponseMsg<SysDictDetailDTO>>() {
					});
			dataMsg = super.initDataMsg(dataMsg);
			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()))
				dataMsg.setMsg("新增成功");
			else
				dataMsg.setData("新增失败");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法insertSysResource异常：", e);
		}
		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:编辑
	 * @date 2014-10-15 10:28:43
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateSysDictDetail")
	@ResponseBody
	public DataMsg updateSysDictDetail(HttpServletRequest request,
			SysDictDetailDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysDictDetailDTO) super.initDto(dto);
			String url = jyptURL
					+ "/api/platform/sysdict/SysDictDetailRest/update/v1";
			ResponseMsg<SysDictDetailDTO> responseMsg = RestClient.doPost(
					jyptAppId, url, dto,
					new TypeReference<ResponseMsg<SysDictDetailDTO>>() {
					});
			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())) {
				dataMsg.setMsg("修改成功");
			} else {
				dataMsg.setMsg("修改失败");
			}
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法updateSysResource异常：", e);
		}
		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:删除
	 * @date 2014-10-15 10:28:43
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteSysDictDetail")
	@ResponseBody
	public DataMsg deleteSysDictDetail(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {
		String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			String url = jyptURL
					+ "/api/platform/sysdict/SysDictDetailRest/delete/v1/"
					+ ids;
			ResponseMsg<SysDictDetailDTO> responseMsg = RestClient.doGet(
					jyptAppId, url,
					new TypeReference<ResponseMsg<SysDictDetailDTO>>() {
					});
			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())) {
				dataMsg.setMsg("删除成功");
			} else {
				dataMsg.setMsg("删除失败");
			}
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}

		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:通过主键查询 其明细信息
	 * @date 2014-10-15 10:28:43
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			String url = jyptURL
					+ "/api/platform/sysdict/SysDictDetailRest/get/v1/" + id;
			ResponseMsg<SysDictDetailDTO> responseMsg = RestClient.doGet(
					jyptAppId, url,
					new TypeReference<ResponseMsg<SysDictDetailDTO>>() {
					});
			SysDictDetailDTO dto = responseMsg.getResponseBody();
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}
}
