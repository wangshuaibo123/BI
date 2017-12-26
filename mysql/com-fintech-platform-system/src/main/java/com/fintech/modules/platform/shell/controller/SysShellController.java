package com.fintech.modules.platform.shell.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;


@Controller
@Scope("prototype")
@RequestMapping("/sysShell")
public class SysShellController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SysShellController.class);

	
	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		
		//到执行Shell脚本的页面
		if("toExecShell".equals(operate)){
			model.setViewName("platform/sysConfig/execShell");
		} 

		return model;
	}
	
	
	/**
	 * 执行shell脚本
	 * @param request
	 * @param dataMsg
	 * @return
	 */
	@RequestMapping(value = "/execShell")
	@ResponseBody
	public DataMsg execShell(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {
		try{
			String shellCmd = (String)request.getParameter("shellCmd");
			String shellParam = (String)request.getParameter("shellParam");
			String shell = shellCmd + " " + shellParam;
			logger.debug("shell："+shell);
			
			Process process = Runtime.getRuntime().exec(shell);
			int exitValue = process.waitFor();
			if(0 != exitValue){
				logger.error("call shell failed. error code is :" + exitValue);
				dataMsg.setMsg("shell脚本执行失败,error code is :" + exitValue);
				dataMsg.setStatus(DataMsg.STATUS_FAILED);
			}
			else{
				dataMsg.setMsg("shell脚本已经执行");
				dataMsg.setStatus(DataMsg.STATUS_OK);
			}
		} 
		catch(Exception e){
			logger.error("shell脚本执行异常：", e);
			dataMsg.failed(e.getMessage());
			dataMsg.setStatus(DataMsg.STATUS_FAILED);
		}
		
		return dataMsg;
	}

}
