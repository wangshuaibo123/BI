package com.fintech.modules.platform.sysindustry.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysindustry.dao.SysIndustryDao;
import com.fintech.modules.platform.sysindustry.dto.SysIndustryDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysIndustryService
 * @description: 定义  sys_industry 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysindustry.service.SysIndustryService")
public class SysIndustryService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysIndustryDao dao;

	/**
     * @author
     * @description: 分页查询 sys_industry列表
     * @date 2014-12-04 14:03:34
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysIndustryDTO> searchSysIndustryByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysIndustryDTO> dataList =  dao.searchSysIndustryByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询sys_industry列表
     * @date 2014-12-04 14:03:34
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysIndustryDTO> searchSysIndustry(Map<String,Object> searchParams) throws Exception {
	    List<SysIndustryDTO> dataList = dao.searchSysIndustry(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询sys_industry对象
     * @date 2014-12-04 14:03:34
     * @param id
     * @return
     * @throws
     */ 
	public SysIndustryDTO querySysIndustryByPrimaryKey(String id) throws Exception {
		
		SysIndustryDTO dto = dao.findSysIndustryByPrimaryKey(id);
		
		if(dto == null) dto = new SysIndustryDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysIndustry
     * @author
     * @description: 新增 sys_industry对象
     * @date 2014-12-04 14:03:34
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysIndustry(SysIndustryDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysIndustry(paramMap);
		
		SysIndustryDTO resultDto = (SysIndustryDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysIndustry
     * @author
     * @description: 修改 sys_industry对象
     * @date 2014-12-04 14:03:34
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysIndustry(SysIndustryDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysIndustry(paramMap);
	}
	/**
     * @title: deleteSysIndustryByPrimaryKey
     * @author
     * @description: 删除 sys_industry,按主键
     * @date 2014-12-04 14:03:34
     * @param paramMap
     * @throws
     */ 
	public void deleteSysIndustryByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysIndustryByPrimaryKey(paramMap);
	}

	/**
     * @author
     * @description: 按条件查询sys_industry列表
     * @date 2014-12-04 14:03:34
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<Map> searchIndustries(Map<String,Object> searchParams) throws Exception {
	    List<Map> dataList = dao.searchIndustries(searchParams);
        return dataList;
    }
	
	
	/**
	 * 根据行业ID查询其下的职位
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryPositionByIndustry(String defaultCode)throws Exception{
		return dao.queryPositionByIndustry(defaultCode);
	}
	
	
	public List<Map> getPositionsByIndustry(Map<String,Object> searchParams)throws Exception{
		return dao.getPositionsByIndustry(searchParams);
	}
	
}

