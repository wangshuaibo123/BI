package com.fintech.modules.platform.sysauth.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysauth.dao.SysAclDao;
import com.fintech.modules.platform.sysauth.dao.SysResourceDao;
import com.fintech.modules.platform.sysauth.dto.SysResourceDTO;
import com.fintech.modules.platform.sysauth.dto.SysResourceRoleDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysResourceService
 * @description: 定义 资源管理表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysauth.service.SysResourceService")
public class SysResourceService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SysResourceDao dao;
	@Autowired
	private SysAclDao aclDao;

	/**
	 * @author
	 * @description: 分页查询 资源管理表列表
	 * @date 2014-10-15 10:24:37
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysResourceDTO> searchSysResourceByPaging(Map<String, Object> searchParams) throws Exception {
		List<SysResourceDTO> dataList = dao.searchSysResourceByPaging(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 按条件查询资源管理表列表
	 * @date 2014-10-15 10:24:37
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysResourceDTO> searchSysResource(Map<String, Object> searchParams) throws Exception {
		List<SysResourceDTO> dataList = dao.searchSysResource(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 查询资源管理表对象
	 * @date 2014-10-15 10:24:37
	 * @param id
	 * @return
	 * @throws
	 */
	public SysResourceDTO querySysResourceByPrimaryKey(String id) throws Exception {

		SysResourceDTO dto = dao.findSysResourceByPrimaryKey(id);

		if (dto == null)
			dto = new SysResourceDTO();

		return dto;

	}

	/**
	 * @title: insertSysResource
	 * @author
	 * @description: 新增 资源管理表对象
	 * @date 2014-10-15 10:24:37
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public Long insertSysResource(SysResourceDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		int count = dao.insertSysResource(paramMap);

		SysResourceDTO resultDto = (SysResourceDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}

	/**
	 * @title: updateSysResource
	 * @author
	 * @description: 修改 资源管理表对象
	 * @date 2014-10-15 10:24:37
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateSysResource(SysResourceDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		dao.updateSysResource(paramMap);
	}

	/**
	 * @title: deleteSysResourceByPrimaryKey
	 * @author
	 * @description: 删除 资源管理表,按主键
	 * @date 2014-10-15 10:24:37
	 * @param paramMap
	 * @throws
	 */
	public void deleteSysResourceByPrimaryKey(BaseDTO baseDto, String ids) throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysResourceByPrimaryKeys(paramMap);
	}

	/**
	 * @author
	 * 按角色id 获取一组资源信息
	 * @param roleId
	 * @return
	 */
	public List<SysResourceDTO> getResourceDTO(Long roleId) {
		List<Long> resourceIdList = aclDao.getResourceIdsByRoleId(roleId);
		return dao.getSysResourceByIds(resourceIdList);

	}
	/**
	 * 通过资源类型获取资源列表，aop缓存
	 * @param resoureType
	 * @return
	 */
	public List<SysResourceRoleDTO> getSysResourceRole(String resoureType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("resoureType", resoureType);
		List<SysResourceRoleDTO> resourceIdList = dao.getSysResourceRole(paramMap);
		return resourceIdList;
	}
	/**
	 * 通过角色获取按钮权限，aop缓存
	 * @param roleCode
	 * @return
	 */
	public List<SysResourceRoleDTO> getSysResourcePermission(String roleCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("resoureType", "button");
		paramMap.put("roleCode", roleCode);
		List<SysResourceRoleDTO> resourceIdList = dao.getSysResourceRole(paramMap);
		return resourceIdList;
	}

	/**
	 * 通过角色获取URL权限，aop缓存 getSysResourceUrl
	 * @param roleCode
	 * @return
	 */
	public List<SysResourceRoleDTO> getSysResourceUrl(String roleCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("resoureType", "url");
		paramMap.put("roleCode", roleCode);
		List<SysResourceRoleDTO> resourceIdList = dao.getSysResourceRole(paramMap);
		return resourceIdList;
	}
	
	
	/**
	 * 导入资源
	 * @param url 资源
	 * @param buttons URL下的button资源
	 */
	public void saveImportSysResource(SysResourceDTO url,List<SysResourceDTO> buttons){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", url);
		dao.insertSysResource(paramMap);
		SysResourceDTO resultDto = (SysResourceDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		String parents = resultDto.getParentIds();
		for (int i = 0; i < buttons.size(); i++) {
			Map<String, Object> param = new HashMap<String, Object>();
			SysResourceDTO buttonResourceDTO = buttons.get(i);
			buttonResourceDTO.setParentId(String.valueOf(keyId));
			buttonResourceDTO.setParentIds(parents+"/"+String.valueOf(keyId));
			param.put("dto",buttonResourceDTO);
			dao.insertSysResource(param);
		}
	}
	
	public List<SysResourceRoleDTO> getSysResourceAllUrl(String url){
		Map<String, Object> paramMap= new HashMap<String, Object>();
		paramMap.put("url", url);
		List<SysResourceRoleDTO> resourceIdList = dao.getSysResourceAllUrl(paramMap);
		return resourceIdList;
	}
	
}
