package com.fintech.modules.platform.sysorg.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.platform.sysorg.dto.SysOrgDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserDTO;
import com.fintech.modules.platform.sysorg.dto.SysPositionDTO;
import com.fintech.modules.platform.sysorg.service.SysOrgService;
import com.fintech.modules.platform.sysorg.service.SysOrgUserService;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
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
 * @classname: SysOrgController
 * @description: 定义  机构部门表 控制层
 * @author
 */
@SuppressWarnings("all")
@Controller
@Scope("prototype")
@RequestMapping("/sysOrg")
public class SysOrgController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysOrgController.class);
	private String jyptAppId = "ptpt"; //rest服务appId
	private String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);//rest服务地址

    @Autowired
    private SysOrgService service;
    
    @Autowired
    private SessionAPI sessionAPI;
    
    @Autowired
    private SysOrgUserService orgUserService;
    
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
        	model.setViewName("platform/sysorg/sysOrg/querySysOrg");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysorg/sysOrg/addSysOrg");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysorg/sysOrg/updateSysOrg");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysorg/sysOrg/viewSysOrg");
        }else if("toTreePage".equals(operate)){//跳转至树形管理界面
        	model.setViewName("platform/sysorg/sysOrg/treeSysOrg");
        }else if("toSelectPage".equals(operate)){//跳转至树形选择界面
        	model.setViewName("platform/sysorg/sysOrg/selectTreeSysOrg");
        }else if("toUpdateForList".equals(operate)){
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysorg/sysOrg/updateSysOrgForList");
        }else if("toViewForList".equals(operate)){
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysorg/sysOrg/viewSysOrgForList");
        }
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-10-15 10:26:06
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
/*    @RequestMapping(value = "/queryListSysOrg")
    @ResponseBody
    public DataMsg queryListSysOrg(HttpServletRequest request, SysOrgDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
        List<SysOrgDTO> list = service.searchSysOrgByPaging(params.getSearchParams());
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }*/
    
    /**Description: 查询tree
     * Create Date: 2014年10月17日上午11:47:30<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryTreeSysOrg")
    @ResponseBody
    public List<Map<String, String>> queryTreeSysOrg(HttpServletRequest request, SysOrgDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	String orgId = request.getParameter("orgId");
    	String trace = request.getParameter("trace");//追溯方向 ：向下追溯down  向上追溯  up  与 orgId配合使用
    	
    	dto.setAppFlag(request.getParameter("dto.appFlag"));
    	dto.setIsVirtual(request.getParameter("dto.isVirtual"));

    	dto.setValidateState("1");//限制选中的是有效的数据 TODO
    	searchParams.put("dto", dto);
    	searchParams.put("orgId", orgId);
    	searchParams.put("trace", trace);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		String url= jyptURL+"/api/platform/SysOrgRest/search/v1";
		ResponseMsg<QueryRespBean<SysOrgDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params, new TypeReference<ResponseMsg<QueryRespBean<SysOrgDTO>>>(){});
		List<SysOrgDTO> list  = responseMsg.getResponseBody().getResult();
    	String openParentId = request.getParameter("openParentId");
        return treeData(list, openParentId);//组织树的数据
    }

    
    /**Description: 根据当前用户orgId获取机构树
     * Create Date: 2014年10月17日上午11:47:30<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryTreeSysOrgByCurrentUserOrg")
    @ResponseBody
    public List<Map<String, String>> queryTreeSysOrgByCurrentUserOrg(HttpServletRequest request, SysOrgDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	String trace = request.getParameter("trace");//追溯方向 ：向下追溯down  向上追溯  up  与 orgId配合使用
    	// 获取当前登录Session中用户信息
    	UserInfo user=sessionAPI.getCurrentUserInfo();
    	dto.setAppFlag(request.getParameter("dto.appFlag"));
    	dto.setIsVirtual(request.getParameter("dto.isVirtual"));

    	dto.setValidateState("1");//限制选中的是有效的数据 TODO
    	searchParams.put("dto", dto);
    	searchParams.put("orgId", user.getOrgParentId()!=null&&!user.getOrgParentId().equals("0")?user.getOrgParentId():"");
    	searchParams.put("trace", trace);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		String url= jyptURL+"/api/platform/SysOrgRest/search/v1";
		ResponseMsg<QueryRespBean<SysOrgDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params, new TypeReference<ResponseMsg<QueryRespBean<SysOrgDTO>>>(){});
		List<SysOrgDTO> list  = responseMsg.getResponseBody().getResult();
    	String openParentId = request.getParameter("openParentId");
        return treeData(list, openParentId);//组织树的数据
    }
    
    /**
     * @author
     * @description:新增
     * @date 2014-10-15 10:26:06
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysOrg")
    @ResponseBody
    public DataMsg insertSysOrg(HttpServletRequest request, SysOrgDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysOrgDTO)super.initDto(dto);
        	//设置版本号
        	dto.setVersion(SysOrgDTO.generatorVersion());
        	//默认有效
        	dto.setValidateState("1");
        	dto.setCreateTime(new Date());
        	if(StringUtils.isNotBlank(dto.getEd())){
        		dto.setEffectiveDate(sdf.parse(dto.getEd()));
        	}
        	if(dto.getEffectiveDate() == null){
        		dto.setEffectiveDate(new Date());
        	}
			String url= jyptURL+"/api/platform/SysOrgRest/create/v1";
			ResponseMsg<SysOrgDTO> responseMsg  = RestClient.doPost(jyptAppId, url, dto, new TypeReference<ResponseMsg<SysOrgDTO>>(){});
            dataMsg = super.initDataMsg(dataMsg);
            if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
				dataMsg.setMsg("新增成功");
				dataMsg.setData(treeNode(responseMsg.getResponseBody()));//TODO
			}else{
				dataMsg.setMsg("新增失败");
			}
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysOrg异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:编辑
     * @date 2014-10-15 10:26:06
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysOrg")
    @ResponseBody
    public DataMsg updateSysOrg(HttpServletRequest request, SysOrgDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysOrgDTO)super.initDto(dto);
        	//设置版本号
        	dto.setVersion(SysPositionDTO.generatorVersion());
        	if(StringUtils.isNotBlank(dto.getEd())){
        		dto.setEffectiveDate(sdf.parse(dto.getEd()));
        	}
        	if(dto.getEffectiveDate() == null){
        		dto.setEffectiveDate(new Date());
        	}
			String url= jyptURL+"/api/platform/SysOrgRest/update/v1";
			ResponseMsg<SysOrgDTO> responseMsg = RestClient.doPost(jyptAppId, url, dto, new TypeReference<ResponseMsg<SysOrgDTO>>(){});
			if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
				dataMsg.setMsg("保存成功");
				dataMsg.setData(treeNode(responseMsg.getResponseBody()));//TODO
			}else{
				dataMsg.setMsg("保存失败");
			}
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysOrg异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:删除
     * @date 2014-10-15 10:26:06
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysOrg")
    @ResponseBody
    public DataMsg deleteSysOrg(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
				//判断是否存机构用户关联
				Map<String, Object> paramMap = new HashMap<String, Object>();
				SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
				sysOrgUserDTO.setOrgId(Long.valueOf(ids));
				paramMap.put("dto", sysOrgUserDTO);
				List<SysOrgUserDTO> list = orgUserService.searchSysOrgUser(paramMap);
		        if(list.size()>0){
		        	dataMsg.setMsg("无法删除,机构中存在用户!");
		        	dataMsg.setStatus(dataMsg.STATUS_FAILED);
		        	return dataMsg;
		        }
		        String url= jyptURL+"/api/platform/SysOrgRest/delete/v1/"+ids;
				ResponseMsg<Void> responseMsg = RestClient.doGet(jyptAppId, url, new TypeReference<ResponseMsg<Void>>(){});
				if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
					dataMsg.setMsg("删除成功");
				}else{
					dataMsg.setMsg("删除失败");
				}
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysOrg异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author
     * @description:通过主键查询 其明细信息
     * @date 2014-10-15 10:26:06
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
			 String url= jyptURL+"/api/platform/SysOrgRest/get/v1/"+id;
			 ResponseMsg<SysOrgDTO> responseMsg = RestClient.doGet(jyptAppId, url, new TypeReference<ResponseMsg<SysOrgDTO>>(){});
			 SysOrgDTO dto = responseMsg.getResponseBody();
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    
	/**Description: 转换成树节点集合
	 * Create Date: 2014年10月12日下午9:47:06<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param data
	 * @return
	 */
	private List<Map<String, String>> treeData(List<SysOrgDTO> data , String openParentId) {
		List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
		if(data!=null && data.size()>0){
			for(SysOrgDTO sysOrg: data){
				Map<String, String>  map = new HashMap<String, String>();
				map.put("ID", sysOrg.getId().toString());
				map.put("NAME", sysOrg.getOrgName());
				map.put("PID", sysOrg.getParentId().trim());
				map.put("CODE", sysOrg.getOrgCode().trim());
				if( openParentId!= null && openParentId.equals(sysOrg.getParentId().trim()) ){
					map.put("open", "true");
				}
				maps.add(map);
			}
			return maps;
		}else{
			Map<String, String>  map = new HashMap<String, String>();
			map.put("NAME", "暂无数据");
			maps.add(map);
			
			return maps;
		}
	}
	
	/**Description: 转换成树节点
	 * Create Date: 2014年10月12日下午9:47:06<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param data
	 * @return
	 */
	private Map<String, String> treeNode(SysOrgDTO sysOrg){
		Map<String, String>  map = new HashMap<String, String>();
		map.put("ID", sysOrg.getId().toString());
		map.put("NAME", sysOrg.getOrgName());
		map.put("PID", sysOrg.getParentId().trim());
		return map;
	}
	
	/**Description: 查询机构列表
     * Create Date: 2015年08月21日下午14:47:30<br/>
     * Author     : xianwu.jiang <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/queryListSysOrg")
    @ResponseBody
    public DataMsg queryListSysOrg(HttpServletRequest request, SysOrgDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		try {
			String url= jyptURL+"/api/platform/SysOrgRest/searchByPage/v1";
			ResponseMsg<QueryRespBean<SysOrgDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params, new TypeReference<ResponseMsg<QueryRespBean<SysOrgDTO>>>(){});
			List<SysOrgDTO> list  = responseMsg.getResponseBody().getResult();
			dataMsg.setData(list);
			dataMsg.setTotalRows(responseMsg.getResponseBody().getPageParameter().getTotalCount());
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
        	logger.error("执行方法queryListSysOrg异常：", e);
		}
        return dataMsg;
    }
	/**
	 * @description:通过orgLevel获取机构列表信息
	 * @author
	 * @date: 2016年7月26日 下午8:31:09
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTreeSysOrgByOrgLevel")
    @ResponseBody
    public List<Map<String, String>> queryTreeSysOrgByOrgLevel(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) throws Exception {
    	String orgLevel = this.getParameterString("orgLevel");//1、2、3
    	String orgType = this.getParameterString("orgType");//org、dept
    	String parentOrgId = this.getParameterString("parentOrgId");//0、
    	
    	String orgId = this.getParameterString("orgId");//0、
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("orgLevel", orgLevel);
    	searchParams.put("orgType", orgType);
    	searchParams.put("parentId", parentOrgId);
    	searchParams.put("orgId", orgId);
    	
    	/*QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);*/
		
		List<SysOrgDTO> list  = service.queryTreeSysOrgByOrgLevel(searchParams);//responseMsg.getResponseBody().getResult();
    	String openParentId = request.getParameter("openParentId");
        return treeData(list, openParentId);//组织树的数据
    }
}
