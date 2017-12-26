package com.fintech.modules.logmonitor.sysappmanagecontactway.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.logmonitor.sysappmanagecontactway.dao.SysAppManageContactWayDao;
import com.fintech.modules.logmonitor.sysappmanagecontactway.dto.SysAppManageContactWayDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysAppManageContactWayService
 * @description: 定义  系统管理者联系方式 实现类
 * @author:  lei
 */
@Service("com.fintech.modules.logmonitor.sysappmanagecontactway.service.SysAppManageContactWayService")
public class SysAppManageContactWayService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysAppManageContactWayDao dao;

	/**
     * @author lei
     * @description: 分页查询 系统管理者联系方式列表
     * @date 2015-06-12 16:34:26
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysAppManageContactWayDTO> searchSysAppManageContactWayByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysAppManageContactWayDTO> dataList =  dao.searchSysAppManageContactWayByPaging(searchParams);
		return dataList;
	}
	/**
     * @author lei
     * @description: 按条件查询系统管理者联系方式列表
     * @date 2015-06-12 16:34:26
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysAppManageContactWayDTO> searchSysAppManageContactWay(Map<String,Object> searchParams) throws Exception {
	    List<SysAppManageContactWayDTO> dataList = dao.searchSysAppManageContactWay(searchParams);
        return dataList;
    }
	/**
     * @author lei
     * @description: 查询系统管理者联系方式对象
     * @date 2015-06-12 16:34:26
     * @param id
     * @return
     * @throws
     */ 
	public SysAppManageContactWayDTO querySysAppManageContactWayByPrimaryKey(String id) throws Exception {
		
		SysAppManageContactWayDTO dto = dao.findSysAppManageContactWayByPrimaryKey(id);
		
		if(dto == null) dto = new SysAppManageContactWayDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysAppManageContactWay
     * @author lei
     * @description: 新增 系统管理者联系方式对象
     * @date 2015-06-12 16:34:26
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysAppManageContactWay(SysAppManageContactWayDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysAppManageContactWay(paramMap);
		
		SysAppManageContactWayDTO resultDto = (SysAppManageContactWayDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysAppManageContactWay
     * @author lei
     * @description: 修改 系统管理者联系方式对象
     * @date 2015-06-12 16:34:26
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysAppManageContactWay(SysAppManageContactWayDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysAppManageContactWay(paramMap);
	}
	/**
     * @title: deleteSysAppManageContactWayByPrimaryKey
     * @author lei
     * @description: 删除 系统管理者联系方式,按主键
     * @date 2015-06-12 16:34:26
     * @param paramMap
     * @throws
     */ 
	public void deleteSysAppManageContactWayByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysAppManageContactWayByPrimaryKey(paramMap);
	}

}

