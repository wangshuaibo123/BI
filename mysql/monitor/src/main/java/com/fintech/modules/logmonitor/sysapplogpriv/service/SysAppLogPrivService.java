package com.fintech.modules.logmonitor.sysapplogpriv.service;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.platform.core.common.BaseDTO;

import com.fintech.modules.logmonitor.sysapplogpriv.dto.SysAppLogPrivDTO;
import com.fintech.modules.logmonitor.sysapplogpriv.dao.SysAppLogPrivDao;

/**
 * @classname: SysAppLogPrivService
 * @description: 定义  日志访问权限表 实现类
 * @author:  sunli
 */
@Service("com.fintech.modules.logmonitor.sysapplogpriv.service.SysAppLogPrivService")
public class SysAppLogPrivService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysAppLogPrivDao dao;

	/**
     * @author sunli
     * @description: 分页查询 日志访问权限表列表
     * @date 2016-05-30 11:30:54
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysAppLogPrivDTO> searchSysAppLogPrivByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysAppLogPrivDTO> dataList =  dao.searchSysAppLogPrivByPaging(searchParams);
		return dataList;
	}
	/**
     * @author sunli
     * @description: 按条件查询日志访问权限表列表
     * @date 2016-05-30 11:30:54
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysAppLogPrivDTO> searchSysAppLogPriv(Map<String,Object> searchParams) throws Exception {
	    List<SysAppLogPrivDTO> dataList = dao.searchSysAppLogPriv(searchParams);
        return dataList;
    }
	/**
     * @author sunli
     * @description: 查询日志访问权限表对象
     * @date 2016-05-30 11:30:54
     * @param id
     * @return
     * @throws
     */ 
	public SysAppLogPrivDTO querySysAppLogPrivByPrimaryKey(String id) throws Exception {
		
		SysAppLogPrivDTO dto = dao.findSysAppLogPrivByPrimaryKey(id);
		
		if(dto == null) dto = new SysAppLogPrivDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysAppLogPriv
     * @author sunli
     * @description: 新增 日志访问权限表对象
     * @date 2016-05-30 11:30:54
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysAppLogPriv(SysAppLogPrivDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysAppLogPriv(paramMap);
		
		SysAppLogPrivDTO resultDto = (SysAppLogPrivDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysAppLogPriv
     * @author sunli
     * @description: 修改 日志访问权限表对象
     * @date 2016-05-30 11:30:54
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysAppLogPriv(SysAppLogPrivDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysAppLogPriv(paramMap);
	}
	/**
     * @title: deleteSysAppLogPrivByPrimaryKey
     * @author sunli
     * @description: 删除 日志访问权限表,按主键
     * @date 2016-05-30 11:30:54
     * @param paramMap
     * @throws
     */ 
	public void deleteSysAppLogPrivByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysAppLogPrivByPrimaryKey(paramMap);
	}
	
	/**
	 * 物理删除
	 * @param baseDto
	 * @param ids
	 * @throws Exception
	 */
	public void deleteSysAppLogPrivByID(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysAppLogPrivByID(paramMap);
	}
	
	/**
	 * 获取用户权限
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public String getUserAppPriv(Map<String,Object> searchParams) throws Exception {
		SysAppLogPrivDTO dto = dao.getUserAppPriv(searchParams);
		if(dto != null){
			return dto.getAppIds();
		}
		else{
			return "null";
		}
	}

}

