package com.fintech.modules.platform.sysauth.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysauth.dao.SysRoleGroupRoleDao;
import com.fintech.modules.platform.sysauth.dto.SysRoleGroupRoleDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysRoleGroupRoleService
 * @description: 定义  角色组角色中间表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysrolegrouprole.service.SysRoleGroupRoleService")
public class SysRoleGroupRoleService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysRoleGroupRoleDao dao;

	/**
     * @author
     * @description: 分页查询 角色组角色中间表列表
     * @date 2014-11-28 17:38:38
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysRoleGroupRoleDTO> searchSysRoleGroupRoleByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysRoleGroupRoleDTO> dataList =  dao.searchSysRoleGroupRoleByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询角色组角色中间表列表
     * @date 2014-11-28 17:38:38
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysRoleGroupRoleDTO> searchSysRoleGroupRole(Map<String,Object> searchParams) throws Exception {
	    List<SysRoleGroupRoleDTO> dataList = dao.searchSysRoleGroupRole(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询角色组角色中间表对象
     * @date 2014-11-28 17:38:38
     * @param id
     * @return
     * @throws
     */ 
	public SysRoleGroupRoleDTO querySysRoleGroupRoleByPrimaryKey(String id) throws Exception {
		
		SysRoleGroupRoleDTO dto = dao.findSysRoleGroupRoleByPrimaryKey(id);
		
		if(dto == null) dto = new SysRoleGroupRoleDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysRoleGroupRole
     * @author
     * @description: 新增 角色组角色中间表对象
     * @date 2014-11-28 17:38:38
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysRoleGroupRole(SysRoleGroupRoleDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysRoleGroupRole(paramMap);
		
		SysRoleGroupRoleDTO resultDto = (SysRoleGroupRoleDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysRoleGroupRole
     * @author
     * @description: 修改 角色组角色中间表对象
     * @date 2014-11-28 17:38:38
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysRoleGroupRole(SysRoleGroupRoleDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysRoleGroupRole(paramMap);
	}
	/**
     * @title: deleteSysRoleGroupRoleByPrimaryKey
     * @author
     * @description: 删除 角色组角色中间表,按主键
     * @date 2014-11-28 17:38:38
     * @param paramMap
     * @throws
     */ 
	public void deleteSysRoleGroupRoleByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysRoleGroupRoleByPrimaryKey(paramMap);
	}
	
	/**
     * @title: deleteSysRoleGroupRoleByPrimaryKey
     * @author
     * @description: 删除 角色组角色中间表,按主键
     * @date 2014-11-28 17:38:38
     * @param paramMap
     * @throws
     */ 
	public void deleteSysRoleGroupRoleByID(String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ids);
		dao.deleteSysRoleGroupRoleByID(paramMap);
	}
}

