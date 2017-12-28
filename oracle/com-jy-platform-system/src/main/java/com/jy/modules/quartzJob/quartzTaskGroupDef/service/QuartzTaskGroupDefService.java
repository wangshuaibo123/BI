package com.jy.modules.quartzJob.quartzTaskGroupDef.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.quartzJob.quartzTaskGroupDef.dao.QuartzTaskGroupDefDao;
import com.jy.modules.quartzJob.quartzTaskGroupDef.dto.QuartzGroupDTO;
import com.jy.modules.quartzJob.quartzTaskGroupDef.dto.QuartzTaskGroupDefDTO;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: QuartzTaskGroupDefService
 * @description: 定义 任务分组定义表 实现类
 * @author: chen_gang
 */
@Service("com.jy.modules.quartzJob.quartzTaskGroupDef.service.QuartzTaskGroupDefService")
public class QuartzTaskGroupDefService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private QuartzTaskGroupDefDao dao;

	/**
	 * @author chen_gang
	 * @description: 分页查询 任务分组定义表列表
	 * @date 2014-10-14 21:37:26
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<QuartzTaskGroupDefDTO> searchQuartzTaskGroupDefByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<QuartzTaskGroupDefDTO> dataList = dao
				.searchQuartzTaskGroupDefByPaging(searchParams);
		return dataList;
	}

	/**
	 * @author chen_gang
	 * @description: 按条件查询任务分组定义表列表
	 * @date 2014-10-14 21:37:26
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<QuartzTaskGroupDefDTO> searchQuartzTaskGroupDef(
			Map<String, Object> searchParams) throws Exception {
		List<QuartzTaskGroupDefDTO> dataList = dao
				.searchQuartzTaskGroupDef(searchParams);
		return dataList;
	}

	/**
	 * @author chen_gang
	 * @description: 查询任务分组定义表对象
	 * @date 2014-10-14 21:37:26
	 * @param id
	 * @return
	 * @throws
	 */
	public QuartzTaskGroupDefDTO queryQuartzTaskGroupDefByPrimaryKey(String id)
			throws Exception {

		QuartzTaskGroupDefDTO dto = dao.findQuartzTaskGroupDefByPrimaryKey(id);

		if (dto == null)
			dto = new QuartzTaskGroupDefDTO();

		return dto;

	}

	/**
	 * @title: insertQuartzTaskGroupDef
	 * @author chen_gang
	 * @description: 新增 任务分组定义表对象
	 * @date 2014-10-14 21:37:26
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public Long insertQuartzTaskGroupDef(QuartzTaskGroupDefDTO dto)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		int count = dao.insertQuartzTaskGroupDef(paramMap);

		QuartzTaskGroupDefDTO resultDto = (QuartzTaskGroupDefDTO) paramMap
				.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}

	/**
	 * @title: insertQuartzTaskGroupDef
	 * @author chen_gang
	 * @description: 新增 任务分组定义表对象
	 * @date 2014-10-14 21:37:26
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public void insertQuartzGroup(QuartzGroupDTO dto) throws Exception {
		if (dto == null || dto.getTaskList() == null
				|| dto.getTaskList().size() == 0)
			return;
		List<QuartzTaskGroupDefDTO> taskList = dto.getTaskList();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int i = 0;
		for (QuartzTaskGroupDefDTO taskDto : taskList) {
			taskDto.setTaskId(dto.getGroupId() + "00" + i++);
			taskDto.setAutoExec(dto.getAutoExec());
			taskDto.setDealChance(dto.getDealChance());
			taskDto.setGroupId(dto.getGroupId());
			taskDto.setGroupName(dto.getGroupName());
			taskDto.setGroupState(dto.getGroupState());
			taskDto.setRunTime(dto.getRunTime());
			paramMap.put("dto", taskDto);
			dao.insertQuartzTaskGroupDef(paramMap);
		}

	}

	/**
	 * @title: updateQuartzTaskGroupDef
	 * @author chen_gang
	 * @description: 修改 任务分组定义表对象
	 * @date 2014-10-14 21:37:26
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateQuartzTaskGroupDef(QuartzTaskGroupDefDTO dto)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		dao.updateQuartzTaskGroupDef(paramMap);
	}
	
	/**
	 * @title: updateQuartzGroup
	 * @author guo_yongliang
	 * @description: 修改任务分组定义表对象
	 * @date 2014-12-04 
	 * @param dto
	 * @return
	 * @throws
	 */
	public void updateQuartzGroup(QuartzGroupDTO dto)
		throws Exception{
		if (dto == null || dto.getTaskList() == null
				|| dto.getTaskList().size() == 0)
			return;
		deleteQuartzTaskGroupDefByPrimaryKey(dto,dto.getGroupId());
		insertQuartzGroup(dto);
	}

	/**
	 * @title: deleteQuartzTaskGroupDefByPrimaryKey
	 * @author chen_gang
	 * @description: 删除 任务分组定义表,按主键
	 * @date 2014-10-14 21:37:26
	 * @param paramMap
	 * @throws
	 */
	public void deleteQuartzTaskGroupDefByPrimaryKey(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteQuartzTaskGroupDefByID(paramMap);
	}

	public List<QuartzGroupDTO> searchQuartzGroupByPaging(
			Map<String, Object> paramMap) {

		return dao.queryQuartzGroupListByPaging(paramMap);
	}
	
	
	public void updateTaskIsEndByTaskId(Map<String,Object> paramMap){
		dao.updateTaskIsEndByTaskId(paramMap);
	}
	
	
	public void updateGroupStateByGroupId(Map<String,Object> paramMap){
		dao.updateGroupStateByGroupId(paramMap);
	}
	
	public void updateGroupStateByBeanId(Map<String,Object> paramMap){
		dao.updateGroupStateByGroupId(paramMap);
	}
}
