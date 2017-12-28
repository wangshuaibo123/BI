package com.jy.modules.platform.sysauth.controller;

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
import com.jy.modules.platform.sysauth.dto.SysAclDTO;
import com.jy.modules.platform.sysauth.dto.SysResourceDTO;
import com.jy.modules.platform.sysauth.dto.SysRoleDTO;
import com.jy.modules.platform.sysauth.service.SysAclService;
import com.jy.modules.platform.sysauth.service.SysRoleService;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restclient.http.RestClient;
import com.jy.platform.restclient.http.RestService;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysAclController
 * @description: 定义  操作权限控制表 控制层
 * @author:  chen_gang
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysAcl")
public class SysAclController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysAclController.class);

 	private String jyptAppId = "jypt"; //rest服务appId
   	private String jyptURL = RestService.getServiceUrl(jyptAppId);//rest服务地址
    
    @Autowired
    @Qualifier("com.jy.modules.platform.sysauth.service.SysAclService")
    private SysAclService service;
    
    @Autowired
    @Qualifier("com.jy.modules.platform.sysauth.service.SysRoleService")
    private SysRoleService roleBiz;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysauth/sysAcl/querySysAcl");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysauth/sysAcl/addSysAcl");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysauth/sysAcl/updateSysAcl");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysauth/sysAcl/viewSysAcl");
        }
        
        return model;
    }
    
    /**
     * @author chen_gang
     * @description:查询分页列表
     * @date 2014-10-15 10:23:44
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysAcl")
    @ResponseBody
    public DataMsg queryListSysAcl(HttpServletRequest request, SysAclDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysAclDTO> list = service.searchSysAclByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author chen_gang
     * @description:新增
     * @date 2014-10-15 10:23:44
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysAcl")
    @ResponseBody
    public DataMsg insertSysAcl(HttpServletRequest request, SysAclDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysAclDTO)super.initDto(dto);

            service.insertSysAcl(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author chen_gang
     * @description:编辑
     * @date 2014-10-15 10:23:44
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysAcl")
    @ResponseBody
    public DataMsg updateSysAcl(HttpServletRequest request, SysAclDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysAclDTO)super.initDto(dto);
           
            service.updateSysAcl(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author chen_gang
     * @description:删除
     * @date 2014-10-15 10:23:44
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysAcl")
    @ResponseBody
    public DataMsg deleteSysAcl(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysAclByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author chen_gang
     * @description:通过主键查询 其明细信息
     * @date 2014-10-15 10:23:44
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysAclDTO dto = service.querySysAclByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    
    
    /**
     * 为角色添加资源
     * @author zhanglin
     * @date 20141027
     * @param request
     * @param roleid
     * @param resoureids
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/saveSysAcl")
    @ResponseBody
    public DataMsg saveSysAcl(HttpServletRequest request, String roleId,String resourceIds, @ModelAttribute DataMsg dataMsg){
        try {
//        	if(StringUtils.isEmpty(resourceIds)||StringUtils.isEmpty(resourceIds)){
//        		 dataMsg.setMsg("保存失败");
//        	}else {
        		boolean result = service.saveSysAcl(roleId, resourceIds);
        		if(result){
        			dataMsg.setMsg("保存成功");
        		}else{
        			dataMsg.setMsg("保存失败");
        		}
//			}
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    } 
    /**
     * 根据角色ID查询所有资源
     * @author zhanglin
     * @date 20141027
     * @param request
     * @param roleId
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/querySysAclByRoleId")
    @ResponseBody
    public List<Map<String, String>>  querySysAclByRoleId(HttpServletRequest request,String roleId, @ModelAttribute DataMsg dataMsg) throws Exception {
        String oldResourseId = "";
        List<String> data = new ArrayList<String>();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(roleId)){
            
            String[] roleIds = roleId.split(",");
            for(String id :roleIds){
                SysRoleDTO role = roleBiz.querySysRoleByPrimaryKey(id);
                Map<String, Object> searchParams = new HashMap<String, Object>();
                SysAclDTO dto = new SysAclDTO();
                dto.setRoleId(Long.parseLong(id));
                searchParams.put("dto", dto);
                List<SysAclDTO> list = service.searchSysAcl(searchParams);
                
                for (SysAclDTO sysAclDTO: list) {
                    if(role.getRoleCode().startsWith("SUB_BASE") && roleId.contains(",")){
                        data.remove(sysAclDTO.getResoureId().toString());
                    }else{
                        data.add(sysAclDTO.getResoureId().toString());
                    }
                }
            }
            for(String str:data){
                if(oldResourseId.length() >0){
                    oldResourseId = oldResourseId+str+",";
                }else{
                    oldResourseId = str+",";
                }
                
            }
            if(oldResourseId.length() > 0){
                oldResourseId = oldResourseId.substring(0,oldResourseId.length()-1);
            }
        }
        
        return loadTreeData(dataMsg,oldResourseId);
    }
    
    /**
     * 加载资源树数据
     * @param dataMsg
     * @return
     */
    private List<Map<String, String>> loadTreeData(DataMsg dataMsg,String checked){
    	List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
    	Map<String, Object>  searchParams= new HashMap<String, Object>();
    	SysResourceDTO dto = new SysResourceDTO();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		String url= jyptURL+"/api/platform/sysauth/SysResourceRest/search/v1";
		ResponseMsg<QueryRespBean<SysResourceDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params, new TypeReference<ResponseMsg<QueryRespBean<SysResourceDTO>>>(){});
		List<SysResourceDTO> resourses  = responseMsg.getResponseBody().getResult();
		
		if(resourses!=null && resourses.size()>0){
				for(SysResourceDTO resource: resourses){
					Map<String, String>  map = new HashMap<String, String>();
					map.put("ID", resource.getId().toString());
					map.put("NAME", resource.getResoureName());
					map.put("PID", resource.getParentId()!=null?resource.getParentId().trim():"");
					map.put("PIDS", resource.getParentIds()!=null?resource.getParentIds().trim():"");
					map.put("OPEN", "true");//默认展开树
					if(!StringUtils.isEmpty(checked)){
						String [] tmp = checked.split(",");
						for (int i = 0; i < tmp.length; i++) {
							if(Long.parseLong(tmp[i]) ==  resource.getId()){
								map.put("checked", "true");
							}
						}
					}
					maps.add(map);
				}
			}
			
		return maps;
    }
    
    
    /**
     * 变更基础资源
     * @param request
     * @param roleId
     * @param resourceIds
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/saveBaseAcl")
    @ResponseBody
    public DataMsg saveBaseAcl(HttpServletRequest request, String userId,String resourceIds, @ModelAttribute DataMsg dataMsg){
        try {
            String roleCode = "ADD_BASE_"+userId;
            service.saveBaseAcl(userId, resourceIds,roleCode);
            dataMsg.setMsg("保存成功");
        }catch (Exception e) {
            dataMsg.setMsg("保存失败");
            dataMsg.failed(e.getMessage());
            logger.error("执行方法saveBaseAcl异常：", e);
        }
        return dataMsg;
    }
    
    /**
     * 变更基础资源
     * @param request
     * @param roleId
     * @param resourceIds
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/subBaseAcl")
    @ResponseBody
    public DataMsg subBaseAcl(HttpServletRequest request, String userId,String resourceIds, @ModelAttribute DataMsg dataMsg){
        try {
            String roleCode = "SUB_BASE_"+userId;
            service.saveBaseAcl(userId, resourceIds,roleCode);
            dataMsg.setMsg("保存成功");
        }catch (Exception e) {
            dataMsg.setMsg("保存失败");
            dataMsg.failed(e.getMessage());
            logger.error("执行方法saveBaseAcl异常：", e);
        }
        return dataMsg;
    }
    
}
