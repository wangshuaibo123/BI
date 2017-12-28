package com.jy.platform.jbpm4.service.impl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.jbpm4.dto.Jbpm4BizTabDTO;
import com.jy.platform.jbpm4.repository.Jbpm4BizTabDao;

/**
 * @classname: Jbpm4BizTabService
 * @description: 定义  工作流与业务表关联表 实现类
 * @author:  chen_gang
 */
@Service("com.jy.platform.jbpm4.service.impl.Jbpm4BizTabService")
public class Jbpm4BizTabService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private Jbpm4BizTabDao dao;

	/**
     * @author chen_gang
     * @description: 分页查询 工作流与业务表关联表列表
     * @date 2014-10-24 15:07:31
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<Jbpm4BizTabDTO> searchJbpm4BizTabByPaging(Map<String,Object> searchParams) throws Exception {
		List<Jbpm4BizTabDTO> dataList =  dao.searchJbpm4BizTabByPaging(searchParams);
		return dataList;
	}
	/**
     * @author chen_gang
     * @description: 按条件查询工作流与业务表关联表列表
     * @date 2014-10-24 15:07:31
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<Jbpm4BizTabDTO> searchJbpm4BizTab(Map<String,Object> searchParams) throws Exception {
	    List<Jbpm4BizTabDTO> dataList = dao.searchJbpm4BizTab(searchParams);
        return dataList;
    }
	/**
     * @author chen_gang
     * @description: 查询工作流与业务表关联表对象
     * @date 2014-10-24 15:07:31
     * @param id
     * @return
     * @throws
     */ 
	public Jbpm4BizTabDTO queryJbpm4BizTabByPrimaryKey(String id) throws Exception {
		
		Jbpm4BizTabDTO dto = dao.findJbpm4BizTabByPrimaryKey(id);
		
		if(dto == null) dto = new Jbpm4BizTabDTO();
		
		return dto;
		
	}

	/**
     * @title: insertJbpm4BizTab
     * @author chen_gang
     * @description: 新增 工作流与业务表关联表对象
     * @date 2014-10-24 15:07:31
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	@Transactional(rollbackFor= Exception.class)
	public Long insertJbpm4BizTab(Jbpm4BizTabDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertJbpm4BizTab(paramMap);
		
		Jbpm4BizTabDTO resultDto = (Jbpm4BizTabDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateJbpm4BizTab
     * @author chen_gang
     * @description: 修改 工作流与业务表关联表对象
     * @date 2014-10-24 15:07:31
     * @param paramMap
     * @return
     * @throws
     */
	@Transactional(rollbackFor= Exception.class)
	public void updateJbpm4BizTab(Jbpm4BizTabDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateJbpm4BizTab(paramMap);
	}
	
    /**
     * 更新超时时间和提前提醒时间
        * @title: updateOvertimeAndRemindTime
        * @author
        * @description:
        * @date 2015年1月30日 上午11:50:14
        * @param paramMap
        * @throws
     */
    public void updateOvertimeAndRemindTime(Jbpm4BizTabDTO dto) throws Exception{
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dto", dto);
        
        dao.updateOvertimeAndRemindTime(paramMap);
    }
	
	/**
     * @title: deleteJbpm4BizTabByPrimaryKey
     * @author chen_gang
     * @description: 删除 工作流与业务表关联表,按主键
     * @date 2014-10-24 15:07:31
     * @param paramMap
     * @throws
     */ 
	@Transactional(rollbackFor= Exception.class)
	public void deleteJbpm4BizTabByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteJbpm4BizTabByPrimaryKey(paramMap);
	}
	/**
	 * 更新待办任务的 业务任务状态信息
	 * @param id
	 * @param bizTaskType
	 * @throws Exception
	 */
	@Transactional(rollbackFor= Exception.class)
	public void updateBizTaskTypeById(String id,String bizTaskType) throws Exception {
		if(StringUtils.isEmpty(id)) throw new Exception("操作失败！传入的参数id为null");
		
		Jbpm4BizTabDTO dto = new Jbpm4BizTabDTO();
		dto.setId(Long.parseLong(id));
		dto.setBizTaskType(bizTaskType);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		dao.updateBizTaskTypeById(paramMap);
	}
	/**
	 * @author chen_gang
	 * @description: 过流程实例ID 查询获取jbpm4_biz_tab信息
	 * @date 2015年12月28日 下午1:25:11
	 * @param proInsId
	 * @return
	 * @throws Exception
	 */
	public Jbpm4BizTabDTO searchJbpm4BizTabByProInsId(String proInsId) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("proInsId", proInsId);
		Jbpm4BizTabDTO dto = dao.searchJbpm4BizTabByProInsId(param);
		
		if(dto == null) dto = new Jbpm4BizTabDTO();
		
		return dto;
		
	}
}

