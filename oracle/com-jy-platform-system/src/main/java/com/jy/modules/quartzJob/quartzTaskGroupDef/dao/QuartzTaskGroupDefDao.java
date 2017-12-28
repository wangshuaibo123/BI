package com.jy.modules.quartzJob.quartzTaskGroupDef.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.quartzJob.quartzTaskGroupDef.dto.QuartzGroupDTO;
import com.jy.modules.quartzJob.quartzTaskGroupDef.dto.QuartzTaskGroupDefDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;

/**
 * @classname: QuartzTaskGroupDefDao
 * @description: 定义 任务分组定义表 持久层 接口 通过@MapperScannerConfigurer扫描目录中的所有接口,
 *               动态在Spring Context中生成实现. 方法名称必须与Mapper.xml中保持一致.
 * @author: chen_gang
 */
@MyBatisRepository
public interface QuartzTaskGroupDefDao {

	/**
	 * @author chen_gang
	 * @description: 分页查询任务分组定义表
	 * @date 2014-10-14 21:37:26
	 * @param searchParams
	 * @return
	 */
	public List<QuartzTaskGroupDefDTO> searchQuartzTaskGroupDefByPaging(
			Map<String, Object> searchParams);

	/**
	 * @author chen_gang
	 * @description:查询对象任务分组定义表
	 * @date 2014-10-14 21:37:26
	 * @param searchParams
	 * @return
	 */
	public List<QuartzTaskGroupDefDTO> searchQuartzTaskGroupDef(
			Map<String, Object> searchParams);

	/**
	 * @author chen_gang
	 * @description:查询对象任务分组定义表
	 * @date 2014-10-14 21:37:26
	 * @param id
	 * @return
	 */
	public QuartzTaskGroupDefDTO findQuartzTaskGroupDefByPrimaryKey(String id);

	/**
	 * @author chen_gang
	 * @description: 新增对象任务分组定义表
	 * @date 2014-10-14 21:37:26
	 * @param paramMap
	 * @return
	 */
	public int insertQuartzTaskGroupDef(Map<String, Object> paramMap);

	/**
	 * @author chen_gang
	 * @description: 更新对象任务分组定义表
	 * @date 2014-10-14 21:37:26
	 * @param paramMap
	 */
	public void updateQuartzTaskGroupDef(Map<String, Object> paramMap);

	/**
	 * @author chen_gang
	 * @description: 按主键删除任务分组定义表
	 * @date 2014-10-14 21:37:26
	 * @param ids
	 * @return
	 */
	public void deleteQuartzTaskGroupDefByPrimaryKey(
			Map<String, Object> paramMap);
	
	public void deleteQuartzTaskGroupDefByID(Map<String,Object> paramMap);

	public List<QuartzGroupDTO> queryQuartzGroupListByPaging(
			Map<String, Object> paramMap);

	public void updateTaskIsEndByTaskId(Map<String, Object> paramMap);

	public void updateGroupStateByGroupId(Map<String, Object> paramMap);

	public void deleteQuartzTaskGroupDefByGroupId(Map<String, Object> paramMap);
}
