package com.fintech.modules.platform.sysorg.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.modules.platform.sysorg.dao.SysOrgDao;
import com.fintech.modules.platform.sysorg.dao.SysOrgSynDao;
import com.fintech.modules.platform.sysorg.dao.SysOrgUserDao;
import com.fintech.modules.platform.sysorg.dao.SysOrgUserSynDao;
import com.fintech.modules.platform.sysorg.dao.SysPositionDao;
import com.fintech.modules.platform.sysorg.dao.SysPositionSynDao;
import com.fintech.modules.platform.sysorg.dao.SysSynDao;
import com.fintech.modules.platform.sysorg.dao.SysUserDao;
import com.fintech.modules.platform.sysorg.dao.SysUserSynDao;
import com.fintech.modules.platform.sysorg.dto.SysOrgDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgSynDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserSynDTO;
import com.fintech.modules.platform.sysorg.dto.SysPositionDTO;
import com.fintech.modules.platform.sysorg.dto.SysPositionSynDTO;
import com.fintech.modules.platform.sysorg.dto.SysSynDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserSynDTO;
/**<pre>
 * 类名中文描述:
 *
 * 基本操作功能:
 *
 * Module ID  : com-pt-platform-orguser-client 
 *
 * Create Date：2015年1月26日 下午7:23:27
 * 
 * 
 * @since 0.1
 * @version: 0.1
 * @author 
 *
 * Change History Log
 * --------------------------------------------------------------------------------------------------------------
 * Date	      | Version | Author	   | Description			              
 * --------------------------------------------------------------------------------------------------------------
 * 2015年1月26日 | 0.1     | cyy| CREATE THE JAVA FILE: SynClientService.java.
 * --------------------------------------------------------------------------------------------------------------
 *
 * --------------------------------------------------------------------------------------------------------------
 *
 * </pre>
 */
@SuppressWarnings("all")
@Service
@Transactional
public class SynClientService {
	private static Logger logger = LoggerFactory.getLogger(SynClientService.class);
	
	@Autowired
	SysSynDao sysSynDao;
	
	@Autowired
	SysPositionSynDao  sysPositionSynDao;
	
	@Autowired
	SysUserSynDao sysUserSynDao;
	
	@Autowired
	SysOrgUserSynDao sysOrgUserSynDao;
	
	@Autowired
	SysOrgSynDao sysOrgSynDao;
	
	@Autowired
	SysUserDao sysUserDao;
	
	@Autowired
	SysOrgDao sysOrgDao;
	
	@Autowired 
	SysPositionDao sysPositionDao;
	
	@Autowired 
	SysOrgUserDao sysOrgUserDao;
	
	
	
	/**
	 * 查询需要同步的数据，不分页
	 * @return
	 * @throws Exception
	 */
	public List<SysSynDTO> searchAutoSysSyn()throws Exception
	{
		return sysSynDao.searchAutoSysSyn();
	}
	

