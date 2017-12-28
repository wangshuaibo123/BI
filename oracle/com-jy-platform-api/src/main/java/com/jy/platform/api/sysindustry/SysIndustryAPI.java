/**
 * 
 */
package com.jy.platform.api.sysindustry;

import java.util.List;
import java.util.Map;

/**
 * <p>行业字典数据接口</p>	
 * @author lin
 * @2014年12月4日 下午2:12:24
 */
public interface SysIndustryAPI {

	/**
	 * 根据父ID查询所有行业数据
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getIndustries(String parentCode) throws Exception;
	
	
	/**
	 * 根据职位查询同行业的职位列表
	 * @param defaultPositionName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	List<Map> getPositionsByIndustry(String defaultIndustry)throws Exception;
	
}
