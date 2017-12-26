package com.fintech.modules.platform.sysdict.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.platform.sysdict.dto.SysDictDTO;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.api.sysdict.SysDictAPI;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.core.message.ResponseStatus;
import com.fintech.platform.restclient.http.RestClient;
import com.fintech.platform.restclient.http.RestClientConfig;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysDictController
 * @description: 定义 数据字典 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysDict")
public class SysDictController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SysDictController.class);
	private String jyptAppId = "ptpt"; // rest服务appId
	private String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);// rest服务地址

	// @Autowired
	// @Qualifier("com.fintech.modules.platform.sysdict.service.SysDictService")
	// private SysDictService service;

	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");

		if ("toQueryMain".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/sysdict/sysDictMain");
		} else if ("toQueryPage".equals(operate)) { // 跳转至 新增页面
			model.setViewName("platform/sysdict/sysDict/querySysDict");
		} else if ("toAdd".equals(operate)) { // 跳转至 新增页面
			model.setViewName("platform/sysdict/sysDict/addSysDict");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/sysdict/sysDict/updateSysDict");
		} else if ("toView".equals(operate)) {// 跳转至 查看页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/sysdict/sysDict/viewSysDict");
		}

		return model;
	}

	/**
	 * @author
	 * @description:查询分页列表
	 * @date 2014-10-15 10:28:27
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListSysDict")
	@ResponseBody
	public DataMsg queryListSysDict(HttpServletRequest request, SysDictDTO dto,
			@ModelAttribute DataMsg dataMsg) throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		String url = jyptURL
				+ "/api/platform/sysdict/SysDictRest/searchByPageNoCache/v1/";
		ResponseMsg<QueryRespBean<SysDictDTO>> responseMsg = RestClient.doPost(
				jyptAppId, url, params,
				new TypeReference<ResponseMsg<QueryRespBean<SysDictDTO>>>() {
				});
		List<SysDictDTO> list = responseMsg.getResponseBody().getResult();

		dataMsg.setData(list);
		dataMsg.setTotalRows(responseMsg.getResponseBody().getPageParameter().getTotalCount());
		return dataMsg;
	}
	
	/**
	 * @author
	 * @description:唯一性验证
	 * @date 2014-10-15 10:28:43
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDictCodeIsOk")
	@ResponseBody
	public DataMsg queryDictCodeIsOk(HttpServletRequest request,
			SysDictDTO dto, @ModelAttribute DataMsg dataMsg)
			throws AbaboonException {
		dataMsg = super.initDataMsg(dataMsg);
		try {
			String code = this.getParameterString("code");

			if (code != null && code.length() > 0) {
				String url = jyptURL
						+ "/api/platform/sysdict/SysDictRest/queryDictCodeIsOk/v1/"+code;
				ResponseMsg<String> responseMsg = RestClient.doGet(jyptAppId,
						url, new TypeReference<ResponseMsg<String>>() {
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
	 * @author
	 * @description:新增
	 * @date 2014-10-15 10:28:27
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertSysDict")
	@ResponseBody
	public DataMsg insertSysDict(HttpServletRequest request, SysDictDTO dto,
			@ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysDictDTO) super.initDto(dto);

			String url = jyptURL
					+ "/api/platform/sysdict/SysDictRest/create/v1";
			ResponseMsg<SysDictDTO> responseMsg = RestClient.doPost(jyptAppId,
					url, dto, new TypeReference<ResponseMsg<SysDictDTO>>() {
					});
			dataMsg = super.initDataMsg(dataMsg);
			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()))
				dataMsg.setMsg("新增成功");
			else
				dataMsg.setMsg("新增失败");
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
	 * @date 2014-10-15 10:28:27
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateSysDict")
	@ResponseBody
	public DataMsg updateSysDict(HttpServletRequest request, SysDictDTO dto,
			@ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysDictDTO) super.initDto(dto);

			String url = jyptURL
					+ "/api/platform/sysdict/SysDictRest/update/v1";
			ResponseMsg<SysDictDTO> responseMsg = RestClient.doPost(jyptAppId,
					url, dto, new TypeReference<ResponseMsg<SysDictDTO>>() {
					});
			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())) {
				dataMsg.setMsg("修改成功");
			} else {
				dataMsg.setMsg("保存失败");
			}
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
	 * @date 2014-10-15 10:28:27
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteSysDict")
	@ResponseBody
	public DataMsg deleteSysDict(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) {
		String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			String url = jyptURL
					+ "/api/platform/sysdict/SysDictRest/delete/v1/" + ids;
			ResponseMsg<SysDictDTO> responseMsg = RestClient.doGet(jyptAppId,
					url, new TypeReference<ResponseMsg<SysDictDTO>>() {
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
	 * @author
	 * @description:通过主键查询 其明细信息
	 * @date 2014-10-15 10:28:27
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			String url = jyptURL + "/api/platform/sysdict/SysDictRest/get/v1/"
					+ id;
			ResponseMsg<SysDictDTO> responseMsg = RestClient.doGet(jyptAppId,
					url, new TypeReference<ResponseMsg<SysDictDTO>>() {
					});
			SysDictDTO dto = responseMsg.getResponseBody();
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}
	/**
	 * @author
	 * @description: 提供 字典表下拉多选
	 * @date 2016年1月18日 下午6:23:08
	 * @param codeType 字典编码
	 * @param used 自定义控制显示
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTreeJSON")
    @ResponseBody
    public List<Map<String, String>> queryTreeJSON(HttpServletRequest request,@ModelAttribute DataMsg dataMsg) throws Exception {
        String codeType = request.getParameter("codeType");
        String used = request.getParameter("used");
        
        ApplicationContext context = null;
		try{
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
			ServletContext servletContext = webApplicationContext.getServletContext();
			context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		}catch(Exception e){
			context = SysConfigAPI.getApplicationContext();
		}
		SysDictAPI sysDictAPI = (SysDictAPI)context.getBean("sysDictAPI");
		
        List<Map> list = new ArrayList<Map>();
        try {
        	if(used != null && used.length()>0){
				List<Map> allList = sysDictAPI.getDictByKey(codeType);
				if(allList != null && allList.size()>0){
					for(int j=0;j<allList.size();j++){
						if((","+used + ",").indexOf(","+allList.get(j).get("DICVALUE")+",") !=-1){
							list.add(allList.get(j));
						 }
					}
				}
			}else{
				list = sysDictAPI.getDictByKey(codeType);
			}
        }catch (Exception e) {
            dataMsg.setStatus(DataMsg.STATUS_FAILED);
            logger.error("执行方法queryTreeJSON异常：", e);
        }
       /* Map<String,Object> firstMap = new HashMap<String,Object>();
        firstMap.put("DICVALUE", "");
        firstMap.put("DICNAME", "请选择");
        list.add(firstMap);*/
        return treeData(list);// 组织树的数据
        
    }
	
	private List<Map<String, String>> treeData(List<Map> data) {
        if (data != null && data.size() > 0) {
            List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
            for (Map dto : data) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("ID", dto.get("DICVALUE").toString());
                map.put("NAME", dto.get("DICNAME").toString());
                /*map.put("orgType", dto.getOrgType());
                map.put("endFlag", dto.getEndFlag());
                map.put("sourceType", dto.getSourceType());*/
                map.put("PID", "-999999");
                // map.put("open", "true");
                maps.add(map);
            }
            return maps;
        } else {
            return null;
        }
    }

    
}
