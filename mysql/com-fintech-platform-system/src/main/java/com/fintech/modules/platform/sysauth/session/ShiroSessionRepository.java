package com.fintech.modules.platform.sysauth.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
/**
 * @ClassName: ShiroSessionRepository 
 * @Description: session库操作接口
 * @author
 * @date 2015年6月2日 下午5:02:44 
 *
 */
public interface ShiroSessionRepository {
	/***
	 * @Title: saveSession 
	 * @Description: 保存会话
	 * @param  session    会话 
	 * @return void    返回类型 
	 */
	void saveSession(Session session);
	/***
	 * @Title: deleteSession 
	 * @Description: 删除会话
	 * @param  sessionId    会话id 
	 * @return void    返回类型 
	 */
	void deleteSession(Serializable sessionId);
	/***
	 * @Title: getSession 
	 * @Description: 获取会话
	 * @param  sessionId    会话id
	 * @return Session    会话 
	 */
	Session getSession(Serializable sessionId);
	/***
	 * @Title: getAllSessions 
	 * @Description: 获取会话集合
	 * @return Collection<Session>    会话集合
	 */
	Collection<Session> getAllSessions();
}
