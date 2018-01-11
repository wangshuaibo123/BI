package com.fintech.modules.jbpm4.controller;

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

import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.jbpm4.dto.Jbpm4BizOptionInfoDTO;
import com.fintech.platform.jbpm4.dto.Jbpm4BizTabDTO;
import com.fintech.platform.jbpm4.service.Jbpm4BizOptionInfoService;
import com.fintech.platform.jbpm4.service.impl.Jbpm4BizTabService;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: JbpmBizOptionController
 * @description: 定义  查看任务轨迹 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/dojbpm/jbpmBizOption")
public class JbpmBizOptionController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(JbpmBizOptionController.class);

    @Autowired
    @Qualifier("com.fintech.platform.jbpm4.service.Jbpm4BizOptionInfoService")
    private Jbpm4BizOptionInfoService service;
    
    @Autowired
    @Qualifier("com.fintech.platform.jbpm4.service.impl.Jbpm4BizTabService")
    private Jbpm4BizTabService jbpm4BizTabService;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}") 
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException{
        ModelAndView model = new ModelAndView();
        logger.info("==============跳转 进入 页面相关操作operate:"+operate);
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("component/jbpm/bizOption/impBizOption");
        }
        return model;
    }
    
    /**
     * 查看待办任务参与者
     * @param request
     * 
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/jbpmBizOptionList")
    @ResponseBody
    public DataMsg queryJbpmBizOptionListBybizTabId(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	try {
    		String bizTabId = request.getParameter("bizTabId");
    		String bizInfId = request.getParameter("bizInfId");
    		String bizType = request.getParameter("bizType");
    		//如果不传bizTabId，则通过bizInfId和bizType取bizTabId
    		if(null==bizTabId || "".equals(bizTabId) || "null".equalsIgnoreCase(bizTabId)){
    			if(null==bizInfId || "".equals(bizInfId) || "null".equalsIgnoreCase(bizInfId)){
    				throw new Exception("bizInfId不能为空！");
    			}
    			if(null==bizType || "".equals(bizType) || "null".equalsIgnoreCase(bizType)){
    				throw new Exception("bizType不能为空！");
    			}
    			Map<String,Object> searchParams = new HashMap<String,Object>();
    			Jbpm4BizTabDTO bizTabDto = new Jbpm4BizTabDTO();
    			bizTabDto.setBizInfId(bizInfId);
    			bizTabDto.setBizType(bizType);
    			searchParams.put("dto", bizTabDto);
    			List<Jbpm4BizTabDTO> bizTabList = jbpm4BizTabService.searchJbpm4BizTab(searchParams);
    			if(null==bizTabList || bizTabList.size()<1){
    				throw new Exception("通过bizInfId["+bizInfId+"]和bizType["+bizType+"],没有查询到jbpm4_biz_tab数据！");
    			}
    			bizTabId = bizTabList.get(0).getId()+"";
    		}
            QueryReqBean params = new QueryReqBean();
        	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
    		params.setPageParameter(pageInfo);
        	
        	Map<String, Object> searchParams = new HashMap<String, Object>();
        	Jbpm4BizOptionInfoDTO dto = new Jbpm4BizOptionInfoDTO();
        	dto.setFkJbpmBizTabId(bizTabId);
        	searchParams.put("dto", dto);
        	params.setSearchParams(searchParams);
			List<Jbpm4BizOptionInfoDTO> list = service.searchJbpm4BizOptionInfoByPage(params.getSearchParams());


			dataMsg.setData(list);
			int totalRows = 0;
			if(list != null) {
				totalRows = list.size();
			}
			dataMsg.setTotalRows(totalRows);
        }catch (Exception e) {
        	logger.error("执行方法queryJbpmBizOptionListBybizTabId异常：", e);
        }
        return dataMsg;
    }
}
