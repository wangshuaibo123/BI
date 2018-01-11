package com.fintech.modules.jbpm4.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.jbpm4.dto.Jbpm4RoleMappingDTO;
import com.fintech.platform.jbpm4.service.Jbpm4RoleMappingService;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;


/**
 * @classname: Jbpm4RoleMappingController
 * @description: 定义  工作流角色映射表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/jbpm4RoleMapping")
public class Jbpm4RoleMappingController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(Jbpm4RoleMappingController.class);

    @Autowired
    @Qualifier("com.fintech.platform.jbpm4.service.Jbpm4RoleMappingService")
    private Jbpm4RoleMappingService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("forward:/component/jbpm/roleMapping/queryJbpm4RoleMapping.jsp");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("forward:/component/jbpm/roleMapping/addJbpm4RoleMapping.jsp");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("forward:/component/jbpm/roleMapping/updateJbpm4RoleMapping.jsp");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("forward:/component/jbpm/roleMapping/viewJbpm4RoleMapping.jsp");
        }
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2015-06-05 10:32:34
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListJbpm4RoleMapping")
    @ResponseBody
    public DataMsg queryListJbpm4RoleMapping(HttpServletRequest request, Jbpm4RoleMappingDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<Jbpm4RoleMappingDTO> list = service.searchJbpm4RoleMappingByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author
     * @description:新增
     * @date 2015-06-05 10:32:34
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertJbpm4RoleMapping")
    @ResponseBody
    public DataMsg insertJbpm4RoleMapping(HttpServletRequest request, Jbpm4RoleMappingDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (Jbpm4RoleMappingDTO)super.initDto(dto);
        	
        	Map<String,Object> searchParams = new HashMap<String,Object>();
        	dto.setValidateState("1");
        	searchParams.put("dto", dto);
        	List<Jbpm4RoleMappingDTO> existLisst = service.searchJbpm4RoleMapping(searchParams);
        	if(null!=existLisst && existLisst.size()>0){
        		 dataMsg = super.initDataMsg(dataMsg);
        		 dataMsg.setStatus("failure");
                 dataMsg.setMsg("该角色映射已经存在！");                 
        	}else{
        		String roleCodes = dto.getMappingRoleCode();
        		if(StringUtils.isNotEmpty(roleCodes)){
        			String[] ss = roleCodes.split(",");
        			for(String roleCode: ss){
        				dto.setMappingRoleCode(roleCode);
        				searchParams.put("dto", dto);
        				List<Jbpm4RoleMappingDTO> exist = service.searchJbpm4RoleMapping(searchParams);
        				if(exist == null || exist.size() ==0)
        				service.insertJbpm4RoleMapping(dto);
        			}
        		}
        		
                
                dataMsg = super.initDataMsg(dataMsg);
                dataMsg.setMsg("新增成功");
                //dataMsg.setData(this.makeJSONData(dto.getId()));
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
     * @date 2015-06-05 10:32:34
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateJbpm4RoleMapping")
    @ResponseBody
    public DataMsg updateJbpm4RoleMapping(HttpServletRequest request, Jbpm4RoleMappingDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (Jbpm4RoleMappingDTO)super.initDto(dto);
           
            service.updateJbpm4RoleMapping(dto);
            
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
     * @date 2015-06-05 10:32:34
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteJbpm4RoleMapping")
    @ResponseBody
    public DataMsg deleteJbpm4RoleMapping(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteJbpm4RoleMappingByPrimaryKey(dto,ids);
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
     * @date 2015-06-05 10:32:34
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	Jbpm4RoleMappingDTO dto = service.queryJbpm4RoleMappingByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