	/**Description: 查询需要同步的数据，需要分页
	 * Create Date: 2015年1月26日下午7:23:32<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public List<SysSynDTO> searchSysSynByPaging(Map<String, Object> searchParams) throws Exception {
		List<SysSynDTO> dataList = sysSynDao.searchSysSynByPaging(searchParams);
		for (SysSynDTO sysSynDTO : dataList) {
			List<String> changeStrings  = new ArrayList<String>();
			
			final SysUserSynDTO sysUserSyn = new SysUserSynDTO();
			sysUserSyn.setVersion(sysSynDTO.getVersion());
			List<SysUserSynDTO> sysUserSynDTOs = sysUserSynDao.searchSysUserSyn(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("dto", sysUserSyn );
				}
			});
			if (sysUserSynDTOs!=null && sysUserSynDTOs.size()>0) {
				
				changeStrings.add( "用户变更:"+"("+sysUserSynDTOs.size() + ")" );
				sysSynDTO.setUserSize(sysUserSynDTOs.size());
			}
			
			final SysOrgUserSynDTO sysOrgUserSynDTO = new SysOrgUserSynDTO();
			sysOrgUserSynDTO.setVersion(sysSynDTO.getVersion());
			List<SysOrgUserSynDTO> sysOrgUserSynDTOs = sysOrgUserSynDao.searchSysOrgUserSyn(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("dto", sysOrgUserSynDTO );
				}
			});
			if (sysOrgUserSynDTOs!=null && sysOrgUserSynDTOs.size()>0) {
				changeStrings.add( "归属机构,任职变更:"+"("+sysOrgUserSynDTOs.size() + ")" );
				sysSynDTO.setOrguserSize(sysOrgUserSynDTOs.size());
			}
			
			final SysOrgSynDTO sysOrgSynDTO = new SysOrgSynDTO();
			sysOrgSynDTO.setVersion(sysSynDTO.getVersion());
			List<SysOrgSynDTO> sysOrgSynDTOs = sysOrgSynDao.searchSysOrgSyn(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("dto", sysOrgSynDTO );
				}
			});
			
			if (sysOrgSynDTOs!=null && sysOrgSynDTOs.size()>0) {
				changeStrings.add( "机构变更:"+"("+sysOrgSynDTOs.size() + ")" );
				sysSynDTO.setOrgSize(sysOrgSynDTOs.size());
			}
			
			final SysPositionSynDTO sysPositionSynDTO = new SysPositionSynDTO();
			sysPositionSynDTO.setVersion(sysSynDTO.getVersion());
			List<SysPositionSynDTO> sysPositionSynDTOs= sysPositionSynDao.searchSysPositionSyn(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("dto", sysPositionSynDTO );
				}
			});
			if (sysPositionSynDTOs!=null && sysPositionSynDTOs.size()>0) {
				changeStrings.add( "岗位变更:"+"("+sysPositionSynDTOs.size() + ")" );
				sysSynDTO.setPositionSize(sysPositionSynDTOs.size());
			}
			sysSynDTO.setRowMsg(StringUtils.join(changeStrings, ","));
		}
		return dataList;
	}

	/**Description: 更新syn
	 * Create Date: 2015年1月30日下午3:26:06<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param ids
	 */
	public void updateSysDataSynAndCheck(final String versions) throws Exception{
		final String[] versionArray  = versions.split(",");
		List<SysSynDTO> sysSynDTOs = sysSynDao.searchSysSyn(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("rowSize", versionArray.length );
				}
		});
		//校验version
		boolean dataChoose = true;
		if(sysSynDTOs.size() != versionArray.length){
			dataChoose = false;
		}
		for(SysSynDTO sysSynDTO : sysSynDTOs){
			if(!versions.contains(sysSynDTO.getVersion().toString())){
				dataChoose = false;
			}
		}
		
		if(!dataChoose){
			throw new Exception("选取的数据不合法！");
		}
		//校验通过开始插入数据
		updateSysDataSyn( versions );
	}
	
	
	/**Description: 实际插入数据的方法
	 * Create Date: 2015年2月13日下午3:56:41<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param versions
	 * @throws Exception
	 */
	public void updateSysDataSyn( final String versions ) throws Exception{
		for(String versionStr : versions.split(",")){
			
			Long version =  Long.parseLong(versionStr);
			
			//机构
			final SysOrgSynDTO orgSynDTO = new SysOrgSynDTO();
			orgSynDTO.setVersion(version);
			List<SysOrgSynDTO> sysOrgSynDTOs = sysOrgSynDao.searchSysOrgSyn(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("dto", orgSynDTO);
				}
			});
			if(sysOrgSynDTOs!=null && sysOrgSynDTOs.size()>0){
				for(SysOrgSynDTO sysOrgSynDTO  :sysOrgSynDTOs){
					SysOrgDTO sysOrgDTO = sysOrgDao.findSysOrgByPrimaryKey(sysOrgSynDTO.getOrgId().toString());
					final SysOrgDTO dest = new SysOrgDTO();
					if(sysOrgSynDTO.getEffectiveDate()==null){
						sysOrgSynDTO.setEffectiveDate(new Date());
					}
					if(sysOrgSynDTO.getCreateDate()==null){
						sysOrgSynDTO.setCreateDate( new java.sql.Timestamp(new Date().getTime()));
					}
					if(sysOrgSynDTO.getCreateTime()==null){
						sysOrgSynDTO.setCreateTime(new Date());
					}
					BeanUtils.copyProperties(dest, sysOrgSynDTO);
					dest.setId(sysOrgSynDTO.getOrgId());
					dest.setCreateTime(new Date());
					if(sysOrgDTO!=null && sysOrgDTO.getId()!=null ){
						sysOrgDao.updateSysOrg(new HashMap<String, Object>() {
							private static final long serialVersionUID = 1L;
							{
								put("dto", dest);
							}
						});
					} else{
						sysOrgDao.insertSysOrg(new HashMap<String, Object>() {
							private static final long serialVersionUID = 1L;
							{
								put("dto", dest);
							}
						});
					}
				}
			}
			
			//岗位
			final SysPositionSynDTO positionSynDTO = new SysPositionSynDTO();
			positionSynDTO.setVersion(version);
			List<SysPositionSynDTO> sysPositionSynDTOs = sysPositionSynDao.searchSysPositionSyn(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("dto", positionSynDTO);
				}
			});
			if( sysPositionSynDTOs!=null && sysPositionSynDTOs.size()>0 ){
				for(SysPositionSynDTO sysPositionSynDTO :sysPositionSynDTOs){
					SysPositionDTO sysPositionDTO = sysPositionDao.findSysPositionByPrimaryKey(sysPositionSynDTO.getPositionId().toString());
					final SysPositionDTO dest = new SysPositionDTO();
					if(sysPositionSynDTO.getEffectiveDate()==null){
						sysPositionSynDTO.setEffectiveDate(new Date());
					}
					if(sysPositionSynDTO.getCreateDate() ==null){
						sysPositionSynDTO.setCreateDate(new Timestamp(new Date().getTime()));
					}
					BeanUtils.copyProperties(dest, sysPositionSynDTO);
					dest.setId(sysPositionSynDTO.getPositionId());
					if(sysPositionDTO!=null && sysPositionDTO.getId()!=null ){
						sysPositionDao.updateSysPosition(new HashMap<String, Object>() {
							private static final long serialVersionUID = 1L;
							{
								put("dto", dest);
							}
						});
					} else{
						sysPositionDao.insertSysPosition(new HashMap<String, Object>() {
							private static final long serialVersionUID = 1L;
							{
								put("dto", dest);
							}
						});
					}
				}
			}
			
			//用户
			final SysUserSynDTO userSynDTO = new SysUserSynDTO();
			userSynDTO.setVersion(version);
			List<SysUserSynDTO> sysUserSynDTOs = sysUserSynDao.searchSysUserSyn(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("dto", userSynDTO);
				}
			});
			if( sysUserSynDTOs!=null && sysUserSynDTOs.size()>0 ){
				for(SysUserSynDTO sysUserSynDTO :sysUserSynDTOs){
					SysUserDTO sysUserDTO = sysUserDao.findSysUserByPrimaryKey(sysUserSynDTO.getUserId().toString());
					final SysUserDTO dest = new SysUserDTO();
					Date ed = sysUserSynDTO.getEntryDate();
					Date qd = sysUserSynDTO.getQuitDate();
					Date wd = sysUserSynDTO.getWorkDate();
					Date nj = sysUserSynDTO.getNjqsrq();
					
					if(sysUserSynDTO.getCreateDate()==null){
						sysUserSynDTO.setCreateDate(new Timestamp(new Date().getTime()));
					}
					if(sysUserSynDTO.getEntryDate()==null){
						sysUserSynDTO.setEntryDate(new Date());
					}
					if(sysUserSynDTO.getQuitDate()==null){
						sysUserSynDTO.setQuitDate(new Date());
					}
					if(sysUserSynDTO.getWorkDate()==null){
						sysUserSynDTO.setWorkDate(new Date());
					}
					if(sysUserSynDTO.getNjqsrq()==null){
						sysUserSynDTO.setNjqsrq(new Date());
					}
					
					BeanUtils.copyProperties(dest, sysUserSynDTO);
					dest.setId(sysUserSynDTO.getUserId());
					dest.setQuitDate(qd);
					dest.setWorkDate(wd);
					dest.setEntryDate(ed);
					dest.setNjqsrq(nj);
					if(sysUserDTO!=null && sysUserDTO.getId()!=null ){
						sysUserDao.updateSysUser(new HashMap<String, Object>() {
							private static final long serialVersionUID = 1L;
							{
								put("dto", dest);
							}
						});
					} else{
						sysUserDao.insertSysUser(new HashMap<String, Object>() {
							private static final long serialVersionUID = 1L;
							{
								put("dto", dest);
							}
						});
					}
				}
			}
			
			//归属机构以及任职
			final SysOrgUserSynDTO orgUserSynDTO = new SysOrgUserSynDTO();
			orgUserSynDTO.setVersion(version);
			List<SysOrgUserSynDTO> sysOrgUserSynDTOs = sysOrgUserSynDao.searchSysOrgUserSyn(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("dto", orgUserSynDTO);
				}
			});
			String userIds = "";
			if( sysOrgUserSynDTOs!=null && sysOrgUserSynDTOs.size()>0 ){
				for(final SysOrgUserSynDTO sysUserOrgSynDTO :sysOrgUserSynDTOs){
					userIds+=sysUserOrgSynDTO.getUserId();
					userIds+=",";
				}
				if(StringUtils.isNotBlank(userIds)){
					Map<String,Object>map = new HashMap<String, Object>();
					map.put("ids", userIds.subSequence(0, userIds.length()-1));
					sysOrgUserDao.deleteSysOrgByUserIds(map);
				}
				
				for(final SysOrgUserSynDTO sysUserOrgSynDTO :sysOrgUserSynDTOs){
					sysOrgUserDao.deleteSysOrgUserByUserIdAndOrgId(new HashMap<String, Object>() {
						private static final long serialVersionUID = 1L;
						{
							put("userId", sysUserOrgSynDTO.getUserId().toString() );
							put("orgId", sysUserOrgSynDTO.getOrgId().toString() );
						}
					});
					final SysOrgUserDTO dest = new SysOrgUserDTO();
					BeanUtils.copyProperties(dest, sysUserOrgSynDTO);
					sysOrgUserDao.insertSysOrgUser(new HashMap<String, Object>() {
						private static final long serialVersionUID = 1L;
						{
							put("dto", dest);
						}
					});
				}
			}
		}
		
		//更改同步状态
//		sysSynDao.updateSynType(  new HashMap<String, Object>() {
//			private static final long serialVersionUID = 1L;
//			{
//				put("versions", versions);
//			}
//		});
		sysSynDao.updateUserSyn(  new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("versions", versions);
			}
		});
		sysSynDao.updateOrgUserSyn(  new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("versions", versions);
			}
		});
		sysSynDao.updatePositionSyn(  new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("versions", versions);
			}
		});
		sysSynDao.updateOrgSyn(  new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("versions", versions);
			}
		});
		
	}
}
