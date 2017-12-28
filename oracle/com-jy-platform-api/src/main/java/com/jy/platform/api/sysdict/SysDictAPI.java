package com.jy.platform.api.sysdict;

import java.util.List;
import java.util.Map;

public interface SysDictAPI {
	/**
	 * 通过数据字典key获取数据字典明细
	 * 
	 * @param key
	 * @return
	 * List<Map>
	 * Map的key值为
	 * 名称 	    dictDetailName
	 * 值		dictDetailValue
	 * 排序		orderBy
	 */
	public List<Map> getDictByKey(String key);
	
	/**
	 * 通过字典编码和字典明细获得明细数据
	 * @param searchParams
	 * @return
	 */
	public SysDictDetailVo queryDetailByDictCodeAndDeatailValue(String dict_code,String detail_value);
	
	/**
	 * 根据字典编码获取字典信息
	 * @param code
	 * @return
	 */
	public Map<String,String> queryDictByDictCode(String code);
	
	/**
	 * 翻译数据字典
	 * @param agrs
	 */
	
	public String codeToName(String type_,String code_);
}
