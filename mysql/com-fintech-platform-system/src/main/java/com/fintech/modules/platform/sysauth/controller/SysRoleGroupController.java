package com.fintech.modules.platform.sysauth.controller;

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

import com.fintech.modules.platform.sysauth.dto.SysRoleGroupDTO;
import com.fintech.modules.platform.sysauth.dto.SysRoleGroupRoleDTO;
import com.fintech.modules.platform.sysauth.service.SysRoleGroupRoleService;
import com.fintech.modules.platform.sysauth.service.SysRoleGroupService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysRoleGroupController
 * @description: 定义  角色组 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysRoleGroup")
public class SysRoleGroupController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysRoleGroupController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.sysrolegroup.service.SysRoleGroupService")
    private SysRoleGroupService service;
    
    @Autowired
    @Qualifier("com.fintech.modules.platform.sysrolegrouprole.service.SysRoleGroupRoleService")
    SysRoleGroupRoleService sysRoleGroupRoleService;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysauth/sysrolegroup/querySysrolegroup");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysauth/sysrolegroup/addSysrolegroup");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysauth/sysrolegroup/updateSysrolegroup");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysauth/sysrolegroup/viewSysrolegroup");
        }
        
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-11-28 15:38:04
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysRoleGroup")
    @ResponseBody
    public DataMsg queryListSysRoleGroup(HttpServletRequest request, SysRoleGroupDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysRoleGroupDTO> list = service.searchSysRoleGroupByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    
    
    @RequestMapping(value = "/searchSysRoleGroup")
    @ResponseBody
    public DataMsg searchSysRoleGroup(HttpServletRequest request, SysRoleGroupDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> data = new HashMap<String, Object>();
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	String roleId = request.getParameter("roleId");
    	if(roleId!=null && !"".equals(roleId)){
        	Map<String, Object> params = new HashMap<String, Object>();
    		SysRoleGroupRoleDTO param = new SysRoleGroupRoleDTO();
    		param.setRoleId(Long.parseLong(roleId));
    		params.put("dto", param);
    		List<SysRoleGroupRoleDTO> groupList= sysRoleGroupRoleService.searchSysRoleGroupRole(params);
    		if(groupList!=null && groupList.size()>0){
    	        data.put("group", groupList.get(0));
    		}
    	}
    	searchParams.put("dto", dto);
        List<SysRoleGroupDTO> list = service.searchSysRoleGroup(searchParams);
        data.put("list", list);
        dataMsg.setData(data);
        return dataMsg;
    }
    

    /**
     * @author
     * @description:新增
     * @date 2014-11-28 15:38:04
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysRoleGroup")
    @ResponseBody
    public DataMsg insertSysRoleGroup(HttpServletRequest request, SysRoleGroupDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysRoleGroupDTO)super.initDto(dto);

            service.insertSysRoleGroup(dto);
            
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
     * @author
     * @description:编辑
     * @date 2014-11-28 15:38:04
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysRoleGroup")
    @ResponseBody
    public DataMsg updateSysRoleGroup(HttpServletRequest request, SysRoleGroupDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysRoleGroupDTO)super.initDto(dto);
           
            service.updateSysRoleGroup(dto);
            
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
     * @date 2014-11-28 15:38:04
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysRoleGroup")
    @ResponseBody
    public DataMsg deleteSysRoleGroup(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysRoleGroupByPrimaryKey(dto,ids);
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
     * @date 2014-11-28 15:38:04
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysRoleGroupDTO dto = service.querySysRoleGroupByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
