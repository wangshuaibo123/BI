package com.fintech.modules.platform.sysorg.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysorg.dao.SysPositionSynDao;
import com.fintech.modules.platform.sysorg.dto.SysPositionChangeDTO;
import com.fintech.modules.platform.sysorg.dto.SysPositionDTO;
import com.fintech.modules.platform.sysorg.dto.SysPositionSynDTO;
import com.fintech.modules.platform.sysorg.service.SysPositionService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.tools.common.DateUtil;

/**
 * @classname: SysPositionSynService
 * @description: 定义  岗位待同步表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysorg.syspositionsyn.service.SysPositionSynService")
public class SysPositionSynService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysPositionSynDao dao;
	
	@Autowired
	private SysPositionService sysPositionService;
	
	public SysPositionChangeDTO getChangeData( String version ) throws Exception{
		//岗位
		final SysPositionSynDTO sysPositionSynDTO = new SysPositionSynDTO();
		sysPositionSynDTO.setVersion(Long.parseLong(version));
		List<SysPositionSynDTO> sysPositionSynDTOs= dao.searchSysPositionSyn(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put("dto", sysPositionSynDTO );
			}
		});
		
		SysPositionChangeDTO sysPositionChangeDTO = null;
		
		if(sysPositionSynDTOs!=null &&  sysPositionSynDTOs.size()>0){
			SysPositionSynDTO dto = sysPositionSynDTOs.get(0);
			SysPositionDTO sysPositionDTO = sysPositionService.querySysPositionByPrimaryKey(dto.getPositionId().toString());
			
			if(sysPositionDTO==null|| sysPositionDTO.getId()==null){//上次更新的数据可能还没有入库，再取一次syn的数据
				final String lastModifyTime = DateUtil.format(dto.getCreateDate(), "yyyy-MM-dd HH:mm:ss.SSS");
				final SysPositionSynDTO param = new SysPositionSynDTO();
				param.setPositionId(dto.getPositionId());
				List<SysPositionSynDTO> SysPositionSynDTOs= dao.searchSysPositionSyn(new HashMap<String, Object>() {
					private static final long serialVersionUID = 1L;
					{
						put("dto", param );
						put("lastModifyTime",lastModifyTime);
					}
				});
				if(SysPositionSynDTOs!=null && SysPositionSynDTOs.size()>0){
					SysPositionSynDTO SysPositionSynDTO  = SysPositionSynDTOs.get(0);
					sysPositionDTO = new SysPositionDTO();
					BeanUtils.copyProperties(sysPositionDTO, SysPositionSynDTO);
				}
			}
			
			if(sysPositionDTO==null|| sysPositionDTO.getId()==null){
				sysPositionDTO = null;
			}
			
			sysPositionChangeDTO = SysPositionChangeDTO.getPositionChangeDTO(sysPositionDTO, dto);
		}
		return sysPositionChangeDTO;
	}
 
	/**
     * @author
     * @description: 分页查询 岗位待同步表列表
     * @date 2015-01-16 16:26:31
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysPositionSynDTO> searchSysPositionSynByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysPositionSynDTO> dataList =  dao.searchSysPositionSynByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询岗位待同步表列表
     * @date 2015-01-16 16:26:31
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysPositionSynDTO> searchSysPositionSyn(Map<String,Object> searchParams) throws Exception {
	    List<SysPositionSynDTO> dataList = dao.searchSysPositionSyn(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询岗位待同步表对象
     * @date 2015-01-16 16:26:31
     * @param id
     * @return
     * @throws
     */ 
	public SysPositionSynDTO querySysPositionSynByPrimaryKey(String id) throws Exception {
		
		SysPositionSynDTO dto = dao.findSysPositionSynByPrimaryKey(id);
		
		if(dto == null) dto = new SysPositionSynDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysPositionSyn
     * @author
     * @description: 新增 岗位待同步表对象
     * @date 2015-01-16 16:26:31
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysPositionSyn(SysPositionSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysPositionSyn(paramMap);
		
		SysPositionSynDTO resultDto = (SysPositionSynDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysPositionSyn
     * @author
     * @description: 修改 岗位待同步表对象
     * @date 2015-01-16 16:26:31
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysPositionSyn(SysPositionSynDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysPositionSyn(paramMap);
	}
	/**
     * @title: deleteSysPositionSynByPrimaryKey
     * @author
     * @description: 删除 岗位待同步表,按主键
     * @date 2015-01-16 16:26:31
     * @param paramMap
     * @throws
     */ 
	public void deleteSysPositionSynByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysPositionSynByPrimaryKey(paramMap);
	}

}

