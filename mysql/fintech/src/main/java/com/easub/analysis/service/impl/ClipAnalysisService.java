package com.easub.analysis.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.easub.analysis.dao.ClipAnalysisDao;
import com.easub.analysis.service.IClipAnalysisService;

@Service("com.easub.analysis.service.impl.ClipAnalysisService")
public class ClipAnalysisService implements IClipAnalysisService{

	@Resource
	private ClipAnalysisDao dao;
	
	@Override
	public Long getVideoVV(Map<String, Object> conditions) throws Exception {
		return this.dao.getVideoVV(conditions);
	}

}
