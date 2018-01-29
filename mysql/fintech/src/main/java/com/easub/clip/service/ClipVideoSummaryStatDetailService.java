package com.easub.clip.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easub.clip.dao.ClipVideoSummaryStatDetailDao;
import com.easub.clip.dto.ClipVideoSummaryStatDTO;
import com.easub.clip.dto.ClipVideoSummaryStatDetailDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: ClipVideoSummaryStatDetailService
 * @description: 定义  clip_video_summary_stat_detail 实现类
 * @author:  jiangshuncheng
 */
@Service("com.easub.clip.service.ClipVideoSummaryStatDetailService")
public class ClipVideoSummaryStatDetailService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private ClipVideoSummaryStatDetailDao dao;

	/**
     * @author jiangshuncheng
     * @description: 分页查询 clip_video_summary_stat_detail列表
     * @date 2018-01-26 20:01:11
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<ClipVideoSummaryStatDetailDTO> searchClipVideoSummaryStatDetailByPaging(Map<String,Object> searchParams) throws Exception {
		List<ClipVideoSummaryStatDetailDTO> dataList =  dao.searchClipVideoSummaryStatDetailByPaging(searchParams);
		return dataList;
	}
	/**
     * @author jiangshuncheng
     * @description: 按条件查询clip_video_summary_stat_detail列表
     * @date 2018-01-26 20:01:11
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<ClipVideoSummaryStatDetailDTO> searchClipVideoSummaryStatDetail(Map<String,Object> searchParams) throws Exception {
	    List<ClipVideoSummaryStatDetailDTO> dataList = dao.searchClipVideoSummaryStatDetail(searchParams);
        return dataList;
    }
	/**
     * @author jiangshuncheng
     * @description: 查询clip_video_summary_stat_detail对象
     * @date 2018-01-26 20:01:11
     * @param id
     * @return
     * @throws
     */ 
	public ClipVideoSummaryStatDetailDTO queryClipVideoSummaryStatDetailByPrimaryKey(String id) throws Exception {
		
		ClipVideoSummaryStatDetailDTO dto = dao.findClipVideoSummaryStatDetailByPrimaryKey(id);
		
		if(dto == null) dto = new ClipVideoSummaryStatDetailDTO();
		
		return dto;
		
	}

	/**
     * @title: insertClipVideoSummaryStatDetail
     * @author jiangshuncheng
     * @description: 新增 clip_video_summary_stat_detail对象
     * @date 2018-01-26 20:01:11
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertClipVideoSummaryStatDetail(ClipVideoSummaryStatDetailDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertClipVideoSummaryStatDetail(paramMap);
		
		ClipVideoSummaryStatDetailDTO resultDto = (ClipVideoSummaryStatDetailDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateClipVideoSummaryStatDetail
     * @author jiangshuncheng
     * @description: 修改 clip_video_summary_stat_detail对象
     * @date 2018-01-26 20:01:11
     * @param paramMap
     * @return
     * @throws
     */
	public void updateClipVideoSummaryStatDetail(ClipVideoSummaryStatDetailDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateClipVideoSummaryStatDetail(paramMap);
	}
	/**
     * @title: deleteClipVideoSummaryStatDetailByPrimaryKey
     * @author jiangshuncheng
     * @description: 删除 clip_video_summary_stat_detail,按主键
     * @date 2018-01-26 20:01:11
     * @param paramMap
     * @throws
     */ 
	public void deleteClipVideoSummaryStatDetailByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteClipVideoSummaryStatDetailByPrimaryKey(paramMap);
	}

	@Transactional
	public void batchInsert(List<ClipVideoSummaryStatDetailDTO> dtoList) throws Exception{
		Map<String,Object> map = new HashMap();
		map.put("dtoList", dtoList);
		this.dao.batchInsert(map);
	}
	
	@Transactional
	public void deleteClipVideoSummaryStatDetail(Map<String, Object> paramMap) throws Exception{
		this.dao.deleteClipVideoSummaryStatDetail(paramMap);
	}
	
}

