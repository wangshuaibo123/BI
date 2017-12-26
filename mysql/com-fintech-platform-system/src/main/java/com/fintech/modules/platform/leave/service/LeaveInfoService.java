package com.fintech.modules.platform.leave.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.leave.dao.LeaveInfoDao;
import com.fintech.modules.platform.leave.dto.LeaveInfoDTO;
import com.fintech.platform.core.common.JYLoggerUtil;

/**
 * @classname: LeaveInfoService
 * @description: 定义  员工请假表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.leave.service.LeaveInfoService")
public class LeaveInfoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private LeaveInfoDao dao;

	/**
	 * @author
	 * @description: 分页查询 员工请假表列表
	 * @date 2014-12-03 13:59:16
	 * @param searchParams 条件
	 * @return
	 * @throws
	 */ 
	public List<LeaveInfoDTO> searchLbTLeaveInfoByPaging(Map<String,Object> searchParams) throws Exception {
		List<LeaveInfoDTO> dataList =  dao.searchLeaveInfoByPaging(searchParams);
		return dataList;
	}
	/**
	 * @author
	 * @description: 按条件查询员工请假表列表
	 * @date 2014-12-03 13:59:16
	 * @param searchParams 条件
	 * @return
	 * @throws
	 */
	public List<LeaveInfoDTO> searchLbTLeaveInfo(Map<String,Object> searchParams) throws Exception {
		List<LeaveInfoDTO> dataList = dao.searchLeaveInfo(searchParams);
		return dataList;
	}
	/**
	 * @author
	 * @description: 查询员工请假表对象
	 * @date 2014-12-03 13:59:16
	 * @param id
	 * @return
	 * @throws
	 */ 
	public LeaveInfoDTO queryLbTLeaveInfoByPrimaryKey(String id) throws Exception {

		LeaveInfoDTO dto = dao.findLeaveInfoByPrimaryKey(id);

		if(dto == null) dto = new LeaveInfoDTO();

		return dto;

	}

	/**
	 * @title: insertLbTLeaveInfo
	 * @author
	 * @description: 新增 员工请假表对象
	 * @date 2014-12-03 13:59:16
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public Long insertLbTLeaveInfo(LeaveInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		int count = dao.insertLeaveInfo(paramMap);

		LeaveInfoDTO resultDto = (LeaveInfoDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
	 * @title: updateLbTLeaveInfo
	 * @author
	 * @description: 修改 员工请假表对象
	 * @date 2014-12-03 13:59:16
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateLbTLeaveInfo(LeaveInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		dao.updateLeaveInfo(paramMap);
	}
	/**
	 * @title: deleteLbTLeaveInfoByPrimaryKey
	 * @author
	 * @description: 删除 员工请假表,按主键
	 * @date 2014-12-03 13:59:16
	 * @param paramMap
	 * @throws
	 */ 
	public void deleteLbTLeaveInfoByUserId(String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		dao.deleteLeaveInfoByUserId(paramMap);
	}
	
	public void deleteLbTLeaveInfoByIds(String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		dao.deleteLeaveInfoByPrimaryKey(paramMap);
	}

	public List<LeaveInfoDTO> searchLbTLeaveInfoByStatus(Map<String, Object> paramMap){
		return dao.searchLeaveInfoByStatus(paramMap);
	}
	
	/***
	 * 批量插入
	 * @param list
	 * @return
	 */
	public int batchInsertLbTLeaveInfo(List<LeaveInfoDTO> list){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("list", list);
		return dao.batchInsertLeaveInfo(paramMap);
	}
	/**
	 * 判断userId 是否请假中
	 * @param userId
	 * @return
	 */
	public boolean  checkLeaveByUserId(String userId){
		boolean leave = false;//请假中
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		
		try {
			Map<String,String> resuMap = dao.checkLeaveByUserId(paramMap);
			String cun = resuMap.get("CUN");
			if(Integer.parseInt(cun) > 0){
				leave = true;
			}
		} catch (Exception e) {
			JYLoggerUtil.error(this.getClass(), "执行方法checkLeaveByUserId 失败："+e.getMessage(),e);
			leave = false;
		}
		return leave;
	}
	
}

