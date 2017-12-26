package com.fintech.modules.platform.bizauth.vmauthregisterinfo.controller;

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

import com.fintech.modules.platform.bizauth.vmauthregisterinfo.dto.VmauthRegisterInfoDTO;
import com.fintech.modules.platform.bizauth.vmauthregisterinfo.service.VmauthRegisterInfoService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: VmauthRegisterInfoController
 * @description: 定义  权限注册表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/vmauthRegisterInfo")
public class VmauthRegisterInfoController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(VmauthRegisterInfoController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.bizauth.vmauthregisterinfo.service.VmauthRegisterInfoService")
    private VmauthRegisterInfoService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/bizauth/vmauthregisterinfo/queryVmauthRegisterInfo");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/bizauth/vmauthregisterinfo/addVmauthRegisterInfo");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/bizauth/vmauthregisterinfo/updateVmauthRegisterInfo");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/bizauth/vmauthregisterinfo/viewVmauthRegisterInfo");
        }
        
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2015-01-16 17:14:11
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListVmauthRegisterInfo")
    @ResponseBody
    public DataMsg queryListVmauthRegisterInfo(HttpServletRequest request, VmauthRegisterInfoDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<VmauthRegisterInfoDTO> list = service.searchVmauthRegisterInfoByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @throws Exception 
     * @author
     * @description:新增
     * @date 2015-01-16 17:14:11
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertVmauthRegisterInfo")
    @ResponseBody
    public DataMsg insertVmauthRegisterInfo(HttpServletRequest request, VmauthRegisterInfoDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception{
        try {
        	dto = (VmauthRegisterInfoDTO)super.initDto(dto);
        	dto.setCreateBy(dto.getOpUserId());
            service.insertVmauthRegisterInfo(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }
    @RequestMapping(value = "/validateVmauthRegisterInfo")
    @ResponseBody
    public DataMsg validateVmauthRegisterInfo(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) throws Exception {
        try {
    		dataMsg = super.initDataMsg(dataMsg);
        	String vmName = this.getParameterString("vmName");
        	String vmCode = this.getParameterString("vmCode");
            service.queryVmauthRegisterInfo(vmName,vmCode);
            
            dataMsg.setMsg("");
            //dataMsg.setData(this.makeJSONData(dto.getId()));*/
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	//logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }
    /**
     * @author
     * @description:编辑
     * @date 2015-01-16 17:14:11
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateVmauthRegisterInfo")
    @ResponseBody
    public DataMsg updateVmauthRegisterInfo(HttpServletRequest request, VmauthRegisterInfoDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (VmauthRegisterInfoDTO)super.initDto(dto);
           
            service.updateVmauthRegisterInfo(dto);
            
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
     * @date 2015-01-16 17:14:11
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteVmauthRegisterInfo")
    @ResponseBody
    public DataMsg deleteVmauthRegisterInfo(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteVmauthRegisterInfoByPrimaryKey(dto,ids);
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
     * @date 2015-01-16 17:14:11
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	VmauthRegisterInfoDTO dto = service.queryVmauthRegisterInfoByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
