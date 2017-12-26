package com.fintech.modules.platform.bizauth.vmrulemapping.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.modules.platform.bizauth.vmdatapriv.dao.VmdataPrivDao;
import com.fintech.modules.platform.bizauth.vmdatapriv.dto.VmdataPrivDTO;
import com.fintech.modules.platform.bizauth.vmrulemapping.dao.VmruleMappingDao;
import com.fintech.modules.platform.bizauth.vmrulemapping.dto.VmruleMappingDTO;
import com.fintech.modules.platform.bizauth.vmtreeinfo.dto.VmtreeInfoDTO;
import com.fintech.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService;
import com.fintech.modules.platform.bizauth.vmuservmorgmap.dao.VmuserVmorgMapDao;
import com.fintech.modules.platform.bizauth.vmuservmorgmap.dto.VmuserVmorgMapDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: VmruleMappingService
 * @description: 定义 映射表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService")
public class VmruleMappingService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private VmruleMappingDao dao;

    @Autowired
    private VmdataPrivDao pDao;

    @Autowired
    private VmtreeInfoService service;

    @Autowired
    private VmuserVmorgMapDao uoDao;

    /**
     * @author
     * @description: 分页查询 映射表列表
     * @date 2015-01-16 17:14:38
     * @param searchParams 条件
     * @return
     * @throws
     */
    public List<VmruleMappingDTO> searchVmruleMappingByPaging(Map<String, Object> searchParams) throws Exception {
        List<VmruleMappingDTO> dataList = dao.searchVmruleMappingByPaging(searchParams);
        return dataList;
    }

    /**
     * @author
     * @description: 按条件查询映射表列表
     * @date 2015-01-16 17:14:38
     * @param searchParams 条件
     * @return
     * @throws
     */
    public List<VmruleMappingDTO> searchVmruleMapping(Map<String, Object> searchParams) throws Exception {
        List<VmruleMappingDTO> dataList = dao.searchVmruleMapping(searchParams);
        return dataList;
    }

    /**
     * @author
     * @description: 查询映射表对象
     * @date 2015-01-16 17:14:38
     * @param id
     * @return
     * @throws
     */
    public VmruleMappingDTO queryVmruleMappingByPrimaryKey(String id) throws Exception {

        VmruleMappingDTO dto = dao.findVmruleMappingByPrimaryKey(id);

        if (dto == null)
            dto = new VmruleMappingDTO();

        return dto;

    }

    /**
     * @title: insertVmruleMapping
     * @author
     * @description: 新增 映射表对象
     * @date 2015-01-16 17:14:38
     * @param dto
     * @return
     * @throws
     */
    @SuppressWarnings("all")
    @Transactional(rollbackFor = Exception.class)
    public void insertVmruleMapping(VmruleMappingDTO dto) throws Exception {
        if (dto.getMapKey() != null && dto.getMapKey().length() > 0) {
            if (dto.getMapKey().indexOf(",") > 0) {
                String[] resStr = dto.getMapKey().split(",");
                for (String str : resStr) {
                    VmruleMappingDTO dto1 = new VmruleMappingDTO();
                    dto1.setMapKey(str);
                    dto1.setMapValue(dto.getMapValue());
                    dto1.setMapType(dto.getMapType());
                    dto1.setOrgType(dto.getOrgType());
                    insertDataPriv(dto1,true);
                }
            } else {
                if(dto.getMapValue().indexOf(",") > 0){
                    String[] resStr = dto.getMapValue().split(",");
                    for (String str : resStr) {
                        VmruleMappingDTO dto1 = new VmruleMappingDTO();
                        dto1.setMapKey(dto.getMapKey());
                        dto1.setMapValue(str);
                        dto1.setMapType(dto.getMapType());
                        dto1.setOrgType(dto.getOrgType());
                        insertDataPriv(dto1,true);
                    }
                }else{
                    // 先插入mapping映射表
                    insertDataPriv(dto,true);
                }
              
            }
        }
        // VmruleMappingDTO resultDto = (VmruleMappingDTO) paramMap.get("dto");
        // Long keyId = resultDto.getId();
        // return keyId;
    }

    public void insertDataPriv(VmruleMappingDTO dto,boolean flag) throws Exception {
        // 在插入权限表
        // 对人
        if ("1".equals(dto.getMapType())) {
            personToPerson(dto,flag);

        }
        // 对组织
        if ("2".equals(dto.getMapType())) {
            personToOrg(dto,flag);
        }
        // 组织对组织
        if ("3".equals(dto.getMapType())) {
            orgToOrg(dto,flag);
        }
    }

    /**
     * 
     * 组织对组织
     * 
     */

    public void orgToOrg(VmruleMappingDTO dto,boolean falg) throws Exception {
        // 插入mapping映射表
    	if(falg)
    	{	
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("dto", dto);
	        paramMap.put("vmtableName", dto.getOrgType() + "_" + "VMRULE_MAPPING");
	        paramMap.put("seqName", "seq_" + dto.getOrgType() + "_VMRULE_MAPPING");
	        dao.insertVmruleMapping(paramMap);
    	}
        // 插入数据权限池 查出key组织的所有人
        Map searchParams = new HashMap<String, Object>();
        searchParams.put("orgId", Long.parseLong(dto.getMapKey()));
        searchParams.put("orgType", dto.getOrgType());
        // 递归查询出所有的叶子节点
        // List<VmtreeInfoDTO> list
        // =service.searchVmtreeInfoForTree(searchParams);

        VmtreeInfoDTO vmtreeInfoDTO = service.queryVmtreeInfoByPrimaryKey(dto.getMapKey(), dto.getOrgType());

        // 装载所有用户
        List<VmuserVmorgMapDTO> users = new ArrayList<VmuserVmorgMapDTO>();
        // for (VmtreeInfoDTO v : list) {
        Map searchUserParams = new HashMap<String, Object>();
        VmuserVmorgMapDTO userOrg = new VmuserVmorgMapDTO();
        userOrg.setOrgId(vmtreeInfoDTO.getOrgId());
        userOrg.setOrgType(dto.getOrgType());
        searchUserParams.put("dto", userOrg);
        List<VmuserVmorgMapDTO> userOrgs = uoDao.searchVmuserVmorgMap(searchUserParams);
        for (VmuserVmorgMapDTO uo : userOrgs) {
            users.add(uo);
        }
        // }
        inserAuthPool(users, dto);
    }

    /**
     * 
     * 组织对组织插入数据权限池
     * 
     */
    public void inserAuthPool(List<VmuserVmorgMapDTO> users, VmruleMappingDTO dto) throws Exception {

        for (VmuserVmorgMapDTO user : users) {
            // 插入到数据权限集合表
            Map<String, Object> dataParamMap = new HashMap<String, Object>();
            VmdataPrivDTO data = new VmdataPrivDTO();
            /** 用户ID */
            data.setUserId(user.getUserId());
            data.setVmRuleMappingId(dto.getId());
            data.setCreateBy(dto.getCreateBy());
            VmtreeInfoDTO vmTree = service.queryVmtreeInfoByPrimaryKey(dto.getMapValue(), dto.getOrgType());
            // 为1的是HR树的叶子节点 直接存入
            if ("1".equals(vmTree.getEndFlag()) && "HR".equals(vmTree.getSourceType())) {
                data.setOrgId(Long.parseLong(dto.getMapValue()));
                data.setOrgType(dto.getOrgType());

                dataParamMap.put("dto", data);
                dataParamMap.put("vmtableName", dto.getOrgType() + "_" + "VMDATA_PRIV");
                dataParamMap.put("seqName", "seq_" + dto.getOrgType() + "_VMDATA_PRIV");
                pDao.insertVmdataPriv(dataParamMap);
            } else {
                Map searchParams = new HashMap<String, Object>();
                searchParams.put("orgId", Long.parseLong(dto.getMapValue()));
                searchParams.put("orgType", dto.getOrgType());
                // 递归查询出所有的叶子节点
                List<VmtreeInfoDTO> list = service.searchVmtreeInfoForTree(searchParams);
                for (VmtreeInfoDTO v : list) {
                	if ("1".equals(v.getEndFlag()) && "HR".equals(v.getSourceType()))
                	{
                		
                		data.setOrgId(v.getOrgId());
                        data.setOrgType(dto.getOrgType());
                        data.setVmRuleMappingId(dto.getId());
                        data.setCreateBy(dto.getCreateBy());
                        dataParamMap.put("dto", data);
                        dataParamMap.put("vmtableName", dto.getOrgType() + "_" + "VMDATA_PRIV");
                        dataParamMap.put("seqName", "seq_" + dto.getOrgType() + "_VMDATA_PRIV");
                        pDao.insertVmdataPriv(dataParamMap);
                	}
                    //Map searchUserParams = new HashMap<String, Object>();
                   // VmuserVmorgMapDTO userOrg = new VmuserVmorgMapDTO();
                    //userOrg.setOrgId(v.getOrgId());
                    //userOrg.setOrgType(v.getOrgType());
                   // searchUserParams.put("dto", userOrg);
                    //List<VmuserVmorgMapDTO> userOrgs = uoDao.searchVmuserVmorgMap(searchUserParams);
                   // for (VmuserVmorgMapDTO uo : userOrgs) {
                      //  data.setOwnerId(uo.getUserId());
                      //  data.setOrgType(uo.getOrgType());
                      //  dataParamMap.put("dto", data);
                      //  dataParamMap.put("vmtableName", dto.getOrgType() + "_" + "VMDATA_PRIV");
                      //  dataParamMap.put("seqName", "seq_" + dto.getOrgType() + "_VMDATA_PRIV");
                      //  pDao.insertVmdataPriv(dataParamMap);
                    //}
                }
            }
        }
    }

    /**
     * 人对人
     * 
     * @param dto
     */
    public void personToPerson(VmruleMappingDTO dto,boolean falg) {
        if (dto.getMapValue().indexOf(",") > 0) {
            String[] resStr = dto.getMapValue().split(",");
            // 先插入mapping映射表
            for (String str : resStr) {
	            Map<String, Object> paramMap = new HashMap<String, Object>();
	            dto.setMapValue(str);
	            paramMap.put("dto", dto);
	            paramMap.put("vmtableName", dto.getOrgType() + "_" + "VMRULE_MAPPING");
	            paramMap.put("seqName", "seq_" + dto.getOrgType() + "_VMRULE_MAPPING");
	            dao.insertVmruleMapping(paramMap);
                // 插入数据权限集合表
                Map<String, Object> dataParamMap = new HashMap<String, Object>();
                VmdataPrivDTO data = new VmdataPrivDTO();
                /** 用户ID */
                data.setUserId(Long.parseLong(dto.getMapKey()));
                /** 数据归属人ID */
                data.setOwnerId(Long.parseLong(str));
                /** ORG_TYPE */
                data.setOrgType(dto.getOrgType());
                data.setCreateBy(dto.getCreateBy());
                data.setVmRuleMappingId(dto.getId());
                dataParamMap.put("dto", data);
                dataParamMap.put("vmtableName", dto.getOrgType() + "_" + "VMDATA_PRIV");
                dataParamMap.put("seqName", "seq_" + dto.getOrgType() + "_VMDATA_PRIV");
                pDao.insertVmdataPriv(dataParamMap);
            }
        } else {
            // 先插入mapping映射表
        	if(falg)
        	{	
	            Map<String, Object> paramMap = new HashMap<String, Object>();
	            paramMap.put("dto", dto);
	            paramMap.put("vmtableName", dto.getOrgType() + "_" + "VMRULE_MAPPING");
	            paramMap.put("seqName", "seq_" + dto.getOrgType() + "_VMRULE_MAPPING");
	            dao.insertVmruleMapping(paramMap);
        	}
            Map<String, Object> dataParamMap = new HashMap<String, Object>();
            VmdataPrivDTO data = new VmdataPrivDTO();
            /** 用户ID */
            data.setUserId(Long.parseLong(dto.getMapKey()));
            /** 数据归属人ID */
            data.setOwnerId(Long.parseLong(dto.getMapValue()));
            /** ORG_TYPE */
            data.setOrgType(dto.getOrgType());
            data.setCreateBy(dto.getCreateBy());
            data.setVmRuleMappingId(dto.getId());
            dataParamMap.put("dto", data);
            dataParamMap.put("vmtableName", dto.getOrgType() + "_" + "VMDATA_PRIV");
            dataParamMap.put("seqName", "seq_" + dto.getOrgType() + "_VMDATA_PRIV");
            pDao.insertVmdataPriv(dataParamMap);
        }
    }

    /**
     * 
     * 人对组织
     * 
     * @param dto
     * @throws Exception
     */
    public void personToOrg(VmruleMappingDTO dto,boolean falg) throws Exception {
        // 先插入mapping映射表
    	if(falg)
    	{	
	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("dto", dto);
	        paramMap.put("vmtableName", dto.getOrgType() + "_" + "VMRULE_MAPPING");
	        paramMap.put("seqName", "seq_" + dto.getOrgType() + "_VMRULE_MAPPING");
	        dao.insertVmruleMapping(paramMap);
    	}
        // 插入到数据权限集合表
        Map<String, Object> dataParamMap = new HashMap<String, Object>();
        VmdataPrivDTO data = new VmdataPrivDTO();
        /** 用户ID */
        data.setUserId(Long.parseLong(dto.getMapKey()));
        VmtreeInfoDTO vmTree = service.queryVmtreeInfoByPrimaryKey(dto.getMapValue(), dto.getOrgType());
        // 为1的是HR树的叶子节点 直接存入
        if ("1".equals(vmTree.getEndFlag()) && "HR".equals(vmTree.getSourceType())) {
            data.setOrgId(Long.parseLong(dto.getMapValue()));
            data.setOrgType(dto.getOrgType());
            data.setVmRuleMappingId(dto.getId());
            data.setCreateBy(dto.getCreateBy());
            dataParamMap.put("dto", data);
            dataParamMap.put("vmtableName", dto.getOrgType() + "_" + "VMDATA_PRIV");
            dataParamMap.put("seqName", "seq_" + dto.getOrgType() + "_VMDATA_PRIV");
            pDao.insertVmdataPriv(dataParamMap);
        } else {
            Map searchParams = new HashMap<String, Object>();
            searchParams.put("orgId", Long.parseLong(dto.getMapValue()));
            searchParams.put("orgType", dto.getOrgType());
            // 递归查询出所有的叶子节点
            List<VmtreeInfoDTO> list = service.searchVmtreeInfoForTree(searchParams);
            for (VmtreeInfoDTO v : list) {
               // Map searchUserParams = new HashMap<String, Object>();
                //VmuserVmorgMapDTO userOrg = new VmuserVmorgMapDTO();
               // userOrg.setOrgId(v.getOrgId());
               // userOrg.setOrgType(v.getOrgType());
                //searchUserParams.put("dto", userOrg);
                //List<VmuserVmorgMapDTO> userOrgs = uoDao.searchVmuserVmorgMap(searchUserParams);
            	if ("1".equals(v.getEndFlag()) && "HR".equals(v.getSourceType())) 
            	{
            		data.setOrgId(v.getOrgId());
                    data.setOrgType(dto.getOrgType());
                    data.setVmRuleMappingId(dto.getId());
                    data.setCreateBy(dto.getCreateBy());
                    dataParamMap.put("dto", data);
                    dataParamMap.put("vmtableName", dto.getOrgType() + "_" + "VMDATA_PRIV");
                    dataParamMap.put("seqName", "seq_" + dto.getOrgType() + "_VMDATA_PRIV");
                    pDao.insertVmdataPriv(dataParamMap);
            	}
            }
                
                
            //    for (VmuserVmorgMapDTO uo : userOrgs) {
                   	// data.setOwnerId(uo.getUserId());
        			//data.setOrgId(Long.parseLong(dto.getMapValue()));
                    //data.setOrgType(dto.getOrgType());
                   	//data.setVmRuleMappingId(dto.getId());
                   	//data.setCreateBy(dto.getCreateBy());
                  	//dataParamMap.put("dto", data);
                   	//dataParamMap.put("vmtableName", dto.getOrgType() + "_" + "VMDATA_PRIV");
                  	//dataParamMap.put("seqName", "seq_" + dto.getOrgType() + "_VMDATA_PRIV");
                  	//pDao.insertVmdataPriv(dataParamMap);
             //   }
           // }
        }
    }

    /**
     * @title: updateVmruleMapping
     * @author
     * @description: 修改 映射表对象
     * @date 2015-01-16 17:14:38
     * @param paramMap
     * @return
     * @throws
     */
    public void updateVmruleMapping(VmruleMappingDTO dto) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dto", dto);

        dao.updateVmruleMapping(paramMap);
    }

    /**
     * @title: deleteVmruleMappingByPrimaryKey
     * @author
     * @description: 删除 映射表,按主键
     * @date 2015-01-16 17:14:38
     * @param paramMap
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteVmruleMappingByPrimaryKey(BaseDTO baseDto, String ids, String orgtype) throws Exception {
        if (StringUtils.isEmpty(ids))
            throw new Exception("删除失败！传入的参数主键为null");
        // 先删除mapping表
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dto", baseDto);
        paramMap.put("ids", ids);
        paramMap.put("vmtableName", orgtype + "_" + "VMRULE_MAPPING");
        dao.deleteVmruleMappingByPrimaryKey(paramMap);

        // 在删除权限表
        Map<String, Object> priParamMap = new HashMap<String, Object>();
        priParamMap.put("dto", baseDto);
        priParamMap.put("ids", ids);
        priParamMap.put("vmtableName", orgtype + "_" + "VMDATA_PRIV");
        pDao.deleteVmdataPrivByMappingId(priParamMap);
    }
    
    /**
     * 
     * 数据权限刷新
     * @param ids
     * @param orgtype
     * @throws Exception
     */
    
    @Transactional(rollbackFor = Exception.class)
    public void fulshVmruleMapping(String ids, String orgtype)throws Exception
    {
    	
    	// 在删除权限表
        Map<String, Object> priParamMap = new HashMap<String, Object>();
        priParamMap.put("ids", ids);
        priParamMap.put("vmtableName", orgtype + "_" + "VMDATA_PRIV");
        pDao.deleteVmdataPrivByMappingId(priParamMap);
    
        
        if (ids.indexOf(",") > 0) {
            String[] resStr = ids.split(",");
            for (String str : resStr) {
            	Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("id", str);
                paramMap.put("vmtableName", orgtype + "_" + "VMRULE_MAPPING");
                VmruleMappingDTO dto1 =dao.findVmruleMappingByPrimaryKeyAndTableName(paramMap); 
                insertDataPriv(dto1,false);
            }
        }else
        {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("id", ids);
            paramMap.put("vmtableName", orgtype + "_" + "VMRULE_MAPPING");
            VmruleMappingDTO dto1 =dao.findVmruleMappingByPrimaryKeyAndTableName(paramMap); 
            insertDataPriv(dto1,false);
        }
    }
    
    /**
     * 
     * 
     * @param sourceId 删除ID
     * @param sourceType 类型 1为人员 2为组织
     * @param orgType orgType 组织树类型
     * 
     */

    @Transactional(rollbackFor = Exception.class)
    public void deletePrivApi(String sourceId, String sourceType, String orgType) throws Exception {

        if ("1".equals(sourceType)) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("vmMappingtableName", orgType + "_" + "VMRULE_MAPPING");
            paramMap.put("vmPrivtableName", orgType + "_" + "VMDATA_PRIV");
            paramMap.put("sourceId", sourceId);
            dao.deleteVmDataPrivByUserId(paramMap);
            dao.deleteVmrleMappingByUserId(paramMap);
        }
        if ("2".equals(sourceType)) {

            Map searchUserParams = new HashMap<String, Object>();
            VmuserVmorgMapDTO userOrg = new VmuserVmorgMapDTO();
            userOrg.setOrgId(Long.parseLong(sourceId));
            userOrg.setOrgType(orgType);
            searchUserParams.put("dto", userOrg);
            List<VmuserVmorgMapDTO> userOrgs = uoDao.searchVmuserVmorgMap(searchUserParams);
            // 删除该组织下的所有用户的映射和数据权限
            for (VmuserVmorgMapDTO uo : userOrgs) {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("vmMappingtableName", orgType + "_" + "VMRULE_MAPPING");
                paramMap.put("vmPrivtableName", orgType + "_" + "VMDATA_PRIV");
                paramMap.put("sourceId", uo.getUserId());
                dao.deleteVmDataPrivByUserId(paramMap);
                dao.deleteVmrleMappingByUserId(paramMap);
            }
            // 删除组织对组织的映射和数据权限
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("vmMappingtableName", orgType + "_" + "VMRULE_MAPPING");
            paramMap.put("vmPrivtableName", orgType + "_" + "VMDATA_PRIV");
            paramMap.put("sourceId", sourceId);

            dao.deleteVmDataPrivByOrgId(paramMap);
            dao.deleteVmrleMappingByOrgId(paramMap);
        }
    }
    
    /**
     * 
     * 查询某个用户下 所有的虚线关系
     * 
     * 
     */
    
    public Map findVmruleMappingByUserId(Map<String, Object> paramMap)
    {
    	List<SysUserDTO> list=  dao.findVmruleMappingByUserId(paramMap);
    	Map<String,String>  maps = new LinkedHashMap<String,String>();
    	for(SysUserDTO su:list)
    	{
    		maps.put(su.getId().toString(), su.getUserName());
    	}
    	return maps;
    	
    }

    /*
     * 离职人员的映射清理以及权限清理
     */
	public void modifyCleanVmruleMapping(Map<String, Object> searchParams) {
		//清理业务权限中的离职人员业务权限
		Map<String, Object> privMap=new HashMap<String, Object>();
		privMap.put("vmtableName", searchParams.get("orgType")+"_VMDATA_PRIV");
		privMap.put("startOrg", searchParams.get("startOrg"));
		privMap.put("mapping", "true");//true 标识这个清理是由映射发起的。
		privMap.put("orgType", searchParams.get("orgType"));
		pDao.modifyCleanVmruleDataPriv(privMap);
		//清理业务权限中的离职人员映射关系
		Map<String, Object> mappingMap=new HashMap<String, Object>();
		mappingMap.put("vmtableName", searchParams.get("orgType")+"_VMRULE_MAPPING");
		mappingMap.put("startOrg", searchParams.get("startOrg"));
		mappingMap.put("orgType", searchParams.get("orgType"));
		dao.modifyCleanVmruleMapping(mappingMap);
	}   
}
