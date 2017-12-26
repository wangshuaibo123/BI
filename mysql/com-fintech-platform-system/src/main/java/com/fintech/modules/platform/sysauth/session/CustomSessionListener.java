package com.fintech.modules.platform.sysauth.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @ClassName: CustomSessionListener 
 * @Description: session监听器 
 * @author
 * @date 2015年6月2日 下午4:51:22 
 *
 */
public class CustomSessionListener implements SessionListener {
	private static final Logger log = LoggerFactory.getLogger(CustomSessionListener.class);  
    private ShiroSessionRepository shiroSessionRepository;

    @Override
    public void onStart(Session session) {
        log.debug("session ["+session.getId()+"] on start");
    }

    @Override
    public void onStop(Session session) {
        log.debug("session ["+session.getId()+"] on stop");
    }

    @Override
    public void onExpiration(Session session) {
    	log.debug("session ["+session.getId()+"] onExpiration");
        shiroSessionRepository.deleteSession(session.getId());
    }

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }
}
