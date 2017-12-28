package com.jy.modules.platform.sysauth.service.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.common.JYLoggerUtil;

public class SessionAPImpl implements SessionAPI {

	@Override
	public UserInfo getCurrentUserInfo() {
		Subject currentSubject = SecurityUtils.getSubject();
		UserInfo currentUser = null;
		try {
			currentUser = (UserInfo) currentSubject.getSession().getAttribute("userInfo");
		} catch (Exception e) {
			// 如果有异常则说明是 通过后台定时任务 获取当前系统登录人
			JYLoggerUtil.info(SessionAPImpl.class, "==error=获取系统当前登录人失败=");
		}
		if (currentUser == null) {
			// 如果没有获取到 返回 1以便支持 后台定时任务的获取
			currentUser = new UserInfo();
			currentUser.setUserId(-1L);
			currentUser.setOrgName("-1");
			currentUser.setOrgId(-1L);
			currentUser.setOrgName("-1");
			currentUser.setLoginName("-1");
		}
		return currentUser;
	}

}
