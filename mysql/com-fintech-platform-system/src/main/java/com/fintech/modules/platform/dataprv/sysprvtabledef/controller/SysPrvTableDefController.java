package com.fintech.modules.platform.dataprv.sysprvtabledef.controller;

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

import com.fintech.modules.platform.dataprv.sysprvtabledef.dto.SysPrvTableDefDTO;
import com.fintech.modules.platform.dataprv.sysprvtabledef.service.SysPrvTableDefService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;


/**
 * @classname: SysPrvTableDefController
 * @description: 定义  数据权限表定义 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysPrvTableDef")
public class SysPrvTableDefController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysPrvTableDefController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.dataprv.sysprvtabledef.service.SysPrvTableDefService")
    private SysPrvTableDefService service;
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/dataprv/sysprvtabledef/querySysPrvTableDef");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model = queryTableList();
        	model.setViewName("platform/dataprv/sysprvtabledef/addSysPrvTableDef");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/dataprv/sysprvtabledef/updateSysPrvTableDef");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/dataprv/sysprvtabledef/viewSysPrvTableDef");
        }
        
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-10-18 16:07:40
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysPrvTableDef")
    @ResponseBody
    public DataMsg queryListSysPrvTableDef(HttpServletRequest request, SysPrvTableDefDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		List<SysPrvTableDefDTO> list = service.searchSysPrvTableDefByPaging(params.getSearchParams());

		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    
    /**
     * @author
     * @description:新增
     * @date 2014-10-18 16:07:40
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysPrvTableDef")
    @ResponseBody
    public DataMsg insertSysPrvTableDef(HttpServletRequest request, SysPrvTableDefDTO dto, @ModelAttribute DataMsg dataMsg){
    	try {
			dto = (SysPrvTableDefDTO) super.initDto(dto);
			service.insertSysPrvTableDef(dto);
			dataMsg = super.initDataMsg(dataMsg);
			dataMsg.setMsg("新增成功");
			dataMsg.setData(this.makeJSONData(dto.getId()));
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法insertSysResource异常：", e);
		}
        return dataMsg;
    }


    /**
     * @author
     * @description:删除
     * @date 2014-10-18 16:07:40
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysPrvTableDef")
    @ResponseBody
    public DataMsg deleteSysPrvTableDef(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	BaseDTO dto = super.initDto(null);
    	String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			service.deleteSysPrvTableDefByID( dto, ids);
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
     * @date 2014-10-18 16:07:40
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try {
			SysPrvTableDefDTO dto = service.querySysPrvTableDefByPrimaryKey(id);
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
        return model;
    }
    /**
     * @author
     * @description:通过主键查询 其明细信息
     * @date 2014-10-18 16:07:40
     * @param id
     * @return
     */
    private ModelAndView queryTableList() throws AbaboonException {
    	ModelAndView model = new ModelAndView();
    	try {
			List<String> tables = service.queryTableList();
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("tables", tables);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
    	return model;
    }
}
