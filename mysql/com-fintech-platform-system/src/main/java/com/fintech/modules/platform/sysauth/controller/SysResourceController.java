package com.fintech.modules.platform.sysauth.controller;

import java.util.ArrayList;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.platform.sysauth.dto.SysResourceDTO;
import com.fintech.modules.platform.sysauth.interceptor.ResourceInterceptor;
import com.fintech.modules.platform.sysauth.service.SysResourceService;
import com.fintech.modules.platform.sysauth.util.ResourceImportUtil;
import com.fintech.platform.core.common.BaseDTO;
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
 * @classname: SysResourceController
 * @description: 定义  资源管理表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysResource")
public class SysResourceController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysResourceController.class);
   	private String jyptAppId = "ptpt"; //rest服务appId
   	private String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);//rest服务地址
    @Autowired
    @Qualifier("com.fintech.modules.platform.sysauth.service.SysResourceService")
    private SysResourceService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysauth/sysResource/querySysResource");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysauth/sysResource/addSysResource");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysauth/sysResource/updateSysResource");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysauth/sysResource/viewSysResource");
        }else if("toTreePage".equals(operate)){//跳转至树形管理界面
        	model.setViewName("platform/sysauth/sysResource/treeSysResource");
        }else if("toRoleResource".equals(operate)){//跳转至树形选择界面
        	model.setViewName("platform/sysauth/sysResource/addSysResourceForRole");
        }else if("toRoleList".equals(operate)){//加载角色列表
        	model.setViewName("platform/sysauth/sysResource/roleList");
        }else if("toResourceTree".equals(operate)){//加载资源树
        	model.setViewName("platform/sysauth/sysResource/resourceTree");
        }else if ("toImport".equals(operate)){//导入
        	model.setViewName("platform/sysauth/sysResource/importSysResource");
		}
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-10-15 10:24:37
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysResource")
    @ResponseBody
    public DataMsg queryListSysResource(HttpServletRequest request, SysResourceDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysResourceDTO> list = service.searchSysResourceByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author
     * @description:新增
     * @date 2014-10-15 10:24:37
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysResource")
    @ResponseBody
    public DataMsg insertSysResource(HttpServletRequest request, SysResourceDTO dto, @ModelAttribute DataMsg dataMsg)throws Exception{
        try {
        	dto = (SysResourceDTO)super.initDto(dto);
        	dto.setParentIds(dto.getParentIds()+"/"+dto.getParentId());
        	dto.setVersion(1L);
        	dto.setValidateState("1");
			String url= jyptURL+"/api/platform/sysauth/SysResourceRest/create/v1";
			ResponseMsg<SysResourceDTO> responseMsg  = RestClient.doPost(jyptAppId, url, dto, new TypeReference<ResponseMsg<SysResourceDTO>>(){});
            dataMsg = super.initDataMsg(dataMsg);
            if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
				dataMsg.setMsg("新增成功");
				dataMsg.setData(treeNode(responseMsg.getResponseBody()));
			}else{
				dataMsg.setMsg("新增失败");
			}
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }

	private Map<String, String> treeNode(SysResourceDTO sysResourceDTO)throws Exception{
		Map<String, String>  map = new HashMap<String, String>();
		map.put("ID", sysResourceDTO.getId().toString());
		map.put("NAME", sysResourceDTO.getResoureName());
		map.put("TYPE", sysResourceDTO.getResoureType());
		map.put("PIDS", sysResourceDTO.getParentIds());
		map.put("PID", sysResourceDTO.getParentId()!=null?sysResourceDTO.getParentId().trim():"");
		return map;
	}
    
    /**
     * @author
     * @description:编辑
     * @date 2014-10-15 10:24:37
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysResource")
    @ResponseBody
    public DataMsg updateSysResource(HttpServletRequest request, SysResourceDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysResourceDTO)super.initDto(dto);
			String url= jyptURL+"/api/platform/sysauth/SysResourceRest/update/v1";
			ResponseMsg<SysResourceDTO> responseMsg = RestClient.doPost(jyptAppId, url, dto, new TypeReference<ResponseMsg<SysResourceDTO>>(){});
			if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
				dataMsg.setMsg("修改成功");
				dataMsg.setData(treeNode(responseMsg.getResponseBody()));
			}else{
				dataMsg.setMsg("修改失败");
			}
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:删除
     * @date 2014-10-15 10:24:37
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysResource")
    @ResponseBody
    public DataMsg deleteSysResource(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysResourceByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author
     * @description:通过主键查询 其明细信息
     * @date 2014-10-15 10:24:37
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysResourceDTO dto = service.querySysResourceByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    
    
    
    /**
     * 查询资源树
     * @author
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryTreeSysResource")
    @ResponseBody
    public List<Map<String, String>> queryTreeSysResource(HttpServletRequest request, SysResourceDTO dto,String type, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
    	dto.setValidateState("1");
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		String url= jyptURL+"/api/platform/sysauth/SysResourceRest/search/v1";
		ResponseMsg<QueryRespBean<SysResourceDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params, new TypeReference<ResponseMsg<QueryRespBean<SysResourceDTO>>>(){});
		List<SysResourceDTO> list  = responseMsg.getResponseBody().getResult();
        return treeData(list,type);
    }
    
    /**
     * 构建资源树数据
     * @param data
     * @param type为要排除的类型
     * @return
     */
    private List<Map<String, String>> treeData(List<SysResourceDTO> data,String type) throws Exception {
		if(data!=null && data.size()>0){
			List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
			for(SysResourceDTO resource: data){
				Map<String, String>  map = new HashMap<String, String>();
				map.put("ID", resource.getId().toString());
				map.put("NAME", resource.getResoureName());
				map.put("PID", resource.getParentId()!=null?resource.getParentId().trim():"");
				map.put("PIDS", resource.getParentIds()!=null?resource.getParentIds().trim():"");
				map.put("TYPE", resource.getResoureType());
				if (!StringUtils.isEmpty(type)&&type.equalsIgnoreCase(resource.getResoureType())) {
					continue;
				}
				maps.add(map);
			}
			return maps;
		}else{
			return null;
		}
	}
    
    
    /**
     * 导入资源
     * @author
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/importSysResource")
    @ResponseBody
    public DataMsg importSysResource(HttpServletRequest request,SysResourceDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
    	String url= jyptURL+"/"+dto.getResoureUrl();
		try {
			String key  = dto.getResoureUrl(); 
			if (dto.getResoureUrl() != null && !dto.getResoureUrl().startsWith("/")) {
				key = "/"+dto.getResoureUrl();
			}
			//模拟访问，使拦截器记录controller到JSP的映射路径
			int httpStatusCode = ResourceImportUtil.visitInterface(url, null);
			System.out.println("请求导入的URL地址返回的状态码："+httpStatusCode);
			
			
			String jspFile = ResourceInterceptor.controllerToJsp.get(key);		
			if(httpStatusCode!= 200 ||StringUtils.isEmpty(jspFile)) {
				dataMsg.setMsg("导入失败");
				return dataMsg;
			}
			List<SysResourceDTO> buttonResource = ResourceImportUtil.parseShiroTag(jspFile);
			dto.setResoureType("url");
			dto.setValidateState("1");
			dto.setVersion(1L);
			dto.setParentIds(dto.getParentIds()+"/"+dto.getParentId());
			service.saveImportSysResource(dto, buttonResource);
			dataMsg.setMsg("导入成功");
		} catch (Exception e) {
			dataMsg.setMsg("导入失败");
			dataMsg.failed(e.getMessage());
            logger.error("执行方法importSysResource异常：", e);
			
		}
        return dataMsg;
    }
    
}
