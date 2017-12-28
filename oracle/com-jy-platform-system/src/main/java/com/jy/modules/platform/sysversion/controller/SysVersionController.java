package com.jy.modules.platform.sysversion.controller;

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

import com.jy.modules.platform.sysversion.dto.SysVersionDTO;
import com.jy.modules.platform.sysversion.service.SysVersionService;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysVersionController
 * @description: 定义  系统版本号表 控制层
 * @author:  lei
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysVersion")
public class SysVersionController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysVersionController.class);

    @Autowired
    @Qualifier("com.jy.modules.platform.sysversion.service.SysVersionService")
    private SysVersionService service;
    
    
   // @Autowired
    //private  FdfsAPI fdfsAPI;
    
    
    
    /**
     * 
     * 跳首页版本列表页
     * @return
     * @throws AbaboonException
     */
    
    @RequestMapping(value = "/toSysVersion")
    public ModelAndView toSysVersion() throws Exception {
        ModelAndView model = new ModelAndView();
        model.setViewName("platform/sysversion/sysVersion");
        Map<String,Object> searchParams = new HashMap<String,Object>();
        SysVersionDTO svd=new SysVersionDTO();
        String systemState=this.getParameterString("systemState");
        svd.setSystemState(systemState);
        searchParams.put("dto", svd);
        List<SysVersionDTO> list= service.searchSysVersion(searchParams);
        model.addObject("dto",list);
        return model;
    }
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysversion/querySysVersion");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysversion/addSysVersion");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysversion/updateSysVersion");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysversion/viewSysVersion");
        }
        
        return model;
    }
    
    /**
     * @author lei
     * @description:查询分页列表
     * @date 2015-03-17 10:32:51
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysVersion")
    @ResponseBody
    public DataMsg queryListSysVersion(HttpServletRequest request, SysVersionDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysVersionDTO> list = service.searchSysVersionByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author lei
     * @description:新增
     * @date 2015-03-17 10:32:51
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysVersion")
    @ResponseBody
    public DataMsg insertSysVersion(HttpServletRequest request, SysVersionDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysVersionDTO)super.initDto(dto);

            service.insertSysVersion(dto);
            
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
     * @date 2015-03-17 10:32:51
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysVersion")
    @ResponseBody
    public DataMsg updateSysVersion(HttpServletRequest request, SysVersionDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysVersionDTO)super.initDto(dto);
           
            service.updateSysVersion(dto);
            
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
     * @date 2015-03-17 10:32:51
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysVersion")
    @ResponseBody
    public DataMsg deleteSysVersion(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysVersionByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
//    @RequestMapping(value = "/downloadZip")
//    public ModelAndView downloadZip(HttpServletRequest request,HttpServletResponse response, @ModelAttribute DataMsg dataMsg){
//    	String[] fileIds  = new String[]{"group1/M00/00/7E/rBJk1VUmKLiAWWBxAJCYAIMG7aU658.zip","group1/M00/00/7E/rBJk1VUmKM6AGO-IAAAmcoaJhWI11.xlsx"};
//    	fdfsAPI.pack(fileIds, request, response);
//        return null;
//    }
    
    
    /**
     * @author lei
     * @description:通过主键查询 其明细信息
     * @date 2015-03-17 10:32:51
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysVersionDTO dto = service.querySysVersionByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    
    
    
    
    
}
