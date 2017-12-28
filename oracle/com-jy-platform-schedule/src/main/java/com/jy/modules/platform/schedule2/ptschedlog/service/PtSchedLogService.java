package com.jy.modules.platform.schedule2.ptschedlog.service;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.jy.modules.platform.schedule2.ptschedlog.dto.PtSchedLogDTO;
import com.jy.modules.platform.schedule2.ptschedlog.dao.PtSchedLogDao;

/**
 * @classname: PtSchedLogService
 * @description: 定义  pt_sched_log 实现类
 * @author:  JY-IT-D001
 */
@Service("com.jy.modules.platform.schedule2.ptschedlog.service.PtSchedLogService")
public class PtSchedLogService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private PtSchedLogDao dao;

	/**
     * @author JY-IT-D001
     * @description: 分页查询 pt_sched_log列表
     * @date 2017-02-07 16:22:25
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<PtSchedLogDTO> searchPtSchedLogByPaging(Map<String,Object> searchParams) throws Exception {
		List<PtSchedLogDTO> dataList =  dao.searchPtSchedLogByPaging(searchParams);
		return dataList;
	}

	/**
     * @title: insertPtSchedLog
     * @author JY-IT-D001
     * @description: 新增 pt_sched_log对象
     * @date 2017-02-07 16:22:25
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertPtSchedLog(PtSchedLogDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertPtSchedLog(paramMap);
		
		PtSchedLogDTO resultDto = (PtSchedLogDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}

	/**
     * @title: updatePtSchedLog
     * @author JY-IT-D001
     * @description: 修改 pt_sched_log对象
     * @date 2017-02-07 16:22:25
     * @param paramMap
     * @return
     * @throws
     */
	public void updatePtSchedLog(PtSchedLogDTO dto) throws Exception {
		// 为了防止字段太长而报错
		if (dto.getResult() != null &&
				dto.getResult().length() > 1000) {
			dto.setResult(dto.getResult().substring(0, 1000));
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updatePtSchedLog(paramMap);
	}
	
}

