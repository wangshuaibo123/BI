package com.fintech.modules.platform.sysdict.dao;

import java.util.List;
import java.util.Map;

import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysCommonTagDao
 * @description: 定义  数据字典 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysCommonTagDao {
	/**
	 * @author
	 * @description: 
	 * @date 2016年2月14日 下午2:49:14
	 * @param codeType
	 * @return list<Map<String,String>>
	 * key:DICNAME	
	 * key:DICVALUE
	 * key:SHORTNAME 概述（非必须）
	 * @throws Exception
	 */
	List<Map<String, String>> queryHRCommonDataInfo(Map<String, Object> searchParams);
	
	/**
	 * @author
	 * @description: 
	 * @date 2016年2月14日 下午2:49:14
	 * @param codeType
	 * @return list<Map<String,String>>
	 * key:DICNAME	
	 * key:DICVALUE
	 * @throws Exception
	 */
	List<Map<String, String>> queryVMTreeCommonDataInfo(Map<String, Object> searchParams);
    
    
}
