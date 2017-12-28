package com.jy.modules.platform.sysbizlog.controller;

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

import com.jy.modules.platform.sysbizlog.dto.SysBizLogDTO;
import com.jy.modules.platform.sysbizlog.service.SysBizLogService;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysBizLogController
 * @description: 定义  业务日志表 控制层
 * @author:  chen_gang
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysBizLog")
public class SysBizLogController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysBizLogController.class);

    @Autowired
    @Qualifier("com.jy.modules.platform.sysbizlog.service.SysBizLogService")
    private SysBizLogService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysbizlog/querySysBizLog");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysbizlog/addSysBizLog");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysbizlog/updateSysBizLog");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysbizlog/viewSysBizLog");
        }
        
        return model;
    }
    
    /**
     * @author chen_gang
     * @description:查询分页列表
     * @date 2014-10-15 16:30:12
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysBizLog")
    @ResponseBody
    public DataMsg queryListSysBizLog(HttpServletRequest request, SysBizLogDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysBizLogDTO> list = service.searchSysBizLogByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author chen_gang
     * @description:新增
     * @date 2014-10-15 16:30:12
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysBizLog")
    @ResponseBody
    public DataMsg insertSysBizLog(HttpServletRequest request, SysBizLogDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysBizLogDTO)super.initDto(dto);

            service.insertSysBizLog(dto);
            
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
     * @date 2014-10-15 16:30:12
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysBizLog")
    @ResponseBody
    public DataMsg updateSysBizLog(HttpServletRequest request, SysBizLogDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysBizLogDTO)super.initDto(dto);
           
            service.updateSysBizLog(dto);
            
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
     * @date 2014-10-15 16:30:12
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysBizLog")
    @ResponseBody
    public DataMsg deleteSysBizLog(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysBizLogByPrimaryKey(dto,ids);
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
     * @date 2014-10-15 16:30:12
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysBizLogDTO dto = service.querySysBizLogByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
