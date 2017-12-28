package com.jy.modules.platform.sysmessage.controller;

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

import com.jy.modules.platform.sysmessage.dto.SysUserMsgRelationDTO;
import com.jy.modules.platform.sysmessage.service.SysUserMsgRelationService;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysUserMsgRelationController
 * @description: 定义  SYS_USER_MSG_RELATION 控制层
 * @author:  lin
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysUserMsgRelation")
public class SysUserMsgRelationController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysUserMsgRelationController.class);

    @Autowired
    @Qualifier("com.jy.modules.platform.sysmessage.service.SysUserMsgRelationService")
    private SysUserMsgRelationService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysmessagesysusermsgrelation/querySysUserMsgRelation");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysmessagesysusermsgrelation/addSysUserMsgRelation");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysmessagesysusermsgrelation/updateSysUserMsgRelation");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysmessagesysusermsgrelation/viewSysUserMsgRelation");
        }
        
        return model;
    }
    
    /**
     * @author lin
     * @description:查询分页列表
     * @date 2014-11-14 11:08:58
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysUserMsgRelation")
    @ResponseBody
    public DataMsg queryListSysUserMsgRelation(HttpServletRequest request, SysUserMsgRelationDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysUserMsgRelationDTO> list = service.searchSysUserMsgRelationByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author lin
     * @description:新增
     * @date 2014-11-14 11:08:58
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysUserMsgRelation")
    @ResponseBody
    public DataMsg insertSysUserMsgRelation(HttpServletRequest request, SysUserMsgRelationDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysUserMsgRelationDTO)super.initDto(dto);

            service.insertSysUserMsgRelation(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(this.makeJSONData(dto.getRelId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author lin
     * @description:编辑
     * @date 2014-11-14 11:08:58
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysUserMsgRelation")
    @ResponseBody
    public DataMsg updateSysUserMsgRelation(HttpServletRequest request, SysUserMsgRelationDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysUserMsgRelationDTO)super.initDto(dto);
           
            service.updateSysUserMsgRelation(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setData(this.makeJSONData(dto.getRelId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author lin
     * @description:删除
     * @date 2014-11-14 11:08:58
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysUserMsgRelation")
    @ResponseBody
    public DataMsg deleteSysUserMsgRelation(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysUserMsgRelationByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author lin
     * @description:通过主键查询 其明细信息
     * @date 2014-11-14 11:08:58
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysUserMsgRelationDTO dto = service.querySysUserMsgRelationByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
