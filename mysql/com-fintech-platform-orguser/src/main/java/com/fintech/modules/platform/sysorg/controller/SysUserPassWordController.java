package com.fintech.modules.platform.sysorg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.modules.platform.sysorg.service.SysUserService;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.restservice.web.base.BaseController;
import com.fintech.platform.tools.pwdhash.PasswordHashMatcher;
import com.fintech.platform.tools.pwdhash.util.HashUtils;

/**
 * @description: 
 * @author: xujunqi
 * @date: 2016年9月5日  下午4:08:31
 * TODO 后续考虑认证风暴
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/sysUserPassWord")
public class SysUserPassWordController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SysUserPassWordController.class);
	
	private PasswordHashMatcher passwordHashMatcher ;
	public SysUserPassWordController(){
		passwordHashMatcher = SysUserService.initPassWordHashMatcher();
	}
	 @Autowired
	 private SysUserService userService;	 
	 
	 
	//@Autowired
	//@Qualifier("com.pt.modules.platform.redisuser.service.UserDataService_platform")
	//private UserDataService redisUser;
	
	//@Autowired
	//@Qualifier("com.pt.modules.platform.ldap.service.UserLdapImpl_platform")
	//public LdapUserAPI ldapUserAPI;
	
	@Autowired
	private SysConfigAPI sysConfigAPI;
	
	
	/*@Autowired
	private SysuserDataCacheService sysuserDataCacheService;*/
	 
	/**
	 * 
	 * 跳转至用户修改密码的页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toModifyPassWord")
    public ModelAndView toQuerySynList(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		String userName=(String)this.getParameter("username");
		model.addObject("userName", userName);
		model.setViewName("modifyPassword");
       return model;
    }
	
	@RequestMapping(value="/modifyPassWord")
    public ModelAndView modifyPassWord(HttpServletRequest request)throws Exception {
		ModelAndView model = new ModelAndView();
		String loginName = (String) this.getParameter("loginName");
		try{
			
			//查他们的主键
			String id = getIdByLoginName(loginName);

			String newPwdInput = (String) this.getParameter("newPwdInput");
			//newPwdInput="111111";
			SysUserDTO  sysUser=userService.querySysUserByPrimaryKey(id);
			//对密码加密 然后存进数据库
			byte[] passWordhash = passwordHashMatcher.hash(newPwdInput.getBytes());
			String hashPwd = HashUtils.base64EncodeStr(passWordhash);
			sysUser.setPassword(hashPwd);
			//sysUser.setPassword(newPwdInput);
			String value=sysConfigAPI.getValue("LDAPONOFF");
			userService.modifySysUserPassWord(sysUser);
			model.setViewName("modifyPasswordSuccess");
			
			
		}catch(Exception e){
			model.setViewName("modifyPasswordfail");
			e.printStackTrace();
		}		
		return model;
    }
	
	@RequestMapping(value="/checkOldPassWord")
	@ResponseBody
    public DataMsg checkOldPassWord(HttpServletRequest request,
			@ModelAttribute DataMsg dataMsg) throws Exception{
		BaseDTO dto = super.initDto(null);
		String oldPassWord = (String) this.getParameter("oldPassWord");
		String loginName = (String) this.getParameter("loginName");
		dataMsg = super.initDataMsg(dataMsg);
		// 查他们的主键
		String id = getIdByLoginName(loginName);
		SysUserDTO  sysUser=userService.querySysUserByPrimaryKey(id);
		
		if(sysUser==null||sysUser.getLoginName()==null||sysUser.getLoginName()==""){
			dataMsg.setMsg("该用户名不存在");
			dataMsg.setStatus("fail");
			return dataMsg;
		}
		
		byte[] oldPassWordhash = passwordHashMatcher.hash(oldPassWord.getBytes());
		oldPassWord = HashUtils.base64EncodeStr(oldPassWordhash);
		if(!oldPassWord.equals(sysUser.getPassword())){
			//旧密码不正确
			dataMsg.setMsg("原始密码不正确");
			dataMsg.setStatus("fail");
		}else{
			//旧密码正确
			dataMsg.setStatus("ok");
		}
		return dataMsg;
	}
	
	@RequestMapping(value = "/updateUserPWDInit")
    @ResponseBody
    public DataMsg updateUserPWDInit(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	String userIds = request.getParameter("userIds");
    	String pwd = request.getParameter("pwd");
    	String [] userId = userIds.split(",");
    	StringBuffer buffer = new StringBuffer();
    	String id = null;
    	try {
    		for(int i=0;i<userId.length;i++){
    			id = userId[i];
    			SysUserDTO user = userService.querySysUserFullByPrimaryKey(id);
        		if(user.getId()==null){
        			//用户不存在的，返回提示信息
        			buffer.append(id + "不存在;").append("\n\t");
        		}else{
        			user.setPassword(pwd);
        			user.setIsChange("1");
        			userService.updateSysUser(user);
        		}
    		}
		} catch (Exception e) {
			logger.error("updateUserPWDInit>>>初始化密码:" + e.getMessage());
		}finally{
			if(buffer.toString().length() ==0){
				dataMsg.setMsg("密码初始化成功");
				dataMsg.setMsg("");
			}else{
				//dataMsg.setMsg();
				dataMsg.failed(buffer.toString());
			}
	    	return dataMsg;
		}
    }
	
	private String getIdByLoginName(String loginName){
		//查他们的主键
		String id = "-1";
		try {
			int tempID = Integer.parseInt(loginName);
			id = String.valueOf(tempID);
		} catch (Exception e1) {
			//有异常说明不是数字
			Map searchParams = new HashMap();
			SysUserDTO dto  = new SysUserDTO();
			dto.setLoginName(loginName);
			searchParams.put("dto",dto);
			List<SysUserDTO> user=userService.searchByParam(searchParams);
			if(user!=null && user.size()>0){
				id=user.get(0).getId().toString();
			}
		}

		return id;
	}
}
