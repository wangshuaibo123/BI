package com.jy.modules.platform.bizauth.vmtreeinfo.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jy.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService;
import com.jy.modules.platform.bizauth.vmtreeinfo.dao.VmtreeInfoDao;
import com.jy.modules.platform.bizauth.vmtreeinfo.dto.VmtreeInfoDTO;
import com.jy.modules.platform.bizauth.vmuservmorgmap.service.VmuserVmorgMapService;

/**
 * @classname: VmtreeInfoService
 * @description: 定义  虚拟树表 实现类
 * @author:  chen_gang
 */
@Service("com.jy.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService")
public class VmtreeInfoService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private VmtreeInfoDao dao;
	@Autowired
	@Qualifier("com.jy.modules.platform.bizauth.vmrulemapping.service.VmruleMappingService")
	private VmruleMappingService  vmruleMappingService;
	@Autowired
	@Qualifier("com.jy.modules.platform.bizauth.vmuservmorgmap.service.VmuserVmorgMapService")
	private VmuserVmorgMapService vmuserVmorgMapService;

	/**
     * @author chen_gang
     * @description: 分页查询 虚拟树表列表
     * @date 2015-01-16 17:14:31
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<VmtreeInfoDTO> searchVmtreeInfoByPaging(Map<String,Object> searchParams) throws Exception {
		List<VmtreeInfoDTO> dataList =  dao.searchVmtreeInfoByPaging(searchParams);
		return dataList;
	}
	/**
     * @author chen_gang
     * @description: 按条件查询虚拟树表列表
     * @date 2015-01-16 17:14:31
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<VmtreeInfoDTO> searchVmtreeInfo(Map<String,Object> searchParams) throws Exception {
	    List<VmtreeInfoDTO> dataList = dao.searchVmtreeInfo(searchParams);
        return dataList;
    }
	/**
     * @author chen_gang
     * @description: 查询虚拟树表对象
     * @date 2015-01-16 17:14:31
     * @param orgId
     * @return
     * @throws
     */ 
	public VmtreeInfoDTO queryVmtreeInfoByPrimaryKey(String orgId,String orgType) throws Exception {
	    Map<String,Object> searchParams=new HashMap<String,Object>();
	    searchParams.put("orgId", orgId);
	    searchParams.put("orgType",orgType );
		VmtreeInfoDTO dto = dao.findVmtreeInfoByPrimaryKey(searchParams);
		
		if(dto == null) dto = new VmtreeInfoDTO();
		
		return dto;
		
	}

	/**
     * @title: insertVmtreeInfo
     * @author chen_gang
     * @description: 新增 虚拟树表对象
     * @date 2015-01-16 17:14:31
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertVmtreeInfo(VmtreeInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertVmtreeInfo(paramMap);
		
		VmtreeInfoDTO resultDto = (VmtreeInfoDTO) paramMap.get("dto");
		Long keyId = resultDto.getOrgId();
		return keyId;
	}
	/**
     * @title: updateVmtreeInfo
     * @author chen_gang
     * @description: 修改 虚拟树表对象
     * @date 2015-01-16 17:14:31
     * @param paramMap
     * @return
     * @throws
     */
	public void updateVmtreeInfo(VmtreeInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateVmtreeInfo(paramMap);
	}
	/**
     * @title: deleteVmtreeInfoByPrimaryKey
     * @author chen_gang
     * @description: 删除 虚拟树表,按主键
     * @date 2015-01-16 17:14:31
     * @param paramMap
     * @throws
     */ 
	public void deleteVmtreeInfoByPrimaryKey(VmtreeInfoDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		//第一步删除该虚拟树节点下权限
		vmruleMappingService.deletePrivApi(ids, "2", baseDto.getOrgType());
		//第二步删除该虚拟树节点下的人员
		vmuserVmorgMapService.deleteVmuserVmorgMapByOrgId(ids,baseDto.getOrgType());
		//删除该节点
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteVmtreeInfoByPrimaryKey(paramMap);
	}
	
		/**
		* @title: searchVmtreeInfoForTree
		* @author
		* @description: 查询虚拟树
		* @date 2015年1月17日 上午11:06:32
		* @param searchParams
		* @return
		* @throws 
		*/ 
	public List<VmtreeInfoDTO> searchVmtreeInfoForTree(	Map<String, Object> searchParams) {
		// TODO Auto-generated method stub
		return dao.searchVmtreeInfoForTree(searchParams);
	}
	
	/**
	 * 
	 * 查询多颗树
	 * 
	 */
	
	 public List<VmtreeInfoDTO> searchVmtreeInfoForAllTree(Map<String, Object> searchParams)
	 {
		 return dao.searchVmtreeInfoForAllTree(searchParams);
	 }
	
		/**
		* @title: insertVmtreeInfoForHR
		* @author
		* @description: 插入一个hr树中一个虚拟节点
		* @date 2015年1月17日 下午2:17:50
		* @param dto
		* @return
		* @throws 
		*/ 
	public Long insertVmtreeInfoForHR(VmtreeInfoDTO dto)throws Exception{
	    Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dto", dto);        
        int count = dao.insertVmtreeInfoForHR(paramMap); 
        VmtreeInfoDTO resultDto = (VmtreeInfoDTO) paramMap.get("dto");
        Long keyId = resultDto.getOrgId();
        return keyId;
	 }
	
	/**
	* @title: searchVmtreeInfoForTree
	* @author
	* @description: 根据TYPE查询虚拟树
		* @date 2015年2月4日 上午11:07:44
	* @param searchParams
	* @return
	* @throws 
	*/ 
	public List<VmtreeInfoDTO> searchVmtreeInfoForTreeByType(	Map<String, Object> searchParams) {
	// TODO Auto-generated method stub
	       return dao.searchVmtreeInfoForTreeByType(searchParams);
	}
	
}

