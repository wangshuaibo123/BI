package com.jy.platform.api.drools;

import java.util.HashMap;
import java.util.List;

public interface DroolsAPI {
	
	/**
	 * 
	 * @param ruleListEnName 规则集英文名
	 * @param insertObjects传入的对象
	 * @param globalServices传入的service,以key value的方式与drl文件中对应
	 * @return
	 */
	public HashMap<String, Object> doDrools(String ruleListEnName,List<?> insertObjects,HashMap<String , ?> globalServices);
}
