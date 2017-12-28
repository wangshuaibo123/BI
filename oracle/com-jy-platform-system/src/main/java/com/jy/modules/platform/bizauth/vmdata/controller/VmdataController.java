package com.jy.modules.platform.bizauth.vmdata.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.modules.platform.bizauth.vmdata.service.VmDataService;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @description: 定义  数据权限 控制层
 * @author chen_gang
 * @date: 2015年9月23日 下午1:04:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/vmdataController")
public class VmdataController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(VmdataController.class);
    @Autowired
    @Qualifier("com.jy.modules.platform.bizauth.vmdata.service.impl.VmDataServiceImpl")
    private VmDataService service;

    /**
     * @author chen_gang
     * @description:新增
     * @date 2015-01-16 17:14:46
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/makeupLastVmdataPrivPart")
    @ResponseBody
    public DataMsg makeupLastVmdataPrivPart(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
        try {
        	String orgType = request.getParameter("orgType");//LOAN
        	String userId = request.getParameter("curUserId");//
        	String trunType = request.getParameter("trunType");//
        	service.makeupLastVmdataPrivPart(userId, orgType,trunType);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("数据权限更新成功");
        }catch (Exception e) {
        	logger.error("执行方法makeupLastVmdataPrivPart异常：", e);
        	dataMsg.failed(e.getMessage());
        	
        }
        return dataMsg;
    }

}
