package com.fintech.modules.platform.sysasynjob.controller;

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

import com.fintech.modules.platform.sysasynjob.dto.SysAsynJobDTO;
import com.fintech.modules.platform.sysasynjob.service.SysAsynJobService;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysAsynJobController
 * @description: 定义  异步接口任务表 控制层
 * @author:  DELL
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysAsynJob")
public class SysAsynJobController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysAsynJobController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.sysasynjob.service.SysAsynJobService")
    private SysAsynJobService service;
    @Autowired
    private SessionAPI sessionAPI;
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysasynjob/querySysAsynJob");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysasynjob/addSysAsynJob");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysasynjob/updateSysAsynJob");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysasynjob/viewSysAsynJob");
        }
        
        return model;
    }
    
    /**
     * @author DELL
     * @description:查询分页列表
     * @date 2016-09-12 14:55:26
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysAsynJob")
    @ResponseBody
    public DataMsg queryListSysAsynJob(HttpServletRequest request, SysAsynJobDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
    	String startTimeTS =this.getParameterString("startTimeTS");
    	String endTimeTS = this.getParameterString("endTimeTS");
    	
		searchParams.put("startTime", startTimeTS);
    	searchParams.put("endTime", endTimeTS);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysAsynJobDTO> list = service.searchSysAsynJobByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author DELL
     * @description:新增
     * @date 2016-09-12 14:55:26
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysAsynJob")
    @ResponseBody
    public DataMsg insertSysAsynJob(HttpServletRequest request, SysAsynJobDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysAsynJobDTO)super.initDto(dto);

            service.insertSysAsynJob(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysAsynJob异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author DELL
     * @description:编辑
     * @date 2016-09-12 14:55:26
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysAsynJob")
    @ResponseBody
    public DataMsg updateSysAsynJob(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
        try {
        	String id = this.getParameterString("id");
            String remark = this.getParameterString("remark");
            service.updateRecoverySysBizJob(id, remark);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("恢复成功");
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysAsynJob异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author DELL
     * @description:删除/暂停异步接口任务
     * @date 2016-09-12 14:55:26
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysAsynJob")
    @ResponseBody
    public DataMsg deleteSysAsynJob(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	String remark = this.getParameterString("remark");
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			if(StringUtils.isEmpty(remark)){
				remark=sessionAPI.getCurrentUserInfo().getUserId()+",人工手工暂停。";
			}
			 service.deleteSysAsynJobByPrimaryKey(remark,ids);
			 dataMsg.setMsg("操作成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysAsynJob异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author DELL
     * @description:通过主键查询 其明细信息
     * @date 2016-09-12 14:55:26
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysAsynJobDTO dto = service.querySysAsynJobByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
