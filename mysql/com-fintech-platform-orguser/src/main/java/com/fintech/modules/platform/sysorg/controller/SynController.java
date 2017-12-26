package com.fintech.modules.platform.sysorg.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.platform.restservice.web.base.BaseController;

@SuppressWarnings("all")
@Controller
@RequestMapping("/syn")
public class SynController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SynController.class);

//	@Autowired
//	private JedisSentinelPool jedisSentinelPool;
	
	/**
	 * 
	 * 跳转同步查询页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toQuerySynList")
    public ModelAndView toQuerySynList(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		//ParseXmlUtil.getFamilyMemebers();
		//ParseXmlUtil.insert();
		//ParseXmlUtil.update();
		model.setViewName("platform/sysorg/sysOrgUser/querySysOrgUser");
       return model;
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
