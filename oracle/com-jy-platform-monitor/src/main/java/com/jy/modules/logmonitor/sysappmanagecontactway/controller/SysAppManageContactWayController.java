package com.jy.modules.logmonitor.sysappmanagecontactway.controller;

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

import com.jy.modules.logmonitor.sysappmanagecontactway.dto.SysAppManageContactWayDTO;
import com.jy.modules.logmonitor.sysappmanagecontactway.service.SysAppManageContactWayService;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysAppManageContactWayController
 * @description: 定义  系统管理者联系方式 控制层
 * @author:  lei
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysAppManageContactWay")
public class SysAppManageContactWayController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysAppManageContactWayController.class);

    @Autowired
    @Qualifier("com.jy.modules.logmonitor.sysappmanagecontactway.service.SysAppManageContactWayService")
    private SysAppManageContactWayService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysappmanagecontactway/querySysAppManageContactWay");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysappmanagecontactway/addSysAppManageContactWay");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysappmanagecontactway/updateSysAppManageContactWay");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysappmanagecontactway/viewSysAppManageContactWay");
        }
        
        return model;
    }
    
    /**
     * @author lei
     * @description:查询分页列表
     * @date 2015-06-12 16:34:26
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysAppManageContactWay")
    @ResponseBody
    public DataMsg queryListSysAppManageContactWay(HttpServletRequest request, SysAppManageContactWayDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysAppManageContactWayDTO> list = service.searchSysAppManageContactWayByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author lei
     * @description:新增
     * @date 2015-06-12 16:34:26
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysAppManageContactWay")
    @ResponseBody
    public DataMsg insertSysAppManageContactWay(HttpServletRequest request, SysAppManageContactWayDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysAppManageContactWayDTO)super.initDto(dto);

            service.insertSysAppManageContactWay(dto);
            
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
     * @author lei
     * @description:编辑
     * @date 2015-06-12 16:34:26
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysAppManageContactWay")
    @ResponseBody
    public DataMsg updateSysAppManageContactWay(HttpServletRequest request, SysAppManageContactWayDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysAppManageContactWayDTO)super.initDto(dto);
           
            service.updateSysAppManageContactWay(dto);
            
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
     * @author lei
     * @description:删除
     * @date 2015-06-12 16:34:26
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysAppManageContactWay")
    @ResponseBody
    public DataMsg deleteSysAppManageContactWay(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysAppManageContactWayByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author lei
     * @description:通过主键查询 其明细信息
     * @date 2015-06-12 16:34:26
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysAppManageContactWayDTO dto = service.querySysAppManageContactWayByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
