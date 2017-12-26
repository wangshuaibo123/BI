package com.fintech.modules.platform.sysorg.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysorg.dao.SysOrgUserSynDao;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserChangeDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserSynDTO;
import com.fintech.modules.platform.sysorg.service.SysOrgUserService;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysOrgUserSynService
 * @description: 定义  SYS_ORG_USER_SYN 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysorg.sysorgusersyn.service.SysOrgUserSynService")
public class SysOrgUserSynService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysOrgUserSynDao dao;
	
	@Autowired
	private SysOrgUserService sysOrgUserService;
	
	public List<SysOrgUserChangeDTO> getChangeData( String version ) throws Exception{
		//归属机构以及任职
		final SysOrgUserSynDTO sysOrgUserSynDTO = new SysOrgUserSynDTO();
		sysOrgUserSynDTO.setVersion(Long.parseLong(version));
		List<SysOrgUserSynDTO> sysOrgUserSynDTOs= dao.searchSysOrgUserSyn(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("dto", sysOrgUserSynDTO );
			}
		});
		List<SysOrgUserChangeDTO> sysOrgUserChangeDTOs = null;
		if(sysOrgUserSynDTOs!=null && sysOrgUserSynDTOs.size()>0){
			final SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
			sysOrgUserDTO.setUserId(sysOrgUserSynDTOs.get(0).getUserId());
			List<SysOrgUserDTO> sysOrgUserDTOs = sysOrgUserService.searchFuzzySysOrgUser(new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("dto", sysOrgUserDTO );
				}
			});
			sysOrgUserChangeDTOs = SysOrgUserChangeDTO.getSysOrgUserChangeDTOs(sysOrgUserDTOs, sysOrgUserSynDTOs);
		}
		return sysOrgUserChangeDTOs;
	}

	/**
     * @author
     * @description: 分页查询 SYS_ORG_USER_SYN列表
     * @date 2015-01-19 11:48:48
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysOrgUserSynDTO> searchSysOrgUserSynByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysOrgUserSynDTO> dataList =  dao.searchSysOrgUserSynByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询SYS_ORG_USER_SYN列表
     * @date 2015-01-19 11:48:48
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysOrgUserSynDTO> searchSysOrgUserSyn(Map<String,Object> searchParams) throws Exception {
	    List<SysOrgUserSynDTO> dataList = dao.searchSysOrgUserSyn(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询SYS_ORG_USER_SYN对象
     * @date 2015-01-19 11:48:48
     * @param id
     * @return
     * @throws
     */ 
	public SysOrgUserSynDTO querySysOrgUserSynByPrimaryKey(String id) throws Exception {
		
		SysOrgUserSynDTO dto = dao.findSysOrgUserSynByPrimaryKey(id);
		
		if(dto == null) dto = new SysOrgUserSynDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysOrgUserSyn
     * @author
     * @description: 新增 SYS_ORG_USER_SYN对象
     * @date 2015-01-19 11:48:48
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysOrgUserSyn(SysOrgUserSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysOrgUserSyn(paramMap);
		
		SysOrgUserSynDTO resultDto = (SysOrgUserSynDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysOrgUserSyn
     * @author
     * @description: 修改 SYS_ORG_USER_SYN对象
     * @date 2015-01-19 11:48:48
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysOrgUserSyn(SysOrgUserSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysOrgUserSyn(paramMap);
	}
	/**
     * @title: deleteSysOrgUserSynByPrimaryKey
     * @author
     * @description: 删除 SYS_ORG_USER_SYN,按主键
     * @date 2015-01-19 11:48:48
     * @param paramMap
     * @throws
     */ 
	public void deleteSysOrgUserSynByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysOrgUserSynByPrimaryKey(paramMap);
	}

}

