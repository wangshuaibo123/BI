package com.easub.bi.dao;

import java.util.List;
import java.util.Map;

import com.easub.bi.dto.ClipUsersDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;

@MyBatisRepository
public interface ClipUsersDao {

	public List<ClipUsersDTO> getUsersList(Map<String,Object> map);
	/**
	 * 获取账号数
	 * @param map 条件
	 * @return
	 */
	public Integer getUsersAccountCount(Map<String,Object> map);
	
	public Integer getUserOperationLogsCount(Map<String,Object> map);
	
}
