package com.jy.modules.platform.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.restservice.web.base.BaseController;
/**
 * @description:监听页面复制相关数据信息
 * @author chengang
 * @date: 2016年8月9日 上午9:58:25
 */
@Controller
@Scope("prototype")
@RequestMapping("/front")
public class FrontMonitorController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(FrontMonitorController.class);

	@Autowired
	private SessionAPI sessionAPI;
	
	@RequestMapping(value = "/ctrl/c")
    @ResponseBody
    public DataMsg monitorCtrlC(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
        try {
        	UserInfo userInfo = sessionAPI.getCurrentUserInfo();
        	String copyText = request.getParameter("copyText");
        	
        	//输出ctrl-c日志
        	logger.info("front-end monitor [ctrl-c]:userId="+userInfo.getUserId()+",copyText="+copyText);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("成功");
			dataMsg.setStatus(DataMsg.STATUS_OK);
        }
        catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法monitorCtrlC异常：", e);
        }
        return dataMsg;
    }
	
	
}
