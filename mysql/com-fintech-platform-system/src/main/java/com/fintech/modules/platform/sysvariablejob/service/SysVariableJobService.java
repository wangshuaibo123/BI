package com.fintech.modules.platform.sysvariablejob.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysvariablejob.dao.SysVariableJobDao;
import com.fintech.modules.platform.sysvariablejob.dto.SysVariableJobDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysVariableJobService
 * @description: 定义  异步任务参数表 实现类
 * @author:  DELL
 */
@Service("com.fintech.modules.platform.sysvariablejob.service.SysVariableJobService")
public class SysVariableJobService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysVariableJobDao dao;

	/**
     * @author DELL
     * @description: 分页查询 异步任务参数表列表
     * @date 2016-09-19 13:51:00
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysVariableJobDTO> searchSysVariableJobByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysVariableJobDTO> dataList =  dao.searchSysVariableJobByPaging(searchParams);
		return dataList;
	}
	/**
     * @author DELL
     * @description: 按条件查询异步任务参数表列表
     * @date 2016-09-19 13:51:00
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysVariableJobDTO> searchSysVariableJob(Map<String,Object> searchParams) throws Exception {
	    List<SysVariableJobDTO> dataList = dao.searchSysVariableJob(searchParams);
        return dataList;
    }
	/**
     * @author DELL
     * @description: 查询异步任务参数表对象
     * @date 2016-09-19 13:51:00
     * @param id
     * @return
     * @throws
     */ 
	public SysVariableJobDTO querySysVariableJobByPrimaryKey(String id) throws Exception {
		
		SysVariableJobDTO dto = dao.findSysVariableJobByPrimaryKey(id);
		
		if(dto == null) dto = new SysVariableJobDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysVariableJob
     * @author DELL
     * @description: 新增 异步任务参数表对象
     * @date 2016-09-19 13:51:00
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysVariableJob(SysVariableJobDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysVariableJob(paramMap);
		
		SysVariableJobDTO resultDto = (SysVariableJobDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysVariableJob
     * @author DELL
     * @description: 修改 异步任务参数表对象
     * @date 2016-09-19 13:51:00
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysVariableJob(SysVariableJobDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysVariableJob(paramMap);
	}
	/**
     * @title: deleteSysVariableJobByPrimaryKey
     * @author DELL
     * @description: 删除 异步任务参数表,按主键
     * @date 2016-09-19 13:51:00
     * @param paramMap
     * @throws
     */ 
	public void deleteSysVariableJobByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysVariableJobByPrimaryKey(paramMap);
	}

}

