package com.fintech.modules.platform.sysorg.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.platform.sysorg.dto.SysPositionDTO;
import com.fintech.modules.platform.sysorg.service.SysPositionService;
import com.fintech.platform.api.org.OrgAPI;
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
 * @classname: SysPositionController
 * @description: 定义  岗位定义表 控制层
 * @author
 */
@SuppressWarnings("all")
@Controller
@Scope("prototype")
@RequestMapping("/sysPosition")
public class SysPositionController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysPositionController.class);
	private String jyptAppId = "ptpt"; //rest服务appId
	private String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);//rest服务地址

    @Autowired
    @Qualifier("com.fintech.modules.platform.sysorg.service.SysPositionService")
    private SysPositionService service;
    
    @Autowired
   	private OrgAPI orgApi;
    
    //格式化时间
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysorg/sysPosition/querySysPosition");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysorg/sysPosition/addSysPosition");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysorg/sysPosition/updateSysPosition");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysorg/sysPosition/viewSysPosition");
        }else if("toSelectPage".equals(operate)){//跳转至选择页面
        	model.setViewName("platform/sysorg/sysPosition/selectSysPosition");
        }else if("toSelectPositionPage".equals(operate)){//跳转至选择页面
        	model.setViewName("platform/sysorg/sysPosition/selectSysOrgPosition");
        }
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-10-15 10:26:19
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysPosition")
    @ResponseBody
    public DataMsg queryListSysPosition(HttpServletRequest request, SysPositionDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	String orgCode = request.getParameter("orgCode");
    	dto.setOrgCode(orgCode);
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		try {
			String url= jyptURL+"/api/platform/sysorg/SysPositionRest/searchByPage/v1";
			ResponseMsg<QueryRespBean<SysPositionDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params, new TypeReference<ResponseMsg<QueryRespBean<SysPositionDTO>>>(){});
			List<SysPositionDTO> list  = responseMsg.getResponseBody().getResult();
			dataMsg.setData(list);
			dataMsg.setTotalRows(responseMsg.getResponseBody().getPageParameter().getTotalCount());
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
		}
        return dataMsg;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-10-15 10:26:19
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysOrgPosition")
    @ResponseBody
    public DataMsg queryListSysOrgPosition(HttpServletRequest request, SysPositionDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	String orgCode = request.getParameter("orgCode");
    	dto.setOrgCode(orgCode);
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		try {
			String url= jyptURL+"/api/platform/sysorg/SysPositionRest/searchByPage/v1";
			ResponseMsg<QueryRespBean<SysPositionDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params, new TypeReference<ResponseMsg<QueryRespBean<SysPositionDTO>>>(){});
			List<SysPositionDTO> list  = responseMsg.getResponseBody().getResult();
			dataMsg.setData(list);
			dataMsg.setTotalRows(responseMsg.getResponseBody().getPageParameter().getTotalCount());
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
		}
        return dataMsg;
    }

    /**
     * @author
     * @description:新增
     * @date 2014-10-15 10:26:19
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysPosition")
    @ResponseBody
    public DataMsg insertSysPosition(HttpServletRequest request, SysPositionDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysPositionDTO)super.initDto(dto);
        	//设置版本号
        	dto.setVersion(SysPositionDTO.generatorVersion());
        	//默认有效
        	dto.setValidateState("1");
        	dto.setCreateDate(new Date());
        	if(StringUtils.isNotBlank(dto.getEfDate())){
        		dto.setEffectiveDate(sdf.parse(dto.getEfDate()));
        	}
        	if(dto.getEffectiveDate() == null){
        		dto.setEffectiveDate(new Date());
        	}
			String url= jyptURL+"/api/platform/sysorg/SysPositionRest/create/v1";
			ResponseMsg<Void> responseMsg  = RestClient.doPost(jyptAppId, url, dto, new TypeReference<ResponseMsg<Void>>(){});
            dataMsg = super.initDataMsg(dataMsg);
            if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
				dataMsg.setMsg("新增成功");
				dataMsg.setData(this.makeJSONData(dto.getId()));//TODO
			}else{
				dataMsg.setMsg("新增失败");
			}
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysPosition异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:编辑
     * @date 2014-10-15 10:26:19
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysPosition")
    @ResponseBody
    public DataMsg updateSysPosition(HttpServletRequest request, SysPositionDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysPositionDTO)super.initDto(dto);
        	//设置版本号
        	dto.setVersion(SysPositionDTO.generatorVersion());
        	if(StringUtils.isNotBlank(dto.getEfDate())){
        		dto.setEffectiveDate(sdf.parse(dto.getEfDate()));
        	}
			String url= jyptURL+"/api/platform/sysorg/SysPositionRest/update/v1";
			ResponseMsg<Void> responseMsg = RestClient.doPost(jyptAppId, url, dto, new TypeReference<ResponseMsg<Void>>(){});
			if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
				dataMsg.setMsg("保存成功");
			}else{
				dataMsg.setMsg("保存失败");
			}
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysPosition异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:删除
     * @date 2014-10-15 10:26:19
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysPosition")
    @ResponseBody
    public DataMsg deleteSysPosition(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 String url= jyptURL+"/api/platform/sysorg/SysPositionRest/delete/v1/"+ids;
				ResponseMsg<Void> responseMsg = RestClient.doGet(jyptAppId, url, new TypeReference<ResponseMsg<Void>>(){});
				if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
					dataMsg.setMsg("删除成功");
				}else{
					dataMsg.setMsg("删除失败");
				}
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysPosition异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author
     * @description:通过主键查询 其明细信息
     * @date 2014-10-15 10:26:19
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	//查询当前ID
			String url= jyptURL+"/api/platform/sysorg/SysPositionRest/get/v1/"+id;
			ResponseMsg<SysPositionDTO> responseMsg = RestClient.doGet(jyptAppId, url, new TypeReference<ResponseMsg<SysPositionDTO>>(){});
			SysPositionDTO dto = responseMsg.getResponseBody();
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
