package com.jy.modules.platform.sysauth.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.platform.sysauth.dao.SysRoleGroupDao;
import com.jy.modules.platform.sysauth.dto.SysRoleGroupDTO;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: SysRoleGroupService
 * @description: 定义  角色组 实现类
 * @author:  yuchengyang-pc
 */
@Service("com.jy.modules.platform.sysrolegroup.service.SysRoleGroupService")
public class SysRoleGroupService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysRoleGroupDao dao;

	/**
     * @author yuchengyang-pc
     * @description: 分页查询 角色组列表
     * @date 2014-11-28 15:38:04
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysRoleGroupDTO> searchSysRoleGroupByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysRoleGroupDTO> dataList =  dao.searchSysRoleGroupByPaging(searchParams);
		return dataList;
	}
	/**
     * @author yuchengyang-pc
     * @description: 按条件查询角色组列表
     * @date 2014-11-28 15:38:04
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysRoleGroupDTO> searchSysRoleGroup(Map<String,Object> searchParams) throws Exception {
	    List<SysRoleGroupDTO> dataList = dao.searchSysRoleGroup(searchParams);
        return dataList;
    }
	/**
     * @author yuchengyang-pc
     * @description: 查询角色组对象
     * @date 2014-11-28 15:38:04
     * @param id
     * @return
     * @throws
     */ 
	public SysRoleGroupDTO querySysRoleGroupByPrimaryKey(String id) throws Exception {
		
		SysRoleGroupDTO dto = dao.findSysRoleGroupByPrimaryKey(id);
		
		if(dto == null) dto = new SysRoleGroupDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysRoleGroup
     * @author yuchengyang-pc
     * @description: 新增 角色组对象
     * @date 2014-11-28 15:38:04
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysRoleGroup(SysRoleGroupDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysRoleGroup(paramMap);
		
		SysRoleGroupDTO resultDto = (SysRoleGroupDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysRoleGroup
     * @author yuchengyang-pc
     * @description: 修改 角色组对象
     * @date 2014-11-28 15:38:04
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysRoleGroup(SysRoleGroupDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysRoleGroup(paramMap);
	}
	/**
     * @title: deleteSysRoleGroupByPrimaryKey
     * @author yuchengyang-pc
     * @description: 删除 角色组,按主键
     * @date 2014-11-28 15:38:04
     * @param paramMap
     * @throws
     */ 
	public void deleteSysRoleGroupByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysRoleGroupByPrimaryKey(paramMap);
	}

}

