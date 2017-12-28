package com.jy.modules.platform.sysarea.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.platform.sysarea.dao.SysAreaDao;
import com.jy.modules.platform.sysarea.dto.SysAreaDTO;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: SysAreaService
 * @description: 定义  行政区域 实现类
 * @author:  lin
 */
@Service("com.jy.modules.platform.sysarea.service.SysAreaService")
public class SysAreaService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysAreaDao dao;

	/**
     * @author lin
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
     * @author lin
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
     * @author lin
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
     * @author lin
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
     * @author lin
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
     * @author lin
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
     * @author lin
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
     * @author bieshuangping
     * @param code
     * @return
     */
    public List<Map> queryChildAreaByCode(String areaCode){
    	return dao.queryChildAreaByCode(areaCode);
    }
    
    /**
     * @author bieshuangping
     * @param pid
     * @return
     */
    public List<Map> queryChildAreaByPid(String parentId){
    	return dao.queryChildAreaByPid(parentId);
    }
    
    /**
     * @author bieshuangping
     * @param pid
     * @return
     */
    public Map getParentCodeByCode(String areaCode){
    	return dao.getParentCodeByCode(areaCode);
    }
    
    /**
     * @author bieshuangping
     * @param pid
     * @return
     */
    public Map getAreaByCode(String areaCode){
    	return dao.getAreaByCode(areaCode);
    }

}

