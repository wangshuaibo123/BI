package com.jy.modules.platform.sysauth.controller;

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

import com.jy.modules.platform.sysauth.dto.SysRoleGroupRoleDTO;
import com.jy.modules.platform.sysauth.service.SysRoleGroupRoleService;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysRoleGroupRoleController
 * @description: 定义  角色组角色中间表 控制层
 * @author:  yuchengyang-pc
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysRoleGroupRole")
public class SysRoleGroupRoleController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysRoleGroupRoleController.class);

    @Autowired
    @Qualifier("com.jy.modules.platform.sysrolegrouprole.service.SysRoleGroupRoleService")
    private SysRoleGroupRoleService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysrolegrouprole/querySysRoleGroupRole");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysrolegrouprole/addSysRoleGroupRole");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysrolegrouprole/updateSysRoleGroupRole");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysrolegrouprole/viewSysRoleGroupRole");
        }
        
        return model;
    }
    
    /**
     * @author yuchengyang-pc
     * @description:查询分页列表
     * @date 2014-11-28 17:38:38
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysRoleGroupRole")
    @ResponseBody
    public DataMsg queryListSysRoleGroupRole(HttpServletRequest request, SysRoleGroupRoleDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysRoleGroupRoleDTO> list = service.searchSysRoleGroupRoleByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author yuchengyang-pc
     * @description:新增
     * @date 2014-11-28 17:38:38
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysRoleGroupRole")
    @ResponseBody
    public DataMsg insertSysRoleGroupRole(HttpServletRequest request, SysRoleGroupRoleDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysRoleGroupRoleDTO)super.initDto(dto);

            service.insertSysRoleGroupRole(dto);
            
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
     * @author yuchengyang-pc
     * @description:编辑
     * @date 2014-11-28 17:38:38
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysRoleGroupRole")
    @ResponseBody
    public DataMsg updateSysRoleGroupRole(HttpServletRequest request, SysRoleGroupRoleDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysRoleGroupRoleDTO)super.initDto(dto);
           
            service.updateSysRoleGroupRole(dto);
            
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
     * @author yuchengyang-pc
     * @description:删除
     * @date 2014-11-28 17:38:38
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysRoleGroupRole")
    @ResponseBody
    public DataMsg deleteSysRoleGroupRole(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysRoleGroupRoleByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author yuchengyang-pc
     * @description:通过主键查询 其明细信息
     * @date 2014-11-28 17:38:38
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysRoleGroupRoleDTO dto = service.querySysRoleGroupRoleByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
