package com.easub.clip.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easub.clip.dao.ClipVideoSummaryStatDao;
import com.easub.clip.dto.ClipVideoSummaryStatDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: ClipVideoSummaryStatService
 * @description: 定义  clip_video_summary_stat 实现类
 * @author:  jiangshuncheng
 */
@Service("com.easub.clip.service.ClipVideoSummaryStatService")
public class ClipVideoSummaryStatService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private ClipVideoSummaryStatDao dao;

	/**
     * @author jiangshuncheng
     * @description: 分页查询 clip_video_summary_stat列表
     * @date 2018-01-24 11:48:49
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<ClipVideoSummaryStatDTO> searchClipVideoSummaryStatByPaging(Map<String,Object> searchParams) throws Exception {
		List<ClipVideoSummaryStatDTO> dataList =  dao.searchClipVideoSummaryStatByPaging(searchParams);
		return dataList;
	}
	/**
     * @author jiangshuncheng
     * @description: 按条件查询clip_video_summary_stat列表
     * @date 2018-01-24 11:48:49
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<ClipVideoSummaryStatDTO> searchClipVideoSummaryStat(Map<String,Object> searchParams) throws Exception {
	    List<ClipVideoSummaryStatDTO> dataList = dao.searchClipVideoSummaryStat(searchParams);
        return dataList;
    }
	/**
     * @author jiangshuncheng
     * @description: 查询clip_video_summary_stat对象
     * @date 2018-01-24 11:48:49
     * @param id
     * @return
     * @throws
     */ 
	public ClipVideoSummaryStatDTO queryClipVideoSummaryStatByPrimaryKey(String id) throws Exception {
		
		ClipVideoSummaryStatDTO dto = dao.findClipVideoSummaryStatByPrimaryKey(id);
		
		if(dto == null) dto = new ClipVideoSummaryStatDTO();
		
		return dto;
		
	}

	/**
     * @title: insertClipVideoSummaryStat
     * @author jiangshuncheng
     * @description: 新增 clip_video_summary_stat对象
     * @date 2018-01-24 11:48:49
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertClipVideoSummaryStat(ClipVideoSummaryStatDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertClipVideoSummaryStat(paramMap);
		
		ClipVideoSummaryStatDTO resultDto = (ClipVideoSummaryStatDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateClipVideoSummaryStat
     * @author jiangshuncheng
     * @description: 修改 clip_video_summary_stat对象
     * @date 2018-01-24 11:48:49
     * @param paramMap
     * @return
     * @throws
     */
	public void updateClipVideoSummaryStat(ClipVideoSummaryStatDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateClipVideoSummaryStat(paramMap);
	}
	
	@Transactional
	public void batchInsert(List<ClipVideoSummaryStatDTO> dtoList) throws Exception{
		Map<String,Object> map = new HashMap();
		map.put("dtoList", dtoList);
		this.dao.batchInsert(map);
	}
	
	@Transactional
	public void deleteClipVideoSummaryStat(Map<String, Object> paramMap) throws Exception{
		this.dao.deleteClipVideoSummaryStat(paramMap);
	}

}

