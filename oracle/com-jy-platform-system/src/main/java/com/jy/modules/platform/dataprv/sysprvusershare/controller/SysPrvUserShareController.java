package com.jy.modules.platform.dataprv.sysprvusershare.controller;

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

import com.jy.modules.platform.dataprv.sysprvusershare.dto.SysPrvUserShareDTO;
import com.jy.modules.platform.dataprv.sysprvusershare.service.SysPrvUserShareService;
import com.jy.platform.api.org.OrgAPI;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysPrvUserShareController
 * @description: 定义  数据共享表 控制层
 * @author:  wangxz
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysPrvUserShare")
public class SysPrvUserShareController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysPrvUserShareController.class);

    @Autowired
    @Qualifier("com.jy.modules.platform.dataprv.sysprvusershare.service.SysPrvUserShareService")
    private SysPrvUserShareService service;
	@Autowired
	private OrgAPI orgApi;
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	String userId = this.getParameterString("userId");
        	if(userId!=null&&userId.length()>0)
        		model.addObject("userId", userId);
        	model.setViewName("platform/dataprv/sysprvusershare/querySysPrvUserShare");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/dataprv/sysprvusershare/addSysPrvUserShare");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/dataprv/sysprvusershare/updateSysPrvUserShare");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/dataprv/sysprvusershare/viewSysPrvUserShare");
        }
        
        return model;
    }
    
    /**
     * @author wangxz
     * @description:查询分页列表
     * @date 2014-10-18 16:07:49
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysPrvUserShare")
    @ResponseBody
    public DataMsg queryListSysPrvUserShare(HttpServletRequest request, SysPrvUserShareDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	String userId = this.getParameterString("userId1");
    	if(userId!=null&&userId.length()>0)
    		dto.setToUserId(Long.parseLong(userId));
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysPrvUserShareDTO> list = service.searchSysPrvUserShareByPaging(params.getSearchParams());
        Map<String,String> userMap = new HashMap<String,String>();
		userMap.put("fromUserId", "createUserNameExt");
		userMap.put("toUserId", "createOrgNameExt");
		
		list = orgApi.mappingOrgUser(list, userMap, null);
        dataMsg.setData(list);
        
        int totalcount = service.searchSysPrvUserShareTotalCount(params.getSearchParams());
        
        dataMsg.setTotalRows(totalcount);
        return dataMsg;
    }
    

    /**
     * @author wangxz
     * @description:新增
     * @date 2014-10-18 16:07:49
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysPrvUserShare")
    @ResponseBody
    public ModelAndView insertSysPrvUserShare(String fromUserIds,String toUserId){
    	ModelAndView model = new ModelAndView();
    	model.addObject("userId", toUserId);
    	model.setViewName("platform/dataprv/sysprvusershare/querySysPrvUserShare");
        try {
            service.insertSysPrvUserShare(fromUserIds,toUserId);
        }catch (Exception e) {
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return model;
    }

    /**
     * @author wangxz
     * @description:编辑
     * @date 2014-10-18 16:07:49
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysPrvUserShare")
    @ResponseBody
    public DataMsg updateSysPrvUserShare(HttpServletRequest request, SysPrvUserShareDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysPrvUserShareDTO)super.initDto(dto);
           
            service.updateSysPrvUserShare(dto);
            
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
     * @author wangxz
     * @description:删除
     * @date 2014-10-18 16:07:49
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysPrvUserShare")
    @ResponseBody
    public DataMsg deleteSysPrvUserShare(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysPrvUserShareByID(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author wangxz
     * @description:通过主键查询 其明细信息
     * @date 2014-10-18 16:07:49
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysPrvUserShareDTO dto = service.querySysPrvUserShareByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    
    /**
     * @description:唯一性验证
     * @date 2014-10-18 16:07:49
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/queryInfoNumByUser")
    @ResponseBody
    public DataMsg queryInfoNumByUser(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("fromUserIds", this.getParameterString("fromUserIds"));
    	paramMap.put("toUserId", this.getParameterString("toUserId"));
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 dataMsg.setData(service.queryInfoNumByUser(paramMap));
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法queryInfoNumByUser异常：", e);
		}
        return dataMsg;
    }
}
