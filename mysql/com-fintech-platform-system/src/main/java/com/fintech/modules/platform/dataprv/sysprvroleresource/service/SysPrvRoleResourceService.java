package com.fintech.modules.platform.dataprv.sysprvroleresource.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.dataprv.sysprvauthresult.service.SysPrvAuthResultService;
import com.fintech.modules.platform.dataprv.sysprvrole.dao.SysPrvRoleDao;
import com.fintech.modules.platform.dataprv.sysprvroleresource.dao.SysPrvRoleResourceDao;
import com.fintech.modules.platform.dataprv.sysprvroleresource.dto.SysPrvRoleResourceDTO;
import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysPrvRoleResourceService
 * @description: 定义 数据角色资源表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.dataprv.sysprvroleresource.service.SysPrvRoleResourceService")
public class SysPrvRoleResourceService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SysPrvRoleResourceDao dao;

	@Autowired
	private SysPrvRoleDao rDao;

	@Autowired
	private SysPrvAuthResultService rService;
	@Autowired
	private OrgAPI orgApi;

	/**
	 * @author
	 * @description: 分页查询 数据角色资源表列表
	 * @date 2014-10-18 16:07:31
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvRoleResourceDTO> searchSysPrvRoleResourceByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvRoleResourceDTO> dataList = dao
				.searchSysPrvRoleResourceByPaging(searchParams);
		for (SysPrvRoleResourceDTO dto : dataList) {
			if ("user".equals(dto.getResourceType()))
				dto.setCreateUserNameExt(orgApi.getUserInfoDetail(
						dto.getResourceId().toString()).getUserName());
			else
				dto.setCreateUserNameExt(orgApi.getOrgInfo(
						dto.getResourceId().toString()).getOrgName());
		}
		return dataList;
	}

	/**
	 * @author
	 * @description: 按条件查询数据角色资源表列表
	 * @date 2014-10-18 16:07:31
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvRoleResourceDTO> searchSysPrvRoleResource(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvRoleResourceDTO> dataList = dao
				.searchSysPrvRoleResource(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 查询数据角色资源表对象
	 * @date 2014-10-18 16:07:31
	 * @param id
	 * @return
	 * @throws
	 */
	public SysPrvRoleResourceDTO querySysPrvRoleResourceByPrimaryKey(String id)
			throws Exception {

		SysPrvRoleResourceDTO dto = dao.findSysPrvRoleResourceByPrimaryKey(id);

		if (dto == null)
			dto = new SysPrvRoleResourceDTO();

		return dto;

	}

	/**
	 * @title: insertSysPrvRoleResource
	 * @author
	 * @description: 新增 数据角色资源表对象
	 * @date 2014-10-18 16:07:31
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public void insertSysPrvRoleResource(String roleId, String resourceIds,
			String resourceType) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SysPrvRoleResourceDTO dto = new SysPrvRoleResourceDTO();
		dto.setRoleId(Long.parseLong(roleId));
		dto.setResourceType(resourceType);
		paramMap.put("dto", dto);

		if (resourceIds != null && resourceIds.length() > 0) {
			if (resourceIds.indexOf(",") > 0) {
				String[] resStr = resourceIds.split(",");
				for (String str : resStr) {
					dto.setResourceId(Long.parseLong(str));
					dao.insertSysPrvRoleResource(paramMap);
				}
			} else {
				dto.setResourceId(Long.parseLong(resourceIds));
				dao.insertSysPrvRoleResource(paramMap);
			}
		}
	}

	/**
	 * @title: updateSysPrvRoleResource
	 * @author
	 * @description: 修改 数据角色资源表对象
	 * @date 2014-10-18 16:07:31
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateSysPrvRoleResource(SysPrvRoleResourceDTO dto)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resourceIds", dto.getId());
		List<Map> deleteList = rDao.queryUserRoleResourceListByIds(map);
		rService.updateOrDeleteRoleAuth(deleteList);
		dao.updateSysPrvRoleResource(paramMap);
	}

	/**
	 * @title: deleteSysPrvRoleResourceByID
	 * @author
	 * @description: 删除 数据角色资源表,按主键
	 * @date 2014-10-18 16:07:31
	 * @param paramMap
	 * @throws
	 */
	public void deleteSysPrvRoleResourceByID(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", ids);
		List<Map> deleteList = rDao.queryUserRoleResourceListByIds(map);
		rService.updateOrDeleteRoleAuth(deleteList);
		dao.deleteSysPrvRoleResourceByID(paramMap);
	}

	public String queryRoleResourceByResource(Map<String, Object> map) {
		return dao.queryRoleResourceByResource(map);
	}

}
