package com.jy.platform.rule.ruledefinition.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.jy.platform.rule.ruledefinition.dto.RuleDefinitionDTO;
import com.jy.platform.rule.ruledefinition.service.RuleDefinitionService;
import com.jy.platform.rule.ruletree.dto.RuleTreeDTO;
import com.jy.platform.rule.ruletree.service.RuleTreeService;

/**
 * @classname: RuleDefinitionController
 * @description: 定义  RULE_DEFINITION 控制层
 * @author:  zhangyu
 */
@Controller
@RequestMapping("/ruleDefinition")
public class RuleDefinitionController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(RuleDefinitionController.class);

    @Autowired
    @Qualifier("com.jy.platform.rule.ruledefinition.service.RuleDefinitionService")
    private RuleDefinitionService service;
    
    @Autowired
    @Qualifier("com.jy.platform.rule.ruletree.service.RuleTreeService")
    private RuleTreeService treeService;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toManagePage".equals(operate)){//跳转至 树形展示页面
        	//model.setViewName("platform/ruledefinition/queryRuleDefinition");
        	model.setViewName("platform/ruledefinition/manageRuleDefinition");
        }else if("toQueryPage".equals(operate)){ //跳转至 列表查询页面// TODO 已废弃
        	model.setViewName("platform/ruledefinition/queryRuleDefinition");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	String parentId = this.getParameterString("parentId");
        	RuleTreeDTO treeDto = null;
        	try {
        		treeDto = treeService.queryRuleTreeByID(parentId);
			} catch (Exception e) {
				throw new AbaboonException("根据ID:" + parentId + "获取规则树节点信息异常");
			}
        	model.addObject("treeDto", treeDto);
        	model.setViewName("platform/ruledefinition/addRuleDefinition");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/ruledefinition/updateRuleDefinition");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/ruledefinition/viewRuleDefinition");
        }else if("toInternalQueryPage".equals(operate)){ //跳转至 内嵌的列表查询页面
        	String parentId = this.getParameterString("parentId");
        	model.addObject("parentId", parentId);
        	model.setViewName("platform/ruledefinition/queryRuleDefinitionInternal");
        }
        
        return model;
    }
    
    /**
     * @author dell
     * @description:查询分页列表
     * @date 2017-03-28 15:17:21
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListRuleDefinition")
    @ResponseBody
    public DataMsg queryListRuleDefinition(HttpServletRequest request, RuleDefinitionDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<RuleDefinitionDTO> list = service.searchRuleDefinitionByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author dell
     * @description:新增
     * @date 2017-03-28 15:17:21
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertRuleDefinition")
    @ResponseBody
    public DataMsg insertRuleDefinition(HttpServletRequest request, RuleDefinitionDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (RuleDefinitionDTO)super.initDto(dto);

            service.insertRuleDefinition(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertRuleDefinition异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author dell
     * @description:编辑
     * @date 2017-03-28 15:17:21
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateRuleDefinition")
    @ResponseBody
    public DataMsg updateRuleDefinition(HttpServletRequest request, RuleDefinitionDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (RuleDefinitionDTO)super.initDto(dto);
           
            service.updateRuleDefinition(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateRuleDefinition异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author dell
     * @description:删除
     * @date 2017-03-28 15:17:21
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteRuleDefinition")
    @ResponseBody
    public DataMsg deleteRuleDefinition(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteRuleDefinitionByID(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteRuleDefinition异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author dell
     * @description:通过主键查询 其明细信息
     * @date 2017-03-28 15:17:21
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	RuleDefinitionDTO dto = service.queryRuleDefinitionByID(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    
}
