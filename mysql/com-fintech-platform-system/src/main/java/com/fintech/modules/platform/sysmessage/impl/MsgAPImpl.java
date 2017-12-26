package com.fintech.modules.platform.sysmessage.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.fintech.platform.api.msg.Msg;
import com.fintech.platform.api.msg.MsgAPI;
import com.fintech.platform.core.redis.JedisSentinelPool;

/**
 * @Description: 消息服务实现类,具体功能实现由子类实现
 * @author
 * @date 2014年11月13日 上午10:24:35
 */
@Service("com.fintech.modules.platform.sysmessage.impl.MsgAPImpl")
public abstract class MsgAPImpl implements MsgAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(MsgAPImpl.class);
	
	protected static final String GLOBAL_MSG = "0";//全局消息
	
	protected static final String SPECIFIC_MSG = "1";//专有消息
	
	/*@Autowired
	private JedisSentinelPool pool;*/

	protected static JedisSentinelPool pool;
	
	static{
		if (logger.isDebugEnabled()) {
			logger.debug("load spring-redis.xml init redis pools.");
		}
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-redis.xml");
		pool = (JedisSentinelPool) context.getBean("jedisSentinelPool");
	}
	
	/**
	 * 由具体子类实现
	 */
	@Override
	public boolean addMsg(Msg msg) throws Exception{return false;};
	
	/**
	 * 由具体子类实现
	 */
	@Override
	public List<Msg> getMsg(String userId, String systemFlag, String msgType)throws Exception {return null;}

	
	
	/**
	 * 由具体子类实现
	 */
	@Override
	public boolean updateMsgHasRead(String msgId,String sysFlag,String userId) throws Exception {return false;}

	
	/**
	 * 由具体子类实现
	 */
	@Override
	public boolean deleteMsg(String msgId,String sysFlag,String currentLoginId)throws Exception{return false;}
	
	/**
	 * 由具体子类实现
	 */
	@Override
	public boolean readerDeleteMsg(String msgId,String sysFlag,String currentLoginId)throws Exception{return false;}
}
