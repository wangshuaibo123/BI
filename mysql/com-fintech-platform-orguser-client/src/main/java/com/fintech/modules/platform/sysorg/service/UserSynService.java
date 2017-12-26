package com.fintech.modules.platform.sysorg.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.modules.platform.sysorg.dao.SysOrgSynDao;
import com.fintech.modules.platform.sysorg.dao.SysOrgUserSynDao;
import com.fintech.modules.platform.sysorg.dao.SysPositionSynDao;
import com.fintech.modules.platform.sysorg.dao.SysUserSynDao;
import com.fintech.modules.platform.sysorg.dto.SysOrgSynDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserSynDTO;
import com.fintech.modules.platform.sysorg.dto.SysPositionSynDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserSynDTO;
import com.fintech.platform.tools.common.DateUtil;

@SuppressWarnings("all")
@Service
@Transactional
public class UserSynService {
	private static Logger logger = LoggerFactory.getLogger(UserSynService.class);
	/***同步数据类型***/
	public enum SYNDATATYPE{
		/**用户数据类型*/
		USER,
		/**机构数据类型*/
		ORG,
		/**岗位数据类型*/
		POSITION,
		/**用户机构岗位关系数据类型*/
		ORG_POSITION_RELATION
	}
	
	@Autowired
	private SysUserSynDao sysUserSynDao;
	@Autowired
	private SysOrgUserSynDao sysOrgUserSynDao;
	@Autowired
	private SysOrgSynDao sysOrgSynDao;
	@Autowired
	private SysPositionSynDao sysPositionSynDao;
	
	
	/**
	 * 新增用户
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long insertSysUserSyn(SysUserSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		int count = sysUserSynDao.insertSysUserSyn(paramMap);
		SysUserSynDTO resultDto = (SysUserSynDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
	 * 批量新增用户,只要一个失败,则回滚
	 * @param dto
	 */
	public void insertSysUserSyns(List<SysUserSynDTO> dtos) {
		for (SysUserSynDTO dto : dtos) {
			try {
				//插入数据库前,先判断数据库是否有此条数据,保障不会出现出错数据
				HashMap<String, Object> searchParams = new HashMap<String, Object>();
				dto.setId(null);//id清空,不根据此条件查询
				searchParams.put("dto", dto);
				List<SysUserSynDTO> searchSysUserSyn = sysUserSynDao.searchSysUserSyn(searchParams);
				logger.debug("before insertSysUserSyns,find the data from local, result,size:"+searchSysUserSyn.size());
				//在数据库中查找到需要插入的数据,不插入
				if(searchSysUserSyn.size() == 0){
					insertSysUserSyn(dto);
				}
				
			} catch (Exception e) {
				logger.info("同步数据,新增用户失败:",e);
			}
		}
	}
	/**
	 * 新增机构
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long insertSysOrgSyn(SysOrgSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		int count = sysOrgSynDao.insertSysOrgSyn(paramMap);
		SysOrgSynDTO resultDto = (SysOrgSynDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
	 * 批量新增机构,只要一个失败,则回滚
	 * @param dto
	 */
	public void insertSysOrgSyns(List<SysOrgSynDTO> dtos) {
		for (SysOrgSynDTO dto : dtos) {
			try {
				//插入数据库前,先判断数据库是否有此条数据,保障不会出现出错数据
				HashMap<String, Object> searchParams = new HashMap<String, Object>();
				dto.setId(null);//id清空,不根据此条件查询
				searchParams.put("dto", dto);
				List<SysOrgSynDTO> searchSysOrgSyn = sysOrgSynDao.searchSysOrgSyn(searchParams);
				logger.debug("before insertSysOrgSyns,find the data from local, result,size:"+searchSysOrgSyn.size());
				//在数据库中查找到需要插入的数据,不插入
				if(searchSysOrgSyn.size() == 0){
					insertSysOrgSyn(dto);
				}
			} catch (Exception e) {
				logger.info("同步数据,新增机构失败:",e);
			}
		}
	}
	/**
	 * 新增岗位
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long insertSysPositionSyn(SysPositionSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		int count = sysPositionSynDao.insertSysPositionSyn(paramMap);
		SysPositionSynDTO resultDto = (SysPositionSynDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
	 * 批量新增岗位,只要一个失败,则回滚
	 * @param dto
	 */
	public void insertSysPositionSyns(List<SysPositionSynDTO> dtos) {
		for (SysPositionSynDTO dto : dtos) {
			try {
				//插入数据库前,先判断数据库是否有此条数据,保障不会出现出错数据
				HashMap<String, Object> searchParams = new HashMap<String, Object>();
				dto.setId(null);//id清空,不根据此条件查询
				searchParams.put("dto", dto);
				List<SysPositionSynDTO> searchSysPositionSyn = sysPositionSynDao.searchSysPositionSyn(searchParams);
				logger.debug("before insertSysPositionSyns,find the data from local, result,size:"+searchSysPositionSyn.size());
				//在数据库中查找到需要插入的数据,不插入
				if(searchSysPositionSyn.size() == 0){
					insertSysPositionSyn(dto);
				}
			} catch (Exception e) {
				logger.info("同步数据,新增岗位失败:",e);
			}
		}
	}
	/**
	 * 新增用户机构岗位关系
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public Long insertSysOrgUserSyn(SysOrgUserSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		int count = sysOrgUserSynDao.insertSysOrgUserSyn(paramMap);
		SysOrgUserSynDTO resultDto = (SysOrgUserSynDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
	 * 批量新增用户机构岗位关系,只要一个失败,则回滚
	 * @param dto
	 */
	public void insertSysOrgUserSyns(List<SysOrgUserSynDTO> dtos) {
		for (SysOrgUserSynDTO dto : dtos) {
			try {
				//插入数据库前,先判断数据库是否有此条数据,保障不会出现出错数据
				HashMap<String, Object> searchParams = new HashMap<String, Object>();
				dto.setId(null);//id清空,不根据此条件查询
				searchParams.put("dto", dto);
				List<SysOrgUserSynDTO> searchSysOrgUserSyn = sysOrgUserSynDao.searchSysOrgUserSyn(searchParams);
				logger.debug("before insertSysOrgUserSyns,find the data from local, result,size:"+searchSysOrgUserSyn.size());
				//在数据库中查找到需要插入的数据,不插入
				if(searchSysOrgUserSyn.size() == 0){
					insertSysOrgUserSyn(dto);
				}
				
			} catch (Exception e) {
				logger.info("同步数据,新增用户机构岗位关系失败:",e);
			}
		}
	}
	/**
	 * 查找最后同步时间,实时查询无缓存
	 * @param paramMap
	 * @param datatype
	 * @return 未找到返回 null
	 */
	public String obtainLastSynTime(Map<String, Object> paramMap,Enum datatype){
		Timestamp time = null;
		
		if(datatype.equals(SYNDATATYPE.ORG)){
			SysOrgSynDTO searchSysOrg = sysOrgSynDao.searchSysOrgSynByMaxCreateDate(paramMap);
			time = searchSysOrg != null ? searchSysOrg.getCreateDate() : null;
		}
		if(datatype.equals(SYNDATATYPE.USER)){
			SysUserSynDTO searchSysUser = sysUserSynDao.searchSysUserSynByMaxCreateDate(paramMap);
			time = searchSysUser != null ? searchSysUser.getCreateDate() : null;
		}
		if(datatype.equals(SYNDATATYPE.POSITION)){
			SysPositionSynDTO searchSysPosition = sysPositionSynDao.searchSysPositionSynByMaxCreateDate(paramMap);
			time = searchSysPosition != null ? searchSysPosition.getCreateDate() : null;
		}
		if(datatype.equals(SYNDATATYPE.ORG_POSITION_RELATION)){
			SysOrgUserSynDTO searchSysOrgUser = sysOrgUserSynDao.searchSysOrgUserSynByMaxCreateDate(paramMap);
			time = searchSysOrgUser != null ? searchSysOrgUser.getCreateDate() : null;
		}
		
		if(time != null){
			//保留三位精度,若同步数据出现重复,则需要添加精度或是程序判断重复
			return DateUtil.format(time, "yyyy-MM-dd HH:mm:ss.SSS");
		}
		
		return null;
	}
	
	
}
