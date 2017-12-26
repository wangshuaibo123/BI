package com.fintech.modules.platform.sysmessage.impl;

import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.platform.sysmessage.dto.SysMessageDTO;
import com.fintech.modules.platform.sysmessage.dto.SysUserMsgRelationDTO;
import com.fintech.modules.platform.sysmessage.util.MsgRestUtil;
import com.fintech.modules.platform.sysmessage.util.MsgUtil;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.platform.api.msg.Msg;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.core.message.ResponseStatus;
import com.fintech.platform.restclient.http.RestClient;
import com.fintech.platform.restclient.http.RestClientConfig;

/**
 * @Description: 消息发布服务实现类
 * @author
 * @date 2014年11月14日 下午3:25:37
 */
public class MsgApiAddImpl extends MsgAPImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(MsgApiAddImpl.class);
	
	/**
	 * 发布消息
	 */
	@Override
	public boolean addMsg(Msg msg) throws Exception {
		boolean result = false;
		if (logger.isDebugEnabled()) {
			logger.debug("发布消息的内容为："+msg.toString());
		}
		SysMessageDTO dto = MsgUtil.beanCopy(msg);
		ResponseMsg<SysMessageDTO> responseMsg  = MsgRestUtil.addMsg(dto);
        if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
        	long msgId = responseMsg.getResponseBody().getMsgId();
        	final boolean isMsgInsertSucc = msgId > 0 ? true : false;
        	if (isMsgInsertSucc && responseMsg.getResponseBody() !=null) {
        		msg.setId(String.valueOf(msgId));
        		dto.setMsgId(msgId);
        		result = isMsgInsertSucc;
        		//废弃该操作--不往redis中存放消息体数据
        		//CacheUtil.put(pool,CacheUtil.buildKey(msg, null, true), JSON.toJSONString(responseMsg.getResponseBody()));
        	}
        	//专有消息，同时插入用户关系
        	if("1".equals(msg.getMsgType()) && msgId > 0 && !StringUtils.isEmpty(msg.getUserIds())){
        		handleSpecificUserMsgRelation(msgId,msg);
        	}
        	//全局消息，同时插入用户关系
        	if("0".equals(msg.getMsgType()) && msgId > 0){
        		handleGlobalUserMsgRelation(msgId,msg);
        	}
        	if(result){//开始推送消息
        		String error = validateMsg(dto);
        		if(null == error || "".equals(error)){
        			onlyPushMsg(dto);
        		}else{
        			logger.error("the validateMsg has not passed error："+"进行消息发布的消息对象验证失败！失败信息："+error);
        		}
        	}
		}
		return result;
	}
	
		/**
		 * 处理用户关系数据--专有消息
		 * @param msgId 插入消息后，新生成的消息ID 
		 * @param msg 消息对象
		 * @throws Exception
		 */
		private void handleSpecificUserMsgRelation(Long newMsgId,Msg msg)throws Exception{
			String [] userIds =  msg.getUserIds().split(",");
			for (String targetUserId : userIds ) {
				SysUserMsgRelationDTO relationDTO = new SysUserMsgRelationDTO();
				relationDTO.setMsgId(newMsgId);
				relationDTO.setReadFlag("0");
				relationDTO.setUserId(targetUserId);
				relationDTO.setRelStatus("0");
				ResponseMsg<SysUserMsgRelationDTO> responseMsg =  MsgRestUtil.addUserMsgRelation(relationDTO);
				if (responseMsg!=null && ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())) {
					//同时更新缓存---废弃该操作
					//msg.setId(String.valueOf(newMsgId));
					//CacheUtil.put(pool,CacheUtil.buildKey(msg, targetUserId, false), JSON.toJSONString(responseMsg.getResponseBody()));
				}else{
					logger.error("MsgApiAddImpl.handleSpecificUserMsgRelation (insert into Sys_User_Msg_Relation error："+"往用户消息管理表中插入数据失败！)");
				}
			}
		}
		
		/**
		 * 处理用户关系数据--全局消息
		 * @param msgId 插入消息后，新生成的消息ID 
		 * @param msg 消息对象
		 * @throws Exception
		 */
		private void handleGlobalUserMsgRelation(Long newMsgId,Msg msg)throws Exception{
			//获取所有可用的（且已分配角色）用户编号列表 
			ResponseMsg<List<SysUserDTO>> response = MsgRestUtil.searchAllUsefulSysUser();
			if (response!=null && ResponseStatus.HTTP_OK.equals(response.getRetCode())) {
				List<SysUserDTO> sysUsers = response.getResponseBody();
				for (SysUserDTO dto : sysUsers) {
					SysUserMsgRelationDTO relationDTO = new SysUserMsgRelationDTO();
					relationDTO.setMsgId(newMsgId);
					relationDTO.setReadFlag("0");
					relationDTO.setUserId(dto.getId()+"");
					relationDTO.setRelStatus("0");
					ResponseMsg<SysUserMsgRelationDTO> responseMsg =  MsgRestUtil.addUserMsgRelation(relationDTO);
					if (responseMsg!=null && ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())) {
						//同时更新缓存---废弃该操作
						//msg.setId(String.valueOf(newMsgId));
						//CacheUtil.put(pool,CacheUtil.buildKey(msg, dto.getId()+"", false), JSON.toJSONString(responseMsg.getResponseBody()));
					}else{
						logger.error("MsgApiAddImpl.handleGlobalUserMsgRelation (insert into Sys_User_Msg_Relation error："+"往用户消息管理表中插入数据失败！)");
					}
				}
			}else{
				logger.error("the searchAllUsefulSysUser in handleGlobalUserMsgRelation error："+"获取所有可用的（且已分配角色）用户编号列表失败！");
			}
		}
		
		/**
		 * 开始推送消息
		 * @param dto 消息体
		 * @return
		 */
		public boolean onlyPushMsg(SysMessageDTO dto){
			boolean result = false;
			ApplicationContext context = null;
			try{
				WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
				ServletContext servletContext = webApplicationContext.getServletContext();
				context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			}catch(Exception e){
				context = SysConfigAPI.getApplicationContext();
			}
			//获取 sysConfigAPI
			SysConfigAPI sysConfig = (SysConfigAPI) context.getBean(SysConfigAPI.class);
			if(null == sysConfig){
				logger.error("the load SysConfig option in onlyPushMsg method error："+"获取系统配置接口失败！");
				return false;
			}
			//获取推送链接
			String msgPushURL = sysConfig.getValue("MSG_PUSH_WEBROOT");
            String jyptAppId = "ptpt"; // rest服务appId
      	 	String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);// rest服务地址
      	 	String MSG_PUSH_URL   = jyptURL + "/api/platform/smsPushlet/push/v1";
      	 	if(null != msgPushURL && !"".equals(msgPushURL)){
      	 		msgPushURL = msgPushURL +"/api/platform/smsPushlet/push/v1";
      	 		MSG_PUSH_URL = msgPushURL;
      	 	}
      	 	try {
				ResponseMsg<String> responseMsg = RestClient.doPost(jyptAppId, MSG_PUSH_URL, dto, new TypeReference<ResponseMsg<String>>(){});
				if(StringUtils.isEmpty(responseMsg.getErrorDesc())){
					result = true;
				}
			} catch (Exception e) {
				logger.error("the push msg option in onlyPushMsg method error："+"推送消息失败！"+e);
			}
      	 	return result;
		}
		
		/**
		 * 验证消息对象是否通过
		 * @param dto
		 * @return
		 */
		public String validateMsg(SysMessageDTO dto){
			if(null == dto){
				return "消息对象为空！";
			}
			StringBuffer error = new StringBuffer();
			if(null == dto.getType() || "".equals(dto.getType())){
				error.append(" 消息类型为空");
			}
			if(null == dto.getSysFlag() || "".equals(dto.getSysFlag())){
				error.append(" 系统标识为空");
			}
			if(null == dto.getMsgId() || "".equals(dto.getMsgId())){
				error.append(" 消息编号为空");
			}
			if(null == dto.getBody() || "".equals(dto.getBody())){
				error.append(" 消息内容为空");
			}
			if((null != dto.getType() && "1".equals(dto.getType())) &&
					(null == dto.getUserId() || "".equals(dto.getUserId()))){
				error.append(" 收件人为空");
			}
			return error.toString();
		}
}
