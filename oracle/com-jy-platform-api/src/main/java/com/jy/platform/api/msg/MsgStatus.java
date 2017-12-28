package com.jy.platform.api.msg;

/**
 * @Description: 消息状态枚举
 * @author zhanglin
 * @date 2014年11月17日 下午5:30:50
 */
public enum MsgStatus {

	INIT("0"), VALID("1"), INVALID("2"), DELETE("3"),HASREAD("1");//hasRead状态存储在关系表中

	private String code;

	private MsgStatus(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return String.valueOf(code);
	}

	
	
}
