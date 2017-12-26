package com.fintech.modules.platform.sysindustry.controller;

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

import com.fintech.modules.platform.sysindustry.dto.SysIndustryDTO;
import com.fintech.modules.platform.sysindustry.service.SysIndustryService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysIndustryController
 * @description: 定义  sys_industry 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysIndustry")
public class SysIndustryController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysIndustryController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.sysindustry.service.SysIndustryService")
    private SysIndustryService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysindustry/querySysIndustry");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysindustry/addSysIndustry");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysindustry/updateSysIndustry");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysindustry/viewSysIndustry");
        }
        
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-12-04 14:03:34
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysIndustry")
    @ResponseBody
    public DataMsg queryListSysIndustry(HttpServletRequest request, SysIndustryDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysIndustryDTO> list = service.searchSysIndustryByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    
    
    @RequestMapping(value = "/queryPositionByIndustry/{defaultCode}")
    @ResponseBody
    public DataMsg queryPositionByIndustry(HttpServletRequest request, @PathVariable String defaultCode, @ModelAttribute DataMsg dataMsg) throws Exception {
    	List<Map> list = service.queryPositionByIndustry(defaultCode);
        dataMsg.setData(list);
        return dataMsg;
    }
    
    

    /**
     * @author
     * @description:新增
     * @date 2014-12-04 14:03:34
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysIndustry")
    @ResponseBody
    public DataMsg insertSysIndustry(HttpServletRequest request, SysIndustryDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysIndustryDTO)super.initDto(dto);

            service.insertSysIndustry(dto);
            
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
     * @date 2014-12-04 14:03:34
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysIndustry")
    @ResponseBody
    public DataMsg updateSysIndustry(HttpServletRequest request, SysIndustryDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysIndustryDTO)super.initDto(dto);
           
            service.updateSysIndustry(dto);
            
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
     * @date 2014-12-04 14:03:34
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysIndustry")
    @ResponseBody
    public DataMsg deleteSysIndustry(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysIndustryByPrimaryKey(dto,ids);
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
     * @date 2014-12-04 14:03:34
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysIndustryDTO dto = service.querySysIndustryByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
