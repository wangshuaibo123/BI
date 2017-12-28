package com.jy.modules.logmonitor.sysapplevelsetup.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.logmonitor.sysapperrorinfo.service.SysAppErrorInfoService;
import com.jy.modules.logmonitor.sysapplevelsetup.dao.SysAppLevelSetupDao;
import com.jy.modules.logmonitor.sysapplevelsetup.dto.SysAppLevelSetupDTO;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: SysAppLevelSetupService
 * @description: 定义  错误级别设定表 实现类
 * @author:  lei
 */
@Service("com.jy.modules.logmonitor.sysapplevelsetup.service.SysAppLevelSetupService")
public class SysAppLevelSetupService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysAppLevelSetupDao dao;
	
	@Autowired
	private SysAppErrorInfoService sysAppErrorInfoService;

	/**
     * @author lei
     * @description: 分页查询 错误级别设定表列表
     * @date 2015-06-12 16:33:50
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysAppLevelSetupDTO> searchSysAppLevelSetupByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysAppLevelSetupDTO> dataList =  dao.searchSysAppLevelSetupByPaging(searchParams);
		return dataList;
	}
	/**
     * @author lei
     * @description: 按条件查询错误级别设定表列表
     * @date 2015-06-12 16:33:50
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysAppLevelSetupDTO> searchSysAppLevelSetup(Map<String,Object> searchParams) throws Exception {
	    List<SysAppLevelSetupDTO> dataList = dao.searchSysAppLevelSetup(searchParams);
        return dataList;
    }
	/**
     * @author lei
     * @description: 查询错误级别设定表对象
     * @date 2015-06-12 16:33:50
     * @param id
     * @return
     * @throws
     */ 
	public SysAppLevelSetupDTO querySysAppLevelSetupByPrimaryKey(String id) throws Exception {
		
		SysAppLevelSetupDTO dto = dao.findSysAppLevelSetupByPrimaryKey(id);
		
		if(dto == null) dto = new SysAppLevelSetupDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysAppLevelSetup
     * @author lei
     * @description: 新增 错误级别设定表对象
     * @date 2015-06-12 16:33:50
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysAppLevelSetup(SysAppLevelSetupDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysAppLevelSetup(paramMap);
		
		SysAppLevelSetupDTO resultDto = (SysAppLevelSetupDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		sysAppErrorInfoService.updateSysAppErrorInfoLevel(dto.getAppFlag(), dto.getKeyWord(), dto.getLogLevel().toString(), keyId);
		return keyId;
	}
	/**
     * @title: updateSysAppLevelSetup
     * @author lei
     * @description: 修改 错误级别设定表对象
     * @date 2015-06-12 16:33:50
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysAppLevelSetup(SysAppLevelSetupDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysAppLevelSetup(paramMap);
		sysAppErrorInfoService.updateSysAppErrorInfoLevel(dto.getAppFlag(), dto.getKeyWord(), dto.getLogLevel().toString(), dto.getId());
	}
	/**
     * @title: deleteSysAppLevelSetupByPrimaryKey
     * @author lei
     * @description: 删除 错误级别设定表,按主键
     * @date 2015-06-12 16:33:50
     * @param paramMap
     * @throws
     */ 
	public void deleteSysAppLevelSetupByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysAppLevelSetupByPrimaryKey(paramMap);
	}
	
	/**
	 * 查询最大的频率
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public int searchMaxRateOfUnit(String rateUnit) throws Exception{
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("rateUnit", rateUnit);
		java.lang.Integer maxRate = dao.searchMaxRateOfUnit(searchParams);
		return maxRate==null ? 0 : maxRate;
    }

}

