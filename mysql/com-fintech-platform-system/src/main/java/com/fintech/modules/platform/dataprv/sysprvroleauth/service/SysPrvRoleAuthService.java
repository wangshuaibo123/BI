package com.fintech.modules.platform.dataprv.sysprvroleauth.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.modules.platform.dataprv.sysprvauthresult.service.SysPrvAuthResultService;
import com.fintech.modules.platform.dataprv.sysprvrole.dao.SysPrvRoleDao;
import com.fintech.modules.platform.dataprv.sysprvroleauth.dao.SysPrvRoleAuthDao;
import com.fintech.modules.platform.dataprv.sysprvroleauth.dto.SysPrvRoleAuthDTO;
import com.fintech.modules.platform.sysprvauthpool.dto.SysPrvAuthPoolDTO;
import com.fintech.modules.platform.sysprvauthpool.service.SysPrvAuthPoolService;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysPrvRoleAuthService
 * @description: 定义 数据权限角色授权表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.dataprv.sysprvroleauth.service.SysPrvRoleAuthService")
public class SysPrvRoleAuthService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SysPrvRoleAuthDao dao;

	@Autowired
	private SysPrvRoleDao rDao;

	@Autowired
	private SysPrvAuthResultService rService;
	
	@Autowired
	private SysPrvAuthPoolService pService;
	

	/**
	 * @author
	 * @description: 分页查询 数据权限角色授权表列表
	 * @date 2014-10-18 16:07:22
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvRoleAuthDTO> searchSysPrvRoleAuthByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvRoleAuthDTO> dataList = dao
				.searchSysPrvRoleAuthByPaging(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 按条件查询数据权限角色授权表列表
	 * @date 2014-10-18 16:07:22
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvRoleAuthDTO> searchSysPrvRoleAuth(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvRoleAuthDTO> dataList = dao
				.searchSysPrvRoleAuth(searchParams);
		return dataList;
	}

	/**
	 * @author
	 * @description: 查询数据权限角色授权表对象
	 * @date 2014-10-18 16:07:22
	 * @param id
	 * @return
	 * @throws
	 */
	public SysPrvRoleAuthDTO querySysPrvRoleAuthByPrimaryKey(String id)
			throws Exception {

		SysPrvRoleAuthDTO dto = dao.findSysPrvRoleAuthByPrimaryKey(id);

		if (dto == null)
			dto = new SysPrvRoleAuthDTO();

		return dto;

	}

	/**
	 * @title: insertSysPrvRoleAuth
	 * @author
	 * @description: 新增 数据权限角色授权表对象
	 * @date 2014-10-18 16:07:22
	 * @param dto
	 * @return
	 * @throws
	 */
	@SuppressWarnings("all")
	@Transactional(rollbackFor=Exception.class)
	public void insertSysPrvRoleAuth(String userIds,String roleIds ) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SysPrvRoleAuthDTO dto = new SysPrvRoleAuthDTO();
		paramMap.put("dto", dto);
		if(userIds!=null&&roleIds!=null){
			if(userIds.indexOf(",")>0){
				dto.setRoleId(Long.parseLong(roleIds));
				String[] users = userIds.split(",");
				for(String str:users){
					dto.setUserId(Long.parseLong(str));
					dao.insertSysPrvRoleAuth(paramMap);
				}
			}else{
				dto.setRoleId(Long.parseLong(roleIds));
				dto.setUserId(Long.parseLong(userIds));
				dao.insertSysPrvRoleAuth(paramMap);
				//添加到权限池
				insertAuthPool(roleIds,userIds);	
			}
			
					
		}
	}
	
	/**
	 * 
	 * 添加到数据权限池
	 * @param roleId
	 */
	public void insertAuthPool(String roleId,String userId ) throws Exception{
		List<Map> list=rDao.queryRoleResourceByRoleId(roleId);
		for(Map map : list)
		{
			if(map!=null && !"".equals(map))
			{	
				if("user".equals((String)map.get("RESOURCETYPE")))
				{	
					SysPrvAuthPoolDTO dto = new SysPrvAuthPoolDTO();
					dto.setUserid(Long.parseLong(userId));
					dto.setOwnerid(((BigDecimal)map.get("RESOURCEID")).longValue());
					dto.setDatesource("角色授权");
					dto.setDatesourceid(roleId);
					pService.insertSysPrvAuthPool(dto);
				}else if("org".equals((String)map.get("RESOURCETYPE")))
				{
					//添加本组织的所有的子组织
					List<Map> orgList=rDao.queryRecursiveByOrgId(((BigDecimal)map.get("RESOURCEID")).toString());
					for(Map orgMap:orgList)
					{
						SysPrvAuthPoolDTO dto = new SysPrvAuthPoolDTO();
						dto.setUserid(Long.parseLong(userId));
						dto.setOrgid(((BigDecimal)orgMap.get("ID")).longValue());
						dto.setDatesource("角色授权");
						dto.setDatesourceid(roleId);
						pService.insertSysPrvAuthPool(dto);
					}
					
				}
			}
			
		}
	}
		
	/**
	 * @title: updateSysPrvRoleAuth
	 * @author
	 * @description: 修改 数据权限角色授权表对象
	 * @date 2014-10-18 16:07:22
	 * @param paramMap
	 * @return
	 * @throws
	 */
	public void updateSysPrvRoleAuth(SysPrvRoleAuthDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("authIds", dto.getId());
		List<Map> deleteList = rDao.queryUserRoleResourceListByIds(queryMap);
		rService.updateOrDeleteRoleAuth(deleteList);
		dao.updateSysPrvRoleAuth(paramMap);
	}

	/**
	 * @title: deleteSysPrvRoleAuthByID
	 * @author
	 * @description: 删除 数据权限角色授权表,按主键
	 * @date 2014-10-18 16:07:22
	 * @param paramMap
	 * @throws
	 */
	@SuppressWarnings("all")
	@Transactional(rollbackFor=Exception.class)
	public void deleteSysPrvRoleAuthByID(BaseDTO baseDto, String ids)
			throws Exception {
		if (StringUtils.isEmpty(ids))
			throw new Exception("删除失败！传入的参数主键为null");
	//	Map<String, Object> queryMap = new HashMap<String, Object>();
	//	queryMap.put("authIds", ids);
	//	List<Map> deleteList = rDao.queryUserRoleResourceListByIds(queryMap);
	//	rService.updateOrDeleteRoleAuth(deleteList);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		List<SysPrvRoleAuthDTO> list=dao.findSysPrvRoleAuthByPrimaryKeys(paramMap);
		for(SysPrvRoleAuthDTO s:list)
		{
			SysPrvAuthPoolDTO pooldto= new SysPrvAuthPoolDTO();
			pooldto.setDatesource("角色授权");
			pooldto.setDatesourceid(s.getRoleId().toString());
			pooldto.setUserid(s.getUserId());
			pService.deleteSysPrvAuthPoolByMap(pooldto);
		}
		dao.deleteSysPrvRoleAuthByID(paramMap);
	}
	
	
	//唯一性验证
	public String queryRoleAuthByUser(Map<String,Object> params){
		return dao.queryRoleAuthByUser(params);
	}
	
}
