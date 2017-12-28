package com.jy.modules.platform.sysbizlog.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.platform.sysbizlog.dao.SysBizLogDao;
import com.jy.modules.platform.sysbizlog.dto.SysBizLogDTO;
import com.jy.platform.core.common.BaseDTO;

/**
 * @classname: SysBizLogService
 * @description: 定义  业务日志表 实现类
 * @author:  chen_gang
 */
@Service("com.jy.modules.platform.sysbizlog.service.SysBizLogService")
public class SysBizLogService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysBizLogDao dao;

	/**
     * @author chen_gang
     * @description: 分页查询 业务日志表列表
     * @date 2014-10-15 16:30:12
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysBizLogDTO> searchSysBizLogByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysBizLogDTO> dataList =  dao.searchSysBizLogByPaging(searchParams);
		return dataList;
	}
	/**
     * @author chen_gang
     * @description: 按条件查询业务日志表列表
     * @date 2014-10-15 16:30:12
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysBizLogDTO> searchSysBizLog(Map<String,Object> searchParams) throws Exception {
	    List<SysBizLogDTO> dataList = dao.searchSysBizLog(searchParams);
        return dataList;
    }
	/**
     * @author chen_gang
     * @description: 查询业务日志表对象
     * @date 2014-10-15 16:30:12
     * @param id
     * @return
     * @throws
     */ 
	public SysBizLogDTO querySysBizLogByPrimaryKey(String id) throws Exception {
		
		SysBizLogDTO dto = dao.findSysBizLogByPrimaryKey(id);
		
		if(dto == null) dto = new SysBizLogDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysBizLog
     * @author chen_gang
     * @description: 新增 业务日志表对象
     * @date 2014-10-15 16:30:12
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysBizLog(SysBizLogDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysBizLog(paramMap);
		
		SysBizLogDTO resultDto = (SysBizLogDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysBizLog
     * @author chen_gang
     * @description: 修改 业务日志表对象
     * @date 2014-10-15 16:30:12
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysBizLog(SysBizLogDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysBizLog(paramMap);
	}
	/**
     * @title: deleteSysBizLogByPrimaryKey
     * @author chen_gang
     * @description: 删除 业务日志表,按主键
     * @date 2014-10-15 16:30:12
     * @param paramMap
     * @throws
     */ 
	public void deleteSysBizLogByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysBizLogByPrimaryKey(paramMap);
	}

}

