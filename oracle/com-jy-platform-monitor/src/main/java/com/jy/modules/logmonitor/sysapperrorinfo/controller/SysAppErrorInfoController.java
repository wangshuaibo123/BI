package com.jy.modules.logmonitor.sysapperrorinfo.controller;

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

import com.jy.modules.logmonitor.sysapperrorinfo.dto.SysAppErrorInfoDTO;
import com.jy.modules.logmonitor.sysapperrorinfo.service.SysAppErrorInfoService;
import com.jy.modules.logmonitor.sysapplogpriv.service.SysAppLogPrivService;
import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysAppErrorInfoController
 * @description: 定义  业务系统节点错误日志 控制层
 * @author:  lei
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysAppErrorInfo")
public class SysAppErrorInfoController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysAppErrorInfoController.class);

    @Autowired
    @Qualifier("com.jy.modules.logmonitor.sysapperrorinfo.service.SysAppErrorInfoService")
    private SysAppErrorInfoService service;
    
    @Autowired
    @Qualifier("com.jy.modules.logmonitor.sysapplogpriv.service.SysAppLogPrivService")
    private SysAppLogPrivService sysAppLogPrivService;
    
    @Autowired
	private SessionAPI sessionAPI;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate,HttpServletRequest request) throws Exception {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	//当前登录系统的用户id
    		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
        	Map<String, Object> searchParams = new HashMap<String, Object>();
        	searchParams.put("userId", userInfo.getUserId());
        	//当前用户权限所在的系统id
        	String appIds = sysAppLogPrivService.getUserAppPriv(searchParams);
        	request.setAttribute("appIds", appIds);
        	
        	model.setViewName("platform/sysapperrorinfo/querySysAppErrorInfo");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysapperrorinfo/addSysAppErrorInfo");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysapperrorinfo/updateSysAppErrorInfo");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysapperrorinfo/viewSysAppErrorInfo");
        }
        
        return model;
    }
    
    /**
     * @author lei
     * @description:查询分页列表
     * @date 2015-04-03 10:07:07
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysAppErrorInfo")
    @ResponseBody
    public DataMsg queryListSysAppErrorInfo(HttpServletRequest request, SysAppErrorInfoDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
    	
    	String startTime = this.getParameterString("createTime_start");
    	String endTime = this.getParameterString("createTime_end");
    	searchParams.put("startTime", startTime);
    	searchParams.put("endTime", endTime);
    	
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysAppErrorInfoDTO> list = service.searchSysAppErrorInfoByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    


    /**
     * @author lei
     * @description:编辑
     * @date 2015-04-03 10:07:07
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysAppErrorInfo")
    @ResponseBody
    public DataMsg updateSysAppErrorInfo(HttpServletRequest request, SysAppErrorInfoDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysAppErrorInfoDTO)super.initDto(dto);
           
            service.updateSysAppErrorInfo(dto);
            
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
     * @date 2015-04-03 10:07:07
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysAppErrorInfo")
    @ResponseBody
    public DataMsg deleteSysAppErrorInfo(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysAppErrorInfoByPrimaryKey(dto,ids);
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
     * @date 2015-04-03 10:07:07
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysAppErrorInfoDTO dto = service.querySysAppErrorInfoByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
