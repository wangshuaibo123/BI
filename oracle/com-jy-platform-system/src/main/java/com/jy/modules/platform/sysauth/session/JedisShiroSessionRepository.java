package com.jy.modules.platform.sysauth.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.util.SafeEncoder;

import com.jy.platform.core.ehcache.ObtainPropertiesInfo;

/**
 * @ClassName: JedisShiroSessionRepository 
 * @Description: redis session库 
 * @author dell
 * @date 2015年6月2日 下午4:56:48 
 *
 */
public class JedisShiroSessionRepository  implements
		ShiroSessionRepository {
	private static final Logger log = LoggerFactory.getLogger(JedisShiroSessionRepository.class);  
	private static final String REDIS_SHIRO_SESSION = "shiro-session:";
    private static final int SESSION_VAL_TIME_SPAN = 18000;
    private static final int DB_INDEX = 0;
    private static final String APP_CODE = ObtainPropertiesInfo.getValByKey("app.code");
    private static int POOL_SIZE = 2;
	private static int cpuNums = Runtime.getRuntime().availableProcessors();
    private static ExecutorService exeSessionPool = Executors.newFixedThreadPool(cpuNums * POOL_SIZE *5);
	
    /***redis管理服务***/
    private JedisManager jedisManager;
    /**
	 * @Title: saveSession 
	 * @Description: 保存session
	 * @param  Session 会话
	 * @return void    返回类型 
	 */
    @Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
        	
            byte[] key = SafeEncoder.encode(buildRedisSessionKey(session.getId()));
            byte[] value = SerializeUtil.serialize(session);
            long sessionTimeOut = session.getTimeout() / 1000;
            //Long expireTime = sessionTimeOut + SESSION_VAL_TIME_SPAN + (5 * 60);
            Long expireTime = sessionTimeOut;
            log.debug("saveSession ["+session.getId()+"] expireTime "+expireTime+" seconds");
            getJedisManager().saveValueByKey(DB_INDEX, key, value, expireTime.intValue());
        } catch (Exception e) {
            log.error("save session error ",e);
        }
    }
    /**
	 * @Title: deleteSession 
	 * @Description: 删除session
	 * @param  Serializable 会话id
	 * @return void    返回类型 
	 */
    @Override
    public void deleteSession(Serializable id) {
        if (id == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            getJedisManager().deleteByKey(DB_INDEX,
            		SafeEncoder.encode(buildRedisSessionKey(id)));
        } catch (Exception e) {
            log.error("delete session error",e);
        }
    }
    /**
	 * @Title: getSession 
	 * @Description: 得到session
	 * @param  Serializable 会话id
	 * @return Session    会话 
	 */
    @Override
    public Session getSession(Serializable id) {
        if (id == null)
            throw new NullPointerException("session id is empty");
        Session session = null;
        try {
            byte[] value = getJedisManager().getValueByKey(DB_INDEX, SafeEncoder.encode(buildRedisSessionKey(id)));
            session = SerializeUtil.deserialize(value, Session.class);
            if(session == null || session.getTimeout() == 0){
            	log.warn("session is timeout or invalid ["+id+"]");
            	return null;
            }
            long expireTime = session.getTimeout()/ 1000;
            log.debug("getSession ["+session.getId()+"] expireTime "+expireTime+" seconds");
        }catch (Exception e) {
            log.error("get session error",e);
        }
        return session;
    }
    /**
	 * @Title: getAllSessions 
	 * @Description: 得到session集合
	 * @return Collection<Session>    会话集合 
	 */
    @Override
    public Collection<Session> getAllSessions() {
    	log.debug("session getAllSessions ");
        final Vector<Session> sessionVector = new Vector<Session>();
        try {
            Set<byte[]> byteKeys = getJedisManager().getKeys(DB_INDEX,SafeEncoder.encode(buildRedisSessionKey("*")));
            
            if (byteKeys != null && byteKeys.size() > 0) {
            	System.out.println("===========count:"+byteKeys.size());
            	final CountDownLatch countDown = new CountDownLatch(byteKeys.size());//线程计数器
            	for (final byte[] bs : byteKeys) {
                    //多线程获取 value
                	exeSessionPool.execute(new Runnable() {
    					public void run() {
    						try {
								Session s = SerializeUtil.deserialize(getJedisManager().getValueByKey(DB_INDEX,bs),Session.class);  
								sessionVector.add(s);
							} catch (Exception e) {
								log.error("========获取redis shiro session error====",e);
							}
    						countDown.countDown();//--
    					}
    				});
        		}
        		
        		countDown.await();//等待子线程都执行完了再执行主线程剩下的动作
        		//exeSessionPool.shutdown();
            }
        } catch (Exception e) {  
        	log.error("get getAllSessions error",e);
        } 
        log.debug("session getAllSessions size ["+sessionVector.size()+"]");
        System.out.println("=======sessionVector.size========="+sessionVector.size());
        return sessionVector;  
    }
    /**
     * @Title: buildRedisSessionKey 
     * @Description: 组织会话id
     * @param  sessionId 会话id
     * @return String    组织会话id
     * @throws
     */
    private String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + APP_CODE + ":" + sessionId;//按系统区分session
    	//return REDIS_SHIRO_SESSION + sessionId;
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }

}
