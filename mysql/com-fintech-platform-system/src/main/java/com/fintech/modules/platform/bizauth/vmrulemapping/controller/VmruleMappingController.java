package com.fintech.modules.platform.bizauth.vmrulemapping.controller;

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

import com.fintech.modules.platform.bizauth.vmrulemapping.dto.VmruleMappingDTO;
import com.fintech.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService;
import com.fintech.modules.platform.sysauth.dto.SysRoleUserDTO;
import com.fintech.modules.platform.sysauth.service.SysRoleUserService;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: VmruleMappingController
 * @description: 定义  映射表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/vmruleMapping")
public class VmruleMappingController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(VmruleMappingController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService")
    private VmruleMappingService service;
    @Autowired
    private SessionAPI sessionAPI;
    
	@Autowired
	@Qualifier("com.fintech.modules.platform.sysauth.service.SysRoleUserService")
	private SysRoleUserService sysRoleUserService;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    @ResponseBody
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	String orgtype=(String)this.getParameter("orgtype");
        	model.addObject("orgtype", orgtype);
        	model.setViewName("platform/bizauth/vmrulemapping/queryVmruleMapping");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	String orgtype=(String)this.getParameter("orgtype");
        	model.addObject("orgtype", orgtype);
        	model.setViewName("platform/bizauth/vmrulemapping/addVmruleMapping");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/bizauth/vmrulemapping/updateVmruleMapping");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/bizauth/vmrulemapping/viewVmruleMapping");
        }
        
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2015-01-16 17:14:38
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListVmruleMapping")
    @ResponseBody
    public DataMsg queryListVmruleMapping(HttpServletRequest request, VmruleMappingDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	UserInfo crrentUser=sessionAPI.getCurrentUserInfo();
    	searchParams.put("dto", dto);
    	//获取当前用户管理的机构
		Map<String, Object> userParam=new HashMap<String, Object>();
		userParam.put("orgId", crrentUser.getOrgId());
		userParam.put("userId", crrentUser.getUserId());
		SysRoleUserDTO sDto=sysRoleUserService.findSysRoleOrgByCurrentUser(userParam);
		//获取当前用户管理的机构
		
		
		if(sDto!=null)
			searchParams.put("startOrg", sDto.getTargetId());
		else{
			if(crrentUser.getOrgParentId()!=null&&!crrentUser.getOrgParentId().equals("0"))
	    		searchParams.put("startOrg", crrentUser.getOrgId());
		}
    	String orgtype=(String)this.getParameter("orgtype");
    	searchParams.put("vmTableName", orgtype+"_VMRULE_MAPPING");
    	searchParams.put("code", orgtype);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		List<VmruleMappingDTO> list = null;
		try {
		   list = service.searchVmruleMappingByPaging(params.getSearchParams());
        }catch (Exception e) {
            // TODO: handle exception
            dataMsg.failed("查询" + orgtype + "的映射关系错误，请在'操作权限->资源管理'下修改!");
            dataMsg.setStatus(DataMsg.STATUS_FAILED);
            logger.error("执行方法queryListVmruleMapping异常：", e);
            dataMsg.setData(null);
            dataMsg.setTotalRows(0);
            return dataMsg;
        }
        
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author
     * @description:新增
     * @date 2015-01-16 17:14:38
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertVmruleMapping")
    @ResponseBody
    public DataMsg insertVmruleMapping(HttpServletRequest request, VmruleMappingDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (VmruleMappingDTO)super.initDto(dto);
        	Map<String,Object> searchParams = new HashMap<String, Object>();
        	searchParams.put("vmTableName",dto.getOrgType() + "_" + "VMRULE_MAPPING");
        	searchParams.put("dto", dto);
        	List<VmruleMappingDTO> vmruleMappingDTO=service.searchVmruleMapping(searchParams);
        	if(vmruleMappingDTO!=null && vmruleMappingDTO.size()!=0)
        	{
        		dataMsg.failed("该授权已经存在，请重新选择添加");
        		dataMsg.setStatus(DataMsg.STATUS_FAILED);
        	}else{
        		dto.setCreateBy(dto.getOpUserId());
                service.insertVmruleMapping(dto);
                dataMsg = super.initDataMsg(dataMsg);
                dataMsg.setMsg("新增成功");
                dataMsg.setData(this.makeJSONData(dto.getId()));
        	}
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:编辑
     * @date 2015-01-16 17:14:38
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateVmruleMapping")
    @ResponseBody
    public DataMsg updateVmruleMapping(HttpServletRequest request, VmruleMappingDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (VmruleMappingDTO)super.initDto(dto);
           
            service.updateVmruleMapping(dto);
            
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
     * @author
     * @description:删除
     * @date 2015-01-16 17:14:38
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteVmruleMapping")
    @ResponseBody
    public DataMsg deleteVmruleMapping(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	String orgtype = (String)this.getParameter("orgtype");
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			service.deleteVmruleMappingByPrimaryKey(dto,ids,orgtype);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    
    @RequestMapping(value = "/fulshVmruleMapping")
    @ResponseBody
    public DataMsg fulshVmruleMapping(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	String orgtype = (String)this.getParameter("orgtype");
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			service.fulshVmruleMapping(ids,orgtype);
			 dataMsg.setMsg("刷新数据权限成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        return dataMsg;
    }
    
    
    
    /**
     * 清理离职人员的映射以及业务数据权限
     * @param request
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/cleanVmruleMapping")
    @ResponseBody
    public DataMsg cleanVmruleMapping(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){

    	String orgtype = (String)this.getParameter("orgtype");
    	dataMsg = super.initDataMsg(dataMsg);
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	UserInfo crrentUser=sessionAPI.getCurrentUserInfo();
    	//获取当前用户管理的机构
    	Map<String, Object> userParam=new HashMap<String, Object>();
    	userParam.put("orgId", crrentUser.getOrgId());
    	userParam.put("userId", crrentUser.getUserId());
    	SysRoleUserDTO sDto=sysRoleUserService.findSysRoleOrgByCurrentUser(userParam);
    	//获取当前用户管理的机构


    	if(sDto!=null)
    		searchParams.put("startOrg", sDto.getTargetId());
    	else{
    		searchParams.put("startOrg", crrentUser.getOrgId());
    	}
    	searchParams.put("orgType", orgtype);
    	try {
    		service.modifyCleanVmruleMapping(searchParams);
    		dataMsg.setMsg("刷新数据权限成功");
    	} catch (Exception e) {
    		dataMsg.failed(e.getMessage());
    		logger.error("执行方法deleteSysResource异常：", e);

    	}
    	return dataMsg;
    }
    
    
    
    
    /**
     * @author
     * @description:通过主键查询 其明细信息
     * @date 2015-01-16 17:14:38
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	VmruleMappingDTO dto = service.queryVmruleMappingByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
