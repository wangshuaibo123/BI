package com.jy.platform.rule.ruletree.controller;

import java.util.ArrayList;
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
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;
import com.jy.platform.rule.ruletree.dto.RuleTreeDTO;
import com.jy.platform.rule.ruletree.service.RuleTreeService;

/**
 * @classname: RuleTreeController
 * @description: 定义  规则树 控制层
 * @author:  zhangyu
 */
@Controller
@Scope("prototype")
@RequestMapping("/ruleTree")
public class RuleTreeController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(RuleTreeController.class);

    @Autowired
    @Qualifier("com.jy.platform.rule.ruletree.service.RuleTreeService")
    private RuleTreeService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();

        if("toAdd".equals(operate)){ //跳转至 新增页面
        	String parentId = this.getParameterString("parentId");
        	if (parentId == null || parentId.isEmpty()) {
        		model.addObject("parentDto", new RuleTreeDTO());
        	} else {
            	RuleTreeDTO dto = null;
				try {
					dto = service.queryRuleTreeByID(parentId);
				} catch (Exception e) {
					throw new AbaboonException("根据父节点:" + parentId + "查询规则节点异常");
				}
                //将信息放入 model 以便于传至页面 使用
                model.addObject("parentDto", dto);
        	}
        	model.setViewName("platform/ruledefinition/addRuleTree");
        	
        } else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	RuleTreeDTO dto = null;
			try {
				dto = service.queryRuleTreeByID2(id);
			} catch (Exception e) {
				throw new AbaboonException("根据id:" + id + "查询规则节点异常");
			}
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        	model.setViewName("platform/ruledefinition/updateRuleTree");
        }
        
        return model;
    }
    
    /**
     * @author dell
     * @description:查询分页列表
     * @date 2017-04-20 16:30:44
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryRuleTree")
    @ResponseBody
    public List<Map<String, Object>> queryRuleTree(HttpServletRequest request, RuleTreeDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put("dto", dto);
        List<RuleTreeDTO> dtoList = service.searchRuleTree(params);
        
        List<Map<String, Object>> nodeList = new ArrayList<Map<String, Object>>();
        for (RuleTreeDTO o: dtoList) {
        	Map<String, Object> node = new HashMap<String, Object>();
        	node.put("ID", String.valueOf(o.getId()));
        	node.put("NAME", o.getNodeName());
        	node.put("PID", String.valueOf(o.getParentId()));
        	node.put("CODE", o.getNodeCode());
        	//node.put("nodes", new ArrayList<Object>());
        	if (o.getParentId() == null || o.getParentId() == 0) {
        		node.put("open", "true");
        	} else {
        		node.put("open", "false");
        	}
        	nodeList.add(node);
        }

        return nodeList;
    }
    

    /**
     * @author dell
     * @description:新增
     * @date 2017-04-20 16:30:44
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertRuleTree")
    @ResponseBody
    public DataMsg insertRuleTree(HttpServletRequest request, RuleTreeDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (RuleTreeDTO)super.initDto(dto);

            service.insertRuleTree(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertRuleTree异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author dell
     * @description:编辑
     * @date 2017-04-20 16:30:44
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateRuleTree")
    @ResponseBody
    public DataMsg updateRuleTree(HttpServletRequest request, RuleTreeDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (RuleTreeDTO)super.initDto(dto);
           
            service.updateRuleTree(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateRuleTree异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author dell
     * @description:删除
     * @date 2017-04-20 16:30:44
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteRuleTree")
    @ResponseBody
    public DataMsg deleteRuleTree(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String id = (String) this.getParameter("id");
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteRuleTreeByID(dto,id);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteRuleTree异常：", e);

		}
        
        return dataMsg;
    }

}
