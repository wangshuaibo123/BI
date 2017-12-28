package com.jy.modules.platform.bizauth.vmuservmorgmap.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.modules.platform.bizauth.vmdatapriv.dao.VmdataPrivDao;
import com.jy.modules.platform.bizauth.vmdatapriv.dto.VmdataPrivDTO;
import com.jy.modules.platform.bizauth.vmdatapriv.service.VmdataPrivService;
import com.jy.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService;
import com.jy.modules.platform.bizauth.vmtreeinfo.dto.VmtreeInfoDTO;
import com.jy.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService;
import com.jy.modules.platform.bizauth.vmuservmorgmap.dao.VmuserVmorgMapDao;
import com.jy.modules.platform.bizauth.vmuservmorgmap.dto.VmuserVmorgMapDTO;
import com.jy.modules.platform.sysorg.dto.SysUserDTO;
import com.jy.platform.api.org.OrgAPI;
import com.jy.platform.api.org.OrgInfo;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: VmuserVmorgMapService
 * @description: 定义  员工虚拟组织关系表 实现类
 * @author:  dlg
 */
@Service("com.jy.modules.platform.bizauth.vmuservmorgmap.service.VmuserVmorgMapService")
public class VmuserVmorgMapService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private VmuserVmorgMapDao dao;
	
	@Autowired
	private VmdataPrivDao pDao;
	
	
	@Autowired
	@Qualifier("com.jy.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService")
	private VmtreeInfoService vmtreeservice;
	
	
	@Autowired
    @Qualifier("com.jy.modules.platform.bizauth.vmdatapriv.service.VmdataPrivService")
    private VmdataPrivService vmdataservice;
	
	@Autowired
    @Qualifier("com.jy.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService")
    private VmruleMappingService vmrulemapservice;
	
	@Autowired
	private OrgAPI orgAPI;

	/**
     * @author dlg
     * @description: 分页查询 员工虚拟组织关系表列表
     * @date 2015-01-16 17:15:01
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<VmuserVmorgMapDTO> searchVmuserVmorgMapByPaging(Map<String,Object> searchParams) throws Exception {
		List<VmuserVmorgMapDTO> dataList =  dao.searchVmuserVmorgMapByPaging(searchParams);
		return dataList;
	}
	/**
     * @author dlg
     * @description: 按条件查询员工虚拟组织关系表列表
     * @date 2015-01-16 17:15:01
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<VmuserVmorgMapDTO> searchVmuserVmorgMap(Map<String,Object> searchParams) throws Exception {
	    List<VmuserVmorgMapDTO> dataList = dao.searchVmuserVmorgMap(searchParams);
        return dataList;
    }
	/**
     * @author dlg
     * @description: 查询员工虚拟组织关系表对象
     * @date 2015-01-16 17:15:01
     * @param id
     * @return
     * @throws
     */ 
	public VmuserVmorgMapDTO queryVmuserVmorgMapByPrimaryKey(String id) throws Exception {
		
		VmuserVmorgMapDTO dto = dao.findVmuserVmorgMapByPrimaryKey(id);
		
		if(dto == null) dto = new VmuserVmorgMapDTO();
		
		return dto;
		
	}
	
	/**
     * @author dlg
     * @description: 查询员工虚拟组织关系表对象是否重复
     * @date 2015-01-26 17:15:01
     * @param id
     * @return
     * @throws
     */ 
	public void validateVmuserVmorgMap(String userId,String orgId,String orgType) throws Exception{
		VmuserVmorgMapDTO dto = new VmuserVmorgMapDTO();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		dto.setUserId(Long.parseLong(userId));
		//dto.setOrgId(Long.parseLong(orgId));//不传orgId，只校验同一虚拟树下，是否存在同一用户
		dto.setOrgType(orgType);
		paramMap.put("dto", dto);
		//验证对象是否重复 
		if(StringUtils.isNotEmpty(userId)){
			List<VmuserVmorgMapDTO> dataList = this.validateVmuserVmorgMapBycondtions(paramMap);
			if(dataList != null && dataList.size() >0) {
				//查询当前用户所在机构名
				String orgName = "";
				VmuserVmorgMapDTO vmuserVmorgMapDTO = dataList.get(0);
				if(vmuserVmorgMapDTO!=null && vmuserVmorgMapDTO.getOrgId()!=null){
					OrgInfo orgInfo = orgAPI.getOrgInfo(vmuserVmorgMapDTO.getOrgId()+"");
					orgName = orgInfo.getOrgName();
				}
				
				//查询当前用户名
				UserInfo userInfo = orgAPI.getUserInfoDetail(userId);
				String userName = "";
				if(userInfo != null){
					userName = userInfo.getUserName();
				}
				
				throw new Exception("当前用户【"+userName+"】ID:"+userId+"已经存在于"+orgName+"，请联系"+orgName+"负责人将其从当前机构中移除后再添加。");
			}
		}
	
	}
	
	
	/**
     * @author dlg
     * @description: 查询员工虚拟组织关系表对象是否重复
     * @date 2015-01-26 17:15:01
     * @param id
     * @return
     * @throws
     */ 
	public List<VmuserVmorgMapDTO> isvalidateVmuserVmorgMap(String userId,String orgId,String orgType) throws Exception{
		VmuserVmorgMapDTO dto = new VmuserVmorgMapDTO();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		dto.setUserId(Long.parseLong(userId));
		dto.setOrgId(Long.parseLong(orgId));
		dto.setOrgType(orgType);
		paramMap.put("dto", dto);
		//验证对象是否重复 
		List<VmuserVmorgMapDTO> dataList = this.validateVmuserVmorgMapBycondtions(paramMap);
		
	    return dataList;
	    
	}
	
	/**
     * @author dlg
     * @description: 根据条件查询员工虚拟组织关系表对象是否重复方法
     * @date 2015-01-26 17:15:01
     * @param id
     * @return
     * @throws
     */ 
	public List<VmuserVmorgMapDTO> validateVmuserVmorgMapBycondtions(Map<String,Object> searchParams) throws Exception {
	    List<VmuserVmorgMapDTO> dataList = dao.validateVmuserVmorgMapBycondtions(searchParams);
        return dataList;
    }

	/**
     * @title: insertcommonVmdata
     * @author dlg
     * @description: 插入数据权限公共方法
     * @date 2015-01-22 14:15:01
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
    public void insertcommonVmdata(VmdataPrivDTO dto) throws Exception{
    	
    	//插入数据权限集合表
		Map<String, Object> dataParamMap = new HashMap<String, Object>();
		VmdataPrivDTO  data = new VmdataPrivDTO();
		/**用户ID*/
		data.setUserId(dto.getUserId());
		/**数据归属人ID*/
		data.setOwnerId(dto.getOwnerId());
		/**数据ORG_ID*/
		data.setOrgId(dto.getOrgId());
		/**ORG_TYPE*/
		data.setOrgType(dto.getOrgType());
		data.setCreateBy(dto.getUserId());
		//data.setVmRuleMappingId(dto.getId());
		dataParamMap.put("dto", data);
		dataParamMap.put("vmtableName",dto.getOrgType()+"_"+"VMDATA_PRIV");
		dataParamMap.put("seqName","seq_"+dto.getOrgType()+"_VMDATA_PRIV");
		//校验重复
		List<VmdataPrivDTO> vmdlist= vmdataservice.searchVmdataPrivrpeate(dataParamMap);
	    if(vmdlist.size()==0||"0".equals(vmdlist.size())){
			pDao.insertVmdataPriv(dataParamMap);
		}
		
    }
	
	
	/**
     * @title: insertVmuserVmorgMap
     * @author dlg
     * @description: 新增 员工虚拟组织关系表对象
     * @date 2015-01-16 17:15:01
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	@Transactional(rollbackFor=Exception.class)
	public Long insertVmuserVmorgMap(VmuserVmorgMapDTO dto,String type) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//新建VmdataPrivDTO数据权限对象
		VmdataPrivDTO  vmdatadto=new VmdataPrivDTO();
		
		//根据组织ID去查询 VmtreeInfoDTO 对象
    	VmtreeInfoDTO vmtreeInfoDTO=vmtreeservice.queryVmtreeInfoByPrimaryKey(dto.getOrgId().toString(),dto.getOrgType());
    	
    	//根据ORGID查询组织员工关系表，查询HR下面管理的人员    
    	//如果是最底层不需要查询,只查询下面一级管辖的人员
		Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("orgId", dto.getOrgId().toString().trim());
        searchParams.put("orgType", dto.getOrgType().toString().trim());
        
		List<VmuserVmorgMapDTO>  vmOrgUserList=dao.searchVmuserVmorgMapByparent(searchParams);
		
		
    	//一、先判断是否是HR的叶子节点
    	if(vmtreeInfoDTO.getSourceType().equals("HR")){
    		     //如果是HR机构，并且是最低层机构endflag为1。 则新增  数据权限表  User_Id, Owner_Id
	    		if(vmtreeInfoDTO.getEndFlag().equals("1")){
	    			    //查询本HR下面管理的人员
	    			if(vmOrgUserList.size()>=1){
	    				    //先查询是否有虚拟的下级机构  如果有，增加下面的管理人员
	    				for(VmuserVmorgMapDTO vmuserdto:vmOrgUserList){
		    				
		    				vmdatadto.setOwnerId(vmuserdto.getUserId());//HR下面管理的人员
		    				vmdatadto.setUserId(dto.getUserId());
		    				vmdatadto.setOrgType(dto.getOrgType());
		    				
		    				//插入数据权限表
		    				insertcommonVmdata(vmdatadto);
		    	            }
	    				//加入自己本身
	    				vmdatadto.setOwnerId(dto.getUserId());
	    				vmdatadto.setUserId(dto.getUserId());
	    				vmdatadto.setOrgType(dto.getOrgType());
	    				   //校验重复
	    					insertcommonVmdata(vmdatadto);
	    				
	    			}else
	    			{
	    				vmdatadto.setOwnerId(dto.getUserId());
	    				vmdatadto.setUserId(dto.getUserId());
	    				vmdatadto.setOrgType(dto.getOrgType());
	    				//插入数据权限表
	    				insertcommonVmdata(vmdatadto);
	    			}
	    		//如果是HR机构，并且不是最低层机构endflag为0。则新增  (1)HR数据权限表 User_Id,  Org_Id (2)虚拟userid userid
	    		//分两种情况，有可能包含下级是HR叶子节点的情况   一、找下级直到是HR叶子节点。 二、找最低层也不是HR叶子节点，需要加最低的虚拟部门的userid
	    		}else{
	    			searchParams.put("sourceType","HR");
	    	        searchParams.put("endFlag", "1");
	    			List<VmtreeInfoDTO> vmtreedtoHRlist=vmtreeservice.searchVmtreeInfoForTreeByType(searchParams);
	    			if(vmtreedtoHRlist.size()>=1){
	    				for(VmtreeInfoDTO vmtreedto:vmtreedtoHRlist){
	    					//找下级是HR叶子节点
	    				
	    						vmdatadto.setUserId(dto.getUserId());
	    	    				vmdatadto.setOrgId(vmtreedto.getOrgId());
	    	    				vmdatadto.setOrgType(vmtreedto.getOrgType());
	    	    				//插入数据权限表
	    	    				insertcommonVmdata(vmdatadto);
	    				}
	    			}
	    			//不是HR叶子节点,并且是虚拟组织,endflag=0的，需要添加该虚拟组织的人员userid-ownerid到数据权限表
	    			else{
	    				//查询虚拟组织下管辖的人员
	    				Map<String, Object> searchParamxn = new HashMap<String, Object>();
	    				searchParamxn.put("orgId", dto.getOrgId().toString().trim());
	    				searchParamxn.put("orgType", dto.getOrgType().toString().trim());
	    				searchParamxn.put("sourceType","XN");
						List<VmtreeInfoDTO>  vmtreedtoXNlist=vmtreeservice.searchVmtreeInfoForTreeByType(searchParamxn);
						if(vmtreedtoXNlist.size()>=1){
	    				    //先查询是否有虚拟的下级机构  如果有，增加下面的人员
	    				for(VmtreeInfoDTO vmtreedto:vmtreedtoXNlist){
	    					
	    					Map<String, Object> searchParamsxn = new HashMap<String, Object>();
	    					searchParamsxn.put("orgId", vmtreedto.getOrgId().toString().trim());
	    					searchParamsxn.put("orgType", vmtreedto.getOrgType().toString().trim());
	    			                  //查询包含的员工
	    					List<VmuserVmorgMapDTO>  vmOrgUserListxn=dao.searchVmuserVmorgMapByOrgId(searchParamsxn);
	    					for(VmuserVmorgMapDTO vmuserxndto:vmOrgUserListxn){
	    						if(vmOrgUserListxn.size()>=1){
		    						vmdatadto.setOwnerId(vmuserxndto.getUserId());//
				    				vmdatadto.setUserId(dto.getUserId());
				    				vmdatadto.setOrgType(vmuserxndto.getOrgType());
				    				//插入数据权限表
				    				insertcommonVmdata(vmdatadto);
		    					}
	    					}
		    	            }
	    			}
	    			}
			    //最后加入自身
			    vmdatadto.setOwnerId(dto.getUserId());
				vmdatadto.setUserId(dto.getUserId());
				vmdatadto.setOrgType(dto.getOrgType());
					insertcommonVmdata(vmdatadto);
				
	    	}
    	}
        //二、如果不是HR机构,是虚拟机构XN，只能是最低层	
    	else
    	{   
                vmdatadto.setUserId(dto.getUserId());
				vmdatadto.setOwnerId(dto.getUserId());//HR下面管理的人员
				vmdatadto.setOrgType(dto.getOrgType());
				//vmdataservice.insertVmdataPrivforUser(vmdatadto);
				//插入数据权限表
				insertcommonVmdata(vmdatadto);
    		
    	}
    	
		paramMap.put("dto", dto);
		//最后新增到组织关系表中
		if(type.equals("add")){
		int count = dao.insertVmuserVmorgMap(paramMap);
		}
		VmuserVmorgMapDTO resultDto = (VmuserVmorgMapDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateVmuserVmorgMap
     * @author dlg
     * @description: 修改 员工虚拟组织关系表对象
     * @date 2015-01-16 17:15:01
     * @param paramMap
     * @return
     * @throws
     */
	public void updateVmuserVmorgMap(VmuserVmorgMapDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateVmuserVmorgMap(paramMap);
	}
	/**
     * @title: deleteVmuserVmorgMapByPrimaryKey
     * @author dlg
     * @description: 删除 员工虚拟组织关系表,按主键
     * @date 2015-01-16 17:15:01
     * @param paramMap
     * @throws
     */ 
	public void deleteVmuserVmorgMapByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteVmuserVmorgMapByPrimaryKey(paramMap);
	}
	
	/**
     * @title: deleteVmuserVmorgMapByUserId
     * @author dlg
     * @description: 按 员工ID和类型删除虚拟组织关系表
     * @date 2015-01-22 16:15:01
     * @param paramMap
     * @throws
     */ 
	public void deleteVmuserVmorgMapByUserId(String userid,String orgType) throws Exception {
		if(StringUtils.isEmpty(userid)) throw new Exception("删除失败！传入的参数为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		paramMap.put("orgType", orgType);
		dao.deleteVmuserVmorgMapByUserId(paramMap);
	}
	
	
	/**
	 * 按userId和orgId删除虚拟组织关系表
	 * @param userId
	 * @param orgId
	 * @param orgType
	 * @throws Exception
	 */
	public void deleteVmuserVmorgMapByUserIdAndOrgId(String userId, String orgId, String orgType) throws Exception {
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(orgId)){
			throw new Exception("删除失败！传入的参数为null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("orgId", orgId);
		paramMap.put("orgType", orgType);
		dao.deleteVmuserVmorgMapByUserIdAndOrgId(paramMap);
	}
	
	/**
     * @title: deleteVmuserVmorgMapByOrgId
     * @author dlg
     * @description: 按 ORGID和类型删除虚拟组织关系表
     * @date 2015-01-22 16:15:01
     * @param paramMap
     * @throws
     */ 
	public void deleteVmuserVmorgMapByOrgId(String orgid,String orgType) throws Exception {
		if(StringUtils.isEmpty(orgid)) throw new Exception("删除失败！传入的参数为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orgid", orgid);
		paramMap.put("orgType", orgType);
		dao.deleteVmuserVmorgMapByOrgId(paramMap);
	}
	
	
	/**
	 * 根据userId和orgId删除“员工虚拟组织关系表”数据，同时删除“数据权限表”和“映射表”数据
     * 逻辑：
     * 1、只删除“员工虚拟组织关系表VMUSER_VMORG_MAP”下，当前用户，当前组织的数据，如果当前用户还在别的组织下，那条数据不删除
     * 2、由于“数据权限表”和“映射表”是以userId维度去管理的，所以，只有当“员工虚拟组织关系表VMUSER_VMORG_MAP”下没有此用户数据后，才删除“数据权限表”和“映射表”数据
	 * @param userIdsAndOrgIdsStr
	 * @param orgType
	 * @throws Exception
	 */
	public void deleteVmuserVmorgMapAndPrivByUserIdAndOrgId(String userIdsAndOrgIdsStr, String orgType) throws Exception{
		if(userIdsAndOrgIdsStr==null || "".equals(userIdsAndOrgIdsStr)){
			throw new Exception("删除失败！传入的参数为空");
		}
		
		String userId = "";
		String orgId = "";
		if(userIdsAndOrgIdsStr.indexOf(",") > 0){
            String[] userIdsAndOrgIds = userIdsAndOrgIdsStr.split(",");
            for(String userIdAndOrgId : userIdsAndOrgIds){
            	userId = userIdAndOrgId.split("#")[0];
            	orgId = userIdAndOrgId.split("#")[1];
            	
            	//删除当前用户，当前组织的“员工虚拟组织关系表VMUSER_VMORG_MAP”数据
            	this.deleteVmuserVmorgMapByUserIdAndOrgId(userId, orgId, orgType);
            	
            	//查询“员工虚拟组织关系表VMUSER_VMORG_MAP”下是否还有当前用户的数据
            	VmuserVmorgMapDTO dto = new VmuserVmorgMapDTO();
            	dto.setUserId(Long.valueOf(userId));
            	dto.setOrgType(orgType);
            	Map<String, Object> searchParams = new HashMap<String, Object>();
        		searchParams.put("dto", dto);
            	
        		//如果虚拟树下没有当前用户的数据了，再删除权限数据
            	List userMapList = dao.searchVmuserVmorgMap(searchParams);
            	if(userMapList!=null && userMapList.size()==0){
            		//删除数据权限和MAPPING 参数：(人员ID 删除类型：机构|人员 ,树类型)
                    vmrulemapservice.deletePrivApi(userId, "1", orgType);
            	}
            }
        } 
		else{
			userId = userIdsAndOrgIdsStr.split("#")[0];
        	orgId = userIdsAndOrgIdsStr.split("#")[1];
        	
        	//删除当前用户，当前组织的“员工虚拟组织关系表VMUSER_VMORG_MAP”数据
        	this.deleteVmuserVmorgMapByUserIdAndOrgId(userId, orgId, orgType);
        	
        	//查询“员工虚拟组织关系表VMUSER_VMORG_MAP”下是否还有当前用户的数据
        	VmuserVmorgMapDTO dto = new VmuserVmorgMapDTO();
        	dto.setUserId(Long.valueOf(userId));
        	dto.setOrgType(orgType);
        	Map<String, Object> searchParams = new HashMap<String, Object>();
    		searchParams.put("dto", dto);
        	
    		//如果虚拟树下没有当前用户的数据了，再删除权限数据
        	List userMapList = dao.searchVmuserVmorgMap(searchParams);
        	if(userMapList!=null && userMapList.size()==0){
        		//删除数据权限和MAPPING 参数：(人员ID 删除类型：机构|人员 ,树类型)
                vmrulemapservice.deletePrivApi(userId, "1", orgType);
        	}
        }
	}
	
	
	public List<SysUserDTO> searchVmuserByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysUserDTO> dataList =  dao.searchVmuserByPaging(searchParams);
		return dataList;
	}
	
	/**
	 * 根据orgId将离职人员以及业务权限进行清理
	 * @param orgId
	 * @param orgType
	 */
	public void modifyCleanVmorgMap(String orgId, String orgType) {
		//清理离职人员在业务用户配置中的业务数据权限
		Map<String, Object> privMap=new HashMap<String, Object>();
		privMap.put("vmtableName", orgType+"_VMDATA_PRIV");
		privMap.put("startOrg", orgId);
		privMap.put("mapping", "false");//true 标识这个清理是由映射发起的。
		privMap.put("orgType", orgType);
		pDao.modifyCleanVmruleDataPriv(privMap);
		
		//清理离职人员的业务用户配置数据
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("orgType", orgType);
		paramMap.put("startOrg", orgId);
		dao.modifyCleanVmorgMap(paramMap);
		
	}
	
}

