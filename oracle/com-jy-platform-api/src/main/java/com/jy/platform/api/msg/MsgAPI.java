package com.jy.platform.api.msg;

import java.util.List;
/**
 * @Description: TODO
 * @author zhanglin
 * @date 2014年11月13日 上午10:14:19
 */
public interface MsgAPI {

	
	/**
	 * @description 查询消息
	 * @param userId 用户ID
	 * @param systemFlag 系统标识
	 * @param msgType 消息类型 0：全局消息；1：专有消息
	 * @return
	 */
	public List<Msg> getMsg(String userId, String systemFlag, String msgType) throws Exception;
	
	/**
	 * 发布消息并且推送
	 * @param msg 消息实体
	 * @param msgType 消息类型 当msgtype=0时，为全局消息，userId失效，否则启用
	 * @param userId 目标用户ID字符串 格式1,2,3标识多人
	 * @param SystemFlag 系统标识
	 * @return 消息编号
	 */
	public boolean addMsg(Msg msg) throws Exception;
	
	
	/**
	 * 更新消息状态为已读
	 * @param msgId
	 * @param sysFlag
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean updateMsgHasRead(String msgId,String sysFlag,String userId)throws Exception;
	
	
	/**
	 * 更新消息状态为删除状态
	 * @param msgId 目标ID
	 * @param sysFlag 系统ID
	 * @currentLoginId 当前登陆系统的用户ID
	 * @return
	 * @throws Exception
	 */
	public boolean deleteMsg(String msgId,String sysFlag,String currentLoginId)throws Exception;
	
	
	
	
	/**
	 * 阅读者删除消息
	 * @param msgId 目标ID
	 * @param sysFlag 系统ID
	 * @currentLoginId 当前登陆系统的用户ID
	 * @return
	 * @throws Exception
	 */
	public boolean readerDeleteMsg(String msgId,String sysFlag,String currentLoginId)throws Exception;
}
