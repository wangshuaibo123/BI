package com.fintech.modules.platform.bizauth.vmauthregisterinfo.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.modules.platform.bizauth.vmauthregisterinfo.dao.VmauthRegisterInfoDao;
import com.fintech.modules.platform.bizauth.vmauthregisterinfo.dto.VmauthRegisterInfoDTO;
import com.fintech.modules.platform.bizauth.vmtreeinfo.dto.VmtreeInfoDTO;
import com.fintech.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: VmauthRegisterInfoService
 * @description: 定义  权限注册表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.bizauth.vmauthregisterinfo.service.VmauthRegisterInfoService")
public class VmauthRegisterInfoService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private VmauthRegisterInfoDao dao;
	@Autowired
	@Qualifier("com.fintech.modules.platform.bizauth.vmtreeinfo.service.VmtreeInfoService")
	private VmtreeInfoService vmtree;

	/**
     * @author
     * @description: 分页查询 权限注册表列表
     * @date 2015-01-16 17:14:11
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<VmauthRegisterInfoDTO> searchVmauthRegisterInfoByPaging(Map<String,Object> searchParams) throws Exception {
		List<VmauthRegisterInfoDTO> dataList =  dao.searchVmauthRegisterInfoByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询权限注册表列表
     * @date 2015-01-16 17:14:11
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<VmauthRegisterInfoDTO> searchVmauthRegisterInfo(Map<String,Object> searchParams) throws Exception {
	    List<VmauthRegisterInfoDTO> dataList = dao.searchVmauthRegisterInfo(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询权限注册表对象
     * @date 2015-01-16 17:14:11
     * @param id
     * @return
     * @throws
     */ 
	public VmauthRegisterInfoDTO queryVmauthRegisterInfoByPrimaryKey(String id) throws Exception {
		
		VmauthRegisterInfoDTO dto = dao.findVmauthRegisterInfoByPrimaryKey(id);
		
		if(dto == null) dto = new VmauthRegisterInfoDTO();
		
		return dto;
		
	}

	/**
     * @title: insertVmauthRegisterInfo
     * @author
     * @description: 新增 权限注册表对象
     * @date 2015-01-16 17:14:11
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	@Transactional(rollbackFor=Exception.class)
	public Long insertVmauthRegisterInfo(VmauthRegisterInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertVmauthRegisterInfo(paramMap);
		
		VmauthRegisterInfoDTO resultDto = (VmauthRegisterInfoDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		//执行创建sql 脚本
		String mapSql = dto.getMapInitSql();
		this.executeCreateTableSql(mapSql,paramMap);
		String dataSql = dto.getDataInitSql();
		this.executeCreateTableSql(dataSql,paramMap);
		/*dao.createTableBySQL(paramMap);
		dao.createTableBySQL2(paramMap);
		dao.createTableBySQL3(paramMap);
		dao.createTableBySQL4(paramMap);*/
		
		VmtreeInfoDTO treeDTO = new VmtreeInfoDTO();
		treeDTO.setCreateBy(dto.getCreateBy());
		treeDTO.setOrgName(dto.getVmtreeName());//虚拟树名称
		treeDTO.setOrgType(dto.getVmtreeCode());//虚拟树CODE
		treeDTO.setParentId(0L);//虚拟树根节点
		treeDTO.setSourceType("XN");//虚拟树
		//新增虚拟树 根节点
		vmtree.insertVmtreeInfo(treeDTO);
		
		return keyId;
	}
	private void executeCreateTableSql(String sqls, Map<String, Object> paramMap) {
		String[] sqlStrs = sqls.split(";");
		for(String sql :sqlStrs){
			paramMap.put("EXECUT_SQL", sql);
			
			if(StringUtils.isNotEmpty(sql.trim())){
				System.out.println("===========sql:"+sql);
				dao.createTableBySQL(paramMap);
			}
			
		}
	}
	/**
     * @title: updateVmauthRegisterInfo
     * @author
     * @description: 修改 权限注册表对象
     * @date 2015-01-16 17:14:11
     * @param paramMap
     * @return
     * @throws
     */
	public void updateVmauthRegisterInfo(VmauthRegisterInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateVmauthRegisterInfo(paramMap);
	}
	/**
     * @title: deleteVmauthRegisterInfoByPrimaryKey
     * @author
     * @description: 删除 权限注册表,按主键
     * @date 2015-01-16 17:14:11
     * @param paramMap
     * @throws
     */ 
	public void deleteVmauthRegisterInfoByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		VmauthRegisterInfoDTO dto = this.queryVmauthRegisterInfoByPrimaryKey(ids);
		String code = dto.getVmtreeCode();
		paramMap.put("vmtreeCode", code);
		dao.deleteVmauthRegisterInfoByPrimaryKey(paramMap);
		dao.dropTabinfo(paramMap);
		dao.dropTabinfo3(paramMap);
		//oracle 放开
//		dao.dropTabinfo2(paramMap);
//		dao.dropTabinfo4(paramMap);
	}
	/**
	 * 
	 * @param vmName
	 * @param vmCode
	 */
	public void queryVmauthRegisterInfo(String vmName,String vmCode) throws Exception{
		VmauthRegisterInfoDTO dto = new VmauthRegisterInfoDTO();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		dto.setVmtreeCode(vmCode);
		dto.setVmtreeName(vmName);
		paramMap.put("dto", dto);
		//先验证 code
		if(StringUtils.isNotEmpty(vmCode)){
			List<VmauthRegisterInfoDTO> dataList = this.searchVmauthRegisterInfo(paramMap);
			if(dataList != null && dataList.size() >0) throw new Exception("虚拟树代码已经存在！！！");
		}
		//验证 name
		if(StringUtils.isNotEmpty(vmName)){
			List<VmauthRegisterInfoDTO> dataList = this.searchVmauthRegisterInfo(paramMap);
			if(dataList != null && dataList.size() >0) throw new Exception("虚拟树名称已经存在！！！");
		}
		//code name
		List<VmauthRegisterInfoDTO> dataList = this.searchVmauthRegisterInfo(paramMap);
		if(dataList != null && dataList.size() >0) throw new Exception("虚拟树代码和名称已经存在！！！");
	}

}

