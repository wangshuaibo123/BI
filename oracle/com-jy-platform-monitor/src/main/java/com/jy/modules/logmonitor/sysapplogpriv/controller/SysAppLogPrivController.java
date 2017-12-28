package com.jy.modules.logmonitor.sysapplogpriv.controller;

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

import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

import com.jy.modules.logmonitor.sysapplogpriv.dto.SysAppLogPrivDTO;
import com.jy.modules.logmonitor.sysapplogpriv.service.SysAppLogPrivService;

/**
 * @classname: SysAppLogPrivController
 * @description: 定义  日志访问权限表 控制层
 * @author:  sunli
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysAppLogPriv")
public class SysAppLogPrivController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysAppLogPrivController.class);

    @Autowired
    @Qualifier("com.jy.modules.logmonitor.sysapplogpriv.service.SysAppLogPrivService")
    private SysAppLogPrivService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysapplogpriv/querySysAppLogPriv");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysapplogpriv/addSysAppLogPriv");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysapplogpriv/updateSysAppLogPriv");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysapplogpriv/viewSysAppLogPriv");
        }
        
        return model;
    }
    
    /**
     * @author sunli
     * @description:查询分页列表
     * @date 2016-05-30 11:30:54
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysAppLogPriv")
    @ResponseBody
    public DataMsg queryListSysAppLogPriv(HttpServletRequest request, SysAppLogPrivDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysAppLogPrivDTO> list = service.searchSysAppLogPrivByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author sunli
     * @description:新增
     * @date 2016-05-30 11:30:54
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysAppLogPriv")
    @ResponseBody
    public DataMsg insertSysAppLogPriv(HttpServletRequest request, SysAppLogPrivDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysAppLogPrivDTO)super.initDto(dto);

        	Map<String, Object> searchParams = new HashMap<String, Object>();
        	SysAppLogPrivDTO param = new SysAppLogPrivDTO();
        	param.setAppId(dto.getAppId());
        	param.setUserId(dto.getUserId());
        	searchParams.put("dto", param);
        	List appLogPrivList = service.searchSysAppLogPriv(searchParams);
        	
        	if(appLogPrivList!=null && appLogPrivList.size()>0){
        		 dataMsg = super.initDataMsg(dataMsg);
                 dataMsg.setStatus("repeat");
                 dataMsg.setMsg("此日志权限数据已经存在");
        	}
        	else{
        		//插入
                service.insertSysAppLogPriv(dto);
                
                dataMsg = super.initDataMsg(dataMsg);
                dataMsg.setMsg("新增成功");
                dataMsg.setData(this.makeJSONData(dto.getId()));
        	}
        	
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysAppLogPriv异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author sunli
     * @description:编辑
     * @date 2016-05-30 11:30:54
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysAppLogPriv")
    @ResponseBody
    public DataMsg updateSysAppLogPriv(HttpServletRequest request, SysAppLogPrivDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysAppLogPrivDTO)super.initDto(dto);
        	
        	Map<String, Object> searchParams = new HashMap<String, Object>();
        	SysAppLogPrivDTO param = new SysAppLogPrivDTO();
        	param.setAppId(dto.getAppId());
        	param.setUserId(dto.getUserId());
        	searchParams.put("dto", param);
        	List appLogPrivList = service.searchSysAppLogPriv(searchParams);
        	
        	if(appLogPrivList!=null && appLogPrivList.size()>0){
        		 dataMsg = super.initDataMsg(dataMsg);
                 dataMsg.setStatus("repeat");
                 dataMsg.setMsg("此日志权限数据已经存在");
        	}
        	else{
        		//更新
                service.updateSysAppLogPriv(dto);
                
                dataMsg = super.initDataMsg(dataMsg);
                dataMsg.setMsg("修改成功");
                dataMsg.setData(this.makeJSONData(dto.getId()));
        	}
           
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysAppLogPriv异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author sunli
     * @description:物理删除
     * @date 2016-05-30 11:30:54
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysAppLogPriv")
    @ResponseBody
    public DataMsg deleteSysAppLogPriv(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysAppLogPrivByID(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysAppLogPriv异常：", e);
		}
        
        return dataMsg;
    }
    
    /**
     * @author sunli
     * @description:通过主键查询 其明细信息
     * @date 2016-05-30 11:30:54
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysAppLogPrivDTO dto = service.querySysAppLogPrivByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
