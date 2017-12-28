package com.jy.modules.quartzJob.quartzTaskGroupInstance.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.platform.common.util.PlatformThreadTool;
import com.jy.modules.quartzJob.quartzTaskGroupDef.dto.QuartzGroupDTO;
import com.jy.modules.quartzJob.quartzTaskGroupInstance.dao.QuartzTaskGroupInstanceDao;
import com.jy.modules.quartzJob.quartzTaskGroupInstance.dto.QuartzTaskGroupInstanceDTO;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: QuartzTaskGroupInstanceService
 * @description: 定义  分组任务实例表 实现类
 * @author:  chen_gang
 */
@Service("com.jy.modules.quartzJob.quartzTaskGroupInstance.service.QuartzTaskGroupInstanceService")
public class QuartzTaskGroupInstanceService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private QuartzTaskGroupInstanceDao dao;

	/**
     * @author chen_gang
     * @description: 分页查询 分组任务实例表列表
     * @date 2014-10-14 21:37:38
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<QuartzTaskGroupInstanceDTO> searchQuartzTaskGroupInstanceByPaging(Map<String,Object> searchParams) throws Exception {
		List<QuartzTaskGroupInstanceDTO> dataList =  dao.searchQuartzTaskGroupInstanceByPaging(searchParams);
		return dataList;
	}
	/**
     * @author chen_gang
     * @description: 按条件查询分组任务实例表列表
     * @date 2014-10-14 21:37:38
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<QuartzTaskGroupInstanceDTO> searchQuartzTaskGroupInstance(Map<String,Object> searchParams) throws Exception {
	    List<QuartzTaskGroupInstanceDTO> dataList = dao.searchQuartzTaskGroupInstance(searchParams);
        return dataList;
    }
	/**
     * @author chen_gang
     * @description: 查询分组任务实例表对象
     * @date 2014-10-14 21:37:38
     * @param id
     * @return
     * @throws
     */ 
	public QuartzTaskGroupInstanceDTO queryQuartzTaskGroupInstanceByPrimaryKey(String id) throws Exception {
		
		QuartzTaskGroupInstanceDTO dto = dao.findQuartzTaskGroupInstanceByPrimaryKey(id);
		
		if(dto == null) dto = new QuartzTaskGroupInstanceDTO();
		
		return dto;
		
	}

	/**
     * @title: insertQuartzTaskGroupInstance
     * @author chen_gang
     * @description: 新增 分组任务实例表对象
     * @date 2014-10-14 21:37:38
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertQuartzTaskGroupInstance(QuartzTaskGroupInstanceDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertQuartzTaskGroupInstance(paramMap);
		
		QuartzTaskGroupInstanceDTO resultDto = (QuartzTaskGroupInstanceDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateQuartzTaskGroupInstance
     * @author chen_gang
     * @description: 修改 分组任务实例表对象
     * @date 2014-10-14 21:37:38
     * @param paramMap
     * @return
     * @throws
     */
	public void updateQuartzTaskGroupInstance(QuartzTaskGroupInstanceDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateQuartzTaskGroupInstance(paramMap);
	}
	/**
     * @title: deleteQuartzTaskGroupInstanceByPrimaryKey
     * @author chen_gang
     * @description: 删除 分组任务实例表,按主键
     * @date 2014-10-14 21:37:38
     * @param paramMap
     * @throws
     */ 
	public void deleteQuartzTaskGroupInstanceByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteQuartzTaskGroupInstanceByPrimaryKey(paramMap);
	}
	
	public List<QuartzGroupDTO> searchQuartzGroupByPaging(
			Map<String, Object> paramMap) {
		List<QuartzGroupDTO> list=dao.queryQuartzGroupListByPaging(paramMap);
		Map<String, Object> subParamMap=new HashMap<String, Object>();
		if(list.size()>0){
			for(QuartzGroupDTO dto:list){
				if(dto.getTaskInsState().equals("3")){
					QuartzTaskGroupInstanceDTO subdto = new QuartzTaskGroupInstanceDTO();
					subdto.setBatchNo(dto.getGroupState());
					subParamMap.put("dto", subdto);
					List<QuartzTaskGroupInstanceDTO> sublist = searchQuartzTaskGroupDef(subParamMap);
					for(QuartzTaskGroupInstanceDTO tDto: sublist){
						if(PlatformThreadTool.THREAD_MAP.containsValue(tDto.getId().toString())){
							dto.setTaskInsState("2");
						}
					}
				}
			}
		}
		return list;
	}
	
	
	public void updateGroupStop(Map<String,Object> paramMap){
		dao.updateGroupTaskByNo(paramMap);
	}

	public void updateTaskIsEndByNo(Map<String,Object> paramMap){
		dao.updateTaskIsEndByNo(paramMap);
	}
	public List<QuartzTaskGroupInstanceDTO> searchQuartzTaskGroupDef(
			Map<String, Object> paramMap) {
		return dao.searchQuartzTaskGroupInstance(paramMap);
	}
	
	public void updateAutoExceGroupTaskByNo(Map<String,Object> paramMap){
		dao.updateAutoExceGroupTaskByNo(paramMap);
	}
	
	public void updateAutoExceGroupTaskByGroup(Map<String,Object> paramMap){
		dao.updateAutoExceGroupTaskByGroup(paramMap);
	}
	
	public void resetTaskState(Map<String, Object> paramMap){
		dao.resetTaskState(paramMap);
	}
	
	public void updateAutoExceByTaskClass(Map<String, Object> paraMap){
		dao.updateAutoExceByTaskClass(paraMap);
	}
	
	public String searchByTaskClassStatus(Map<String, Object> paraMap){
		QuartzGroupDTO qdto=dao.searchByTaskClassStatus(paraMap);
		if(qdto!=null){
			return qdto.getAutoExec();
		}
		return null;
	}
}


