package com.fintech.modules.platform.sysarea.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysarea.dao.SysAreaDao;
import com.fintech.modules.platform.sysarea.dto.SysAreaDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysAreaService
 * @description: 定义  行政区域 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysarea.service.SysAreaService")
public class SysAreaService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysAreaDao dao;

	/**
     * @author
     * @description: 分页查询 行政区域列表
     * @date 2014-10-23 09:53:30
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysAreaDTO> searchSysAreaByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysAreaDTO> dataList =  dao.searchSysAreaByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询行政区域列表
     * @date 2014-10-23 09:53:30
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysAreaDTO> searchSysArea(Map<String,Object> searchParams) throws Exception {
	    List<SysAreaDTO> dataList = dao.searchSysArea(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询行政区域对象
     * @date 2014-10-23 09:53:30
     * @param id
     * @return
     * @throws
     */ 
	public SysAreaDTO querySysAreaByPrimaryKey(String id) throws Exception {
		
		SysAreaDTO dto = dao.findSysAreaByPrimaryKey(id);
		
		if(dto == null) dto = new SysAreaDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysArea
     * @author
     * @description: 新增 行政区域对象
     * @date 2014-10-23 09:53:30
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysArea(SysAreaDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysArea(paramMap);
		
		SysAreaDTO resultDto = (SysAreaDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysArea
     * @author
     * @description: 修改 行政区域对象
     * @date 2014-10-23 09:53:30
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysArea(SysAreaDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysArea(paramMap);
	}
	/**
     * @title: deleteSysAreaByPrimaryKey
     * @author
     * @description: 删除 行政区域,按主键
     * @date 2014-10-23 09:53:30
     * @param paramMap
     * @throws
     */ 
	public void deleteSysAreaByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysAreaByPrimaryKey(paramMap);
	}
	
	
	/**
     * @author
     * @description: 按城市编码查询父节点信息
     * @date 2014-10-28 10:53:30
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysAreaDTO> searchSysAreaByAreaCode(Map<String,Object> searchParams) throws Exception {
	    List<SysAreaDTO> dataList = dao.searchSysAreaByAreaCode(searchParams);
        return dataList;
    }
	
	/**
     * @author
     * @param code
     * @return
     */
    public List<Map> queryChildAreaByCode(String areaCode){
    	return dao.queryChildAreaByCode(areaCode);
    }
    
    /**
     * @author
     * @param pid
     * @return
     */
    public List<Map> queryChildAreaByPid(String parentId){
    	return dao.queryChildAreaByPid(parentId);
    }
    
    /**
     * @author
     * @param pid
     * @return
     */
    public Map getParentCodeByCode(String areaCode){
    	return dao.getParentCodeByCode(areaCode);
    }
    
    /**
     * @author
     * @param pid
     * @return
     */
    public Map getAreaByCode(String areaCode){
    	return dao.getAreaByCode(areaCode);
    }

}

