package com.jy.modules.platform.sysmessage.rest;

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.modules.platform.sysmessage.dto.SysMessageDTO;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: MsgPushletRest
 * @description:定义 SYS_MESSAGE rest服务
 * @author dengfeng.hua
 * @date:2015年12月01日上午9:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/smsPushlet")
public class MsgPushletRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(MsgPushletRest.class);
	
	/**
	 * 根据不同类型进行推送消息
	 * body 为必填属性
	 * sysFlag 为必填属性
	 * userId 可选，如：1000
	 * MsgId 为必填属性
	 * Type 为必填属性
	 * @param dto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/push/v1", method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<String>> push(@RequestBody SysMessageDTO dto) throws Exception{
		ResponseMsg<String> responseMsg = new ResponseMsg<String>();
		responseMsg.setResponseBody("消息推送成功");
		responseMsg.setErrorDesc(null);
		Event[] events = packageEvent(dto);
		if(null == events || events.length==0){
			responseMsg.setResponseBody("消息推送失败");
			responseMsg.setErrorDesc("封装事件对象失败");
			return new ResponseEntity<ResponseMsg<String>>(responseMsg, HttpStatus.NO_CONTENT);
		}
		//全局消息的全局广播
		if(!StringUtils.isEmpty(dto.getType()) && "0".equals(dto.getType())){
			Dispatcher.getInstance().broadcast(events[0]);
		}
		//专有消息的多播
		if(!StringUtils.isEmpty(dto.getType()) && "1".equals(dto.getType())){
			//接收者有多个，循环进行推送消息
			for(int i=0;i<events.length;i++){
				Dispatcher.getInstance().multicast(events[i]);
			}
		}
		ResponseEntity<ResponseMsg<String>> responseEntity=new ResponseEntity<ResponseMsg<String>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 封装事件对象
	 * @param dto
	 * @return
	 */
	public Event[] packageEvent(SysMessageDTO dto){
		Event[] result = null;
		Event event = null;
		if(null == dto || (null == dto.getBody() || "".equals(dto.getBody().trim()))){
			return null;
		}
		try{
			if(!StringUtils.isEmpty(dto.getType()) && "0".equals(dto.getType())){//全局消息的全局广播
				result = new Event[1];
				event = new Event("data");
				event.setField("p_event", "data");
				event.setField("msg_id", dto.getMsgId());
				event.setField("msg", new String(dto.getBody().getBytes("UTF-8"), "ISO8859-1"));
				result[0] = event;
			}
			if(!StringUtils.isEmpty(dto.getType()) && "1".equals(dto.getType())){//专有消息的多播或单播
				if(null == dto.getUserId() || "".equals(dto.getUserId().trim())){
					return null;
				}
				//针对某几个人的推送
				String[] userIds = dto.getUserId().split(",");
				result = new Event[userIds.length];
				for(int i=0;i<userIds.length;i++){
					event = new Event("data");
					event.setField("p_event", "data");
					event.setField("msg_id", dto.getMsgId());
					event.setField("p_subject", "/"+dto.getSysFlag()+"_"+userIds[i]);
					event.setField("msg", new String(dto.getBody().getBytes("UTF-8"), "ISO8859-1"));
					result[i] = event;
				}
			}
		}catch(Exception e){
			result = null;
			logger.error("packageEvent error",e);
		}
		return result;
	}
}
