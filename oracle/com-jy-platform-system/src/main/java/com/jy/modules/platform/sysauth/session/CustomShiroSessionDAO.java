package com.jy.modules.platform.sysauth.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @ClassName: CustomShiroSessionDAO 
 * @Description: session dao持久化操作session
 * @author luoyr
 * @date 2015年6月2日 下午4:51:36 
 *
 */
public class CustomShiroSessionDAO extends AbstractSessionDAO {
	private static final Logger log = LoggerFactory.getLogger(CustomShiroSessionDAO.class);  
	private ShiroSessionRepository shiroSessionRepository;

    @Override
    public void update(Session session) throws UnknownSessionException {
    	/*if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
    		log.debug("session is valid,will not update");
    		return; //如果会话过期/停止
    	}*/
        getShiroSessionRepository().saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null) {
            return;
        }
        Serializable id = session.getId();
        if (id != null) {
            getShiroSessionRepository().deleteSession(id);
        }
        //TODO if session is too large,when session destory clear shiro cache
    }

    @Override
    public Collection<Session> getActiveSessions() {
    	//session 有效性交给redis 处理
        //return getShiroSessionRepository().getAllSessions();
    	return null;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        getShiroSessionRepository().saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return getShiroSessionRepository().getSession(sessionId);
    }

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(
            ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }
}