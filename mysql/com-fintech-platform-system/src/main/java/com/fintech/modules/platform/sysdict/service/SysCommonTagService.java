package com.fintech.modules.platform.sysdict.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.bizauth.vmtreeinfo.dto.VmtreeInfoDTO;
import com.fintech.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService;
import com.fintech.modules.platform.sysdict.dao.SysCommonTagDao;
import com.fintech.modules.platform.sysorg.dto.SysOrgDTO;
import com.fintech.modules.platform.sysorg.service.SysOrgService;

/**
 * @description: 定义通用标签server类
 * @author
 * @date: 2016年2月14日 下午2:18:50
 */
@Service("com.fintech.modules.platform.sysdict.service.SysCommonTagService")
public class SysCommonTagService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private SysCommonTagDao dao;
	@Autowired
	private VmtreeInfoService vmtreeInfoService;
	@Autowired
	private SysOrgService sysOrgService;
	/**
	 * @author
	 * @description: 
	 * @date 2016年2月14日 下午2:49:14
	 * @param codeType
	 * @return list<Map<String,String>>
	 * key:DICNAME
	 * key:SHORTNAME 概述
	 * key:DICVALUE
	 * key:ORDERBY
	 * @throws Exception
	 */
	public List<Map<String, String>> queryCommonDataInfo(String codeType) throws Exception {
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		Map<String, Object> searchParams = new HashMap<String,Object>();
		searchParams.put("codeType", codeType);
		if("HR".equals(codeType)){
			//取HR sys_org 信息
			//oracle
			//dataList = dao.queryHRCommonDataInfo(searchParams);
			dataList=queryHRCommonDataInfo();
		}else{
			//取虚拟树 vmtree_info 信息
			//oracle
			//dataList = dao.queryVMTreeCommonDataInfo(searchParams);
			//mysql
			dataList=queryVMTreeCommonDataInfo(codeType);
		}
		
		return dataList;
	}
	
	private List<Map<String,String>>queryVMTreeCommonDataInfo(String codeType)throws Exception{
		List<Map<String,String>> maps=new ArrayList<Map<String,String>>();
		Map<String, Object> searchParams = new HashMap<String,Object>();
		VmtreeInfoDTO bean =new VmtreeInfoDTO();
		bean.setOrgName("东方银谷");
		bean.setOrgType(codeType);
		searchParams.put("dto", bean);
		List<VmtreeInfoDTO> list=vmtreeInfoService.searchVmtreeInfo(searchParams);
		if(list==null || list.size()==0)return null;
		
		long rootId=list.get(0).getOrgId();
		searchParams.remove("dto");
		searchParams.put("orgType", codeType);
		searchParams.put("orgId", rootId);
		List<VmtreeInfoDTO> vmList=vmtreeInfoService.searchVmtreeInfoForTree(searchParams);
		for(VmtreeInfoDTO dto :vmList){
			Map<String,String> map=new HashMap<String,String>();
			map.put("DICVALUE", dto.getOrgId()+codeType);
			map.put("DICNAME", dto.getBaseExt7()+"-"+dto.getOrgName());
			maps.add(map);
		}
		return maps;
	}
	
	private List<Map<String,String>>queryHRCommonDataInfo()throws Exception{
		List<Map<String,String>> maps=new ArrayList<Map<String,String>>();
		Map<String, Object> searchParams = new HashMap<String,Object>();
		SysOrgDTO bean =new SysOrgDTO();
		bean.setOrgName("东方银谷");
		searchParams.put("dto", bean);
		List<SysOrgDTO> list=sysOrgService.searchSysOrg(searchParams);
		if(list==null || list.size()==0)return null;
		String orgId=String.valueOf(list.get(0).getId());
		List<SysOrgDTO> orglist=sysOrgService.searchSysOrgTreeInfo(orgId);
		for(SysOrgDTO dto :orglist){
			Map<String,String> map=new HashMap<String,String>();
			map.put("DICVALUE", dto.getOrgCode());
			map.put("DICNAME", dto.getParentName()+"-"+dto.getOrgName());
			maps.add(map);
		}
		return maps;
	}
}
