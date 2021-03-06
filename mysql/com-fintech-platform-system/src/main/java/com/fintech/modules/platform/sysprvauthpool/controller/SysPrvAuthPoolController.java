package com.fintech.modules.platform.sysprvauthpool.controller;

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

import com.fintech.modules.platform.sysprvauthpool.dto.SysPrvAuthPoolDTO;
import com.fintech.modules.platform.sysprvauthpool.service.SysPrvAuthPoolService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysPrvAuthPoolController
 * @description: 定义  SYS_PRV_AUTH_POOL 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysPrvAuthPool")
public class SysPrvAuthPoolController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysPrvAuthPoolController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.sysprvauthpool.service.SysPrvAuthPoolService")
    private SysPrvAuthPoolService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysprvauthpool/querySysPrvAuthPool");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysprvauthpool/addSysPrvAuthPool");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysprvauthpool/updateSysPrvAuthPool");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysprvauthpool/viewSysPrvAuthPool");
        }
        
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2015-01-12 20:13:17
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysPrvAuthPool")
    @ResponseBody
    public DataMsg queryListSysPrvAuthPool(HttpServletRequest request, SysPrvAuthPoolDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysPrvAuthPoolDTO> list = service.searchSysPrvAuthPoolByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author
     * @description:新增
     * @date 2015-01-12 20:13:17
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysPrvAuthPool")
    @ResponseBody
    public DataMsg insertSysPrvAuthPool(HttpServletRequest request, SysPrvAuthPoolDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysPrvAuthPoolDTO)super.initDto(dto);

            service.insertSysPrvAuthPool(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
           // dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:编辑
     * @date 2015-01-12 20:13:17
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysPrvAuthPool")
    @ResponseBody
    public DataMsg updateSysPrvAuthPool(HttpServletRequest request, SysPrvAuthPoolDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysPrvAuthPoolDTO)super.initDto(dto);
           
            service.updateSysPrvAuthPool(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            //dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:删除
     * @date 2015-01-12 20:13:17
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysPrvAuthPool")
    @ResponseBody
    public DataMsg deleteSysPrvAuthPool(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysPrvAuthPoolByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    
    
    
    @RequestMapping(value = "/insertSysAuthPool")
    @ResponseBody
    public DataMsg insertSysAuthPool(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			// service.deleteSysPrvAuthPoolByPrimaryKey(dto,ids); 
			 service.authRefresh(ids);
			 dataMsg.setMsg("权限刷新成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        return dataMsg;
    }
    
    /**
     * @author
     * @description:通过主键查询 其明细信息
     * @date 2015-01-12 20:13:17
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysPrvAuthPoolDTO dto = service.querySysPrvAuthPoolByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
