package com.jy.platform.api.sysdict;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface SysCommonTagAPI extends Serializable{
	/**
	 * @author chen_gang
	 * @description: 通过codeType获取相关数据信息 返回list<Map<String,String>>
	 * @date 2016年2月14日 下午2:46:43
	 * @param codeType
	 * @return 
	 * key:DICNAME   必须有
	 * key:DICVALUE  必须有
	 * key:SHORTNAME 概述（非必须）
	 * key:ORDERBY   非必须
	 */
	public List<Map<String,String>> getCommonDataByKey(String codeType);
}
