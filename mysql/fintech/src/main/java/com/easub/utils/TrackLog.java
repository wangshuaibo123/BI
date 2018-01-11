package com.easub.utils;

import java.util.Map;

public class TrackLog {

	private String distinctId;
	private Long time;
	private String event;
	private String type;
	
	private Map<String,Object> properties;
	
	private Map<String,Object> lib;
	
	
	public Object getProperty(String key) {
		if(this.properties == null) {
			return null;
		}
		return this.properties.get(key);
	}
	
	public Object getLibValue(String key) {
		if(lib == null) {
			return null;
		}
		return this.lib.get(key);
	}
	

	public String getDistinctId() {
		return distinctId;
	}

	public void setDistinctId(String distinctId) {
		this.distinctId = distinctId;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Map<String, Object> getLib() {
		return lib;
	}

	public void setLib(Map<String, Object> lib) {
		this.lib = lib;
	}

	
}
