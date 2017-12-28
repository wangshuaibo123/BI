package com.jy.modules.platform.sysdict.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.platform.sysdict.dao.SysCommonTagDao;

/**
 * @description: 定义通用标签server类
 * @author chen_gang
 * @date: 2016年2月14日 下午2:18:50
 */
@Service("com.jy.modules.platform.sysdict.service.SysCommonTagService")
public class SysCommonTagService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SysCommonTagDao dao;
	/**
	 * @author chen_gang
	 * @description: 
	 * @date 2016年2月14日 下午2:49:14
	 * @param codeType
	 * @return list<Map<String,String>>
	 * key:DICNAME
	 * key:SHORTNAME 概述
	 * key:DICVALUE
	 * key:ORDERBY
	 * @throws Exception
	 */
	public List<Map<String, String>> queryCommonDataInfo(String codeType) throws Exception {
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		Map<String, Object> searchParams = new HashMap<String,Object>();
		searchParams.put("codeType", codeType);
		if("HR".equals(codeType)){
			//取HR sys_org 信息
			dataList = dao.queryHRCommonDataInfo(searchParams);
		}else{
			//取虚拟树 vmtree_info 信息
			dataList = dao.queryVMTreeCommonDataInfo(searchParams);
		}
		
		return dataList;
	}
	

}
