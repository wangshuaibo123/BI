package com.jy.modules.platform.sysmessage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jy.modules.platform.sysmessage.dto.SysMessageDTO;
import com.jy.modules.platform.sysmessage.dto.SysUserMsgRelationDTO;
import com.jy.modules.platform.sysmessage.util.CacheUtil;
import com.jy.modules.platform.sysmessage.util.MsgRestUtil;
import com.jy.modules.platform.sysmessage.util.MsgUtil;
import com.jy.platform.api.msg.Msg;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.core.message.ResponseStatus;

/**
 * @Description: 消息服务查询实现类
 * @author zhanglin
 * @date 2014年11月14日 下午3:43:56
 */
public class MsgApiGetImpl extends MsgAPImpl {
	
	
	private static final Logger logger = LoggerFactory.getLogger(MsgApiGetImpl.class);

	/**
	 * ======废弃
	 * 查询用户消息
	 * @param userId 用户ID
	 * @param systemFlag 系统标识 
	 * @param msgType 消息类型  0: 全局消息 ;1:专有消息
	 * @return
	 */
	@Deprecated
	@Override
	public List<Msg> getMsg(String userId, String systemFlag, String msgType) throws Exception {
		List<Msg> result = new ArrayList<Msg>(); 
		if (pool != null) {
			final boolean isGolbal = "0".equals(msgType)? true: false;
			if (isGolbal) handGlobalMsg(userId,systemFlag);
			String cacheRelKey = searchKeysFromCache(userId,systemFlag);
			if (StringUtils.isEmpty(cacheRelKey)) {
				List<SysMessageDTO> messageDatas = searchMsgFromDb(userId,systemFlag,msgType);
				if (messageDatas !=null && messageDatas.size() > 0) {
					for (SysMessageDTO messageDTO : messageDatas) {
						result.add(MsgUtil.beanChange(messageDTO));
					}
				}
			}else{
				String [] keyArray = cacheRelKey.replace("[", "").replace("]", "").split(",");
				for (String relKey : keyArray) {
					 String relValue = CacheUtil.get(pool,relKey.trim());
					 SysUserMsgRelationDTO relDto = (SysUserMsgRelationDTO)MsgUtil.parseTextToObject(relValue,SysUserMsgRelationDTO.class);
					 if (relDto!=null && "0".equals(relDto.getReadFlag()) && "0".equals(relDto.getRelStatus())) {
						 String keyOfMsg =  CacheUtil.KEY_PREFIX + ":" + relDto.getMsgId() + ":" + systemFlag + ":" + msgType;
						 if (StringUtils.isEmpty(CacheUtil.get(pool,keyOfMsg))) {
							ResponseMsg<SysMessageDTO> responseMsg = MsgRestUtil.getSysMessage(relDto.getMsgId());
							if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode()) && responseMsg.getResponseBody() !=null && msgType.equals(responseMsg.getResponseBody().getType())) {
								result.add(MsgUtil.beanChange(responseMsg.getResponseBody()));
							}
					 	 }else{
					 		 SysMessageDTO msgDto = (SysMessageDTO)MsgUtil.parseTextToObject(CacheUtil.get(pool,keyOfMsg),SysMessageDTO.class);
					 		 result.add(MsgUtil.beanChange(msgDto));
					 	 }
					}
				}
			  }
			}
		return result;
	}
	
	
	/**======废弃
	 * 处理全局消息
	 * @param userId
	 * @param systemFlag
	 * @throws Exception
	 */
	@Deprecated
	private void handGlobalMsg(String userId, String systemFlag)throws Exception{
		List<SysMessageDTO> sysMessageDTOs = searchGolbalMsg(systemFlag);
		if (sysMessageDTOs != null && sysMessageDTOs.size() > 0) {
			for (SysMessageDTO dto: sysMessageDTOs) {
				final String userMsgKey = CacheUtil.KEY_PREFIX_REL + ":" + userId  + ":" + systemFlag + ":" + dto.getMsgId();
				final boolean isExistRelation = CacheUtil.isExistKey(pool, userMsgKey);
				if (!isExistRelation) {
					SysUserMsgRelationDTO relationDTO = new SysUserMsgRelationDTO();
	    			relationDTO.setMsgId(dto.getMsgId());
	    			relationDTO.setReadFlag("0");
	    			relationDTO.setUserId(userId);
	    			relationDTO.setRelStatus("0");
	    			ResponseMsg<SysUserMsgRelationDTO> responseMsg = MsgRestUtil.addUserMsgRelation(relationDTO);
	    			if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())) {
	    				String userMsgEntityKey =  CacheUtil.KEY_PREFIX_REL + ":" + responseMsg.getResponseBody().getUserId() + ":" + systemFlag + ":" + responseMsg.getResponseBody().getMsgId();
						CacheUtil.put(pool,userMsgEntityKey, JSON.toJSONString(responseMsg.getResponseBody()));
					}
				}
				
			}
		}
	}
	
	/**======废弃
	 * 根据系统标识查询全局消息
	 * @param sysFlag
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	private List<SysMessageDTO> searchGolbalMsg(String sysFlag)throws Exception{
		List<SysMessageDTO> resultDtos = new ArrayList<SysMessageDTO>();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		SysMessageDTO dto = new SysMessageDTO();
    	dto.setSysFlag(sysFlag);
    	dto.setStatus("0");
    	dto.setType("0");
    	searchParams.put("dto", dto);
    	QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		ResponseMsg<QueryRespBean<SysMessageDTO>> responseMsg = MsgRestUtil.searchMsgByCondition(params);
		if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())) {
			resultDtos = responseMsg.getResponseBody().getResult();
		}
		return resultDtos;
	}
	
	/**======废弃
	 * 根据用户ID，系统标识，消息类型查询消息数据
	 * @param userId
	 * @param sysFlag
	 * @param msgType
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	private List<SysMessageDTO> searchMsgFromDb(String userId, String sysFlag, String msgType)throws Exception{
		List<SysMessageDTO> resultDtos = new ArrayList<SysMessageDTO>();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		SysMessageDTO dto = new SysMessageDTO();
    	dto.setSysFlag(sysFlag);
    	dto.setStatus("0");
    	dto.setType(msgType);
    	dto.setUserId(userId);
    	searchParams.put("dto", dto);
    	QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		ResponseMsg<QueryRespBean<SysMessageDTO>> responseMsg = MsgRestUtil.searchMsgByComplexCondition(params);
		if (ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())) {
			resultDtos = responseMsg.getResponseBody().getResult();
		}
		return resultDtos;
		
	}

	/**======废弃
	 * 根据用户ID，系统标识，查询缓存中符合条件的关系KEY
	 * @param userId 用户ID
	 * @param systemFlag 系统标识
	 * @return 关系KEY字符串
	 * @throws Exception
	 */
	@Deprecated
	private String searchKeysFromCache(String userId, String systemFlag)throws Exception{
		final String key =  CacheUtil.KEY_PREFIX_REL + ":" +  userId + ":" + systemFlag + ":" + "*";
		if (logger.isDebugEnabled()) {
			logger.debug("从缓存中获取用户[userId :" + userId +"] 消息  keys: "+ CacheUtil.getKeyByCondition(pool,key));
		}
		return  CacheUtil.getKeyByCondition(pool,key);
	}
}
