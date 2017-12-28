package com.jy.modules.platform.leave.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.leave.dto.LeaveInfoDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: LeaveInfoDao
 * @description: 定义  员工请假表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  JY-IT-D001
 */
@MyBatisRepository
public interface LeaveInfoDao {

	/**
	 * @author JY-IT-D001
	 * @description: 分页查询员工请假表
	 * @date 2014-12-03 13:59:17
	 * @param searchParams
	 * @return
	 */
	public List<LeaveInfoDTO> searchLeaveInfoByPaging(Map<String, Object> searchParams) ;

	/**
	 * @author JY-IT-D001
	 * @description:查询对象员工请假表
	 * @date 2014-12-03 13:59:17
	 * @param searchParams
	 * @return
	 */
	public List<LeaveInfoDTO> searchLeaveInfo(Map<String,Object> searchParams);

	/**
	 * @author JY-IT-D001
	 * @description:查询对象员工请假表
	 * @date 2014-12-03 13:59:17
	 * @param id
	 * @return
	 */
	public LeaveInfoDTO findLeaveInfoByPrimaryKey(String id);

	/**
	 * @author JY-IT-D001
	 * @description: 新增对象员工请假表
	 * @date 2014-12-03 13:59:17
	 * @param paramMap
	 * @return
	 */
	public int insertLeaveInfo(Map<String, Object> paramMap);

	/**
	 * @author JY-IT-D001
	 * @description: 更新对象员工请假表
	 * @date 2014-12-03 13:59:17
	 * @param paramMap
	 */
	public void updateLeaveInfo(Map<String, Object> paramMap);

	/**
	 * @author JY-IT-D001
	 * @description: 按主键删除员工请假表
	 * @date 2014-12-03 13:59:17
	 * @param ids
	 * @return
	 */ 
	public void deleteLeaveInfoByUserId(Map<String, Object> paramMap);
	
	public void deleteLeaveInfoByPrimaryKey(Map<String, Object> paramMap);

	/***
	 * 通过请假状态和组织、用户Id进行关联查询
	 * @param paramMap
	 * @return
	 */
	List<LeaveInfoDTO> searchLeaveInfoByStatus(Map<String, Object> paramMap);

	/***
	 * 批量请假
	 * @param paramMap
	 * @return
	 */
	public int batchInsertLeaveInfo(Map<String, Object> paramMap);

	public Map<String, String> checkLeaveByUserId(Map<String, Object> paramMap) throws Exception;






}
