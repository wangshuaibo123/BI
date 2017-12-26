package com.fintech.modules.platform.sysorg.service;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysorg.dao.SysUserSynDao;
import com.fintech.modules.platform.sysorg.dto.SysUserChangeDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserSynDTO;
import com.fintech.modules.platform.sysorg.service.SysUserService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.tools.common.DateUtil;

/**
 * @classname: SysUserSynService
 * @description: 定义  系统用户表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysorg.sysusersyn.service.SysUserSynService")
public class SysUserSynService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysUserSynDao dao;
	
	@Autowired
	private SysUserService sysUserService;
	
	public List<SysUserChangeDTO> getChangeData( String version ) throws Exception{
		//用户
		final SysUserSynDTO sysUserSynDTO = new SysUserSynDTO();
		sysUserSynDTO.setVersion(Long.parseLong(version));
		
		List<SysUserSynDTO> sysUserSynDTOs= dao.searchSysUserSyn(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("dto", sysUserSynDTO );
			}
		});
		List<SysUserChangeDTO> sysUserChangeDTOs =null;
		if(sysUserSynDTOs!=null &&  sysUserSynDTOs.size()>0){
			sysUserChangeDTOs = new ArrayList<SysUserChangeDTO>();
			for (SysUserSynDTO dto :sysUserSynDTOs){
				//SysUserSynDTO dto = sysUserSynDTOs.get(0);
				SysUserDTO sysUserDTO = sysUserService.querySysUserFullByPrimaryKey(dto.getUserId().toString());
				if(sysUserDTO.getId()==null){//上次更新的数据可能还没有入库，再取一次syn的数据
					
					final String lastModifyTime = DateUtil.format(dto.getCreateDate(), "yyyy-MM-dd HH:mm:ss.SSS");
					final SysUserSynDTO param = new SysUserSynDTO();
					param.setUserId(dto.getUserId());
					List<SysUserSynDTO> SysUserSynDTOs = dao.searchSysUserSyn(new HashMap<String, Object>() {
						private static final long serialVersionUID = 1L;
						{
							put("dto", param );
							put("lastModifyTime",lastModifyTime);
						}
					});
					if(SysUserSynDTOs!=null && SysUserSynDTOs.size()>0){
						SysUserSynDTO SysUserSynDTO  = SysUserSynDTOs.get(0);
						//sysUserDTO = new SysUserDTO();
						Date ed = SysUserSynDTO.getEntryDate();
						Date qd = SysUserSynDTO.getQuitDate();
						Date wd = SysUserSynDTO.getWorkDate();
						Date nj = SysUserSynDTO.getNjqsrq();
						
						if(SysUserSynDTO.getCreateDate()==null){
							SysUserSynDTO.setCreateDate(new Timestamp(new Date().getTime()));
						}
						if(SysUserSynDTO.getEntryDate()==null){
							SysUserSynDTO.setEntryDate(new Date());
						}
						if(SysUserSynDTO.getQuitDate()==null){
							SysUserSynDTO.setQuitDate(new Date());
						}
						if(SysUserSynDTO.getWorkDate()==null){
							SysUserSynDTO.setWorkDate(new Date());
						}
						if(SysUserSynDTO.getNjqsrq()==null){
							SysUserSynDTO.setNjqsrq(new Date());
						}
						BeanUtils.copyProperties(sysUserDTO, SysUserSynDTO);
						sysUserDTO.setId(sysUserSynDTO.getUserId());
						sysUserDTO.setQuitDate(qd);
						sysUserDTO.setWorkDate(wd);
						sysUserDTO.setEntryDate(ed);
						sysUserDTO.setNjqsrq(nj);
					}else{
						sysUserDTO = null;
					}
				}
				sysUserChangeDTOs.add( SysUserChangeDTO.getUserChangeDTO(sysUserDTO, dto));
			}
		}
		return sysUserChangeDTOs;
	}
	

	/**
     * @author
     * @description: 分页查询 系统用户表列表
     * @date 2015-01-17 17:09:03
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysUserSynDTO> searchSysUserSynByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysUserSynDTO> dataList =  dao.searchSysUserSynByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询系统用户表列表
     * @date 2015-01-17 17:09:03
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysUserSynDTO> searchSysUserSyn(Map<String,Object> searchParams) throws Exception {
	    List<SysUserSynDTO> dataList = dao.searchSysUserSyn(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询系统用户表对象
     * @date 2015-01-17 17:09:03
     * @param id
     * @return
     * @throws
     */ 
	public SysUserSynDTO querySysUserSynByPrimaryKey(String id) throws Exception {
		
		SysUserSynDTO dto = dao.findSysUserSynByPrimaryKey(id);
		
		if(dto == null) dto = new SysUserSynDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysUserSyn
     * @author
     * @description: 新增 系统用户表对象
     * @date 2015-01-17 17:09:03
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysUserSyn(SysUserSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysUserSyn(paramMap);
		
		SysUserSynDTO resultDto = (SysUserSynDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysUserSyn
     * @author
     * @description: 修改 系统用户表对象
     * @date 2015-01-17 17:09:03
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysUserSyn(SysUserSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysUserSyn(paramMap);
	}
	/**
     * @title: deleteSysUserSynByPrimaryKey
     * @author
     * @description: 删除 系统用户表,按主键
     * @date 2015-01-17 17:09:03
     * @param paramMap
     * @throws
     */ 
	public void deleteSysUserSynByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysUserSynByPrimaryKey(paramMap);
	}

}

