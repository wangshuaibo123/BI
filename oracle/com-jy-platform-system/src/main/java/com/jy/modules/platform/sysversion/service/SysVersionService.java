package com.jy.modules.platform.sysversion.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.platform.sysversion.dao.SysVersionDao;
import com.jy.modules.platform.sysversion.dto.SysVersionDTO;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: SysVersionService
 * @description: 定义  系统版本号表 实现类
 * @author:  lei
 */
@Service("com.jy.modules.platform.sysversion.service.SysVersionService")
public class SysVersionService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysVersionDao dao;

	/**
     * @author lei
     * @description: 分页查询 系统版本号表列表
     * @date 2015-03-17 10:32:51
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysVersionDTO> searchSysVersionByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysVersionDTO> dataList =  dao.searchSysVersionByPaging(searchParams);
		return dataList;
	}
	/**
     * @author lei
     * @description: 按条件查询系统版本号表列表
     * @date 2015-03-17 10:32:51
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysVersionDTO> searchSysVersion(Map<String,Object> searchParams) throws Exception {
	    List<SysVersionDTO> dataList = dao.searchSysVersion(searchParams);
        return dataList;
    }
	/**
     * @author lei
     * @description: 查询系统版本号表对象
     * @date 2015-03-17 10:32:51
     * @param id
     * @return
     * @throws
     */ 
	public SysVersionDTO querySysVersionByPrimaryKey(String id) throws Exception {
		
		SysVersionDTO dto = dao.findSysVersionByPrimaryKey(id);
		
		if(dto == null) dto = new SysVersionDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysVersion
     * @author lei
     * @description: 新增 系统版本号表对象
     * @date 2015-03-17 10:32:51
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysVersion(SysVersionDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysVersion(paramMap);
		
		SysVersionDTO resultDto = (SysVersionDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysVersion
     * @author lei
     * @description: 修改 系统版本号表对象
     * @date 2015-03-17 10:32:51
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysVersion(SysVersionDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysVersion(paramMap);
	}
	/**
     * @title: deleteSysVersionByPrimaryKey
     * @author lei
     * @description: 删除 系统版本号表,按主键
     * @date 2015-03-17 10:32:51
     * @param paramMap
     * @throws
     */ 
	public void deleteSysVersionByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysVersionByPrimaryKey(paramMap);
	}

}

