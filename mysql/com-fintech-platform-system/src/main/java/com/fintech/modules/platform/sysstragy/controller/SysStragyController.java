package com.fintech.modules.platform.sysstragy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.platform.api.ldap.UserSecurityStragyVo;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restclient.http.RestClient;
import com.fintech.platform.restclient.http.RestClientConfig;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysStragyController
 * @description: 安全策略 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysStragy")
public class SysStragyController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysStragyController.class);
    
    private String jyptURL = RestClientConfig.getServiceUrl("mdata");// rest服务地址
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        if("toView".equals(operate)){//查看配置页面
        	model = this.queryOneDTO();
        	model.setViewName("platform/sysstragy/viewSysStragy");
        }else if("toUpdate".equals(operate)){ //跳转至 修改页面
        	model = this.queryOneDTO();
        	model.setViewName("platform/sysstragy/updateSysStragy");
        }else if("toUnlock".equals(operate)){//跳转至 解锁页面
        	model.setViewName("platform/sysstragy/unlock");
        }
        return model;
    }
    
    /**
     * 更新安全策略配置
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/updateSysStragy")
    @ResponseBody
    public DataMsg updateSysStragy(HttpServletRequest request, UserSecurityStragyVo dto, @ModelAttribute DataMsg dataMsg){
        try {
        	String url = jyptURL + "/api/platform/usersecurity/update/v1";
        	ResponseMsg<UserSecurityStragyVo> responseMsg  = RestClient.doPost("mdata", url, dto, new TypeReference<ResponseMsg<UserSecurityStragyVo>>(){});
            dataMsg = super.initDataMsg(dataMsg);
            if(responseMsg.getRetCode().equals("200")){
        		dataMsg.setMsg("修改成功");
        	}else{
        		dataMsg.setMsg("修改失败,请联系管理员");
        	}
            
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysStragy异常：", e);
        }
        return dataMsg;
    }
    /**
     * 解锁用户
     * @param request
     * @param loginName 登录用户名
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/unlock")
    @ResponseBody
    public DataMsg unlock(HttpServletRequest request, String loginName, @ModelAttribute DataMsg dataMsg){
        try {
        	String userid = queryUserId(loginName);
        	if(userid == null){
        		dataMsg.setMsg("解锁失败,未找到此用户");
        		return dataMsg;
        	}
        	String url = jyptURL + "/api/platform/usersecurity/unlock/v1/"+userid;
    		ResponseMsg<Void> responseMsg  = RestClient.doGet("mdata", url, new TypeReference<ResponseMsg<Void>>(){});
            dataMsg = super.initDataMsg(dataMsg);
            if(responseMsg.getRetCode().equals("200")){
        		dataMsg.setMsg("解锁成功");
        	}else{
        		dataMsg.setMsg("解锁失败,请联系管理员");
        	}
            
        }catch (Exception e) {
        	dataMsg.failed("解锁失败,请联系管理员");
            logger.error("执行方法unlock异常：", e);
        }
        return dataMsg;
    }
    /**
     * 根据登录名查询出用户id,查询不到为null
     * @param loginName
     * @return
     */
    private String queryUserId(String loginName){
    	String  userid = null;
    	String url = jyptURL + "/api/platform/SysUserRest/searchByParam/v1";
		QueryReqBean params = new QueryReqBean();
		Map<String, Object> searchParams = new HashMap<String,Object>();
		SysUserDTO dto = new SysUserDTO();
		dto.setLoginName(loginName);
		searchParams.put("dto", dto);
		params.setSearchParams(searchParams);
		ResponseMsg<QueryRespBean<SysUserDTO>> responseMsg  = RestClient.doPost("mdata", url, params, new TypeReference<ResponseMsg<QueryRespBean<SysUserDTO>>>(){});
		if(responseMsg.getRetCode().equals("200")){
			List<SysUserDTO> result = responseMsg.getResponseBody().getResult();
			if(result.size() == 1){
				userid = result.get(0).getId().toString();
			}
		}
		return userid;
    }

   
    /**
     * 查询安全策略配置
     * @return
     * @throws AbaboonException
     */
    private ModelAndView queryOneDTO() throws AbaboonException {
    	
        ModelAndView model = new ModelAndView();
        String url = jyptURL + "/api/platform/usersecurity/lookup/v1";
		ResponseMsg<UserSecurityStragyVo> responseMsg  = RestClient.doGet("mdata", url, new TypeReference<ResponseMsg<UserSecurityStragyVo>>(){});
		UserSecurityStragyVo dto = responseMsg.getResponseBody();
        model.addObject("dto", dto);
        logger.debug("query usersecurity config:"+dto);
        return model;
    }
}
