package com.jy.modules.platform.dataprv.sysprvmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysPrvAuthResultController
 * @description: 定义 数据权限授权结果表 控制层
 * @author: wangxz
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysPrvManager")
public class SysPrvManagerController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SysPrvManagerController.class);
	
	@RequestMapping(value = "/prepareExecute/{operate}") 
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");
       
       if("toQueryPage".equals(operate)){//跳转至 查询页面
       		model.setViewName("platform/dataprv/sysprvmanager/querySysUser");
       }else if("toManager".equals(operate)){ //跳转至 新增页面
       	model.setViewName("platform/dataprv/sysprvmanager/sysPrvUserManager");
       }
       return model;
   }
}
