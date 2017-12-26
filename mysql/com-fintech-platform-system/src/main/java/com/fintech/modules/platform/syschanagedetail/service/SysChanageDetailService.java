package com.fintech.modules.platform.syschanagedetail.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.syschanagedetail.dao.SysChanageDetailDao;
import com.fintech.modules.platform.syschanagedetail.dto.SysChanageDetailDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysChanageDetailService
 * @description: 定义  变更信息明细表 实现类
 * @author:  DELL
 */
@Service("com.fintech.modules.platform.syschanagedetail.service.SysChanageDetailService")
public class SysChanageDetailService implements Serializable {
	private static final org.slf4j.Logger logger =  LoggerFactory.getLogger(SysChanageDetailService.class);
    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysChanageDetailDao dao;

	/**
     * @author DELL
     * @description: 分页查询 变更信息明细表列表
     * @date 2016-09-09 13:31:52
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysChanageDetailDTO> searchSysChanageDetailByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysChanageDetailDTO> dataList =  dao.searchSysChanageDetailByPaging(searchParams);
		return dataList;
	}
	/**
     * @author DELL
     * @description: 按条件查询变更信息明细表列表
     * @date 2016-09-09 13:31:52
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysChanageDetailDTO> searchSysChanageDetail(Map<String,Object> searchParams) throws Exception {
	    List<SysChanageDetailDTO> dataList = dao.searchSysChanageDetail(searchParams);
        return dataList;
    }
	/**
     * @author DELL
     * @description: 查询变更信息明细表对象
     * @date 2016-09-09 13:31:52
     * @param id
     * @return
     * @throws
     */ 
	public SysChanageDetailDTO querySysChanageDetailByPrimaryKey(String id) throws Exception {
		
		SysChanageDetailDTO dto = dao.findSysChanageDetailByPrimaryKey(id);
		
		if(dto == null) dto = new SysChanageDetailDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysChanageDetail
     * @author DELL
     * @description: 新增 变更信息明细表对象
     * @date 2016-09-09 13:31:52
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysChanageDetail(SysChanageDetailDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysChanageDetail(paramMap);
		
		SysChanageDetailDTO resultDto = (SysChanageDetailDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysChanageDetail
     * @author DELL
     * @description: 修改 变更信息明细表对象
     * @date 2016-09-09 13:31:52
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysChanageDetail(SysChanageDetailDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysChanageDetail(paramMap);
	}
	/**
     * @title: deleteSysChanageDetailByPrimaryKey
     * @author DELL
     * @description: 删除 变更信息明细表,按主键
     * @date 2016-09-09 13:31:52
     * @param paramMap
     * @throws
     */ 
	public void deleteSysChanageDetailByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysChanageDetailByPrimaryKey(paramMap);
	}
	/**
	 * 通过批次号/流程实例更新新数据至业务表中
	 * @param batNo
	 */
	public void updateNewVaToBizTab(String batNo) throws Exception {
		if(StringUtils.isEmpty(batNo)){
			String msg = "批次号不能为空,执行失败！";
			logger.error(msg);
			throw new Exception(msg);
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batNo", batNo);
		
		dao.updateNewVaToBizTab(paramMap);
	}
	
	/**
	 * 通过流程实例ID撤销业务表中状态变更
	 * @param batNo
	 */
	public void updateOldVaToBizTab(String batNo) throws Exception {
		if(StringUtils.isEmpty(batNo)){
			String msg = "流程实例ID不能为空,执行失败！";
			logger.error(msg);
			throw new Exception(msg);
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batNo", batNo);
		
		dao.updateOldVaToBizTab(paramMap);
	}

}

