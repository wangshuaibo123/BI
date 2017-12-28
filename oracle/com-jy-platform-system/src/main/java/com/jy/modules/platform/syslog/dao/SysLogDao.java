package com.jy.modules.platform.syslog.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.syslog.dto.SysLogDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;

@MyBatisRepository
public interface SysLogDao {
	/**
	 * @author guo_yongliang
	 * @description: 新增对象日志表
	 * @date 2014-12-15
	 * @param paramMap
	 * @return
	 */
	public int insertSysLog(List<SysLogDTO> paramMap);
	
	public List<SysLogDTO> searchSysLogByPaging(Map<String, Object> paramMap);
	
	public SysLogDTO findSysLogByPrimaryKey(String id);
}
