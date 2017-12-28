package com.jy.modules.platform.bizauth.vmdatapriv.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.modules.platform.bizauth.vmdatapriv.dao.VmdataPrivDao;
import com.jy.modules.platform.bizauth.vmdatapriv.dto.VmdataPrivDTO;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: VmdataPrivService
 * @description: 定义  映射表 实现类
 * @author:  chen_gang
 */
@Service("com.jy.modules.platform.bizauth.vmdatapriv.service.VmdataPrivService")
public class VmdataPrivService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private VmdataPrivDao dao;

	/**
     * @author chen_gang
     * @description: 分页查询 映射表列表
     * @date 2015-01-16 17:14:46
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<VmdataPrivDTO> searchVmdataPrivByPaging(Map<String,Object> searchParams) throws Exception {
		
		List<VmdataPrivDTO> dataList =  dao.searchVmdataPrivByPaging(searchParams);
		return dataList;
	}
	/**
     * @author chen_gang
     * @description: 按条件查询映射表列表
     * @date 2015-01-16 17:14:46
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<VmdataPrivDTO> searchVmdataPriv(Map<String,Object> searchParams) throws Exception {
	    List<VmdataPrivDTO> dataList = dao.searchVmdataPriv(searchParams);
        return dataList;
    }
	
	/**
     * @author dlg
     * @description: 按条件查询映射表列表是否重复
     * @date 2015-01-16 17:14:46
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<VmdataPrivDTO> searchVmdataPrivrpeate(Map<String,Object> searchParams) throws Exception {
	    List<VmdataPrivDTO> dataList = dao.searchVmdataPrivrpeate(searchParams);
        return dataList;
    }
	
	
	
	/**
     * @author chen_gang
     * @description: 查询映射表对象
     * @date 2015-01-16 17:14:46
     * @param id
     * @return
     * @throws
     */ 
	public VmdataPrivDTO queryVmdataPrivByPrimaryKey(String id) throws Exception {
		
		VmdataPrivDTO dto = dao.findVmdataPrivByPrimaryKey(id);
		
		if(dto == null) dto = new VmdataPrivDTO();
		
		return dto;
		
	}

	/**
     * @title: insertVmdataPriv
     * @author chen_gang
     * @description: 新增 映射表对象
     * @date 2015-01-16 17:14:46
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertVmdataPriv(VmdataPrivDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertVmdataPriv(paramMap);
		
		VmdataPrivDTO resultDto = (VmdataPrivDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	
	/**
     * @title: insertVmdataPriv
     * @author chen_gang
     * @description: 添加员工时 -新增 数据权限表对象
     * @date 2015-01-16 17:14:46
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertVmdataPrivforUser(VmdataPrivDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertVmdataPrivforUser(paramMap);
		
		VmdataPrivDTO resultDto = (VmdataPrivDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateVmdataPriv
     * @author chen_gang
     * @description: 修改 映射表对象
     * @date 2015-01-16 17:14:46
     * @param paramMap
     * @return
     * @throws
     */
	public void updateVmdataPriv(VmdataPrivDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateVmdataPriv(paramMap);
	}
	/**
     * @title: deleteVmdataPrivByPrimaryKey
     * @author chen_gang
     * @description: 删除 映射表,按主键
     * @date 2015-01-16 17:14:46
     * @param paramMap
     * @throws
     */ 
	public void deleteVmdataPrivByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteVmdataPrivByPrimaryKey(paramMap);
	}
	
	   /**
     * @title: deletePrivByuserId
     * @author dlg
     * @description: 删除数据权限表,按userid
     * @date 2015-01-26 17:14:38
     * @param paramMap
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    public void deletePrivByuserId(String userId, String sourceType, String orgType) throws Exception {

        if ("1".equals(sourceType)) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            
            paramMap.put("vmPrivtableName", orgType + "_" + "VMDATA_PRIV");
            paramMap.put("userId", userId);
            paramMap.put("orgType", orgType);
            dao.deleteVmdataPrivByconditions(paramMap);
        }
    }
    /**
     * @author chen_gang
     * @description: 业务虚拟树快速调岗
     * @date 2015年11月10日 上午9:54:16
     * @param param
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
	public void fastChangeOrg(Map<String, Object> param) throws Exception {
    	//1、删除现有业务虚拟树的归属及现有岗位对应的数据权限，。
    	//2、新增新的业务虚拟树的新的机构岗位归属、增加相关人员的数据权限
    	//dao.fastChangeOrg(param);
    	dao.fastChangeOrg(param);
	}

}

