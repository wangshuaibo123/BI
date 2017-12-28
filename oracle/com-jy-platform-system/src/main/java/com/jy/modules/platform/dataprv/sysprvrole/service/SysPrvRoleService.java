package com.jy.modules.platform.dataprv.sysprvrole.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.modules.platform.dataprv.sysprvauthresult.service.SysPrvAuthResultService;
import com.jy.modules.platform.dataprv.sysprvrole.dao.SysPrvRoleDao;
import com.jy.modules.platform.dataprv.sysprvrole.dto.SysPrvRoleDTO;
import com.jy.modules.platform.dataprv.sysprvroleauth.dao.SysPrvRoleAuthDao;
import com.jy.modules.platform.dataprv.sysprvroleresource.dao.SysPrvRoleResourceDao;
import com.jy.modules.platform.sysprvauthpool.dto.SysPrvAuthPoolDTO;
import com.jy.modules.platform.sysprvauthpool.service.SysPrvAuthPoolService;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: SysPrvRoleService
 * @description: 定义 数据权限角色定义 实现类
 * @author: wangxz
 */
@Service("com.jy.modules.platform.dataprv.sysprvrole.service.SysPrvRoleService")
public class SysPrvRoleService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SysPrvRoleDao dao;

	@Autowired
	private SysPrvAuthResultService rService;
	@Autowired
	private SysPrvRoleAuthDao raDao;
	@Autowired
	private SysPrvRoleResourceDao rrDao;
	@Autowired
	private SysPrvAuthPoolService pService;

	/**
	 * @author wangxz
	 * @description: 分页查询 数据权限角色定义列表
	 * @date 2014-10-18 16:07:13
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvRoleDTO> searchSysPrvRoleByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvRoleDTO> dataList = dao
				.searchSysPrvRoleByPaging(searchParams);
		return dataList;
	}

	/**
	 * @author wangxz
	 * @description: 按条件查询数据权限角色定义列表
	 * @date 2014-10-18 16:07:13
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvRoleDTO> searchSysPrvRole(Map<String, Object> searchParams)
			throws Exception {
		List<SysPrvRoleDTO> dataList = dao.searchSysPrvRole(searchParams);
		return dataList;
	}

	/**
	 * @author wangxz
	 * @description: 查询数据权限角色定义对象
	 * @date 2014-10-18 16:07:13
	 * @param id
	 * @return
	 * @throws
	 */
	public SysPrvRoleDTO querySysPrvRoleByPrimaryKey(String id)
			throws Exception {

		SysPrvRoleDTO dto = dao.findSysPrvRoleByPrimaryKey(id);

		if (dto == null)
			dto = new SysPrvRoleDTO();

		return dto;

	}

	/**
	 * @title: insertSysPrvRole
	 * @author wangxz
	 * @description: 新增 数据权限角色定义对象
	 * @date 2014-10-18 16:07:13
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	public Long insertSysPrvRole(SysPrvRoleDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);

		int count = dao.insertSysPrvRole(paramMap);

		SysPrvRoleDTO resultDto = (SysPrvRoleDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}

	/**
	 * @title: updateSysPrvRole
	 * @author wangxz
	 * @description: 修改 数据权限角色定义对象
	 * @date 2014-10-18 16:07:13
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateSysPrvRole(SysPrvRoleDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysPrvRole(paramMap);
	}

	/**
	 * @title: deleteSysPrvRoleByID
	 * @author wangxz
	 * @description: 删除 数据权限角色定义,按主键
	 * @date 2014-10-18 16:07:13
	 * @param paramMap
	 * @throws
	 */
	@SuppressWarnings("all")
	@Transactional(rollbackFor=Exception.class)
	public void deleteSysPrvRoleByID(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roleIds", ids);
		//List<Map> deleteList = dao.queryUserRoleResourceListByIds(map);
		//rService.updateOrDeleteRoleAuth(deleteList);
		dao.deleteSysPrvRoleByID(paramMap);
		rrDao.deleteSysPrvRoleResourceByRoleIds(map);
		raDao.deleteSysPrvRoleAuthByRoleIds(map);
		/**
		 * 
		 * 删除 资源pool 跟数据角色相关的所有的数据  pengliuxiang
		 */
		if(ids!=null){
			if(ids.indexOf(",")>0){
				String[] roles = ids.split(",");
				for(String str:roles){
					SysPrvAuthPoolDTO dto  = new SysPrvAuthPoolDTO();
					dto.setDatesource("角色授权");
					dto.setDatesourceid(str);
					pService.deleteSysPrvAuthPoolByMap(dto);
				}
			}else{
				SysPrvAuthPoolDTO dto  = new SysPrvAuthPoolDTO();
				dto.setDatesource("角色授权");
				dto.setDatesourceid(ids);
				pService.deleteSysPrvAuthPoolByMap(dto);	
			}
			
					
		}	
		
	}
	
	public String queryRoleByCode(String code){
		return dao.queryRoleByCode(code);
	}

}
