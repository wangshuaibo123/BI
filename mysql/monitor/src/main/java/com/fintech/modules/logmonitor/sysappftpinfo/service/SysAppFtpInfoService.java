package com.fintech.modules.logmonitor.sysappftpinfo.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.logmonitor.sysappftpinfo.dao.SysAppFtpInfoDao;
import com.fintech.modules.logmonitor.sysappftpinfo.dto.SysAppFtpInfoDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysAppFtpInfoService
 * @description: 定义  业务系统节点FTP配置表 实现类
 * @author:  lei
 */
@Service("com.fintech.modules.logmonitor.sysappftpinfo.service.SysAppFtpInfoService")
public class SysAppFtpInfoService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysAppFtpInfoDao dao;

	/**
     * @author lei
     * @description: 分页查询 业务系统节点FTP配置表列表
     * @date 2015-04-03 10:06:16
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysAppFtpInfoDTO> searchSysAppFtpInfoByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysAppFtpInfoDTO> dataList =  dao.searchSysAppFtpInfoByPaging(searchParams);
		return dataList;
	}
	/**
     * @author lei
     * @description: 按条件查询业务系统节点FTP配置表列表
     * @date 2015-04-03 10:06:16
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysAppFtpInfoDTO> searchSysAppFtpInfo(Map<String,Object> searchParams) throws Exception {
	    List<SysAppFtpInfoDTO> dataList = dao.searchSysAppFtpInfo(searchParams);
        return dataList;
    }
	/**
     * @author lei
     * @description: 查询业务系统节点FTP配置表对象
     * @date 2015-04-03 10:06:16
     * @param id
     * @return
     * @throws
     */ 
	public SysAppFtpInfoDTO querySysAppFtpInfoByPrimaryKey(String id) throws Exception {
		
		SysAppFtpInfoDTO dto = dao.findSysAppFtpInfoByPrimaryKey(id);
		
		if(dto == null) dto = new SysAppFtpInfoDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysAppFtpInfo
     * @author lei
     * @description: 新增 业务系统节点FTP配置表对象
     * @date 2015-04-03 10:06:16
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysAppFtpInfo(SysAppFtpInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysAppFtpInfo(paramMap);
		
		SysAppFtpInfoDTO resultDto = (SysAppFtpInfoDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysAppFtpInfo
     * @author lei
     * @description: 修改 业务系统节点FTP配置表对象
     * @date 2015-04-03 10:06:16
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysAppFtpInfo(SysAppFtpInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysAppFtpInfo(paramMap);
	}
	/**
     * @title: deleteSysAppFtpInfoByPrimaryKey
     * @author lei
     * @description: 删除 业务系统节点FTP配置表,按主键
     * @date 2015-04-03 10:06:16
     * @param paramMap
     * @throws
     */ 
	public void deleteSysAppFtpInfoByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysAppFtpInfoByPrimaryKey(paramMap);
	}

}

