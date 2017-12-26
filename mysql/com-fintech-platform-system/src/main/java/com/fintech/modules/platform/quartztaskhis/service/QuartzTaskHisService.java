package com.fintech.modules.platform.quartztaskhis.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.quartztaskhis.dao.QuartzTaskHisDao;
import com.fintech.modules.platform.quartztaskhis.dto.QuartzTaskHisDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: QuartzTaskHisService
 * @description: 定义  定时任务执行轨迹表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.quartztaskhis.service.QuartzTaskHisService")
public class QuartzTaskHisService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private QuartzTaskHisDao dao;

	/**
     * @author
     * @description: 分页查询 定时任务执行轨迹表列表
     * @date 2014-12-24 10:12:17
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<QuartzTaskHisDTO> searchQuartzTaskHisByPaging(Map<String,Object> searchParams) throws Exception {
		List<QuartzTaskHisDTO> dataList =  dao.searchQuartzTaskHisByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询定时任务执行轨迹表列表
     * @date 2014-12-24 10:12:17
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<QuartzTaskHisDTO> searchQuartzTaskHis(Map<String,Object> searchParams) throws Exception {
	    List<QuartzTaskHisDTO> dataList = dao.searchQuartzTaskHis(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询定时任务执行轨迹表对象
     * @date 2014-12-24 10:12:17
     * @param id
     * @return
     * @throws
     */ 
	public QuartzTaskHisDTO queryQuartzTaskHisByPrimaryKey(String id) throws Exception {
		
		QuartzTaskHisDTO dto = dao.findQuartzTaskHisByPrimaryKey(id);
		
		if(dto == null) dto = new QuartzTaskHisDTO();
		
		return dto;
		
	}

	/**
     * @title: insertQuartzTaskHis
     * @author
     * @description: 新增 定时任务执行轨迹表对象
     * @date 2014-12-24 10:12:17
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertQuartzTaskHis(QuartzTaskHisDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertQuartzTaskHis(paramMap);
		
		QuartzTaskHisDTO resultDto = (QuartzTaskHisDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateQuartzTaskHis
     * @author
     * @description: 修改 定时任务执行轨迹表对象
     * @date 2014-12-24 10:12:17
     * @param paramMap
     * @return
     * @throws
     */
	public void updateQuartzTaskHis(QuartzTaskHisDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateQuartzTaskHis(paramMap);
	}
	/**
     * @title: deleteQuartzTaskHisByPrimaryKey
     * @author
     * @description: 删除 定时任务执行轨迹表,按主键
     * @date 2014-12-24 10:12:17
     * @param paramMap
     * @throws
     */ 
	public void deleteQuartzTaskHisByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteQuartzTaskHisByPrimaryKey(paramMap);
	}

}

