package com.fintech.modules.platform.sysorg.service;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysorg.dao.SysOrgSynDao;
import com.fintech.modules.platform.sysorg.dto.SysOrgChangeDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgDTO;
import com.fintech.modules.platform.sysorg.dto.SysOrgSynDTO;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.tools.common.DateUtil;

/**
 * @classname: SysOrgSynService
 * @description: 定义  机构部门表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysorg.sysorgsyn.service.SysOrgSynService")
public class SysOrgSynService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysOrgSynDao dao;
	
	@Autowired
	private SysOrgService sysOrgService;
	
	public SysOrgChangeDTO getChangeData( String version ) throws Exception{
		//机构
		final SysOrgSynDTO sysOrgSynDTO = new SysOrgSynDTO();
		sysOrgSynDTO.setVersion(Long.parseLong(version));
		List<SysOrgSynDTO> sysOrgSynDTOs= dao.searchSysOrgSyn(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("dto", sysOrgSynDTO );
			}
		});
		
		SysOrgChangeDTO sysOrgChangeDTO = null;
		if(sysOrgSynDTOs!=null &&  sysOrgSynDTOs.size()>0){
			SysOrgSynDTO dto = sysOrgSynDTOs.get(0);
			SysOrgDTO sysOrgDTO = sysOrgService.querySysOrgByPrimaryKey(dto.getOrgId().toString());
			
			if(sysOrgDTO==null|| sysOrgDTO.getId()==null){//上次更新的数据可能还没有入库，再取一次syn的数据
					final String lastModifyTime = DateUtil.format(dto.getCreateDate(), "yyyy-MM-dd HH:mm:ss.SSS");
					final SysOrgSynDTO param = new SysOrgSynDTO();
					param.setOrgId(dto.getOrgId());
					List<SysOrgSynDTO> SysOrgSynDTOs= dao.searchSysOrgSyn(new HashMap<String, Object>() {
						private static final long serialVersionUID = 1L;
						{
							put("dto", param );
							put("lastModifyTime",lastModifyTime);
						}
					});
					if(SysOrgSynDTOs!=null && SysOrgSynDTOs.size()>0){
						SysOrgSynDTO sysOrgSyn  = SysOrgSynDTOs.get(0);
						sysOrgDTO = new SysOrgDTO();
						Date effectiveDate= sysOrgSyn.getEffectiveDate();
						Date createTime=sysOrgSyn.getCreateTime();
						if(sysOrgSyn.getEffectiveDate()==null){
							sysOrgSyn.setEffectiveDate(new Date());
						}
						if(sysOrgSyn.getCreateDate()==null){
							sysOrgSyn.setCreateDate(new java.sql.Timestamp(new Date().getTime()));
						}
						if(sysOrgSyn.getCreateTime()==null){
							sysOrgSyn.setCreateTime(new Date());
						}
						BeanUtils.copyProperties(sysOrgDTO, sysOrgSyn);
						sysOrgDTO.setEffectiveDate(effectiveDate);
						sysOrgDTO.setCreateTime(createTime);
					}
			}
			
			String sysOrgParentName = null;
			String sysOrgSynParentName = null;
			if(sysOrgDTO!=null && sysOrgDTO.getId()!=null ){
				SysOrgDTO  sysOrgParentDTO = sysOrgService.querySysOrgByPrimaryKey(sysOrgDTO.getParentId());
				sysOrgParentName = sysOrgParentDTO.getOrgName();
			}
			SysOrgDTO  sysOrgSynParentDTO = sysOrgService.querySysOrgByPrimaryKey(dto.getParentId());
			if(sysOrgSynParentDTO!=null && sysOrgSynParentDTO.getId()!=null ){
				sysOrgSynParentName = sysOrgSynParentDTO.getOrgName();
			}
			sysOrgChangeDTO = SysOrgChangeDTO.getOrgChangeDTO(sysOrgDTO, sysOrgParentName, dto, sysOrgSynParentName);
		}
		
		return sysOrgChangeDTO;
	}


	/**
     * @author
     * @description: 分页查询 机构部门表列表
     * @date 2015-01-20 10:24:15
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysOrgSynDTO> searchSysOrgSynByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysOrgSynDTO> dataList =  dao.searchSysOrgSynByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询机构部门表列表
     * @date 2015-01-20 10:24:15
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysOrgSynDTO> searchSysOrgSyn(Map<String,Object> searchParams) throws Exception {
	    List<SysOrgSynDTO> dataList = dao.searchSysOrgSyn(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询机构部门表对象
     * @date 2015-01-20 10:24:15
     * @param id
     * @return
     * @throws
     */ 
	public SysOrgSynDTO querySysOrgSynByPrimaryKey(String id) throws Exception {
		
		SysOrgSynDTO dto = dao.findSysOrgSynByPrimaryKey(id);
		
		if(dto == null) dto = new SysOrgSynDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysOrgSyn
     * @author
     * @description: 新增 机构部门表对象
     * @date 2015-01-20 10:24:15
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysOrgSyn(SysOrgSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysOrgSyn(paramMap);
		
		SysOrgSynDTO resultDto = (SysOrgSynDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysOrgSyn
     * @author
     * @description: 修改 机构部门表对象
     * @date 2015-01-20 10:24:15
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysOrgSyn(SysOrgSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysOrgSyn(paramMap);
	}
	/**
     * @title: deleteSysOrgSynByPrimaryKey
     * @author
     * @description: 删除 机构部门表,按主键
     * @date 2015-01-20 10:24:15
     * @param paramMap
     * @throws
     */ 
	public void deleteSysOrgSynByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysOrgSynByPrimaryKey(paramMap);
	}

}

