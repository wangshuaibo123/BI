package com.jy.platform.rule;

import java.util.List;

public interface RuleDefService {

	/** 根据规则编码，获取规则定义列表 */
	List<? extends RuleDef> searchRuleDefinition(String rules) throws Exception;
}
