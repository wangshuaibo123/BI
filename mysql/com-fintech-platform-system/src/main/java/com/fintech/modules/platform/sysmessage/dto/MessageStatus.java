package com.fintech.modules.platform.sysmessage.dto;

public class MessageStatus {
	//消息生命周期 
	public enum LifeStatus{
		INIT("初始","0"),VALID("生效","1"),INVALID("失效","2"),DEL("删除","3");
		private String name;
		private String value;
		
		private LifeStatus(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		
	}
	//读取状态
	public enum ReadStatus {
		HASREAD("已读", "1"), NOREAD("未读", "0");

		private String name;
		private String value;

		private ReadStatus(String name, String value) {
			this.name = name;
			this.value = value;
		}

		/**
		 * 根据值获取名称
		 * 
		 * @param value
		 * @return
		 */
		public static String getName(String value) {
			for (ReadStatus m : ReadStatus.values()) {
				if (m.getValue().equals(value)) {
					return m.name;
				}
			}
			return "";
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
	
}
