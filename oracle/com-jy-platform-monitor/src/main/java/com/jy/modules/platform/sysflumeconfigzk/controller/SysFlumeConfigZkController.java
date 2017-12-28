package com.jy.modules.platform.sysflumeconfigzk.controller;

import java.sql.Timestamp;
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

import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;
import com.jy.modules.platform.sysflumeconfigzk.dto.SysFlumeConfigZkDTO;
import com.jy.modules.platform.sysflumeconfigzk.service.SysFlumeConfigZkService;

/**
 * @classname: SysFlumeConfigZkController
 * @description: 定义  Flume配置表 控制层
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysFlumeConfigZk")
public class SysFlumeConfigZkController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysFlumeConfigZkController.class);

    @Autowired
    @Qualifier("com.jy.modules.platform.sysflumeconfigzk.service.SysFlumeConfigZkService")
    private SysFlumeConfigZkService service;
    
    @Autowired
    private SessionAPI sessionAPI;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysflumeconfigzk/querySysFlumeConfigZk");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysflumeconfigzk/addSysFlumeConfigZk");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysflumeconfigzk/updateSysFlumeConfigZk");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysflumeconfigzk/viewSysFlumeConfigZk");
        }
        
        return model;
    }
    
    /**
     * @description:查询分页列表
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysFlumeConfigZk")
    @ResponseBody
    public DataMsg queryListSysFlumeConfigZk(HttpServletRequest request, SysFlumeConfigZkDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysFlumeConfigZkDTO> list = service.searchSysFlumeConfigZkByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @description:新增
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysFlumeConfigZk")
    @ResponseBody
    public DataMsg insertSysFlumeConfigZk(HttpServletRequest request, SysFlumeConfigZkDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysFlumeConfigZkDTO)super.initDto(dto);
        	
        	UserInfo currentUser = sessionAPI.getCurrentUserInfo();
        	
        	dto.setAppRole("agent");
        	dto.setStatus("saved");
        	dto.setCreateBy(currentUser.getLoginName());
        	dto.setCreateByName(currentUser.getUserName());
        	dto.setCreateTime(new Timestamp(System.currentTimeMillis()));
        	dto.setUpdateBy(currentUser.getLoginName());
        	dto.setUpdateByName(currentUser.getUserName());
        	dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        	//新增
            service.insertSysFlumeConfigZk(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysFlumeConfigZk异常：", e);
        }
        return dataMsg;
    }
    
    /**
     * @description:新增并提交
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertAndCommitSysFlumeConfigZk")
    @ResponseBody
    public DataMsg insertAndCommitSysFlumeConfigZk(HttpServletRequest request, SysFlumeConfigZkDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysFlumeConfigZkDTO)super.initDto(dto);
        	
        	UserInfo currentUser = sessionAPI.getCurrentUserInfo();
        	
        	dto.setAppRole("agent");
        	dto.setStatus("commited");
        	dto.setCreateBy(currentUser.getLoginName());
        	dto.setCreateByName(currentUser.getUserName());
        	dto.setCreateTime(new Timestamp(System.currentTimeMillis()));
        	dto.setUpdateBy(currentUser.getLoginName());
        	dto.setUpdateByName(currentUser.getUserName());
        	dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        	//新增并提交
            service.insertAndCommitSysFlumeConfigZk(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("提交成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysFlumeConfigZk异常：", e);
        }
        return dataMsg;
    }

    /**
     * @description:编辑
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysFlumeConfigZk")
    @ResponseBody
    public DataMsg updateSysFlumeConfigZk(HttpServletRequest request, SysFlumeConfigZkDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysFlumeConfigZkDTO)super.initDto(dto);
        	
        	UserInfo currentUser = sessionAPI.getCurrentUserInfo();
        	
        	dto.setAppRole("agent");
        	dto.setStatus("saved");
        	dto.setUpdateBy(currentUser.getLoginName());
        	dto.setUpdateByName(currentUser.getUserName());
        	dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
           
        	//更新
            service.updateSysFlumeConfigZk(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysFlumeConfigZk异常：", e);
        }
        return dataMsg;
    }
    
    /**
     * @description:更新并提交
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateAndCommitSysFlumeConfigZk")
    @ResponseBody
    public DataMsg updateAndCommitSysFlumeConfigZk(HttpServletRequest request, SysFlumeConfigZkDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysFlumeConfigZkDTO)super.initDto(dto);
        	
        	UserInfo currentUser = sessionAPI.getCurrentUserInfo();
        	
        	dto.setAppRole("agent");
        	dto.setStatus("commited");
        	dto.setUpdateBy(currentUser.getLoginName());
        	dto.setUpdateByName(currentUser.getUserName());
        	dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
           
        	//更新并提交
            service.updateAndCommitSysFlumeConfigZk(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("提交成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysFlumeConfigZk异常：", e);
        }
        return dataMsg;
    }

    /**
     * @description:删除
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysFlumeConfigZk")
    @ResponseBody
    public DataMsg deleteSysFlumeConfigZk(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysFlumeConfigZkByID(dto, ids);
			 dataMsg.setMsg("删除成功");
		} 
		catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysFlumeConfigZk异常：", e);
		}
        
        return dataMsg;
    }
    
    
    /**
     * 批量提交
     * @param request
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/batchCommitToZk")
    @ResponseBody
    public DataMsg batchCommitToZk(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			UserInfo currentUser = sessionAPI.getCurrentUserInfo();
			SysFlumeConfigZkDTO dto = new SysFlumeConfigZkDTO();
        	dto.setUpdateBy(currentUser.getLoginName());
        	dto.setUpdateByName(currentUser.getUserName());
			
			service.batchCommitToZk(dto, ids);
			dataMsg.setMsg("提交成功");
		} 
		catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法batchCommitToZk异常：", e);
		}
        
        return dataMsg;
    }
    
    /**
     * @description:通过主键查询 其明细信息
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysFlumeConfigZkDTO dto = service.querySysFlumeConfigZkByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
