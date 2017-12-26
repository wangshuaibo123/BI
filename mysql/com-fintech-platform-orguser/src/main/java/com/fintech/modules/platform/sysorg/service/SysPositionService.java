package com.fintech.modules.platform.sysorg.service;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysorg.dao.SysOrgUserDao;
import com.fintech.modules.platform.sysorg.dao.SysPositionDao;
import com.fintech.modules.platform.sysorg.dto.SysPositionDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysPositionService
 * @description: 定义  岗位定义表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysorg.service.SysPositionService")
public class SysPositionService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysPositionDao dao;
	@Autowired
	SysOrgUserDao sysOrgUserDao;

	/**
     * @author
     * @description: 分页查询 岗位定义表列表
     * @date 2014-10-15 10:26:19
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysPositionDTO> searchSysPositionByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysPositionDTO> dataList =  dao.searchSysPositionByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询岗位定义表列表
     * @date 2014-10-15 10:26:19
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysPositionDTO> searchSysPosition(Map<String,Object> searchParams) throws Exception {
	    List<SysPositionDTO> dataList = dao.searchSysPosition(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询岗位定义表对象
     * @date 2014-10-15 10:26:19
     * @param id
     * @return
     * @throws
     */ 
	public SysPositionDTO querySysPositionByPrimaryKey(String id) throws Exception {
		
		SysPositionDTO dto = dao.findSysPositionByPrimaryKey(id);
		
		if(dto == null) dto = new SysPositionDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysPosition
     * @author
     * @description: 新增 岗位定义表对象
     * @date 2014-10-15 10:26:19
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysPosition(SysPositionDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		
		
		int count = dao.insertSysPosition(paramMap);
		
		SysPositionDTO resultDto = (SysPositionDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysPosition
     * @author
     * @description: 修改 岗位定义表对象
     * @date 2014-10-15 10:26:19
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysPosition(SysPositionDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysPosition(paramMap);
	}
	/**
     * @title: deleteSysPositionByPrimaryKey
     * @author
     * @description: 删除 岗位定义表,按主键
     * @date 2014-10-15 10:26:19
     * @param paramMap
     * @throws
     */ 
	public void deleteSysPositionByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysPositionByPrimaryKey(paramMap);
		
		sysOrgUserDao.deleteSysOrgByPositionIds(paramMap);
	}
	/**
     * @author
     * @description: 根据岗位ID查询岗位详细信息
     * @date 2015-06-24 10:26:19
     * @param id
     * @return
     * @throws
     */ 
	public SysPositionDTO findSysPositionByPrimaryKey(String id) throws Exception {
		
		SysPositionDTO dto = dao.findSysPositionByPrimaryKey(id);
		return dto;
		
	}
}

