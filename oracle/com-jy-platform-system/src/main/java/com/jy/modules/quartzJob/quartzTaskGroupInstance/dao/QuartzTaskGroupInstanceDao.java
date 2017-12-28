package com.jy.modules.quartzJob.quartzTaskGroupInstance.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.quartzJob.quartzTaskGroupDef.dto.QuartzGroupDTO;
import com.jy.modules.quartzJob.quartzTaskGroupInstance.dto.QuartzTaskGroupInstanceDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;

/**
 * @classname: QuartzTaskGroupInstanceDao
 * @description: 定义 分组任务实例表 持久层 接口 通过@MapperScannerConfigurer扫描目录中的所有接口,
 *               动态在Spring Context中生成实现. 方法名称必须与Mapper.xml中保持一致.
 * @author: chen_gang
 */
@MyBatisRepository
public interface QuartzTaskGroupInstanceDao {

	/**
	 * @author chen_gang
	 * @description: 分页查询分组任务实例表
	 * @date 2014-10-14 21:37:39
	 * @param searchParams
	 * @return
	 */
	public List<QuartzTaskGroupInstanceDTO> searchQuartzTaskGroupInstanceByPaging(
			Map<String, Object> searchParams);

	/**
	 * @author chen_gang
	 * @description:查询对象分组任务实例表
	 * @date 2014-10-14 21:37:39
	 * @param searchParams
	 * @return
	 */
	public List<QuartzTaskGroupInstanceDTO> searchQuartzTaskGroupInstance(
			Map<String, Object> searchParams);

	/**
	 * @author chen_gang
	 * @description:查询对象分组任务实例表
	 * @date 2014-10-14 21:37:39
	 * @param id
	 * @return
	 */
	public QuartzTaskGroupInstanceDTO findQuartzTaskGroupInstanceByPrimaryKey(
			String id);

	/**
	 * @author chen_gang
	 * @description: 新增对象分组任务实例表
	 * @date 2014-10-14 21:37:39
	 * @param paramMap
	 * @return
	 */
	public int insertQuartzTaskGroupInstance(Map<String, Object> paramMap);

	/**
	 * @author chen_gang
	 * @description: 更新对象分组任务实例表
	 * @date 2014-10-14 21:37:39
	 * @param paramMap
	 */
	public void updateQuartzTaskGroupInstance(Map<String, Object> paramMap);

	/**
	 * @author chen_gang
	 * @description: 按主键删除分组任务实例表
	 * @date 2014-10-14 21:37:39
	 * @param ids
	 * @return
	 */
	public void deleteQuartzTaskGroupInstanceByPrimaryKey(
			Map<String, Object> paramMap);

	public List<QuartzGroupDTO> queryQuartzGroupListByPaging(
			Map<String, Object> paramMap);

	public void updateGroupTaskByNo(Map<String,Object> paramMap);
	public void updateTaskIsEndByNo(Map<String,Object> paramMap);
	
	public void updateAutoExceGroupTaskByNo(Map<String,Object> paramMap);
	
	/**
	 * 根据批次更新自动执行
	 * @param paramMap
	 */
	public void updateAutoExceGroupTaskByGroup(Map<String,Object> paramMap);

	public void resetTaskState(Map<String, Object> paramMap);

	/**
	 * 根据任务类名称更新当天任务为自动执行
	 * @param paramMap
	 */
	public void updateAutoExceByTaskClass(Map<String,Object> paramMap);
	
	/**
	 * 根据任务类名获取当天任务的自动执行状态
	 * @param paramMap
	 */
	public QuartzGroupDTO searchByTaskClassStatus(Map<String, Object> paramMap);
}
