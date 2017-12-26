package com.fintech.modules.platform.dataprv.sysprvauthresult.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.dataprv.sysprvauthresult.dao.SysPrvAuthResultDao;
import com.fintech.modules.platform.dataprv.sysprvauthresult.dto.SysPrvAuthResultDTO;
import com.fintech.modules.platform.dataprv.sysprvbizuser.dao.SysPrvBizUserDao;
import com.fintech.modules.platform.dataprv.sysprvorgauth.dao.SysPrvOrgAuthDao;
import com.fintech.modules.platform.dataprv.sysprvroleauth.dao.SysPrvRoleAuthDao;
import com.fintech.modules.platform.dataprv.sysprvusershare.dao.SysPrvUserShareDao;
import com.fintech.platform.api.org.OrgAPI;

/**
 * @classname: SysPrvAuthResultService
 * @description: 定义 数据权限授权结果表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.dataprv.sysprvauthresult.service.SysPrvAuthResultService")
public class SysPrvAuthResultService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SysPrvAuthResultDao dao;

	@Autowired
	private OrgAPI orgApi;

	@Autowired
	private SysPrvBizUserDao bDao;

	@Autowired
	private SysPrvRoleAuthDao rDao;

	@Autowired
	private SysPrvOrgAuthDao oDao;

	@Autowired
	private SysPrvUserShareDao uDao;

	/**
	 * @author
	 * @description: 分页查询 数据权限授权结果表列表
	 * @date 2014-10-18 16:06:51
	 * @param searchParams
	 *            条件
	 * @return
	 * @throws
	 */
	public List<SysPrvAuthResultDTO> searchSysPrvAuthResultByPaging(
			Map<String, Object> searchParams) throws Exception {
		List<SysPrvAuthResultDTO> dataList = dao
				.searchSysPrvAuthResultByPaging(searchParams);
		return dataList;
	}

	/**
	 * 当角色数据权限做修改时调用此方法。 当汇总表中存在此数据且数据的insert_from==dto的insert_from时做delete
	 * insert_from<>dto的insert_from时做更新操作
	 * 
	 * @param dto
	 */
	@SuppressWarnings("rawtypes")
	public void updateOrDeleteRoleAuth(List<Map> deleteList) {
		if (deleteList != null && deleteList.size() > 0) {
			List<Map> orgDeleteList = new ArrayList<Map>();
			List<Map> userDeleteList = new ArrayList<Map>();
			for (Map map : deleteList) {
				if ("user".equals(map.get("RTYPE"))) {
					userDeleteList.add(map);
				} else {
					orgDeleteList.add(map);
				}
			}
			if (orgDeleteList.size() > 0) {
				for (Map map : orgDeleteList) {
					if (map.get("USREID") != null && map.get("RID") != null)
						this.updateOrDeleteOrgAuth(
								Long.parseLong(map.get("USREID").toString()),
								map.get("RID").toString(), "Y");
				}
			}

			if (userDeleteList.size() > 0) {
				for (Map map : userDeleteList) {
					if (map.get("USREID") != null && map.get("RID") != null)
						this.updateOrDeleteUserAuth(
								Long.parseLong(map.get("USREID").toString()),
								Long.parseLong(map.get("RID").toString()), "X");
				}
			}
		}
	}

	/**
	 * 处理组织机构授权修改
	 */
	public void updateOrDeleteOrgAuth(long userIdTo, String orgId, String type) {
		List<String> userIds = orgApi.getUsersByOrgId(orgId);
		if (userIds != null && userIds.size() > 0) {
			List<SysPrvAuthResultDTO> deleteList = new ArrayList<SysPrvAuthResultDTO>();
			for (String str : userIds) {
				SysPrvAuthResultDTO dto = new SysPrvAuthResultDTO();
				dto.setUserIdFrom(Long.parseLong(str));
				dto.setUserIdTo(userIdTo);
				dto.setInsertFrom(type);
				deleteList.add(dto);
			}
			List<SysPrvAuthResultDTO> queryList = dao
					.findSysPrvAuthResultByUser(deleteList);
			List<SysPrvAuthResultDTO> updateList = removeDeleteData(deleteList,
					queryList);
			if (updateList.size() > 0) {
				for (SysPrvAuthResultDTO dto : updateList)
					dao.updateSysPrvAuthResultType(dto);
			}
			if (deleteList.size() > 0) {
				dao.deleteSysPrvAuthResultByUserBatch(deleteList);
			}
		}
	}

	/**
	 * 处理要批量删除的数据，将插入来源的多个的修改组成updateList
	 * 
	 * @param deleteList
	 * @param queryList
	 * @return
	 */
	private List<SysPrvAuthResultDTO> removeDeleteData(
			List<SysPrvAuthResultDTO> deleteList,
			List<SysPrvAuthResultDTO> queryList) {
		List<SysPrvAuthResultDTO> updateList = new ArrayList<SysPrvAuthResultDTO>();
		if (queryList != null && queryList.size() > 0) {
			for (SysPrvAuthResultDTO dto : queryList) {
				for (SysPrvAuthResultDTO dto1 : deleteList) {
					if (!dto1.getInsertFrom().equals(dto.getInsertFrom())) {
						dto1.setInsertFrom(removeType(dto.getInsertFrom(),
								dto1.getInsertFrom()));
						updateList.add(dto1);
						deleteList.remove(dto1);
						break;
					}
				}
			}
		}
		return updateList;
	}

	/**
	 * 处理用户修改删除权限的方法
	 * 
	 * @param userIdTo
	 * @param userIdFrom
	 * @param type
	 */
	public void updateOrDeleteUserAuth(long userIdTo, long userIdFrom,
			String type) {
		List<SysPrvAuthResultDTO> paramList = new ArrayList<SysPrvAuthResultDTO>();
		SysPrvAuthResultDTO dto1 = new SysPrvAuthResultDTO();
		dto1.setInsertFrom(type);
		dto1.setUserIdFrom(userIdFrom);
		dto1.setUserIdTo(userIdTo);
		paramList.add(dto1);
		List<SysPrvAuthResultDTO> queryList = dao
				.findSysPrvAuthResultByUser(paramList);
		if (queryList != null && queryList.size() > 0) {
			SysPrvAuthResultDTO dto = queryList.get(0);
			String dtoType = dto.getInsertFrom();
			if (dtoType.equals(type)) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("userIdFrom", userIdFrom);
				paramMap.put("userIdTo", userIdTo);
				dao.deleteSysPrvAuthResultByUser(paramMap);
			} else {
				dto.setInsertFrom(removeType(dtoType, type));
				dao.updateSysPrvAuthResultType(dto);
			}
		}
	}

	private String removeType(String dtoType, String type) {
		if (dtoType.indexOf("," + type) >= 0)
			return dtoType.replace("," + type, "");
		else
			return dtoType.replace(type + ",", "");
	}

	/**
	 * 定时抽取数据放入sysAuthResult表中
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertSysAuthResult(String sType) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sType", sType);
		if (sType == null || sType.length() == 0) {
			dao.deleteAllSysPrvAuthResult();
		}
		List<Map> userMapping = dao.searchUserPrvMappingByAction(paramMap);
		List<Map> orgMapping = dao.searchOrgPrvMappingByAction(paramMap);

		List<Map> userMappingTotal = new ArrayList<Map>();
		if (userMapping != null && userMapping.size() > 0) {
			userMappingTotal.addAll(userMapping);
		}

		if (orgMapping != null && orgMapping.size() > 0) {
			userMappingTotal.addAll(orgMapping2UserMapping(orgMapping));
		}

		// 合并排序的效率要再考虑
		Collections.sort(userMappingTotal, new MyComparator());

		List<SysPrvAuthResultDTO> insertList = prcessUserDataPrv2List(userMappingTotal);
		if (insertList.size() > 0) {
			List<SysPrvAuthResultDTO> queryList = dao
					.findSysPrvAuthResultByUser(insertList);
			if (queryList != null && queryList.size() > 0) {
				List<SysPrvAuthResultDTO> updateList = this.prcessUpdateData(
						insertList, queryList);
				if (updateList.size() > 0) {
					for (SysPrvAuthResultDTO dto : updateList) {
						dao.updateSysPrvAuthResultType(dto);
					}
				}
			}
			if (insertList.size() > 0)
				dao.insertSysPrvAuthResultBatch(insertList);
		}

		// bDao.updateSysPrvBizUserSyn();
		rDao.updateSysPrvRoleAuthSyn();
		uDao.updateSysPrvUserShareSyn();
		oDao.updateSysPrvOrgAuthSyn();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> orgMapping2UserMapping(List<Map> orgMapping) {
		List<Map> userMappingTotal = new ArrayList<Map>();
		for (Map map : orgMapping) {
			List<String> userIds = orgApi.getUsersByOrgId(map.get("ORGID")
					.toString());
			if (userIds != null && userIds.size() > 0) {
				for (String userId : userIds) {
					if (userId.equals(map.get("TOUSER").toString()))
						continue;
					Map uMap = new HashMap();
					uMap.put("TOUSER", map.get("TOUSER"));
					uMap.put("FROMUSER", userId);
					uMap.put("ITYPE", map.get("ITYPE"));
					userMappingTotal.add(uMap);
				}
			}
		}
		return userMappingTotal;
	}

	/**
	 * Map比较的类
	 * 
	 * @author
	 * 
	 */
	class MyComparator implements Comparator {

		@SuppressWarnings("rawtypes")
		@Override
		public int compare(Object o1, Object o2) {
			Map map1 = (Map) o1;
			Map map2 = (Map) o2;
			long lon1 = Long.parseLong(map1.get("FROMUSER").toString());
			long lon2 = Long.parseLong(map2.get("FROMUSER").toString());
			int cr = 0;
			int a = (int) (lon2 - lon1);
			if (a != 0) {
				cr = (a > 0) ? 3 : -1;
			} else {
				long lon11 = Long.parseLong(map1.get("TOUSER").toString());
				long lon22 = Long.parseLong(map2.get("TOUSER").toString());
				a = (int) (lon22 - lon11);
				if (a != 0) {
					cr = (a > 0) ? 2 : -2;
				} else {
					a = map1.get("ITYPE").toString()
							.compareTo(map2.get("ITYPE").toString());
					if (a != 0)
						cr = (a > 0) ? 1 : -1;
				}
			}
			return cr;
		}
	}

	/**
	 * 处理要插入的List，将数据库已经存在的去除。
	 * 
	 * @param insertData
	 * @param queryData
	 * @return
	 */
	private List<SysPrvAuthResultDTO> prcessUpdateData(
			List<SysPrvAuthResultDTO> insertList,
			List<SysPrvAuthResultDTO> queryList) {
		List<SysPrvAuthResultDTO> updateList = new ArrayList<SysPrvAuthResultDTO>();
		for (SysPrvAuthResultDTO dto : queryList) {
			for (SysPrvAuthResultDTO dto1 : insertList) {
				if (dto.getUserIdFrom() == dto1.getUserIdFrom()
						&& dto.getUserIdTo() == dto.getUserIdTo()) {
					if (dto1.getInsertFrom().indexOf(dto.getInsertFrom()) < 0) {
						dto1.setInsertFrom(dto1.getInsertFrom() + ","
								+ dto.getInsertFrom());
						updateList.add(dto1);
					}
					break;
				}
			}
		}
		insertList.removeAll(updateList);
		return updateList;
	}

	/**
	 * 处理查询四个表(用户-->用户，用户-->机构，用户-->角色，数据业务表)获得的数据， 将formUser和toUser相同的数据合并，
	 * iType变为“O,S，R”结构
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<SysPrvAuthResultDTO> prcessUserDataPrv2List(
			List<Map> userDataPrv) {
		List<SysPrvAuthResultDTO> resultList = new ArrayList<SysPrvAuthResultDTO>();
		List<Map> tmpList = new ArrayList<Map>();
		if (userDataPrv != null && userDataPrv.size() > 2) {
			for (int i = 0; i < userDataPrv.size() - 1; i++) {
				Map map1 = userDataPrv.get(i);
				Map map2 = userDataPrv.get(i + 1);
				if (Long.parseLong(map1.get("TOUSER").toString()) == Long
						.parseLong(map2.get("TOUSER").toString())) {
					if (Long.parseLong(map1.get("FROMUSER").toString()) == Long
							.parseLong(map2.get("FROMUSER").toString())) {
						if (!map1.get("ITYPE").toString()
								.equals(map2.get("ITYPE").toString())
								&& map1.get("ITYPE").toString()
										.indexOf(map2.get("ITYPE").toString()) < 0)
							map2.put("ITYPE", map1.get("ITYPE").toString()
									+ "," + map2.get("ITYPE").toString());
					} else {
						tmpList.add(map1);
					}
				} else {
					tmpList.add(map1);
				}
			}
			tmpList.add(userDataPrv.get(userDataPrv.size() - 1));
		} else {
			tmpList = userDataPrv;
		}
		for (Map map : tmpList) {
			if (!map.get("FROMUSER").equals(map.get("TOUSER")))
				resultList.add(map2SysPrvAuthResultDTO(map));
		}

		// System.out.print(resultList);
		return resultList;
	}

	private SysPrvAuthResultDTO map2SysPrvAuthResultDTO(Map map) {
		SysPrvAuthResultDTO dto = new SysPrvAuthResultDTO();
		dto.setUserIdFrom(Long.parseLong(map.get("FROMUSER").toString()));
		dto.setUserIdTo(Long.parseLong(map.get("TOUSER").toString()));
		dto.setInsertFrom(map.get("ITYPE").toString());
		// System.out.println(map.get("ITYPE").toString());
		// System.out.println(dto.getUserIdFrom() + "  " + dto.getUserIdTo()
		// + "  " + dto.getInsertFrom());
		return dto;
	}
}
