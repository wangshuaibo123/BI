package com.fintech.modules.platform.dataprv.sysprvorgauth.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.modules.platform.dataprv.sysprvauthresult.dao.SysPrvAuthResultDao;
import com.fintech.modules.platform.dataprv.sysprvauthresult.service.SysPrvAuthResultService;
import com.fintech.modules.platform.dataprv.sysprvorgauth.dao.SysPrvOrgAuthDao;
import com.fintech.modules.platform.dataprv.sysprvorgauth.dto.SysPrvOrgAuthDTO;
import com.fintech.modules.platform.dataprv.sysprvrole.dao.SysPrvRoleDao;
import com.fintech.modules.platform.sysprvauthpool.dto.SysPrvAuthPoolDTO;
import com.fintech.modules.platform.sysprvauthpool.service.SysPrvAuthPoolService;
import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysPrvOrgAuthService
 * @description: 定义 组织授权表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.dataprv.sysprvorgauth.service.SysPrvOrgAuthService")
public class SysPrvOrgAuthService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SysPrvOrgAuthDao dao;
	@Autowired
	private SysPrvAuthResultDao sDao;
	@Autowired
	private OrgAPI orgApi;
	
	@Autowired
	private SysPrvRoleDao rDao;

	@Autowired
	private SysPrvAuthResultService rService;
	
	@Autowired
	private SysPrvAuthPoolService pService;

	/**
	 * @author
	 * @description: 分页查询 组织授权表列表
	 * @date 2014-10-18 16:07:01
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvOrgAuthDTO> searchSysPrvOrgAuthByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvOrgAuthDTO> dataList = dao
				.searchSysPrvOrgAuthByPaging(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 按条件查询组织授权表列表
	 * @date 2014-10-18 16:07:01
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvOrgAuthDTO> searchSysPrvOrgAuth(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvOrgAuthDTO> dataList = dao.searchSysPrvOrgAuth(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 查询组织授权表对象
	 * @date 2014-10-18 16:07:01
	 * @param id
	 * @return
	 * @throws
	 */
	public SysPrvOrgAuthDTO querySysPrvOrgAuthByPrimaryKey(String id)
			throws Exception {

		SysPrvOrgAuthDTO dto = dao.findSysPrvOrgAuthByPrimaryKey(id);

		if (dto == null)
			dto = new SysPrvOrgAuthDTO();

		return dto;

	}

	/**
	 * @title: insertSysPrvOrgAuth
	 * @author
	 * @description: 新增 组织授权表对象
	 * @date 2014-10-18 16:07:01
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	@Transactional(rollbackFor=Exception.class)
	public void insertSysPrvOrgAuth(String userId, String orgIds)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SysPrvOrgAuthDTO dto = new SysPrvOrgAuthDTO();
		dto.setUserId(Long.parseLong(userId));
		paramMap.put("dto", dto);
		if (userId != null && orgIds != null) {
			String[] orgs = orgIds.split(",");
			for (String org : orgs) {
				dto.setOrgId(Long.parseLong(org));
				dao.insertSysPrvOrgAuth(paramMap);
				//添加到数据权限资源池
				insertAuthPool(org,userId);
				
			}
		}

	}
	/**
	 * 添加到数据权限资源池
	 * @param roleId
	 * @param userId
	 * @throws Exception
	 */
	private void insertAuthPool(String orgId,String userId ) throws Exception{
		//添加本组织的所有的子组织
		List<Map> orgList=rDao.queryRecursiveByOrgId(orgId);
		for(Map orgMap:orgList)
		{
			SysPrvAuthPoolDTO dto = new SysPrvAuthPoolDTO();
			dto.setUserid(Long.parseLong(userId));
			dto.setOrgid(((BigDecimal)orgMap.get("ID")).longValue());
			dto.setDatesource("组织授权");
			dto.setDatesourceid(orgId);
			pService.insertSysPrvAuthPool(dto);
		}
		
	}
	
	/**
	 * @title: updateSysPrvOrgAuth
	 * @author
	 * @description: 修改 组织授权表对象
	 * @date 2014-10-18 16:07:01
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateSysPrvOrgAuth(SysPrvOrgAuthDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		SysPrvOrgAuthDTO oldDto = dao.findSysPrvOrgAuthByPrimaryKey(dto.getId()
				.toString());
		rService.updateOrDeleteOrgAuth(oldDto.getUserId(), oldDto.getOrgId()
				.toString(), "O");
		dao.updateSysPrvOrgAuth(paramMap);

	}

	/**
	 * @title: deleteSysPrvOrgAuthByID
	 * @author
	 * @description: 删除 组织授权表,按主键
	 * @date 2014-10-18 16:07:01
	 * @param paramMap
	 * @throws
	 */
	@SuppressWarnings("all")
	@Transactional(rollbackFor=Exception.class)
	public void deleteSysPrvOrgAuthByID(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		List<SysPrvOrgAuthDTO> dtoList = dao
				.searchSysPrvOrgAuthsByPrimaryKeys(paramMap);
		for (SysPrvOrgAuthDTO dto : dtoList) {
			//rService.updateOrDeleteOrgAuth(dto.getUserId(), dto.getOrgId()
			//		.toString(), "O");
			SysPrvAuthPoolDTO pooldto= new SysPrvAuthPoolDTO();
			pooldto.setDatesource("组织授权");
			pooldto.setDatesourceid(dto.getOrgId().toString());
			pooldto.setUserid(dto.getUserId());
			pService.deleteSysPrvAuthPoolByMap(pooldto);
		}	
		dao.deleteSysPrvOrgAuthByID(paramMap);
		
	}
	
	public String queryInfoByUserAndOrg(Map<String,Object> param){
		return dao.queryInfoByUserAndOrg(param);
	}

}
