package com.fintech.modules.platform.sysmessage.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.fintech.modules.platform.sysmessage.dto.SysMessageDTO;
import com.fintech.modules.platform.sysmessage.dto.SysUserMsgRelationDTO;
import com.fintech.modules.platform.sysmessage.util.CacheUtil;
import com.fintech.modules.platform.sysmessage.util.MsgRestUtil;
import com.fintech.modules.platform.sysmessage.util.MsgUtil;
import com.fintech.platform.api.msg.Msg;
import com.fintech.platform.api.msg.MsgStatus;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.core.message.ResponseStatus;

/**
 * @Description: 消息服务修改实现
 * @author
 * @date 2014年11月14日 下午3:56:36
 */
public class MsgApiUpdateImpl extends MsgAPImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(MsgApiUpdateImpl.class);
	
	/**
	 * <p>更新消息状态为已读
	 */
	@Override
	public boolean updateMsgHasRead(String msgId,String sysFlag,String userId) throws Exception {
		final String relationKey = CacheUtil.KEY_PREFIX_REL + ":" + userId + ":" + sysFlag + ":" + msgId;
		ResponseMsg<SysMessageDTO> result = MsgRestUtil.getSysMessage(Long.parseLong(msgId));
		if (ResponseStatus.HTTP_OK.equals(result.getRetCode()) && result.getResponseBody() !=null) {
			SysMessageDTO messageDTO = result.getResponseBody();
			final boolean isGlobalMsg = GLOBAL_MSG.equals(messageDTO.getType()) ? true : false;
			final boolean isSpecificMsg = SPECIFIC_MSG.equals(messageDTO.getType()) ? true : false;
			if (isGlobalMsg) handlerGlobalMsg(msgId,userId,relationKey);
			if (isSpecificMsg) handlerSpecificMsg(msgId,userId,relationKey);
			return true;
		}
		return false;
	}
	
	/**
	 * 处理全局消息
	 * @param msgId 消息ID
	 * @param userId 用户ID
	 * @param cacheKey 缓存中关系KEY
	 * @throws Exception
	 */
	private void handlerGlobalMsg(String msgId,String userId,String cacheKey) throws Exception{
		
	    final boolean isExistRelation = isExistUserMsgRelation(msgId,userId);
		SysUserMsgRelationDTO relationDTO = new SysUserMsgRelationDTO();
		relationDTO.setMsgId(Long.parseLong(msgId));
		relationDTO.setReadFlag(MsgStatus.HASREAD.toString());
		relationDTO.setUserId(userId);
		relationDTO.setRelStatus(MsgStatus.INIT.toString());              //状态为有效
		if (isExistRelation) {
			ResponseMsg<SysUserMsgRelationDTO> responseMsg = MsgRestUtil.updateUserMsgRelation(relationDTO);
			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()) && responseMsg.getResponseBody() !=null) {
				//该操作废弃---不再向redis中维护数据
				//CacheUtil.put(pool,cacheKey, JSON.toJSONString(responseMsg.getResponseBody()));
			}
		}else {
			ResponseMsg<SysUserMsgRelationDTO> responseMsg = MsgRestUtil.addUserMsgRelation(relationDTO);
			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()) && responseMsg.getResponseBody() !=null) {
				//该操作废弃---不再向redis中维护数据
				//CacheUtil.put(pool,cacheKey, JSON.toJSONString(responseMsg.getResponseBody()));
			}
		}
		
	}
	
	/**
	 * 验证是否存在用户消息关系
	 * @param msgId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private boolean isExistUserMsgRelation(String msgId,String userId)throws Exception{
		boolean flag = false;
		Map<String, Object> searchParams = new HashMap<String, Object>();
		SysUserMsgRelationDTO dto = new SysUserMsgRelationDTO();
		dto.setMsgId(Long.parseLong(msgId));
		dto.setUserId(userId);
    	searchParams.put("dto", dto);
    	QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>> response= MsgRestUtil.searchMsgRelationData(params);
		List<SysUserMsgRelationDTO> resultList = response.getResponseBody().getResult();
		if (ResponseStatus.HTTP_OK.equals(response.getRetCode()) && resultList!=null && resultList.size() > 0 ) {
			flag = true;
		}
		return flag;
	}
	
	
	private void handlerSpecificMsg(String msgId,String userId,String cacheKey) throws Exception{
		SysUserMsgRelationDTO relationDTO = new SysUserMsgRelationDTO();
		relationDTO.setUserId(userId);
		relationDTO.setMsgId(Long.parseLong(msgId));
		relationDTO.setReadFlag(MsgStatus.HASREAD.toString());
		ResponseMsg<SysUserMsgRelationDTO> responseMsg = MsgRestUtil.updateUserMsgRelation(relationDTO);
		if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()) && responseMsg.getResponseBody() !=null) {
			//该操作废弃---不再向redis中维护数据
			//CacheUtil.put(pool, cacheKey, JSON.toJSONString(responseMsg.getResponseBody()));
		}
	}
	
	

	@Deprecated
	public boolean updateMsgState(String[] msgIds,String sysflag) throws Exception {
		boolean flag = false;
		for (String msgId : msgIds) {
			SysMessageDTO dto = new SysMessageDTO();
			dto.setMsgId(Long.parseLong(msgId));
			dto.setStatus(MsgStatus.INVALID.toString());//失效
			ResponseMsg<Void> result = MsgRestUtil.updateMsgState(dto); 
			if (ResponseStatus.HTTP_OK.equals(result.getRetCode())) {
				Msg msg = new Msg();
				msg.setId(msgId);
				final String []  msgType = {"0","1"};
				for (int i = 0; i < msgType.length; i++) {
					updateRelationFromCache(msgId,sysflag, msgType[i]);
				}
			}
		}
		
		return flag;
	}
	
	/**
	 * 更新缓存中关系状态字段
	 * @param msgId 消息ID
	 * @param sysflag 系统标识
	 * @param msgType 消息类型
	 * @throws Exception
	 */
	@Deprecated
	private void updateRelationFromCache(String msgId,String sysflag,String msgType)throws Exception{
		String key = CacheUtil.KEY_PREFIX + ":" + msgId +":"+ sysflag +":"+msgType;
		if (CacheUtil.isExistKey(pool, key)) {
			String updateBefore = CacheUtil.get(pool, key); 
			SysUserMsgRelationDTO relationDTO = MsgUtil.parseTextToObject(updateBefore,SysUserMsgRelationDTO.class);
			relationDTO.setRelStatus(MsgStatus.INVALID.toString());
			CacheUtil.put(pool, key, JSON.toJSONString(relationDTO));
			if (logger.isDebugEnabled()) {
				logger.debug("==============更新缓存关系数据状态为失效状态============");
				logger.debug("更新前===" + updateBefore);
				logger.debug("更新后===" + CacheUtil.get(pool, key));
			}
		}
	}

	
	

}
