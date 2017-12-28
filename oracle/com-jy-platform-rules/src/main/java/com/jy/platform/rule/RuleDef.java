package com.jy.platform.rule;

/** 规则定义 */
public interface RuleDef {

	/** 规则ID */
	String getRuleId();
	/** 规则版本 */
	String getRuleVersion();
	/** 规则定义 */
	String getRuleResource();
	/** 定义类型 */
	String getResourceType();
}
