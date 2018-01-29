package com.easub.bi.service;

import java.util.List;
import java.util.Map;

import com.easub.bi.dto.ClipUsersDTO;

public interface IClipUsersService {

	public List<ClipUsersDTO> getUsersList(Map<String,Object> conditions) throws Exception;
	
	/**
	 * 获取账号数
	 * @param map 条件
	 * @return
	 */
	public Integer getUsersAccountCount(Map<String,Object> map);
	
	public Integer getUserOperationLogsCount(Map<String,Object> map);
	
	
}
