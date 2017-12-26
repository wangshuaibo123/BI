package com.fintech.modules.platform.sysauth.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.modules.platform.sysauth.dao.SysAclDao;
import com.fintech.modules.platform.sysauth.dto.SysAclDTO;
import com.fintech.modules.platform.sysauth.dto.SysRoleDTO;
import com.fintech.modules.platform.sysauth.dto.SysRoleUserDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysAclService
 * @description: 定义  操作权限控制表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysauth.service.SysAclService")
public class SysAclService implements Serializable {
	private static final Logger logger =  LoggerFactory.getLogger(SysAclService.class);
    private static final long serialVersionUID = 1L;
    @Autowired
	@Qualifier("com.fintech.modules.platform.sysauth.service.SysRoleService")
	private SysRoleService roleBiz;
	@Autowired
	private SysAclDao dao;
	@Autowired
	@Qualifier("com.fintech.modules.platform.sysauth.service.SysRoleUserService")
	private SysRoleUserService roleUserBiz;
	/**
     * @author
     * @description: 分页查询 操作权限控制表列表
     * @date 2014-10-15 10:23:43
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysAclDTO> searchSysAclByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysAclDTO> dataList =  dao.searchSysAclByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询操作权限控制表列表
     * @date 2014-10-15 10:23:43
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysAclDTO> searchSysAcl(Map<String,Object> searchParams) throws Exception {
	    List<SysAclDTO> dataList = dao.searchSysAcl(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询操作权限控制表对象
     * @date 2014-10-15 10:23:43
     * @param id
     * @return
     * @throws
     */ 
	public SysAclDTO querySysAclByPrimaryKey(String id) throws Exception {
		
		SysAclDTO dto = dao.findSysAclByPrimaryKey(id);
		
		if(dto == null) dto = new SysAclDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysAcl
     * @author
     * @description: 新增 操作权限控制表对象
     * @date 2014-10-15 10:23:43
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysAcl(SysAclDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysAcl(paramMap);
		
		SysAclDTO resultDto = (SysAclDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysAcl
     * @author
     * @description: 修改 操作权限控制表对象
     * @date 2014-10-15 10:23:43
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysAcl(SysAclDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysAcl(paramMap);
	}
	/**
     * @title: deleteSysAclByPrimaryKey
     * @author
     * @description: 删除 操作权限控制表,按主键
     * @date 2014-10-15 10:23:43
     * @param paramMap
     * @throws
     */ 
	public void deleteSysAclByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysAclByPrimaryKey(paramMap);
	}
	/**
     * @title: 获取一组资源id
     * @author
     * @description: 角色id获取相应权限
     * @date 2014-10-15 10:23:43
     * @param paramMap
     * @throws
     */ 
	public List<Long> getPermissionByRoleId(Long roleId){
		 return dao.getResourceIdsByRoleId(roleId);
	}

	/**
	 * 为角色添加资源
	 * @param roId
	 * @param resourceIds
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public boolean saveSysAcl(String roleId,String resourceIds) throws Exception{
		boolean result = false;
		String [] temp = resourceIds.split(",");
		
		this.deleteOldAclByRoleId(roleId);
		try {
		    if(temp.length>0&&temp[0].trim()!=""){
		        for(int i=0;i<temp.length;i++){
		            SysAclDTO aclDTO = new SysAclDTO();
		            aclDTO.setRoleId(Long.parseLong(roleId));
		            aclDTO.setResoureId(Long.parseLong(temp[i]));
		            aclDTO.setAccessibility(1L);
		            aclDTO.setAppId(1L);
		            aclDTO.setValidateState("1");
		            aclDTO.setVersion(1L);
		            Map<String, Object> paramMap = new HashMap<String, Object>();
		            paramMap.put("dto", aclDTO);
		            dao.insertSysAcl(paramMap);
		           } 
		    }

			result = true;
		} catch (Exception e) {
			result = false;
			throw new Exception(e);
		}
		return result;
		
	}
	private void deleteOldAclByRoleId(String roleId){
		//删除角色已有资源，从新分配
		Map<String, Object> searchParams = new HashMap<String, Object>();
		SysAclDTO dto = new SysAclDTO();
		dto.setRoleId(Long.parseLong(roleId));
    	searchParams.put("dto", dto);
		List<SysAclDTO> list = dao.searchSysAcl(searchParams);
		String oldRclId = "";
		for (int k = 0; k < list.size(); k++) {
			SysAclDTO sysAclDTO = (SysAclDTO)list.get(k);
			oldRclId +=sysAclDTO.getId();
			if(k!=list.size()-1){
				oldRclId +=",";
			}
		}
		if(!StringUtils.isEmpty(oldRclId)){
			Map<String, Object> delparamMap = new HashMap<String, Object>();
			BaseDTO baseDto = new BaseDTO();
			delparamMap.put("dto", baseDto);
			delparamMap.put("ids", oldRclId);
			dao.deleteSysAclByPrimaryKeys(delparamMap);
		}
		
	}
	/**
	 * 变更资源
	 * @param userId
	 * @param resourceIds
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public void saveBaseAcl(String userId, String resourceIds,String roleCode) throws Exception {
		SysRoleDTO paramsDto = new SysRoleDTO();
		paramsDto.setRoleCode(roleCode);
		
		Map<String, Object> searchParams = new HashMap<String,Object>();
		searchParams.put("dto", paramsDto);
		List<SysRoleDTO> searchSysRole = roleBiz.searchSysRole(searchParams);
		long roleId =0L;
		if (searchSysRole.size() <= 0) {
			paramsDto.setRoleName(roleCode);
			
			if(roleCode.startsWith("SUB_BASE_")){
				paramsDto.setRoleType("4");
			}else{
				paramsDto.setRoleType("3");
			}
			roleId = roleBiz.insertSysRole(paramsDto);
		}else{
			roleId = searchSysRole.get(0).getId();
		}
		
		this.saveSysAcl(String.valueOf(roleId), resourceIds);
		
		SysRoleUserDTO roleUser = new SysRoleUserDTO();
		roleUser.setRoleId(roleId);
		
		roleUserBiz.deleteSysRoleUserByRoleId(roleUser);
		
		roleUser.setTargetId(Long.parseLong(userId));
		roleUser.setTargetType("user");
		roleUserBiz.insertSysRoleUser(roleUser);
	}
	
	
}

