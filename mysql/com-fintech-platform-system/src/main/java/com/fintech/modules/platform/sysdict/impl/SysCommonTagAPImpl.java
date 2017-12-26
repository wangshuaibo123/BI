package com.fintech.modules.platform.sysdict.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fintech.modules.platform.sysdict.service.SysCommonTagService;
import com.fintech.platform.api.sysdict.SysCommonTagAPI;
/**
 * @description: 定义通用标签接口实现类
 * @author
 * @date: 2016年2月14日 下午2:17:41
 */
@Component("com.fintech.modules.platform.sysdict.impl.SysCommonTagAPImpl")
public class SysCommonTagAPImpl implements SysCommonTagAPI {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(SysCommonTagAPImpl.class);
	
	@Autowired
	@Qualifier("com.fintech.modules.platform.sysdict.service.SysCommonTagService")
	private SysCommonTagService tagService;
	/**
	 * @author
	 * @description: 通过codeType获取相关数据信息 返回list<Map<String,String>>
	 * @date 2016年2月14日 下午2:46:43
	 * @param codeType
	 * @return 
	 * key:DICNAME   必须有
	 * key:DICVALUE  必须有
	 * key:SHORTNAME 概述（非必须）
	 * key:ORDERBY   非必须
	 */
	public List<Map<String,String>> getCommonDataByKey(String codeType){
		List<Map<String, String>> detailList = null;
		try {
			
			detailList = tagService.queryCommonDataInfo(codeType);
			
		} catch (Exception e) {
			detailList = new ArrayList<Map<String,String>>();
			logger.error(" SysCommonTagAPImpl getCommonDataByKey error ",e);
		}
		return detailList;
	}

}
