package com.jy.modules.quartzJob.qrtzextlog.service;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import nl.justobjects.pushlet.util.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.modules.quartzJob.qrtzextlog.dto.QrtzExtLogDTO;
import com.jy.modules.quartzJob.qrtzextlog.dao.QrtzExtLogDao;

/**
 * @classname: QrtzExtLogService
 * @description: 定义  QRTZ_EXT_LOG 实现类
 * @author:  dell
 */
@Service("com.jy.modules.quartzJob.qrtzextlog.service.QrtzExtLogService")
public class QrtzExtLogService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private QrtzExtLogDao dao;

	/**
     * @author dell
     * @description: 分页查询 QRTZ_EXT_LOG列表
     * @date 2016-11-15 16:08:47
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<QrtzExtLogDTO> searchQrtzExtLogByPaging(Map<String,Object> searchParams) throws Exception {
		List<QrtzExtLogDTO> dataList =  dao.searchQrtzExtLogByPaging(searchParams);
		return dataList;
	}
	/**
     * @author dell
     * @description: 查询QRTZ_EXT_LOG对象
     * @date 2016-11-15 16:08:47
     * @param id
     * @return
     * @throws
     */ 
	public QrtzExtLogDTO queryQrtzExtLogByPrimaryKey(String id)
			throws Exception {
		QrtzExtLogDTO dto = dao.findQrtzExtLogByPrimaryKey(id);
		if (dto == null)
			dto = new QrtzExtLogDTO();
		return dto;
	}

	/**
     * @title: insertQrtzExtLog
     * @author dell
     * @description: 新增 QRTZ_EXT_LOG对象
     * @date 2016-11-15 16:08:47
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertQrtzExtLog(QrtzExtLogDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertQrtzExtLog(paramMap);
		
		QrtzExtLogDTO resultDto = (QrtzExtLogDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateQrtzExtLog
     * @author dell
     * @description: 修改 QRTZ_EXT_LOG对象
     * @date 2016-11-15 16:08:47
     * @param paramMap
     * @return
     * @throws
     */
	public int updateQrtzExtLogFinish(QrtzExtLogDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		return dao.updateQrtzExtLogFinish(paramMap);
	}

	/**
	 * 查询定时任务是否记录日志（qrtz_triggers.write_log == 0）
	 * @param triggerName 定时任务名称（qrtz_triggers.trigger_name）
	 * @return 默认都记录日志，只有设置为0时才不记录日志
	 */
	public boolean getQrtzTriggersWriteLogByTriggerName(String triggerName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("triggerName", triggerName);
		
		Map<String, Object> resultMap = null;
		try {
			resultMap = dao.getQrtzTriggersWriteLogByPrimaryKey(paramMap);
		} catch (Exception ex) {
			Log.error("查询定时任务是否记录日志出错", ex);
		}
		if (resultMap != null) {
			BigDecimal zero = new BigDecimal(0);
			Object writeLog = resultMap.get("WRITE_LOG");
			if (zero.equals(writeLog)) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
}

