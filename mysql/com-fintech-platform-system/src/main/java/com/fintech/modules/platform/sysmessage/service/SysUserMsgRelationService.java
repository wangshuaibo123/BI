package com.fintech.modules.platform.sysmessage.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysmessage.dao.SysUserMsgRelationDao;
import com.fintech.modules.platform.sysmessage.dto.SysUserMsgRelationDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysUserMsgRelationService
 * @description: 定义  SYS_USER_MSG_RELATION 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysmessage.service.SysUserMsgRelationService")
public class SysUserMsgRelationService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysUserMsgRelationDao dao;

	/**
     * @author
     * @description: 分页查询 SYS_USER_MSG_RELATION列表
     * @date 2014-11-14 11:08:58
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysUserMsgRelationDTO> searchSysUserMsgRelationByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysUserMsgRelationDTO> dataList =  dao.searchSysUserMsgRelationByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询SYS_USER_MSG_RELATION列表
     * @date 2014-11-14 11:08:58
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysUserMsgRelationDTO> searchSysUserMsgRelation(Map<String,Object> searchParams) throws Exception {
	    List<SysUserMsgRelationDTO> dataList = dao.searchSysUserMsgRelation(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询SYS_USER_MSG_RELATION对象
     * @date 2014-11-14 11:08:58
     * @param id
     * @return
     * @throws
     */ 
	public SysUserMsgRelationDTO querySysUserMsgRelationByPrimaryKey(String id) throws Exception {
		
		SysUserMsgRelationDTO dto = dao.findSysUserMsgRelationByPrimaryKey(id);
		
		if(dto == null) dto = new SysUserMsgRelationDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysUserMsgRelation
     * @author
     * @description: 新增 SYS_USER_MSG_RELATION对象
     * @date 2014-11-14 11:08:58
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysUserMsgRelation(SysUserMsgRelationDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysUserMsgRelation(paramMap);
		
		SysUserMsgRelationDTO resultDto = (SysUserMsgRelationDTO) paramMap.get("dto");
		Long keyId = resultDto.getRelId();
		return keyId;
	}
	/**
     * @title: updateSysUserMsgRelation
     * @author
     * @description: 修改 SYS_USER_MSG_RELATION对象
     * @date 2014-11-14 11:08:58
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysUserMsgRelation(SysUserMsgRelationDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysUserMsgRelation(paramMap);
	}
	/**
     * @title: deleteSysUserMsgRelationByPrimaryKey
     * @author
     * @description: 删除 SYS_USER_MSG_RELATION,按主键
     * @date 2014-11-14 11:08:58
     * @param paramMap
     * @throws
     */ 
	public void deleteSysUserMsgRelationByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysUserMsgRelationByPrimaryKey(paramMap);
	}
	
	/**
     * @description: 获取所有可用的（且已分配角色）用户编号列表 
     * @date 2015-12-03 09:37:58
     * @return
     */
	public List<SysUserDTO> queryAllUsefulSysUser(){
		return dao.searchAllUsefulSysUser();
	}
}

