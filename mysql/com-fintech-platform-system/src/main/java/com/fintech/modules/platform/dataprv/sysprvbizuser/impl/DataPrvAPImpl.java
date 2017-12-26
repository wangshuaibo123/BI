package com.fintech.modules.platform.dataprv.sysprvbizuser.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fintech.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService;
import com.fintech.modules.platform.dataprv.sysprvbizuser.dto.SysPrvBizUserDTO;
import com.fintech.modules.platform.dataprv.sysprvbizuser.service.SysPrvBizUserService;
import com.fintech.platform.api.dataprv.DataPrvAPI;

public class DataPrvAPImpl implements DataPrvAPI {

	@Autowired
	private SysPrvBizUserService sysPrvBizUserService;
	
	@Autowired
	private VmruleMappingService vmruleMappingService;

	@Override
	public int insertDataPrv(Map<String, Object> paramMap) {
		SysPrvBizUserDTO dto = new SysPrvBizUserDTO();
		dto.setBizId(Long.parseLong(paramMap.get("bizId").toString()));
		dto.setUserId(Long.parseLong(paramMap.get("userId").toString()));
		dto.setOrgId(Long.parseLong(paramMap.get("orgId").toString()));
		dto.setTableName(paramMap.get("tableName").toString());
		paramMap.clear();
		paramMap.put("dto", dto);
		return sysPrvBizUserService.insertSysPrvBizUser(paramMap);
	}

	@Override
	public void deleteDataPrv(Map<String, Object> paramMap) {
		sysPrvBizUserService.deleteSysPrvBizUserByParams(paramMap);
	}
	
	public void updateDataUser(Map<String,Object> paramMap){
		sysPrvBizUserService.updateSysPrvBizUser(paramMap);
	}
	
	
	/**
     * 
     * 查询某个用户下 所有的虚线关系
     * 
     * vmMappingtableName
     * 
     * userId
     * 
     * orgType
     */
    
    public Map findVmruleMappingByUserId(Map<String, Object> paramMap)
    {
    	return vmruleMappingService.findVmruleMappingByUserId(paramMap);
    }

}
