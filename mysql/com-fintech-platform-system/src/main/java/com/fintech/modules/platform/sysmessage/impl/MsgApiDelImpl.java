package com.fintech.modules.platform.sysmessage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.modules.platform.sysmessage.dto.SysMessageDTO;
import com.fintech.modules.platform.sysmessage.dto.SysUserMsgRelationDTO;
import com.fintech.modules.platform.sysmessage.util.CacheUtil;
import com.fintech.modules.platform.sysmessage.util.MsgRestUtil;
import com.fintech.platform.api.msg.MsgStatus;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.core.message.ResponseStatus;

/**
 * @Description: 消息服务删除实现类
 * @author
 * @date 2014年11月14日 下午3:59:40
 */
public class MsgApiDelImpl extends MsgAPImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(MsgApiDelImpl.class);
	
	
	/**
	 * 删除消息，实为修改消息及关系状态为删除状态
	 */
	@Override
	public boolean deleteMsg(String msgId, String sysFlag,String loginId)
			throws Exception {
		boolean flag = false;
		ResponseMsg<SysMessageDTO> result = MsgRestUtil.getSysMessage(Long.parseLong(msgId));
		if (ResponseStatus.HTTP_OK.equals(result.getRetCode()) && result.getResponseBody() !=null) {
			SysMessageDTO messageDTO = result.getResponseBody();
			final boolean isPublisher = messageDTO.getPublisher().equals(loginId) ? true : false;
			final boolean isGlobalMsg = GLOBAL_MSG.equals(messageDTO.getType()) ? true : false;
			final boolean isSpecificMsg = SPECIFIC_MSG.equals(messageDTO.getType()) ? true : false;
			if (isPublisher) {
				messageDTO.setStatus(MsgStatus.DELETE.toString());
				//发布者删除消息
				publisherDeleteMsg(messageDTO,msgId,sysFlag);
				//发布者删除关系 要将所有关系删掉
				handlerAllMsgRelation(msgId,sysFlag);
			}else{
				 handlerSpecificUserMsgRelation(msgId,loginId,sysFlag);
			}
				return true;
			}
		
		return flag;
	}

	
	/**
	 * 发布者更新消息实体
	 * @param messageDTO
	 * @param msgId
	 * @param sysFlag
	 * @throws Exception
	 */
	private void publisherDeleteMsg(SysMessageDTO messageDTO,String msgId, String sysFlag)throws Exception{
		ResponseMsg<SysMessageDTO> modifiedMsg = MsgRestUtil.updateMsg(messageDTO);
		if (ResponseStatus.HTTP_OK.equals(modifiedMsg.getRetCode()) && modifiedMsg.getResponseBody() !=null) {
			//废弃该操作--不再向redis中维护数据
			/*SysMessageDTO modifiedDto = modifiedMsg.getResponseBody();
			String msgKey = CacheUtil.KEY_PREFIX + ":" + msgId + ":" + sysFlag + ":" + modifiedDto.getType();
			if (CacheUtil.isExistKey(pool, msgKey)) {
				CacheUtil.put(pool, msgKey, JSON.toJSONString(modifiedDto));
			}*/
		}
	}
	


	/**
	 * 处理全局消息
	 * @param msgId 消息ID
	 * @param userId 用户ID
	 * @param cacheKey 缓存中关系KEY
	 * @throws Exception
	 */
	private void handlerAllMsgRelation(String msgId,String sysFlag) throws Exception{
		List<SysUserMsgRelationDTO> resultDtos = new ArrayList<SysUserMsgRelationDTO>();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		SysUserMsgRelationDTO relationDTO = new SysUserMsgRelationDTO();
		relationDTO.setMsgId(Long.parseLong(msgId));
    	searchParams.put("dto", relationDTO);
    	QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		ResponseMsg<QueryRespBean<SysUserMsgRelationDTO>> responseMsg = MsgRestUtil.searchMsgRelationData(params);
		if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()) && responseMsg.getResponseBody()!=null) {
			resultDtos = responseMsg.getResponseBody().getResult();
			for (SysUserMsgRelationDTO relation : resultDtos) {
				relation.setRelStatus(MsgStatus.DELETE.toString());
				//relation.setReadFlag(MsgStatus.HASREAD.toString());
				ResponseMsg<SysUserMsgRelationDTO> response = MsgRestUtil.updateUserMsgRelation(relation);
				if (ResponseStatus.HTTP_OK.equals(response.getRetCode()) && response.getResponseBody()!=null) {
					//废弃该操作--不再向redis中维护数据
					/*String relationKey = CacheUtil.KEY_PREFIX_REL + ":"+response.getResponseBody().getUserId()+":" + sysFlag + ":" + msgId;
					if (CacheUtil.isExistKey(pool, relationKey)) {
						CacheUtil.put(pool,relationKey, JSON.toJSONString(response.getResponseBody()));
					}*/
				}
			}
		}
		
	}
	/**
	 * 处理专有消息
	 * @param msgId
	 * @param userId
	 * @param cacheKey
	 * @throws Exception
	 */
	private void handlerSpecificUserMsgRelation(String msgId,String userId,String sysFlag) throws Exception{
		SysUserMsgRelationDTO relationDTO = new SysUserMsgRelationDTO();
		relationDTO.setUserId(userId);
		relationDTO.setMsgId(Long.parseLong(msgId));
		relationDTO.setRelStatus(MsgStatus.DELETE.toString());
		//relationDTO.setReadFlag(MsgStatus.HASREAD.toString());
		ResponseMsg<SysUserMsgRelationDTO> responseMsg = MsgRestUtil.updateUserMsgRelation(relationDTO);
		if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()) && responseMsg.getResponseBody() !=null) {
			//废弃该操作--不再向redis中维护数据
			/*String relationKey = CacheUtil.KEY_PREFIX_REL + ":" + userId + ":" + sysFlag + ":" + msgId;
			CacheUtil.put(pool, relationKey, JSON.toJSONString(responseMsg.getResponseBody()));*/
		}
	}
	
	/**
	 * 批量删除
	 * @param msgIds
	 * @param sysFlag
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean readerBatchDelMsg(String [] msgIds,String sysFlag,String userId)throws Exception{
		
		for (int i = 0; i < msgIds.length; i++) {
			readerDeleteMsg(msgIds[i], sysFlag, userId);
		}
		
		return false;
	}
	
	
	
	/**
	 * 由具体子类实现
	 */
	@Override
	public boolean readerDeleteMsg(String msgId,String sysFlag,String userId)throws Exception{
		boolean result = false;
		try {
			 final boolean isExistRelation = isExistUserMsgRelation(msgId,userId);
			SysUserMsgRelationDTO relationDTO = new SysUserMsgRelationDTO();
			relationDTO.setUserId(userId);
			relationDTO.setMsgId(Long.parseLong(msgId));
			relationDTO.setRelStatus(MsgStatus.DELETE.toString());
			String relationKey = CacheUtil.KEY_PREFIX_REL + ":" + userId + ":" + sysFlag + ":" + msgId;
			if (isExistRelation) {
				ResponseMsg<SysUserMsgRelationDTO> responseMsg = MsgRestUtil.updateUserMsgRelation(relationDTO);
				if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()) && responseMsg.getResponseBody() !=null) {
					//废弃该操作--不再向redis中维护数据
					//CacheUtil.put(pool,relationKey, JSON.toJSONString(responseMsg.getResponseBody()));
				}
			}else {
				ResponseMsg<SysUserMsgRelationDTO> responseMsg = MsgRestUtil.addUserMsgRelation(relationDTO);
				if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()) && responseMsg.getResponseBody() !=null) {
					//废弃该操作--不再向redis中维护数据
					//CacheUtil.put(pool,relationKey, JSON.toJSONString(responseMsg.getResponseBody()));
				}
			}
			result = true;
		} catch (Exception e) {
			result =false;
			if (logger.isDebugEnabled()) {
				logger.debug("阅读者删除异常",e);
			}
		}
		return result;
		
	}
	
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
		if (ResponseStatus.HTTP_OK.equals(response.getRetCode()) && response.getResponseBody().getResult()!=null) {
			flag = true;
		}
		return flag;
	}
}
