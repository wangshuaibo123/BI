package com.jy.modules.platform.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.modules.platform.common.service.CommonService;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.restservice.web.base.BaseController;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private CommonService service;
	
    /**Description: 唯一性校验相关字段
     * Create Date: 2014年10月20日下午5:24:08<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param request
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/checkUnique")
    @ResponseBody
    public DataMsg checkUnique(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
        try{
        	String tableName = (String)this.getParameter("tableName");
        	String uniqueColumn = (String)this.getParameter("uniqueColumn");
        	String checkValue = (String)this.getParameter("checkValue");
        	String nocheckId = (String)this.getParameter("nocheckId");
        	if(service.checkUnique(tableName, uniqueColumn, checkValue, nocheckId)){
        		
    		} else{
    			dataMsg.failed("数据唯一校验失败！");
    		}
        }catch(Exception e){
        	logger.error(e.getMessage());
			dataMsg.failed(e.getMessage());
        }
        return dataMsg;
    }
}
