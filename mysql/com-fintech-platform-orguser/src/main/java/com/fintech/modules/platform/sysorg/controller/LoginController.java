package com.fintech.modules.platform.sysorg.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.modules.platform.sysorg.service.SysUserService;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.restservice.web.base.BaseController;
import com.fintech.platform.tools.pwdhash.PasswordHashMatcher;
import com.fintech.platform.tools.pwdhash.util.AESUtils;
import com.fintech.platform.tools.pwdhash.util.HashUtils;
import com.fintech.platform.tools.pwdhash.util.RC4;

@SuppressWarnings("all")
@Controller
@RequestMapping("/user")
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

//	@Autowired
//	private JedisSentinelPool jedisSentinelPool;
	private PasswordHashMatcher passwordHashMatcher ;
	public LoginController(){
		passwordHashMatcher = SysUserService.initPassWordHashMatcher();
	}
	@RequestMapping(value="/toLogin")
    public ModelAndView toLogin(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
       return mv;
    }
	@RequestMapping(value="/toLoginReal")
    public ModelAndView toLoginR(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorInfo", this.getParameterString("errorInfo"));
		mv.setViewName("loginReal");
       return mv;
    }
	
	@RequestMapping(value="/caslogin")
	public void caslogin(HttpServletRequest request) {
		logger.debug("CAS登录,不进入此方法====================");
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(HttpServletRequest request) {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ismulpwd = request.getParameter("ismulpwd");
		if(StringUtils.isEmpty(username)) {
			request.setAttribute("errorInfo", "用户名不能为空!");
			return "login";
		}
		if(StringUtils.isEmpty(password)) {
			request.setAttribute("errorInfo", "密码不能为空!");
			return "login";
		}
		//直连redis 验证用户密码
		//解密
        String decryptLonginPwd = null;
		try {
			decryptLonginPwd = RC4.decrypt(password, "qhcjr01234567890");
		} catch (Exception e) {
			e.printStackTrace();
		}

        //String aesPwd = AESUtils.aesEncryptStr(decryptLonginPwd, "qhcjr01234567890"); // CAS和主数据间约定的加密方式
        byte[] oldPassWordhash = passwordHashMatcher.hash(decryptLonginPwd.getBytes());
        if("chenGnag".equals(ismulpwd)){
        	
        }else{
        	password = HashUtils.base64EncodeStr(oldPassWordhash);
        }
		
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(
				username, password);
		try {
			currentUser.login(token);
		} catch (UnknownAccountException uae) {
			logger.error("用户名不存在系统！username="+username);
			request.setAttribute("errorInfo", "用户名不存在系统！");
		} catch (IncorrectCredentialsException ice) {
			logger.error("密码错误！username="+username);
			request.setAttribute("errorInfo", "密码错误!");
		} catch (LockedAccountException lae) {
			logger.error("用户已经被锁定不能登录，请与管理员联系！username="+username);
			request.setAttribute("errorInfo", "用户已经被锁定不能登录，请与管理员联系!");
		} catch (ExcessiveAttemptsException eae) {
			logger.error("错误次数过多！username="+username);
			request.setAttribute("errorInfo", "错误次数过多！");
		} catch (AuthenticationException ae) {
			logger.error("登录失败，用户名或密码错误！username="+username);
			request.setAttribute("errorInfo", "登录失败，用户名或密码错误!");
		}
		// 验证是否成功登录的方法
		if (currentUser.isAuthenticated()  || checkOldPWD(username,password,ismulpwd)) {
			//获取用户的菜单信息
			setSession("menu", "");
			UserInfo userInfo = (UserInfo)currentUser.getSession().getAttribute("userInfo");
			if (logger.isInfoEnabled()) {
				logger.info("登录成功");
				logger.info("登录信息："+userInfo.toString());
			}
			
			return "redirect:/user/home";
		}
		return "login";
	}
	
	private boolean checkOldPWD(String username, String password,String ismulpwd) {
	    boolean flag =false;
	    String decryptLonginPwd = null;
        try {
            decryptLonginPwd = RC4.decrypt(password, "jieyue1234567890");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //String aesPwd = AESUtils.aesEncryptStr(decryptLonginPwd, "qhcjr01234567890"); // CAS和主数据间约定的加密方式
        byte[] oldPassWordhash = passwordHashMatcher.hash(decryptLonginPwd.getBytes());
        if("chenGnag".equals(ismulpwd)){
            
        }else{
            password = HashUtils.base64EncodeStr(oldPassWordhash);
        }
        
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(
                username, password);
        try {
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            logger.error("用户名不存在系统！username="+username);
        } catch (IncorrectCredentialsException ice) {
            logger.error("密码错误！username="+username);
        } catch (LockedAccountException lae) {
            logger.error("用户已经被锁定不能登录，请与管理员联系！username="+username);
        } catch (ExcessiveAttemptsException eae) {
            logger.error("错误次数过多！username="+username);
        } catch (AuthenticationException ae) {
            logger.error("登录失败，用户名或密码错误！username="+username);
        }
        if(currentUser.isAuthenticated()){
            flag = true;
        }
        return flag;
    }
    @RequestMapping(value="/logout")
    public String logout() {
       Subject currentUser = SecurityUtils.getSubject();
       currentUser.logout();
       return "login";
    }
	
	 @RequestMapping(value="/home")
	 public String index() {
	      return "home";
	 }
	 
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null) {
			Session session = currentUser.getSession();
			if (session != null) {
				session.setAttribute(key, value);
			}
		}
	}

	private Object getSession(Object key) {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser != null) {
			Session session = currentUser.getSession();
			if (session != null) {
				return session.getAttribute("userInfo");
			}
		}
		return null;
	}

}
