package com.fintech.modules.platform.sysorg.service;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysorg.dao.SysOrgUserDao;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysOrgUserService
 * @description: 定义  SYS_ORG_USER 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysorg.service.SysOrgUserService")
public class SysOrgUserService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysOrgUserDao dao;

	/**
     * @author
     * @description: 分页查询 SYS_ORG_USER列表
     * @date 2014-10-15 10:26:28
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysOrgUserDTO> searchSysOrgUserByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysOrgUserDTO> dataList =  dao.searchSysOrgUserByPaging(searchParams);
		return dataList;
	}
	
	/**Description: 分页查询 SYS_ORG_USER列表	模糊查询
	 * 	parentIds 		父节点id
	 *  orgName 		机构名称
	 *  userName 		用户名称
	 *  positionName	岗位名称
	 * Create Date: 2014年12月3日下午3:26:10<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgUserDTO> searchFuzzySysOrgUserByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysOrgUserDTO> dataList =  dao.searchFuzzySysOrgUserByPaging(searchParams);
		return dataList;
	}
	
	/**Description: 查询 SYS_ORG_USER列表	模糊查询	(不分页) 
	 * 	parentIds 		父节点id
	 *  orgName 		机构名称
	 *  userName 		用户名称
	 *  positionName	岗位名称
	 * Create Date: 2014年12月3日下午3:26:10<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgUserDTO> searchFuzzySysOrgUser( Map<String, Object> searchParams) {
		List<SysOrgUserDTO> dataList =  dao.searchFuzzySysOrgUser(searchParams);
		return dataList;
	}
	
	
	/**
     * @author
     * @description: 按条件查询SYS_ORG_USER列表
     * @date 2014-10-15 10:26:28
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysOrgUserDTO> searchSysOrgUser(Map<String,Object> searchParams) throws Exception {
	    List<SysOrgUserDTO> dataList = dao.searchSysOrgUser(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询SYS_ORG_USER对象
     * @date 2014-10-15 10:26:28
     * @param id
     * @return
     * @throws
     */ 
	public SysOrgUserDTO querySysOrgUserByPrimaryKey(String id) throws Exception {
		
		SysOrgUserDTO dto = dao.findSysOrgUserByPrimaryKey(id);
		
		if(dto == null) dto = new SysOrgUserDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysOrgUser
     * @author
     * @description: 新增 SYS_ORG_USER对象
     * @date 2014-10-15 10:26:28
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysOrgUser(SysOrgUserDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysOrgUser(paramMap);
		
		SysOrgUserDTO resultDto = (SysOrgUserDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysOrgUser
     * @author
     * @description: 修改 SYS_ORG_USER对象
     * @date 2014-10-15 10:26:28
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysOrgUser(SysOrgUserDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysOrgUser(paramMap);
	}
	/**
     * @title: deleteSysOrgUserByPrimaryKey
     * @author
     * @description: 删除 SYS_ORG_USER,按主键
     * @date 2014-10-15 10:26:28
     * @param paramMap
     * @throws
     */ 
	public void deleteSysOrgUserByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysOrgUserByPrimaryKey(paramMap);
	}


}

