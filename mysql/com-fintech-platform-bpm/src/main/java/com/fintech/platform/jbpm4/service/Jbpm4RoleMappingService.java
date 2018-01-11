package com.fintech.platform.jbpm4.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.platform.api.sysauth.SysRoleAPI;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.jbpm4.dto.Jbpm4RoleMappingDTO;
import com.fintech.platform.jbpm4.repository.Jbpm4RoleMappingDao;


/**
 * @classname: Jbpm4RoleMappingService
 * @description: 定义  工作流角色映射表 实现类
 * @author
 */
@Service("com.fintech.platform.jbpm4.service.Jbpm4RoleMappingService")
public class Jbpm4RoleMappingService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private Jbpm4RoleMappingDao dao;
	
	@Autowired
	private SysRoleAPI sysRoleAPI;

	/**
     * @author
     * @description: 分页查询 工作流角色映射表列表
     * @date 2015-06-05 10:32:32
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<Jbpm4RoleMappingDTO> searchJbpm4RoleMappingByPaging(Map<String,Object> searchParams) throws Exception {
		List<Jbpm4RoleMappingDTO> dataList =  dao.searchJbpm4RoleMappingByPaging(searchParams);
		if(null!=dataList && dataList.size()>0){
			for(Jbpm4RoleMappingDTO roleMappingDTO:dataList){
				if(null!=roleMappingDTO.getRoleCode() && !"".equals(roleMappingDTO.getRoleCode())){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("roleCode", roleMappingDTO.getRoleCode());
					List<Map<String, Object>> roleList = sysRoleAPI.queryRoleList(0, 1, param);
					if(roleList != null && roleList.size() > 0){
						Map<String,Object> roleInfo = roleList.get(0);
						roleMappingDTO.setRoleCodeName((String)roleInfo.get("roleName"));
					}
				}
				if(null!=roleMappingDTO.getMappingRoleCode() && !"".equals(roleMappingDTO.getMappingRoleCode())){
					Map<String, Object> param1 = new HashMap<String, Object>();
					param1.put("roleCode", roleMappingDTO.getMappingRoleCode());
					List<Map<String, Object>> roleList1 = sysRoleAPI.queryRoleList(0, 1, param1);
					if(roleList1 != null && roleList1.size() > 0){
						Map<String,Object> roleInfo1 = roleList1.get(0);
						roleMappingDTO.setMappingRoleCodeName((String)roleInfo1.get("roleName"));
					}
				}
				
			}
			
		}
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询工作流角色映射表列表
     * @date 2015-06-05 10:32:32
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<Jbpm4RoleMappingDTO> searchJbpm4RoleMapping(Map<String,Object> searchParams) throws Exception {
	    List<Jbpm4RoleMappingDTO> dataList = dao.searchJbpm4RoleMapping(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询工作流角色映射表对象
     * @date 2015-06-05 10:32:32
     * @param id
     * @return
     * @throws
     */ 
	public Jbpm4RoleMappingDTO queryJbpm4RoleMappingByPrimaryKey(String id) throws Exception {
		
		Jbpm4RoleMappingDTO dto = dao.findJbpm4RoleMappingByPrimaryKey(id);
		
		if(dto == null) dto = new Jbpm4RoleMappingDTO();
		
		return dto;
		
	}

	/**
     * @title: insertJbpm4RoleMapping
     * @author
     * @description: 新增 工作流角色映射表对象
     * @date 2015-06-05 10:32:32
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertJbpm4RoleMapping(Jbpm4RoleMappingDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertJbpm4RoleMapping(paramMap);
		
		Jbpm4RoleMappingDTO resultDto = (Jbpm4RoleMappingDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateJbpm4RoleMapping
     * @author
     * @description: 修改 工作流角色映射表对象
     * @date 2015-06-05 10:32:32
     * @param paramMap
     * @return
     * @throws
     */
	public void updateJbpm4RoleMapping(Jbpm4RoleMappingDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateJbpm4RoleMapping(paramMap);
	}
	/**
     * @title: deleteJbpm4RoleMappingByPrimaryKey
     * @author
     * @description: 删除 工作流角色映射表,按主键
     * @date 2015-06-05 10:32:32
     * @param paramMap
     * @throws
     */ 
	public void deleteJbpm4RoleMappingByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteJbpm4RoleMappingByPrimaryKey(paramMap);
	}

}

