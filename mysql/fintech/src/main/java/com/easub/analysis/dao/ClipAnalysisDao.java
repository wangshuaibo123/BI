package com.easub.analysis.dao;

import java.util.Map;

import com.fintech.platform.core.mybatis.MyBatisRepository;

@MyBatisRepository
public interface ClipAnalysisDao {

	public Long getVideoVV(Map<String,Object> conditions);
	
}
