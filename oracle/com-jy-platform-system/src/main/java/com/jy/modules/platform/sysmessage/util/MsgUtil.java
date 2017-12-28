package com.jy.modules.platform.sysmessage.util;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.jy.modules.platform.sysmessage.dto.SysMessageDTO;
import com.jy.platform.api.msg.Msg;

/**
 * @Description: 消息处理工具类
 * @author zhanglin
 * @date 2014年11月14日 下午3:27:33
 */
public final class MsgUtil {
	/**
	 * 实体属性名称不同，进行转换
	 * @param msg
	 * @param SystemFlag
	 * @param msgType
	 * @return
	 */
	public static SysMessageDTO beanCopy(Msg msg){
		SysMessageDTO dto = new SysMessageDTO();
		dto.setTitle(msg.getTitle());
		dto.setBody(msg.getConent());
		dto.setCharset("UTF-8");
		dto.setCreateDate(new Date());
		dto.setStartDate(msg.getStartTime());
		dto.setEndDate(msg.getEndTime());
		dto.setStatus("0");
		dto.setSysFlag(msg.getSysFlag());
		dto.setPublisher(msg.getCreator());
		dto.setType(msg.getMsgType());
		dto.setUrl(msg.getUrl());
		dto.setUrgentFlag(msg.getUrgentFlag());
		dto.setUserId(msg.getUserIds());
		return dto;
	}
	
	/**
	 * 从SysMessageDto 转换成 Msg
	 * @return
	 */
	public static Msg beanChange(SysMessageDTO msgDto){
		Msg msg = new Msg();
		msg.setId(String.valueOf(msgDto.getMsgId()));
		msg.setConent(msgDto.getBody());
		msg.setTitle(msgDto.getTitle());
		msg.setStartTime(msgDto.getStartDate());
		msg.setEndTime(msgDto.getEndDate());
		msg.setUrgentFlag(msgDto.getUrgentFlag());
		msg.setUrl(msgDto.getUrl());
		msg.setCreator(msgDto.getPublisher());
		msg.setMsgType(msgDto.getType());
		msg.setSysFlag(msgDto.getSysFlag());
		msg.setUserIds(msgDto.getUserId());
		return msg;
	}
	
	/**
	 * 将jsonText转换成对应实体
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseTextToObject(String jsonText,Class clazz){
		T result = (T)JSON.parseObject(jsonText, clazz);
		return result;
	}
}
