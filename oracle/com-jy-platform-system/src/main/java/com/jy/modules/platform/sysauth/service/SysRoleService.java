package com.jy.modules.platform.sysauth.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jy.modules.platform.sysauth.dao.SysRoleDao;
import com.jy.modules.platform.sysauth.dto.SysRoleDTO;
import com.jy.modules.platform.sysauth.dto.SysRoleUserDTO;
import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.restclient.http.RestService;

/**
 * @classname: SysRoleService
 * @description: 定义  角色表 实现类
 * @author:  chen_gang
 */
@Service("com.jy.modules.platform.sysauth.service.SysRoleService")
public class SysRoleService implements Serializable {

    private static final long serialVersionUID = 1L;
    private String jyptAppId = "jypt"; //rest服务appId
    private String jyptURL = RestService.getServiceUrl(jyptAppId);//rest服务地址
	@Autowired
	private SysRoleDao dao;
	
	@Autowired
	private SessionAPI sessionAPI;
	
	@Autowired
	@Qualifier("com.jy.modules.platform.sysauth.service.SysRoleUserService")
	private SysRoleUserService service;

	/**
     * @author chen_gang
     * @description: 分页查询 角色表列表
     * @date 2014-10-15 10:24:59
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysRoleDTO> searchSysRoleByPaging(Map<String,Object> searchParams) throws Exception {
		UserInfo user=sessionAPI.getCurrentUserInfo();
		
    	//获取当前用户管理的机构
		Map<String, Object> userParam=new HashMap<String, Object>();
		userParam.put("orgId", user.getOrgId());
		userParam.put("userId", user.getUserId());
		SysRoleUserDTO sDto=service.findSysRoleOrgByCurrentUser(userParam);
		//获取当前用户管理的机构
		
		
		if(sDto!=null && user.getOrgParentId()!=null && !user.getOrgParentId().equals("0"))
			searchParams.put("orgId", sDto.getTargetId());
		List<SysRoleDTO> dataList =  dao.searchSysRoleByPaging(searchParams);
		return dataList;
	}
	/**
     * @author chen_gang
     * @description: 按条件查询角色表列表
     * @date 2014-10-15 10:24:59
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysRoleDTO> searchSysRole(Map<String,Object> searchParams) throws Exception {
	    List<SysRoleDTO> dataList = dao.searchSysRole(searchParams);
        return dataList;
    }
	/**
     * @author chen_gang
     * @description: 查询角色表对象
     * @date 2014-10-15 10:24:59
     * @param id
     * @return
     * @throws
     */ 
	public SysRoleDTO querySysRoleByPrimaryKey(String id) throws Exception {
		
		SysRoleDTO dto = dao.findSysRoleByPrimaryKey(id);
		
		if(dto == null) dto = new SysRoleDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysRole
     * @author chen_gang
     * @description: 新增 角色表对象
     * @date 2014-10-15 10:24:59
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysRole(SysRoleDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysRole(paramMap);
		
		SysRoleDTO resultDto = (SysRoleDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysRole
     * @author chen_gang
     * @description: 修改 角色表对象
     * @date 2014-10-15 10:24:59
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysRole(SysRoleDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysRole(paramMap);
	}
	/**
     * @title: deleteSysRoleByPrimaryKey
     * @author chen_gang
     * @description: 删除 角色表,按主键
     * @date 2014-10-15 10:24:59
     * @param paramMap
     * @throws
     */ 
	public void deleteSysRoleByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysRoleByPrimaryKey(paramMap);
	}
	
	
	
	/**
	 * 通过用户获取角色，aop缓存
	 * @param userId
	 * @return
	 */
	public List<SysRoleDTO>  getRoleByUserId(Long userId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("targetId", userId);
		return dao.getSysRoleByTargetId(paramMap);
	}
	
}

