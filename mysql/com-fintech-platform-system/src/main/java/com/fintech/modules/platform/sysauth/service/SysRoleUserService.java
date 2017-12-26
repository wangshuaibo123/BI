package com.fintech.modules.platform.sysauth.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysauth.dao.SysRoleDao;
import com.fintech.modules.platform.sysauth.dao.SysRoleUserDao;
import com.fintech.modules.platform.sysauth.dto.SysRoleUserDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysRoleUserService
 * @description: 定义  角色用户表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysauth.service.SysRoleUserService")
public class SysRoleUserService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysRoleUserDao dao;
	
	@Autowired
	private SysRoleDao sysRoleDao;

	/**
     * @author
     * @description: 分页查询 角色用户表列表
     * @date 2014-10-15 10:25:12
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysRoleUserDTO> searchSysRoleUserByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysRoleUserDTO> dataList =  dao.searchSysRoleUserByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询角色用户表列表
     * @date 2014-10-15 10:25:12
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysRoleUserDTO> searchSysRoleUser(Map<String,Object> searchParams) throws Exception {
	    List<SysRoleUserDTO> dataList = dao.searchSysRoleUser(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询角色用户表对象
     * @date 2014-10-15 10:25:12
     * @param id
     * @return
     * @throws
     */ 
	public SysRoleUserDTO querySysRoleUserByPrimaryKey(String id) throws Exception {
		
		SysRoleUserDTO dto = dao.findSysRoleUserByPrimaryKey(id);
		
		if(dto == null) dto = new SysRoleUserDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysRoleUser
     * @author
     * @description: 新增 角色用户表对象
     * @date 2014-10-15 10:25:12
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysRoleUser(SysRoleUserDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysRoleUser(paramMap);
		
		SysRoleUserDTO resultDto = (SysRoleUserDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysRoleUser
     * @author
     * @description: 修改 角色用户表对象
     * @date 2014-10-15 10:25:12
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysRoleUser(SysRoleUserDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysRoleUser(paramMap);
	}
	/**
     * @title: deleteSysRoleUserByPrimaryKey
     * @author
     * @description: 删除 角色用户表,按roleId
     * @date 2014-10-15 10:25:12
     * @param paramMap
     * @throws
     */ 
	public void deleteSysRoleUserByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysRoleUserByPrimaryKey(paramMap);
	}
	
	/**
     * @title: deleteSysRoleUserByRoleId
     * @author
     * @description: 删除 角色用户表,按角色id
     * @date 2014-10-15 10:25:12
     * @param paramMap
     * @throws
     */ 
	public void deleteSysRoleUserByRoleId(BaseDTO baseDto){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		dao.deleteSysRoleUserByRoleId(paramMap);
	}
	
	public SysRoleUserDTO findSysRoleOrgByRoleId(Map<String, Object> searchParams){
		List<SysRoleUserDTO> list=dao.findSysRoleOrgByRoleId(searchParams); 
		if(list==null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	
    /**
     * @author
     * @deprecated: 获取当前用户是管理员的情况下，能管理的机构和权限信息
     * @return
     */
	public SysRoleUserDTO findSysRoleOrgByCurrentUser(Map<String, Object> searchParams){
		SysRoleUserDTO sysRoleUserDTO=dao.findSysRoleOrgByCurrentUser(searchParams);
		return sysRoleUserDTO;
	}
	
	/**
     * @title: cleanSysRoleUserByOrgId
     * @author
     * @description: 清理当前登陆用户管理机构下的离职人员的操作权限,根据orgId进行无效用户操作权限清理
     * @date 2015-07-10
     * @param paramMap
     * @throws
     */ 
	public void modifySysRoleUserByOrgId(Map<String, Object> searchParams) throws Exception {
		dao.modifySysRoleUserByOrgId(searchParams);
	}
}

